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
import com.wolfscore.activity.FavoriteFollowingResponce;
import com.wolfscore.listener.FavUnfavListener;

import java.util.List;

/**
 * Created by mindiii on 1/30/19.
 */

public class FollowingPlayerAdapter extends RecyclerView.Adapter<FollowingPlayerAdapter.MyViewHolder> {
    private Context mContext;
    private List<FavoriteFollowingResponce.DataBean.PlayerListBeanX.PlayerListBean> playerList;
    private FavUnfavListener listener;

    public FollowingPlayerAdapter(List<FavoriteFollowingResponce.DataBean.PlayerListBeanX.PlayerListBean> playerList, Context mContext, FavUnfavListener listener) {
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (playerList.size() != 0) {
            FavoriteFollowingResponce.DataBean.PlayerListBeanX.PlayerListBean bean = playerList.get(position);
            if (position == 0) {
                holder.ivStar.setVisibility(View.INVISIBLE);
                holder.ivPlayer.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_browse_plus));
            } else {
                Picasso.with(holder.ivPlayer.getContext()).load(bean.getPlayer_image()).fit().into(holder.ivPlayer);

                holder.tvPlayerName.setText(bean.getFirst_name());
                holder.ivStar.setVisibility(View.VISIBLE);
            }
            holder.ivPlayer.setOnClickListener(v -> {
                if (holder.getAdapterPosition() == 0) {
                    Intent intent = new Intent(mContext, AddFavrouitPlayerActivity.class);
                    mContext.startActivity(intent);
                }
            });
            holder.ivStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.playerFavUnfav(bean.getPlayer_id(), holder.getAdapterPosition());
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
