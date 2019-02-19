package com.wolfscore.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfscore.R;
import com.wolfscore.aboutMatch.DataItem;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.adapter.LocalTeamAdapter;
import com.wolfscore.adapter.MatchFactAdapter;
import com.wolfscore.databinding.FragmentMatchFactsBinding;

import java.util.ArrayList;


public class MatchFactsFragment extends Fragment {
    FragmentMatchFactsBinding binding;
    MatchFactAdapter adapter;
    ArrayList<DataItem> dataItems=new ArrayList<>();
    public MatchFactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_facts, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvLocalTeam.setLayoutManager(layoutManager);
        binding.tournament.setText(""+AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().getLeague().leagueData.name);
        if (AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().getVenue()!=null)
            binding.stadium.setText(""+AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().getVenue().venueData.name);
        if (AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().getReferee()!=null)
            binding.refrees.setText(""+AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().getReferee().referyData.fullname);
        if (AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().time!=null)
            binding.matchDate.setText(""+AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().time.startingAt.dateTime);
        if (AboutMatchActivity.aboutMatchActivity.emp.getData().data.attendance!=0)
            binding.attendance.setText(""+AboutMatchActivity.aboutMatchActivity.emp.getData().data.attendance);

        dataItems.clear();
            dataItems.addAll(AboutMatchActivity.aboutMatchActivity.emp.getData().getData1().getEvents().data);
            adapter = new MatchFactAdapter(getActivity(),dataItems);
            binding.rvLocalTeam.setAdapter(adapter);

        return binding.getRoot();
    }


}
