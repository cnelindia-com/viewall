package com.example.viewall.serviceapi;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.contact.ContactResponse;
import com.example.viewall.models.getuser.GetUserResponse;
import com.example.viewall.models.homecategorylist.HomeCategoryResponse;
import com.example.viewall.models.popularviedos.PopularVideoResponse;
import com.example.viewall.models.register.RegisterResponse;
import com.example.viewall.models.singlecategorylist.SingleCategoryResponse;
import com.example.viewall.models.singlevideo.SingleVideoResponse;
import com.example.viewall.models.track.TrackResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //Getting the base url from shared preference
    SharedPreferences sharedPreferences = GlobalApplication.getAppContext().getSharedPreferences("VIEWALL", MODE_PRIVATE);
    String myU = sharedPreferences.getString("baseurl2", "");

    String BASE_URL = sharedPreferences.getString("baseurl2", "");

    //http://dev.view4all.tv/API/api.php?action=registero_first_name:Hemrajo_last_name:Sharmao_phone:7890657457pindex:4o_custom_1:ghgho_custom_2:fgfhffemail:mymail@gmail.com
    @NonNull
    @FormUrlEncoded
    @POST("api.php?action=register")
    Call<RegisterResponse> register(@Field("o_first_name") String o_first_name,
                                    @Field("o_last_name") String o_last_name,
                                    @Field("o_phone") String o_phone,
                                    @Field("mac_address") String mac_address
                                    /*@Field("email") String email*/);

    //http://dev.view4all.tv/API/api.php?action=getallcategory
    @NonNull
    @GET("api.php?action=getallcategory")
    Call<HomeCategoryResponse> homeCategory();

    //http://dev.view4all.tv/API/api.php?action=getsinglecategory&id=0e3b2725-de08-44c0-a47a-eccb94bd77d9&=
    @NonNull
    @GET("api.php?action=getsinglecategory")
    Call<SingleCategoryResponse> singleCategory(@Query("id") String id);

    //http://dev.view4all.tv/API/api.php?action=getalladvertimage
    @NonNull
    @GET("api.php?action=getalladvertimage")
    Call<BannerResponse> bannerList();

    //http://dev.view4all.tv/API/api.php?action=getallvideos
    @NonNull
    @GET("api.php?action=getallvideos")
    Call<PopularVideoResponse> popularVideos();

    //http://dev.view4all.tv/API/api.php?action=getsinglevideo&id=154fe4de-f06c-4bbf-86fd-513fca0091ab&=
    @NonNull
    @GET("api.php?action=getsinglevideo")
    Call<SingleVideoResponse> singleVideo(@Query("id") String id);

    //http://dev.view4all.tv/API/api.php?action=getuser&&id=bfc6c4339390eaa33f032f8fc8e96c35
    @NonNull
    @GET("api.php?action=getuser")
    Call<GetUserResponse> getUserData(@Query("id") String id);

    //http://dev.view4all.tv/API/api.php?action=insertcontact
    @NonNull
    @POST("api.php?action=insertcontact")
    Call<ContactResponse> sendContact(@Query("user_id") String user_id,
                                      @Query("name") String name,
                                      @Query("phone_number") String phone_number,
                                      @Query("subject") String subject,
                                      @Query("message") String message);

    //http://dev.view4all.tv/API/api.php?action=track
    @NonNull
    @POST("api.php?action=track")
    Call<TrackResponse> sendTrack(@Query("user_id") String user_id,
                                  @Query("type") String type);

}
