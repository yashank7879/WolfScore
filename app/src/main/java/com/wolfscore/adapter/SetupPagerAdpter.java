package com.wolfscore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wolfscore.listener.GetTeamListener;
import com.wolfscore.fragment.LocalTeamFragment;
import com.wolfscore.fragment.NotificationTypesFragment;
import com.wolfscore.fragment.PopularTeamFragment;
import com.wolfscore.fragment.TopPlayerFragment;

/**
 * Created by mindiii on 1/21/19.
 */

public class SetupPagerAdpter extends FragmentPagerAdapter {
private GetTeamListener listener;
    public SetupPagerAdpter(FragmentManager fm, GetTeamListener listener) {
        super(fm);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new LocalTeamFragment();
        }else if (i == 1){
            return new PopularTeamFragment();
        }else if (i == 2){
            return new TopPlayerFragment();
        }else{
            return new NotificationTypesFragment();
        }
    }


}
