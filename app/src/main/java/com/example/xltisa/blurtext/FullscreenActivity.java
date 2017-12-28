package com.example.xltisa.blurtext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * App starting ground
 */
public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        final Intent intent = new Intent(this, MainActivity.class);

        //延时效果
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();//finish() for finish this activity,
                         //otherwise will return this activity
                         //while exits MainActivity
            }
        };

        //after 3 seconds jump to Login activity
        timer.schedule(timerTask, 3*1000);

    }

}
