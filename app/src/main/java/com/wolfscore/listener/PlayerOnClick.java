package com.wolfscore.listener;

import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.responce.TopPlayerResponce;

/**
 * Created by mindiii on 1/25/19.
 */

public interface PlayerOnClick {
    void playerItemOnCLick(TopPlayerResponce.DataBean.PlayerListBean bean, String value);

}
