package com.wolfscore.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.FilterTournamentAdapter;
import com.wolfscore.databinding.ActivityLeagueFilteringBinding;
import com.wolfscore.matches.modal.FilteredEvent;
import com.wolfscore.responce.GetLeagueResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.wolfscore.utils.ApiCollection.ADD_REMOVE_FILTERED_LEAGUE_API;
import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_LEAGUE_LIST_API;

public class LeagueFilteringActivity extends AppCompatActivity implements View.OnClickListener, FilterTournamentAdapter.FilterTournamentListenr {
    ActivityLeagueFilteringBinding binding;
    private ProgressDialog progressDialog;
    private FilterTournamentAdapter adapter;
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;
    private Set<GetLeagueResponce.DataBean.LeagueListBean> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_league_filtering);

        intializeView();

    }

    private void intializeView() {
        leagueList = new ArrayList<>();
        tempList = new HashSet<>();
        progressDialog = new ProgressDialog(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvFilter.setLayoutManager(layoutManager);
        adapter = new FilterTournamentAdapter(this, leagueList, this);
        binding.rvFilter.setAdapter(adapter);


        binding.tvSorting.setOnClickListener(this);
        binding.tvDone.setOnClickListener(this);
        binding.tvDeselect.setOnClickListener(this);
        binding.tvSelect.setOnClickListener(this);


        binding.etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                LeagueFilteringActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

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
                                    leagueList.clear();
                                    GetLeagueResponce leagueResponce = new Gson().fromJson(String.valueOf(response), GetLeagueResponce.class);
                                    leagueList.addAll(leagueResponce.getData().getLeague_list());
                                    adapter.notifyDataSetChanged();

                                    for (GetLeagueResponce.DataBean.LeagueListBean listBean : leagueList) {
                                        if (listBean.getIs_selected().equals("1")) {
                                            tempList.add(listBean);
                                        }
                                    }

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
                startActivity(new Intent(LeagueFilteringActivity.this,SortingTournamentActivity.class));

                break;
            case R.id.tv_done:
                Constant.hideSoftKeyBoard(this, binding.etSearch);
                EventBus.getDefault().post(new FilteredEvent(StringBuffer().toString()));
                onBackPressed();

              // EventBus.getDefault().post(new FilteredEvent(StringBuffer().toString()));
                break;
            case R.id.tv_select:

                break;
            case R.id.tv_deselect:
                AddRemoveLeagueApi("", "remove_all");
                break;
        }
    }

    private StringBuffer StringBuffer() {
        StringBuffer sb = new StringBuffer();
        for (GetLeagueResponce.DataBean.LeagueListBean list : tempList) {
            sb.append(list.getLeague_id()).append(",");
        }

        if (!sb.toString().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            // Log.e("favroit team", stringBuffer.toString());
        }
        return sb;
    }

    @Override
    public void filterTournamentOnClick(GetLeagueResponce.DataBean.LeagueListBean bean, String value) {
        if (value.equals("1")) {
            bean.setIs_selected("1");
            adapter.notifyDataSetChanged();
            tempList.add(bean);
            AddRemoveLeagueApi(bean.getLeague_id(), "add");
        } else {
            bean.setIs_selected("0");
            adapter.notifyDataSetChanged();
            tempList.remove(bean);
            AddRemoveLeagueApi(bean.getLeague_id(), "remove");
        }
    }

    private void AddRemoveLeagueApi(String league_id, final String value) {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            // progressDialog.show();
            AndroidNetworking.post(BASE_URL + ADD_REMOVE_FILTERED_LEAGUE_API)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .addBodyParameter("type", value)
                    .addBodyParameter("league_id", league_id)
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

                                    if (value.equals("remove_all")) {
                                        getLeagueList();
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
    public void onBackPressed() {
        super.onBackPressed();

    }


}
