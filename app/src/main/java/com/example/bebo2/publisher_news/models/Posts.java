package com.example.bebo2.publisher_news.models;

import com.google.gson.annotations.SerializedName;

/*
This class we can fetch the post description and image url and category  we art use it as object
*/

public class Posts {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("department_post")
    private String department_post;

    public Posts(String description, String image_url, String department_post, String date_time) {
        this.description = description;
        this.image_url = image_url;
        this.department_post = department_post;
        this.date_time = date_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    private String date_time;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDepartment_post() {
        return department_post;
    }

    public void setDepartment_post(String department_post) {
        this.department_post = department_post;
    }
}
