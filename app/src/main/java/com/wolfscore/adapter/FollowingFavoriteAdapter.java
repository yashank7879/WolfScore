package com.wolfscore.adapter;

import android.app.Activity;
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
import com.wolfscore.activity.FavoriteFollowingResponce;
import com.wolfscore.activity.LeagueListActivity;
import com.wolfscore.listener.FavUnfavListener;

import java.util.List;

/**
 * Created by mindiii on 11/3/19.
 */

public class FollowingFavoriteAdapter extends RecyclerView.Adapter<FollowingFavoriteAdapter.MyViewHolder> {
    private Context mContext;
    private List<FavoriteFollowingResponce.DataBean.FavoriteListBeanX.FavListBean> playerList;
    private FavUnfavListener listener;

    public FollowingFavoriteAdapter(List<FavoriteFollowingResponce.DataBean.FavoriteListBeanX.FavListBean> playerList, Context mContext, FavUnfavListener listener) {
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
            FavoriteFollowingResponce.DataBean.FavoriteListBeanX.FavListBean bean = playerList.get(position);
            if (position == 0) {
                holder.ivStar.setVisibility(View.INVISIBLE);
                holder.ivPlayer.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_browse_plus));
            } else {
                holder.ivPlayer.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_white_bg));
                if (bean.getLeague_flag() != null && !bean.getLeague_flag().isEmpty())
                    Picasso.with(holder.ivPlayer.getContext()).load(bean.getLeague_flag()).fit().into(holder.ivPlayer);
                holder.ivStar.setVisibility(View.VISIBLE);
                holder.tvPlayerName.setText(bean.getLeague_name());

            }
            holder.ivPlayer.setOnClickListener(v -> {
                if (holder.getAdapterPosition() == 0) {
                    Activity activity=(Activity)mContext;
                   Intent intent = new Intent(mContext, LeagueListActivity.class);
                    mContext.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });
            holder.ivStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.leagueFavUnfav(bean.getLeague_id(),holder.getAdapterPosition());
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
