package com.wolfscore.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wolfscore.R;

public class SplashScreenActivity extends AppCompatActivity {

    private Runnable mRunable;
    private Handler mHandler  = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen
        );

         mRunable= new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,SetupWolfScoreScreenOne.class);
                startActivity(intent);
                finish();
            }
        };
         mHandler.postDelayed(mRunable,2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunable);
    }
}

