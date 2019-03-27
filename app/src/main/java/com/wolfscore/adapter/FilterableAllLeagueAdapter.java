package com.wolfscore.adapter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
import com.wolfscore.league.fragments.adapter.AllLeagueAdapter;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.utils.SvgDecoder;
import com.wolfscore.utils.SvgDrawableTranscoder;
import com.wolfscore.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 14/3/19.
 */

public class FilterableAllLeagueAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    ArrayList<Country> countriesLeague;
    ArrayList<Country> countriesLeagueList = new ArrayList<>();;
    private List<String> mKeys = new ArrayList<>();
    private GenericRequestBuilder mRequestBuilder;
    private FilteredFavAdapter.FilterTournamentListenr  listener;

    public FilterableAllLeagueAdapter(Context context, ArrayList<Country> countriesLeagueList, FilteredFavAdapter.FilterTournamentListenr listener) {
        this._context = context;
        this.countriesLeague = countriesLeagueList;
        this.countriesLeagueList=countriesLeagueList;
        this.listener=listener;
        //  mKeys.addAll(this.tableHashMap.keySet());
        initGlideForSVG();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return countriesLeagueList.get(groupPosition).getLeagueArrayList().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Country.League league = (Country.League) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.filter_tour_cell, null);
        }
      /*  TextView tv_team_name;
        CircleImageView iv_team=convertView.findViewById(R.id.iv_team);
        iv_team.setVisibility(View.GONE);

        final ImageView arrow, iv_star;

        tv_team_name=convertView.findViewById(R.id.tv_team_name);
        iv_star=convertView.findViewById(R.id.iv_star);
        arrow=convertView.findViewById(R.id.arrow);
        iv_star.setVisibility(View.VISIBLE);
        arrow.setVisibility(View.GONE);
        tv_team_name.setText(league.getLeague_name());
        if (String.valueOf(league.getIs_favorite()).equals("1")) {// show plus icon
            iv_star.setImageDrawable(ContextCompat.getDrawable(_context, R.drawable.ic_active_star));

        } else {
            iv_star.setImageDrawable(ContextCompat.getDrawable(_context, R.drawable.ic_white_star3x));

        }*/



        /////////////////////
        RelativeLayout  rlView = (RelativeLayout) convertView.findViewById(R.id.rl_view);
        CircleImageView  ivTeam = (CircleImageView) convertView.findViewById(R.id.iv_team);
        TextView tvLeagueName = (TextView) convertView.findViewById(R.id.tv_league_name);
        ImageView ivPlus = (ImageView) convertView.findViewById(R.id.iv_plus);
        ImageView ivSelectTeam = (ImageView) convertView.findViewById(R.id.iv_select_team);
        TextView country_name=(TextView)convertView.findViewById(R.id.country_name);


        tvLeagueName.setText(league.getLeague_name());


        country_name.setVisibility(View.GONE);
        Picasso.with(_context).load(league.getLeague_flag()).placeholder(R.drawable.logo).into(ivTeam);


        if (league.getIs_selected()==1){
            ivSelectTeam.setVisibility(View.VISIBLE);
            ivPlus.setVisibility(View.INVISIBLE);
            // listener.filterTournamentOnClick(tournamentListFiltered.get(holder.getAdapterPosition()), "1");
        }else {
            ivSelectTeam.setVisibility(View.GONE);
            ivPlus.setVisibility(View.VISIBLE);
        }

        rlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"""""""" check / Uncheck  """""""""//
                if (league.getIs_selected()==0) {
                    ivPlus.setVisibility(View.INVISIBLE);
                    ivSelectTeam.setVisibility(View.VISIBLE);
                    listener.filterTournamentOnClick(league, "1");

                    //  listener.filterTournamentOnClick(tournamentListFiltered.get(holder.getAdapterPosition()), "1");
                } else {
                    ivPlus.setVisibility(View.VISIBLE);
                    ivSelectTeam.setVisibility(View.GONE);
                    listener.filterTournamentOnClick(league, "0");

                    //   listener.filterTournamentOnClick(tournamentListFiltered.get(holder.getAdapterPosition()), "0");
                }
            }
        });





        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return countriesLeagueList.get(groupPosition).getLeagueArrayList().size();
        //  return 5;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return countriesLeagueList.get(groupPosition);
        //  return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return countriesLeagueList.size();
        //  return 4;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Country country = (Country) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.country_league_list_item, null);
        }

        CircleImageView iv_team=convertView.findViewById(R.id.iv_team);
        RelativeLayout  fav_layout=(RelativeLayout)convertView.findViewById(R.id.fav_layout);
        fav_layout.setVisibility(View.GONE);

        /*Picasso.with(_context)
                .load(country.getCountry_flag())
                .into(iv_team);*/
        loadImage(iv_team,country.getCountry_flag());

        TextView tv_team_name;
        tv_team_name=convertView.findViewById(R.id.tv_team_name);
        tv_team_name.setText(country.getCountry_name());

        ImageView arrow, iv_star;
        iv_star =convertView.findViewById(R.id.iv_star);
        iv_star.setVisibility(View.GONE);
        arrow=convertView.findViewById(R.id.arrow);
        //  arrow.setVisibility(View.VISIBLE);


      /*  ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
*/
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initGlideForSVG() {
        mRequestBuilder = Glide.with(_context)
                .using(Glide.buildStreamModelLoader(Uri.class, _context), InputStream.class)
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
/*
            mRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(Uri.parse(url))
                    .error(R.drawable.ic_team_placeholder)
                    .into(mImageView);
*/
        } else if (url.contains("png")) {
            Glide.with(_context).load(url) .error(R.drawable.ic_team_placeholder).into(mImageView);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    if (constraint.length() > 0) {
                        ArrayList<Country> mFilter = new ArrayList<>();
                        for (Country country : countriesLeague) {
                            ArrayList<Country.League> leagues = country.getLeagueArrayList();
                            ArrayList<Country.League> filterLeague = new ArrayList<>();

                            boolean flag = false;
                            for (Country.League league : leagues) {
                                if (league.getLeague_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                    filterLeague.add(league);
                                    flag = true;
                                }
                            }

                            if (flag) {
                                Country newCountry = new Country();
                                newCountry.setLeagueArrayList(filterLeague);
                                newCountry.setCountry_flag(country.getCountry_flag());
                                newCountry.setCountry_name(country.getCountry_name());
                                newCountry.setCountry_id(country.getCountry_id());
                                mFilter.add(newCountry);
                            }

                        }

                        filterResults.values = mFilter;
                    } else {
                        filterResults.values = countriesLeague;

                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countriesLeagueList = (ArrayList<Country>) results.values;
                notifyDataSetChanged();

            }
        };
    }

}