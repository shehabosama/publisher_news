package com.example.bebo2.publisher_news.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bebo2.publisher_news.R;
import com.example.bebo2.publisher_news.View_news;
import com.example.bebo2.publisher_news.WebServiceapi.WebService;
import com.example.bebo2.publisher_news.Fragments.coustome_dialog;
import com.example.bebo2.publisher_news.models.MainResponse;
import com.example.bebo2.publisher_news.models.Posts;
import com.example.bebo2.publisher_news.models.User;
import com.example.bebo2.publisher_news.utils.Session;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _Adapter extends RecyclerView.Adapter  {
    Context context;
    List<Posts> posts_list;

    /*this function allow us to access adapter class
   To be able display all the items by this format in adapter
   */
    public _Adapter(Context context , List<Posts> posts_list)
    {
        this.context = context;
        this.posts_list = posts_list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // here we hold on the layout to control it
        View view = LayoutInflater.from(context).inflate(R.layout.all_post_layout,viewGroup,false);
        postHolder postHolder = new postHolder(view);
        return postHolder;

    }

    // here we custom every item to display different items and different data
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        postHolder postHolder = (postHolder) viewHolder;
        final Posts postRoom = posts_list.get(i);
        postHolder.post_desc.setText(postRoom.getDescription());
        postHolder.post_date_time.setText(postRoom.getDate_time());
        postHolder.post_title.setText(postRoom.getTitle());
        Picasso.with(context).load("https://shehabosama.000webhostapp.com/uploads/"+postRoom.getImage_url()).into(postHolder.imageView);

        postHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = Session.getInstance().getUser();
                if (user.isAdmin) {

                    final CharSequence options[] = new CharSequence[]
                            {
                                    "delete post"
                                    , "edit post"

                            };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("option");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (which == 0) {


                                int postID = Integer.parseInt(posts_list.get(i).getId());
                                WebService.getInstance().getApi().deletepost(postID).enqueue(new Callback<MainResponse>() {
                                    @Override
                                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                                        if(response.body().status==1)
                                        {
                                            Toast.makeText(context, ""+response.body().message, Toast.LENGTH_SHORT).show();
                                            posts_list.remove(i);
                                            _Adapter adapter = new _Adapter(context,posts_list);
                                            adapter.notifyItemRemoved(i);

                                            if (context.getClass().equals(View_news.class)) {
                                                ((View_news) context).recreate();
                                            }

                                        }else
                                        {
                                            Toast.makeText(context, ""+response.body().message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MainResponse> call, Throwable t) {

                                        Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            if (which == 1)
                            {


                                coustome_dialog coustome = new coustome_dialog();
                                coustome.showDialog((Activity) context,Integer.parseInt(postRoom.getId()));

                            }

                        }
                    });
                    builder.show();
                } else

                {
                    Toast.makeText(context, "you are not admin", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    /*
    Hold on the design elements  which will  display in activity
    */
    @Override
    public int getItemCount() {
        return posts_list.size();
    }

    public class postHolder extends  RecyclerView.ViewHolder
    {
        TextView post_desc;
        TextView post_date_time;
        ImageView imageView;
        TextView post_title;

        public postHolder(@NonNull View itemView) {
            super(itemView);
            post_title = itemView.findViewById(R.id.texttitlepost);
            post_desc = itemView.findViewById(R.id.textdescriptionpost);
            post_date_time = itemView.findViewById(R.id.textdatepost);
            imageView = itemView.findViewById(R.id.postImage);
        }
    }
}
