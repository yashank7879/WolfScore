package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.fragment.MatchFactsFragment;

/**
 * Created by mindiii on 2/2/19.
 */

public class AboutMatchPagerAdpter extends FragmentPagerAdapter {
     Context mContext;
    public AboutMatchPagerAdpter(FragmentManager supportFragmentManager, Context mContext) {
        super(supportFragmentManager);
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MatchFactsFragment();
        }else {
            return  new MatchFactsFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.media);
        } else if (position == 1){
            return mContext.getString(R.string.match_facts );
        } else if (position == 2){
            return mContext.getString(R.string.head_to_head );
        } else if (position == 3){
            return mContext.getString(R.string.line_up );
        } else if (position == 4){
            return mContext.getString(R.string.live_tracker );
        } else if (position == 5){
            return mContext.getString(R.string.table );
        }else {
            return  mContext.getString(R.string.statistics);
        }
    }
}
