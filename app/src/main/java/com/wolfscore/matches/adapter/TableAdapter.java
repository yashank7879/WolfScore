package com.wolfscore.matches.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.wolfscore.R;
import com.wolfscore.activity.AboutMatchActivity;
import com.wolfscore.matches.modal.TableDTO;
import com.wolfscore.utils.SvgDecoder;
import com.wolfscore.utils.SvgDrawableTranscoder;
import com.wolfscore.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mindiii on 22/2/19.
 */

public class TableAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    HashMap<String, ArrayList<TableDTO>> tableHashMap;
    private List<String> mKeys = new ArrayList<>();
    private GenericRequestBuilder mRequestBuilder;

    public TableAdapter(Context context,  HashMap<String, ArrayList<TableDTO>> tableHashMap) {
        this._context = context;
        this.tableHashMap=tableHashMap;
        mKeys.addAll(this.tableHashMap.keySet());
        initGlideForSVG();

    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
/*
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
*/
       return tableHashMap.get(mKeys.get(groupPosition)).get(childPosititon);
      //  return childPosititon;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final TableDTO tableDTO = (TableDTO) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.table_list_item_layout, null);
        }

        TextView child_count,team_name,games_played,won,draw,lost,goals_scored,goals_against,goal_difference,points;
         child_count = (TextView) convertView.findViewById(R.id.child_count);
         team_name = (TextView) convertView.findViewById(R.id.team_name);
         games_played = (TextView) convertView.findViewById(R.id.games_played);
         won = (TextView) convertView.findViewById(R.id.won);
         draw = (TextView) convertView.findViewById(R.id.draw);
         lost = (TextView) convertView.findViewById(R.id.lost);
         goals_scored = (TextView) convertView.findViewById(R.id.goals_scored);
         goals_against = (TextView) convertView.findViewById(R.id.goals_against);
         goal_difference = (TextView) convertView.findViewById(R.id.goal_difference);
         points = (TextView) convertView.findViewById(R.id.points);
        CircleImageView team_logo=convertView.findViewById(R.id.team_logo);
        View highlight_view=convertView.findViewById(R.id.highlight_view);

         int count=childPosition+1;
        child_count.setText(""+count);
        team_name.setText(""+tableDTO.getTeam_name());
        games_played.setText(""+tableDTO.getGames_played());
        won.setText(""+tableDTO.getWon());
        draw.setText(""+tableDTO.getDraw());
        lost.setText(""+tableDTO.getLost());
        goals_scored.setText(""+tableDTO.getGoals_scored());
        goals_against.setText(""+tableDTO.getGoals_against());
        goal_difference.setText(""+tableDTO.getGoal_difference());
        points.setText(""+tableDTO.getPoints());
        loadImage(team_logo,tableDTO.getLogo_path());
        if (groupPosition==0)
        {
            if (childPosition<4)
            {
                highlight_view.setVisibility(View.VISIBLE);
                highlight_view.setBackgroundColor(_context.getResources().getColor(R.color.colorGreen));
            }
           else if (childPosition==4||childPosition==5)
            {
                highlight_view.setBackgroundColor(_context.getResources().getColor(R.color.colorBlue));
                highlight_view.setVisibility(View.VISIBLE);

            }
            else {
                highlight_view.setVisibility(View.GONE);

            }
        }
        else {
            highlight_view.setVisibility(View.GONE);

        }

        if (AboutMatchActivity.aboutMatchActivity!=null)
        {
            if (AboutMatchActivity.aboutMatchActivity.matches!=null)
            {
                if (AboutMatchActivity.aboutMatchActivity.matches.getLocalTeam().getName()
                        .equalsIgnoreCase(tableDTO.getTeam_name())||(AboutMatchActivity.aboutMatchActivity.matches.getVisitorTeam().getName()
                        .equalsIgnoreCase(tableDTO.getTeam_name()))){
                    convertView.setBackgroundColor(_context.getResources().getColor(R.color.Header_bg));
                }
                else {
                    convertView.setBackgroundColor(_context.getResources().getColor(R.color.list_item_bg));

                }

            }
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return tableHashMap.get(mKeys.get(groupPosition)).size();
      //  return 5;
    }

    @Override
    public Object getGroup(int groupPosition) {
       return tableHashMap.get(mKeys.get(groupPosition));
      //  return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return tableHashMap.size();
      //  return 4;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
     //  String headerTitle = (String) getGroup(groupPosition);
       String headerTitle = mKeys.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.table_header_item_layout, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
     //  lblListHeader.setText(headerTitle);
        lblListHeader.setText(headerTitle);

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);

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

    private void loadImage(ImageView mImageView, String url) {
        if (url.contains("svg")){
            mRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(Uri.parse(url))
                    .error(R.drawable.ic_team_placeholder)
                    .into(mImageView);
        } else if (url.contains("png")) {
            Glide.with(_context).load(url) .error(R.drawable.ic_team_placeholder).into(mImageView);
        }
        else {
            Glide.with(_context).load(url) .error(R.drawable.ic_team_placeholder).into(mImageView);

        }
    }
}