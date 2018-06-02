package com.example.xyx.link;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 陈钊燚 on 2018/6/3.
 * QQ 1215638092
 * Github FourfireChen
 */
public class SplashActivity extends AppCompatActivity {
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                mHandler.post(() -> {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
                    finish();
                });
            }
        };
        timer.schedule(timerTask, 5000);

    }
}
