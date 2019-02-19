package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class Data {
    public Data1 data;
    public ArrayList<Data1> data1ArrayList=new ArrayList<>();

    public ArrayList<Data1> getData1ArrayList() {
        return data1ArrayList;
    }

    public void setData1ArrayList(ArrayList<Data1> data1ArrayList) {
        this.data1ArrayList = data1ArrayList;
    }

    public Data1 getData1() {
        return data;

    }

    public void setData1(Data1 data) {
        this.data = data;
    }

    /* @JsonProperty("address")
        private String address;
        @JsonProperty("surface")
        private String surface;
        @JsonProperty("city")
        private String city;
        @JsonProperty("image_path")
        private String imagePath;
        @JsonProperty("name")
        private String name;
        @JsonProperty("coordinates")
        private String coordinates;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("capacity")
        private Integer capacity;
        @JsonProperty("data")*/


}