package com.wolfscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.responce.*;
import com.wolfscore.responce.GetLeagueResponce;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 7/3/19.
 */

public class LeagueListAdapter extends RecyclerView.Adapter<LeagueListAdapter.MyviewHolder>  {
    private List<com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean> leagueList;
    private List<com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean> tournamentListFiltered;
    private Context mContext;
    private FavoriteUnFavorite listener;

    public LeagueListAdapter(Context mContext, List<com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean> leagueList, FavoriteUnFavorite listener) {
        this.leagueList = leagueList;
        this.tournamentListFiltered = leagueList;
        this.mContext = mContext;
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_league_list_item, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        if (tournamentListFiltered.size() != 0){
            final com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean listBean = tournamentListFiltered.get(position);
            holder.tv_team_name.setText(listBean.getLeague_name());
         //   holder.country_name.setText(listBean.getCountry_name());
            Picasso.with(holder.ivTeam.getContext()).load(listBean.getLeague_flag()).fit().placeholder(R.drawable.logo).into(holder.ivTeam);
            holder.main_layout.setBackgroundColor(mContext.getResources().getColor(R.color.Header_bg));
            holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBalck));
            holder.view.setVisibility(View.VISIBLE);
            if (listBean.getIs_favorite().equals("1")){
                    holder.iv_star.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_active_star));
            }else {
                holder.iv_star.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_white_star3x));
            }


            holder.iv_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (String.valueOf(listBean.getIs_favorite()).equals("0")) {
                        holder.iv_star.setImageResource(R.drawable.ic_active_star);
                        listener.favUnfav(listBean, "1",  holder.iv_star);
                    } else if (String.valueOf(listBean.getIs_favorite()).equals("1")){
                        holder.iv_star.setImageResource(R.drawable.ic_white_star3x);
                        listener.favUnfav(listBean, "0",  holder.iv_star);
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
        private RelativeLayout main_layout;
        private CircleImageView ivTeam;
        private TextView tv_team_name;
        private ImageView iv_star;
        private View view;

        MyviewHolder(View itemView) {
            super(itemView);
            main_layout = (RelativeLayout) itemView.findViewById(R.id.main_layout);
            ivTeam = (CircleImageView) itemView.findViewById(R.id.iv_team);
            tv_team_name = (TextView) itemView.findViewById(R.id.tv_team_name);
            iv_star = (ImageView) itemView.findViewById(R.id.iv_star);
            view = (View) itemView.findViewById(R.id.view);
         }
    }


/*
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    if (constraint.length() > 0) {
                        ArrayList<com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean> mFilter = new ArrayList<>();
                        for (com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean country: leagueList) {
                                  if (country.getLeague_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                      mFilter.add(country);
                            }
                        }
                        tournamentListFiltered = mFilter;

                    } else {
                        tournamentListFiltered = leagueList;

                    }
                    notifyDataSetChanged();
                    Log.d("listFragmnet", "afterTextChanged: notifyDataSetChanged");
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
*/

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    tournamentListFiltered = leagueList;
                } else {
                    List<com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean> filteredList = new ArrayList<>();
                    for (com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean row : leagueList) {

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


    public interface FavoriteUnFavorite{
        void favUnfav(com.wolfscore.responce.GetLeagueResponce.DataBean.LeagueListBean bean, String value, ImageView ivStar);
    }
}




