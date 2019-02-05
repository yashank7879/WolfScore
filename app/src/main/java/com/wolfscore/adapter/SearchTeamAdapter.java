package com.wolfscore.adapter;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.activity.SearchActivity;
import com.wolfscore.responce.LocalTeamResponce;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 2/4/19.
 */

public class SearchTeamAdapter extends RecyclerView.Adapter<SearchTeamAdapter.MyViewHolder> {
    private Context mContext;
    private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
    private FavrouitTeamSelect listener;

    public SearchTeamAdapter(Context mContext, List<LocalTeamResponce.DataBean.TeamListBean> teamList, FavrouitTeamSelect listener) {
        this.mContext = mContext;
        this.teamList = teamList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        if (teamList.size() != 0) {
            final LocalTeamResponce.DataBean.TeamListBean team = teamList.get(position);
            holder.tvTeamName.setText(team.getName());
            Picasso.with(holder.ivTeam.getContext()).load(team.getLogo_path()).placeholder(R.drawable.app_icon).fit().into(holder.ivTeam);

            //""""""" visible plus image """""""//
            if (team.getIs_favorite().equals("0")) {// show plus icon

                holder.ivStar.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_white_star3x));
            } else if (team.getIs_favorite().equals("1")) { //""""" visible right tick image
                holder.ivStar.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_active_star));

            }

            holder.ivStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //"""""""" check / Uncheck  """""""""//
                    if (team.getIs_favorite().equals("0")) {
                        holder.ivStar.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_active_star));

                        listener.favrouitSelectUnselect(teamList.get(holder.getAdapterPosition()), "1", holder.ivStar);
                    } else {
                        holder.ivStar.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_white_star3x));

                        listener.favrouitSelectUnselect(teamList.get(holder.getAdapterPosition()), "0",holder.ivStar);
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
        private TextView tvTeamName;
        private ImageView ivStar;
        private ImageView ivTeam;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivTeam = (CircleImageView) itemView.findViewById(R.id.iv_team);
            tvTeamName = (TextView) itemView.findViewById(R.id.tv_team_name);
            ivStar = (ImageView) itemView.findViewById(R.id.iv_star);
        }
    }

    public interface FavrouitTeamSelect {
        void favrouitSelectUnselect(LocalTeamResponce.DataBean.TeamListBean teamListBean, String value, ImageView ivStar);
    }
}


