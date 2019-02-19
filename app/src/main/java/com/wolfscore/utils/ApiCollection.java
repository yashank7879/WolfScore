package com.wolfscore.utils;

/**
 * Created by mindiii on 1/22/19.
 */

public class ApiCollection {
    public static final String BASE_URL = "http://dev.wolfscore.info/api_v1/";
    public static final String APIKEY = "B1214622-0CC1-4452-8657-B8D7228B8B29";
    public static final String GUEST_SIGNUP = "service/guest_signup";
    public static final String GET_LOCAL_TEAM = "teams/get_local_teams?country=Denmark&limit=20&offset=0";
    public static final String GET_POPULAR_TEAM = "teams/get_popular_teams?limit=20&offset=20";
///favorite
public static final String ADD_FAVOURITES = "users/ADD_FAVOURITES";
    public static final String GET_MY_FAVORITE_LIST_API = "users/get_my_favorite_list";
    ////////matches api
    public static final String GET_FIXTURES="matches/get_fixtures";
    public static final String SINGLE_FAVORITE_UNFAVORITE_API = "users/single_favorite_unfavorite";
    public static final String GET_LEAGUE_LIST_API = "leagues/get_league_list";
    public static final String GET_POPULAR_TEAMS_API = "teams/get_popular_teams";
    public static final String GET_SEARCH_MATCHES = "matches/search_matches";
    public static final String ADD_REMOVE_FILTERED_LEAGUE_API = "leagues/add_remove_filtered_league";

    public static final String LEAGUE_FILTER_SEQUENCE_LIST_API = "leagues/get_league_filter_sequence_list";
    public static final String SET_FILTER_LEAGUES_API = "leagues/set_filter_leagues";
    public static final String HEAD_TO_HEAD="matches/get_head_to_head";
    public static final String MATCH_DETAIL="matches/get_match_details";

    public static final String GET_MATCH_DETAILS_API = "matches/get_team_lineup";


}
