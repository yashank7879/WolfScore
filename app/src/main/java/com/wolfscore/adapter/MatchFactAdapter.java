package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.aboutMatch.DataItem;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.responce.LocalTeamResponce;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindiii on 12/2/19.
 */

public class MatchFactAdapter extends RecyclerView.Adapter<MatchFactAdapter.MyViewHolder> {
    private Context mContext;
   // private List<LocalTeamResponce.DataBean.TeamListBean> teamList;
   ArrayList<DataItem> dataItems;

    public MatchFactAdapter(Context mContext,ArrayList<DataItem> dataItems) {
        this.mContext = mContext;
        this.dataItems=dataItems;
     //   this.teamList = teamList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.match_fact_list_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        if (AboutMatchActivity.aboutMatchActivity.matches.getLocalTeam().getId()==dataItems.get(i).teamId)
        {
            holder.local_layout.setVisibility(View.VISIBLE);
            holder.visitor_layout.setVisibility(View.GONE);
            if (dataItems.get(i).type.equalsIgnoreCase("Goal")){
                holder.l_card_layout.setVisibility(View.GONE);
                holder.l_in_out_layout.setVisibility(View.GONE);
                holder.l_goal_layout.setVisibility(View.VISIBLE);
                holder.local_player_name.setText(dataItems.get(i).player_name);
                holder.l_time.setText(""+dataItems.get(i).minute+"'");
            }
            else if (dataItems.get(i).type.equalsIgnoreCase("yellowcard")){
                holder.l_card_layout.setVisibility(View.VISIBLE);
                holder.l_in_out_layout.setVisibility(View.GONE);
                holder.l_goal_layout.setVisibility(View.GONE);
                holder.l_player_card.setText(dataItems.get(i).player_name);
                holder.l_card_time.setText(""+dataItems.get(i).minute+"'");
                holder.l_card.setImageResource(R.drawable.ic_yellow_card);

            }
            else if (dataItems.get(i).type.equalsIgnoreCase("substitution")){
                holder.l_card_layout.setVisibility(View.GONE);
                holder.l_in_out_layout.setVisibility(View.VISIBLE);
                holder.l_goal_layout.setVisibility(View.GONE);
                holder.l_player_in_name.setText(dataItems.get(i).player_name);
                holder.l_player_out_name.setText(dataItems.get(i).related_player_name);
                holder.l_in_out_time.setText(""+dataItems.get(i).minute+"'");

            }
            else {
                holder.l_card_layout.setVisibility(View.VISIBLE);
                holder.l_in_out_layout.setVisibility(View.GONE);
                holder.l_goal_layout.setVisibility(View.GONE);
                holder.l_player_card.setText(dataItems.get(i).player_name);
                holder.l_card_time.setText(""+dataItems.get(i).minute+"'");
                holder.l_card.setImageResource(R.drawable.ic_red_card);
            }

        }
        else {
            holder.local_layout.setVisibility(View.GONE);
            holder.visitor_layout.setVisibility(View.VISIBLE);
            if (dataItems.get(i).type.equalsIgnoreCase("Goal")){
                holder.v_card_layout.setVisibility(View.GONE);
                holder.v_in_out_layout.setVisibility(View.GONE);
                holder.v_goal_layout.setVisibility(View.VISIBLE);
                holder.v_player_name.setText(dataItems.get(i).player_name);
                holder.v_time.setText(""+dataItems.get(i).minute+"'");
            }
            else if (dataItems.get(i).type.equalsIgnoreCase("yellowcard")){
                holder.v_card_layout.setVisibility(View.VISIBLE);
                holder.v_in_out_layout.setVisibility(View.GONE);
                holder.v_goal_layout.setVisibility(View.GONE);
                holder.v_card_name.setText(dataItems.get(i).player_name);
                holder.v_card_time.setText(""+dataItems.get(i).minute+"'");
                holder.v_card_icon.setImageResource(R.drawable.ic_yellow_card);
            }
            else if (dataItems.get(i).type.equalsIgnoreCase("substitution")){
                holder.v_card_layout.setVisibility(View.GONE);
                holder.v_in_out_layout.setVisibility(View.VISIBLE);
                holder.v_goal_layout.setVisibility(View.GONE);
                holder.v_in_player.setText(dataItems.get(i).player_name);
                holder.v_out_player.setText(dataItems.get(i).related_player_name);
                holder.v_in_out_time.setText(""+dataItems.get(i).minute+"'");

            }
            else {
                holder.v_card_layout.setVisibility(View.VISIBLE);
                holder.v_in_out_layout.setVisibility(View.GONE);
                holder.v_goal_layout.setVisibility(View.GONE);
                holder.v_card_name.setText(dataItems.get(i).player_name);
                holder.v_card_time.setText(""+dataItems.get(i).minute+"'");
                holder.v_card_icon.setImageResource(R.drawable.ic_red_card);
            }

        }

    }


    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout l_card_layout,l_in_out_layout,v_card_layout,v_in_out_layout,
                v_goal_layout,l_goal_layout;
        private LinearLayout visitor_layout,local_layout;
        private ImageView l_card,v_card_icon;
        private TextView local_player_name,l_time,l_player_card,l_in_out_time,l_player_out_name,l_player_in_name,l_card_time,
        v_player_name,v_time,v_card_name,v_card_time,v_in_out_time,v_out_player,v_in_player;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            local_player_name = itemView.findViewById(R.id.local_player_name);
            l_time = itemView.findViewById(R.id.l_time);
            l_player_card = itemView.findViewById(R.id.l_player_card);
            l_in_out_time = itemView.findViewById(R.id.l_in_out_time);
            l_player_out_name = itemView.findViewById(R.id.l_player_out_name);
            l_player_in_name = itemView.findViewById(R.id.l_player_in_name);
            v_player_name = itemView.findViewById(R.id.v_player_name);
            v_time = itemView.findViewById(R.id.v_time);
            v_card_name = itemView.findViewById(R.id.v_card_name);
            v_card_time = itemView.findViewById(R.id.v_card_time);
            v_in_out_time = itemView.findViewById(R.id.v_in_out_time);
            local_layout = itemView.findViewById(R.id.local_layout);
            visitor_layout = itemView.findViewById(R.id.visitor_layout);
            l_card_layout=itemView.findViewById(R.id.l_card_layout);
            l_in_out_layout=itemView.findViewById(R.id.l_in_out_layout);
            v_card_layout=itemView.findViewById(R.id.v_card_layout);
            v_in_out_layout=itemView.findViewById(R.id.v_in_out_layout);
            v_goal_layout=itemView.findViewById(R.id.v_goal_layout);
            l_goal_layout=itemView.findViewById(R.id.l_goal_layout);
            v_out_player=itemView.findViewById(R.id.v_out_player);
            v_in_player=itemView.findViewById(R.id.v_in_player);
            l_card_time=itemView.findViewById(R.id.l_card_time);
            v_card_icon=itemView.findViewById(R.id.v_card_icon);
            l_card=itemView.findViewById(R.id.l_card);

        }
    }


}
