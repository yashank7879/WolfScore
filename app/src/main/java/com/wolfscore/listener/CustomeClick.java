package com.wolfscore.listener;

/**
 * Created by mindiii on 6/2/19.
 */

public class CustomeClick {

    private static CustomeClick mInstance;
    private RefreshList refreshListListner;


    public static CustomeClick getInstance(){
        if(mInstance != null){
            mInstance = new CustomeClick();

        }
        return  mInstance;
    }


    public void setListner(RefreshList listner){
        this.refreshListListner = listner;

    }

    public void ontextChange(String text){

        if(refreshListListner!=null){
            refreshListListner.textChange();

        }
    }

    public interface RefreshList{
        void textChange();
    }
}
