package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.aboutMatch.DataLineUpItem;
import com.wolfscore.aboutMatch.DataSubstiItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 2/15/19.
 */

public class BenchAdapter extends RecyclerView.Adapter<BenchAdapter.MyviewAdapter> {
    private Context mContext;
    private List<DataLineUpItem> benchList;
    private List<DataSubstiItem> substituteList;
    private int teamId;


    public BenchAdapter(Context mContext, List<DataLineUpItem> benchList, int teamId, List<DataSubstiItem> substituteList) {
        this.mContext = mContext;
        this.benchList = benchList;
        this.teamId = teamId;
        this.substituteList =substituteList;
    }

    @NonNull
    @Override
    public MyviewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bench_player_cell, parent, false);
        return new MyviewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewAdapter holder, int position) {
        if (benchList.size() != 0) {
            DataLineUpItem data = benchList.get(position);
            if (teamId == data.teamId) {
                holder.rlImageview.setVisibility(View.VISIBLE);
                holder.tvPlayerName.append(""+data.number);
                holder.tvPlayerName.append(" "+data.playerName);

                if (data.player!=null&&data.player.dataPlayer!=null&&data.player.dataPlayer.imagePath!=null)
                Picasso.with(holder.ivProfile.getContext()).load(data.player.dataPlayer.imagePath).error(R.drawable.logo)
                        .placeholder(R.drawable.ic_player_placeholder)
                        .fit().into(holder.ivProfile);

                int yelllowCard = data.stats.cards.yellowcards != null ? data.stats.cards.yellowcards : 0;
                if (yelllowCard == 1) {
                    holder.yellowCard.setVisibility(View.VISIBLE);
                } else {
                    holder.yellowCard.setVisibility(View.GONE);
                }

                int rCard = data.stats.cards.redcards != null ? data.stats.cards.redcards : 0;
                if (rCard == 1) {
                    holder.redCard.setVisibility(View.VISIBLE);
                } else {
                    holder.redCard.setVisibility(View.GONE);
                }

                //"""""" show player position """"""
                String playerPos = data.position != null ? data.position :"";
                switch (playerPos) {
                    case "G":
                        holder.tvPlayerPos.setText(R.string.keeper);
                        break;
                    case "D":
                        holder.tvPlayerPos.setText(R.string.defender);
                        break;
                    case "F":
                        holder.tvPlayerPos.setText(R.string.attacker);
                        break;
                    case "M":
                        holder.tvPlayerPos.setText(R.string.midfielder);
                        break;
                }

                for (DataSubstiItem item : substituteList){
                    if (item.playerInId != null) {
                        if (item.playerInId.equals(data.playerId)) {
                            holder.llInOut.setVisibility(View.VISIBLE);
                            holder.tvInOutTime.append("" + item.minute + "' ");
                            holder.ivInTime.setVisibility(View.VISIBLE);
                        }
                    }
                    if (item.playerOutId != null) {
                        if (item.playerOutId.equals(data.playerId)) {
                            holder.llInOut.setVisibility(View.VISIBLE);
                            holder.tvInOutTime.append(item.minute+"' ");
                            holder.ivOutTime.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }else {
                holder.rlImageview.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return benchList.size();
    }

    class MyviewAdapter extends RecyclerView.ViewHolder {
        private RelativeLayout rlImageview;
        private CircleImageView ivProfile;
        private ImageView yellowCard;
        private ImageView redCard;
        private TextView tvPlayerName;
        private TextView tvPlayerPos;
        private LinearLayout llInOut;
        private TextView tvInOutTime;
        private ImageView ivInTime;
        private ImageView ivOutTime;

        MyviewAdapter(View itemView) {
            super(itemView);
            rlImageview = (RelativeLayout) itemView.findViewById(R.id.rl_imageview);
            ivProfile = (CircleImageView) itemView.findViewById(R.id.iv_profile);
            tvPlayerName = (TextView) itemView.findViewById(R.id.tv_player_name);
            tvPlayerPos = (TextView) itemView.findViewById(R.id.tv_player_position);
            yellowCard =  itemView.findViewById(R.id.iv_yellow_card);
            redCard =  itemView.findViewById(R.id.iv_red_card);
            llInOut =  itemView.findViewById(R.id.ll_in_out);
            tvInOutTime = (TextView) itemView.findViewById(R.id.tv_in_out_time);
            ivInTime = (ImageView) itemView.findViewById(R.id.iv_in_time);
            ivOutTime = (ImageView) itemView.findViewById(R.id.iv_out_time);
        }
    }
}


