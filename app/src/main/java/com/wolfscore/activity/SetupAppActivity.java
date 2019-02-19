package com.wolfscore.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wolfscore.listener.GetTeamListener;
import com.wolfscore.R;
import com.wolfscore.adapter.SetupPagerAdpter;
import com.wolfscore.databinding.ActivitySetupAppBinding;
import com.wolfscore.listener.NextOnClick;
import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.responce.NotificationModel;
import com.wolfscore.responce.TopPlayerResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.wolfscore.utils.ApiCollection.ADD_FAVOURITES;
import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.Constant.CurrentPage;

public class SetupAppActivity extends AppCompatActivity implements View.OnClickListener, GetTeamListener, NextOnClick {
    ActivitySetupAppBinding binding;
    private int previosCount = 0;
    private int nextCount = 1;
    private Locale locale;
    private Handler handler;
    private ProgressDialog progressDialog;
    List<NotificationModel> dataBeansList = new ArrayList<>();
    private String teamType = "team";
    NotificationModel model = new NotificationModel();
    private Map<String,LocalTeamResponce.DataBean.TeamListBean> tempLocalTeamList;
    private Map<String,LocalTeamResponce.DataBean.TeamListBean> tempPopularTeamList;
    private Map<String,TopPlayerResponce.DataBean.PlayerListBean> tempPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setup_app);
        initializeView();
        Log.e("Auth token: ", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""));
        @SuppressLint("HardwareIds") String AndroidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    //896e9c23c3e93b69
    //896e9c23c3e93b69
    private void initializeView() {

        progressDialog = new ProgressDialog(this);
        tempPlayerList = new HashMap<>();
        tempLocalTeamList = new HashMap<>();
        tempPopularTeamList = new HashMap<>();
        binding.tvNext.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.tvSkip.setOnClickListener(this);
        SetupPagerAdpter adapter = new SetupPagerAdpter(getSupportFragmentManager(), this);
        binding.viewpager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        binding.viewpager.setPagingEnabled(false);

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    binding.tabLayout.setVisibility(View.GONE);
                } else {
                    binding.tabLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        LinearLayout tabStrip = ((LinearLayout) binding.tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                //Log.e( "current page ", binding.viewpager.getCurrentItem()+"");
                if (teamType.equals("Localteam") && binding.viewpager.getCurrentItem() == 0) {
                    teamType = "team";
                    getLocalSelectedTeam();
                    selectFavrouitApi(getLocalSelectedTeam());
                } else if (teamType.equals("Popularteam")  && binding.viewpager.getCurrentItem() == 1) {
                    teamType = "team";

                    selectFavrouitApi(getPopularSelectedTeam()  );
                } else if (teamType.equals("player") && binding.viewpager.getCurrentItem() == 2) {
                    getSelectedPlayer();
                    selectFavrouitApi(getSelectedPlayer());
                } else if (teamType.equals("Notification") && binding.viewpager.getCurrentItem() == 3) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("goal", dataBeansList.get(0).goal);
                        jsonObject.put("red_card", dataBeansList.get(0).red_card);
                        jsonObject.put("yellow_card", dataBeansList.get(0).yellow_card);
                        jsonObject.put("match_reminder", dataBeansList.get(0).match_reminder);
                        jsonObject.put("match_start", dataBeansList.get(0).match_start);
                        jsonObject.put("half_time", dataBeansList.get(0).half_time);
                        jsonObject.put("full_time_result", dataBeansList.get(0).full_time_result);
                        Log.e("onClick json: ", jsonObject.toString());
                        notificationApi(jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //if ( binding.viewpager.getCurrentItem() == )

                    if (binding.viewpager.getCurrentItem() == 3){
                        Intent intent = new Intent(SetupAppActivity.this, HomeActivity.class);
                        startActivity(intent);
                       // overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);

                        finish();
                    }
                    binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);

                    CurrentPage = binding.viewpager.getCurrentItem();



                   /* binding.viewpager.setCurrentItem(nextCount);
                    if (nextCount < 3) {
                        previosCount = nextCount - 1;
                        nextCount++;
                    } else {
                        previosCount = nextCount - 1;
                    }*/
                }


                break;
            case R.id.iv_back:

                if (binding.viewpager.getCurrentItem() == 0) {
                    onBackPressed();
                }

                binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() - 1);

           /* if (binding.viewpager.getCurrentItem() == 0){
                onBackPressed();
            }*/

            /*    if (previosCount == 0 && nextCount == 1) {
                    onBackPressed();
                }
                if (nextCount <= 3) {
                    previosCount = nextCount - 1;
                    if (nextCount != 1)
                        nextCount = nextCount - 1;
                }
                binding.viewpager.setCurrentItem(previosCount);

*/
                break;
            default:
        }
    }

    private StringBuffer getPopularSelectedTeam() {
      //  String joined = TextUtils.join(", ", tempPopularTeamList.toArray());
      //  Log.e("getSelectedTeam: ", joined);
        StringBuffer stringBuffer = new StringBuffer();
        for (LocalTeamResponce.DataBean.TeamListBean teamListBean : tempPopularTeamList.values()) {
            if (teamListBean.getIs_favorite().equals("1")) {
                stringBuffer.append(teamListBean.getTeam_id()).append(",");
            }
        }
        if (!stringBuffer.toString().isEmpty()) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            // Log.e("favroit team", stringBuffer.toString());
        }
        return stringBuffer;
    }

    //""""""" Notification type """"""""""""//
    private void notificationApi(String notify) {
        progressDialog.show();
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            AndroidNetworking.post(BASE_URL + "users/update_notification_setting")
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .addBodyParameter("notification_key", notify)
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
                                    tempPlayerList.clear();
                                    PreferenceConnector.writeBoolean(SetupAppActivity.this, PreferenceConnector.IS_LOGIN, true);

                                    finishAffinity();
                                    Intent intent = new Intent(SetupAppActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                 //   overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                                    finish();


                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(SetupAppActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    private StringBuffer getSelectedPlayer() {
        StringBuffer stringBuffer = new StringBuffer();
        for (TopPlayerResponce.DataBean.PlayerListBean teamListBean : tempPlayerList.values()) {
            if (teamListBean.getIs_favorite().equals("1")) {
                stringBuffer.append(teamListBean.getPlayer_id()).append(",");
            }
        }
        if (!stringBuffer.toString().isEmpty()) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            Log.e("favroit player", stringBuffer.toString());
        }
        return stringBuffer;
    }

    private StringBuffer getLocalSelectedTeam() {

        // String joined = TextUtils.join(", ", tempLocalTeamList.toArray());
        ///  Log.e("getSelectedTeam: ", joined);
        StringBuffer stringBuffer = new StringBuffer();
        for (LocalTeamResponce.DataBean.TeamListBean teamListBean : tempLocalTeamList.values()) {
            if (teamListBean.getIs_favorite().equals("1")) {
                stringBuffer.append(teamListBean.getTeam_id()).append(",");
            }
        }
        if (!stringBuffer.toString().isEmpty()) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            // Log.e("favroit team", stringBuffer.toString());
        }
        return stringBuffer;
    }

    //""""""" favroit local team , popular team  """""""""""//
    private void selectFavrouitApi(StringBuffer popularSelectedTeam) {
        progressDialog.show();
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            AndroidNetworking.post(BASE_URL + ADD_FAVOURITES)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .addBodyParameter("favourite_type", teamType)
                    .addBodyParameter("favourite_ids", popularSelectedTeam.toString())
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

                                    tempLocalTeamList.clear();
                                    tempPopularTeamList.clear();
                                    tempPlayerList.clear();

                                    binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);
                                    CurrentPage = binding.viewpager.getCurrentItem();

                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(SetupAppActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.dismiss();
                        }
                    });
        }
    }



    //""""""  show selected player name or team name  """""""""""//
    @Override
    public void getTeamNameListener(final String name) {
        binding.tvFavroitTeam.setVisibility(View.VISIBLE);
        binding.tvFavroitTeam.setText(MessageFormat.format("{0} {1}", name, getString(R.string.added_as_favorite)));
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.tvFavroitTeam.setVisibility(View.GONE);
            }
        }, 3000);

        // Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void nextLocalOnclickListener(LocalTeamResponce.DataBean.TeamListBean bean, String team, boolean value) {
        teamType = team;
        if (bean != null) {

            if (bean.getIs_favorite().equals("1")) {
                tempLocalTeamList.put(bean.getTeam_id(),bean);

            } else {
                tempLocalTeamList.remove(bean);
            }
        }
        // Toast.makeText(this, "" + tempTeamList.size(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void nextPopularOnclickListener(LocalTeamResponce.DataBean.TeamListBean bean, String team, boolean value) {
        teamType = team;
        if (bean != null) {
            if (bean.getIs_favorite().equals("1")) {
                tempPopularTeamList.put(bean.getTeam_id(),bean);
            } else {
                tempPopularTeamList.remove(bean);
            }
        }
    }

    @Override
    public void nextPlayerOnclickListener(TopPlayerResponce.DataBean.PlayerListBean bean, String team, boolean value) {
        teamType = team;
        if (bean != null) {
            if (bean.getIs_favorite().equals("1")) {
                tempPlayerList.put(bean.getPlayer_id(),bean);
            } else {
                tempPlayerList.remove(bean);
            }
        }
    }

    @Override
    public void nextNotification(String next, boolean isChecked) {
        String value = isChecked ? "1" : "0";
        teamType = "Notification";
        if (next.equals("goal")) {
            model.goal = value;
            dataBeansList.add(0, model);

        } else if (next.equals("red_card")) {
            model.red_card = value;
            dataBeansList.add(0, model);
        } else if (next.equals("yellow_card")) {
            model.yellow_card = value;
            dataBeansList.add(0, model);
        } else if (next.equals("match_start")) {
            model.match_start = value;
            dataBeansList.add(0, model);
        } else if (next.equals("half_time")) {
            model.half_time = value;
            dataBeansList.add(0, model);
        } else if (next.equals("All_checked")) {
            model.goal = value;
            model.red_card = value;
            model.yellow_card = value;
            model.match_start = value;
            model.half_time = value;
            dataBeansList.add(0, model);
        }

    }
}
