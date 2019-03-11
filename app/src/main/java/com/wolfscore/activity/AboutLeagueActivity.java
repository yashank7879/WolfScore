package com.wolfscore.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.wolfscore.aboutMatch.DataLineUpItem;
import com.wolfscore.aboutMatch.DataPlayer;
import com.wolfscore.aboutMatch.DataSubstiItem;
import com.wolfscore.adapter.AboutLeaguePagerAdapter;
import com.wolfscore.adapter.AboutMatchPagerAdpter;
import com.wolfscore.databinding.ActivityAboutLeagueBinding;
import com.wolfscore.databinding.ActivityAboutMatchBinding;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.matches.modal.Matches;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_MATCH_DETAILS_API;
import static com.wolfscore.utils.ApiCollection.MATCH_DETAIL;

/**
 * Created by mindiii on 27/2/19.
 */

public class AboutLeagueActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAboutLeagueBinding binding;
    public static AboutLeagueActivity aboutLeagueActivity;
    private ProgressDialog progressDialog;
    public Matches matches;
    Context mContext;
    public AboutLeaguePagerAdapter adapter;
    public AboutMatchResponce emp;
    ////////lineup
    public List<DataLineUpItem> lineUpList, benchList;
    public List<DataLineUpItem> slidelineList;//injured list
    public List<DataSubstiItem> substituteList;//injured list
    public String localFormation = "", visitorFormation = "", matchId, LocalTeam, VisitorTeam;
    public int LocalteamId, VisitorteamId;
    public DataPlayer localCoach, visitorCoach;
   public Country.League league;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_league);
        aboutLeagueActivity = this;
        mContext = this;
        league=(Country.League) getIntent().getSerializableExtra("League");
        initialization();
    }

    private void initialization() {
        adapter = new AboutLeaguePagerAdapter(getSupportFragmentManager(), this);

        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tvLeagueName.setText(league.getLeague_name());
        binding.viewPager.setCurrentItem(1);
        binding.ivBack.setOnClickListener(this);

    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }
}
