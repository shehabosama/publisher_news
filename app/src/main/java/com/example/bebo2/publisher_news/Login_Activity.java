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
import com.example.bebo2.publisher_news.models.LoginResponse;
import com.example.bebo2.publisher_news.models.User;
import com.example.bebo2.publisher_news.utils.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {

    private EditText email,password;
    private Button btn_login;
    private TextView link_register;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        Declaration(); // to merge between design and programming

        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register_Activity.class));// to transfer from this activity to Register Activity
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stremail = email.getText().toString(); // Make inputs  entered by the user as String

                String strpassword = password.getText().toString();// Make inputs  entered by the user as String

                LoginFunc(stremail,strpassword);/*receive the email as String and password as String and check the EditTexts  is not empty and
                                                  register the user is login because not logout every session it is save the user information to remember him every-time and
                                                 take the text to check is it the  data  already exist in the server if already exist you will transfer to section news to publish the news*/

            }
        });

    }

    private void LoginFunc(String stremail, String strpassword)
    {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box

        if ( TextUtils.isEmpty(stremail)||
                TextUtils.isEmpty(strpassword))

        {
            Toast.makeText(Login_Activity.this, "pleas fill all the flied", Toast.LENGTH_SHORT).show();
        }else
        {
            final User user = new User();
            user.email = stremail;
            user.password = strpassword;

            WebService.getInstance().getApi().loginUser(user).enqueue(new Callback<LoginResponse>()
            {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
                {
                    if(response.body().status == 0)
                    {
                        Toast.makeText(Login_Activity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();

                    }else if(response.body().status==1)
                    {
                        Toast.makeText(Login_Activity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                        user.username = response.body().user.username;
                        user.id = Integer.parseInt( response.body().user.id);
                        user.isAdmin = response.body().user.is_admin.equals("1");
                        Session.getInstance().loginUser(user);

                        progressDialog.dismiss();// exit from the dialog box because the process is done
                        User user1 = Session.getInstance().getUser();
                        if(user1.isAdmin)
                        {
                            startActivity(new Intent(getApplicationContext(),Section_news.class));
                            finish();
                        }else{
                            startActivity(new Intent(getApplicationContext(),department_desplay_news.class));
                            finish();
                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t)
                {
                    progressDialog.dismiss();

                    Toast.makeText(Login_Activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void Declaration()//to merge the design with programming to able handling the design
    {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.link_register);
        progressDialog = new ProgressDialog(this);// dialog to make  user wait when login the account
    }
}
