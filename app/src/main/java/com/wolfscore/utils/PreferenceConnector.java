package com.wolfscore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wolfscore.responce.CountryDto;

import java.util.ArrayList;
import java.util.List;

public class PreferenceConnector {
    public static final String PREF_NAME = "WOLFSCORE";
    public static final int MODE = Context.MODE_PRIVATE;
    //----------session-----//

    public static final String REM_LOGIN_INFO = "LoginInfo";

    //----UserInfo


    public static final String Email = "email";
    public static final String AUTH_TOKEN = "AuthToken1";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String MY_USER_ID = "MyUserId";
    public static final String PROFILE_IMG = "ProfileImg";
    public static final String Name = "name";
    public static final String IS_LOGIN = "Login";
    public static final String PREFERANCE_COUNTRY_LIST="preferance country list";




    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeBoolean(Context context, String key, Boolean value) {
        getEditor(context).putBoolean(key, value).commit();

    }

    public static Boolean readBoolean(Context context, String key,
                                      Boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void clear(Context context) {

        getEditor(context).clear().commit();
    }

    public static void saveCountryList(Context context, List<CountryDto> categoryDTOS) {
        try {
            Gson gson = new Gson();
            String category = gson.toJson(categoryDTOS);

            getEditor(context).putString(PREFERANCE_COUNTRY_LIST, category).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<CountryDto> getCountryList(Context context) {
      //  SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);

        ArrayList<CountryDto> categoryDTOS = new ArrayList<>();
        Gson gson = new Gson();
       // String vjson = prefs.getString(PREFERANCE_COUNTRY_LIST, "");
        String vjson= getPreferences(context).getString(PREFERANCE_COUNTRY_LIST, "");
        categoryDTOS = gson.fromJson(vjson, new TypeToken<List<CountryDto>>() {
        }.getType());

        return categoryDTOS;
    }


}
