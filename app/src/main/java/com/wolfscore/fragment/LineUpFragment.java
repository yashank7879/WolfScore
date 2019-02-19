package com.wolfscore.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.aboutMatch.DataLineUpItem;
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
    String visitorFormationlist[];
    List<DataLineUpItem> templineUpList = new ArrayList<>();
    private BenchAdapter adapter;

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
        counter=1;
     /*   binding.rvLocalBench.setLayoutManager(new GridLayoutManager(mContext, 1));
        adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.LocalteamId);
        binding.rvLocalBench.setAdapter(adapter);

        binding.rvVisitorBench.setLayoutManager(new GridLayoutManager(mContext, 1));
        adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.VisitorteamId);
        binding.rvVisitorBench.setAdapter(adapter);
*/

        binding.rvVisitorInjured.setLayoutManager(new GridLayoutManager(mContext, 1));
        adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.LocalteamId);
        binding.rvVisitorInjured.setAdapter(adapter);

        binding.rvLocalInjured.setLayoutManager(new GridLayoutManager(mContext, 1));
        adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.VisitorteamId);
        binding.rvLocalInjured.setAdapter(adapter);

        setCoachValue();

        if (AboutMatchActivity.aboutMatchActivity.lineUpList.size() != 0) {

            Log.e("onViewCreated: ", "" + AboutMatchActivity.aboutMatchActivity.lineUpList.size());

            Picasso.with(binding.ivLocalKeeper.getContext())
                    .load("https://cdn.sportmonks.com/images/soccer/players/1/212033.png")
                    .fit().into(binding.ivLocalKeeper);

            //"""" red card and yellow card """"""""""//
            if (AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.yellowcards == 1) {
                binding.ivKeeprLyCard.setVisibility(View.VISIBLE);
            } else {
                binding.ivKeeprLyCard.setVisibility(View.GONE);

            }

            if (AboutMatchActivity.aboutMatchActivity.lineUpList.get(0).stats.cards.redcards == 1) {
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
            binding.tvVisitorFormation.setText(AboutMatchActivity.aboutMatchActivity.visitorFormation);


            //"""" red card and yellow card """"""""""//
            if (AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).stats.cards.yellowcards == 1) {
                binding.ivKeeprVyCard.setVisibility(View.VISIBLE);
            } else {
                binding.ivKeeprVyCard.setVisibility(View.GONE);
            }

            if (AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).stats.cards.redcards == 1) {
                binding.ivKeeprVrCard.setVisibility(View.VISIBLE);
            } else {
                binding.ivKeeprVrCard.setVisibility(View.GONE);
            }

            binding.tvVisitorKeperName.append("" + AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).number);
            binding.tvVisitorKeperName.append(" " + AboutMatchActivity.aboutMatchActivity.lineUpList.get(11).player.dataPlayer.fullname);

            String localFormation = AboutMatchActivity.aboutMatchActivity.localFormation.trim();

            String localFormationlist[] = localFormation.trim().split("-");

            String visitorFormation = AboutMatchActivity.aboutMatchActivity.visitorFormation.trim();

            visitorFormationlist = visitorFormation.trim().split("-");


            for (int i = 0; i < localFormationlist.length; i++) {
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
        }   else{
            binding.goalLayout.setVisibility(View.GONE);
        }
        if (AboutMatchActivity.aboutMatchActivity.benchList.size()>0) {
            binding.rvLocalBench.setLayoutManager(new GridLayoutManager(mContext, 1));
            adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.LocalteamId);
            binding.rvLocalBench.setAdapter(adapter);

            binding.rvVisitorBench.setLayoutManager(new GridLayoutManager(mContext, 1));
            adapter = new BenchAdapter(mContext, AboutMatchActivity.aboutMatchActivity.benchList, AboutMatchActivity.aboutMatchActivity.VisitorteamId);
            binding.rvVisitorBench.setAdapter(adapter);

        }
        else {
            binding.benchLayout.setVisibility(View.GONE);
        }
        if (AboutMatchActivity.aboutMatchActivity.benchList.size()>0&&AboutMatchActivity.aboutMatchActivity.lineUpList.size()>0)
        {
            binding.noData.setVisibility(View.GONE);
        }else {
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    private void setCoachValue() {
        if (AboutMatchActivity.aboutMatchActivity.visitorCoach != null) {
            Picasso.with(binding.ivCoachVisitor.getContext())
                    .load(AboutMatchActivity.aboutMatchActivity.visitorCoach.imagePath)
                    .fit().into(binding.ivCoachVisitor);
            binding.tvCoachVname.setText(AboutMatchActivity.aboutMatchActivity.visitorCoach.commonName);
        }
        if (AboutMatchActivity.aboutMatchActivity.localCoach != null) {
            Picasso.with(binding.ivLocalKeeper.getContext())
                    .load(AboutMatchActivity.aboutMatchActivity.localCoach.imagePath)
                    .fit().into(binding.ivLcoach);

            binding.tvLcoachName.setText(AboutMatchActivity.aboutMatchActivity.localCoach.commonName);

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

        final View v = layoutInflater.inflate(R.layout.multiple_local_image_cell, linearLayout, false);

        final ImageView showTime = v.findViewById(R.id.iv_profile);
        final ImageView yellowCard = v.findViewById(R.id.iv_keepr_ly_card);
        final TextView playerName = v.findViewById(R.id.tv_player_name);
        final RelativeLayout rlImageCell = v.findViewById(R.id.rl_imageview);

        rlImageCell.setOnClickListener(this);
        rlImageCell.setId(counter);
        playerName.append("" + AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).number);
        playerName.append(" " + AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).player.dataPlayer.fullname);

        Picasso.with(showTime.getContext()).load(AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).player.dataPlayer.imagePath)
                .fit().into(showTime);


        if (AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).stats.cards.yellowcards == 1) {
            yellowCard.setVisibility(View.VISIBLE);
        } else {
            yellowCard.setVisibility(View.GONE);

        }

        linearLayout.addView(v);
        counter++;
        Log.e("addhorizontalLocal: ", counter + "");
    }

    void addhorizontalVisitorTeamView(LinearLayout linearLayout, String profileImage, int leftMargin) {
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View v = layoutInflater.inflate(R.layout.multiple_visitor_image_cell, linearLayout, false);

        final ImageView showTime = v.findViewById(R.id.iv_profile);
        final ImageView yellowCard = v.findViewById(R.id.iv_keepr_vy_card);
        final TextView playerName = v.findViewById(R.id.tv_player_name);
        final RelativeLayout rlImageCell = v.findViewById(R.id.rl_imageview);
        rlImageCell.setOnClickListener(this);
        rlImageCell.setId(counter);

        if (counter >= 11) {
            //  playerName.setText(AboutMatchActivity.aboutMatchActivity.lineUpList.get(counter).getPlayerName());
            playerName.append("" + templineUpList.get(tempCounter).number);
            playerName.append(" " + templineUpList.get(tempCounter).player.dataPlayer.fullname);
            Picasso.with(showTime.getContext()).load(templineUpList.get(tempCounter).player.dataPlayer.imagePath)
                    .fit().into(showTime);

            if (templineUpList.get(tempCounter).stats.cards.yellowcards == 1) {
                yellowCard.setVisibility(View.VISIBLE);
            } else {
                yellowCard.setVisibility(View.GONE);
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
