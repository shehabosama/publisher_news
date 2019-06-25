package com.example.bebo2.publisher_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bebo2.publisher_news.models.User;
import com.example.bebo2.publisher_news.utils.Session;

public class Section_news extends AppCompatActivity {
    private TextView sport,policy,art,technology,weather,stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_news);

        Declaration();// to merge between the design and programming

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferToPostActivity("message","sport");// transfer to the post Activity but pas

            }
        });
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferToPostActivity("message","policy");
            }
        });
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferToPostActivity("message","art");
            }
        });
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferToPostActivity("message","technology");
            }
        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferToPostActivity("message","weather");
            }
        });
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferToPostActivity("message","stock");
            }
        });
        User user = Session.getInstance().getUser();
        if(user!= null)
        {
            if(getSupportActionBar() != null)
            {
                if(user.isAdmin)
                {
                    getSupportActionBar().setTitle("welcome admin : "+user.username+" ...choose section");

                }else
                {
                    startActivity(new Intent(getApplicationContext(),department_desplay_news.class));
                    //getSupportActionBar().setTitle(user.username);

                }

            }
        }
    }

    private void Declaration()// to merge the design with programming
    {
         sport= findViewById(R.id.sport);
         policy= findViewById(R.id.Policy);
         art= findViewById(R.id.art);
         technology= findViewById(R.id.tec);
         stock= findViewById(R.id.stock_market);
         weather= findViewById(R.id.weather);

    }

    private void TransferToPostActivity(String message, String value)//to transfer to the post Activity and take some data to post Activity
    {
        Intent to_post_publisher = new Intent(getApplicationContext(),Post_activity.class);
        to_post_publisher.putExtra(message,value);
        startActivity(to_post_publisher);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                Session.getInstance().logoutAndGoTologin(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
