package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Post {
    private int id;
    private String url;
    private String status;
    private String type;


    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

}
