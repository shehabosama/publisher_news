package com.example.bebo2.publisher_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bebo2.publisher_news.utils.Session;

public class department_desplay_news extends AppCompatActivity {
    private TextView sport,policy,art,technology,weather,stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_desplay_news);

        Declaration();

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferToPostActivity("message","sport");
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
    }

    private void Declaration()//to merge the design with programming to able handling the design
    {
         sport= findViewById(R.id.sport);
         policy= findViewById(R.id.Policy);
         art= findViewById(R.id.art);
         technology= findViewById(R.id.tec);
         stock= findViewById(R.id.stock_market);
         weather= findViewById(R.id.weather);
    }

    private void TransferToPostActivity(String message, String value)//to transfer between activities and take data with it
    {
        Intent to_post_publisher = new Intent(getApplicationContext(),View_news.class);
        to_post_publisher.putExtra(message,value);
        startActivity(to_post_publisher);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
