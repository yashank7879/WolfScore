package com.wolfscore.league.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.activity.HomeActivity;
import com.wolfscore.league.fragments.adapter.LeagueMatchAdapter;
import com.wolfscore.matches.adapter.StickyHeaderAdapter;
import com.wolfscore.matches.modal.LocalTeam;
import com.wolfscore.matches.modal.MatchHeader;
import com.wolfscore.matches.modal.Matches;
import com.wolfscore.matches.modal.Score;
import com.wolfscore.matches.modal.Time;
import com.wolfscore.matches.modal.VisitorTeam;
import com.wolfscore.pagination.EndlessScrollListener;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_FIXTURES;
import static com.wolfscore.utils.ApiCollection.GET_LEAGUE_MATCHES;

/**
 * Created by mindiii on 6/3/19.
 */

public class LeagueMatchFragment extends Fragment {
    //  public ArrayList<Matches> matchesArrayList = new ArrayList<>();

    private ProgressDialog progressDialog;
    LeagueMatchAdapter adapter;
    public StickyListHeadersListView stickyList;
    public ArrayList<Matches> leagueMatchesArrayList=new ArrayList<>();
    int page=1;
    String league_id="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yesterday_list, null);
        initialise(rootView);
        getMatchData(page);
        return rootView;
    }

    private void initialise(View rootView)
    {

        // matchesArrayList.clear();
        stickyList = (StickyListHeadersListView)rootView.findViewById(R.id.list);

        adapter = new LeagueMatchAdapter(getActivity(),leagueMatchesArrayList);
        stickyList.setAdapter(adapter);
        stickyList.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                // Toast.makeText(getActivity(),"Header", Toast.LENGTH_SHORT).show();
            }
        });

        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  startActivity(new Intent(getActivity(), AboutMatchActivity.class).putExtra("obj",HomeActivity.homeActivity.todayMatchesArrayList.get(position)));
            //    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        stickyList.setOnScrollListener(new EndlessScrollListener() {
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
        Collections.sort(leagueMatchesArrayList, new Comparator<Matches>() {
            public int compare(Matches matches, Matches nextMatches) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                try {
                    Date d1 = sdf.parse(matches.getTime().getDate());
                    Date d2 = sdf.parse(nextMatches.getTime().getDate());
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return Integer.parseInt(matches.getTime().getDate()) - Integer.parseInt(nextMatches.getTime().getDate());
                }
            }
        });

    }
    private void sortByDate()
    {
        Collections.sort(leagueMatchesArrayList, new Comparator<Matches>() {
            public int compare(Matches o1, Matches o2) {
                return o1.getTime().getDate().compareTo(o2.getTime().getDate());
            }
        });
    }









    private void getMatchData(int page) {
        Log.d("auttoken", "getMatchData: auttoken...."+ PreferenceConnector.readString(getActivity(), PreferenceConnector.AUTH_TOKEN,""));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        if (Constant.isNetworkAvailable(getActivity(), stickyList)) {
            //  http://dev.wolfscore.info/api_v1/matches/get_fixtures?type=today&page=1&date=&team_id=&league_id=""
            AndroidNetworking.get(BASE_URL + GET_LEAGUE_MATCHES)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(getActivity(), PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("page", page+"")
                    .addQueryParameter("league_id","1283")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (progressDialog!=null){
                                    if (progressDialog.isShowing())
                                    progressDialog.dismiss();}
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

                                            /*MatchHeader matchHeader = new MatchHeader();
                                            matchHeader.setId(league_data_obj.getInt("id"));
                                            matchHeader.setName(league_data_obj.getString("name"));
                                            matchHeader.setMatch_id(object.getInt("id"));
                                            matchHeader.setSeason_id(object.getInt("season_id"));
                                            JSONObject  country_obj=null,country_data=null;

                                            if (league_data_obj.has("country")) {
                                                country_obj = league_data_obj.getJSONObject("country");
                                                if (country_obj.has("data")){
                                                    country_data=country_obj.getJSONObject("data");
                                                    if (country_data.has("name")){
                                                        matchHeader.setCountryName(!country_data.isNull("name") ? country_data.getString("name") : "");
                                                    }
                                                }
                                            }*/

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

                                            MatchHeader headerByDate = new MatchHeader();
                                            headerByDate.setId(Integer.parseInt(time.getDate().replaceAll("-","")));
                                            headerByDate.setName(time.getDate());


                                            Matches matches=new Matches(headerByDate,localTeam,visitorTeam,time,score);
                                            // Matches matches = new Matches(localTeam, matchHeader);
                                            leagueMatchesArrayList.add(matches);

                                        }
                                        //  addRecyclerHeaders();
                                       bindData();
                                       // sortByDate();
                                        adapter.notifyDataSetChanged();
                                        JSONObject meta_obj=  data_obj.getJSONObject("meta");
                                        JSONObject pagination_obj=   meta_obj.getJSONObject("pagination");
                                        int current_page=   pagination_obj.getInt("current_page");
                                        int total_pages=pagination_obj.getInt("total_pages");
                                    /* if (current_page<total_pages){
                                         page=current_page+1;
                                        // getMatchData();
                                     }*/
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
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
