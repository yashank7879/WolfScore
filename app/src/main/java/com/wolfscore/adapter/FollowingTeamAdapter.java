package com.wolfscore.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.wolfscore.activity.AddFavrouitPlayerActivity;
import com.wolfscore.activity.AddFavrouitTeamActivity;
import com.wolfscore.activity.FavoriteFollowingResponce;
import com.wolfscore.listener.FavUnfavListener;

import java.util.List;

/**
 * Created by mindiii on 1/30/19.
 */

public class FollowingTeamAdapter extends RecyclerView.Adapter<FollowingTeamAdapter.MyViewHolder> {
    private Context mContext;
    private List<FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean> playerList;
    private FavUnfavListener listener;

    public FollowingTeamAdapter(List<FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean> playerList, Context mContext, FavUnfavListener listener) {
        this.playerList = playerList;
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_player_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (playerList.size() != 0) {
            FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean bean = playerList.get(position);
            if (position == 0) {
                holder.ivPlayer.setBackground(null);
                holder.tvPlayerName.setText("");
                holder.ivStar.setVisibility(View.INVISIBLE);
                holder.ivPlayer.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_browse_plus));
            } else {
                holder.ivPlayer.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_white_bg));
                Picasso.with(holder.ivPlayer.getContext()).load(bean.getLogo_path()).fit().into(holder.ivPlayer);
                holder.ivStar.setVisibility(View.VISIBLE);
                holder.tvPlayerName.setText(bean.getName());

            }
            holder.ivPlayer.setOnClickListener(v -> {
                if (holder.getAdapterPosition() == 0) {
                    Intent intent = new Intent(mContext, AddFavrouitTeamActivity.class);
                    mContext.startActivity(intent);
                }
            });
            holder.ivStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.teamFavUnfav(bean.getTeam_id(),holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPlayer;
        private ImageView ivStar;
        private TextView tvPlayerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPlayer = (ImageView) itemView.findViewById(R.id.iv_player);
            ivStar = (ImageView) itemView.findViewById(R.id.iv_star);
            tvPlayerName = (TextView) itemView.findViewById(R.id.tv_player_name);
        }
    }
}
