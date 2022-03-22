package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.example.viewall.adapters.HomeAddSliderAdapter;
import com.example.viewall.adapters.SingleCategoryAdapter;
import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.channel1.Channel1Response;
import com.example.viewall.models.singlecategorylist.DataItem;
import com.example.viewall.models.singlecategorylist.HeaderItem;
import com.example.viewall.models.singlecategorylist.SingleCategoryResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.SharePrefrancClass;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsActivity extends AppCompatActivity {

    LinearLayout idiskLayoutId, soccerlifeLayoutId, soccerLayoutId, cricketLayoutId;
    ImageView img1, imgBack, imgCatHeaderId;
    TextView categoryNameId;
    String strCatName, strCatId;
    Intent getData;
    RecyclerView singleCategoryRec;
    ProgressDialog progressDialog;
    ArrayList<DataItem> listData;
    ArrayList<HeaderItem> listHeader;

    int myImageList[] = {R.drawable.addicon, R.drawable.banner2, R.drawable.banner1};
    SingleCategoryAdapter singleCategoryAdapter;
    SliderView imageSliderCat;

    ArrayList<com.example.viewall.models.bannerlist.DataItem> bannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        idiskLayoutId = findViewById(R.id.idiskLayoutId);
        soccerlifeLayoutId = findViewById(R.id.soccerlifeLayoutId);
        soccerLayoutId = findViewById(R.id.soccerLayoutId);
        cricketLayoutId = findViewById(R.id.cricketLayoutId);
        img1 = findViewById(R.id.img1);
        imgBack = findViewById(R.id.imgBack);
        categoryNameId = findViewById(R.id.categoryNameId);

        imgCatHeaderId = findViewById(R.id.imgCatHeaderId);

        singleCategoryRec = findViewById(R.id.singleCategoryRec);

        getData = getIntent();
        strCatName = getData.getStringExtra("catName");
        strCatId = getData.getStringExtra("catId");
        categoryNameId.setText(strCatName);

        imageSliderCat = findViewById(R.id.imageSliderCat);
        /*imageSliderCat.setSliderAdapter(new HomeAddSliderAdapter(myImageList, SportsActivity.this));
        imageSliderCat.startAutoCycle();*/

        //Calling api
        callSingleCategoryList(strCatId);

        //Calling banner api.
        callBannerListApi();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, HomeActivity.class));
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        idiskLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        soccerlifeLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        soccerLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        cricketLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });
    }

    private void callChannel1Api() {
        String bannerUrl = "";
        String tempStr = "";
        for (int i=0; i<bannerList.size(); i++){
            tempStr = bannerList.get(i).getImageUrl().replace("http://dev.view4all.tv/content/", "");
            bannerUrl = bannerUrl + ", " + tempStr;
            /*bannerUrl = bannerUrl + ", " + bannerList.get(i).getImageUrl();*/
        }
        Log.d("ChannelBANNERURL", bannerUrl);

        Call<Channel1Response> call = RetrofitClient.getInstance().getMyApi().channel1(bannerUrl,
                SharePrefrancClass.getInstance(SportsActivity.this).getPref("phone_number"));

        call.enqueue(new Callback<Channel1Response>() {
            @Override
            public void onResponse(Call<Channel1Response> call, Response<Channel1Response> response) {
                if (response.body() != null) {
                    /*Toast.makeText(HomeActivity.this, "Index status:" + response.body().getStatus(), Toast.LENGTH_SHORT).show();*/
                    Log.d("channel1res", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Channel1Response> call, Throwable t) {
                Log.d("channel1fail", t.getMessage());
            }
        });
    }

    private void callBannerListApi(){
        progressDialog.show();

        Call<BannerResponse> call = RetrofitClient.getInstance().getMyApi().bannerList();

        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null){
                    bannerList = new ArrayList<>();
                    bannerList.addAll(response.body().getData());
                    imageSliderCat.setSliderAdapter(new HomeAddSliderAdapter(bannerList, SportsActivity.this));
                    imageSliderCat.startAutoCycle();

                    //Calling channel1 api
                    callChannel1Api();
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SportsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSingleCategoryList(String categoryId) {
        progressDialog.show();

        Call<SingleCategoryResponse> call = RetrofitClient.getInstance().getMyApi().singleCategory(/*strCatId*/ categoryId);

        call.enqueue(new Callback<SingleCategoryResponse>() {
            @Override
            public void onResponse(Call<SingleCategoryResponse> call, Response<SingleCategoryResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null){
                    listData = new ArrayList<>();
                    listData.addAll(response.body().getData());

                    listHeader = new ArrayList<>();
                    listHeader.addAll(response.body().getHeader());

                    //Show header image
                    Glide.with(imgCatHeaderId)
                            .load(response.body().getHeader().get(0).getDescription().getValue())
                            .into(imgCatHeaderId);

                    singleCategoryAdapter = new SingleCategoryAdapter(SportsActivity.this, listData, listHeader);
                    singleCategoryRec.setLayoutManager(new LinearLayoutManager(SportsActivity.this, RecyclerView.VERTICAL, false));
                    singleCategoryRec.setAdapter(singleCategoryAdapter);

                    /*Toast.makeText(SportsActivity.this, response.body().getData().get(0).getName(), Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<SingleCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SportsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callSingleCategoryList(SharePrefrancClass.getInstance(SportsActivity.this).getPref("catIdFromHome" ));
    }
}