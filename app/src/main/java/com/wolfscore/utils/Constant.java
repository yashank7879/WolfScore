package com.wolfscore.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by mindiii on 1/22/19.
 */

public class Constant {
    public static int CurrentPage=0;
    public static boolean isRunApi;
    public static Calendar calendar = Calendar.getInstance();
    //*****************check for network connection******************//
    public static boolean isNetworkAvailable(Context context, View coordinatorLayout) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        // Check for network connections
        if (activeNetworkInfo != null) {

            // if connected with internet
            return true;

        } else if (activeNetworkInfo == null) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_SHORT)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*not used*/
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
            return false;
        }
        return false;
    }

    //**************hide keyboard********************//
    public static void hideSoftKeyBoard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getFormatedDateTime(String dateStr, String strReadFormat, String strWriteFormat) {

        String formattedDate = dateStr;
        DateFormat readFormat = new SimpleDateFormat(strReadFormat, Locale.getDefault());
        DateFormat writeFormat = new SimpleDateFormat(strWriteFormat, Locale.getDefault());

        Date date = null;

        try {
            date = readFormat.parse(dateStr);
        } catch (ParseException e) {
        }

        if (date != null) {
            formattedDate = writeFormat.format(date);
        }

        return formattedDate;
    }

  public static String  getDayFromDate(Date date)
    {
        calendar.setTime(date);

        String[] days = new String[] { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

        String day = days[calendar.get(Calendar.DAY_OF_WEEK)-1];
        return day;
    }


    public static String getServerFormattedDate(String dateString, String dateFormatFrom, String dateFormatTo, Context context) {

        //{"date":"2017-03-04 05:18:41","time_zone":"UTC"}

        //  String SERVER_TIMEZONE = SharedPref.getSharedPrefData(context, SharedPref.PREFRENCE_SERVER_TIME);
     //   String SERVER_TIMEZONE="America/Los_Angeles";
        String SERVER_TIMEZONE="UTC";
        SimpleDateFormat dateFormatServer = new SimpleDateFormat(
                dateFormatFrom);
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat(
                dateFormatTo);

        TimeZone timeZone = TimeZone.getDefault();
        TimeZone timeZoneServer = TimeZone.getTimeZone(SERVER_TIMEZONE);
        Log.d("Time Zone:", "Current timezone: " + timeZone.getRawOffset());
        Log.d("Time Zone:", "Server timezone: " + timeZoneServer.getRawOffset());

        Date date = null;
        try {
            dateFormatServer.setTimeZone(timeZoneServer);
            date = dateFormatServer.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String convertedDateString = dateFormatLocal.format(date);
        Log.d("Time Zone:", "convertedDateString=" + convertedDateString);
        return convertedDateString;
    }

}
