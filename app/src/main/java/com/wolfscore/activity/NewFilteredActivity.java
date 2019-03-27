package com.wolfscore.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.adapter.FilterTournamentAdapter;
import com.wolfscore.adapter.FilterableAllLeagueAdapter;
import com.wolfscore.adapter.FilteredFavAdapter;
import com.wolfscore.databinding.ActivityLeagueFilteringBinding;
import com.wolfscore.league.fragments.LeagueFragment;
import com.wolfscore.league.fragments.adapter.AllLeagueAdapter;
import com.wolfscore.league.fragments.adapter.FavPopularAdapter;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.matches.modal.FilteredEvent;
import com.wolfscore.responce.GetLeagueResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.wolfscore.utils.ApiCollection.ADD_REMOVE_FILTERED_LEAGUE_API;
import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_COUNTRY_LEAGUE_LIST;
import static com.wolfscore.utils.ApiCollection.GET_LEAGUE_LIST_API;

/**
 * Created by mindiii on 14/3/19.
 */

public class NewFilteredActivity extends AppCompatActivity implements View.OnClickListener,  FilteredFavAdapter.FilterTournamentListenr {
    ActivityLeagueFilteringBinding binding;
    private ProgressDialog progressDialog;
    private FilterTournamentAdapter adapter;
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;
  //  private Set<GetLeagueResponce.DataBean.LeagueListBean> tempList;
    NewFilteredActivity mContext;
    /////////////
    private ArrayList<Country> countriesLeagueList=new ArrayList<>();
    private List<Country> mLeagues = new ArrayList<>();
    private List<Country.League> fav_leagueArrayList = new ArrayList<>();
    private List<Country.League> popularLeagueList = new ArrayList<>();
    private ArrayList<Country.League> tempLeagueList = new ArrayList<>();
    private FilterableAllLeagueAdapter allLeagueAdapter ;
    private FilteredFavAdapter favPopularAdapter,popularAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_league_filtering);
                 mContext=this;
        intializeView();

    }

    private void intializeView() {
        leagueList = new ArrayList<>();
     //   tempList = new HashSet<>();
        progressDialog = new ProgressDialog(this);
      //  LinearLayoutManager layoutManager = new LinearLayoutManager(this);
     //   binding.rvFilter.setLayoutManager(layoutManager);
      //  adapter = new FilterTournamentAdapter(this, leagueList, this);
      //  binding.rvFilter.setAdapter(adapter);


        binding.tvSorting.setOnClickListener(this);
        binding.tvDone.setOnClickListener(this);
        binding.tvDeselect.setOnClickListener(this);
        binding.tvSelect.setOnClickListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        binding.favoriteListView.setLayoutManager(layoutManager);
        binding.popularListView.setLayoutManager(layoutManager1);
        favPopularAdapter=new FilteredFavAdapter(mContext,fav_leagueArrayList,this);
        popularAdapter=new FilteredFavAdapter(mContext,popularLeagueList,this);
        binding.favoriteListView.setAdapter(favPopularAdapter);
        binding.popularListView.setAdapter(popularAdapter);


        binding.etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
              //  NewFilteredActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                allLeagueAdapter.getFilter().filter(arg0);
                favPopularAdapter.getFilter().filter(arg0);
                popularAdapter.getFilter().filter(arg0);
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        binding.expListView.setIndicatorBounds(width - GetPixelFromDips(40), width - GetPixelFromDips(0));


        //  getLeagueList();
        getAllLeauge();
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


    @Override
    public void filterTournamentOnClick(Country.League bean, String value) {
        if (value.equals("1")) {

            bean.setIs_selected(1);
            favPopularAdapter.notifyDataSetChanged();
            popularAdapter.notifyDataSetChanged();
            tempLeagueList.add(bean);
            AddRemoveLeagueApi(""+bean.getLeague_id(), "add");
        } else {
            bean.setIs_selected(0);
            favPopularAdapter.notifyDataSetChanged();
            popularAdapter.notifyDataSetChanged();
            tempLeagueList.remove(bean);
            AddRemoveLeagueApi(""+bean.getLeague_id(), "remove");
        }
    }


/*
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
                                       */
/* teamList.clear();
                                        adapter.notifyDataSetChanged();*//*

                                        // binding.tvNoResult.setVisibility(View.VISIBLE);
                                    } else {
                                        //  binding.tvNoResult.setVisibility(View.GONE);
                                    }


                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(NewFilteredActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sorting:
                startActivity(new Intent(NewFilteredActivity.this,SortingTournamentActivity.class));

                break;
            case R.id.tv_done:
                Constant.hideSoftKeyBoard(this, binding.etSearch);
              //  noRepeatMethod(tempLeagueList);
                if (noRepeatMethod(tempLeagueList).size()>25){
                    Toast.makeText(NewFilteredActivity.this,"Can not select more than 25 league", Toast.LENGTH_SHORT).show();

                }
                else {

                    EventBus.getDefault().post(new FilteredEvent(
                            StringBuffer(noRepeatMethod(tempLeagueList)).toString()));
                    onBackPressed();
                }

                // EventBus.getDefault().post(new FilteredEvent(StringBuffer().toString()));
                break;
            case R.id.tv_select:

                break;
            case R.id.tv_deselect:
                AddRemoveLeagueApi("", "remove_all");
                break;
        }
    }

    private StringBuffer StringBuffer(ArrayList<Country.League> leagues) {
        StringBuffer sb = new StringBuffer();
        for (Country.League list : leagues) {
            sb.append(list.getLeague_id()).append(",");
        }

        if (!sb.toString().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            // Log.e("favroit team", stringBuffer.toString());
        }
        return sb;
    }

    private ArrayList<Country.League> noRepeatMethod( ArrayList<Country.League> allEvents)
    {
       // fill with your events.
                ArrayList<Country.League> noRepeat = new ArrayList<Country.League>();

        for (Country.League event : allEvents) {
            boolean isFound = false;
            // check if the event name exists in noRepeat
            for (Country.League e : noRepeat) {
                if (e.getLeague_id()==event.getLeague_id() || (e.equals(event))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) noRepeat.add(event);
        }
        return  noRepeat;
    }

/*
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
*/

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
                                        HomeActivity.homeActivity.league_id="";
                                        tempLeagueList.clear();
                                        HomeActivity.homeActivity.todayMatchesArrayList.clear();
                                        HomeActivity.homeActivity.my_todayMatchesArrayList.clear();
                                      getAllLeauge();
                                    }
                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(NewFilteredActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
                                    tempLeagueList.clear();
                                    JSONObject data_obj= response.getJSONObject("data");
                                    JSONArray country_list_array=new JSONArray();
                                    JSONArray favorite_list_array=new JSONArray();
                                    JSONArray popular_list_array=new JSONArray();

                                    if (data_obj.has("favorite_league"))
                                        favorite_list_array= data_obj.getJSONArray("favorite_league");

                                    if (favorite_list_array.length()>0) {
                                        for (int i = 0; i < favorite_list_array.length(); i++) {
                                            JSONObject league_list_object = favorite_list_array.getJSONObject(i);
                                            Country.League league=new Country.League();

                                            if (league_list_object.has("league_id"))
                                                league.setLeague_id(!league_list_object.isNull("league_id") ? league_list_object.getInt("league_id") : 0);
                                            if (league_list_object.has("country_id"))
                                                league.setCountry_id(!league_list_object.isNull("country_id") ? league_list_object.getInt("country_id") : 0);
                                            if (league_list_object.has("league_name"))
                                                league.setLeague_name(!league_list_object.isNull("league_name") ? league_list_object.getString("league_name") : "");
                                            if (league_list_object.has("league_flag"))
                                                league.setLeague_flag(!league_list_object.isNull("league_flag") ? league_list_object.getString("league_flag") : "");
                                            if (league_list_object.has("is_favorite"))
                                                league.setIs_favorite(!league_list_object.isNull("is_favorite") ? league_list_object.getInt("is_favorite") : 0);
                                            if (league_list_object.has("is_selected")){
                                                league.setIs_selected(!league_list_object.isNull("is_selected") ? league_list_object.getInt("is_selected") : 0);
                                                if (league_list_object.getInt("is_selected")==1)
                                                {
                                                   tempLeagueList.add(league);
                                                }

                                            }


                                            fav_leagueArrayList.add(league);


                                        }
                                        binding.favLayout.setVisibility(View.VISIBLE);
                                        favPopularAdapter.notifyDataSetChanged();

                                    }

                                    if (data_obj.has("popular_league"))
                                        popular_list_array= data_obj.getJSONArray("popular_league");

                                    if (popular_list_array.length()>0) {
                                        for (int i = 0; i < popular_list_array.length(); i++) {
                                            JSONObject league_list_object = popular_list_array.getJSONObject(i);
                                            Country.League league=new Country.League();

                                            if (league_list_object.has("league_id"))
                                                league.setLeague_id(!league_list_object.isNull("league_id") ? league_list_object.getInt("league_id") : 0);
                                            if (league_list_object.has("country_id"))
                                                league.setCountry_id(!league_list_object.isNull("country_id") ? league_list_object.getInt("country_id") : 0);
                                            if (league_list_object.has("league_name"))
                                                league.setLeague_name(!league_list_object.isNull("league_name") ? league_list_object.getString("league_name") : "");
                                            if (league_list_object.has("league_flag"))
                                                league.setLeague_flag(!league_list_object.isNull("league_flag") ? league_list_object.getString("league_flag") : "");
                                            if (league_list_object.has("is_favorite"))
                                                league.setIs_favorite(!league_list_object.isNull("is_favorite") ? league_list_object.getInt("is_favorite") : 0);

                                            if (league_list_object.has("is_selected")){
                                                league.setIs_selected(!league_list_object.isNull("is_selected") ? league_list_object.getInt("is_selected") : 0);
                                                if (league_list_object.getInt("is_selected")==1)
                                                {
                                                    tempLeagueList.add(league);
                                                }

                                            }

                                            popularLeagueList.add(league);


                                        }
                                        binding.popularLayout.setVisibility(View.VISIBLE);
                                       popularAdapter.notifyDataSetChanged();

                                    }





                                    if (data_obj.has("country_list"))
                                        country_list_array= data_obj.getJSONArray("country_list");

                                    if (country_list_array.length()>0) {
                                        for (int i = 0; i < country_list_array.length(); i++) {
                                            JSONObject object = country_list_array.getJSONObject(i);
                                            //  tableDTOS.clear();

                                            if (object.has("league_list"));
                                            JSONArray league_list_array=object.getJSONArray("league_list");

                                            ArrayList<Country.League> leagueArrayList=new ArrayList<>();

                                            if (league_list_array.length()>0) {

                                                Country country = new Country();

                                                if (object.has("country_id"))
                                                    country.setCountry_id(!object.isNull("country_id") ? object.getInt("country_id") : 0);
                                                if (object.has("country_name"))
                                                    country.setCountry_name(!object.isNull("country_name") ? object.getString("country_name") : "");
                                                if (object.has("country_flag"))
                                                    country.setCountry_flag(!object.isNull("country_flag") ? object.getString("country_flag") : "");



                                                for (int j = 0; j < league_list_array.length(); j++) {
                                                    Country.League league=new Country.League();
                                                    JSONObject league_list_object = league_list_array.getJSONObject(j);

                                                    if (league_list_object.has("league_id"))
                                                        league.setLeague_id(!league_list_object.isNull("league_id") ? league_list_object.getInt("league_id") : 0);
                                                    if (league_list_object.has("country_id"))
                                                        league.setCountry_id(!league_list_object.isNull("country_id") ? league_list_object.getInt("country_id") : 0);
                                                    if (league_list_object.has("league_name"))
                                                        league.setLeague_name(!league_list_object.isNull("league_name") ? league_list_object.getString("league_name") : "");
                                                    if (league_list_object.has("league_flag"))
                                                        league.setLeague_flag(!league_list_object.isNull("league_flag") ? league_list_object.getString("league_flag") : "");
                                                    if (league_list_object.has("is_favorite"))
                                                        league.setIs_favorite(!league_list_object.isNull("is_favorite") ? league_list_object.getInt("is_favorite") : 0);

                                                    if (league_list_object.has("is_selected")){
                                                        league.setIs_selected(!league_list_object.isNull("is_selected") ? league_list_object.getInt("is_selected") : 0);
                                                        if (league_list_object.getInt("is_selected")==1)
                                                        {
                                                            tempLeagueList.add(league);
                                                        }

                                                    }

                                                    leagueArrayList.add(league);
                                                }

                                                country.setLeagueArrayList(leagueArrayList);
                                                countriesLeagueList.add(country);
                                                mLeagues.addAll(countriesLeagueList);

                                            }
                                        }
                                        binding.countryLayout.setVisibility(View.VISIBLE);

                                       allLeagueAdapter = new FilterableAllLeagueAdapter(mContext,countriesLeagueList,NewFilteredActivity.this );
                                        binding.expListView.setAdapter(allLeagueAdapter);
                                       // setListViewHeight(binding.expListView);

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
