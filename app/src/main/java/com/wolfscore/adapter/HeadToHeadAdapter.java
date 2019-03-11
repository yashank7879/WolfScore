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
import com.wolfscore.matches.modal.Matches;
import com.wolfscore.utils.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mindiii on 12/2/19.
 */

public class HeadToHeadAdapter extends RecyclerView.Adapter<HeadToHeadAdapter.MyViewHolder> {
    private Context mContext;
    ArrayList<Matches> matchesArrayList;
    DateFormat dateFormat = new SimpleDateFormat("DD-MM-yyyy");
    Calendar calendar;
    // private List<LocalTeamResponce.DataBean.TeamListBean> teamList;

    public HeadToHeadAdapter(Context mContext,ArrayList<Matches> matchesArrayList) {
        this.mContext = mContext;
        this.matchesArrayList=matchesArrayList;
        //   this.teamList = teamList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.head_to_head_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

       if (matchesArrayList.get(position).getMatchHeader()!=null){
           holder.league_group_round.setText(matchesArrayList.get(position).getMatchHeader().getName());
        }


        if (matchesArrayList.get(position).getLocalTeam()!=null) {
            holder.local_team.setText(matchesArrayList.get(position).getLocalTeam().getName());
            Picasso.with(mContext).load(matchesArrayList.get(position).getLocalTeam().getLogo_path())
                    .placeholder(R.drawable.app_icon).into(holder.local_icon);

        }
        if (matchesArrayList.get(position).getVisitorTeam()!=null)
        {
            holder.visiter_team.setText(matchesArrayList.get(position).getVisitorTeam().getName());
            Picasso.with(mContext).load(matchesArrayList.get(position).getVisitorTeam().getLogo_path())
                    .placeholder(R.drawable.app_icon).into(holder.visitor_icon);

        }
        if (matchesArrayList.get(position).getScore()!=null)
        {
            holder.score.setText(" "+matchesArrayList.get(position).getScore().getLocalteam_score()+"-"+
                    matchesArrayList.get(position).getScore().getVisitorteam_score()+" ");

        }

        if (matchesArrayList.get(position).getTime()!=null)
        {
            String time=  Constant.getFormatedDateTime(matchesArrayList.get(position).getTime().getTime(),"HH:mm:ss", "hh:mm a");
            Date dt1;
            try {
                 dt1=dateFormat.parse(matchesArrayList.get(position).getTime().getDate());
              //  Constant.getDayFromDate(dt1);
              String date=  Constant.getFormatedDateTime(matchesArrayList.get(position).getTime().getDate(),"yyyy-MM-dd","dd-MM-yyyy");
                holder.day_date.setText(Constant.getDayFromDate(dt1)+","+date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

/*
            if (matchesArrayList.get(position).getTime().getStatus().equals("NS")){
                holder.score_layout.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setText(time);
                holder.status.setBackgroundResource(R.drawable.green_bg);

            }
            else if(matchesArrayList.get(position).getTime().getStatus().equals("TBA"))
            {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);
                holder.score.setText(time);
                holder.status.setText("TBA");
                holder.status.setBackgroundResource(R.color.list_item_bg);
            }
            else if(matchesArrayList.get(position).getTime().getStatus().equals("FT"))
            {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);
                //  holder.score.setText(time);
                holder.status.setText("FT");
                holder.status.setBackgroundResource(R.drawable.red_bg);
            }
            else if(matchesArrayList.get(position).getTime().getStatus().equals("POSTP"))
            {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);
                holder.score.setText(time);
                holder.status.setText("POSTP");
                holder.status.setBackgroundResource(R.drawable.green_bg);
            }
            else if(matchesArrayList.get(position).getTime().getStatus().equals("DELAYED"))
            {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);
                holder.score.setText(time);
                holder.status.setText("DELAYED");
                holder.status.setBackgroundResource(R.drawable.green_bg);
            }
            else if(matchesArrayList.get(position).getTime().getStatus().equals("LIVE"))
            {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);
                holder.status.setText("Live");
                holder.status.setBackgroundResource(R.drawable.green_bg);
            }
            else {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);
                holder.score.setText(time);
                holder.status.setText(matchesArrayList.get(position).getTime().getStatus());
                holder.status.setBackgroundResource(R.drawable.green_bg);

            }*/

        }

    }


    @Override
    public int getItemCount() {
        return matchesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView local_team,visiter_team,score,status,time,day_date,league_group_round;
        ImageView visitor_icon,local_icon;
        LinearLayout score_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // rlView = (RelativeLayout) itemView.findViewById(R.id.rl_view);
            visiter_team=itemView.findViewById(R.id.visiter_team);
            local_team=itemView.findViewById(R.id.local_team);
            local_icon=itemView.findViewById(R.id.local_icon);
            visitor_icon=itemView.findViewById(R.id.visitor_icon);
            score=itemView.findViewById(R.id.score);
            score_layout=itemView.findViewById(R.id.score_layout);
            status=itemView.findViewById(R.id.status);
            time=itemView.findViewById(R.id.time);
            league_group_round=itemView.findViewById(R.id.league_group_round);
            day_date=itemView.findViewById(R.id.day_date);


        }
    }


}
