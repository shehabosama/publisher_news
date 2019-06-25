
package com.example.bebo2.publisher_news;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bebo2.publisher_news.WebServiceapi.API;
import com.example.bebo2.publisher_news.utils.Session;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Post_activity extends AppCompatActivity {
    private String server_url = "https://shehabosama.000webhostapp.com/upload_file.php"; // url to connection internet and server
    private Uri orgUri;// variable to storage uri the image you select it from gallery
    private Button upload,review;
    private static final int REQUEST_PERMISSION = 1000 ;
    private ImageView select_photo;
    private EditText editText,editTextTitle;
    private String recev;
    private String check;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activity);
        recev = getIntent().getExtras().getString("message").toString();

        RequestPermission();
        Declaration();



    upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("wait minuet..");
                progressDialog.setMessage("we will publish the post now");
                progressDialog.setCancelable(false);
                progressDialog.show();

                getRealPathFromUri(orgUri);

            }
        });
        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropActivity();
            }
        });

    review.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),View_news.class).putExtra("message",recev));
        }
    });
    }

    private void getRealPathFromUri(Uri orgUri)
    {
        if (orgUri != null)
        {
            if(!TextUtils.isEmpty(editText.getText().toString())||TextUtils.isEmpty(editTextTitle.getText().toString()))
            {
                String path = orgUri.toString();
                if (path.toLowerCase().startsWith("file://"))
                {
                    // Selected file/directory path is below
                    path = (new File(URI.create(path))).getAbsolutePath();
                    Log.e("ini", path);
                    String filename=path.substring(path.lastIndexOf("/")+1);
                    Toast.makeText(Post_activity.this, ""+filename, Toast.LENGTH_SHORT).show();

                    FileUpload(path,filename);

                }
            }else
            {
                Toast.makeText(Post_activity.this, "please writer title and description ", Toast.LENGTH_SHORT).show();
            }

            }else
            {
                Toast.makeText(Post_activity.this, "please select  photo ", Toast.LENGTH_SHORT).show();
            }

    }


    private void RequestPermission() // to request from the user  permission to access file from his phone
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION);
        }

    }

    private void Declaration()//to merge the design with programming to able handling the design
    {
        progressDialog = new ProgressDialog(this);
        editText = findViewById(R.id.edt_desc);
        upload = findViewById(R.id.btn_upload);
        select_photo = findViewById(R.id.select);
        review = findViewById(R.id.review);
        editTextTitle = findViewById(R.id.edt_title);

    }


    private void CropActivity()// to  open the gallery or camera or any app can take a photo  and select the photo and crop the photo and submit crop
    {
        CropImage.activity()
               // .setGuidelines(CropImageView.Guidelines.ON)
              //  .setAspectRatio(1, 1)
                //.setMaxCropResultSize(700, 700)
                .start(Post_activity.this);
    }

    /*this function take the post description and the file name and the categories post  to insert it in database on the server */

    private void InsertToDatabase(String title,String desc, String filename, String categories)
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://shehabosama.000webhostapp.com/").build();

        API _shehabAPI = retrofit.create(API.class);
        Call<ResponseBody> addostconnection = _shehabAPI.addpost(title,desc,filename,categories);

        addostconnection.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String res = response.body().string();
                    Toast.makeText(Post_activity.this, res, Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(Post_activity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                orgUri = result.getUri();


                select_photo.setImageURI(orgUri);
               // String convertedPath = getRealPathFromURI(orgUri);
                        //getRealPathFromURI(getApplicationContext(),orgUri);//path converted from Uri
               // Toast.makeText(this, ""+convertedPath, Toast.LENGTH_SHORT).show();

            }
        }


    }
    /*this the function take the path and check the file if the file is already exist it will upload the file to the server in the uploads folder */
    private void FileUpload(final String filepath , final String filename )
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection uploadConnection = null; // Declaration variable to  allowed us to connection the internet and server through the url we declaration it above
                DataOutputStream outputStream;// this the file's  streaming   transfer from app to server
                String boundary = "********";// this the break between  streams's parts
                String CRLF = "\r\n"; // break between the header and body and it use with the boundary
                String Hyphens ="--";// using with boundary to break between header and body
                int bytesRead,bytesAvailable,bufferSize;// bytesread is the streams  successfully read  -- byteAvailable is the streams available not read
                int maxBufferSize = 1024*1024;// this maxSize in the stack streams
                byte[] buffer;// this is the array to storage all streams  inside it


                File outFile = new File(filepath);// this to access the the file we will uploaded and make sure the file is already exist or now

                try {
                    FileInputStream fileInputStream = new FileInputStream(outFile);// this to converter File to streams to give file to the server in the form of streams
                    URL url = new URL(server_url);//set url variable in the Url class to convert the String url to url c
                    uploadConnection = (HttpURLConnection) url.openConnection();// now we will open connection
                  //  uploadConnection.setConnectTimeout(40);
                    //uploadConnection.setConnectTimeout(60);
                  //  uploadConnection.setReadTimeout(60);
                    uploadConnection.setDoInput(true);//here we will make the server accept the input which we send it
                    uploadConnection.setDoOutput(true);//here we will make the server accept the output which we receive it
                    uploadConnection.setRequestMethod("POST");// here we select the kind of method

                    uploadConnection.setRequestProperty("Connection", "Keep-Alive");// send server that it will stay open connection  as long as there streams
                    uploadConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);//select the kind of data will sen it  and set boundary to break between streams
                    uploadConnection.setRequestProperty("uploaded_file", filepath);//set file path who we will upload it

                    outputStream = new DataOutputStream(uploadConnection.getOutputStream());//here we will set the connection output stream to Data output stream because the DataoutputStream is help us to upload the file and connection to server as streams

                    outputStream.writeBytes(Hyphens + boundary + CRLF);// to lift  line between the header and the body

                    outputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + filepath + "\"" + CRLF);// define the file here
                    outputStream.writeBytes(CRLF);// to make space


                    bytesAvailable= fileInputStream.available();// set the available stream in the bytesAvailable
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);// select which what the minimum between bytesAvailable and maxbufferSize and set the minimum in the bufferSize
                    buffer = new byte[bufferSize];// set the result bufferSize in the array (buffer)
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);//read the byte from the file stream

                    while (bytesRead>0){//as long as there bytes in the bytesread enter again and upload it
                        outputStream.write(buffer,0,bufferSize);//to write the stream in the server and take it from the app
                        bytesAvailable = fileInputStream.available();//set the available stream in the bytesAvailable after the write the outputStream
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);// select which what the minimum between bytesAvailable and maxbufferSize and set the minimum in the bufferSize
                        bytesRead = fileInputStream.read(buffer,0,bufferSize);//read the byte from the file stream
                    }

                    outputStream.writeBytes(CRLF);//after end the line will move the new line
                    outputStream.writeBytes(Hyphens+boundary+Hyphens+CRLF);


                    InputStreamReader resultReader = new InputStreamReader(uploadConnection.getInputStream());
                    final BufferedReader reader = new BufferedReader(resultReader);
                    String line = "";
                    String response="";
                    while ((line = reader.readLine()) != null ){
                        response+= line;
                    }

                    final String finalResponse = response;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           check = finalResponse;
                            Toast.makeText(Post_activity.this, check, Toast.LENGTH_LONG).show();
                            InsertToDatabase(editTextTitle.getText().toString(),editText.getText().toString(),filename,recev);
                            progressDialog.dismiss();
                        }
                    });

                    fileInputStream.close();
                    outputStream.flush();
                    outputStream.close();

                } catch (FileNotFoundException e) {
                    Log.e("MYAPP", "exception", e);
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case REQUEST_PERMISSION:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
        }
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
