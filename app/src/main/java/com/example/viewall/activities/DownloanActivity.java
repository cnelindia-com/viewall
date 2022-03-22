package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import com.example.viewall.R;
import com.example.viewall.adapters.OfflineVideoAdapter;
import com.example.viewall.models.databasemodels.VideoModel;
import com.example.viewall.utils.DatabaseHandler;

import java.util.List;

public class DownloanActivity extends AppCompatActivity {

    List<VideoModel> offlineData;
    DatabaseHandler databaseHandler;

    RecyclerView downloadRec;
    OfflineVideoAdapter offlineVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloan);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        databaseHandler = new DatabaseHandler(this);

        //Call get data method for get data from database
        offlineData = databaseHandler.getAllVideoData();

        downloadRec = findViewById(R.id.downloadRec);

        offlineVideoAdapter = new OfflineVideoAdapter(DownloanActivity.this, offlineData);
        downloadRec.setLayoutManager(new LinearLayoutManager(DownloanActivity.this, RecyclerView.VERTICAL, false));
        downloadRec.setAdapter(offlineVideoAdapter);

    }
}