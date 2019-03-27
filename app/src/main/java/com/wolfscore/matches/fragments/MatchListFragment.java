package com.wolfscore.matches.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wolfscore.R;
import com.wolfscore.activity.HomeActivity;
import com.wolfscore.activity.LeagueFilteringActivity;
import com.wolfscore.activity.NewFilteredActivity;
import com.wolfscore.matches.adapter.matchListPagerAdapter;
import com.wolfscore.matches.modal.FilteredEvent;
import com.wolfscore.matches.modal.Matches;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MatchListFragment extends Fragment {
    TabLayout tab_layout;
    ViewPager viewpager;
    String day="";
    matchListPagerAdapter adapter;
    boolean is_filtered=false;
    FilteredEvent event;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches_list, null);

        initialise(rootView);
        return rootView;
    }
    private void initialise(View rootView) {
        //register EventBus (this activity is a subscriber)
        Bundle bundle = this.getArguments();
        day = bundle.getString("day");
        event=(FilteredEvent)bundle.getSerializable("event");

        tab_layout=rootView.findViewById(R.id.tab_layout);
        viewpager=rootView.findViewById(R.id.viewpager);
        adapter = new matchListPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        viewpager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewpager);
        if (day!=null&&!day.isEmpty()&&day.equalsIgnoreCase("0"))
        viewpager.setCurrentItem(0);
        else if (day!=null&&!day.isEmpty()&&day.equalsIgnoreCase("1"))
            viewpager.setCurrentItem(1);
       else if (day!=null&&!day.isEmpty()&&day.equalsIgnoreCase("2"))
            viewpager.setCurrentItem(2);
       else if (event.getLeague_id()!=null&&!event.getLeague_id().isEmpty()){
            viewpager.setCurrentItem( HomeActivity.homeActivity.current_item);
        }
       else
            viewpager.setCurrentItem(1);
        //viewpager.setOffscreenPageLimit(2);

        HomeActivity.homeActivity.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // is_filtered=true;
                HomeActivity.homeActivity.current_item=viewpager.getCurrentItem();
              //  startActivity(new Intent(getActivity(), LeagueFilteringActivity.class));
                startActivity(new Intent(getActivity(), NewFilteredActivity.class));
            }
        });

    }

}
