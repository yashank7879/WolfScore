package com.wolfscore.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.aboutMatch.AboutMatchResponce;

import com.wolfscore.aboutMatch.DataPlayer;
import com.wolfscore.adapter.AboutMatchPagerAdpter;
import com.wolfscore.databinding.ActivityAboutMatchBinding;

import com.wolfscore.matches.modal.Matches;

import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;
import com.wolfscore.aboutMatch.DataLineUpItem;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_MATCH_DETAILS_API;
import static com.wolfscore.utils.ApiCollection.MATCH_DETAIL;

public class AboutMatchActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAboutMatchBinding binding;
    public static AboutMatchActivity aboutMatchActivity;
    private ProgressDialog progressDialog;
    public Matches matches;
    Context mContext;
    public AboutMatchPagerAdpter adapter;
    public AboutMatchResponce emp;
    ////////lineup
    public List<DataLineUpItem> lineUpList, benchList;
    public String localFormation = "", visitorFormation = "", matchId, LocalTeam, VisitorTeam;
    public int LocalteamId, VisitorteamId;
    public DataPlayer localCoach,visitorCoach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_match);
        aboutMatchActivity = this;
        mContext = this;
        initialization();
    }

    private void initialization() {
        matches = (Matches) getIntent().getSerializableExtra("obj");
        progressDialog = new ProgressDialog(mContext);
        adapter = new AboutMatchPagerAdpter(getSupportFragmentManager(), this);
        lineUpList = new ArrayList<>();
        benchList = new ArrayList<>();

        binding.score.setText(matches.getScore().getLocalteam_score() + " - " + matches.getScore().getVisitorteam_score());
        binding.localTeamName.setText(matches.getLocalTeam().getName());
        binding.visitorTeamName.setText(matches.getVisitorTeam().getName());
        binding.matchType.setText(matches.getTime().getStatus());
        Picasso.with(mContext).load(matches.getLocalTeam().getLogo_path()).error(R.drawable.australia).placeholder(R.drawable.app_icon).fit().into(binding.localFlagIcon);
        Picasso.with(mContext).load(matches.getVisitorTeam().getLogo_path()).error(R.drawable.logo).placeholder(R.drawable.app_icon).fit().into(binding.visitorFlagIcon);
        matchId = "" + matches.getMatchHeader().getMatch_id();
        //getIntent().getStringExtra("MatchId");
        LocalTeam = matches.getLocalTeam().getName();
        //getIntent().getStringExtra("LocalTeam");
        VisitorTeam = matches.getVisitorTeam().getName();
        LocalteamId = matches.getLocalTeam().getId();
        VisitorteamId = matches.getVisitorTeam().getId();
        binding.ivBack.setOnClickListener(this);
        getMatchDetail();
        getDataApi();
    }

    private void getMatchDetail() {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + MATCH_DETAIL)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("match_id", "" + matches.getMatchHeader().getMatch_id())
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

                                    ObjectMapper objectMapper = new ObjectMapper();
                                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                                    objectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

                                    emp = objectMapper.readValue(String.valueOf(response), AboutMatchResponce.class);
                                    binding.viewPager.setAdapter(adapter);
                                    binding.tabLayout.setupWithViewPager(binding.viewPager);

                                    binding.viewPager.setCurrentItem(1);

                                    //  Log.d("match detail", "onResponse: match detail.."+ emp.getData().getData1().getEvents().data.get(1).fixtureId);
                                    //  Toast.makeText(mContext, "" + emp.getData().getData1().getPitch(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            } catch (JsonMappingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    private void getDataApi() {
        progressDialog.show();
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            AndroidNetworking.get(BASE_URL + GET_MATCH_DETAILS_API)
                    .addHeaders("Api-Key", APIKEY)
                   // .addQueryParameter("match_id", matchId)
                    .addQueryParameter("match_id", "10420287")
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
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

                                    ObjectMapper objectMapper = new ObjectMapper();
                                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                                    objectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

                                    AboutMatchResponce emp = objectMapper.readValue(String.valueOf(response), AboutMatchResponce.class);

                                    lineUpList.addAll(emp.getData().getData1().getLineup().getData());

                                    benchList.addAll(emp.getData().getData1().bench.dataBench);

                                    localFormation = emp.data.data.getFormations().getLocalteam_formation();
                                    visitorFormation = emp.data.data.getFormations().getVisitorteam_formation();
                                    localCoach = emp.data.data.localCoach.dataPlayer;
                                    visitorCoach = emp.data.data.visitorCoach.dataPlayer;

                                    Log.e("onResponse: ", benchList.size() + "");


                                } else {
                                    Toast.makeText(AboutMatchActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            } catch (JsonMappingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }
}
