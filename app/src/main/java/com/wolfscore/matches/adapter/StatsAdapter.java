package com.wolfscore.matches.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wolfscore.R;

/**
 * Created by mindiii on 19/2/19.
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.MyViewHolder> {
    private Context mContext;

    public StatsAdapter(Context mContext) {
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stats_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView local_team;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
               //   local_team=itemView.findViewById(R.id.local_team);
        }
    }


}