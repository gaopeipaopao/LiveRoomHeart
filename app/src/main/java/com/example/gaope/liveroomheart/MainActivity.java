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
        liveHeartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveHeartView.addHeart();
            }
        });
//        for (int i = 0;i < 5;i++){
//            liveHeartView.addHeart();
//        }
//        liveHeartView.addHeart();

    }
}
