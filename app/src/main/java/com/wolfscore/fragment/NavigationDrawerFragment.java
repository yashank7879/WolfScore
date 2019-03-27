package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wolfscore.R;
import com.wolfscore.activity.HomeActivity;
import com.wolfscore.databinding.FragmentNavigationDrawerBinding;

/**
 * Created by mindiii on 22/3/19.
 */

public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {
    FragmentNavigationDrawerBinding binding;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_navigation_drawer, container, false);
        initialise();

        return binding.getRoot();
    }

    private void initialise() {
        binding.profileLayout.setOnClickListener(this);
        binding.matchLayout.setOnClickListener(this);
        binding.settingLayout.setOnClickListener(this);
        binding.aboutLayout.setOnClickListener(this);
        binding.bannerLayout.setOnClickListener(this);
        binding.purchaseLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_layout:
                if(HomeActivity.homeActivity.drawer_layout.isDrawerOpen(Gravity.START)) {
                    HomeActivity.homeActivity.drawer_layout.closeDrawer(Gravity.START);
                    // mSlideState=false;
                }
                Toast.makeText(context, "my profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.match_layout:
                Toast.makeText(context, "Matches", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_layout:
                Toast.makeText(context, "setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_layout:
                Toast.makeText(context, "about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.banner_layout:
                Toast.makeText(context, "banner", Toast.LENGTH_SHORT).show();
                break;
            case R.id.purchase_layout:
                Toast.makeText(context, "purchase", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "others", Toast.LENGTH_SHORT).show();
                break;

        }
        if(HomeActivity.homeActivity.drawer_layout.isDrawerOpen(Gravity.START)) {
            HomeActivity.homeActivity.drawer_layout.closeDrawer(Gravity.START);
            // mSlideState=false;
        }
    }
}
