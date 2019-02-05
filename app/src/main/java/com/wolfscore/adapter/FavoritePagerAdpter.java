package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.wolfscore.R;
import com.wolfscore.activity.FavoriteActivity;
import com.wolfscore.fragment.FavoriteFollowingFragment;
import com.wolfscore.fragment.FavoriteNotificationFragment;

/**
 * Created by mindiii on 1/30/19.
 */

public class FavoritePagerAdpter extends FragmentPagerAdapter {
    Context mContext;
    public FavoritePagerAdpter(FragmentManager supportFragmentManager, Context mContext) {
        super(supportFragmentManager);
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FavoriteFollowingFragment();
        } else {
            return new FavoriteNotificationFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.following);
        } else {
            return mContext.getString(R.string.notificationsCapital);
        }
    }
}
