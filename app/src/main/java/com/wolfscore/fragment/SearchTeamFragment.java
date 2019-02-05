package com.wolfscore.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.activity.FavoriteActivity;
import com.wolfscore.activity.SearchActivity;
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
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_POPULAR_TEAMS_API;
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

/**
 * Created by mindiii on 5/2/19.
 */

public class SearchTeamFragment extends Fragment  implements SearchTeamAdapter.FavrouitTeamSelect ,View.OnClickListener
{
    ActivitySearchBinding binding;
    private ProgressDialog progressDialog;
    private SearchTeamAdapter adapter;
    private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
    private int offset = 0;
    private String search = "";
    private Context mContext;
    private int limit = 40;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_search,container, false);
        // intializeView(rootView);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intializeView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private void intializeView() {
        teamList = new ArrayList<>();
        progressDialog = new ProgressDialog(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvSearchTeams.setLayoutManager(layoutManager);
        adapter = new SearchTeamAdapter(mContext, teamList, this);
        binding.rvSearchTeams.setAdapter(adapter);
        binding.tvCancelSearch.setOnClickListener(this);


        //******  Pagination """""""""""""""//
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                    if (page != 1) {
                        progressDialog.show();
                    }

                    offset = offset + 50; //load 5 items in recyclerview
                    // progressDialog.show();
                    getPopularTeam();

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
                offset = 0;
                adapter.notifyDataSetChanged();
                search = editable.toString();
                getPopularTeam();
            }
        });

        getPopularTeam();
        binding.rvSearchTeams.addOnScrollListener(scrollListener);
    }

    private void getPopularTeam() {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {

            AndroidNetworking.get(BASE_URL + GET_POPULAR_TEAMS_API)
                    .addQueryParameter("search_term", search)
                    .addQueryParameter("limit", "" + limit)
                    .addQueryParameter("offset", "" + offset)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
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
                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
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
            if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                star.setEnabled(false);
                bean.setIs_favorite("1");
                adapter.notifyDataSetChanged();
                favrouitApi(bean, value, star);
            }
        } else {
            if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                star.setEnabled(false);
                bean.setIs_favorite("0");
                adapter.notifyDataSetChanged();
                favrouitApi(bean, value,star);
            }
        }
    }

    //""""" Single favorite selct and un select """""""""//
    private void favrouitApi(LocalTeamResponce.DataBean.TeamListBean bean, String value, final ImageView star) {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {//http://dev.wolfscore.info/api_v1/users/single_favorite_unfavorite
            AndroidNetworking.post(BASE_URL + SINGLE_FAVORITE_UNFAVORITE_API)
                    .addBodyParameter("request_type", value)
                    .addBodyParameter("request_id", bean.getTeam_id())//team id,player id,league id
                    .addBodyParameter("type","team")// team || player ||league
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
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
                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();

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
                getActivity().onBackPressed();
                break;
        }
    }
}