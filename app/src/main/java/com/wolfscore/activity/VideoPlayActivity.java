package com.wolfscore.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.wolfscore.R;

/**
 * Created by mindiii on 26/2/19.
 */

public class VideoPlayActivity extends AppCompatActivity {
    String videourl="";
    VideoView videoView;
    ProgressBar progressBar = null;
    ImageView cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_play);
        videourl=getIntent().getStringExtra("url");
        videoView=(VideoView)findViewById(R.id.videoView);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        cancel=findViewById(R.id.cancel);
        Uri uri = Uri.parse(videourl); //Declare your url here.
        // videoView.setMediaController(new MediaController(this));
        videoView.setMediaController(null);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        progressBar.setVisibility(View.VISIBLE);


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        progressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);

            }
        });
    }
}
