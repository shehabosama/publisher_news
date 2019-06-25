package com.example.bebo2.publisher_news.utils;

import android.app.Application;

import io.realm.Realm;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
