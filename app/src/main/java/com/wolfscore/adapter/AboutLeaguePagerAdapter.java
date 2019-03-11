package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.wolfscore.R;
import com.wolfscore.league.fragments.LeagueMatchFragment;
import com.wolfscore.league.fragments.LeagueTableFragment;
import com.wolfscore.matches.fragments.BlankFragment;
/**
 * Created by mindiii on 27/2/19.
 */

public class AboutLeaguePagerAdapter extends FragmentPagerAdapter {
    Context mContext;

    public AboutLeaguePagerAdapter(FragmentManager supportFragmentManager, Context mContext) {
        super(supportFragmentManager);
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {

        if (position==1)
        {
            return  new LeagueTableFragment();
        }
        else if (position == 2) {
            return new LeagueMatchFragment();
        }
        else {
            return  new BlankFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.trophies);
        } else if (position == 1){
            return mContext.getString(R.string.table );
        } else if (position == 2){
            return mContext.getString(R.string.matches);
        }

        else {
            return mContext.getString(R.string.matches);
        }
    }
}
