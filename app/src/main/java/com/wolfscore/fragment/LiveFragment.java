package com.wolfscore.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wolfscore.R;
import com.wolfscore.activity.FavoriteActivity;

public class LiveFragment extends Fragment {

    Button favorite_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_live_score, null);
        favorite_btn=rootView.findViewById(R.id.favorite_btn);
        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FavoriteActivity.class));
            }
        });
       // initialise(rootView);
        return rootView;
    }
}
