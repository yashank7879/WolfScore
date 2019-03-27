package com.wolfscore.listener;

/**
 * Created by mindiii on 3/15/19.
 */

public interface FavUnfavListener {
    void playerFavUnfav(String playerId, int pos);
    void teamFavUnfav(String teamId, int pos);
    void leagueFavUnfav(String leagueId, int pos);
}
