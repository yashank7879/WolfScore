package com.wolfscore.matches.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wolfscore.R;
import com.wolfscore.activity.HomeActivity;
import com.wolfscore.activity.LeagueFilteringActivity;
import com.wolfscore.fragment.SearchTeamFragment;
import com.wolfscore.matches.adapter.matchListPagerAdapter;


public class MatchListFragment extends Fragment implements View.OnClickListener {
    private TabLayout tab_layout;
    private ViewPager viewpager;
    private matchListPagerAdapter adapter;
    private  RelativeLayout rl_search;
    private ImageView filter;
    private ImageView calender;
    private FrameLayout search_fragment;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches_list, null);
        initialise(rootView);
        return rootView;
    }
    private void initialise(View rootView) {
      //  View toolBar = rootView.findViewById(R.id.toolbar);

       /* rl_search = toolBar.findViewById(R.id.rl_search);
        filter = toolBar.findViewById(R.id.filter);
        calender = toolBar.findViewById(R.id.calender);*/
        tab_layout=rootView.findViewById(R.id.tab_layout);
        viewpager=rootView.findViewById(R.id.viewpager);
        search_fragment=rootView.findViewById(R.id.search_fragment);
        adapter = new matchListPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        viewpager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(1);
       // viewpager.getCurrentItem();

      //  rl_search.setOnClickListener(this);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext =context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.rl_search:
                search_fragment.setVisibility(View.VISIBLE);
                replacesearchFragment(new SearchTeamFragment());
                break;
            case R.id.filter:
                Intent intent = new Intent(mContext,  LeagueFilteringActivity.class);
                intent.putExtra("CurrentFragment","");
                startActivity(intent);
               // startActivity(new Intent(mContext, LeagueFilteringActivity.class));
                break;*/
        }
    }

    protected void replacesearchFragment(Fragment fragment) {
        try {
            String backStateName = fragment.getClass().getName();
            android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(R.id.search_fragment, fragment);
                if (backStateName.equals("com.wolfscore.matches.fragments.MatchListFragment")) {
                    ft.addToBackStack(backStateName);
                } else {
                    ft.addToBackStack(null);
                }
                ft.commit();
            }
        } catch (Exception e) {

        }
    }

}
