package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.activity.LeagueFilteringActivity;
import com.wolfscore.responce.GetLeagueResponce;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 2/5/19.
 */

public class FilterTournamentAdapter extends RecyclerView.Adapter<FilterTournamentAdapter.MyviewHolder> {
    private List<GetLeagueResponce.DataBean.LeagueListBean> leagueList;
    private List<GetLeagueResponce.DataBean.LeagueListBean> tournamentListFiltered;
    private Context mContext;
    private LeagueFilteringActivity listener;

    public FilterTournamentAdapter(Context mContext, List<GetLeagueResponce.DataBean.LeagueListBean> leagueList, LeagueFilteringActivity listener) {
        this.leagueList = leagueList;
        this.tournamentListFiltered = leagueList;
        this.mContext = mContext;
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_tour_cell, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        if (tournamentListFiltered.size() != 0){
            final GetLeagueResponce.DataBean.LeagueListBean listBean = tournamentListFiltered.get(position);
            holder.tvLeagueName.setText(listBean.getLeague_name());
            Picasso.with(holder.ivTeam.getContext()).load(listBean.getLeague_flag()).placeholder(R.drawable.logo).into(holder.ivTeam);


            if (listBean.getIs_selected().equals("1")){
                holder.ivSelectTeam.setVisibility(View.VISIBLE);
                holder.ivPlus.setVisibility(View.INVISIBLE);
               // listener.filterTournamentOnClick(tournamentListFiltered.get(holder.getAdapterPosition()), "1");
            }else {
                holder.ivSelectTeam.setVisibility(View.GONE);
                holder.ivPlus.setVisibility(View.VISIBLE);
            }

            holder.rlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //"""""""" check / Uncheck  """""""""//
                    if (listBean.getIs_selected().equals("0")) {
                        holder.ivPlus.setVisibility(View.INVISIBLE);
                        holder.ivSelectTeam.setVisibility(View.VISIBLE);
                        listener.filterTournamentOnClick(tournamentListFiltered.get(holder.getAdapterPosition()), "1");
                    } else {
                        holder.ivPlus.setVisibility(View.VISIBLE);
                        holder.ivSelectTeam.setVisibility(View.GONE);
                        listener.filterTournamentOnClick(tournamentListFiltered.get(holder.getAdapterPosition()), "0");
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return tournamentListFiltered.size();
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



    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    tournamentListFiltered = leagueList;
                } else {
                    List<GetLeagueResponce.DataBean.LeagueListBean> filteredList = new ArrayList<>();
                    for (GetLeagueResponce.DataBean.LeagueListBean row : leagueList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getLeague_name().toLowerCase().contains(charString.toLowerCase())) {// || row.getPhone().contains(charSequence)
                            filteredList.add(row);
                        }
                    }

                    tournamentListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = tournamentListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tournamentListFiltered = (ArrayList<GetLeagueResponce.DataBean.LeagueListBean>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
   public interface FilterTournamentListenr{
       void filterTournamentOnClick(GetLeagueResponce.DataBean.LeagueListBean bean , String value);
    }
}



