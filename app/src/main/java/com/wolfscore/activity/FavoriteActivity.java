package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wolfscore.R;
import com.wolfscore.adapter.FavoritePagerAdpter;
import com.wolfscore.adapter.SetupPagerAdpter;
import com.wolfscore.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        intializeView();
    }

    private void intializeView() {
        binding.ivBack.setOnClickListener(this);

        FavoritePagerAdpter adapter = new FavoritePagerAdpter(getSupportFragmentManager(), this);
        binding.viewpager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
