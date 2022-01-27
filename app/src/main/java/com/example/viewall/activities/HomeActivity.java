package com.example.viewall.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.viewall.adapters.PopularVideoHomeAdapter;
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

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navigationView;
    TextView tvSports, txtMusic, txtGospel, txtSoap, txtComedy, txtLifestyle, txtCartoons, txtBeauty;
    LinearLayout popularVideoLayoutId;
    ImageView icn_hamburger, img1;
    /*RecyclerView recPopularVideoId;*/
    RecyclerView popularVideoRec;
    PopularVideoHomeAdapter popularVideoHomeAdapter;
    SliderView imageSlider;
    int myImageList[] = {R.drawable.addicon, R.drawable.banner2, R.drawable.banner1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toast.makeText(HomeActivity.this, SharePrefrancClass.getInstance(HomeActivity.this).getPref("baseurl"), Toast.LENGTH_SHORT).show();

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(null);
        toolbar.setSubtitle("");

        imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setSliderAdapter(new HomeAddSliderAdapter(myImageList, HomeActivity.this));
        imageSlider.startAutoCycle();

        /*recPopularVideoId = findViewById(R.id.recPopularVideoId);*/
        popularVideoRec = findViewById(R.id.popularVideoRec);

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

        popularVideoHomeAdapter = new PopularVideoHomeAdapter(getApplicationContext(), popularImages);
        popularVideoRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        popularVideoRec.setAdapter(popularVideoHomeAdapter);

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
}