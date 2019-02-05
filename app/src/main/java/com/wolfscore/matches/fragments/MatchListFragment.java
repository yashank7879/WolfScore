package com.wolfscore.matches.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wolfscore.R;
import com.wolfscore.matches.adapter.matchListPagerAdapter;
public class MatchListFragment extends Fragment {
    TabLayout tab_layout;
    ViewPager viewpager;
    matchListPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches_list, null);
        initialise(rootView);
        return rootView;
    }
    private void initialise(View rootView)
    {

        tab_layout=rootView.findViewById(R.id.tab_layout);
        viewpager=rootView.findViewById(R.id.viewpager);
        adapter = new matchListPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        viewpager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(1);

    }
}
