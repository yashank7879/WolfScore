package com.wolfscore.matches.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.databinding.FragmentMediaBinding;
import com.wolfscore.matches.adapter.LiveTickerAdapter;
import com.wolfscore.matches.adapter.MediaAdapter;
import com.wolfscore.matches.modal.CommentryDTO;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.ApiCollection.GET_MATCH_COMMENTARY;
import static com.wolfscore.utils.ApiCollection.GET_MATCH_HIGHLIGHT;

/**
 * Created by mindiii on 22/2/19.
 */

public class MeadiaFragment extends Fragment {
    FragmentMediaBinding binding;
       Context mContext;
    MediaAdapter mediaAdapter;
    ArrayList<CommentryDTO> mediaList=new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media, container, false);
        initialisation();
      /*  Bitmap bitmap = null;

        try {
            bitmap = retriveVideoFrameFromVideo("");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
          //  YOUR_IMAGE_VIEW.setImageBitmap(bitmap);
        }*/
        return binding.getRoot();
    }

    private void initialisation() {
        mediaList=new ArrayList<>();
        getDataApi();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvList.setLayoutManager(layoutManager);
        mediaAdapter = new MediaAdapter(mContext,mediaList);
        binding.rvList.setAdapter(mediaAdapter);

    }

    private void getDataApi() {

        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            AndroidNetworking.get(BASE_URL + GET_MATCH_HIGHLIGHT)
                    .addHeaders("Api-Key", APIKEY)
                     .addQueryParameter("match_id", ""+ AboutMatchActivity.aboutMatchActivity.matches.getMatchHeader().getMatch_id())
                   // .addQueryParameter("match_id", "10328820")
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
                                    if (data_obj.has("data")) {
                                        JSONObject json_data = data_obj.getJSONObject("data");
                                        if (json_data.has("highlights")) {
                                            JSONObject json_highlights = json_data.getJSONObject("highlights");
                                        if (json_highlights.has("data"))
                                        {
                                            data_array= json_highlights.getJSONArray("data");

                                            if (data_array.length()>0) {
                                                for (int i = 0; i < data_array.length(); i++) {
                                                    JSONObject object = data_array.getJSONObject(i);
                                                    CommentryDTO commentryDTO=new CommentryDTO();
                                                    if (object.has("fixture_id"))
                                                        commentryDTO.setFixture_id(!object.isNull("fixture_id") ? object.getInt("fixture_id") : 0);
                                                    if (object.has("location"))
                                                        commentryDTO.setLocation(!object.isNull("location") ? object.getString("location") : "");
                                                    if (object.has("created_at")){
                                                        commentryDTO.setDate(object.getJSONObject("created_at").getString("date"));
                                                    }
                                                    mediaList.add(commentryDTO);
                                                }

                                                mediaAdapter.notifyDataSetChanged();
                                            }
                                            else {
                                                binding.noData.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        }
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
