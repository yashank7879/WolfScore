package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.aboutMatch.DataLineUpItem;
import com.wolfscore.aboutMatch.DataSubstiItem;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.adapter.BenchAdapter;
import com.wolfscore.databinding.FragmentLineUpBinding;

import java.util.ArrayList;
import java.util.List;


public class LineUpFragment extends Fragment implements View.OnClickListener {
    FragmentLineUpBinding binding;
    private Context mContext;
    private int counter = 1, tempCounter = 0;
    int listLength = 0, jValue = 0;
    // String visitorFormationlist[];
    List<DataLineUpItem> templineUpList = new ArrayList<>();
    private BenchAdapter adapter;
    private Typeface robotoMedium;
    int localLength=0,visitorLength=0,formationValue=0;

    public LineUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_line_up, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        counter = 1;
     /*   binding.rvLocalBench.setLayoutManager(new GridLayoutManager(mContext, 1));
        adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.LocalteamId);
        binding.rvLocalBench.setAdapter(adapter);

        binding.rvVisitorBench.setLayoutManager(new GridLayoutManager(mContext, 1));
        adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.VisitorteamId);
        binding.rvVisitorBench.setAdapter(adapter);
*/
        robotoMedium = ResourcesCompat.getFont(getActivity(), R.font.roboto_medium);

