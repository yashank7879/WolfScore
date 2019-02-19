package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.aboutMatch.DataLineUpItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 2/15/19.
 */

public class BenchAdapter extends RecyclerView.Adapter<BenchAdapter.MyviewAdapter> {
    private Context mContext;
    private List<DataLineUpItem> benchList;
    private int teamId;


    public BenchAdapter(Context mContext, List<DataLineUpItem> benchList, int teamId) {
        this.mContext = mContext;
        this.benchList = benchList;
        this.teamId = teamId;
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
                Picasso.with(holder.ivProfile.getContext()).load(data.player.dataPlayer.imagePath).error(R.drawable.logo).fit().into(holder.ivProfile);


                switch (data.position) {
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
            }else {
               // holder.rlImageview.setVisibility(View.GONE);
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
        private TextView tvPlayerName;
        private TextView tvPlayerPos;

        MyviewAdapter(View itemView) {
            super(itemView);
            rlImageview = (RelativeLayout) itemView.findViewById(R.id.rl_imageview);
            ivProfile = (CircleImageView) itemView.findViewById(R.id.iv_profile);
            tvPlayerName = (TextView) itemView.findViewById(R.id.tv_player_name);
            tvPlayerPos = (TextView) itemView.findViewById(R.id.tv_player_position);
        }
    }
}

