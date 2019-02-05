package com.wolfscore.listener;

import com.wolfscore.responce.LocalTeamResponce;
import com.wolfscore.responce.TopPlayerResponce;

/**
 * Created by mindiii on 1/24/19.
 */

public interface NextOnClick {
   // void nextOnclickListener(LocalTeamResponce.DataBean.TeamListBean bean,String team, boolean value);
    void nextPlayerOnclickListener(TopPlayerResponce.DataBean.PlayerListBean bean, String team, boolean value);
    void nextNotification(String next,boolean ischecked);

    void nextLocalOnclickListener(LocalTeamResponce.DataBean.TeamListBean bean, String team, boolean value);
    void nextPopularOnclickListener(LocalTeamResponce.DataBean.TeamListBean bean, String team, boolean value);
}
