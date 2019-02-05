package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.wolfscore.listener.GetTeamListener;
import com.wolfscore.R;
import com.wolfscore.adapter.LocalTeamAdapter;
import com.wolfscore.databinding.FragmentPopularTeamBinding;
import com.wolfscore.listener.NextOnClick;
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


public class PopularTeamFragment extends Fragment implements LocalTeamAdapter.TeamOnClick {
    FragmentPopularTeamBinding binding;
    private Context mContext;
    private ProgressDialog progressDialog;
    private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
    private LocalTeamAdapter adapter;
    private int limit=20;
    private String search="";
    private int offset=0;
    private GetTeamListener listener;
    private NextOnClick nextListener;


    public PopularTeamFragment() {
     //  this.listener = listener;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_team, container, false);
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        //getPopularTeam(search);
    }



 /*   @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            // ...
            Log.e( "setMenuVisibility: ","123" );

            getPopularTeam(search);
        }
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e( "setUserVisibleHint: ","123" );
            teamList.clear();
            limit=20;
            offset=0;
            getPopularTeam(search);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(mContext);
        teamList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvPopularTeam.setLayoutManager(layoutManager);
        adapter = new LocalTeamAdapter(mContext, teamList, this);
        binding.rvPopularTeam.setAdapter(adapter);


        //******  Pagination """""""""""""""//
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                    if (page != 1) {
                        progressDialog.show();
                    }
                    offset = offset + 20; //load 5 items in recyclerview
                    // progressDialog.show();
                    getPopularTeam(search);

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
                getPopularTeam(search);
            }
        });

     //   getPopularTeam(search);

        binding.rvPopularTeam.addOnScrollListener(scrollListener);
    }

    private void getPopularTeam(String search) {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            AndroidNetworking.get(BASE_URL + "teams/get_popular_teams?search_term="+ this.search +"&limit="+limit+"&offset="+offset)
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

                                    if (teamResponce.getData().getTotal_records().equals("0")){
                                       /*teamList.clear();
                                        adapter.notifyDataSetChanged();*/
                                        binding.tvNoResult.setVisibility(View.VISIBLE);
                                    }else {
                                        binding.tvNoResult.setVisibility(View.GONE);
                                    }

                                    for (LocalTeamResponce.DataBean.TeamListBean team :teamList){
                                        if (team.getIs_favorite().equals("1")){
                                            nextListener.nextPopularOnclickListener(team, "Popularteam", true);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        listener = (GetTeamListener) context;
        nextListener = (NextOnClick) context;
    }

    @Override
    public void teamItemOnCLick(LocalTeamResponce.DataBean.TeamListBean bean, String value) {
        if (value.equals("1")) {
            bean.setIs_favorite("1");
            listener.getTeamNameListener(bean.getName());
            adapter.notifyDataSetChanged();
            nextListener.nextPopularOnclickListener(bean, "Popularteam", false);
        } else {
            bean.setIs_favorite("0");
            adapter.notifyDataSetChanged();
            nextListener.nextPopularOnclickListener(bean, "Popularteam", true);
        }
    }
}
