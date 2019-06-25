package com.example.bebo2.publisher_news.WebServiceapi;

import com.example.bebo2.publisher_news.models.LoginResponse;
import com.example.bebo2.publisher_news.models.MainResponse;
import com.example.bebo2.publisher_news.models.Posts;
import com.example.bebo2.publisher_news.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/*
This class there are all the function witch use it to fetch data from my sql
*/
public interface API {

    @POST("Login_user.php")
    Call<LoginResponse> loginUser(@Body User user);//to send request and response the message if the account is login or not

    @POST("register_user.php")
    Call<MainResponse> registerUser(@Body User user);// to send request and response the message if the user inserted in database or not


    @FormUrlEncoded
    @POST("delete-post.php")
    Call<MainResponse> deletepost(@Field("id") int id);//to send request and response the message if the post delete successfully or not

    @FormUrlEncoded
    @POST("update-post.php")
    Call<MainResponse> updatepost(@Field("id") int id,@Field("title")String title,@Field("description")String description) ;//to send request and response the message if the post update or not


    @POST("get-all-posts-art.php")
    Call<List<Posts>> getAllPostsArt();//to send request and response the message  if happened any error while fetching the data or not and fetch the art posts

    @POST("get-all-posts-sport.php")
    Call<List<Posts>> getAllPostsSport();//to send request and response the message  if happened any error while fetching the data or not and fetch the sport posts

    @POST("get-all-posts-stock.php")
    Call<List<Posts>> getAllPostsStock();//to send request and response the message  if happened any error while fetching the data or not and fetch the stock posts

    @POST("get-all-posts-weather.php")
    Call<List<Posts>> getAllPostsWeather();//to send request and response the message  if happened any error while fetching the data or not and fetch the weather posts

    @POST("get-all-posts-policy.php")
    Call<List<Posts>> getAllPostsPoicy();//to send request and response the message  if happened any error while fetching the data or not and fetch the policy posts

    @POST("get-all-posts-techno.php")
    Call<List<Posts>> getAllPostsTechno();//to send request and response the message  if happened any error while fetching the data or not and fetch the techno posts

    //@POST("add-chat-rooms.php")
    //Call<MainResponse> add_chat_rooms(@Body ChatRoom chatRoom);

    @FormUrlEncoded
    @POST("insetposts.php")//to send request and response the status and the message say it is the register request successfully or not
    Call<ResponseBody> addpost(@Field("title")String title,@Field("description")String description, @Field("image_url")String image_url,@Field("department_post")String department_post);

}
