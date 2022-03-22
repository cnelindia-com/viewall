package com.example.viewall.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewall.R;
import com.example.viewall.adapters.HomeAddSliderAdapter;
import com.example.viewall.adapters.HomeCategoryAdapter;
import com.example.viewall.adapters.PopularVideoHomeAdapter;
import com.example.viewall.broadcastrecivers.NetworkReceiver;
import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.databasemodels.VideoModel;
import com.example.viewall.models.homecategorylist.DataItem;
import com.example.viewall.models.homecategorylist.HomeCategoryResponse;
import com.example.viewall.models.index.IndexResponse;
import com.example.viewall.models.index1.Index1Response;
import com.example.viewall.models.popularviedos.PopularVideoResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.DatabaseHandler;
import com.example.viewall.utils.SharePrefrancClass;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navigationView;
    TextView tvSports, txtMusic, txtGospel, txtSoap, txtComedy, txtLifestyle, txtCartoons, txtBeauty;
    LinearLayout popularVideoLayoutId;
    ImageView icn_hamburger, img1;
    /*RecyclerView recPopularVideoId;*/
    RecyclerView popularVideoRec, homeCategoryRec;
    PopularVideoHomeAdapter popularVideoHomeAdapter;
    SliderView imageSlider;
    TextView userNameTxtId;
    int myImageList[] = {R.drawable.addicon, R.drawable.banner2, R.drawable.banner1};

    ArrayList<DataItem> listCategory;
    HomeCategoryAdapter homeCategoryAdapter;

    ArrayList<com.example.viewall.models.bannerlist.DataItem> bannerList;
    ArrayList<com.example.viewall.models.popularviedos.DataItem> popularVideoList;

    ProgressDialog progressDialog;

    DatabaseHandler databaseHandler;

    String fileToDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toast.makeText(HomeActivity.this, SharePrefrancClass.getInstance(HomeActivity.this).getPref("baseurl"), Toast.LENGTH_SHORT).show();

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

//        Toast.makeText(HomeActivity.this, "MAC "+getMacAddress(), Toast.LENGTH_SHORT).show();

        //Checking app is connected to net or not connected returns true if connected
        /*if (isNetworkConnected()){
            Toast.makeText(HomeActivity.this, "Connected to Internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HomeActivity.this, "you are offline", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, DownloanActivity.class));
        }*/

        //Code for create folder
        File dir = new File(Environment.getExternalStorageDirectory() + "/Download/view4all/");
        dir.mkdirs(); // creates needed dirs

        databaseHandler = new DatabaseHandler(this);

        //Call get data method for get data from database
        /*databaseHandler.getAllVideoData();
        List<VideoModel> data = databaseHandler.getAllVideoData();*/
        /*Toast.makeText(HomeActivity.this, data.get(0).getVideoUrl(), Toast.LENGTH_SHORT).show();*/

        NetworkReceiver networkReceiver = new NetworkReceiver();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(null);
        toolbar.setSubtitle("");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        imageSlider = findViewById(R.id.imageSlider);
        /*imageSlider.setSliderAdapter(new HomeAddSliderAdapter(myImageList, HomeActivity.this));
        imageSlider.startAutoCycle();*/


        userNameTxtId = findViewById(R.id.userNameTxtId);
        userNameTxtId.setText(SharePrefrancClass.getInstance(HomeActivity.this).getPref("fname"));

        /*recPopularVideoId = findViewById(R.id.recPopularVideoId);*/
        popularVideoRec = findViewById(R.id.popularVideoRec);

        homeCategoryRec = findViewById(R.id.homeCategoryRec);

        tvSports = findViewById(R.id.tvSports);
        txtMusic = findViewById(R.id.txtMusic);
        txtGospel = findViewById(R.id.txtGospel);
        txtSoap = findViewById(R.id.txtSoap);
        txtComedy = findViewById(R.id.txtComedy);
        txtLifestyle = findViewById(R.id.txtLifestyle);
        txtCartoons = findViewById(R.id.txtCartoons);
        txtBeauty = findViewById(R.id.txtBeauty);

        popularVideoLayoutId = findViewById(R.id.popularVideoLayoutId);
        icn_hamburger = findViewById(R.id.icn_hamburger);
        img1 = findViewById(R.id.img1);

        int popularImages[] = {R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5};

        /*popularVideoHomeAdapter = new PopularVideoHomeAdapter(getApplicationContext(), popularImages);
        popularVideoRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        popularVideoRec.setAdapter(popularVideoHomeAdapter);*/

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, VideoShowActivity.class));
            }
        });

        txtSoap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtLifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtCartoons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        tvSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtGospel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        popularVideoLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, VideoShowActivity.class));
            }
        });

        icn_hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of Popupmenu
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, view);
                //Inflating the Popup using xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                //registering popup with OnMenuItemClickListener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.term_item) {
                            startActivity(new Intent(HomeActivity.this, TermConditionsActivity.class));
                        } else if (item.getItemId() == R.id.contact_item) {
                            startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
                        }
                        return true;
                    }
                });
                popupMenu.show(); //showing popup menu
            }

        });

