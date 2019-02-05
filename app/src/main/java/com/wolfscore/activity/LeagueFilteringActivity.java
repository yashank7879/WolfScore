package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.FilterTournamentAdapter;
import com.wolfscore.adapter.LocalTeamAdapter;
import com.wolfscore.databinding.ActivityLeagueFilteringBinding;
import com.wolfscore.responce.GetLeagueResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_LEAGUE_LIST_API;

public class LeagueFilteringActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLeagueFilteringBinding binding;
    private ProgressDialog progressDialog;
    private FilterTournamentAdapter adapter;
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_league_filtering);

        intializeView();

    }

    private void intializeView() {
        leagueList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvFilter.setLayoutManager(layoutManager);
        adapter = new FilterTournamentAdapter(this, leagueList);
        binding.rvFilter.setAdapter(adapter);


        binding.tvSorting.setOnClickListener(this);
        binding.tvDone.setOnClickListener(this);
        binding.tvDeselect.setOnClickListener(this);
        binding.tvSelect.setOnClickListener(this);
        getLeagueList();
    }

    private void getLeagueList() {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + GET_LEAGUE_LIST_API)
                    .addHeaders("Api-Key", APIKEY)
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
                                    GetLeagueResponce leagueResponce = new Gson().fromJson(String.valueOf(response), GetLeagueResponce.class);
                                    leagueList.addAll(leagueResponce.getData().getLeague_list());
                                    adapter.notifyDataSetChanged();

                                    if (leagueResponce.getData().getTotal_records().equals("0")) {
                                       /* teamList.clear();
                                        adapter.notifyDataSetChanged();*/
                                        // binding.tvNoResult.setVisibility(View.VISIBLE);
                                    } else {
                                        //  binding.tvNoResult.setVisibility(View.GONE);
                                    }


                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(LeagueFilteringActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sorting:

                break;
            case R.id.tv_done:
                onBackPressed();
                break;
            case R.id.tv_select:

                break;
            case R.id.tv_deselect:

                break;
        }
    }
}
