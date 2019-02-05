package com.wolfscore.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wolfscore.R;
import com.wolfscore.fragment.LiveFragment;
import com.wolfscore.fragment.SearchTeamFragment;
import com.wolfscore.matches.fragments.MatchListFragment;
import com.wolfscore.matches.fragments.YesterdayFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout match_layout, live_score_layout, league_layout, news_layout, highlight_layout;
    ImageView match_img, live_score_img, league_img, news_img, highlight_img;
    TextView match, live_score, league, news, highlight;
    RelativeLayout rl_search;
    FrameLayout search_fragment;
    private Typeface robotoregular;
    ImageView filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFragment(new MatchListFragment());
        initialisation();

    }

    private void initialisation() {
        match_layout = findViewById(R.id.match_layout);
        live_score_layout = findViewById(R.id.live_score_layout);
        league_layout = findViewById(R.id.league_layout);
        news_layout = findViewById(R.id.news_layout);
        highlight_layout = findViewById(R.id.highlight_layout);
        match_img = findViewById(R.id.match_img);
        live_score_img = findViewById(R.id.live_score_img);
        league_img = findViewById(R.id.league_img);
        news_img = findViewById(R.id.news_img);
        highlight_img = findViewById(R.id.highlight_img);
        match = findViewById(R.id.match);
        live_score = findViewById(R.id.live_score);
        league = findViewById(R.id.league);
        news = findViewById(R.id.news);
        highlight = findViewById(R.id.highlight);
        rl_search=findViewById(R.id.rl_search);
        search_fragment=findViewById(R.id.search_fragment);
        filter=findViewById(R.id.filter);
        match_layout.setOnClickListener(this);
        live_score_layout.setOnClickListener(this);
        league_layout.setOnClickListener(this);
        news_layout.setOnClickListener(this);
        highlight_layout.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        filter.setOnClickListener(this);
        robotoregular = ResourcesCompat.getFont(this, R.font.roboto_regular);

    }

    protected void replaceFragment(Fragment fragment) {
        try {
            String backStateName = fragment.getClass().getName();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.container, fragment);
               /* if (backStateName.equals("com.wolfscore.matches.fragments.MatchListFragment")) {
                    ft.addToBackStack(backStateName);
                } else {
                    ft.addToBackStack(null);
                }*/
                ft.commit();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.match_layout:
                search_fragment.setVisibility(View.GONE);

                match_img.setColorFilter(getResources().getColor(R.color.colorBlue));
                live_score_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                news_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                league_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                highlight_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                match.setTextColor(getResources().getColor(R.color.colorBlue));
                league.setTextColor(getResources().getColor(R.color.inactive_txt));
                live_score.setTextColor(getResources().getColor(R.color.inactive_txt));
                news.setTextColor(getResources().getColor(R.color.inactive_txt));
                highlight.setTextColor(getResources().getColor(R.color.inactive_txt));


                replaceFragment(new MatchListFragment());
                break;
            case R.id.live_score_layout:
                search_fragment.setVisibility(View.GONE);

                match_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                live_score_img.setColorFilter(getResources().getColor(R.color.colorBlue));
                news_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                league_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                highlight_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                match.setTextColor(getResources().getColor(R.color.inactive_txt));
                league.setTextColor(getResources().getColor(R.color.inactive_txt));
                live_score.setTextColor(getResources().getColor(R.color.colorBlue));
                news.setTextColor(getResources().getColor(R.color.inactive_txt));
                highlight.setTextColor(getResources().getColor(R.color.inactive_txt));

                replaceFragment(new LiveFragment());
                break;
            case R.id.league_layout:
                search_fragment.setVisibility(View.GONE);

                match_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                live_score_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                news_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                league_img.setColorFilter(getResources().getColor(R.color.colorBlue));
                highlight_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                match.setTextColor(getResources().getColor(R.color.inactive_txt));
                league.setTextColor(getResources().getColor(R.color.colorBlue));
                live_score.setTextColor(getResources().getColor(R.color.inactive_txt));
                news.setTextColor(getResources().getColor(R.color.inactive_txt));
                highlight.setTextColor(getResources().getColor(R.color.inactive_txt));

                //  replaceFragment(new MatchListFragment());
                break;
            case R.id.news_layout:
                search_fragment.setVisibility(View.GONE);

                match_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                live_score_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                news_img.setColorFilter(getResources().getColor(R.color.colorBlue));
                league_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                highlight_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                match.setTextColor(getResources().getColor(R.color.inactive_txt));
                league.setTextColor(getResources().getColor(R.color.inactive_txt));
                live_score.setTextColor(getResources().getColor(R.color.inactive_txt));
                news.setTextColor(getResources().getColor(R.color.colorBlue));
                highlight.setTextColor(getResources().getColor(R.color.inactive_txt));

                //  replaceFragment(new MatchListFragment());
                break;
            case R.id.highlight_layout:
                search_fragment.setVisibility(View.GONE);

                match_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                live_score_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                news_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                league_img.setColorFilter(getResources().getColor(R.color.inactive_txt));
                highlight_img.setColorFilter(getResources().getColor(R.color.colorBlue));
                match.setTextColor(getResources().getColor(R.color.inactive_txt));
                league.setTextColor(getResources().getColor(R.color.inactive_txt));
                live_score.setTextColor(getResources().getColor(R.color.inactive_txt));
                news.setTextColor(getResources().getColor(R.color.inactive_txt));
                highlight.setTextColor(getResources().getColor(R.color.colorBlue));

                //   replaceFragment(new MatchListFragment());
                break;

            case R.id.rl_search:
                search_fragment.setVisibility(View.VISIBLE);
            replacesearchFragment(new SearchTeamFragment());
            break;
            case R.id.filter:
                startActivity(new Intent(this,LeagueFilteringActivity.class));
                break;
            default:
        }
    }

    protected void replacesearchFragment(Fragment fragment) {
        try {
            String backStateName = fragment.getClass().getName();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(R.id.search_fragment, fragment);
               if (backStateName.equals("com.wolfscore.matches.fragments.MatchListFragment")) {
                    ft.addToBackStack(backStateName);
                } else {
                    ft.addToBackStack(null);
                }
                ft.commit();
            }
        } catch (Exception e) {

        }
    }

}


