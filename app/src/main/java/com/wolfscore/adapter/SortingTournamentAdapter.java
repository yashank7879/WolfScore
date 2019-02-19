package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.activity.SortingTournamentActivity;
import com.wolfscore.responce.*;
import com.wolfscore.responce.GetLeagueResponce;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 2/11/19.
 */

public class SortingTournamentAdapter extends RecyclerView.Adapter<SortingTournamentAdapter.MyviewHolder> implements ItemTouchHelperAdapter {
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;
    private Context mContext;
    private SortingOnItemClick listener;

    public SortingTournamentAdapter(Context mContext, List<GetLeagueResponce.DataBean.LeagueListBean> leagueList, SortingOnItemClick listener) {
        this.mContext = mContext;
        this.leagueList = leagueList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sorting_tour_cell, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        if (leagueList.size() != 0) {
            final GetLeagueResponce.DataBean.LeagueListBean bean = leagueList.get(position);
            holder.tvCountry.setText(bean.getCountry_name());
            holder.tvLeagueName.setText(bean.getLeague_name());
            Picasso.with(holder.ivTeam.getContext()).load(bean.getLeague_flag()).placeholder(R.drawable.logo).into(holder.ivTeam);


            if (bean.isChecked()) {
                holder.ivSelectTeam.setVisibility(View.VISIBLE);
                holder.ivPlus.setVisibility(View.GONE);
            } else {
                holder.ivSelectTeam.setVisibility(View.GONE);
                holder.ivPlus.setVisibility(View.VISIBLE);
            }


            holder.rlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //"""""""" check / Uncheck  """""""""//
                    if (!bean.isChecked()) {
                        bean.setChecked(true);
                        holder.ivSelectTeam.setVisibility(View.VISIBLE);
                        holder.ivPlus.setVisibility(View.GONE);
                        listener.onItemCLick(leagueList.get(holder.getAdapterPosition()), "1");
                    } else {
                        bean.setChecked(false);
                        holder.ivSelectTeam.setVisibility(View.GONE);
                        holder.ivPlus.setVisibility(View.VISIBLE);
                        listener.onItemCLick(leagueList.get(holder.getAdapterPosition()), "0");
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(leagueList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(leagueList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

  /*  @Override
    public void onItemDismiss(int position) {

    }*/

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlView;
        private CircleImageView ivTeam;
        private TextView tvLeagueName;
        private TextView tvCountry;
        private ImageView ivPlus;
        private ImageView ivSelectTeam;

        public MyviewHolder(View view) {
            super(view);
            rlView = (RelativeLayout) view.findViewById(R.id.rl_view);
            ivTeam = (CircleImageView) view.findViewById(R.id.iv_team);
            tvLeagueName = (TextView) view.findViewById(R.id.tv_league_name);
            tvCountry = (TextView) view.findViewById(R.id.tv_country);
            ivPlus = (ImageView) view.findViewById(R.id.iv_plus);
            ivSelectTeam = (ImageView) view.findViewById(R.id.iv_select_team);
        }
    }

    public interface SortingOnItemClick {
        void onItemCLick(GetLeagueResponce.DataBean.LeagueListBean bean, String value);
    }
}


