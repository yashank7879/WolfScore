package com.wolfscore.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.activity.SetupAppActivity;
import com.wolfscore.databinding.ActivitySetupWolfScoreScreenOneBinding;
import com.wolfscore.responce.CountryDto;
import com.wolfscore.responce.GuestUserResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_COUNTRY_LIST;
import static com.wolfscore.utils.ApiCollection.GUEST_SIGNUP;
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

public class SetupWolfScoreScreenOne extends AppCompatActivity implements View.OnClickListener {
    ActivitySetupWolfScoreScreenOneBinding binding;
    private Typeface robotoMedium;
    private Typeface robotoLight;
    private String androidDeviceId;
    private ProgressDialog progressDialog;
    HashMap<String, String> countryLookupMap;
    RelativeLayout main_view;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setup_wolf_score_screen_one);

        progressDialog = new ProgressDialog(this);

        binding.rlNewToWs.setOnClickListener(this);
        binding.rlLogMe.setOnClickListener(this);
        main_view=findViewById(R.id.main_view);

        robotoMedium = ResourcesCompat.getFont(this, R.font.roboto_medium);
        robotoLight = ResourcesCompat.getFont(this, R.font.roboto_light);


        Log.e("auth token", PreferenceConnector.readString(this,PreferenceConnector.AUTH_TOKEN,""));
        //"""""""""""""" get android device id :- it will change after hard reset """"""""""//
        androidDeviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
     /*   String locale = getResources().getConfiguration().locale.getCountry();*/
        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
      //  Toast.makeText(this, "" + countryCodeValue, Toast.LENGTH_SHORT).show();

    //  String countyName =   countryLookupMap.get(countryCodeValue);

    }


    //896e9c23c3e93b69


    //"""""""""" Guest Signup """""""""""//
    private void guestSignUpApi(String androidDeviceId) {
        if (Constant.isNetworkAvailable(this, binding.mainView)) {
            progressDialog.show();
            AndroidNetworking.post(BASE_URL + GUEST_SIGNUP)
                    .addBodyParameter("device_token", androidDeviceId)
                    .addHeaders("Api-Key", APIKEY)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressDialog.dismiss();
                                String status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                    GuestUserResponce userResponce = new Gson().fromJson(String.valueOf(response),GuestUserResponce.class);
                                    PreferenceConnector.writeString(SetupWolfScoreScreenOne.this, PreferenceConnector.AUTH_TOKEN, userResponce.getData().getAuth_token());
                                    PreferenceConnector.writeString(SetupWolfScoreScreenOne.this, PreferenceConnector.MY_USER_ID, userResponce.getData().getUserId());
                                    PreferenceConnector.writeString(SetupWolfScoreScreenOne.this, PreferenceConnector.PROFILE_IMG, userResponce.getData().getProfile_photo());
                                    PreferenceConnector.writeString(SetupWolfScoreScreenOne.this, PreferenceConnector.Name, userResponce.getData().getName());
                                    PreferenceConnector.writeString(SetupWolfScoreScreenOne.this, PreferenceConnector.Email, userResponce.getData().getEmail());
                                    PreferenceConnector.writeString(SetupWolfScoreScreenOne.this, PreferenceConnector.DEVICE_TOKEN, userResponce.getData().getDevice_token());

                                    getCountryData();
                                    Intent intent = new Intent(SetupWolfScoreScreenOne.this, SetupAppActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);


                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_log_me:
                binding.tvNewToWs.setTypeface(robotoLight);
                binding.rlNewToWs.setBackground(null);
                binding.tvLogMe.setTypeface(robotoMedium);
                binding.rlLogMe.setBackground(ContextCompat.getDrawable(this, R.color.active_text));
                break;
            case R.id.rl_new_to_ws:
                guestSignUpApi(androidDeviceId);
                binding.tvLogMe.setTypeface(robotoLight);
                binding.rlLogMe.setBackground(null);
                binding.tvNewToWs.setTypeface(robotoMedium);
                binding.rlNewToWs.setBackground(ContextCompat.getDrawable(this, R.color.active_text));
                break;

        }
    }

    private void getCountryData() {
        if (Constant.isNetworkAvailable(SetupWolfScoreScreenOne.this, main_view)) {//http://dev.wolfscore.info/api_v1/users/single_favorite_unfavorite

            Log.d("auttoken", "getMatchData: auttoken...."+PreferenceConnector.readString(SetupWolfScoreScreenOne.this, PreferenceConnector.AUTH_TOKEN,""));

            AndroidNetworking.get(BASE_URL + GET_COUNTRY_LIST)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(SetupWolfScoreScreenOne.this, PreferenceConnector.AUTH_TOKEN, ""))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //   progressDialog.dismiss();
                            String status = null;
                            try {
                                status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                    JSONObject data_obj= response.getJSONObject("data");
                                   JSONArray country_list_array= data_obj.getJSONArray("country_list");

                                    ArrayList<CountryDto> countryDtos=new ArrayList<>();
                                   for (int i=0;i<country_list_array.length();i++)
                                   {
                                       CountryDto countryDto=new CountryDto();
                                       JSONObject object=country_list_array.getJSONObject(i);
                                       if (object.has("country_id"))
                                           countryDto.setCountry_id(!object.isNull("country_id") ? object.getString("country_id") : "");
                                       if (object.has("country_name"))
                                           countryDto.setCountry_name(!object.isNull("country_name") ? object.getString("country_name") : "");
                                       if (object.has("country_flag"))
                                           countryDto.setCountry_flag(!object.isNull("country_flag") ? object.getString("country_flag") : "");
                                       countryDtos.add(countryDto);
                                   }
                                    PreferenceConnector.saveCountryList(SetupWolfScoreScreenOne.this,countryDtos);
                                    ArrayList<CountryDto> countrylist= PreferenceConnector.getCountryList(SetupWolfScoreScreenOne.this);
                                    Log.d("countrylist", "onResponse: countrylist"+countrylist);

                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                               anError.printStackTrace();
                        }
                    });
        }
    }

}
