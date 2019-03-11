package com.wolfscore.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.crashlytics.android.Crashlytics;
import com.wolfscore.R;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

public class SplashScreenActivity extends AppCompatActivity {

    private Runnable mRunable;
    private Handler mHandler  = new Handler();
    RelativeLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
        main_layout=findViewById(R.id.main_layout);
       // getCountryData();

         mRunable= new Runnable() {
            @Override
            public void run() {
               /*if (PreferenceConnector.readBoolean(SplashScreenActivity.this, PreferenceConnector.IS_LOGIN, false)) {
                    Intent intent = new Intent(SplashScreenActivity.this,HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();

                }
                else{*/
                Intent intent = new Intent(SplashScreenActivity.this,SetupWolfScoreScreenOne.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();}
            //}
        };
         mHandler.postDelayed(mRunable,2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunable);
    }





}

