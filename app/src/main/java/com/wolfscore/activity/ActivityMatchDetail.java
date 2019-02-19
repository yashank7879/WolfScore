package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolfscore.R;
import com.wolfscore.adapter.AboutMatchPagerAdpter;
import com.wolfscore.databinding.ActivityAboutMatchBinding;
import com.wolfscore.databinding.ActivityMatchDetailBinding;

/**
 * Created by mindiii on 12/2/19.
 */

public class ActivityMatchDetail extends AppCompatActivity {
    ActivityMatchDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_match_detail);

      //  AboutMatchPagerAdpter adapter = new AboutMatchPagerAdpter(getSupportFragmentManager(), this);

    }
}
