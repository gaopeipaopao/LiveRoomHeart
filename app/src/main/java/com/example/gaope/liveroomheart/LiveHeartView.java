package com.example.gaope.liveroomheart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 仿直播间送爱心
 * 可以控制缩放,fraction
 * 可以控制透明度，fraction
 * 可以控制速率，动画的长短来控制
 * 使用List来存储数据
 * Created by gaope on 2018/5/24.
 */

public class LiveHeartView extends View {

    private static final String TAG = "LiveHeartView";

    private Paint paint;

    /**
     *存储Heart对象
     */
    private List<Heart> heartLists;

    /**
     * 存储Bitmap图片对象
     */
    private List<Bitmap> bitmapLists;

    /**
     * 映射PathMeasure
     */
    private float curr;

    /**
     * 缩放图片
     */
    private Matrix matrix;






    /**
     *
     * @param context
     * @param attrs
     */


    public LiveHeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();


        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);


//        path = new Path();
//        pm = new PathMeasure();

        init();
    }

    private void init() {

        bitmapLists = new ArrayList<>();
        heartLists = new ArrayList<>();

        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize = 2;
        Bitmap bitmap1 = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.blue,op);
        bitmapLists.add(bitmap1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.bule_deep,op);
        bitmapLists.add(bitmap2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.gray,op);
        bitmapLists.add(bitmap3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.green,op);
        bitmapLists.add(bitmap4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.popule,op);
        bitmapLists.add(bitmap5);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.red,op);
        bitmapLists.add(bitmap6);
        Bitmap bitmap7 = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.yellow,op);
        bitmapLists.add(bitmap7);
    }

    //addHeart()的调用在LiveHeartView的onSizeChanged()之前
    public void addHeart(){
        addHearets(new Random().nextInt(bitmapLists.size() - 1));
    }

    public void addHearets(final int iBitmap) {

      //  Log.d(TAG,"i:"+iBitmap);

        //当path为全局变量的时候，就导致每一个heart图片对象的三阶贝塞尔曲线都一致
        Path path = new Path();
        final PathMeasure pm = new PathMeasure();

        //动画中执行invalidate();方法时，画的是Heart对象的坐标，即三阶贝塞尔曲线
        final Heart heart = new Heart();
        PointF data1 = new PointF(0,0);
        PointF data2 = new PointF(0,0);
        PointF control1 = new PointF(0,0);
        PointF control2 = new PointF(0,0);

        if (iBitmap <0 || iBitmap > (bitmapLists.size() - 1))
            return;
        //得到控制点1与控制点2
        getData(data1,data2);
        getControl(control1,control2);
        //得到三阶贝塞尔曲线
        path.moveTo(data1.x,data1.y);
        path.cubicTo(control1.x,control1.y,control2.x,control2.y,data2.x,data2.y);
        //将path与pm相关联
        pm.setPath(path,false);
//        Log.d(TAG,"i:"+iBitmap);
//        Log.d(TAG,"datal1x:"+data1.x);
//        Log.d(TAG,"datal1y:"+data1.y);
//        Log.d(TAG,"datal2x:"+data2.x);
//        Log.d(TAG,"datal2y:"+data2.y);
//        Log.d(TAG,"control1x:"+control1.x);
//        Log.d(TAG,"control1y:"+control1.y);
//        Log.d(TAG,"control2x:"+control2.x);
//        Log.d(TAG,"control2y:"+control2.y);

        //开启动画

        ValueAnimator value = ValueAnimator.ofFloat(0,1);
        //采用随机数来控制动画的时间
        value.setDuration((long) (3000 + (Math.random() * 2000)));
        value.setInterpolator(new AccelerateDecelerateInterpolator());
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curr = (float) animation.getAnimatedValue();
                float[] pos = new float[2];
                float[] tan = new float[2];

//                Log.d(TAG,"aaa"+curr);

                //使用pm.getPosTan()方法得到pm关联的path的点上面的位置
                pm.getPosTan(curr * pm.getLength(),pos,tan);
                heart.setX(pos[0]);
                heart.setY(pos[1]);
                heart.setProcess(curr);
                invalidate();
            }
        });
        value.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                heartLists.add(heart);
                heart.setLoc(iBitmap);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                heartLists.remove(heart);
            }
        });
        value.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Log.d(TAG,TAG+"aaaaa");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (heartLists == null && heartLists.size() == 0)
            return;
        canvasBitmap(canvas);

    }

    private void canvasBitmap(Canvas canvas) {
       // Log.d(TAG,"hLSIez:"+heartLists.size());
        for (int i = 0;i < heartLists.size();i++){
            Heart heart = heartLists.get(i);
            paint.setAlpha((int) (255 * (1.0f - heart.getProcess())));
            matrix.reset();
            //设置heart上升过程中的透明度
            matrix.postTranslate(heart.getX(),heart.getY());
            //在heart上升过程中进行大小的缩放
            Log.d(TAG,"0.5f + heart.getProcess():"+(0.5f + heart.getProcess()));
            matrix.postScale(0.5f + heart.getProcess(),
                    0.5f + heart.getProcess(),
                    getWidth() / 2,getHeight());
            if (heart != null){
              //  Log.d(TAG,"loc:"+heart.getLoc());
                canvas.drawBitmap(bitmapLists.get(heart.getLoc()),matrix,paint);
            }
        }
    }

    private void getData(PointF data1,PointF data2){
        data1.x = getWidth() / 2;
        data1.y = getHeight();
        data2.x = getWidth() / 2;
        data2.y = 0;
    }

    private void getControl(PointF control1,PointF control2) {
        //Math.random()返回介于 0到1 之间的一个随机数
        //使用Math.random()得到随机的控制点

        Random r = new Random();
        float aa = (float) Math.random();
        float bb = (float) Math.random();

        Log.d(TAG,"aa:"+aa);
        Log.d(TAG,"bb:"+bb);

//        float a = (float) Math.random() * getWidth();
//        float b = (float) Math.random() * getHeight();

        control1.x = (float) (Math.random() * getWidth());
        control1.y = (float) (Math.random() * getWidth()  );

        control2.x = (float) (Math.random() * getWidth() );
        control2.y = (float) (Math.random() *getHeight() );

//        Log.d(TAG,TAG);
//        Log.d(TAG,"getWidth:"+getWidth());
//        Log.d(TAG,"control1x:"+control1.x);
//        Log.d(TAG,"control1y:"+control1.y);
//        Log.d(TAG,"control2x:"+control2.x);
//        Log.d(TAG,"control2y:"+control2.y);

        if (control1.x == control2.x && control1.y == control2.y){
            getControl(control1,control2);
        }

    }

}
