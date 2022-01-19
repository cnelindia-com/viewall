package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.viewall.R;

public class SportsActivity extends AppCompatActivity {

    LinearLayout idiskLayoutId, soccerlifeLayoutId, soccerLayoutId, cricketLayoutId;
    ImageView img1, imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        idiskLayoutId = findViewById(R.id.idiskLayoutId);
        soccerlifeLayoutId = findViewById(R.id.soccerlifeLayoutId);
        soccerLayoutId = findViewById(R.id.soccerLayoutId);
        cricketLayoutId = findViewById(R.id.cricketLayoutId);
        img1 = findViewById(R.id.img1);
        imgBack = findViewById(R.id.imgBack);

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
}