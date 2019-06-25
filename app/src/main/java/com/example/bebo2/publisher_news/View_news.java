package com.example.bebo2.publisher_news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bebo2.publisher_news.Adapters._Adapter;
import com.example.bebo2.publisher_news.WebServiceapi.WebService;
import com.example.bebo2.publisher_news.models.Posts;
import com.example.bebo2.publisher_news.utils.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_news extends AppCompatActivity {

    private RecyclerView recycler_posts;
    private String recev;
    private List<Posts> posts;
    private Call<List<Posts>> getAllPosts;
    _Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        recev = getIntent().getExtras().getString("message").toString();
        recycler_posts = findViewById(R.id.recycler_posts);
        recycler_posts.setLayoutManager(new LinearLayoutManager(this));
        if(recev.equals("art"))
        {
            posts_fetch(WebService.getInstance().getApi().getAllPostsArt());
        }else if(recev.equals("sport"))
        {
            posts_fetch(WebService.getInstance().getApi().getAllPostsSport());
        }else if(recev.equals("policy"))
        {
            posts_fetch(WebService.getInstance().getApi().getAllPostsPoicy());

        }else if(recev.equals("stock"))
        {
            posts_fetch(WebService.getInstance().getApi().getAllPostsStock());

        }else if(recev.equals("weather"))
        {
            posts_fetch(WebService.getInstance().getApi().getAllPostsWeather());

        }else if(recev.equals("technology"))
        {
            posts_fetch(WebService.getInstance().getApi().getAllPostsTechno());

        }

    }
    public void posts_fetch(Call<List<Posts>> getAllPosts)// to fetch the posts
    {
        getAllPosts.enqueue(new Callback<List<Posts>>()
        {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                posts = response.body();
                adapter = new _Adapter(View_news.this,posts);
                adapter.notifyDataSetChanged();
                recycler_posts.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t)
            {
                Toast.makeText(View_news.this, "" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                Session.getInstance().logoutAndGoTologin(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
