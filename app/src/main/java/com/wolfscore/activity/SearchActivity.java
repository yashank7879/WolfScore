package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.SearchTeamAdapter;
import com.wolfscore.databinding.ActivitySearchBinding;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_POPULAR_TEAMS_API;
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

public class SearchActivity extends AppCompatActivity implements SearchTeamAdapter.FavrouitTeamSelect ,View.OnClickListener{
    ActivitySearchBinding binding;
    private ProgressDialog progressDialog;
    private SearchTeamAdapter adapter;
    private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
    private int offset = 0;
    private String search = "";
    private int limit = 40;
    List<LocalTeamResponce.DataBean.TeamListBean> noRepeat=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        intializeView();
    }

    private void intializeView() {
        teamList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvSearchTeams.setLayoutManager(layoutManager);
        adapter = new SearchTeamAdapter(this, noRepeat, this);
        binding.rvSearchTeams.setAdapter(adapter);
        binding.tvCancelSearch.setOnClickListener(this);


        //******  Pagination """""""""""""""//
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(SearchActivity.this, binding.mainLayout)) {
                  if (search.isEmpty()) {
                      if (page != 1) {
                          progressDialog.show();

                      }
                      offset = offset + 50; //load 5 items in recyclerview
                      getPopularTeam();
                  }


                }
            }
        };

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                teamList.clear();
                noRepeat.clear();
                offset = 0;
                search = editable.toString();
                getPopularTeam();
                adapter.notifyDataSetChanged();

            }
        });

        getPopularTeam();
        binding.rvSearchTeams.addOnScrollListener(scrollListener);
    }

    private void getPopularTeam() {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {

            AndroidNetworking.get(BASE_URL + GET_POPULAR_TEAMS_API)
                    .addQueryParameter("search_term", search)
                    .addQueryParameter("limit", "" + limit)
                    .addQueryParameter("offset", "" + offset)
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
                                    LocalTeamResponce teamResponce = new Gson().fromJson(String.valueOf(response), LocalTeamResponce.class);
                                    teamList.addAll(teamResponce.getData().getTeam_list());
                                   // unique(teamList);


                                   // List<LocalTeamResponce.DataBean.TeamListBean> allEvents = // fill with your events.
                                    noRepeat = new ArrayList<LocalTeamResponce.DataBean.TeamListBean>();

                                    for (LocalTeamResponce.DataBean.TeamListBean event : teamList) {
                                        boolean isFound = false;
                                        // check if the event name exists in noRepeat
                                        for (LocalTeamResponce.DataBean.TeamListBean e : noRepeat) {
                                            if (e.getTeam_id().equals(event.getTeam_id()) || (e.equals(event))) {
                                                isFound = true;
                                                break;
                                            }
                                        }
                                        if (!isFound) noRepeat.add(event);
                                    }



                                    adapter.notifyDataSetChanged();
                                  /*  if (teamResponce.getData().getTotal_records().equals("0")) {
                                       *//* teamList.clear();
                                        adapter.notifyDataSetChanged();*//*
                                        binding.tvNoResult.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.tvNoResult.setVisibility(View.GONE);
                                    }*/

                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(SearchActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
    public void favrouitSelectUnselect(LocalTeamResponce.DataBean.TeamListBean bean, String value, ImageView star) {
        if (value.equals("1")) {
            if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
                star.setEnabled(false);
                bean.setIs_favorite("1");
                adapter.notifyDataSetChanged();
                favrouitApi(bean, value, star);
            }
        } else {
            if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
                star.setEnabled(false);
                bean.setIs_favorite("0");
                adapter.notifyDataSetChanged();
                favrouitApi(bean, value,star);
            }
        }
    }

    //""""" Single favorite selct and un select """""""""//
    private void favrouitApi(LocalTeamResponce.DataBean.TeamListBean bean, String value, final ImageView star) {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {//http://dev.wolfscore.info/api_v1/users/single_favorite_unfavorite
            AndroidNetworking.post(BASE_URL + SINGLE_FAVORITE_UNFAVORITE_API)
                    .addBodyParameter("request_type", value)
                    .addBodyParameter("request_id", bean.getTeam_id())//team id,player id,league id
                    .addBodyParameter("type","team")// team || player ||league
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            String status = null;
                            try {
                                status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                    star.setEnabled(true);
                                } else {
                                    Toast.makeText(SearchActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel_search:
                onBackPressed();
                break;
        }
    }
}


