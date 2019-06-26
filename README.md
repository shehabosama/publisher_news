News control panel (assignment )
By : shehab osama fathi
First class : splash screen 
Content 
1-	getInstance() 
Type session 
Without attribute 
Return function 
Description : this function to direction access to session class  to access the functions inside session class  

1-1	: isUserLogin()
Type Boolean 
Without attribute 
Return true or false  
Description : check if user is registered in data base lite in app or not if is registered  you will return true else return false
 
Second class : section_news 
1-	Declaretion()
Type void 
Without attribute 
No return 
Description : to merge the design with programming to able handling the design
2-	TransferToPostActivity(String key ,String value)
Type void 
Attributes key and value 
Description : to transfer between activities and take data with it 
3-	 getInstance().logoutAndGoTologin(this);
Type void 
Attribute Activity 
Description : to logout the user and clear his session  and transfer to login activity again 

Third class : Post_Activity 
1-	RequestPermission()
Type void 
No Attribute 
Description : request permission to access the file from user 
2-	 CropActivity();
Type void 
No attribute 
Description: transfer to the gallery or camera or drive to select the photo 

3-	getRealPathFromUri();
Type void 
No attribute 
Description : first thing this function do check if you are select the photo and fall the description  or not if selected the photo  and fall the description for post  . take image uri and get the real path from image uri  

 3-1 FileUpload(String path)
Type void 
Attribute : String path
Description : this the function take the path and check the file if the file is already exist it will upload the file to the server in the uploads folder 



 3-2 : InsertToDatabase(
 String  description ,String filename,String categories ) 
Type void 
Attribute :String description ,String filename ,String categories 
Description : this function take the post description and the file name and the categories post  to insert it in database on the server 

Fourthly class : View_news
1-posts fetch(Call<List<Posts>> getallposts );
Type void
Attribute: Call<List<Posts>> getAllposts
Description : take the function API like the function to fetch the art posts we will call the function getAllArtPosts(),getAllsportPosts






Utils and helper

Fifth Class: Adapter extends from the RecylerView.Adapter 

Description : 
Adapter is a bridge between UI component and data source that helps us to fill data in UI component. It holds the data and send the data to an Adapter view then view can takes the data from the adapter view and shows the data on the different view as ListView, GridView, Spinner etc. For more customization in Views we uses the base adapter or custom adapters.
To fill data in a list or a grid we need to implement And we can handling every element from this class like we can make on Click function to delete post through get id post and make web service to delete post by id we can send request with id post to web service to delete  this post 
1-_Adapter(Context context,List<Posts>);
Attribute : Context context , List<posts>
Description : this function allow us to access adapter class 
To be able display all the items by this format in adapter


1-	Sub class Post_holder extends from RecyclerView.Viewholder.
Description :Hold on the design elements  which will  display in activity 


Sixthly Class: LoginResponse 
Description : This class we can fetch status and message and the user is admin or not we are use it  as object 

Seventh Class :  MainResponse
Description : This class we can fetch status and message
We are use it as object 

Eighth Class: posts
Description : This class we can fetch the post description and image url and category  we art use it as object 

Ninth Class: User
Description : Fetch the user name and password and email through setter and getter function we are use it as object




Tenthly Class: API
Description : This class there are all the function witch use it to fetch data from my sql 

@POST("Login_user.php")
Call<LoginResponse> loginUser(@Body User user);
Description : to send request and response the message if the account is login or not 




    @POST("register_user.php")
    Call<MainResponse> registerUser(@Body User user);
Description : to send request and response the message if the user inserted in database or not 

    @FormUrlEncoded
    @POST("delete-post.php")
    Call<MainResponse> deletepost(@Field("id") int id);
Description : to send request and response the message if the post delete successfully or not 

@FormUrlEncoded
 @POST("update-post.php")
 Call<MainResponse> updatepost(@Field("id") int id,@Field("description")String description) ;
 Description : to send request and response the message if the post update or not 

    @POST("get-all-posts-art.php")
    Call<List<Posts>> getAllPostsArt();
Description : to send request and response the message  if happened any error while fetching the data or not and fetch the art posts

    @POST("get-all-posts-sport.php")
    Call<List<Posts>> getAllPostsSport();
Description : to send request and response the message  if happened any error while fetching the data or not and fetch the sport posts

    @POST("get-all-posts-stock.php")
    Call<List<Posts>> getAllPostsStock();
Description :  to send request and response the message  if happened any error while fetching the data or not and fetch the stock posts


    @POST("get-all-posts-weather.php")
    Call<List<Posts>> getAllPostsWeather();
Description :  to send request and response the message  if happened any error while fetching the data or not and fetch the weather posts






    @POST("get-all-posts-policy.php")
    Call<List<Posts>> getAllPostsPoicy();
Description : to send request and response the message  if happened any error while fetching the data or not and fetch the policy  posts
@POST("get-all-posts-techno.php")
Call<List<Posts>> getAllPostsTechno();
Description : to send request and response the message  if happened any error while fetching the data or not and fetch the technology posts

@FormUrlEncoded
@POST("insetposts.php")
Call<ResponseBody> addpost(@Field("description")String description, @Field("image_url")String image_url,@Field("department_post")String department_post); 
Description :  to send request and response the status and the message say it is the register request successfully or not 

Eleventh class :URLS
Description : This class contain the server url

Twelfth Class: WebServes 
1-	WebService()   ( constrictor )
No type 
No attribute 
Description :this function contain the  retrofit to connection with server and php file

2-getInstance()
Type WebService 
No attribute 
Description : this function allow us to direction access 
The webService constrictor and class 



Thirteenth interface: APP 
        Description : This interface  is initial  define the realm
database 

Fourteenth  Class :Dialog 
Show_dialog();
Type void 
Without attribute 
Description :this function display dialog to edit post description and title 
Web Service 
Get-all-posts-art.php
Description : this the file get all art posts by query to database and convert the result to json to deal with java language

Get-all-posts-weather.php
Description : this the file get all weather posts by query to database and convert the result to json to deal with java language
Get-all-posts-stock.php
Description : this the file get all stock posts by query to database and convert the result to json to deal with java language
Get-all-posts-policy.php
Description : this the file get all policy posts by query to database and convert the result to json to deal with java language
Get-all-posts-sport.php
Description : this the file get all sport posts by query to database and convert the result to json to deal with java language
Get-all-posts-techno.php
Description : this the file get all technology posts by query to database and convert the result to json to deal with java language

