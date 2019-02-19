package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.R;
import com.wolfscore.databinding.FragmentNotificationTypesBinding;
import com.wolfscore.listener.NextOnClick;
import com.wolfscore.responce.NotificationResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;


public class NotificationTypesFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    FragmentNotificationTypesBinding binding;
    private Context mContext;
    private ProgressDialog progressDialog;
    private NextOnClick listener;


    public NotificationTypesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification_types, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.switchGoals.setOnCheckedChangeListener(this);
        binding.switchAllEvent.setOnCheckedChangeListener(this);
        binding.switchRedCard.setOnCheckedChangeListener(this);
        binding.switchYellowCard.setOnCheckedChangeListener(this);
        binding.switchStarted.setOnCheckedChangeListener(this);
        binding.switchTransfer.setOnCheckedChangeListener(this);
        progressDialog = new ProgressDialog(mContext);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getNotificationApi();
            listener.nextNotification("Not", true);
        }

    }

    private void getNotificationApi() {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
            progressDialog.show();
            AndroidNetworking.get(BASE_URL + "users/get_notification_setting")
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                String status = response.getString("status");
                                String message = response.getString("message");
                                if (status.equals("success")) {
                                    NotificationResponce responce = new Gson().fromJson(response.toString(), NotificationResponce.class);

                                    binding.setResponce(responce.getData());
                                    setResponceData(responce);

                                } else {
                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }
    }

    private void setResponceData(NotificationResponce responce) {
        if (responce.getData().getGoal().equals("1")) {
            binding.switchGoals.setChecked(true);
        } else {
            binding.switchGoals.setChecked(false);
        }

        if (responce.getData().getRed_card().equals("1")) {
            binding.switchRedCard.setChecked(true);
        } else {
            binding.switchRedCard.setChecked(false);
        }

        if (responce.getData().getYellow_card().equals("1")) {
            binding.switchYellowCard.setChecked(true);
        } else {
            binding.switchYellowCard.setChecked(false);
        }

        if (responce.getData().getMatch_start().equals("1")) {
            binding.switchStarted.setChecked(true);
        } else {
            binding.switchStarted.setChecked(false);
        }

        if (responce.getData().getHalf_time().equals("1")) {
            binding.switchTransfer.setChecked(true);
        } else {
            binding.switchTransfer.setChecked(false);
        }


        allChecke();
    }

    private void allChecke() {
        if (binding.switchGoals.isChecked() && binding.switchRedCard.isChecked() && binding.switchYellowCard.isChecked()
                && binding.switchStarted.isChecked() && binding.switchTransfer.isChecked()) {
            binding.switchAllEvent.setChecked(true);
        } else if (!binding.switchGoals.isChecked() && !binding.switchRedCard.isChecked() && !binding.switchYellowCard.isChecked()
                && !binding.switchStarted.isChecked() && !binding.switchTransfer.isChecked()){
            binding.switchAllEvent.setChecked(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.listener = (NextOnClick) context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_all_event:
                if (isChecked) {
                    allCheckedNotification();
                } else {
                    allUncheckedNotification();
                }
                break;

            case R.id.switch_goals:
                listener.nextNotification("goal", isChecked);
                binding.switchGoals.setChecked(isChecked);
                allChecke();
                break;

            case R.id.switch_red_card:
                listener.nextNotification("red_card", isChecked);
                binding.switchRedCard.setChecked(isChecked);
                allChecke();

                break;

            case R.id.switch_yellow_card:
                listener.nextNotification("yellow_card", isChecked);
                binding.switchYellowCard.setChecked(isChecked);
                allChecke();

                break;

            case R.id.switch_started:
                listener.nextNotification("match_start", isChecked);
                binding.switchStarted.setChecked(isChecked);
                allChecke();

                break;
            case R.id.switch_transfer:
                listener.nextNotification("half_time", isChecked);
                binding.switchTransfer.setChecked(isChecked);
                allChecke();

                break;
        }

    }

    private void allUncheckedNotification() {
        binding.switchAllEvent.setChecked(false);
        binding.switchGoals.setChecked(false);
        binding.switchRedCard.setChecked(false);
        binding.switchYellowCard.setChecked(false);
        binding.switchStarted.setChecked(false);
        binding.switchTransfer.setChecked(false);
    }

    private void allCheckedNotification() {
        binding.switchAllEvent.setChecked(true);
        binding.switchGoals.setChecked(true);
        binding.switchRedCard.setChecked(true);
        binding.switchYellowCard.setChecked(true);
        binding.switchStarted.setChecked(true);
        binding.switchTransfer.setChecked(true);
    }
}
