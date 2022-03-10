package com.example.viewall.models.databasemodels;

public class VideoModel {

    int _id;
    String name;
    String videoUrl;

    public VideoModel() {
    }

    public VideoModel(String name, String videoUrl) {
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public VideoModel(int _id, String name, String videoUrl) {
        this._id = _id;
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
