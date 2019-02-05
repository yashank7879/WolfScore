package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.fragment.TopPlayerFragment;
import com.wolfscore.listener.PlayerOnClick;
import com.wolfscore.responce.TopPlayerResponce;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 1/25/19.
 */

public class TopPlayerAdapter extends RecyclerView.Adapter<TopPlayerAdapter.MyViewHolder> {
    private Context mContext;
    private List<TopPlayerResponce.DataBean.PlayerListBean> teamList;
    private PlayerOnClick listener;

    public TopPlayerAdapter(Context mContext, List<TopPlayerResponce.DataBean.PlayerListBean> teamList, PlayerOnClick listener) {
        this.mContext = mContext;
        this.teamList = teamList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_player_cell, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        if (teamList.size() != 0) {
            final TopPlayerResponce.DataBean.PlayerListBean playerList = teamList.get(i);
            Picasso.with(holder.ivPlayer.getContext()).load(playerList.getPlayer_image()).fit().into(holder.ivPlayer);
            holder.tvTeamName.setText(playerList.getFull_name());
            holder.tvNationName.setText(playerList.getCountry_name());

            //""""""" visible plus image """""""//
            if (playerList.getIs_favorite().equals("0")) {
                holder.ivPlus.setVisibility(View.VISIBLE);
                holder.ivSelectTeam.setVisibility(View.GONE);

            } else if (playerList.getIs_favorite().equals("1")) { //""""" visible right tick image
                holder.ivSelectTeam.setVisibility(View.VISIBLE);
                holder.ivPlus.setVisibility(View.GONE);
            }

            holder.rlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //"""""""" check / Uncheck  """""""""//
                    if (playerList.getIs_favorite().equals("0")) {
                        listener.playerItemOnCLick(teamList.get(i), "1");
                    } else {
                        listener.playerItemOnCLick(teamList.get(i), "0");
                    }
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlView;
        private CircleImageView ivPlayer;
        private TextView tvTeamName;
        private CircleImageView ivNationFlag;
        private TextView tvNationName;
        private ImageView ivPlus;
        private ImageView ivSelectTeam;

        public MyViewHolder(@NonNull View view) {
            super(view);
            rlView = (RelativeLayout) view.findViewById(R.id.rl_view);
            ivPlayer = (CircleImageView) view.findViewById(R.id.iv_player);
            tvTeamName = (TextView) view.findViewById(R.id.tv_team_name);
            ivNationFlag = (CircleImageView) view.findViewById(R.id.iv_nation_flag);
            tvNationName = (TextView) view.findViewById(R.id.tv_nation_name);
            ivPlus = (ImageView) view.findViewById(R.id.iv_plus);
            ivSelectTeam = (ImageView) view.findViewById(R.id.iv_select_team);
        }
    }
}

