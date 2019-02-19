package com.wolfscore.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikesu.horizontalexpcalendar.HorizontalExpCalendar;
import com.mikesu.horizontalexpcalendar.common.Config;
import com.mikesu.horizontalexpcalendar.common.Marks;
import com.wolfscore.CalenderListener;
import com.wolfscore.R;
import com.wolfscore.fragment.LiveFragment;
import com.wolfscore.fragment.SearchTeamFragment;
import com.wolfscore.matches.fragments.BlankFragment;
import com.wolfscore.matches.fragments.CalenderFragment;
import com.wolfscore.matches.fragments.MatchListFragment;
import com.wolfscore.matches.fragments.SelectedDateLeagueFragment;
import com.wolfscore.matches.fragments.TodayFragment;
import com.wolfscore.matches.fragments.TomorrowFragment;
import com.wolfscore.matches.fragments.YesterdayFragment;
import com.wolfscore.matches.modal.FilteredEvent;
import com.wolfscore.matches.modal.Matches;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout match_layout, live_score_layout, league_layout, news_layout, highlight_layout;
    ImageView match_img, live_score_img, league_img, news_img, highlight_img;
    TextView match, live_score, league, news, highlight,current_date;
  public   RelativeLayout rl_search,calander_layout;
    FrameLayout search_fragment;
    private Typeface robotoregular;
   public ImageView filter,calender;
    public static HomeActivity homeActivity;
    private HorizontalExpCalendar horizontalExpCalendar;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat currentDateFormat = new SimpleDateFormat("dd");
    Calendar cal_yesterday = Calendar.getInstance();
    Calendar cal_tomorrow = Calendar.getInstance();
    Calendar cal_today = Calendar.getInstance();
    String yesterday_date="",tomorrow_date="",today_date="";
    MatchListFragment matchListFragment = null;
    Context context;
    public int current_item=0;
    public FilteredEvent event=new FilteredEvent();

    protected FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;
    protected Bundle mSavedInstanceState;
   public ArrayList<Matches> tomorrowMatchesArrayList =new ArrayList<>();
   public ArrayList<Matches> yesterdayMatchesArrayList=new ArrayList<>();
   public ArrayList<Matches> todayMatchesArrayList=new ArrayList<>();
    private HorizontalExpCalendar.HorizontalExpCalListener horizontalExpCalListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      //  matchListFragment = new MatchListFragment();
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        replaceFragment(new MatchListFragment());
        homeActivity=this;
        context=this;
        mSavedInstanceState = savedInstanceState;
        initialisation();

    }

    private void initialisation() {
        horizontalExpCalendar = (HorizontalExpCalendar)findViewById(R.id.calendar);
        EventBus.getDefault().register(this);
        tomorrowMatchesArrayList =new ArrayList<>();
        yesterdayMatchesArrayList=new ArrayList<>();
        todayMatchesArrayList=new ArrayList<>();
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
        current_date=findViewById(R.id.current_date);
        calander_layout=findViewById(R.id.calander_layout);

        calander_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: This will prevent the click over scroll view behind calender
            }
        });
        filter=findViewById(R.id.filter);
        calender=findViewById(R.id.calender);
        match_layout.setOnClickListener(this);
        live_score_layout.setOnClickListener(this);
        league_layout.setOnClickListener(this);
        news_layout.setOnClickListener(this);
        highlight_layout.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        filter.setOnClickListener(this);
        calender.setOnClickListener(this);
        robotoregular = ResourcesCompat.getFont(this, R.font.roboto_regular);
        cal_yesterday.add(Calendar.DATE, -1);
        yesterday_date= dateFormat.format(cal_yesterday.getTime());
        cal_tomorrow.add(Calendar.DATE, 1);
        tomorrow_date= dateFormat.format(cal_tomorrow.getTime());
        today_date= dateFormat.format(cal_today.getTime());
        String date=currentDateFormat.format(cal_today.getTime());
        current_date.setText(date);

        ////
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        horizontalExpCalendar.setHorizontalExpCalListener(horizontalExpCalListener);
        //this.horizontalExpCalListener = horizontalExpCalListener;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        //unregister eventbus here
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(FilteredEvent event){
        this.event=event;
        Bundle bundle=new Bundle();
        bundle.putSerializable("event",event);


        fragmentManager = getSupportFragmentManager();
        //check if on orientation change.. do not re-add fragments!
        if(mSavedInstanceState == null) {
            //instantiate the fragment manager

            fragmentTransaction = fragmentManager.beginTransaction();

            //the navigation fragments
            MatchListFragment matchListFragment=new MatchListFragment();
            matchListFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, matchListFragment);
            fragmentTransaction.commitAllowingStateLoss();


        }


    }

    protected void replaceFragment(Fragment fragment) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("day","");
            bundle.putSerializable("event",event);
            fragment.setArguments(bundle);
            String backStateName = fragment.getClass().getName();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.right_in,  R.anim.left_out);

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

    protected void replaceFragment(Fragment fragment,Bundle bundle) {
        try {
            String backStateName = fragment.getClass().getName();
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.right_in,  R.anim.left_out);
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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.match_layout:
                search_fragment.setVisibility(View.GONE);
                calander_layout.setVisibility(View.GONE);
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
                calander_layout.setVisibility(View.GONE);
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
                calander_layout.setVisibility(View.GONE);
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
                calander_layout.setVisibility(View.GONE);

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
                calander_layout.setVisibility(View.GONE);

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
                calander_layout.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                String myMessage = "search";
                bundle.putString("date", myMessage );
                replacesearchFragment(new SearchTeamFragment(),bundle);
            break;
           /* case R.id.filter:
                calander_layout.setVisibility(View.GONE);
                startActivity(new Intent(this,LeagueFilteringActivity.class));
                break;*/

            case R.id.calender:
                calander_layout.setVisibility(View.VISIBLE);
                horizontalExpCalendar.scrollToDate(new DateTime(), true, true, true);

                View mark_today_view= horizontalExpCalendar.findViewById(com.mikesu.horizontalexpcalendar.R.id.mark_today_view);
                mark_today_view.setVisibility(View.VISIBLE);
                TextView btnClose = horizontalExpCalendar.findViewById(com.mikesu.horizontalexpcalendar.R.id.close_btn);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calander_layout.setVisibility(View.GONE);
                        horizontalExpCalendar.setGone();
                        Marks.markToday();
                        Marks.refreshMarkSelected(new DateTime(today_date));
                        horizontalExpCalendar.updateMarks();

                        //  refreshTitleTextView();
                        horizontalExpCalendar.horizontalExpCalListener.onDateSelected(new DateTime(today_date));
                    }
                });
                horizontalExpCalendar.setVisible();

                horizontalExpCalendar.setHorizontalExpCalListener(new HorizontalExpCalendar.HorizontalExpCalListener() {
                    @Override
                    public void onCalendarScroll(DateTime dateTime) {
                        Log.i("Calender", "onCalendarScroll: " + dateTime.toString());
                    }

                    @Override
                    public void onDateSelected(DateTime dateTime) {
                        Log.i("Calender", "onDateSelected: " + dateTime.toString());
                        search_fragment.setVisibility(View.VISIBLE);
                        calander_layout.setVisibility(View.GONE);
                        horizontalExpCalendar.setGone();
                        String dateStr = dateTime.toString();
                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateObj = null;
                        try {
                            dateObj = curFormater.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String newDateStr = curFormater.format(dateObj);

                        Bundle bundle = new Bundle();
                        if (newDateStr.equalsIgnoreCase(today_date)){
                            bundle.putString("day", "1" );
                            replaceFragment(new MatchListFragment(),bundle);
                        }
                        else if (newDateStr.equalsIgnoreCase(tomorrow_date)){
                            bundle.putString("day", "2" );
                            replaceFragment(new MatchListFragment(),bundle);
                        }
                        else if (newDateStr.equalsIgnoreCase(yesterday_date)){
                            bundle.putString("day", "0" );
                            replaceFragment(new MatchListFragment(),bundle);
                        }
                        else {
                            bundle.putString("date", newDateStr );
                            bundle.putSerializable("datetime",dateTime);
                            replacesearchFragment(new SelectedDateLeagueFragment(),bundle);
                        }
                    }

                    @Override
                    public void onChangeViewPager(Config.ViewPagerType viewPagerType) {
                        Log.i("Calender", "onChangeViewPager: " + viewPagerType.name());
                    }
                });
              //  replaceFragment(new CalenderFragment());
                break;
            default:
        }
    }


    protected void replacesearchFragment(Fragment fragment,Bundle bundle) {
        try {
            String backStateName = fragment.getClass().getName();
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.right_in,  R.anim.left_out);
                ft.add(R.id.search_fragment, fragment);
               if (backStateName.equals("com.wolfscore.matches.fragments.MatchListFragment")) {
                    ft.addToBackStack(backStateName);
                } else {
                    ft.addToBackStack(null);
                }
                ft.setCustomAnimations(R.anim.right_in,  R.anim.left_out);
                ft.commit();
            }
        } catch (Exception e) {

        }
    }

public void setSelectedDate()
{

    Marks.markToday();
    Marks.refreshMarkSelected(new DateTime(today_date));
    horizontalExpCalendar.updateMarks();

  //  refreshTitleTextView();
    horizontalExpCalendar.horizontalExpCalListener.onDateSelected(new DateTime(today_date));
}
    protected void onResume() {
        super.onResume();
        Marks.markToday();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}


