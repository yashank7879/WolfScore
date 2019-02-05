package com.wolfscore.utils;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by mindiii on 1/22/19.
 */

public class MyApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());

    }
}
