package com.wolfscore.matches.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.wolfscore.R;
import com.wolfscore.aboutMatch.AboutMatchResponce;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.adapter.BenchAdapter;
import com.wolfscore.databinding.FragmentLiveTickerBinding;
import com.wolfscore.databinding.FragmentMediaBinding;
import com.wolfscore.matches.adapter.LiveTickerAdapter;
import com.wolfscore.matches.modal.CommentryDTO;
import com.wolfscore.matches.modal.TableDTO;
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
import static com.wolfscore.utils.ApiCollection.GET_MATCH_COMMENTARY;
import static com.wolfscore.utils.ApiCollection.GET_MATCH_DETAILS_API;

/**
 * Created by mindiii on 25/2/19.
 */

public class LiveTickerFragment extends Fragment {
    FragmentLiveTickerBinding binding;
    LiveTickerAdapter tickerAdapter;
   Context mContext;
   ArrayList<CommentryDTO> commentryList=new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live_ticker, container, false);
        initialisation();
        return binding.getRoot();

        /*View view =inflater.inflate(R.layout.live_ticker_list_item_layout, null);
        return view;*/
    }

    private void initialisation() {
        commentryList.clear();
        getDataApi();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvList.setLayoutManager(layoutManager);
        tickerAdapter = new LiveTickerAdapter(mContext,commentryList);
        binding.rvList.setAdapter(tickerAdapter);

    }

    private void getDataApi() {

        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            AndroidNetworking.get(BASE_URL + GET_MATCH_COMMENTARY)
                    .addHeaders("Api-Key", APIKEY)
                   .addQueryParameter("match_id", ""+AboutMatchActivity.aboutMatchActivity.matches.getMatchHeader().getMatch_id())
                  //   .addQueryParameter("match_id", "10421350")
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
                                    JSONObject data_obj= response.getJSONObject("data");
                                    JSONArray data_array=new JSONArray();
                                    if (data_obj.has("data"))
                                        data_array= data_obj.getJSONArray("data");

                                    if (data_array.length()>0) {
                                        for (int i = 0; i < data_array.length(); i++) {
                                            JSONObject object = data_array.getJSONObject(i);
                                            CommentryDTO commentryDTO=new CommentryDTO();
                                            if (object.has("fixture_id"))
                                                commentryDTO.setFixture_id(!object.isNull("fixture_id") ? object.getInt("fixture_id") : 0);
                                            if (object.has("important"))
                                                commentryDTO.setImportant(!object.isNull("important") ? object.getBoolean("important") : false);
                                            if (object.has("order"))
                                                commentryDTO.setOrder(!object.isNull("order") ? object.getInt("order") : 0);
                                            if (object.has("goal"))
                                                commentryDTO.setGoal(!object.isNull("goal") ? object.getBoolean("goal") : false);
                                            if (object.has("minute"))
                                                commentryDTO.setMinute(!object.isNull("minute") ? object.getInt("minute") : 0);
                                            if (object.has("extra_minute"))
                                                commentryDTO.setExtra_minute(!object.isNull("extra_minute") ? object.getInt("extra_minute") : 0);
                                            if (object.has("comment"))
                                                commentryDTO.setComment(!object.isNull("comment") ? object.getString("comment") : "");

                                          commentryList.add(commentryDTO);
                                        }

                                        tickerAdapter.notifyDataSetChanged();
                                    }


                                else {
                                        binding.noData.setVisibility(View.VISIBLE);
                                    }
                                }

                                 else {
                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch(JSONException e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError (ANError anError){
                        }
                    });
        }
    }


}
