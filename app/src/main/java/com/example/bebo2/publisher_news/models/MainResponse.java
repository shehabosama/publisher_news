package com.example.bebo2.publisher_news.models;

import com.google.gson.annotations.SerializedName;

/*
 This class we can fetch status and message We are use it as object
*/
public class MainResponse {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
}
