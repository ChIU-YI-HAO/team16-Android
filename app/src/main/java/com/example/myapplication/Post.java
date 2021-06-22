package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Post {
    private int id;
    private int userid;
    private String url;
    private String status;
    private String username;

    public Post(String username, String url, String status) {
        this.username = username;
        this.url = url;
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public int getUserid(){
        return userid;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }
}
