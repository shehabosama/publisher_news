package com.example.bebo2.publisher_news.WebServiceapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebService {
    private static WebService instance;
    private API api;

    public WebService()//this function contain the  retrofit to connection with server and php file
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Urls.MAIN_URL)
                .build();
        api = retrofit.create(API.class);
    }
    /*
    this function allow us to direction access
    The webService constrictor and class
    */
    public static WebService getInstance()
    {
        if(instance == null)
        {
            instance = new WebService();
        }
        return instance;
    }

    public API getApi()
    {
        return api;
    }
}
