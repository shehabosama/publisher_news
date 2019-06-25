package com.example.bebo2.publisher_news.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/*
Fetch the user name and password and email through setter and getter function we are use it as object
*/
public class User extends RealmObject {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;

    public int id ;
    public boolean isAdmin;

}
