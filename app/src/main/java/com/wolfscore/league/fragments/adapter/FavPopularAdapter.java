package com.wolfscore.league.fragments.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.activity.AboutLeagueActivity;
import com.wolfscore.adapter.TopPlayerAdapter;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.listener.PlayerOnClick;
import com.wolfscore.responce.GetLeagueResponce;
import com.wolfscore.responce.TopPlayerResponce;
import com.wolfscore.utils.SvgDecoder;
import com.wolfscore.utils.SvgDrawableTranscoder;
import com.wolfscore.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 5/3/19.
 */

public class FavPopularAdapter extends RecyclerView.Adapter<FavPopularAdapter.MyViewHolder> {
    private Context mContext;
   // private PlayerOnClick listener;
    List<Country.League> fav_leagueArrayList;
    List<Country.League> filtered_leagueArrayList;
    private GenericRequestBuilder mRequestBuilder;
    private AllLeagueAdapter.FavrouitTeamSelect listener;
    private  LinearLayout linearLayout;

    public FavPopularAdapter(Context mContext, List<Country.League> fav_leagueArrayList, LinearLayout linearLayout, AllLeagueAdapter.FavrouitTeamSelect listener) {
        this.mContext = mContext;
        this.fav_leagueArrayList = fav_leagueArrayList;
        this.filtered_leagueArrayList=fav_leagueArrayList;
        this.listener = listener;
        this.linearLayout=linearLayout;
        initGlideForSVG();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_league_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        if (fav_leagueArrayList.size() != 0) {
            holder.dividerView.setVisibility(View.VISIBLE);
        //   Picasso.with(mContext).load(fav_leagueArrayList.get(i).getLeague_flag()).fit().into(holder.iv_team);
            holder.tv_team_name.setText(fav_leagueArrayList.get(i).getLeague_name());
            if (String.valueOf(fav_leagueArrayList.get(i).getIs_favorite()).equals("1")) {// show plus icon
                holder.iv_star.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_active_star));

            } else {
                holder.iv_star.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_white_star3x));

            }
            loadImage( holder.iv_team,fav_leagueArrayList.get(i).getLeague_flag());
           holder.main_layout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Activity activity = (Activity) mContext;
                   mContext.startActivity(new Intent(mContext, AboutLeagueActivity.class)
                           .putExtra("League",  fav_leagueArrayList.get(i)));
                   activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

               }
           });
            holder.fav_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (String.valueOf(fav_leagueArrayList.get(i).getIs_favorite()).equals("0")) {
                        holder.iv_star.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_active_star));
                        listener.favrouitSelectUnselect(fav_leagueArrayList.get(i), "1", holder.iv_star);
                    } else {
                        holder.iv_star.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_white_star3x));

                        listener.favrouitSelectUnselect(fav_leagueArrayList.get(i), "0",holder.iv_star);
                    }
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return fav_leagueArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_team_name;
        CircleImageView iv_team;
        ImageView iv_star;
        View dividerView;
        RelativeLayout main_layout,fav_layout;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_team_name = (TextView) view.findViewById(R.id.tv_team_name);
            iv_team = (CircleImageView) view.findViewById(R.id.iv_team);
            iv_star=(ImageView)view.findViewById(R.id.iv_star);
            dividerView=(View)view.findViewById(R.id.view);
            main_layout=(RelativeLayout)view.findViewById(R.id.main_layout);
            fav_layout=(RelativeLayout)view.findViewById(R.id.fav_layout);
              }
    }

    private void initGlideForSVG() {
        mRequestBuilder = Glide.with(mContext)
                .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

    private void loadImage(ImageView mImageView,String url) {
        if (url.contains("svg")){
            mRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(Uri.parse(url))
                    .error(R.drawable.ic_team_placeholder)
                    .into(mImageView);
        } else if (url.contains("png")) {
            Glide.with(mContext).load(url) .error(R.drawable.ic_team_placeholder).into(mImageView);
        }
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    fav_leagueArrayList = filtered_leagueArrayList;
                } else {
                    List<Country.League> filteredList = new ArrayList<>();
                    for (Country.League row : filtered_leagueArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getLeague_name().toLowerCase().contains(charString.toLowerCase())) {// || row.getPhone().contains(charSequence)
                            filteredList.add(row);
                        }
                    }

                    fav_leagueArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = fav_leagueArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                fav_leagueArrayList = (ArrayList<Country.League>) filterResults.values;
                if (fav_leagueArrayList.size()==0)
                {
                    linearLayout.setVisibility(View.GONE);
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


}

