package com.wolfscore.matches.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.wolfscore.R;
import com.wolfscore.matches.fragments.TodayFragment;
import com.wolfscore.matches.fragments.TomorrowFragment;
import com.wolfscore.matches.fragments.YesterdayFragment;

/**
 * Created by mindiii on 4/2/19.
 */

public class matchListPagerAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    public matchListPagerAdapter(FragmentManager supportFragmentManager, Context mContext) {
        super(supportFragmentManager);
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new YesterdayFragment();
        }
        else if (position==1){
            return new TodayFragment();
        }
        else {
            return new TomorrowFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.yesterday);
        }
        else if (position==1){
            return mContext.getString(R.string.today);

        }
        else {
            return mContext.getString(R.string.Tomorrow);
        }
    }


}
