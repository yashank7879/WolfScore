package com.wolfscore.matches.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.matches.adapter.StickyHeaderAdapter;
import com.wolfscore.matches.modal.LocalTeam;
import com.wolfscore.matches.modal.MatchHeader;
import com.wolfscore.matches.modal.Matches;
import com.wolfscore.matches.modal.Score;
import com.wolfscore.matches.modal.Time;
import com.wolfscore.matches.modal.VisitorTeam;
import com.wolfscore.model.SelectedLeagueModel;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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

public class TodayFragment extends Fragment {
    ArrayList<Matches> matchesArrayList = new ArrayList<>();

    private ProgressDialog progressDialog;
    StickyHeaderAdapter adapter;
    StickyListHeadersListView stickyList;
    String leagueId = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yesterday_list, null);
        getMatchData();
        initialise(rootView);
        return rootView;
    }

    private void initialise(View rootView) {
        EventBus.getDefault().register(this);


        stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.list);

        adapter = new StickyHeaderAdapter(getActivity(), matchesArrayList);
        stickyList.setAdapter(adapter);
        stickyList.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                Toast.makeText(getActivity(), "Header", Toast.LENGTH_SHORT).show();
            }
        });

        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), AboutMatchActivity.class));
            }
        });
    }

    private void bindData() {
        Collections.sort(matchesArrayList, new Comparator<Matches>() {
            public int compare(Matches Galaxy, Matches nextGalaxy) {
                return Galaxy.getHeaderId() - nextGalaxy.getHeaderId();
            }
        });

    }

    @Subscribe
    public void onEvent(SelectedLeagueModel event) {
        //Log.e( "onEvent: ",event.toString() );
        matchesArrayList.clear();
        adapter.notifyDataSetChanged();
        leagueId = event.toString();
        getMatchData();
        //check if login was successful

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //unregister eventbus here
        EventBus.getDefault().unregister(this);
    }
/*
    EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            if (Constant.isNetworkAvailable(getActivity(), stickyList)) {
                if (page != 1) {
                    progressDialog.show();
                }
               // offset = offset + 20; //load 5 items in recyclerview
                // progressDialog.show();
                getMatchData();

            }
        }
    };
*/


    private void getMatchData() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        if (Constant.isNetworkAvailable(getActivity(), stickyList)) {
            //  http://dev.wolfscore.info/api_v1/matches/get_fixtures?type=today&page=1&date=&team_id=&league_id=""
            AndroidNetworking.get(BASE_URL + GET_FIXTURES)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(getActivity(), PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("type", "today")
                    .addQueryParameter("page", "1")
                    .addQueryParameter("date", "")
                    .addQueryParameter("team_id", "")
                    .addQueryParameter("league_id", leagueId)
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
                                    /*matchesArrayList.clear();*/
                                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                                    JSONObject data_obj = response.getJSONObject("data");
                                    JSONArray data_array = data_obj.getJSONArray("data");
                                    if (data_array.length() > 0) {
                                        for (int i = 0; i < data_array.length(); i++) {
                                            JSONObject object = data_array.getJSONObject(i);
                                            JSONObject league_obj = object.getJSONObject("league");
                                            JSONObject league_data_obj = league_obj.getJSONObject("data");
                                            league_data_obj.getInt("id");
                                            league_data_obj.getString("name");

                                            MatchHeader matchHeader = new MatchHeader(league_data_obj.getInt("id"), league_data_obj.getString("name"));

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
                            progressDialog.dismiss();
                        }
                    });
        }

    }

}
