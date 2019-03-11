package com.wolfscore.matches.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mikesu.horizontalexpcalendar.HorizontalExpCalendar;
import com.mikesu.horizontalexpcalendar.common.Config;
import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.activity.HomeActivity;
import com.wolfscore.databinding.FragmentSelectedDateLeagueBinding;
import com.wolfscore.matches.adapter.StickyHeaderAdapter;
import com.wolfscore.matches.modal.LocalTeam;
import com.wolfscore.matches.modal.MatchHeader;
import com.wolfscore.matches.modal.Matches;
import com.wolfscore.matches.modal.Score;
import com.wolfscore.matches.modal.Time;
import com.wolfscore.matches.modal.VisitorTeam;
import com.wolfscore.pagination.EndlessScrollListener;
import com.wolfscore.responce.CountryDto;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_SEARCH_MATCHES;

/**
 * Created by mindiii on 6/2/19.
 */

public class SelectedDateLeagueFragment extends Fragment implements View.OnClickListener {
    FragmentSelectedDateLeagueBinding binding;
    ArrayList<Matches> matchesArrayList = new ArrayList<>();

    private ProgressDialog progressDialog;
    StickyHeaderAdapter adapter;
    StickyListHeadersListView stickyList;
    int page = 1;
    String date = "";
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat currentDateFormat = new SimpleDateFormat("dd");
    Calendar cal_today = Calendar.getInstance();
    String today_date="";
    private Context context;

    private HorizontalExpCalendar horizontalExpCalendar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected_date_league, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        today_date= dateFormat.format(cal_today.getTime());
        Bundle bundle = this.getArguments();
        date = bundle.getString("date");
        DateTime dateTime = (DateTime) bundle.getSerializable("datetime");
        int month = dateTime.getMonthOfYear();
        String month_name = "";
        if (month==1) month_name = "January";
        if (month == 2) month_name = "Fabruary";
        if (month == 3) month_name = "March";
        if (month == 4) month_name = "April";
        if (month == 5) month_name = "May";
        if (month == 6) month_name = "June";
        if (month == 7) month_name = "July";
        if (month == 8) month_name = "August";
        if (month == 9) month_name = "September";
        if (month == 10) month_name = "October";
        if (month == 11) month_name = "November";
        if (month == 12) month_name = "December";
        binding.date.setText(month_name+ " " + dateTime.getDayOfMonth() + "," + dateTime.getYear());

