package com.wolfscore.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.wolfscore.activity.LeagueFilteringActivity;
import com.wolfscore.activity.SortingTournamentActivity;
import com.wolfscore.adapter.FilterTournamentAdapter;
import com.wolfscore.adapter.LeagueListAdapter;
import com.wolfscore.adapter.PopularListAdapter;
import com.wolfscore.adapter.TopPlayerAdapter;
import com.wolfscore.databinding.ActivityLeagueFilteringBinding;
import com.wolfscore.databinding.FragmentLeagueListBinding;
import com.wolfscore.databinding.FragmentTopPlayerBinding;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.listener.GetTeamListener;
import com.wolfscore.listener.NextOnClick;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.GetLeagueResponce;
import com.wolfscore.responce.TopPlayerResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

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
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

/**
 * Created by mindiii on 7/3/19.
 */

public class LeagueListFragment extends Fragment implements View.OnClickListener, LeagueListAdapter.FavoriteUnFavorite{
    private Context mContext;
    private FragmentLeagueListBinding binding;
    private int limit = 20;
    private int offset = 0;
    private GetTeamListener listener;
    private NextOnClick nextListener;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ProgressDialog progressDialog;
    private LeagueListAdapter adapter;
    private PopularListAdapter popAdapter;
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;
    private List<GetLeagueResponce.DataBean.LeagueListBean> popular_leagueList;
    int total_Record=0;String search="";

    public LeagueListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_list, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intializeview();
        //  getTopPlayer();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e( "setUserVisibleHint: ","123" );
            leagueList.clear();
         //   scrollListener.resetState();
            limit=50;
            offset=0;
            if (search.isEmpty()) {
                progressDialog.show();
            }
            getLeagueList();


        }

    }


    private void intializeview() {

        leagueList = new ArrayList<>();
        popular_leagueList = new ArrayList<>();
        progressDialog = new ProgressDialog(mContext);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager.setAutoMeasureEnabled(true);
        binding.rvFilter.setLayoutManager(layoutManager);
        binding.popularRv.setLayoutManager(layoutManager1);
        adapter = new LeagueListAdapter(mContext, leagueList, this);
        popAdapter = new PopularListAdapter(mContext, popular_leagueList, this);
        binding.rvFilter.setAdapter(adapter);
        binding.popularRv.setAdapter(popAdapter);
        Rect scrollBounds = new Rect();
        binding.popularRv.getHitRect(scrollBounds);


        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              //  adapter.getFilter().filter(charSequence);
              //  popAdapter.getFilter().filter(charSequence);
             //   adapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("listFragmnet", "afterTextChanged: ");

                leagueList.clear();
                popular_leagueList.clear();
                offset = 0;
                search = editable.toString();
                if (search.isEmpty()) {
                        progressDialog.show();
                    }
                getLeagueList();

                //ToDo: Progress will start here
/*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.rvFilter.refreshDrawableState();
                        binding.popularRv.refreshDrawableState();
                        */
/*binding.rvFilter.requestLayout();
                        binding.popularRv.requestLayout();*//*

                        //ToDo: Progress will stop here
                    }
                },1000);
*/
            }
        });


       // getLeagueList();

        binding.nestedScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (binding.popularRv.getLocalVisibleRect(scrollBounds)) {
              //  Log.d("OUTRECT","VISIBLE");
                binding.mainCollapsing.setTitle("Popular League");
            } else {
             //   Log.d("OUTRECT","INVISIBLE");
                binding.mainCollapsing.setTitle("Rest of the World");
            }


            if(v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {

                    offset = offset + 50; //load 5 items in recyclerview
                    // progressDialog.show();
                 /*   if (offset<=total_Record) {*/
                    if (search.isEmpty()) {
                        progressDialog.show();
                    }
                        getLeagueList();
                    //}


                    //code to fetch more data for endless scrolling
                }
            }
        });

      /*  scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                    progressDialog.show();
                    offset = offset + 50; //load 5 items in recyclerview
                    // progressDialog.show();
                    getLeagueList();

                }
            }
        };
        binding.rvFilter.addOnScrollListener(scrollListener);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private void getLeagueList() {
      //  progressDialog.show();
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            AndroidNetworking.get(BASE_URL + GET_LEAGUE_LIST_API)
                    .addQueryParameter("search_term",search)
                    .addQueryParameter("offset",""+offset)
                    .addQueryParameter("limit","50")
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                String status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                  //  leagueList.clear();
                                    popular_leagueList.clear();
                                    GetLeagueResponce leagueResponce = new Gson().fromJson(String.valueOf(response), GetLeagueResponce.class);
                                    total_Record=Integer.parseInt(leagueResponce.getData().getTotal_records());
                                    leagueList.addAll(leagueResponce.getData().getLeague_list());
                                    popular_leagueList.addAll(leagueResponce.getData().getPopular_league());


                                     if(popular_leagueList.size()==0&&leagueList.size()==0)
                                    {
                                        binding.mainCollapsing.setTitle("No Data found");
                                        binding.viewBg.setVisibility(View.GONE);
                                        binding.tvPopularContent.setVisibility(View.GONE);
                                    }

                                     else if (popular_leagueList.size()==0)
                                   {
                                       binding.mainCollapsing.setTitle("Rest of the World");
                                       binding.viewBg.setVisibility(View.GONE);
                                       binding.tvPopularContent.setVisibility(View.VISIBLE);
                                   }
                                   else {
                                         binding.viewBg.setVisibility(View.VISIBLE);
                                         binding.tvPopularContent.setVisibility(View.VISIBLE);
                                     }

                                    adapter.notifyDataSetChanged();
                                    popAdapter.notifyDataSetChanged();
                                    if (progressDialog.isShowing())
                                        progressDialog.dismiss();

                                }
                                else {

                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    });
        }

    }
    @Override
    public void onClick(View v) {

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void favUnfav(com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean league, String value) {
        if (value.equals("1")) {
            if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
               // ivStar.setEnabled(false);
                league.setIs_favorite("1");
                favrouitApi(league, value);
            }
        } else {
            if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
             //   ivStar.setEnabled(false);
                league.setIs_favorite("0");
                favrouitApi(league, value);
            }
        }
    }



    private void favrouitApi(final com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean league,final String value) {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {//http://dev.wolfscore.info/api_v1/users/single_favorite_unfavorite

            Log.d("auttoken", "getMatchData: auttoken...."+PreferenceConnector.readString(getActivity(), PreferenceConnector.AUTH_TOKEN,""));

            AndroidNetworking.post(BASE_URL + SINGLE_FAVORITE_UNFAVORITE_API)
                    .addBodyParameter("request_type", value)
                    .addBodyParameter("request_id", ""+league.getLeague_id())//team id,player id,league id
                    .addBodyParameter("type", "league")// team || player ||league
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
                                  //  star.setEnabled(true);
                                    league.setIs_favorite(value);
                                } else {
                                    if (value.equals("1")) {
                                        league.setIs_favorite("0");
                                    } else if (value.equals("0")) {
                                        league.setIs_favorite("1");
                                    }
                                   adapter.notifyDataSetChanged();
                                    popAdapter.notifyDataSetChanged();
                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
                                }
                                //adapter.notifyDataSetChanged();
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
