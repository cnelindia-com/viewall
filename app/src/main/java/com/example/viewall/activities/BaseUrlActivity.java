package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.viewall.R;
import com.example.viewall.utils.SharePrefrancClass;

public class BaseUrlActivity extends AppCompatActivity {

    Button submitId;
    EditText baseUrlId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_url);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        submitId = findViewById(R.id.submitId);
        baseUrlId = findViewById(R.id.baseUrlId);

        submitId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePrefrancClass.getInstance(BaseUrlActivity.this).savePref("baseurl", baseUrlId.getText().toString());
                startActivity(new Intent(BaseUrlActivity.this, RegisterActivity.class));
            }
        });
    }
}