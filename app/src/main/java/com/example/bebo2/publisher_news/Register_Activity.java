package com.example.bebo2.publisher_news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bebo2.publisher_news.WebServiceapi.WebService;
import com.example.bebo2.publisher_news.models.MainResponse;
import com.example.bebo2.publisher_news.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {

    private EditText username,email,password, confirm_password;
    private Button btn_register;
    private TextView link_login;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Declaration();// to merge between the design and programming

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login_Activity.class)); // to transfer to Login Activity
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strusername = username.getText().toString();// Make inputs  entered by the user as String
                String stremail = email.getText().toString();// Make inputs  entered by the user as String
                String strpassword = password.getText().toString();// Make inputs  entered by the user as String
                String strconfirm = confirm_password.getText().toString();// Make inputs  entered by the user as String
                Registration_func(strusername,stremail,strpassword,strconfirm);/* first thing this function check is it the EditText is empty or not if not empty
                                                                                   it will take the data and insert it in the database */


            }
        });
    }

    private void Registration_func(String strusername,  String stremail, String strpassword, String strconfirm )
    {

        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
        if (TextUtils.isEmpty(strusername)||
                TextUtils.isEmpty(stremail)||
                TextUtils.isEmpty(strpassword)||
                TextUtils.isEmpty(strconfirm))
        {
            Toast.makeText(Register_Activity.this, "pleas fill all the flied", Toast.LENGTH_SHORT).show();
        }else if (!strpassword.equals(strconfirm))
        {
            Toast.makeText(Register_Activity.this, "password don't match", Toast.LENGTH_SHORT).show();
        }else
        {
            User user = new User();
            user.username = strusername;
            user.email = stremail;
            user.password = strpassword;

            WebService.getInstance().getApi().registerUser(user).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if(response.body().status == 2)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Register_Activity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();

                    }else if(response.body().status==1)
                    {
                        progressDialog.dismiss();// exit from the dialog box because the process is done
                        Toast.makeText(Register_Activity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Login_Activity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(Register_Activity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void Declaration()// to merge the design with programming
    {
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        btn_register = findViewById(R.id.btn_register);
        link_login = findViewById(R.id.link_login);
        progressDialog = new ProgressDialog(this);// dialog to make  user wait when login the account
    }
}
