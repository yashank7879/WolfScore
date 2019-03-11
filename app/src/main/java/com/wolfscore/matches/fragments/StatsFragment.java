package com.wolfscore.matches.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.databinding.FragmentMatchStatsBinding;
import com.wolfscore.matches.adapter.StatsAdapter;
import com.wolfscore.statsModal.StatsResponse;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_MATCH_STATS;

/**
 * Created by mindiii on 19/2/19.
 */

public class StatsFragment  extends Fragment {
    FragmentMatchStatsBinding binding;
    private ProgressDialog progressDialog;
    StatsAdapter adapter;
    StatsResponse statsResponse;
    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_stats, container, false);
        initialise();
        return binding.getRoot();
    }

    private void initialise() {

     //   LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
    //    binding.rvStatsList.setLayoutManager(layoutManager);
        //   adapter = new LocalTeamAdapter(mContext, teamList, this);
    //    adapter = new StatsAdapter(context);
    //    binding.rvStatsList.setAdapter(adapter);
        if (statsResponse!=null){
            setData();
        }
        else
        getStatsData();

    }

    private void getStatsData() {
        if (Constant.isNetworkAvailable(context, binding.mainLayout)) {
           // progressDialog.show();
            AndroidNetworking.get(BASE_URL + GET_MATCH_STATS)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(context, PreferenceConnector.AUTH_TOKEN, ""))
                    .addQueryParameter("match_id", ""+ AboutMatchActivity.aboutMatchActivity.matches.getMatchHeader().getMatch_id())
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
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                                    objectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


                                    statsResponse = objectMapper.readValue(String.valueOf(response), StatsResponse.class);
                                    setData();

                                  //  Toast.makeText(context, "" + statsResponse, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            } catch (JsonMappingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
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

    private void setData()
    {
        if (statsResponse.data.data.stats.data.size()>0) {
            com.wolfscore.statsModal.DataItem L_dataItem,V_dataItem;
           if (statsResponse.data.data.stats.data.get(0).teamId==AboutMatchActivity.aboutMatchActivity.matches.getLocalTeam().getId()){
                L_dataItem = statsResponse.data.data.stats.data.get(0);
                V_dataItem=statsResponse.data.data.stats.data.get(1);
            }
           else {
               L_dataItem = statsResponse.data.data.stats.data.get(1);
               V_dataItem=statsResponse.data.data.stats.data.get(0);
           }
            if (statsResponse.data.data.stats.data.get(0).passes!=null) {
                binding.lAccuratePass.setText("" + L_dataItem.passes.accurate);
                binding.lPassSuccess.setText("" + L_dataItem.passes.percentage + "%");
                binding.vPassSuccess.setText("" + V_dataItem.passes.percentage + "%");
                binding.vAccuratePass.setText("" +V_dataItem.passes.accurate);
                binding.seekBar.setProgress(L_dataItem.possessiontime);
                binding.lProcessTime.setText(""+L_dataItem.possessiontime+"%");
                binding.vProcessTime.setText(""+V_dataItem.possessiontime+"%");
              if (L_dataItem.possessiontime!=0)
                    binding.seekBar.setVisibility(View.VISIBLE);
                else
                    binding.seekBar.setVisibility(View.GONE);
                binding.seekBar.setEnabled(false);
            }
            if (statsResponse.data.data.stats.data.get(0).shots!=null)
            {
                binding.lTShots.setText(""+L_dataItem.shots.total);
                binding.lOffTarget.setText(""+L_dataItem.shots.offgoal);
                binding.lShotsTarget.setText(""+L_dataItem.shots.ongoal);
                binding.lBlock.setText(""+L_dataItem.shots.blocked);
                binding.lInsidebox.setText(""+L_dataItem.shots.insidebox);
                binding.lOutsidebox.setText(""+L_dataItem.shots.outsidebox);
                binding.vTShots.setText(""+V_dataItem.shots.total);
                binding.vOffTarget.setText(""+V_dataItem.shots.offgoal);
                binding.vShotsTarget.setText(""+V_dataItem.shots.ongoal);
                binding.vBlock.setText(""+V_dataItem.shots.blocked);
                binding.vInsidebox.setText(""+V_dataItem.shots.insidebox);
                binding.vOutsidebox.setText(""+V_dataItem.shots.outsidebox);

            }
            binding.lCorners.setText(""+L_dataItem.corners);
            binding.lFouls.setText(""+L_dataItem.fouls);
            binding.lOffsides.setText(""+L_dataItem.offsides);
            binding.vCorners.setText(""+V_dataItem.corners);
            binding.vFouls.setText(""+V_dataItem.fouls);
            binding.vOffsides.setText(""+V_dataItem.offsides);
            if (L_dataItem.possessiontime!=0)
                binding.seekBar.setVisibility(View.VISIBLE);
            else
                binding.seekBar.setVisibility(View.GONE);
            binding.seekBar.setEnabled(false);

        }
        else {
          binding.noData.setVisibility(View.VISIBLE);
          binding.dataLayout.setVisibility(View.GONE);
        }

    }


}
