package com.example.bebo2.publisher_news.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bebo2.publisher_news.R;
import com.example.bebo2.publisher_news.WebServiceapi.WebService;
import com.example.bebo2.publisher_news.models.MainResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class coustome_dialog {


    public void showDialog(final Activity activity, final int  id){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.costome_dialoge);
       // dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog);
        final EditText text = (EditText) dialog.findViewById(R.id.text_dialog);
        final EditText text_title = (EditText) dialog.findViewById(R.id.text_title);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        TextView text_close = (TextView) dialog.findViewById(R.id.close_text);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebService.getInstance().getApi().updatepost(id,text_title.getText().toString(),text.getText().toString()).enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        if (response.body().status == 1)
                        {
                            Toast.makeText(activity, ""+response.body().message, Toast.LENGTH_SHORT).show();
                            activity.recreate();

                        }else
                            {
                                Toast.makeText(activity, ""+response.body().message, Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {

                    }
                });

                dialog.dismiss();

            }
        });

        dialog.show();

    }

}
