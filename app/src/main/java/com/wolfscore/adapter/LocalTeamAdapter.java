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
import com.wolfscore.responce.LocalTeamResponce;

import java.util.List;

/**
 * Created by mindiii on 1/22/19.
 */

public class LocalTeamAdapter extends RecyclerView.Adapter<LocalTeamAdapter.MyViewHolder> {
    private Context mContext;
    private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
    private TeamOnClick listener;

    public LocalTeamAdapter(Context mContext, List<LocalTeamResponce.DataBean.TeamListBean> teamList, TeamOnClick listener) {
        this.mContext = mContext;
        this.teamList = teamList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.local_team_cell, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        if (teamList.size() != 0) {
            final LocalTeamResponce.DataBean.TeamListBean team = teamList.get(i);
            holder.tvTeamName.setText(team.getName());
            Picasso.with(holder.ivTeam.getContext()).load(team.getLogo_path()).placeholder(R.drawable.app_icon).fit().into(holder.ivTeam);

            //""""""" visible plus image """""""//
            if (team.getIs_favorite().equals("0")) {// show plus icon
                holder.ivPlus.setVisibility(View.VISIBLE);
                holder.ivSelectTeam.setVisibility(View.GONE);
            } else if (team.getIs_favorite().equals("1")) { //""""" visible right tick image
                holder.ivSelectTeam.setVisibility(View.VISIBLE);
                holder.ivPlus.setVisibility(View.GONE);
            }

            holder.rlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //"""""""" check / Uncheck  """""""""//
                    if (team.getIs_favorite().equals("0")) {
                        holder.ivPlus.setVisibility(View.GONE);
                        holder.ivSelectTeam.setVisibility(View.VISIBLE);
                        listener.teamItemOnCLick(teamList.get(i), "1");
                    } else {
                        holder.ivPlus.setVisibility(View.VISIBLE);
                        holder.ivSelectTeam.setVisibility(View.GONE);
                        listener.teamItemOnCLick(teamList.get(i), "0");
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
        private ImageView ivTeam;
        private ImageView ivSelectTeam;
        private ImageView ivPlus;
        private TextView tvTeamName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rlView = (RelativeLayout) itemView.findViewById(R.id.rl_view);
            ivTeam = (ImageView) itemView.findViewById(R.id.iv_team);
            ivSelectTeam = (ImageView) itemView.findViewById(R.id.iv_select_team);
            ivPlus = (ImageView) itemView.findViewById(R.id.iv_plus);
            tvTeamName = (TextView) itemView.findViewById(R.id.tv_team_name);


        }
    }

    public interface TeamOnClick {
        void teamItemOnCLick(LocalTeamResponce.DataBean.TeamListBean bean, String value);
    }

}


