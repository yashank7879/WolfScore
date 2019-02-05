package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wolfscore.R;
import com.wolfscore.adapter.AboutMatchPagerAdpter;
import com.wolfscore.databinding.ActivityAboutMatchBinding;

public class AboutMatchActivity extends AppCompatActivity {
    ActivityAboutMatchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding =  DataBindingUtil.setContentView(this,R.layout.activity_about_match);

        AboutMatchPagerAdpter adapter = new AboutMatchPagerAdpter(getSupportFragmentManager(), this);
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.viewPager.setCurrentItem(1);
    }
}
