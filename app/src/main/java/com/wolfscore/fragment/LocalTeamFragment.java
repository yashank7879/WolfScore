package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.wolfscore.listener.GetTeamListener;
import com.wolfscore.R;
import com.wolfscore.adapter.LocalTeamAdapter;
import com.wolfscore.databinding.FragmentLocalTeamBinding;
import com.wolfscore.listener.NextOnClick;
import com.wolfscore.pagination.EndlessRecyclerViewScrollListener;
import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.utils.Constant;
import com.wolfscore.utils.PreferenceConnector;
import com.wolfscore.utils.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.wolfscore.utils.ApiCollection.APIKEY;
import static com.wolfscore.utils.ApiCollection.BASE_URL;
import static com.wolfscore.utils.Constant.CurrentPage;


public class LocalTeamFragment extends Fragment implements LocalTeamAdapter.TeamOnClick, View.OnClickListener {
    FragmentLocalTeamBinding binding;
    private Context mContext;
    private ProgressDialog progressDialog;
    private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
    private List<LocalTeamResponce.DataBean.TeamListBean> noRepeatList;
    private LocalTeamAdapter adapter;
    private int limit = 200;
    private int offset = 0;
    private String search = "";
    private HashMap<String, String> countryLookupMap;
    private String countryCodeValue = "";
    private GetTeamListener listener;
    private NextOnClick nextListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_local_team, container, false);
        return binding.getRoot();
    }

    /*  @Override
      public void setUserVisibleHint(boolean isVisibleToUser) {
          super.setUserVisibleHint(isVisibleToUser);
          if (isVisibleToUser) {
              teamList.clear();
              limit = 50;
              offset = 0;
              getLocalTeam(search);
          }

      }*/

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();


    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        teamList = new ArrayList<>();
        noRepeatList=new ArrayList<>();
        countryLookupMap = new HashMap<>();


        countryCode();
        progressDialog = new ProgressDialog(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvLocalTeam.setLayoutManager(layoutManager);
     //   adapter = new LocalTeamAdapter(mContext, teamList, this);
        adapter = new LocalTeamAdapter(mContext, noRepeatList, this);
        binding.rvLocalTeam.setAdapter(adapter);

        //"""""""""" Get current country """""""""""//
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        assert tm != null;
        countryCodeValue = tm.getNetworkCountryIso().toUpperCase();

        countryCodeValue = !countryCodeValue.isEmpty() ? countryCodeValue : "US";


        //******  Pagination """""""""""""""//
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Constant.isNetworkAvailable(mContext, binding.mainLayout)) {
                    if (search.isEmpty()) {
                        if (page != 0 && page != 1) {
                            progressDialog.show();
                        }
                        offset = offset + 50; //load 5 items in recyclerview
                        getLocalTeam();
                    }
/*
                    boolean flag = true;
                    if (!search.isEmpty() && teamList.size() < limit) {
                        flag = false;
                    }
                    if (flag)  getLocalTeam();*/

                }
            }
        };




        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                teamList.clear();
                noRepeatList.clear();
                offset = 0;
                search = editable.toString();
                getLocalTeam();
                adapter.notifyDataSetChanged();
            }
        });

        binding.rvLocalTeam.addOnScrollListener(scrollListener);

        getLocalTeam();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
             Log.e( "setUserVisibleHint: ","123" );
             if (CurrentPage == 1){
                 search = "";
                 if (binding!=null&&binding.etSearch!=null){
                     binding.etSearch.setText("");
                 }
                // binding.etSearch.setText("");
             }
            // nextListener.nextPopularOnclickListener(null, "Popularteam", true);
        }

    }

    //""""""""""  get the county from the country code """""""""""//
    private void countryCode() {
        countryLookupMap.put("AD", "Andorra");
        countryLookupMap.put("AE", "United Arab Emirates");
        countryLookupMap.put("AF", "Afghanistan");
        countryLookupMap.put("AG", "Antigua and Barbuda");
        countryLookupMap.put("AI", "Anguilla");
        countryLookupMap.put("AL", "Albania");
        countryLookupMap.put("AM", "Armenia");
        countryLookupMap.put("AN", "Netherlands Antilles");
        countryLookupMap.put("AO", "Angola");
        countryLookupMap.put("AQ", "Antarctica");
        countryLookupMap.put("AR", "Argentina");
        countryLookupMap.put("AS", "American Samoa");
        countryLookupMap.put("AT", "Austria");
        countryLookupMap.put("AU", "Australia");
        countryLookupMap.put("AW", "Aruba");
        countryLookupMap.put("AZ", "Azerbaijan");
        countryLookupMap.put("BA", "Bosnia and Herzegovina");
        countryLookupMap.put("BB", "Barbados");
        countryLookupMap.put("BD", "Bangladesh");
        countryLookupMap.put("BE", "Belgium");
        countryLookupMap.put("BF", "Burkina Faso");
        countryLookupMap.put("BG", "Bulgaria");
        countryLookupMap.put("BH", "Bahrain");
        countryLookupMap.put("BI", "Burundi");
        countryLookupMap.put("BJ", "Benin");
        countryLookupMap.put("BM", "Bermuda");
        countryLookupMap.put("BN", "Brunei");
        countryLookupMap.put("BO", "Bolivia");
        countryLookupMap.put("BR", "Brazil");
        countryLookupMap.put("BS", "Bahamas");
        countryLookupMap.put("BT", "Bhutan");
        countryLookupMap.put("BV", "Bouvet Island");
        countryLookupMap.put("BW", "Botswana");
        countryLookupMap.put("BY", "Belarus");
        countryLookupMap.put("BZ", "Belize");
        countryLookupMap.put("CA", "Canada");
        countryLookupMap.put("CC", "Cocos (Keeling) Islands");
        countryLookupMap.put("CD", "Congo, The Democratic Republic of the");
        countryLookupMap.put("CF", "Central African Republic");
        countryLookupMap.put("CG", "Congo");
        countryLookupMap.put("CH", "Switzerland");
        countryLookupMap.put("CI", "Côte d?Ivoire");
        countryLookupMap.put("CK", "Cook Islands");
        countryLookupMap.put("CL", "Chile");
        countryLookupMap.put("CM", "Cameroon");
        countryLookupMap.put("CN", "China");
        countryLookupMap.put("CO", "Colombia");
        countryLookupMap.put("CR", "Costa Rica");
        countryLookupMap.put("CU", "Cuba");
        countryLookupMap.put("CV", "Cape Verde");
        countryLookupMap.put("CX", "Christmas Island");
        countryLookupMap.put("CY", "Cyprus");
        countryLookupMap.put("CZ", "Czech Republic");
        countryLookupMap.put("DE", "Germany");
        countryLookupMap.put("DJ", "Djibouti");
        countryLookupMap.put("DK", "Denmark");
        countryLookupMap.put("DM", "Dominica");
        countryLookupMap.put("DO", "Dominican Republic");
        countryLookupMap.put("DZ", "Algeria");
        countryLookupMap.put("EC", "Ecuador");
        countryLookupMap.put("EE", "Estonia");
        countryLookupMap.put("EG", "Egypt");
        countryLookupMap.put("EH", "Western Sahara");
        countryLookupMap.put("ER", "Eritrea");
        countryLookupMap.put("ES", "Spain");
        countryLookupMap.put("ET", "Ethiopia");
        countryLookupMap.put("FI", "Finland");
        countryLookupMap.put("FJ", "Fiji Islands");
        countryLookupMap.put("FK", "Falkland Islands");
        countryLookupMap.put("FM", "Micronesia, Federated States of");
        countryLookupMap.put("FO", "Faroe Islands");
        countryLookupMap.put("FR", "France");
        countryLookupMap.put("GA", "Gabon");
        countryLookupMap.put("GB", "United Kingdom");
        countryLookupMap.put("GD", "Grenada");
        countryLookupMap.put("GE", "Georgia");
        countryLookupMap.put("GF", "French Guiana");
        countryLookupMap.put("GH", "Ghana");
        countryLookupMap.put("GI", "Gibraltar");
        countryLookupMap.put("GL", "Greenland");
        countryLookupMap.put("GM", "Gambia");
        countryLookupMap.put("GN", "Guinea");
        countryLookupMap.put("GP", "Guadeloupe");
        countryLookupMap.put("GQ", "Equatorial Guinea");
        countryLookupMap.put("GR", "Greece");
        countryLookupMap.put("GS", "South Georgia and the South Sandwich Islands");
        countryLookupMap.put("GT", "Guatemala");
        countryLookupMap.put("GU", "Guam");
        countryLookupMap.put("GW", "Guinea-Bissau");
        countryLookupMap.put("GY", "Guyana");
        countryLookupMap.put("HK", "Hong Kong");
        countryLookupMap.put("HM", "Heard Island and McDonald Islands");
        countryLookupMap.put("HN", "Honduras");
        countryLookupMap.put("HR", "Croatia");
        countryLookupMap.put("HT", "Haiti");
        countryLookupMap.put("HU", "Hungary");
        countryLookupMap.put("ID", "Indonesia");
        countryLookupMap.put("IE", "Ireland");
        countryLookupMap.put("IL", "Israel");
        countryLookupMap.put("IN", "India");
        countryLookupMap.put("IO", "British Indian Ocean Territory");
        countryLookupMap.put("IQ", "Iraq");
        countryLookupMap.put("IR", "Iran");
        countryLookupMap.put("IS", "Iceland");
        countryLookupMap.put("IT", "Italy");
        countryLookupMap.put("JM", "Jamaica");
        countryLookupMap.put("JO", "Jordan");
        countryLookupMap.put("JP", "Japan");
        countryLookupMap.put("KE", "Kenya");
        countryLookupMap.put("KG", "Kyrgyzstan");
        countryLookupMap.put("KH", "Cambodia");
        countryLookupMap.put("KI", "Kiribati");
        countryLookupMap.put("KM", "Comoros");
        countryLookupMap.put("KN", "Saint Kitts and Nevis");
        countryLookupMap.put("KP", "North Korea");
        countryLookupMap.put("KR", "South Korea");
        countryLookupMap.put("KW", "Kuwait");
        countryLookupMap.put("KY", "Cayman Islands");
        countryLookupMap.put("KZ", "Kazakstan");
        countryLookupMap.put("LA", "Laos");
        countryLookupMap.put("LB", "Lebanon");
        countryLookupMap.put("LC", "Saint Lucia");
        countryLookupMap.put("LI", "Liechtenstein");
        countryLookupMap.put("LK", "Sri Lanka");
        countryLookupMap.put("LR", "Liberia");
        countryLookupMap.put("LS", "Lesotho");
        countryLookupMap.put("LT", "Lithuania");
        countryLookupMap.put("LU", "Luxembourg");
        countryLookupMap.put("LV", "Latvia");
        countryLookupMap.put("LY", "Libyan Arab Jamahiriya");
        countryLookupMap.put("MA", "Morocco");
        countryLookupMap.put("MC", "Monaco");
        countryLookupMap.put("MD", "Moldova");
        countryLookupMap.put("MG", "Madagascar");
        countryLookupMap.put("MH", "Marshall Islands");
        countryLookupMap.put("MK", "Macedonia");
        countryLookupMap.put("ML", "Mali");
        countryLookupMap.put("MM", "Myanmar");
        countryLookupMap.put("MN", "Mongolia");
        countryLookupMap.put("MO", "Macao");
        countryLookupMap.put("MP", "Northern Mariana Islands");
        countryLookupMap.put("MQ", "Martinique");
        countryLookupMap.put("MR", "Mauritania");
        countryLookupMap.put("MS", "Montserrat");
        countryLookupMap.put("MT", "Malta");
        countryLookupMap.put("MU", "Mauritius");
        countryLookupMap.put("MV", "Maldives");
        countryLookupMap.put("MW", "Malawi");
        countryLookupMap.put("MX", "Mexico");
        countryLookupMap.put("MY", "Malaysia");
        countryLookupMap.put("MZ", "Mozambique");
        countryLookupMap.put("NA", "Namibia");
        countryLookupMap.put("NC", "New Caledonia");
        countryLookupMap.put("NE", "Niger");
        countryLookupMap.put("NF", "Norfolk Island");
        countryLookupMap.put("NG", "Nigeria");
        countryLookupMap.put("NI", "Nicaragua");
        countryLookupMap.put("NL", "Netherlands");
        countryLookupMap.put("NO", "Norway");
        countryLookupMap.put("NP", "Nepal");
        countryLookupMap.put("NR", "Nauru");
        countryLookupMap.put("NU", "Niue");
        countryLookupMap.put("NZ", "New Zealand");
        countryLookupMap.put("OM", "Oman");
        countryLookupMap.put("PA", "Panama");
        countryLookupMap.put("PE", "Peru");
        countryLookupMap.put("PF", "French Polynesia");
        countryLookupMap.put("PG", "Papua New Guinea");
        countryLookupMap.put("PH", "Philippines");
        countryLookupMap.put("PK", "Pakistan");
        countryLookupMap.put("PL", "Poland");
        countryLookupMap.put("PM", "Saint Pierre and Miquelon");
        countryLookupMap.put("PN", "Pitcairn");
        countryLookupMap.put("PR", "Puerto Rico");
        countryLookupMap.put("PS", "Palestine");
        countryLookupMap.put("PT", "Portugal");
        countryLookupMap.put("PW", "Palau");
        countryLookupMap.put("PY", "Paraguay");
        countryLookupMap.put("QA", "Qatar");
        countryLookupMap.put("RE", "Réunion");
        countryLookupMap.put("RO", "Romania");
        countryLookupMap.put("RU", "Russian Federation");
        countryLookupMap.put("RW", "Rwanda");
        countryLookupMap.put("SA", "Saudi Arabia");
        countryLookupMap.put("SB", "Solomon Islands");
        countryLookupMap.put("SC", "Seychelles");
        countryLookupMap.put("SD", "Sudan");
        countryLookupMap.put("SE", "Sweden");
        countryLookupMap.put("SG", "Singapore");
        countryLookupMap.put("SH", "Saint Helena");
        countryLookupMap.put("SI", "Slovenia");
        countryLookupMap.put("SJ", "Svalbard and Jan Mayen");
        countryLookupMap.put("SK", "Slovakia");
        countryLookupMap.put("SL", "Sierra Leone");
        countryLookupMap.put("SM", "San Marino");
        countryLookupMap.put("SN", "Senegal");
        countryLookupMap.put("SO", "Somalia");
        countryLookupMap.put("SR", "Suriname");
        countryLookupMap.put("ST", "Sao Tome and Principe");
        countryLookupMap.put("SV", "El Salvador");
        countryLookupMap.put("SY", "Syria");
        countryLookupMap.put("SZ", "Swaziland");
        countryLookupMap.put("TC", "Turks and Caicos Islands");
        countryLookupMap.put("TD", "Chad");
        countryLookupMap.put("TF", "French Southern territories");
        countryLookupMap.put("TG", "Togo");
        countryLookupMap.put("TH", "Thailand");
        countryLookupMap.put("TJ", "Tajikistan");
        countryLookupMap.put("TK", "Tokelau");
        countryLookupMap.put("TM", "Turkmenistan");
        countryLookupMap.put("TN", "Tunisia");
        countryLookupMap.put("TO", "Tonga");
        countryLookupMap.put("TP", "East Timor");
        countryLookupMap.put("TR", "Turkey");
        countryLookupMap.put("TT", "Trinidad and Tobago");
        countryLookupMap.put("TV", "Tuvalu");
        countryLookupMap.put("TW", "Taiwan");
        countryLookupMap.put("TZ", "Tanzania");
        countryLookupMap.put("UA", "Ukraine");
        countryLookupMap.put("UG", "Uganda");
        countryLookupMap.put("UM", "United States Minor Outlying Islands");
        countryLookupMap.put("US", "United States");
        countryLookupMap.put("UY", "Uruguay");
        countryLookupMap.put("UZ", "Uzbekistan");
        countryLookupMap.put("VA", "Holy See (Vatican City State)");
        countryLookupMap.put("VC", "Saint Vincent and the Grenadines");
        countryLookupMap.put("VE", "Venezuela");
        countryLookupMap.put("VG", "Virgin Islands, British");
        countryLookupMap.put("VI", "Virgin Islands, U.S.");
        countryLookupMap.put("VN", "Vietnam");
        countryLookupMap.put("VU", "Vanuatu");
        countryLookupMap.put("WF", "Wallis and Futuna");
        countryLookupMap.put("WS", "Samoa");
        countryLookupMap.put("YE", "Yemen");
        countryLookupMap.put("YT", "Mayotte");
        countryLookupMap.put("YU", "Yugoslavia");
        countryLookupMap.put("ZA", "South Africa");
        countryLookupMap.put("ZM", "Zambia");
        countryLookupMap.put("ZW", "Zimbabwe");
    }

    private void getLocalTeam() {
        if (Constant.isNetworkAvailable(mContext, binding.mainLayout) ) {

            AndroidNetworking.get(BASE_URL + "teams/get_local_teams?country=" + countryCodeValue + "&search_term=" + this.search + "&limit=" + 50 + "&offset=" + offset)
                    .addHeaders("Api-Key", APIKEY)
                    .addHeaders("Auth-Token", PreferenceConnector.readString(mContext, PreferenceConnector.AUTH_TOKEN, ""))
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

                                   /* if (!search.isEmpty() && offset == 0) {
                                        teamList.clear();
                                    }else if (search.isEmpty() && offset == 0){
                                        teamList.clear();
                                    }*/
                                    LocalTeamResponce teamResponce = new Gson().fromJson(String.valueOf(response), LocalTeamResponce.class);
                                    teamList.addAll(teamResponce.getData().getTeam_list());

                                    noRepeatList.clear();

                                    //////////// for removing duplicate element

                                    for (LocalTeamResponce.DataBean.TeamListBean event : teamList) {
                                        boolean isFound = false;
                                        // check if the event name exists in noRepeat
                                        for (LocalTeamResponce.DataBean.TeamListBean e : noRepeatList) {
                                            if (e.getTeam_id().equals(event.getTeam_id()) || (e.equals(event))) {
                                                isFound = true;
                                                break;
                                            }
                                        }
                                        if (!isFound) noRepeatList.add(event);
                                    }
                                    /////////


                                    adapter.notifyDataSetChanged();
                                    if (teamResponce.getData().getTotal_records().equals("0")) {
                                       /* teamList.clear();
                                        adapter.notifyDataSetChanged();*/
                                        binding.tvNoResult.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.tvNoResult.setVisibility(View.GONE);
                                    }

                                    for (LocalTeamResponce.DataBean.TeamListBean team : noRepeatList) {
                                        if (team.getIs_favorite().equals("1")) {
                                            nextListener.nextLocalOnclickListener(team, "Localteam", true);
                                        }
                                    }
                                    //  Toast.makeText(SetupWolfScoreScreenOne.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        listener = (GetTeamListener) context;
        nextListener = (NextOnClick) context;
    }




    @Override
    public void teamItemOnCLick(LocalTeamResponce.DataBean.TeamListBean bean, String value) {
     /*   if (value) {
            bean.setFavorite(true);
            adapter.notifyDataSetChanged();
            nextListener.nextLocalOnclickListener(bean,"team", false);
        }else {
            bean.setFavorite(false);
            adapter.notifyDataSetChanged();
            listener.getTeamNameListener(bean.getName());
            nextListener.nextLocalOnclickListener(bean,"team", true);
        } */

        if (value.equals("1")) {
            listener.getTeamNameListener(bean.getName());
            bean.setIs_favorite("1");
            adapter.notifyDataSetChanged();
            nextListener.nextLocalOnclickListener(bean, "Localteam", false);
        } else {
            bean.setIs_favorite("0");
            adapter.notifyDataSetChanged();
            nextListener.nextLocalOnclickListener(bean, "Localteam", true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }




}



