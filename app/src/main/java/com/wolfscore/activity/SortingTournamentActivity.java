package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.model.Progress;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.FilterTournamentAdapter;

import com.wolfscore.adapter.SimpleItemTouchHelperCallback;
import com.wolfscore.adapter.SortingTournamentAdapter;
import com.wolfscore.databinding.ActivitySortingTournamentBinding;
import com.wolfscore.responce.GetLeagueResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_LEAGUE_LIST_API;
import static com.wolfscore.utils.ApiCollection.LEAGUE_FILTER_SEQUENCE_LIST_API;
import static com.wolfscore.utils.ApiCollection.SET_FILTER_LEAGUES_API;

public class SortingTournamentActivity extends AppCompatActivity implements View.OnClickListener, SortingTournamentAdapter.SortingOnItemClick {
    ActivitySortingTournamentBinding binding;
    private ProgressDialog progressDialog;
    private SortingTournamentAdapter adapter;
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;
    private List<GetLeagueResponce.DataBean.LeagueListBean> resetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sorting_tournament);

        intializeView();
    }

    private void intializeView() {
        leagueList = new ArrayList<>();
        resetList = new ArrayList<>();

        binding.tvMoveBottom.setOnClickListener(this);
        binding.tvMoveTop.setOnClickListener(this);
        binding.tvReset.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvSorting.setLayoutManager(layoutManager);
        adapter = new SortingTournamentAdapter(this, leagueList, this);
        binding.rvSorting.setAdapter(adapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(binding.rvSorting);
        getLeagueList();
    }

    private void getLeagueList() {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + LEAGUE_FILTER_SEQUENCE_LIST_API)
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
                                    leagueList.clear();
                                    GetLeagueResponce leagueResponce = new Gson().fromJson(String.valueOf(response), GetLeagueResponce.class);
                                    leagueList.addAll(leagueResponce.getData().getLeague_list());
                                    adapter.notifyDataSetChanged();
                                    resetList.addAll(leagueList);

                                } else {
                                    Toast.makeText(SortingTournamentActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
            case R.id.tv_move_top:
                List<GetLeagueResponce.DataBean.LeagueListBean> selectList = new ArrayList<>();
                List<GetLeagueResponce.DataBean.LeagueListBean> unSelectList = new ArrayList<>();

                for (int i = 0; i < leagueList.size(); i++) {
                    if (leagueList.get(i).isChecked()) {
                        selectList.add(leagueList.get(i));
                    } else {
                        unSelectList.add(leagueList.get(i));
                    }
                }

                leagueList.clear();
                leagueList.addAll(selectList);
                leagueList.addAll(unSelectList);

                for (GetLeagueResponce.DataBean.LeagueListBean list : leagueList) {
                    list.setChecked(false);
                }

                adapter.notifyDataSetChanged();


                break;
            case R.id.tv_move_bottom:

                List<GetLeagueResponce.DataBean.LeagueListBean> selectList1 = new ArrayList<>();
                List<GetLeagueResponce.DataBean.LeagueListBean> unSelectList2 = new ArrayList<>();

                for (int i = 0; i < leagueList.size(); i++) {

                    if (leagueList.get(i).isChecked()) {
                        selectList1.add(leagueList.get(i));
                    } else {
                        unSelectList2.add(leagueList.get(i));
                    }
                }

                leagueList.clear();
                leagueList.addAll(unSelectList2);
                leagueList.addAll(selectList1);

                for (GetLeagueResponce.DataBean.LeagueListBean list : leagueList) {
                    list.setChecked(false);
                }
                adapter.notifyDataSetChanged();

                break;
            case R.id.tv_reset:
                leagueList.clear();
                leagueList.addAll(resetList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < leagueList.size(); i++) {
                    try {
                        jsonObject.put(leagueList.get(i).getLeague_id(), i + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("legue id", jsonObject.toString());
                setPreferOrderApi(jsonObject.toString());
                break;

        }

    }

    private void setPreferOrderApi(String legueOrder) {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.post(BASE_URL + SET_FILTER_LEAGUES_API)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .addBodyParameter("parameter", legueOrder)
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
                                    onBackPressed();

                                } else {
                                    Toast.makeText(SortingTournamentActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
    public void onItemCLick(GetLeagueResponce.DataBean.LeagueListBean bean, String value) {
     /*   if (value.equals("1")) {
            tempList.add(bean);
            bean.setIs_selected("0");
            adapter.notifyDataSetChanged();
        } else {
            tempList.remove(bean);
            bean.setIs_selected("1");
        }*/

    }

}