        String date=currentDateFormat.format(cal_today.getTime());
        binding.currentdate.setText(date);
        getMatchData(page);
        initialise(view);

    }

    private void initialise(View rootView) {
        matchesArrayList.clear();
        binding.ivBack.setOnClickListener(this);
        binding.calanderIcon.setOnClickListener(this);
        binding.clLayout.setOnClickListener(this);
        binding.topLayout.setOnClickListener(this);

        //  stickyList = (StickyListHeadersListView)rootView.findViewById(R.id.list);

        adapter = new StickyHeaderAdapter(context, matchesArrayList);
        binding.list.setAdapter(adapter);
        binding.list.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
              //  Toast.makeText(context, "Header", Toast.LENGTH_SHORT).show();
            }
        });

        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(context, AboutMatchActivity.class)
               .putExtra("obj",matchesArrayList.get(position)));
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        binding.list.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                getMatchData(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

    }

    private void bindData() {
        Collections.sort(matchesArrayList, new Comparator<Matches>() {
            public int compare(Matches matches, Matches nextmatches) {
                return matches.getHeaderId() - nextmatches.getHeaderId();
            }
        });

    }

    private void getMatchData(int page) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        if (Constant.isNetworkAvailable(context, stickyList)) {
            //  http://dev.wolfscore.info/api_v1/matches/get_fixtures?type=today&page=1&date=&team_id=&league_id=""
            AndroidNetworking.get(BASE_URL + GET_SEARCH_MATCHES)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(context, PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("type", "date")
                    .addQueryParameter("page", page + "")
                    .addQueryParameter("date", date)
                    .addQueryParameter("team_id", "")
                    .addQueryParameter("league_id", "")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (progressDialog!=null&&progressDialog.isShowing())
                                    progressDialog.dismiss();
                                String status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                    //     matchesArrayList.clvalue = {JSONObject@5233} "{"subscription":{"started_at":{"date":"2018-12-19 08:01:22.000000","timezone_type":3,"timezone":"UTC"},"trial_ends_at":{"date":"2019-01-02 08:01:14.000000","timezone_type":3,"timezone":"UTC"},"ends_at":null},"plan":{"name":"Pro Plan Standard","price":"175.00","request_limit":"2000,60"},"sports":[{"id":1,"name":"Soccer","current":true}],"pagination":{"total":227,"count":100,"per_page":100,"current_page":1,"total_pages":3,"links":{"next":"https:\/\/soccer.sportmonks.com\/api\/v2.0\/fixtures\/date\/2019-02-15?page=2"}}}"ear();
                                  //  Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                    JSONObject data_obj = response.getJSONObject("data");
                                    JSONArray data_array = data_obj.getJSONArray("data");
                                    if (data_array.length() > 0) {
                                        for (int i = 0; i < data_array.length(); i++) {
                                            JSONObject object = data_array.getJSONObject(i);
                                            JSONObject league_obj = object.getJSONObject("league");
                                            JSONObject league_data_obj = league_obj.getJSONObject("data");
                                            league_data_obj.getInt("id");
                                            league_data_obj.getString("name");

                                          //  MatchHeader matchHeader = new MatchHeader(league_data_obj.getInt("id"), league_data_obj.getString("name"));

                                            MatchHeader matchHeader = new MatchHeader();
                                            matchHeader.setId(league_data_obj.getInt("id"));
                                            matchHeader.setName(league_data_obj.getString("name"));
                                            matchHeader.setMatch_id(object.getInt("id"));
                                            matchHeader.setSeason_id(object.getInt("season_id"));

                                            JSONObject  country_obj=null,country_data=null;
                                            int countryId = league_data_obj.getInt("country_id");


                                            //   matchHeader.setSeason_id(object.getInt("country_id"));

                                            ArrayList<CountryDto> countryDtos= PreferenceConnector.getCountryList(getActivity());
                                            for (int j = 0; j < countryDtos.size(); j++) {
                                                if (String.valueOf(countryId).equalsIgnoreCase(countryDtos.get(j).getCountry_id())){
                                                    matchHeader.setCountryName(countryDtos.get(j).getCountry_name());
                                                    break;
                                                }

                                            }

/*
                                            if (league_data_obj.has("country")) {
                                                country_obj = league_data_obj.getJSONObject("country");
                                                if (country_obj.has("data")){
                                                    country_data=country_obj.getJSONObject("data");
                                                    if (country_data.has("name")){
                                                        matchHeader.setCountryName(!country_data.isNull("name") ? country_data.getString("name") : "");
                                                    }
                                                }
                                            }
*/


                                            JSONObject localTeam_obj = object.getJSONObject("localTeam");
                                            JSONObject localteam_data = localTeam_obj.getJSONObject("data");
                                            LocalTeam localTeam = new LocalTeam();
                                            localTeam.setId(localteam_data.getInt("id"));
                                            localTeam.setName(localteam_data.getString("name"));
                                            localTeam.setLogo_path(localteam_data.getString("logo_path"));

                                            VisitorTeam visitorTeam = new VisitorTeam();
                                            JSONObject visitorTeam_obj = object.getJSONObject("visitorTeam");
                                            JSONObject visitorTeam_data = visitorTeam_obj.getJSONObject("data");
                                            visitorTeam.setName(visitorTeam_data.getString("name"));
                                            visitorTeam.setId(visitorTeam_data.getInt("id"));
                                            visitorTeam.setLogo_path(visitorTeam_data.getString("logo_path"));

                                            Score score = new Score();
                                            JSONObject scores_obj = object.getJSONObject("scores");
                                            score.setLocalteam_score(scores_obj.getInt("localteam_score"));
                                            score.setVisitorteam_score(scores_obj.getInt("visitorteam_score"));

                                            Time time = new Time();
                                            JSONObject time_obj = object.getJSONObject("time");
                                            JSONObject starting_at_data = time_obj.getJSONObject("starting_at");
                                            time.setStatus(time_obj.getString("status"));
                                            time.setDate(starting_at_data.getString("date"));
                                            time.setTime(starting_at_data.getString("time"));

                                            Matches matches = new Matches(matchHeader, localTeam, visitorTeam, time, score);
                                            // Matches matches = new Matches(localTeam, matchHeader);
                                            matchesArrayList.add(matches);

                                        }
                                        //  addRecyclerHeaders();
                                        bindData();
                                        adapter.notifyDataSetChanged();
                                        JSONObject meta_obj = data_obj.getJSONObject("meta");
                                        JSONObject pagination_obj = meta_obj.getJSONObject("pagination");
                                        int current_page = pagination_obj.getInt("current_page");
                                        int total_pages = pagination_obj.getInt("total_pages");
/*
                                        if (current_page < total_pages) {
                                            page = current_page + 1;
                                            getMatchData();
                                        }
*/
                                    }

                                } else {
                                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            if (progressDialog!=null&&progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().getSupportFragmentManager().popBackStack();
                HomeActivity.homeActivity.setSelectedDate();
                break;
            case R.id.calanderIcon:
                openCalender();
            case R.id.clLayout:
                break;
            case R.id.top_layout:
                break;
            default:
        }
    }

    void openCalender(){
        binding.clLayout.setVisibility(View.VISIBLE);
      //  horizontalExpCalendar = (HorizontalExpCalendar)findViewById(R.id.calendar);
        binding.calendar.scrollToDate(new DateTime(), true, true, true);

        TextView btnClose = binding.calendar.findViewById(com.mikesu.horizontalexpcalendar.R.id.close_btn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.clLayout.setVisibility(View.GONE);
                binding.calendar.setGone();
            }
        });
        binding.calendar.setVisible();
        binding.calendar.setHorizontalExpCalListener(new HorizontalExpCalendar.HorizontalExpCalListener() {
            @Override
            public void onCalendarScroll(DateTime dateTime) {
                Log.i("Calender", "onCalendarScroll: " + dateTime.toString());
            }

            @Override
            public void onDateSelected(DateTime dateTime) {
                Log.i("Calender", "onDateSelected: " + dateTime.toString());
                binding.clLayout.setVisibility(View.GONE);
                binding.calendar.setGone();
                matchesArrayList.clear();
                page=1;

                String dateStr = dateTime.toString();
                SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                Date dateObj = null;
                try {
                    dateObj = curFormater.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                date= curFormater.format(dateObj);
                if (date.equalsIgnoreCase(today_date)){
                    getActivity().getSupportFragmentManager().popBackStack();
                    HomeActivity.homeActivity.setSelectedDate();

                }else {
                    int month = dateTime.getMonthOfYear();
                    String month_name = "";
                    if (month==1) month_name = "January";
                    if (month == 2) month_name = "Fabruary";
                    if (month == 3) month_name = "March";
                    if (month == 4) month_name = "April";
                    if (month == 5) month_name = "May";
                    if (month == 6) month_name = "June";
                    if (month == 7) month_name = "July";
                    if (month == 8) month_name = "August";
                    if (month == 9) month_name = "September";
                    if (month == 10) month_name = "October";
                    if (month == 11) month_name = "November";
                    if (month == 12) month_name = "December";
                    binding.date.setText(month_name+ " " + dateTime.getDayOfMonth() + "," + dateTime.getYear());
                    getMatchData(page);
                }

            }

            @Override
            public void onChangeViewPager(Config.ViewPagerType viewPagerType) {
                Log.i("Calender", "onChangeViewPager: " + viewPagerType.name());
            }
        });
    }

}
