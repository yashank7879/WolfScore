package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.wolfscore.activity.FavoriteFollowingResponce;
import com.wolfscore.adapter.FollowingFavoriteAdapter;
import com.wolfscore.adapter.FollowingPlayerAdapter;
import com.wolfscore.adapter.FollowingTeamAdapter;
import com.wolfscore.databinding.FragmentFavoriteFollowingBinding;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_MY_FAVORITE_LIST_API;


public class FavoriteFollowingFragment extends Fragment {
    FragmentFavoriteFollowingBinding binding;
    private Context mContext;
    private ProgressDialog progressDialog;
    private List<FavoriteFollowingResponce.DataBean.PlayerListBeanX.PlayerListBean> playerList;
    private List<FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean> teamList;
    private List<FavoriteFollowingResponce.DataBean.FavoriteListBeanX.FavListBean> favList;
    private FollowingPlayerAdapter playerAdapter;
    private FollowingTeamAdapter teamAdapter;
    private FollowingFavoriteAdapter favoriteAdapter;

    public FavoriteFollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_following, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intializeView();
    }

    private void intializeView() {
        progressDialog = new ProgressDialog(mContext);
        playerList = new ArrayList<>();
        teamList = new ArrayList<>();
        favList = new ArrayList<>();

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        binding.rvPlayer.setLayoutManager(horizontalLayoutManager);
        playerAdapter = new FollowingPlayerAdapter(playerList, mContext);
        binding.rvPlayer.setAdapter(playerAdapter);

        addTeamList();

        LinearLayoutManager horizontalLayout = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        binding.rvTeams.setLayoutManager(horizontalLayout);
        teamAdapter = new FollowingTeamAdapter(teamList, mContext);
        binding.rvTeams.setAdapter(teamAdapter);


        LinearLayoutManager favHorizontalLayout = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        binding.rvFav.setLayoutManager(favHorizontalLayout);
        favoriteAdapter = new FollowingFavoriteAdapter(favList, mContext);
        binding.rvFav.setAdapter(favoriteAdapter);


        getFollowingFavorite();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFollowingFavorite();
    }

    private void addTeamList(){

        FavoriteFollowingResponce.DataBean.PlayerListBeanX.PlayerListBean playerListBean = new FavoriteFollowingResponce.DataBean.PlayerListBeanX.PlayerListBean();
        playerListBean.setFirst_name("Browse");
        playerList.add(playerListBean);

        FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean teamListBean = new FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean();
        teamListBean.setName("Browse");
        teamList.add(teamListBean);

        FavoriteFollowingResponce.DataBean.FavoriteListBeanX.FavListBean fav = new FavoriteFollowingResponce.DataBean.FavoriteListBeanX.FavListBean();
        fav.setLeague_name("Browse");
        favList.add(fav);


    }

    private void getFollowingFavorite() {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + GET_MY_FAVORITE_LIST_API)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (progressDialog!=null&&progressDialog.isShowing())
                                progressDialog.dismiss();
                            try {
                                String status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                    teamList.clear();
                                    playerList.clear();
                                    favList.clear();
                                    addTeamList();
                                    FavoriteFollowingResponce responce1 = new Gson().fromJson(response.toString(), FavoriteFollowingResponce.class);
                                    teamList.addAll(responce1.getData().getTeam_list().getTeam_list());
                                    playerList.addAll(responce1.getData().getPlayer_list().getPlayer_list());
                                    favList.addAll(responce1.getData().getLeague_list().getLeague_list());

                                    setFavrouitCount(responce1);
                                    teamAdapter.notifyDataSetChanged();
                                    playerAdapter.notifyDataSetChanged();
                                    favoriteAdapter.notifyDataSetChanged();
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

    private void setFavrouitCount(FavoriteFollowingResponce responce1) {
        binding.tvPlayerCount.setText("Player"+" ("+responce1.getData().getPlayer_list().getTotal_records()+")");
        binding.tvTeamCount.setText("Team"+" ("+responce1.getData().getTeam_list().getTotal_records()+")");
        binding.tvFavCount.setText("Favorite"+" ("+responce1.getData().getLeague_list().getTotal_records()+")");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

    }

}
