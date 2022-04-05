package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewall.R;
import com.example.viewall.models.databasemodels.TableOfflineModel;
import com.example.viewall.utils.DatabaseHandler;
import com.example.viewall.utils.ScalableVideoView;
import com.example.viewall.utils.SharePrefrancClass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoShowActivityOffline extends AppCompatActivity {

    TextView txtVideoName;
    ProgressBar progressbarVideo;
    String strVideoNameOff, strVideoUrlOff, strVideoIdOff, strAdVideoUrlOff;

    String date;

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show_offline);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        progressbarVideo = findViewById(R.id.progressbarVideo);
        txtVideoName = findViewById(R.id.txtVideoName);

        databaseHandler = new DatabaseHandler(this);

        //Code for get the current data and time
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        date = sdf.format(new Date());

        Intent intent = getIntent();
        strVideoUrlOff = intent.getStringExtra("videoUrlOffline");
        strVideoIdOff = intent.getStringExtra("videoIdOffline");
        strAdVideoUrlOff = intent.getStringExtra("adVideoUrlOffline");
        txtVideoName.setText(intent.getStringExtra("videoNameOffline"));

        /*Toast.makeText(VideoShowActivityOffline.this, intent.getStringExtra("videoNameOffline")
                + "\n" + intent.getStringExtra("videoUrlOffline"), Toast.LENGTH_SHORT).show();*/

        //Call here the method for run the video
        runVideo(intent.getStringExtra("adVideoUrlOffline"),
                intent.getStringExtra("videoUrlOffline"));
    }

    private void runVideo(String adUrl, String url) {
        try {
//            String link="http://s1133.photobucket.com/albums/m590/Anniebabycupcakez/?action=view&amp; current=1376992942447_242.mp4";
            /*String link = url;*/
            ScalableVideoView videoView = findViewById(R.id.VideoView);

            progressbarVideo.setVisibility(View.VISIBLE);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            /*Uri video = Uri.parse("https://www.w3schools.com/html/mov_bbb.mp4");*/
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
            Uri video = Uri.parse(adUrl);
            videoView.setMediaController(null);
            videoView.setVideoURI(video);
            videoView.start();


            //Calling watch1 api when ad video start

            //Calling watch2 api when ad video start


            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    /*Toast.makeText(VideoShowActivity.this, "Complete", Toast.LENGTH_SHORT).show();*/
                    //Calling watch3 api, when ad video end

                    Uri video = Uri.parse(url);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);
                    videoView.start();

                    //Saving offline data information here
                    databaseHandler.addOfflineData(new TableOfflineModel("advert", "video",
                            strVideoIdOff,
                            SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                    .getPref("phone_number"), date));

                    //Calling watch4 api, when video start

                    //Code for convert the hhmmss in seconds


                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            /*Toast.makeText(VideoShowActivity.this, "Finished", Toast.LENGTH_SHORT).show();*/
                            //Calling watch5 api, when video end

                            //Trying to saving only date in database
                            databaseHandler.addOfflineData(new TableOfflineModel("", "",
                                    "", "", date, "end"));
                        }
                    });

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
}