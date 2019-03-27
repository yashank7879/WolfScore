package com.wolfscore.league.fragments;

import android.app.ExpandableListActivity;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.activity.AboutLeagueActivity;
import com.wolfscore.activity.HomeActivity;
import com.wolfscore.adapter.SearchTeamAdapter;
import com.wolfscore.databinding.ActivitySearchBinding;
import com.wolfscore.databinding.FragmentLeagueLayoutBinding;
import com.wolfscore.league.fragments.adapter.AllLeagueAdapter;
import com.wolfscore.league.fragments.adapter.FavPopularAdapter;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.matches.adapter.TableAdapter;
import com.wolfscore.matches.modal.TableDTO;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_COUNTRY_LEAGUE_LIST;
import static com.wolfscore.utils.ApiCollection.GET_POPULAR_TEAMS_API;
import static com.wolfscore.utils.ApiCollection.SINGLE_FAVORITE_UNFAVORITE_API;

/**
 * Created by mindiii on 27/2/19.
 */

public class LeagueFragment extends Fragment implements AllLeagueAdapter.FavrouitTeamSelect, View.OnClickListener {
    FragmentLeagueLayoutBinding binding;
    private ProgressDialog progressDialog;
    /* private SearchTeamAdapter adapter;
     private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
     private List<LocalTeamResponce.DataBean.TeamListBean> noRepeatList;

     private int offset = 0;
     private String search = "";
     private int limit = 50;*/
    private ArrayList<Country> countriesLeagueList = new ArrayList<>();
    private List<Country.League> fav_leagueArrayList = new ArrayList<>();
    private List<Country.League> popularLeagueList = new ArrayList<>();
    private AllLeagueAdapter allLeagueAdapter;
    private FavPopularAdapter favPopularAdapter;
    private FavPopularAdapter popularAdapter;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_layout, container, false);
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

        progressDialog = new ProgressDialog(mContext);
        binding.rlSearch.setOnClickListener(this);
        binding.tvCancelSearch.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        binding.favoriteListView.setLayoutManager(layoutManager);
        binding.popularListView.setLayoutManager(layoutManager1);
        favPopularAdapter = new FavPopularAdapter(mContext, fav_leagueArrayList, binding.favLayout, this);
        popularAdapter = new FavPopularAdapter(mContext, popularLeagueList,  binding.popularLayout,this);
        binding.favoriteListView.setAdapter(favPopularAdapter);
        binding.popularListView.setAdapter(popularAdapter);


        binding.expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Country.League league = (Country.League) allLeagueAdapter.getChild(groupPosition, childPosition);
                league.getLeague_id();
                startActivity(new Intent(mContext, AboutLeagueActivity.class).putExtra("League", league));
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                return false;
            }
        });

        binding.expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //  setListViewHeight(parent, groupPosition);

                return false;
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        binding.expListView.setIndicatorBounds(width - GetPixelFromDips(40), width - GetPixelFromDips(0));


        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                allLeagueAdapter.getFilter().filter(editable);
                favPopularAdapter.getFilter().filter(editable);
                popularAdapter.getFilter().filter(editable);
            }
        });

        getAllLeauge();
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void favrouitSelectUnselect(Country.League league, String value, ImageView ivStar) {
        if (value.equals("1")) {
            if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                ivStar.setEnabled(false);
                // league.setIs_favorite(1);
                favrouitApi(league, value, ivStar);
            }
        } else {
            if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                ivStar.setEnabled(false);
                //  league.setIs_favorite(0);
                favrouitApi(league, value, ivStar);
            }
        }
    }

