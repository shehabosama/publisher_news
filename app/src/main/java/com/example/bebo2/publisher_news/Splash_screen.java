package com.example.bebo2.publisher_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bebo2.publisher_news.utils.Session;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(Session.getInstance().isUserLoggedin())
        {
            if(Session.getInstance().getUser().isAdmin)
            {
                startActivity(new Intent(getApplicationContext(),Section_news.class));
                finish();
            }else
                {
                    startActivity(new Intent(getApplicationContext(),department_desplay_news.class));
                    finish();
                }

        }else
            {
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));

            }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
