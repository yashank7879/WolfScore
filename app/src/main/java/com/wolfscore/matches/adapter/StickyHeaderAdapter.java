package com.wolfscore.matches.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolfscore.R;
import com.wolfscore.matches.modal.Matches;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by mindiii on 4/2/19.
 */

public class StickyHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private LayoutInflater inflater;
    ArrayList<Matches> matchesArrayList;
    Context context;
    private Typeface robotoMedium;


    public StickyHeaderAdapter(Context context,   ArrayList<Matches> matchesArrayList) {
        inflater = LayoutInflater.from(context);
        this.matchesArrayList=matchesArrayList;
        this.context=context;
        robotoMedium = ResourcesCompat.getFont(context, R.font.roboto_medium);
    }

    @Override
    public int getCount() {
        return matchesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.matches_list_item_cell_view, parent, false);
            holder.visiter_team=convertView.findViewById(R.id.visiter_team);
            holder.local_team=convertView.findViewById(R.id.local_team);
            holder.local_icon=convertView.findViewById(R.id.local_icon);
            holder.visitor_icon=convertView.findViewById(R.id.visitor_icon);
            holder.score=convertView.findViewById(R.id.score);
            holder.score_layout=convertView.findViewById(R.id.score_layout);
            holder.status=convertView.findViewById(R.id.status);
            holder.time=convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (matchesArrayList.get(position).getLocalTeam()!=null) {
            holder.local_team.setText(matchesArrayList.get(position).getLocalTeam().getName());
            Picasso.with(context).load(matchesArrayList.get(position).getLocalTeam().getLogo_path())
                    .placeholder(R.drawable.app_icon).into(holder.local_icon);

        }
        if (matchesArrayList.get(position).getVisitorTeam()!=null)
        {
            holder.visiter_team.setText(matchesArrayList.get(position).getVisitorTeam().getName());
            Picasso.with(context).load(matchesArrayList.get(position).getVisitorTeam().getLogo_path())
                    .placeholder(R.drawable.app_icon).into(holder.visitor_icon);

        }
        if (matchesArrayList.get(position).getScore()!=null)
        {
            holder.score.setText(" "+matchesArrayList.get(position).getScore().getLocalteam_score()+"-"+
                    matchesArrayList.get(position).getScore().getVisitorteam_score()+" ");

        }
        if (matchesArrayList.get(position).getTime()!=null)
        {
            String time=   getFormatedDateTime(matchesArrayList.get(position).getTime().getTime(),"HH:mm:ss", "hh:mm a");

            if (matchesArrayList.get(position).getTime().getStatus().equals("NS")){
                holder.score_layout.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setText(time);

            }
            else if(matchesArrayList.get(position).getTime().getStatus().equals("TBA"))
            {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);
                holder.score.setText(time);
                holder.status.setText("TBA");
                holder.status.setBackgroundResource(R.drawable.green_bg);
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
            }

        }
        else {
        }
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.matches_list_header_layout, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header_txt);
            holder.header_img=(ImageView)convertView.findViewById(R.id.header_img);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText=   matchesArrayList.get(position).getHeaderName();
        holder.text.setText(headerText);
        holder.text.setTypeface(robotoMedium);
        Picasso.with(context).load("https:\\/\\/cdn.sportmonks.com\\/images\\/soccer\\/teams\\/7\\/2279.png")
                .placeholder(R.drawable.app_icon).into( holder.header_img);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
      return   matchesArrayList.get(position).getHeaderId();
    }

    class HeaderViewHolder {
        TextView text;
        ImageView header_img;
    }

    class ViewHolder {
        TextView local_team,visiter_team,score,status,time;
        ImageView visitor_icon,local_icon;
        LinearLayout score_layout;
    }

    public static String getFormatedDateTime(String dateStr, String strReadFormat, String strWriteFormat) {
        String formattedDate = dateStr;
        DateFormat readFormat = new SimpleDateFormat(strReadFormat, Locale.getDefault());
        DateFormat writeFormat = new SimpleDateFormat(strWriteFormat, Locale.getDefault());
        Date date = null;
        try {
            date = readFormat.parse(dateStr);
        } catch (ParseException e) {
        }
        if (date != null) {formattedDate = writeFormat.format(date);}
        return formattedDate;
    }

}