/*
    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
*/


    //""""" Single favorite selct and un select """""""""//
    private void favrouitApi(Country.League league, String value, final ImageView star) {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {//http://dev.wolfscore.info/api_v1/users/single_favorite_unfavorite

            Log.d("auttoken", "getMatchData: auttoken...." + PreferenceConnector.readString(getActivity(), PreferenceConnector.AUTH_TOKEN, ""));

            AndroidNetworking.post(BASE_URL + SINGLE_FAVORITE_UNFAVORITE_API)
                    .addBodyParameter("request_type", value)
                    .addBodyParameter("request_id", "" + league.getLeague_id())//team id,player id,league id
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
                                    if (value.equals("1")) {
                                        league.setIs_favorite(1);
                                        fav_leagueArrayList.add(league);
                                    } else {
                                        league.setIs_favorite(0);
                                        for (int i = 0; i < fav_leagueArrayList.size(); i++) {
                                            if (fav_leagueArrayList.get(i).getLeague_id() == league.getLeague_id()) {
                                                fav_leagueArrayList.remove(i);
                                                break;
                                            }
                                        }
                                    }

                                    if (fav_leagueArrayList.size() == 0)
                                        binding.favLayout.setVisibility(View.GONE);
                                    else
                                        binding.favLayout.setVisibility(View.VISIBLE);

                                    star.setEnabled(true);

                                } else {

                                    if (fav_leagueArrayList.size() == 0)
                                        binding.favLayout.setVisibility(View.GONE);
                                    else
                                        binding.favLayout.setVisibility(View.VISIBLE);

                                    star.setEnabled(true);

                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
                                }

                                allLeagueAdapter.notifyDataSetChanged();
                                popularAdapter.notifyDataSetChanged();
                                favPopularAdapter.notifyDataSetChanged();
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
        switch (v.getId()) {
            case R.id.tv_cancel_search:
                binding.etSearch.setText("");
                allLeagueAdapter.notifyDataSetChanged();
                favPopularAdapter.notifyDataSetChanged();
                popularAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_search:

                break;

        }
    }

    private void getAllLeauge() {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + GET_COUNTRY_LEAGUE_LIST)
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
                                    //  Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                    countriesLeagueList.clear();
                                    fav_leagueArrayList.clear();
                                    popularLeagueList.clear();

                                    JSONObject data_obj = response.getJSONObject("data");
                                    JSONArray country_list_array = new JSONArray();
                                    JSONArray favorite_list_array = new JSONArray();
                                    JSONArray popular_list_array = new JSONArray();

                                    if (data_obj.has("favorite_league"))
                                        favorite_list_array = data_obj.getJSONArray("favorite_league");

                                    if (favorite_list_array.length() > 0) {
                                        for (int i = 0; i < favorite_list_array.length(); i++) {
                                            JSONObject league_list_object = favorite_list_array.getJSONObject(i);
                                            Country.League league = new Country.League();

                                            if (league_list_object.has("league_id"))
                                                league.setLeague_id(!league_list_object.isNull("league_id") ? league_list_object.getInt("league_id") : 0);
                                            if (league_list_object.has("country_id"))
                                                league.setCountry_id(!league_list_object.isNull("country_id") ? league_list_object.getInt("country_id") : 0);
                                            if (league_list_object.has("league_name"))
                                                league.setLeague_name(!league_list_object.isNull("league_name") ? league_list_object.getString("league_name") : "");
                                            if (league_list_object.has("league_flag"))
                                                league.setLeague_flag(!league_list_object.isNull("league_flag") ? league_list_object.getString("league_flag") : "");
                                            if (league_list_object.has("is_selected"))
                                                league.setIs_selected(!league_list_object.isNull("is_selected") ? league_list_object.getInt("is_selected") : 0);
                                            if (league_list_object.has("is_favorite"))
                                                league.setIs_favorite(!league_list_object.isNull("is_favorite") ? league_list_object.getInt("is_favorite") : 0);
                                            fav_leagueArrayList.add(league);


                                        }
                                        binding.favLayout.setVisibility(View.VISIBLE);
                                        favPopularAdapter.notifyDataSetChanged();

                                    }

                                    if (data_obj.has("popular_league"))
                                        popular_list_array = data_obj.getJSONArray("popular_league");

                                    if (popular_list_array.length() > 0) {
                                        for (int i = 0; i < popular_list_array.length(); i++) {
                                            JSONObject league_list_object = popular_list_array.getJSONObject(i);
                                            Country.League league = new Country.League();

                                            if (league_list_object.has("league_id"))
                                                league.setLeague_id(!league_list_object.isNull("league_id") ? league_list_object.getInt("league_id") : 0);
                                            if (league_list_object.has("country_id"))
                                                league.setCountry_id(!league_list_object.isNull("country_id") ? league_list_object.getInt("country_id") : 0);
                                            if (league_list_object.has("league_name"))
                                                league.setLeague_name(!league_list_object.isNull("league_name") ? league_list_object.getString("league_name") : "");
                                            if (league_list_object.has("league_flag"))
                                                league.setLeague_flag(!league_list_object.isNull("league_flag") ? league_list_object.getString("league_flag") : "");
                                            if (league_list_object.has("is_selected"))
                                                league.setIs_selected(!league_list_object.isNull("is_selected") ? league_list_object.getInt("is_selected") : 0);
                                            if (league_list_object.has("is_favorite"))
                                                league.setIs_favorite(!league_list_object.isNull("is_favorite") ? league_list_object.getInt("is_favorite") : 0);
                                            popularLeagueList.add(league);
                                        }
                                        binding.popularLayout.setVisibility(View.VISIBLE);
                                        popularAdapter.notifyDataSetChanged();
                                    }


                                    if (data_obj.has("country_list"))
                                        country_list_array = data_obj.getJSONArray("country_list");

                                    if (country_list_array.length() > 0) {
                                        for (int i = 0; i < country_list_array.length(); i++) {
                                            JSONObject object = country_list_array.getJSONObject(i);
                                            //  tableDTOS.clear();
                                            JSONArray league_list_array = null;
                                            if (object.has("league_list"))
                                                league_list_array = object.getJSONArray("league_list");

                                            ArrayList<Country.League> leagueArrayList = new ArrayList<>();

                                            if (league_list_array.length() > 0) {

                                                Country country = new Country();

                                                if (object.has("country_id"))
                                                    country.setCountry_id(!object.isNull("country_id") ? object.getInt("country_id") : 0);
                                                if (object.has("country_name"))
                                                    country.setCountry_name(!object.isNull("country_name") ? object.getString("country_name") : "");
                                                if (object.has("country_flag"))
                                                    country.setCountry_flag(!object.isNull("country_flag") ? object.getString("country_flag") : "");


                                                for (int j = 0; j < league_list_array.length(); j++) {
                                                    Country.League league = new Country.League();
                                                    JSONObject league_list_object = league_list_array.getJSONObject(j);

                                                    if (league_list_object.has("league_id"))
                                                        league.setLeague_id(!league_list_object.isNull("league_id") ? league_list_object.getInt("league_id") : 0);
                                                    if (league_list_object.has("country_id"))
                                                        league.setCountry_id(!league_list_object.isNull("country_id") ? league_list_object.getInt("country_id") : 0);
                                                    if (league_list_object.has("league_name"))
                                                        league.setLeague_name(!league_list_object.isNull("league_name") ? league_list_object.getString("league_name") : "");
                                                    if (league_list_object.has("league_flag"))
                                                        league.setLeague_flag(!league_list_object.isNull("league_flag") ? league_list_object.getString("league_flag") : "");
                                                    if (league_list_object.has("is_selected"))
                                                        league.setIs_selected(!league_list_object.isNull("is_selected") ? league_list_object.getInt("is_selected") : 0);
                                                    if (league_list_object.has("is_favorite"))
                                                        league.setIs_favorite(!league_list_object.isNull("is_favorite") ? league_list_object.getInt("is_favorite") : 0);

                                                    leagueArrayList.add(league);
                                                }
                                                country.setLeagueArrayList(leagueArrayList);
                                                countriesLeagueList.add(country);

                                            }
                                        }
                                        binding.countryLayout.setVisibility(View.VISIBLE);
                                        //  HomeActivity.homeActivity.mLeagues.clear();
                                        //   HomeActivity.homeActivity.mLeagues.addAll(countriesLeagueList);
                                        //HomeActivity.homeActivity.mLeagues=new ArrayList<>();
                                        //Collections.copy(countriesLeagueList,HomeActivity.homeActivity.mLeagues);

                                        allLeagueAdapter = new AllLeagueAdapter(mContext, countriesLeagueList, binding.countryLayout,LeagueFragment.this);
                                        binding.expListView.setAdapter(allLeagueAdapter);
                                        //   setListViewHeight(binding.expListView);

                                    }
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

}