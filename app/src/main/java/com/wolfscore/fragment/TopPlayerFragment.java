package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.TopPlayerAdapter;
import com.wolfscore.databinding.FragmentTopPlayerBinding;
import com.wolfscore.listener.GetTeamListener;
import com.wolfscore.listener.NextOnClick;
import com.wolfscore.listener.PlayerOnClick;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.responce.TopPlayerResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;

public class TopPlayerFragment extends Fragment implements PlayerOnClick {
    FragmentTopPlayerBinding binding;
    private Context mContext;
    private ProgressDialog progressDialog;
    private List<TopPlayerResponce.DataBean.PlayerListBean> teamList;
    private TopPlayerAdapter adapter;
    private int limit = 20;
    private int offset = 0;
    private GetTeamListener listener;
    private NextOnClick nextListener;

    public TopPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_player, container, false);
        return binding.getRoot();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e( "setUserVisibleHint: ","123" );
            teamList.clear();
            limit=20;
            offset=0;
            getTopPlayer();
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intializeview();
      //  getTopPlayer();
    }

    private void intializeview() {

        teamList = new ArrayList<>();
        progressDialog = new ProgressDialog(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvTopPlayer.setLayoutManager(layoutManager);
        adapter = new TopPlayerAdapter(mContext, teamList, this);
        binding.rvTopPlayer.setAdapter(adapter);

        //******  Pagination """""""""""""""//
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                    progressDialog.show();
                    offset = offset + 20; //load 5 items in recyclerview
                    // progressDialog.show();
                    getTopPlayer();

                }
            }
        };
        binding.rvTopPlayer.addOnScrollListener(scrollListener);
        getTopPlayer();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        listener = (GetTeamListener) context;
        nextListener = (NextOnClick) context;
    }

    private void getTopPlayer() {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + "players/get_popular_players?limit=" + limit + "&offset=" + offset)
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
                                    TopPlayerResponce teamResponce = new Gson().fromJson(String.valueOf(response), TopPlayerResponce.class);
                                    teamList.addAll(teamResponce.getData().getPlayer_list());
                                    adapter.notifyDataSetChanged();

                                    for (TopPlayerResponce.DataBean.PlayerListBean player : teamList) {
                                        if (player.getIs_favorite().equals("1")) {
                                            nextListener.nextPlayerOnclickListener(player, "player", false);
                                        }
                                    }
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
    public void playerItemOnCLick(TopPlayerResponce.DataBean.PlayerListBean bean, String value) {
        if (value.equals("1")) {
            bean.setIs_favorite("1");
            adapter.notifyDataSetChanged();
            listener.getTeamNameListener(bean.getFirst_name());
            nextListener.nextPlayerOnclickListener(bean, "player", false);
        } else {
            bean.setIs_favorite("0");
            adapter.notifyDataSetChanged();
            nextListener.nextPlayerOnclickListener(bean, "player", true);
        }
    }
}
