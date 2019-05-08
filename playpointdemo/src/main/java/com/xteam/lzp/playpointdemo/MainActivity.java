package com.xteam.lzp.playpointdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    PlayPointView mPlayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlayView = findViewById(R.id.palyview);
        mPlayView.setOnTestListener(new PlayPointView.OnTestListener() {
            @Override
            public void finishView(PlayPointView view, boolean result) {
                MainActivity.this.finish();
            }
        });
    }
}
