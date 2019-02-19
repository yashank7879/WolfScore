package com.wolfscore.matches.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikesu.horizontalexpcalendar.HorizontalExpCalendar;
import com.mikesu.horizontalexpcalendar.common.Config;
import com.wolfscore.R;

import org.joda.time.DateTime;

/**
 * Created by mindiii on 5/2/19.
 */

public class CalenderFragment extends Fragment {
  //  private static final String TAG = MainActivity.class.getName();
    private HorizontalExpCalendar horizontalExpCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calender_view, null);
        horizontalExpCalendar = (HorizontalExpCalendar)rootView.findViewById(R.id.calendar);
        horizontalExpCalendar.setHorizontalExpCalListener(new HorizontalExpCalendar.HorizontalExpCalListener() {
            @Override
            public void onCalendarScroll(DateTime dateTime) {
                Log.i("Calender", "onCalendarScroll: " + dateTime.toString());
            }

            @Override
            public void onDateSelected(DateTime dateTime) {
                Log.i("Calender", "onDateSelected: " + dateTime.toString());
            }

            @Override
            public void onChangeViewPager(Config.ViewPagerType viewPagerType) {
                Log.i("Calender", "onChangeViewPager: " + viewPagerType.name());
            }
        });
          return rootView;
    }


}

