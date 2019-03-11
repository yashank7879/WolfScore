package com.wolfscore.matches.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wolfscore.R;
import com.wolfscore.activity.VideoPlayActivity;
import com.wolfscore.matches.modal.CommentryDTO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mindiii on 26/2/19.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<CommentryDTO> mediaList;
    Bitmap bitmap = null;

    public MediaAdapter(Context mContext,ArrayList<CommentryDTO> mediaList) {
        this.mContext = mContext;
        this.mediaList=mediaList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_list_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

       /* try {
            bitmap = retriveVideoFrameFromVideo(mediaList.get(position).getLocation());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
            holder.vedio_img.setImageBitmap(bitmap);
        }*/

       //ToDo: Resolve this line error
   //     mediaList.get(position).get
        holder.time.setText(""+mediaList.get(position).getDate());
        Glide.with(mContext).load(mediaList.get(position).getLocation()).asBitmap().into(holder.vedio_img);


        holder.video_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, VideoPlayActivity.class)
                        .putExtra("url",mediaList.get(position).getLocation()));
            }
        });



    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView local_team,time;
        ImageView vedio_img;
        RelativeLayout video_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vedio_img=itemView.findViewById(R.id.vedio_img);
            video_layout=itemView.findViewById(R.id.video_layout);
            time=itemView.findViewById(R.id.time);
        }

    }
    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

}