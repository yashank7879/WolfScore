package com.wolfscore.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfscore.R;
import com.wolfscore.databinding.FragmentTableBinding;

/**
 * Created by mindiii on 13/2/19.
 */

public class TableFragment extends Fragment {
    FragmentTableBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_table, container, false);

        return binding.getRoot();
    }
}