      //  setCoachValue();
        setInjuredValue();
        setLineup();


    }

    private void setLineup()
    {
        if (AboutMatchActivity.aboutMatchActivity.lineUpList!=null&&AboutMatchActivity.aboutMatchActivity.lineUpList.size() != 0) {
            String localFormation = AboutMatchActivity.aboutMatchActivity.localFormation != null ? AboutMatchActivity.aboutMatchActivity.localFormation.trim() : "4-4-2";

            String localFormationlist[] = localFormation.trim().split("-");
            localLength=localFormationlist.length;

            String visitorFormation = AboutMatchActivity.aboutMatchActivity.visitorFormation != null ? AboutMatchActivity.aboutMatchActivity.visitorFormation.trim() : "4-4-2";

            String visitorFormationlist[] = visitorFormation.trim().split("-");
            visitorLength=visitorFormationlist.length;

            binding.tvLocalFormation.setText(localFormation);
            binding.tvLocalFormation.setTypeface(robotoMedium);

            binding.tvVisitorFormation.setText(visitorFormation);
            binding.tvVisitorFormation.setTypeface(robotoMedium);
            binding.tvLocalTeamName.setText(AboutMatchActivity.aboutMatchActivity.LocalTeam);
            binding.tvLocalTeamName.setTypeface(robotoMedium);
            binding.tvVisitorName.setText(AboutMatchActivity.aboutMatchActivity.VisitorTeam);
            binding.tvVisitorName.setTypeface(robotoMedium);

            binding.tvVisitorName1.setText(AboutMatchActivity.aboutMatchActivity.VisitorTeam);
            binding.tvVisitorName1.setTypeface(robotoMedium);
            binding.tvLocalTeamName1.setText(AboutMatchActivity.aboutMatchActivity.LocalTeam);
            binding.tvLocalTeamName1.setTypeface(robotoMedium);

            Log.e("onViewCreated: ", "" + AboutMatchActivity.aboutMatchActivity.lineUpList.size());

            Picasso.with(binding.ivLocalKeeper.getContext())
                    .load("https://cdn.sportmonks.com/images/soccer/players/1/212033.png")
                    .fit().into(binding.ivLocalKeeper);

            //"""" red card and yellow card """"""""""//
            int yelllowCard = AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.yellowcards != null ? AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.yellowcards : 0;
            if (yelllowCard == 1) {
                binding.ivKeeprLyCard.setVisibility(View.VISIBLE);
            } else {
                binding.ivKeeprLyCard.setVisibility(View.GONE);
            }

            int redCard = AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.redcards != null ? AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.yellowcards : 0;
            if (redCard == 1) {
                binding.ivKeeprLrCard.setVisibility(View.VISIBLE);
            } else {
                binding.ivKeeprLrCard.setVisibility(View.GONE);
            }

            binding.tvLocalKeeprName.append("" + AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).number);
            binding.tvLocalKeeprName.append(" " + AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).player.dataPlayer.fullname);

            Picasso.with(binding.ivVisitorKeeper.getContext())
                    .load(AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).player.dataPlayer.imagePath)
                    .fit().into(binding.ivVisitorKeeper);


            binding.tvLocalFormation.setText(AboutMatchActivity.aboutMatchActivity.localFormation);
            binding.tvLocalFormation.setTypeface(robotoMedium);
            binding.tvVisitorFormation.setText(AboutMatchActivity.aboutMatchActivity.visitorFormation);
            binding.tvVisitorFormation.setTypeface(robotoMedium);


            //"""" red card and yellow card """"""""""//
            yelllowCard = AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.yellowcards != null ? AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).stats.cards.yellowcards : 0;
            if (yelllowCard == 1) {
                binding.ivKeeprVyCard.setVisibility(View.VISIBLE);
            } else {
                binding.ivKeeprVyCard.setVisibility(View.GONE);
            }

            redCard = AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.redcards != null ? AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).stats.cards.yellowcards : 0;
            if (redCard == 1) {
                binding.ivKeeprVrCard.setVisibility(View.VISIBLE);
            } else {
                binding.ivKeeprVrCard.setVisibility(View.GONE);
            }

            binding.tvVisitorKeperName.append("" + AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).number);
            binding.tvVisitorKeperName.append(" " + AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).player.dataPlayer.fullname);


            for (int i = 0; i < localFormationlist.length; i++) {
                formationValue= Integer.parseInt(localFormationlist[i]);
                for (int j = 0; j < Integer.parseInt(localFormationlist[i]); j++) {
                    if (i == 0) {
                        addhorizontalLocalTeamView(binding.llLocalPostion1, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    } else if (i == 1) {
                        addhorizontalLocalTeamView(binding.flLocalPostion2, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    } else if (i == 2) {
                        addhorizontalLocalTeamView(binding.flLocalPostion3, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    } else if (i == 3) {
                        addhorizontalLocalTeamView(binding.flLocalPostion4, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    }
                }
            }

            for (int i = 0; i < visitorFormationlist.length; i++) {
                templineUpList.clear();
                tempCounter = Integer.parseInt(visitorFormationlist[i]);
                formationValue = Integer.parseInt(visitorFormationlist[i]);
                tempCounter--;
                for (int j = 0; j < Integer.parseInt(visitorFormationlist[i]); j++) {
                    templineUpList.add(AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter + j + 1));
                }
                for (int j = 0; j < Integer.parseInt(visitorFormationlist[i]); j++) {
                    //  listLength= Integer.parseInt(visitorFormationlist[i]);
                    //  jValue=i;
                    if (i == 0) {
                        addhorizontalVisitorTeamView(binding.flVisitorPostion1, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    } else if (i == 1) {
                        addhorizontalVisitorTeamView(binding.flVisitorPostion2, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    } else if (i == 2) {
                        addhorizontalVisitorTeamView(binding.flVisitorPostion3, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    } else if (i == 3) {
                        addhorizontalVisitorTeamView(binding.flVisitorPostion4, AboutMatchActivity.aboutMatchActivity.lineUpList.get(i).playerName, 10);
                    }
                }

            }
        } else {
            binding.goalLayout.setVisibility(View.GONE);
        }
        if (AboutMatchActivity.aboutMatchActivity.benchList.size() > 0) {
            binding.rvLocalBench.setLayoutManager(new GridLayoutManager(mContext, 1));
            adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.LocalteamId, AboutMatchActivity.aboutMatchActivity.substituteList);
            binding.rvLocalBench.setAdapter(adapter);

            binding.rvVisitorBench.setLayoutManager(new GridLayoutManager(mContext, 1));
            adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.VisitorteamId, AboutMatchActivity.aboutMatchActivity.substituteList);
            binding.rvVisitorBench.setAdapter(adapter);

        } else {
            binding.benchLayout.setVisibility(View.GONE);
        }


        if (AboutMatchActivity.aboutMatchActivity.benchList.size() > 0
                || AboutMatchActivity.aboutMatchActivity.lineUpList.size() > 0
                || AboutMatchActivity.aboutMatchActivity.slidelineList.size() > 0
                || AboutMatchActivity.aboutMatchActivity.visitorCoach != null
                || AboutMatchActivity.aboutMatchActivity.localCoach != null) {
            binding.noData.setVisibility(View.GONE);
        } else {
            binding.noData.setVisibility(View.VISIBLE);
        }

    }

    //""""""" injured list """""""""""'//
    private void setInjuredValue() {
        if (AboutMatchActivity.aboutMatchActivity.slidelineList.size() != 0) {
            binding.llInjured.setVisibility(View.VISIBLE);
            binding.rvVisitorInjured.setLayoutManager(new GridLayoutManager(mContext, 1));
            adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.LocalteamId, AboutMatchActivity.aboutMatchActivity.substituteList);
            binding.rvVisitorInjured.setAdapter(adapter);

            binding.rvLocalInjured.setLayoutManager(new GridLayoutManager(mContext, 1));
            adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.VisitorteamId, AboutMatchActivity.aboutMatchActivity.substituteList);
            binding.rvLocalInjured.setAdapter(adapter);
        } else {
            binding.llInjured.setVisibility(View.GONE);
        }
    }

    //""""""""" set coach data"""""""""""//
    private void setCoachValue() {
        if (AboutMatchActivity.aboutMatchActivity.visitorCoach != null) {
            binding.coachLayout.setVisibility(View.VISIBLE);
            Picasso.with(binding.ivCoachVisitor.getContext())
                    .load(AboutMatchActivity.aboutMatchActivity.visitorCoach.imagePath).placeholder(R.drawable.ic_player_placeholder)
                    .fit().into(binding.ivCoachVisitor);
            binding.tvCoachVname.setText(AboutMatchActivity.aboutMatchActivity.visitorCoach.commonName);
            binding.tvCoachVname.setTypeface(robotoMedium);
        } else {
            binding.coachLayout.setVisibility(View.GONE);
        }

        if (AboutMatchActivity.aboutMatchActivity.localCoach != null) {
            binding.coachLayout.setVisibility(View.VISIBLE);
            Picasso.with(binding.ivLocalKeeper.getContext())
                    .load(AboutMatchActivity.aboutMatchActivity.localCoach.imagePath).placeholder(R.drawable.ic_player_placeholder)
                    .fit().into(binding.ivLcoach);

            binding.tvLcoachName.setText(AboutMatchActivity.aboutMatchActivity.localCoach.commonName);
            binding.tvLcoachName.setTypeface(robotoMedium);
        } else {
            binding.coachLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    void addhorizontalLocalTeamView(LinearLayout linearLayout, String playerName1, int leftMargin) {
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = layoutInflater.inflate(R.layout.multiple_local_image_cell, linearLayout, false);

        if (localLength==2||localLength==3){
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            if (formationValue==2)
             buttonLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen._40sdp),getResources().getDimensionPixelSize(R.dimen._8sdp),getResources().getDimensionPixelSize(R.dimen._40sdp), 0);
           else if (formationValue==3)
                buttonLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen._20sdp),getResources().getDimensionPixelSize(R.dimen._8sdp),getResources().getDimensionPixelSize(R.dimen._20sdp), 0);
            else
            buttonLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen._8sdp),getResources().getDimensionPixelSize(R.dimen._8sdp), getResources().getDimensionPixelSize(R.dimen._8sdp), 0);
             v.setLayoutParams(buttonLayoutParams);

        }

        ImageView showTime = v.findViewById(R.id.iv_profile);
        ImageView yellowCard = v.findViewById(R.id.iv_keepr_ly_card);
        ImageView redCard = v.findViewById(R.id.iv_keepr_lr_card);
        TextView playerName = v.findViewById(R.id.tv_player_name);
        RelativeLayout rlImageCell = v.findViewById(R.id.rl_imageview);
        LinearLayout llInOut = v.findViewById(R.id.ll_in_out);
        TextView tvInOutTime = v.findViewById(R.id.tv_in_out_time);
        ImageView ivInTime = v.findViewById(R.id.iv_in_time);
        ImageView ivOutTime = v.findViewById(R.id.iv_out_time);

        rlImageCell.setOnClickListener(this);
        rlImageCell.setId(counter);
        playerName.setTypeface(robotoMedium);
       /* playerName.setText("" + AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).number
        +" " + AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).player.dataPlayer.fullname);
*/
        playerName.append("" + AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).number);
        playerName.append(" " + AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).player.dataPlayer.fullname);

        Picasso.with(showTime.getContext()).load(AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter)
                .player.dataPlayer.imagePath).placeholder(R.drawable.ic_player_placeholder)
                .fit().into(showTime);
        int yelllowCard = AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).stats.cards.yellowcards != null ? AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).stats.cards.yellowcards : 0;

        if (yelllowCard == 1) {
            yellowCard.setVisibility(View.VISIBLE);
        } else {
            yellowCard.setVisibility(View.GONE);

        }
        int rCard = AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).stats.cards.redcards != null ? AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).stats.cards.yellowcards : 0;

        if (rCard == 1) {
            redCard.setVisibility(View.VISIBLE);
        } else {
            redCard.setVisibility(View.GONE);

        }
        int playerId = AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).playerId;
        for (DataSubstiItem item : AboutMatchActivity.aboutMatchActivity.substituteList) {
            if (item.teamId.equals(String.valueOf(AboutMatchActivity.aboutMatchActivity.LocalteamId))) {
                if (item.playerInId!=null&&item.playerInId.equals(playerId)) {
                    llInOut.setVisibility(View.VISIBLE);
                    tvInOutTime.append("" + item.minute + "' ");
                    ivInTime.setVisibility(View.VISIBLE);
                }
                if (item.playerOutId!=null&&item.playerOutId.equals(playerId)) {
                    llInOut.setVisibility(View.VISIBLE);
                    tvInOutTime.append(item.minute + "' ");
                    ivOutTime.setVisibility(View.VISIBLE);
                }
            }
        }

        linearLayout.addView(v);
        counter++;
        Log.e("addhorizontalLocal: ", counter + "");
    }

    void addhorizontalVisitorTeamView(LinearLayout linearLayout, String profileImage, int leftMargin) {
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View v = layoutInflater.inflate(R.layout.multiple_visitor_image_cell, linearLayout, false);

        if (visitorLength==2||visitorLength==3){
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            if (formationValue==2)
                buttonLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen._40sdp),0,getResources().getDimensionPixelSize(R.dimen._40sdp), getResources().getDimensionPixelSize(R.dimen._8sdp));
            else if (formationValue==3)
                buttonLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen._20sdp),0,getResources().getDimensionPixelSize(R.dimen._20sdp), getResources().getDimensionPixelSize(R.dimen._8sdp));
            else
            buttonLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen._8sdp),0,getResources().getDimensionPixelSize(R.dimen._8sdp), getResources().getDimensionPixelSize(R.dimen._8sdp));
            v.setLayoutParams(buttonLayoutParams);

        }

        final ImageView showTime = v.findViewById(R.id.iv_profile);
        final ImageView yellowCard = v.findViewById(R.id.iv_keepr_vy_card);
        final ImageView redCard = v.findViewById(R.id.iv_keepr_vr_card);
        final TextView playerName = v.findViewById(R.id.tv_player_name);
        final RelativeLayout rlImageCell = v.findViewById(R.id.rl_imageview);
        LinearLayout llInOut = v.findViewById(R.id.ll_in_out);
        TextView tvInOutTime = v.findViewById(R.id.tv_in_out_time);
        ImageView ivInTime = v.findViewById(R.id.iv_in_time);
        ImageView ivOutTime = v.findViewById(R.id.iv_out_time);
        rlImageCell.setOnClickListener(this);
        rlImageCell.setId(counter);
        playerName.setTypeface(robotoMedium);

        if (counter >= 11) {
            playerName.append("" + templineUpList.get(tempCounter).number);
            if (templineUpList.get(tempCounter).player!=null) {
                if (templineUpList.get(tempCounter).player.dataPlayer!=null) {
                    playerName.append(" " + templineUpList.get(tempCounter).player.dataPlayer.fullname);
                    Picasso.with(showTime.getContext()).load(templineUpList.get(tempCounter).player.dataPlayer.imagePath)
                            .placeholder(R.drawable.ic_player_placeholder).fit().into(showTime);
                }

            }
            int yelllowCard = templineUpList.get(tempCounter).stats.cards.yellowcards != null ? templineUpList.get(tempCounter).stats.cards.yellowcards : 0;
            if (yelllowCard == 1) {
                yellowCard.setVisibility(View.VISIBLE);
            } else {
                yellowCard.setVisibility(View.GONE);
            }

            int rCard = templineUpList.get(tempCounter).stats.cards.redcards != null ? templineUpList.get(tempCounter).stats.cards.redcards : 0;
            if (rCard == 1) {
                redCard.setVisibility(View.VISIBLE);
            } else {
                redCard.setVisibility(View.GONE);
            }

            int playerId = templineUpList.get(tempCounter).playerId;
            for (DataSubstiItem item : AboutMatchActivity.aboutMatchActivity.substituteList) {
                if (item.teamId.equals(String.valueOf(AboutMatchActivity.aboutMatchActivity.VisitorteamId))) {
                    if (item.playerInId!=null&&item.playerInId.equals(playerId)) {
                        llInOut.setVisibility(View.VISIBLE);
                        tvInOutTime.append("" + item.minute + "' ");
                        ivInTime.setVisibility(View.VISIBLE);
                    }
                    if (item.playerOutId!=null&&item.playerOutId.equals(playerId)) {
                        llInOut.setVisibility(View.VISIBLE);
                        tvInOutTime.append(item.minute + "' ");
                        ivOutTime.setVisibility(View.VISIBLE);
                    }
                }
            }

            tempCounter--;

        } else {

        }

        counter++;
        linearLayout.addView(v);

    }

    @Override
    public void onClick(View v) {
        //DataLineUpItem dataLineUpItem = (DataLineUpItem) AboutMatchActivity.aboutMatchActivity.lineUpList;
        switch (v.getId()) {
            case 1: {
                // Toast.makeText(mContext, AboutMatchActivity.aboutMatchActivity.lineUpList.get(1).playerName, Toast.LENGTH_SHORT).show();
            }
            break;
            case 2: {
                // Toast.makeText(mContext, AboutMatchActivity.aboutMatchActivity.lineUpList.get(2).playerName, Toast.LENGTH_SHORT).show();
            }
            break;
            case 3: {
                //   Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
}
