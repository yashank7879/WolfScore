package com.wolfscore.activity;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.TopPlayerAdapter;
import com.wolfscore.databinding.FragmentTopPlayerBinding;
import com.wolfscore.listener.PlayerOnClick;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.TopPlayerResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.ADD_FAVOURITES;
import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

public class AddFavrouitPlayerActivity extends AppCompatActivity implements PlayerOnClick, View.OnClickListener {
    FragmentTopPlayerBinding binding;
    private ProgressDialog progressDialog;
    private List<TopPlayerResponce.DataBean.PlayerListBean> playerList;
    private List<TopPlayerResponce.DataBean.PlayerListBean> tempList;
    private TopPlayerAdapter adapter;
    private int limit = 20;
    private int offset = 0;
    private Handler handler;
    private StringBuilder stringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_top_player);

        intializeView();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void intializeView() {
        binding.rlBottomBar.setVisibility(View.VISIBLE);
        binding.btnDone.setOnClickListener(this);
        playerList = new ArrayList<>();
        tempList = new ArrayList<>();
        binding.actionBar.setVisibility(View.VISIBLE);
        binding.ivBack.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvTopPlayer.setLayoutManager(layoutManager);
        adapter = new TopPlayerAdapter(this, playerList, this);
        binding.rvTopPlayer.setAdapter(adapter);

        //******  Pagination """""""""""""""//
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(AddFavrouitPlayerActivity.this, binding.mainLayout)) {
                    offset = offset + 20; //load 5 items in recyclerview
                    // progressDialog.show();
                    getTopPlayer();
                }
            }
        };
        binding.rvTopPlayer.addOnScrollListener(scrollListener);

        getTopPlayer();
    }

    private void getTopPlayer() {
        if (Constant.isNetworkAvailable(AddFavrouitPlayerActivity.this, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + "players/get_popular_players?limit=" + limit + "&offset=" + offset)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(AddFavrouitPlayerActivity.this, PreferenceConnector.AUTH_TOKEN, ""))
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

                                    TopPlayerResponce teamResponce = new Gson().fromJson(String.valueOf(response), TopPlayerResponce.class);
                                    playerList.addAll(teamResponce.getData().getPlayer_list());
                                    adapter.notifyDataSetChanged();

                                    for (TopPlayerResponce.DataBean.PlayerListBean player : playerList) {
                                        if (player.getIs_favorite().equals("1")) {
                                            tempList.add(player);
                                        }
                                    }
                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AddFavrouitPlayerActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
    public void playerItemOnCLick(TopPlayerResponce.DataBean.PlayerListBean bean, String value) {
        if (value.equals("1")) {

            favrouitApi(bean,"1");
         //   bean.setIs_favorite("1");
         //   adapter.notifyDataSetChanged();

           // tempList.add(bean);
            binding.tvFavroitTeam.setVisibility(View.VISIBLE);
            binding.tvFavroitTeam.setText(MessageFormat.format("{0} {1}", bean.getFirst_name(), getString(R.string.added_as_favorite)));
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.tvFavroitTeam.setVisibility(View.GONE);
                }
            }, 3000);

        } else {
            favrouitApi(bean,"0");
         //   bean.setIs_favorite("0");
         //   adapter.notifyDataSetChanged();
          //  tempList.remove(bean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                onBackPressed();
               // getSelectedPlayer();
                //selectFavrouitApi();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    private void getSelectedPlayer() {
        stringBuffer = new StringBuilder();
        for (TopPlayerResponce.DataBean.PlayerListBean teamListBean : tempList) {
            if (teamListBean.getIs_favorite().equals("1")) {
                stringBuffer.append(teamListBean.getPlayer_id()).append(",");
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
                    .addBodyParameter("favourite_type", "player")
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
                                    Toast.makeText(AddFavrouitPlayerActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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

    private void favrouitApi(TopPlayerResponce.DataBean.PlayerListBean bean, String value) {
        if ( Constant.isNetworkAvailable(this, binding.mainLayout)) {//http://dev.wolfscore.info/api_v1/users/single_favorite_unfavorite
           getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            AndroidNetworking.post(BASE_URL + SINGLE_FAVORITE_UNFAVORITE_API)
                    .addBodyParameter("request_type", value)
                    .addBodyParameter("request_id", ""+bean.getPlayer_id())//team id,player id,league id
                    .addBodyParameter("type", "player")// team || player ||league
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
                                    Toast.makeText(AddFavrouitPlayerActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
