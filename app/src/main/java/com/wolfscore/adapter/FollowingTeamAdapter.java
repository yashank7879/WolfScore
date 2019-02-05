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

import java.util.List;

/**
 * Created by mindiii on 1/30/19.
 */

public class FollowingTeamAdapter extends RecyclerView.Adapter<FollowingTeamAdapter.MyViewHolder> {
    private Context mContext;
    private List<FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean> playerList;

    public FollowingTeamAdapter(List<FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean> playerList, Context mContext) {
        this.playerList = playerList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_player_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        if (playerList.size() != 0){
            FavoriteFollowingResponce.DataBean.TeamListBeanX.TeamListBean bean = playerList.get(position);
            if (position == 0){
                holder.ivPlayer.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_browse_plus));
            }else {
                Picasso.with(holder.ivPlayer.getContext()).load(bean.getLogo_path()).fit().into(holder.ivPlayer);
            }
            holder.tvPlayerName.setText(bean.getName());

            holder.ivPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.getAdapterPosition() == 0){
                        Intent intent = new Intent(mContext, AddFavrouitTeamActivity.class);
                        mContext.startActivity(intent);
                    }
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
        private TextView tvPlayerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPlayer = (ImageView) itemView.findViewById(R.id.iv_player);
            tvPlayerName = (TextView) itemView.findViewById(R.id.tv_player_name);
        }
    }
}
