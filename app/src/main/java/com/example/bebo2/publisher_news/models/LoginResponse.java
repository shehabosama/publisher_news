package com.example.bebo2.publisher_news.models;

import com.google.gson.annotations.SerializedName;

/*
This class we can fetch status and message and the user is admin or not we are use it  as object
*/
public class LoginResponse
{
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("user")
    public User user;

    public static class User
    {
        @SerializedName("id")
        public String id;
        @SerializedName("username")
        public String username;
        @SerializedName("email")
        public String email;
        @SerializedName("is_admin")
        public String is_admin;

    }
}
