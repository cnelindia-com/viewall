package com.example.viewall.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.viewall.models.databasemodels.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "urlListDataBase";
    private static final String TABLE_VIDEOS = "videosurl";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_VIDEO_PATH = "path";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIDEOS +
                "("
                + "id" + " INTEGER PRIMARY KEY,"
                + "name" + " TEXT,"
                + "path" + " TEXT"
                + ")";

        sqLiteDatabase.execSQL(CREATE_VIDEOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    //Code to add new data
    public void addData(VideoModel videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", videoUrl.getName());
        values.put("path", videoUrl.getVideoUrl());

        //Inserting Row
        db.insert(TABLE_VIDEOS, null, values);
//        Log.d("TESTRETURN" , String.valueOf(db.insert(TABLE_VIDEOS, null, values)));


        /*String vName = values.get("VIDEO_NAME").toString();
        String vPath = values.get("KEY_VIDEO_PATH").toString();
        db.execSQL("INSERT INTO" +
                " videosurl "+
                "VALUES("+vName+"," +vPath+");");*/

        db.close();
    }

    //Code for get the list of videos url and name
    public List<VideoModel> getAllVideoData(){
        List<VideoModel> videosData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_VIDEOS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                VideoModel videoModel = new VideoModel();
                videoModel.set_id(Integer.parseInt(cursor.getString(0)));
                videoModel.setName(cursor.getString(1));
                videoModel.setVideoUrl(cursor.getString(2));

                //Adding videomodel to list
                videosData.add(videoModel);
            } while (cursor.moveToNext());
        }


        return videosData;
    }

    // code to get the single video url
    /*public VideoModel getVideoUrl() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VIDEOS, new String[]{KEY_ID, KEY_NAME,
                        KEY_VIDEO_PATH}, KEY_ID + "=?",
                new String[]{String.valueOf(KEY_ID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        VideoModel videoModel = new VideoModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return videoModel;
    }*/
}
