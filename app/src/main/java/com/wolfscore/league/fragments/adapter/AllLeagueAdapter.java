package com.wolfscore.league.fragments.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.league.fragments.leagueModel.Country;
import com.wolfscore.matches.modal.TableDTO;
import com.wolfscore.responce.GetLeagueResponce;
import com.wolfscore.utils.SvgDecoder;
import com.wolfscore.utils.SvgDrawableTranscoder;
import com.wolfscore.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 28/2/19.
 */

public class AllLeagueAdapter  extends BaseExpandableListAdapter implements Filterable {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    ArrayList<Country> countriesLeague;
    ArrayList<Country> countriesLeagueList = new ArrayList<>();;
    private List<String> mKeys = new ArrayList<>();
    private GenericRequestBuilder mRequestBuilder;
    private FavrouitTeamSelect listener;

    public AllLeagueAdapter(Context context, ArrayList<Country> countriesLeagueList,FavrouitTeamSelect listener) {
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
            convertView = infalInflater.inflate(R.layout.country_league_list_item, null);
        }
        TextView tv_team_name;
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

        }

        iv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(league.getIs_favorite()).equals("0")) {
                    iv_star.setImageDrawable(ContextCompat.getDrawable(_context, R.drawable.ic_active_star));
                    listener.favrouitSelectUnselect(league, "1", iv_star);
                } else {
                    iv_star.setImageDrawable(ContextCompat.getDrawable(_context, R.drawable.ic_white_star3x));

                    listener.favrouitSelectUnselect(league, "0",iv_star);
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
            mRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(Uri.parse(url))
                    .error(R.drawable.ic_team_placeholder)
                    .into(mImageView);
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
                        for (Country country: countriesLeague) {
                            ArrayList<Country.League> leagues = country.getLeagueArrayList();
                            ArrayList<Country.League> filterLeague = new ArrayList<>();
                            boolean flag = false;
                            for (Country.League league: leagues) {
                                if (league.getLeague_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                    filterLeague.add(league);
                                    flag = true;
                                }
                            }

                            country.setLeagueArrayList(leagues);
                            if (flag) {
                                country.setLeagueArrayList(filterLeague);
                                mFilter.add(country);
                            }
                        }

                        filterResults.values = mFilter;
                    //   notifyDataSetChanged();
                    } else {
                        filterResults.values = countriesLeague;
                      //  notifyDataSetChanged();
                    }
                }
                return filterResults;
               // return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countriesLeagueList = (ArrayList<Country>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public interface FavrouitTeamSelect {
        void favrouitSelectUnselect(Country.League league, String value, ImageView ivStar);
    }
}