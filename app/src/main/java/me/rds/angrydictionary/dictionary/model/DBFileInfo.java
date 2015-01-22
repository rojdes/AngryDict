package me.rds.angrydictionary.dictionary.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by D1m11n on 22.01.2015.
 */


public class DBFileInfo {


    public long timestamp;

    public String url;

    public static class Array{

        @SerializedName("db")
        public DBFileInfo[] files;
    }

}