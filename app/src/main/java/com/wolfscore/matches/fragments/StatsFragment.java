package com.wolfscore.matches.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.wolfscore.R;

/**
 * Created by mindiii on 19/2/19.
 */

public class StatsFragment  extends Fragment {
//    FragmentMatchStatsBinding binding;
  //  SeekBar seekBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_stats, null);
      //gi  initialise(rootView);

        return rootView;
    }

/*
    private void initialise(View rootView) {
        seekBar.setProgress(7);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    seekBar.setProgress(2);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
*/
}
