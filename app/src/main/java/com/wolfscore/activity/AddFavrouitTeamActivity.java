package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.LocalTeamAdapter;
import com.wolfscore.databinding.ActivityAddFavrouitTeamBinding;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.responce.TopPlayerResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.wolfscore.utils.ApiCollection.ADD_FAVOURITES;
import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

public class AddFavrouitTeamActivity extends AppCompatActivity implements LocalTeamAdapter.TeamOnClick, View.OnClickListener {
    ActivityAddFavrouitTeamBinding binding;
    private ProgressDialog progressDialog;
    private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
    private List<LocalTeamResponce.DataBean.TeamListBean> tempList;

    private LocalTeamAdapter adapter;
    private int offset = 0;
    private int limit = 20;
    private String search = "";
    private Handler handler;
    private StringBuilder stringBuffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_favrouit_team);

        intializeView();

    }

    private void intializeView() {
        progressDialog = new ProgressDialog(this);
        tempList = new ArrayList<>();
        teamList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.layoutPopularTeam.rvPopularTeam.setLayoutManager(layoutManager);
        adapter = new LocalTeamAdapter(this, teamList, this);
        binding.layoutPopularTeam.rvPopularTeam.setAdapter(adapter);

        binding.rlBottomBar.setVisibility(View.VISIBLE);
        binding.actionBar.setVisibility(View.VISIBLE);
        binding.ivBack.setOnClickListener(this);
        binding.btnDone.setOnClickListener(this);


        //******  Pagination """""""""""""""//
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(AddFavrouitTeamActivity.this, binding.mainLayout)) {
                    if (page != 1) {
                        progressDialog.show();
                    }
                    offset = offset + 20; //load 5 items in recyclerview
                    // progressDialog.show();
                    getPopularTeam(search);

                }
            }
        };


        binding.layoutPopularTeam.etSearch.addTextChangedListener(new TextWatcher() {
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
                getPopularTeam(search);
            }
        });

        binding.layoutPopularTeam.rvPopularTeam.addOnScrollListener(scrollListener);

        getPopularTeam(search);
    }

    private void getPopularTeam(String search) {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            AndroidNetworking.get(BASE_URL + "teams/get_popular_teams")
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("search_term", search)
                    .addQueryParameter("limit", limit + "")
                    .addQueryParameter("offset", offset + "")

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

                                    if (teamResponce.getData().getTotal_records().equals("0")) {
                                       /*teamList.clear();
                                        adapter.notifyDataSetChanged();*/
                                        binding.layoutPopularTeam.tvNoResult.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.layoutPopularTeam.tvNoResult.setVisibility(View.GONE);
                                    }

                                    for (LocalTeamResponce.DataBean.TeamListBean team : teamList) {
                                        if (team.getIs_favorite().equals("1")) {
                                            tempList.add(team);
                                        }
                                    }

                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AddFavrouitTeamActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
    public void teamItemOnCLick(LocalTeamResponce.DataBean.TeamListBean bean, String value) {
        if (value.equals("1")) {
            favrouitApi(bean,"1");
            binding.tvFavroitTeam.setVisibility(View.VISIBLE);
            binding.tvFavroitTeam.setText(MessageFormat.format("{0} {1}", bean.getName(), getString(R.string.added_as_favorite)));
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.tvFavroitTeam.setVisibility(View.GONE);
                }
            }, 3000);

           // tempList.add(bean);
          //  bean.setIs_favorite("1");
         //   adapter.notifyDataSetChanged();
        } else {
            favrouitApi(bean,"0");
            //tempList.remove(bean);
         //   bean.setIs_favorite("0");
        //    adapter.notifyDataSetChanged();
        }
    }




    private void favrouitApi(LocalTeamResponce.DataBean.TeamListBean bean, String value) {
        if ( Constant.isNetworkAvailable(this, binding.mainLayout)) {//http://dev.wolfscore.info/api_v1/users/single_favorite_unfavorite
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            AndroidNetworking.post(BASE_URL + SINGLE_FAVORITE_UNFAVORITE_API)
                    .addBodyParameter("request_type", value)
                    .addBodyParameter("request_id", ""+bean.getTeam_id())//team id,player id,league id
                    .addBodyParameter("type", "team")// team || player ||league
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            String status = null;
                            try {
                                status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {

                                    if (value.equals("0")){
                                        bean.setIs_favorite("0");
                                        adapter.notifyDataSetChanged();
                                    }else {
                                        bean.setIs_favorite("1");
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    if (value.equals("0")){
                                        bean.setIs_favorite("1");
                                    }else {
                                        bean.setIs_favorite("0");
                                    }  adapter.notifyDataSetChanged();

                                    Toast.makeText(AddFavrouitTeamActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                }
                                //adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressDialog.dismiss();
                        }
                    });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_done:
                onBackPressed();
              //  getSelectedteam();
              //  selectFavrouitApi();
                break;

        }
    }


    private void getSelectedteam() {
        stringBuffer = new StringBuilder();
        for (LocalTeamResponce.DataBean.TeamListBean teamListBean : tempList) {
            if (teamListBean.getIs_favorite().equals("1")) {
                stringBuffer.append(teamListBean.getTeam_id()).append(",");
            }
        }
        if (!stringBuffer.toString().isEmpty()) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            Log.e("favroit player", stringBuffer.toString());
        }
    }

    //""""""" favroit local team , popular team  """""""""""//
    private void selectFavrouitApi() {
        if (Constant.isNetworkAvailable(this, binding.mainLayout)) {
            AndroidNetworking.post(BASE_URL + ADD_FAVOURITES)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(this, PreferenceConnector.AUTH_TOKEN, ""))
                    .addBodyParameter("favourite_type", "team")
                    .addBodyParameter("favourite_ids", stringBuffer.toString())
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

                                    tempList.clear();
                                    onBackPressed();
                                  /*  if(nextCount == 3){
                                        Intent intent = new Intent(SetupAppActivity.this,HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }*/

                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AddFavrouitTeamActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
}
