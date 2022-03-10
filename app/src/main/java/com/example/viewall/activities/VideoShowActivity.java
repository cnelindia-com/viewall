package com.example.viewall.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.viewall.R;
import com.example.viewall.adapters.HomeAddSliderAdapter;
import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.databasemodels.VideoModel;
import com.example.viewall.models.singlevideo.SingleVideoResponse;
import com.example.viewall.models.track.TrackResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.DatabaseHandler;
import com.example.viewall.utils.ScalableVideoView;
import com.example.viewall.utils.SharePrefrancClass;
import com.smarteist.autoimageslider.SliderView;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.Func;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoShowActivity extends AppCompatActivity {

    ImageView imgBack;
    Intent getData;
    String strVideoId, strChannelName;
    ProgressDialog progressDialog;
    ProgressBar progressbarVideo;
    ImageView imgDownloadId;
    SliderView imageSlider;

    TextView txtVideoName, txtVideoDuration, txtChannelNameId;

    //Creating reference variable of fetch
    private Fetch fetch;

    String strVideoUrlForDownload;
    String strVideoName, strAddVideoName;

    String strDbVideoName;


    //Below is working code.
    String fileToDownload /*= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/view4all/" + strVideoName*/;

    private static final int REQ_CODE = 1;

    DatabaseHandler databaseHandler;

    ArrayList<com.example.viewall.models.bannerlist.DataItem> bannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        imageSlider = findViewById(R.id.imageSlider);

        databaseHandler = new DatabaseHandler(this);

        /*Toast.makeText(VideoShowActivity.this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString(), Toast.LENGTH_SHORT).show();*/

        //Configuring fetch
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(VideoShowActivity.this)
                .setDownloadConcurrentLimit(3)
                .build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        progressbarVideo = findViewById(R.id.progressbarVideo);

        imgDownloadId = findViewById(R.id.imgDownloadId);

        //Getting value from intent video id
        getData = getIntent();
        strVideoId = getData.getStringExtra("storedVideoId");
        strChannelName = getData.getStringExtra("storedChannelName");

        //Calling banner api.
        callBannerListApi();

        /*Toast.makeText(VideoShowActivity.this, strVideoId, Toast.LENGTH_SHORT).show();*/
        callSingleVideoApi();

        txtVideoName = findViewById(R.id.txtVideoName);
        txtVideoDuration = findViewById(R.id.txtVideoDuration);
        txtChannelNameId = findViewById(R.id.txtChannelNameId);

        txtChannelNameId.setText(strChannelName);


        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoShowActivity.this, SportsActivity.class));
            }
        });

        imgDownloadId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //code for permission.
                if (ActivityCompat.checkSelfPermission(VideoShowActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(VideoShowActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(VideoShowActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    }, REQ_CODE);
                } else {
                    /*Toast.makeText(VideoShowActivity.this, "Permission Granted.", Toast.LENGTH_SHORT).show();*/
//                    startActivity(new Intent(VideoShowActivity.this, SyncDataActivity.class));
                    callDownload();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    /*Toast.makeText(this, "Request permission method", Toast.LENGTH_SHORT).show();*/
                    /*startActivity(new Intent(VideoShowActivity.this, SyncDataActivity.class));*/
//                    callDownload();
                } else {

                }
                break;
        }
    }

    //Method for call the download function
    private void callDownload() {

        fileToDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/view4all/" + strVideoName;


        final Request request = new Request(strVideoUrlForDownload, fileToDownload);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(@NonNull Request result) {
                Toast.makeText(VideoShowActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload));
            }
        }, new Func<Error>() {
            @Override
            public void call(@NonNull Error result) {
                Toast.makeText(VideoShowActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callTrackApi() {
        progressDialog.show();

        SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("id");

        Call<TrackResponse> call = RetrofitClient.getInstance().getMyApi()
                .sendTrack(SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("id"),
                        "video");

        call.enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Toast.makeText(VideoShowActivity.this,
                            response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VideoShowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSingleVideoApi() {
        progressDialog.show();

        Call<SingleVideoResponse> call = RetrofitClient.getInstance().getMyApi().singleVideo(strVideoId);

        call.enqueue(new Callback<SingleVideoResponse>() {
            @Override
            public void onResponse(Call<SingleVideoResponse> call, Response<SingleVideoResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Toast.makeText(VideoShowActivity.this, response.body().getData().get(0).getUrlVideo(), Toast.LENGTH_SHORT).show();
                    Log.d("dataValue", response.body().getData().get(0).getUrlVideo());

                    txtVideoName.setText(response.body().getData().get(0).getDescription().getName());
                    txtVideoDuration.setText(response.body().getData().get(0).getTime());
                    strDbVideoName = response.body().getData().get(0).getDescription().getName();


                    strVideoName = response.body().getData().get(0).getUrlVideo()
                            .replace("http://dev.view4all.tv/content/", "");

                    //Add video add name
                    strAddVideoName = response.body().getData().get(0).getAddUrlVideo();

                    strVideoUrlForDownload = response.body().getData().get(0).getUrlVideo();
                    //Method for try running video
                    runVideo(response.body().getData().get(0).getUrlVideo());
                }
            }

            @Override
            public void onFailure(Call<SingleVideoResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VideoShowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void runVideo(String url) {
        try {
//            String link="http://s1133.photobucket.com/albums/m590/Anniebabycupcakez/?action=view&amp; current=1376992942447_242.mp4";
            String link = url;
            ScalableVideoView videoView = findViewById(R.id.VideoView);

            progressbarVideo.setVisibility(View.VISIBLE);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            /*Uri video = Uri.parse("https://www.w3schools.com/html/mov_bbb.mp4");*/
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
            Uri video = Uri.parse(strAddVideoName);
            videoView.setMediaController(null);
            videoView.setVideoURI(video);
            videoView.start();

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    /*Toast.makeText(VideoShowActivity.this, "Complete", Toast.LENGTH_SHORT).show();*/
                    Uri video = Uri.parse(link);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);
                    videoView.start();

                }
            });

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                            progressbarVideo.setVisibility(View.GONE);
                            mediaPlayer.start();
                        }
                    });
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
        }
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
                    imageSlider.setSliderAdapter(new HomeAddSliderAdapter(bannerList, VideoShowActivity.this));
                    imageSlider.startAutoCycle();
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VideoShowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}