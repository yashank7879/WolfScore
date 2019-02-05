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
import com.wolfscore.activity.LeagueFilteringActivity;
import com.wolfscore.responce.GetLeagueResponce;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 2/5/19.
 */

public class FilterTournamentAdapter extends RecyclerView.Adapter<FilterTournamentAdapter.MyviewHolder> {
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;
    private Context mContext;

    public FilterTournamentAdapter(Context mContext, List<GetLeagueResponce.DataBean.LeagueListBean> leagueList) {
        this.leagueList = leagueList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_tour_cell, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        if (leagueList.size() != 0){
            GetLeagueResponce.DataBean.LeagueListBean listBean = leagueList.get(position);
            holder.tvLeagueName.setText(listBean.getLeague_name());
            Picasso.with(holder.ivTeam.getContext()).load(listBean.getLeague_flag()).placeholder(R.drawable.logo).into(holder.ivTeam);
        }

    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlView;
        private CircleImageView ivTeam;
        private TextView tvLeagueName;
        private ImageView ivPlus;
        private ImageView ivSelectTeam;

        MyviewHolder(View itemView) {
            super(itemView);
            rlView = (RelativeLayout) itemView.findViewById(R.id.rl_view);
            ivTeam = (CircleImageView) itemView.findViewById(R.id.iv_team);
            tvLeagueName = (TextView) itemView.findViewById(R.id.tv_league_name);
            ivPlus = (ImageView) itemView.findViewById(R.id.iv_plus);
            ivSelectTeam = (ImageView) itemView.findViewById(R.id.iv_select_team);
        }
    }
}



