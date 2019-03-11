package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.aboutMatch.AboutMatchResponce;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.adapter.HeadToHeadAdapter;
import com.wolfscore.adapter.MatchFactAdapter;
import com.wolfscore.databinding.FragmentHeadToHeadBinding;
import com.wolfscore.databinding.FragmentMatchFactsBinding;
import com.wolfscore.matches.modal.LocalTeam;
import com.wolfscore.matches.modal.MatchHeader;
import com.wolfscore.matches.modal.Matches;
import com.wolfscore.matches.modal.Score;
import com.wolfscore.matches.modal.Time;
import com.wolfscore.matches.modal.VisitorTeam;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.HEAD_TO_HEAD;

/**
 * Created by mindiii on 12/2/19.
 */

public class HeadToHeadMatchFragment  extends Fragment {
    FragmentHeadToHeadBinding binding;
    HeadToHeadAdapter adapter;
  //  private ProgressDialog progressDialog;
    int page=1;
    private Context mContext;
    ArrayList<Matches> matchesArrayList=new ArrayList<>();

    public HeadToHeadMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_head_to_head, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
     //   progressDialog = new ProgressDialog(mContext);
        binding.rvlocalTeam.setLayoutManager(layoutManager);
        //   adapter = new LocalTeamAdapter(mContext, teamList, this);
        adapter = new HeadToHeadAdapter(mContext,matchesArrayList);
        binding.rvlocalTeam.setAdapter(adapter);
        if (matchesArrayList!=null&&matchesArrayList.size()>0)
        {
            adapter.notifyDataSetChanged();
        }else
        getLocalTeam(page);


/*
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(mContext, binding.scrollingMainLayout)) {

                        getLocalTeam(page);
                   }
            }
        };
*/


     //  binding.rvlocalTeam.addOnScrollListener(scrollListener);


        return binding.getRoot();
    }

    private void getLocalTeam(int page) {
        if (Constant.isNetworkAvailable(mContext, binding.scrollingMainLayout) ) {
         //   progressDialog.show();
            AndroidNetworking.get(BASE_URL+HEAD_TO_HEAD )
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("team_one_id", ""+AboutMatchActivity.aboutMatchActivity.matches.getLocalTeam().getId())
                    .addQueryParameter("team_two_id",""+AboutMatchActivity.aboutMatchActivity.matches.getVisitorTeam().getId())
                    .addQueryParameter("page",""+page)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                              /*  if (progressDialog!=null)
                                    if (progressDialog.isShowing())
                                progressDialog.dismiss();*/
                                String status = response.getString("status");
                                String message = response.getString("message");
/*
                                if (status.equals("success")) {

                                   ObjectMapper objectMapper = new ObjectMapper();
                                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                                    objectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

                                    AboutMatchResponce emp = objectMapper.readValue(String.valueOf(response), AboutMatchResponce.class);
                                    Toast.makeText(mContext, "" + emp, Toast.LENGTH_SHORT).show();
                               }
*/

                                if (status.equals("success")) {
                                    //     matchesArrayList.clvalue = {JSONObject@5233} "{"subscription":{"started_at":{"date":"2018-12-19 08:01:22.000000","timezone_type":3,"timezone":"UTC"},"trial_ends_at":{"date":"2019-01-02 08:01:14.000000","timezone_type":3,"timezone":"UTC"},"ends_at":null},"plan":{"name":"Pro Plan Standard","price":"175.00","request_limit":"2000,60"},"sports":[{"id":1,"name":"Soccer","current":true}],"pagination":{"total":227,"count":100,"per_page":100,"current_page":1,"total_pages":3,"links":{"next":"https:\/\/soccer.sportmonks.com\/api\/v2.0\/fixtures\/date\/2019-02-15?page=2"}}}"ear();
                                   // Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
                                    JSONObject data_obj = response.getJSONObject("data");
                                    JSONArray data_array = data_obj.getJSONArray("data");
                                    if (data_array.length() > 0) {
                                        for (int i = 0; i < data_array.length(); i++) {
                                            JSONObject object = data_array.getJSONObject(i);
                                            JSONObject league_obj = object.getJSONObject("league");
                                            JSONObject league_data_obj = league_obj.getJSONObject("data");

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

                                        adapter.notifyDataSetChanged();
                                        JSONObject meta_obj = data_obj.getJSONObject("meta");
                                        JSONObject pagination_obj = meta_obj.getJSONObject("pagination");
                                        int current_page = pagination_obj.getInt("current_page");
                                        int total_pages = pagination_obj.getInt("total_pages");

                                    }
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
/*
                            if (progressDialog!=null&&progressDialog.isShowing())
                                progressDialog.dismiss();
*/
                        }
                    });
        }

    }


}
