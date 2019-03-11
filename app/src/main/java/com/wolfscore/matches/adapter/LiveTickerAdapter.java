package com.wolfscore.matches.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolfscore.R;
import com.wolfscore.matches.modal.CommentryDTO;

import java.util.ArrayList;

/**
 * Created by mindiii on 25/2/19.
 */

public class LiveTickerAdapter extends RecyclerView.Adapter<LiveTickerAdapter.MyViewHolder> {
    private Context mContext;
    private  ArrayList<CommentryDTO> commentryList;
    public LiveTickerAdapter(Context mContext, ArrayList<CommentryDTO> commentryList) {
        this.mContext = mContext;
        this.commentryList=commentryList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.live_ticker_list_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (position==0)
        {
            holder.timer.setVisibility(View.GONE);
        }
        else{
            holder.timer.setVisibility(View.VISIBLE);
        }
        if (commentryList.get(position).isGoal())
        {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText("GOAL");
            holder.title.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            holder.timer.setBackground(mContext.getResources().getDrawable(R.drawable.green_bg));

        }
        else if(commentryList.get(position).isImportant())
        {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText("IMPORTANT");
            holder.title.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
            holder.timer.setBackground(mContext.getResources().getDrawable(R.drawable.blue_bg));

        }else {
            holder.title.setVisibility(View.GONE);
            holder.timer.setBackground(mContext.getResources().getDrawable(R.drawable.gray_border_bg));

        }
        holder.comment.setText(commentryList.get(position).getComment());
        holder.timer.setText(""+commentryList.get(position).getMinute());



    }

    @Override
    public int getItemCount() {
        return commentryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView timer,title,comment;
        ImageView timer_icon;
        View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timer=itemView.findViewById(R.id.timer);
            title=itemView.findViewById(R.id.title);
            comment=itemView.findViewById(R.id.comment);
            timer_icon=itemView.findViewById(R.id.timer_icon);
            view=itemView.findViewById(R.id.view);
        }
    }


}