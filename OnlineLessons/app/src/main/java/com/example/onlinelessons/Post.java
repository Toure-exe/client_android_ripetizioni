package com.example.onlinelessons;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("submit")
    private String submit;
    private String email;
    private String password;

    public Post(String submit, String password, String email){
        this.submit = submit;
        this.password = password;
        this.email = email;
    }
}
