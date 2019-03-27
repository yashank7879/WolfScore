package com.wolfscore.matches.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.activity.ActivityMatchDetail;
import com.wolfscore.activity.HomeActivity;
import com.wolfscore.activity.NewFilteredActivity;
import com.wolfscore.matches.adapter.StickyHeaderAdapter;
import com.wolfscore.matches.modal.LocalTeam;
import com.wolfscore.matches.modal.MatchHeader;
import com.wolfscore.matches.modal.Matches;
import com.wolfscore.matches.modal.Score;
import com.wolfscore.matches.modal.Time;
import com.wolfscore.matches.modal.VisitorTeam;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.pagination.EndlessScrollListener;
import com.wolfscore.responce.CountryDto;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_FIXTURES;

/**
 * Created by mindiii on 4/2/19.
 */

public class TodayFragment  extends Fragment {
    private ProgressDialog progressDialog;
    StickyHeaderAdapter adapter;
    public StickyListHeadersListView stickyList;
    int page=1;
    String league_id="",onGoing="",byTime="";
    TextView my_matches,all_matches,ongoing,by_time,show_all,no_data_txt;
    int total_pages=1;
    private boolean isMyMatch=false,isOngoing=true,isBytime=true;
    public ArrayList<Matches> MatchesArrayList=new ArrayList<>();
    private LinearLayout no_data;
    Context context;
    View rootView;
    private boolean isVisible=false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_yesterday_list, null);
        initialise(rootView);
        if (HomeActivity.homeActivity.event!=null&&HomeActivity.homeActivity.event.getLeague_id()
                !=null&&!HomeActivity.homeActivity.event.getLeague_id().isEmpty()
               ){
            MatchesArrayList.clear();
            HomeActivity.homeActivity.league_id= HomeActivity.homeActivity.event.getLeague_id();
            HomeActivity.homeActivity.todayMatchesArrayList.clear();
            HomeActivity.homeActivity.my_todayMatchesArrayList.clear();
            page=1;
            getMatchData(page);
           /* if (!HomeActivity.homeActivity.league_id.isEmpty()&&HomeActivity.homeActivity.league_id.equalsIgnoreCase(HomeActivity.homeActivity.event.getLeague_id())){
                HomeActivity.homeActivity.league_id= HomeActivity.homeActivity.event.getLeague_id();

                MatchesArrayList.clear();
              if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("my")){
                  MatchesArrayList.addAll(HomeActivity.homeActivity.my_todayMatchesArrayList);

              }else {
                  MatchesArrayList.addAll(HomeActivity.homeActivity.todayMatchesArrayList);
              }

                adapter.notifyDataSetChanged();
            }
            else {
                MatchesArrayList.clear();
                HomeActivity.homeActivity.league_id= HomeActivity.homeActivity.event.getLeague_id();
                HomeActivity.homeActivity.todayMatchesArrayList.clear();
                HomeActivity.homeActivity.my_todayMatchesArrayList.clear();
                page=1;
                getMatchData(page);
            }*/
        }
        else {
         //  if (!isVisible) {
               page = 1;
               getMatchData(page);
           //    isVisible=true;
         //  }
         }

        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (rootView != null){
                if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("my")) {
                    my_matches.setBackgroundColor(getResources().getColor(R.color.list_item_bg));
                    my_matches.setTextColor(getResources().getColor(R.color.active_text));
                    isMyMatch=false;
                }
                else {
                    my_matches.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                    my_matches.setTextColor(getResources().getColor(R.color.inactive_txt));
                    isMyMatch=true;
                }
                if (HomeActivity.homeActivity.byTime.equalsIgnoreCase("time")) {
                    by_time.setBackgroundColor(getResources().getColor(R.color.list_item_bg));
                    by_time.setTextColor(getResources().getColor(R.color.active_text));
                }else {
                    by_time.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                    by_time.setTextColor(getResources().getColor(R.color.inactive_txt));

                }
               /* if (isVisible) {
                    page = 1;
                    MatchesArrayList.clear();
                    getMatchData(page);
                }*/

            }

        }

    }

    private void initialise(View rootView)
    {
        stickyList = (StickyListHeadersListView)rootView.findViewById(R.id.list);
        my_matches = (TextView) rootView.findViewById(R.id.my_matches);
        all_matches = (TextView) rootView.findViewById(R.id.all_matches);
        ongoing = (TextView) rootView.findViewById(R.id.ongoing);
        by_time = (TextView) rootView.findViewById(R.id.by_time);
        no_data = (LinearLayout) rootView.findViewById(R.id.no_data);
        show_all = (TextView) rootView.findViewById(R.id.show_all);
        no_data_txt = (TextView) rootView.findViewById(R.id.no_data_txt);

        if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("my")){
            my_matches.setBackgroundColor(getResources().getColor(R.color.list_item_bg));
          //  all_matches.setBackgroundColor(getResources().getColor(R.color.Header_bg));
            my_matches.setTextColor(getResources().getColor(R.color.active_text));
          //  all_matches.setTextColor(getResources().getColor(R.color.inactive_txt));
        }
        else {
            my_matches.setBackgroundColor(getResources().getColor(R.color.Header_bg));
         //   all_matches.setBackgroundColor(getResources().getColor(R.color.list_item_bg));
            my_matches.setTextColor(getResources().getColor(R.color.inactive_txt));
         //   all_matches.setTextColor(getResources().getColor(R.color.active_text));

        }

        adapter = new StickyHeaderAdapter(context,MatchesArrayList);
        stickyList.setAdapter(adapter);
        stickyList.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
            }
        });

        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(context, AboutMatchActivity.class).putExtra("obj",MatchesArrayList.get(position)));
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        stickyList.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {

               if (page<=total_pages)
                getMatchData(page);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity.homeActivity.list_type="all";
                HomeActivity.homeActivity.todayMatchesArrayList.clear();
                page=1;
                MatchesArrayList.clear();
                isMyMatch=true;
                my_matches.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                my_matches.setTextColor(getResources().getColor(R.color.inactive_txt));

                onGoing="";
                isOngoing=true;
                ongoing.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                ongoing.setTextColor(getResources().getColor(R.color.inactive_txt));

                HomeActivity.homeActivity.byTime="";
                isBytime=true;
                by_time.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                by_time.setTextColor(getResources().getColor(R.color.inactive_txt));

                if (show_all.getText().toString().equalsIgnoreCase("Show All Matches"))
                {
                getMatchData(page);
                }
                else {
                    HomeActivity.homeActivity.current_item=1;
                    //  startActivity(new Intent(getActivity(), LeagueFilteringActivity.class));
                    startActivity(new Intent(getActivity(), NewFilteredActivity.class));
                }

            }
        });
        my_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               my_matches.setEnabled(false);
                all_matches.setEnabled(true);


                if (isMyMatch){
                HomeActivity.homeActivity.list_type="my";
                isMyMatch=false;
                    my_matches.setBackgroundColor(getResources().getColor(R.color.list_item_bg));
                    my_matches.setTextColor(getResources().getColor(R.color.active_text));
                }
                else{
                    HomeActivity.homeActivity.list_type="all";
                    isMyMatch=true;
                    my_matches.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                    my_matches.setTextColor(getResources().getColor(R.color.inactive_txt));
                }
                page=1;
                MatchesArrayList.clear();

                    getMatchData(page);
            }
        });

        ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ongoing.setEnabled(false);

                if (isOngoing){
                    onGoing="ongoing";
                    isOngoing=false;
                    ongoing.setBackgroundColor(getResources().getColor(R.color.list_item_bg));
                    ongoing.setTextColor(getResources().getColor(R.color.active_text));

                }
                else{
                    onGoing="";
                    isOngoing=true;
                    ongoing.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                    ongoing.setTextColor(getResources().getColor(R.color.inactive_txt));
                }

                page=1;
                MatchesArrayList.clear();

                getMatchData(page);

            }
        });
        by_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                by_time.setEnabled(false);
                if (isBytime){
                    HomeActivity.homeActivity.byTime="time";
                    isBytime=false;
                    by_time.setBackgroundColor(getResources().getColor(R.color.list_item_bg));
                    by_time.setTextColor(getResources().getColor(R.color.active_text));
                }
                else{
                    HomeActivity.homeActivity.byTime="";
                    isBytime=true;
                    by_time.setBackgroundColor(getResources().getColor(R.color.Header_bg));
                    by_time.setTextColor(getResources().getColor(R.color.inactive_txt));
                }
                page=1;
                MatchesArrayList.clear();
                getMatchData(page);

            }
        });

    }

    private void bindData( ArrayList<Matches> MatchesArrayList) {
        Collections.sort(MatchesArrayList, new Comparator<Matches>() {
            public int compare(Matches matches, Matches nextMatches) {
                return matches.getHeaderId() - nextMatches.getHeaderId();
            }
        });

    }

    private void getMatchData(int page) {
        Log.d("auttoken", "getMatchData: auttoken...."+PreferenceConnector.readString(context, PreferenceConnector.AUTH_TOKEN,""));
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        if (Constant.isNetworkAvailable(context, stickyList)) {
            //  http://dev.wolfscore.info/api_v1/matches/get_fixtures?type=today&page=1&date=&team_id=&league_id=""
            AndroidNetworking.get(BASE_URL + GET_FIXTURES)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(context, PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("type", "today")
                    .addQueryParameter("page", page+"")
                    .addQueryParameter("date", "")
                    .addQueryParameter("team_id", "")
                    .addQueryParameter("list_type", HomeActivity.homeActivity.list_type)
                    .addQueryParameter("league_id", HomeActivity.homeActivity.league_id)
                    .addQueryParameter("ongoing", onGoing)
                    .addQueryParameter("sort_by",HomeActivity.homeActivity.byTime)
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
                               //     matchesArrayList.clear();
                                 //   Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                                    JSONObject data_obj= response.getJSONObject("data");
                                    JSONArray data_array= data_obj.getJSONArray("data");
                                    if (data_array.length()>0) {
                                        for (int i = 0; i < data_array.length(); i++) {
                                            JSONObject object=  data_array.getJSONObject(i);
                                            JSONObject league_obj= object.getJSONObject("league");
                                            JSONObject league_data_obj=league_obj.getJSONObject("data");
                                            league_data_obj.getInt("id");
                                            league_data_obj.getString("name");

                                         //   MatchHeader matchHeader = new MatchHeader( league_data_obj.getInt("id"), league_data_obj.getString("name"));

                                            MatchHeader matchHeader = new MatchHeader();
                                            matchHeader.setId(league_data_obj.getInt("id"));
                                            matchHeader.setName(league_data_obj.getString("name"));
                                            matchHeader.setMatch_id(object.getInt("id"));
                                            matchHeader.setSeason_id(object.getInt("season_id"));

                                            int countryId = league_data_obj.getInt("country_id");


                                         //   matchHeader.setSeason_id(object.getInt("country_id"));
                                            JSONObject  country_obj=null,country_data=null;

                                           ArrayList<CountryDto> countryDtos= PreferenceConnector.getCountryList(context);
                                            for (int j = 0; j < countryDtos.size(); j++) {
                                                if (String.valueOf(countryId).equalsIgnoreCase(countryDtos.get(j).getCountry_id())){
                                                    matchHeader.setCountryName(countryDtos.get(j).getCountry_name());
                                                    break;
                                                }

                                            }


                                            JSONObject localTeam_obj=object.getJSONObject("localTeam");
                                            JSONObject localteam_data=localTeam_obj.getJSONObject("data");
                                            LocalTeam localTeam=new LocalTeam();
                                            localTeam.setId(localteam_data.getInt("id"));
                                            localTeam.setName(localteam_data.getString("name"));
                                            localTeam.setLogo_path(localteam_data.getString("logo_path"));

                                            VisitorTeam visitorTeam=new VisitorTeam();
                                            JSONObject visitorTeam_obj=  object.getJSONObject("visitorTeam");
                                            JSONObject visitorTeam_data=visitorTeam_obj.getJSONObject("data");
                                            visitorTeam.setName(visitorTeam_data.getString("name"));
                                            visitorTeam.setId(visitorTeam_data.getInt("id"));
                                            visitorTeam.setLogo_path(visitorTeam_data.getString("logo_path"));

                                            Score score=new Score();
                                            JSONObject scores_obj=  object.getJSONObject("scores");
                                            score.setLocalteam_score(scores_obj.getInt("localteam_score"));
                                            score.setVisitorteam_score(scores_obj.getInt("visitorteam_score"));

                                            Time time=new Time();
                                            JSONObject time_obj=  object.getJSONObject("time");
                                            JSONObject starting_at_data=time_obj.getJSONObject("starting_at");
                                            time.setStatus(time_obj.getString("status"));
                                            time.setDate(starting_at_data.getString("date"));
                                            time.setTime(starting_at_data.getString("time"));

                                            Matches matches=new Matches(matchHeader,localTeam,visitorTeam,time,score);
                                            // Matches matches = new Matches(localTeam, matchHeader);

                                            if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("my"))
                                            {
                                             HomeActivity.homeActivity.my_todayMatchesArrayList.add(matches);
                                                MatchesArrayList.add(matches);
                                               // MatchesArrayList.addAll(HomeActivity.homeActivity.my_todayMatchesArrayList);
                                            }
                                            else {
                                                HomeActivity.homeActivity.todayMatchesArrayList.add(matches);
                                                MatchesArrayList.add(matches);
                                                //  MatchesArrayList.addAll(HomeActivity.homeActivity.todayMatchesArrayList);
                                            }


                                        }
                                        //  addRecyclerHeaders();
                                        bindData(MatchesArrayList);
                                        if (HomeActivity.homeActivity.todayMatchesArrayList.size()>0)
                                        bindData(HomeActivity.homeActivity.todayMatchesArrayList);
                                        if (HomeActivity.homeActivity.my_todayMatchesArrayList.size()>0)
                                        bindData(HomeActivity.homeActivity.my_todayMatchesArrayList);
                                        no_data.setVisibility(View.GONE);
                                        adapter.notifyDataSetChanged();
                                        my_matches.setEnabled(true);
                                        ongoing.setEnabled(true);
                                        by_time.setEnabled(true);

                                        JSONObject meta_obj=  data_obj.getJSONObject("meta");
                                     JSONObject pagination_obj=   meta_obj.getJSONObject("pagination");

                                      total_pages=pagination_obj.getInt("total_pages");
                                        int current_page=   pagination_obj.getInt("current_page");

                                    }

                                    else {
                                        //todo for empty array
                                        MatchesArrayList.clear();
                                        adapter.notifyDataSetChanged();
                                        my_matches.setEnabled(true);
                                        ongoing.setEnabled(true);
                                        by_time.setEnabled(true);

                                        no_data.setVisibility(View.VISIBLE);
                                        if (onGoing.equalsIgnoreCase("ongoing")&&
                                           HomeActivity.homeActivity.list_type.equalsIgnoreCase("my"))
                                        {
                                            no_data_txt.setText("No my matches that are ongoing");
                                            show_all.setText(getActivity().getResources().getString(R.string.show_all));
                                        }
                                        else if (onGoing.equalsIgnoreCase("ongoing")) {
                                            no_data_txt.setText("No ongoing matches");
                                            show_all.setText(getActivity().getResources().getString(R.string.show_all));
                                        }

                                        else if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("all")){
                                            no_data_txt.setText("No matches scheduled");
                                            show_all.setText("FILTER TOURNAMENTS");
                                        }
                                        else if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("my")) {
                                            no_data_txt.setText("No Favorite playing");
                                            show_all.setText(getActivity().getResources().getString(R.string.show_all));

                                        }
                                        else {
                                            no_data_txt.setText("No matches scheduled");
                                            show_all.setText(getActivity().getResources().getString(R.string.show_all));
                                        }
                                            //   Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                else {

                                    MatchesArrayList.clear();
                                    adapter.notifyDataSetChanged();
                                    my_matches.setEnabled(true);
                                    ongoing.setEnabled(true);
                                    by_time.setEnabled(true);
                                    no_data.setVisibility(View.VISIBLE);
                                    if (onGoing.equalsIgnoreCase("ongoing")&&
                                            HomeActivity.homeActivity.list_type.equalsIgnoreCase("my"))
                                    {
                                        no_data_txt.setText("No my matches that are ongoing");
                                        show_all.setText(getActivity().getResources().getString(R.string.show_all));

                                    }

                                    else if (onGoing.equalsIgnoreCase("ongoing")) {
                                        no_data_txt.setText("No ongoing matches");
                                        show_all.setText(getActivity().getResources().getString(R.string.show_all));
                                    }
                                    else if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("all")){
                                        no_data_txt.setText("No matches scheduled");
                                        show_all.setText("FILTER TOURNAMENTS");
                                    }

                                    else if (HomeActivity.homeActivity.list_type.equalsIgnoreCase("my")) {
                                        no_data_txt.setText("No Favorite playing");
                                        show_all.setText(getActivity().getResources().getString(R.string.show_all));
                                    }
                                    else {
                                        no_data_txt.setText("No matches scheduled");
                                        show_all.setText(getActivity().getResources().getString(R.string.show_all));
                                    }
                                        //    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
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

}