//        setSupportActionBar(binding.appBarHome.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Calling index api.
        callIndexApi();


        //Calling category list api for get the list in home page
        callCategoryList();

        //Calling banner api.
        callBannerListApi();

        //Calling popular api.
        callPopularVideoApi();

    }

    private void callBannerListApi() {
        progressDialog.show();

        Call<BannerResponse> call = RetrofitClient.getInstance().getMyApi().bannerList();

        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    bannerList = new ArrayList<>();
                    bannerList.addAll(response.body().getData());
                    imageSlider.setSliderAdapter(new HomeAddSliderAdapter(bannerList, HomeActivity.this));
                    imageSlider.startAutoCycle();

                    //Calling index1 api
                    callIndex1Api();
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callPopularVideoApi() {
        progressDialog.show();

        Call<PopularVideoResponse> call = RetrofitClient.getInstance().getMyApi().popularVideos();

        call.enqueue(new Callback<PopularVideoResponse>() {
            @Override
            public void onResponse(Call<PopularVideoResponse> call, Response<PopularVideoResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    popularVideoList = new ArrayList<>();
                    popularVideoList.addAll(response.body().getData());

                    popularVideoHomeAdapter = new PopularVideoHomeAdapter(getApplicationContext(), popularVideoList);
                    popularVideoRec.setLayoutManager(new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false));
                    popularVideoRec.setAdapter(popularVideoHomeAdapter);
                }
            }

            @Override
            public void onFailure(Call<PopularVideoResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callIndex1Api() {
        String bannerUrl = "";
        String tempStr = "";
        for (int i = 0; i < bannerList.size(); i++) {
            tempStr = bannerList.get(i).getImageUrl().replace("http://dev.view4all.tv/content/", "");
            bannerUrl = bannerUrl + ", " + tempStr;
        }
        Log.d("BANNERURL", bannerUrl);

        Call<Index1Response> call = RetrofitClient.getInstance().getMyApi().index1(
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("phone_number"),
                bannerUrl);

        call.enqueue(new Callback<Index1Response>() {
            @Override
            public void onResponse(Call<Index1Response> call, Response<Index1Response> response) {
                if (response.body() != null) {
                    /*Toast.makeText(HomeActivity.this, "Index status:" + response.body().getStatus(), Toast.LENGTH_SHORT).show();*/
                    Log.d("index1res", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Index1Response> call, Throwable t) {
                /*Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();*/
                Log.d("index1fail", t.getMessage());
            }
        });
    }

    private void callIndexApi() {
        progressDialog.show();

        Call<IndexResponse> call = RetrofitClient.getInstance().getMyApi().index(
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("phone_number"));

        call.enqueue(new Callback<IndexResponse>() {
            @Override
            public void onResponse(Call<IndexResponse> call, Response<IndexResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    /*Toast.makeText(HomeActivity.this, "Index status:" + response.body().getStatus(), Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<IndexResponse> call, Throwable t) {
                progressDialog.dismiss();
                /*Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    private void callCategoryList() {
        progressDialog.show();
        Call<HomeCategoryResponse> call = RetrofitClient.getInstance().getMyApi().homeCategory();

        call.enqueue(new Callback<HomeCategoryResponse>() {
            @Override
            public void onResponse(Call<HomeCategoryResponse> call, Response<HomeCategoryResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    listCategory = new ArrayList<>();
                    listCategory.addAll(response.body().getData());
                    //Setting the recycler adapter
                    homeCategoryAdapter = new HomeCategoryAdapter(HomeActivity.this, listCategory);
                    //Setting staggered layout in recyceler view
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    //Setting the adapter of recycler view
                    homeCategoryRec.setAdapter(homeCategoryAdapter);
                    // Setting LayoutManager to RecyclerView
                    homeCategoryRec.setLayoutManager(staggeredGridLayoutManager);

                }
            }

            @Override
            public void onFailure(Call<HomeCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.home, menu);*/
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Menu menu = navigationView.getMenu();
        if (id == R.id.nav_home) {
            Toast.makeText(HomeActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_category) {
            startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
        } else if (id == R.id.nav_tac) {
            Toast.makeText(HomeActivity.this, "Term and conditions", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_contact) {
            Toast.makeText(HomeActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.hamburger) {
            Toast.makeText(HomeActivity.this, "Menu Item", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


}