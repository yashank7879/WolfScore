package com.wolfscore.matches.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;

import com.wolfscore.databinding.FragmentTableBinding;
import com.wolfscore.matches.adapter.TableAdapter;

import com.wolfscore.matches.modal.TableDTO;

import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_MATCH_TABLE;

/**
 * Created by mindiii on 22/2/19.
 */

public class TableFragment extends Fragment {
    FragmentTableBinding binding;
    TableAdapter tableAdapter;
   // ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<TableDTO> tableDTOS=new ArrayList<>();
    HashMap<String, ArrayList<TableDTO>> tableHashMap=new HashMap<String, ArrayList<TableDTO>>();
 //   private ProgressDialog progressDialog;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_table, container, false);
        initialise();
        return binding.getRoot();
    }

    private void initialise() {
      //  progressDialog=new ProgressDialog(context);
            if (tableHashMap.size()>0)
            {
                tableAdapter = new TableAdapter(getActivity(), tableHashMap);
                binding.expListView.setAdapter(tableAdapter);

            }
        else {
                getStatsData();

            }
            binding.expListView.setGroupIndicator(null);
        // Setting List Adapter to Expandable ListView:-
        binding.expListView.setAdapter(tableAdapter);
        //Setting OnChildClick Listener in Expandable ListView:-
/*
        binding.expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });
*/
    }


    private void getStatsData() {
        if (Constant.isNetworkAvailable(context, binding.mainLayout)) {
          //  progressDialog.show();
            AndroidNetworking.get(BASE_URL + GET_MATCH_TABLE)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(context, PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("season_id", ""+ AboutMatchActivity.aboutMatchActivity.matches.getMatchHeader().getSeason_id())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
/*
                                if (progressDialog!=null&&progressDialog.isShowing())
                                    progressDialog.dismiss();
*/
                                String status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                  //  Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

                                        JSONObject data_obj= response.getJSONObject("data");
                                    JSONArray data_array=new JSONArray();
                                    if (data_obj.has("data"))
                                           data_array= data_obj.getJSONArray("data");

/*
                                    if (object.has("muscels_image")) {
                                        musclesDTO.setImage(!object.isNull("muscels_image") ? object.getString("muscels_image") : "");
                                    }
*/
                                    if (data_array.length()>0) {
                                        for (int i = 0; i < data_array.length(); i++) {
                                            JSONObject object = data_array.getJSONObject(i);
                                             String group_type= object.getString("name");
                                              tableDTOS.clear();

                                                  JSONObject standings_obj = object.getJSONObject("standings");
                                                 JSONArray standing_array=  standings_obj.getJSONArray("data");
                                              if (standing_array.length()>0) {
                                                 for (int j = 0; j < standing_array.length(); j++) {
                                                     TableDTO tableDTO=new TableDTO();

                                                    JSONObject standing_object = standing_array.getJSONObject(j);
                                                     JSONObject json_overall=null;
                                                     JSONObject json_total = null;
                                                     JSONObject json_team=null,json_team_data=null;

                                                     if (standing_object.has("overall")) {
                                                         json_overall=standing_object.getJSONObject("overall");
                                                         if (json_overall.has("games_played"))
                                                             tableDTO.setGames_played(!json_overall.isNull("games_played") ? json_overall.getInt("games_played") : 0);
                                                         if (json_overall.has("draw"))
                                                             tableDTO.setDraw(!json_overall.isNull("draw") ? json_overall.getInt("draw") : 0);
                                                         if (json_overall.has("won"))
                                                             tableDTO.setWon(!json_overall.isNull("won") ? json_overall.getInt("won") : 0);
                                                         if (json_overall.has("lost"))
                                                             tableDTO.setLost(!json_overall.isNull("lost") ? json_overall.getInt("lost") : 0);
                                                         if (json_overall.has("goals_scored"))
                                                             tableDTO.setGoals_scored(!json_overall.isNull("goals_scored") ? json_overall.getInt("goals_scored") : 0);
                                                         if (json_overall.has("goals_against"))
                                                             tableDTO.setGoals_against(!json_overall.isNull("goals_against") ? json_overall.getInt("goals_against") : 0);
                                                     }

                                                     if (standing_object.has("total")) {
                                                         json_total = standing_object.getJSONObject("total");
                                                         if (json_total.has("goal_difference"))
                                                             tableDTO.setGoal_difference(!json_total.isNull("goal_difference") ? json_total.getInt("goal_difference") : 0);
                                                         if (json_total.has("points"))
                                                             tableDTO.setPoints(!json_total.isNull("points") ? json_total.getInt("points") : 0);

                                                     }
                                                     if (standing_object.has("team")) {
                                                         json_team = standing_object.getJSONObject("team");
                                                         if (json_team.has("data")) {
                                                             json_team_data = json_team.getJSONObject("data");
                                                             if (json_team_data.has("logo_path"))
                                                                 tableDTO.setLogo_path(!json_team_data.isNull("logo_path") ? json_team_data.getString("logo_path") : "");
                                                         }
                                                     }

                                                     if (standing_object.has("team_name"))
                                                         tableDTO.setTeam_name(!standing_object.isNull("team_name") ? standing_object.getString("team_name") : "");


                                                     tableDTOS.add(tableDTO);


                                                     // Toast.makeText(context, "" + games_played, Toast.LENGTH_SHORT).show();

                                                 }
                                            }
                                            tableHashMap.put(group_type,tableDTOS);

                                        }
                                        tableAdapter = new TableAdapter(getActivity(), tableHashMap);
                                        binding.expListView.setAdapter(tableAdapter);
                                        // Setting List Adapter to Expandable ListView:-
                                       // tableAdapter.notifyDataSetChanged();
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
/*
                            if (progressDialog!=null&&progressDialog.isShowing())
                                progressDialog.dismiss();
*/
                        }
                    });
        }

    }

}
