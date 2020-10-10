package com.nccs.java.gameapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {
/*
    public static int SLASH_TIME_OUT = 2000;
*/
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        Thread thread = new Thread(){


            @Override
            public void run() {
                try {
                    for(int i=0;i<100;i++){
                        progressBar.setProgress(i);
                        sleep(40);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {


                    // This method will be executed once the timer is over

                    Intent i = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
        }
}
