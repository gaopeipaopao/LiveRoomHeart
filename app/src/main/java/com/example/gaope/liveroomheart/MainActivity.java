package com.example.gaope.liveroomheart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private LiveHeartView liveHeartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        liveHeartView = (LiveHeartView) findViewById(R.id.live_view);
        //使用liveHeartView.setOnClickListener这种方法时，LiveHeartView的View对象已经创建好啦，
        //LiveHeartView的构造方法，onSizeChanged方法依次执行，再调用liveHeartView的addHeart()方法
        liveHeartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveHeartView.addHeart();
            }
        });
//        for (int i = 0;i < 5;i++){
//            liveHeartView.addHeart();
//        }

          //使用这种方法时，先调用liveHeartView的addHeart()方法，再创建LiveHeartView的View对象，
          //然后LiveHeartView的构造方法，onSizeChanged方法依次执行，
//        liveHeartView.addHeart();

    }
}
