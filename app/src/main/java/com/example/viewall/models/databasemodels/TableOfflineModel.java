package com.example.viewall.models.databasemodels;

public class TableOfflineModel {

    int _id;
    String adStart;
    String videoStart;
    String videoId;
    String contactId;
    String datetime;
    String videoend;

    public TableOfflineModel(String adStart, String videoStart, String videoId, String contactId, String datetime, String videoend) {
        this.adStart = adStart;
        this.videoStart = videoStart;
        this.videoId = videoId;
        this.contactId = contactId;
        this.datetime = datetime;
        this.videoend = videoend;
    }

    public TableOfflineModel(String adStart, String videoStart, String videoId, String contactId, String datetime) {
        this.adStart = adStart;
        this.videoStart = videoStart;
        this.videoId = videoId;
        this.contactId = contactId;
        this.datetime = datetime;
    }

    public TableOfflineModel(String adStart, String videoStart, String videoId, String contactId) {
        this.adStart = adStart;
        this.videoStart = videoStart;
        this.videoId = videoId;
        this.contactId = contactId;
    }

    public TableOfflineModel() {
    }

    public TableOfflineModel(String adStart, String videoStart) {
        this.adStart = adStart;
        this.videoStart = videoStart;
    }

    public TableOfflineModel(int _id, String adStart, String videoStart) {
        this._id = _id;
        this.adStart = adStart;
        this.videoStart = videoStart;
    }

    public String getVideoend() {
        return videoend;
    }

    public void setVideoend(String videoend) {
        this.videoend = videoend;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAdStart() {
        return adStart;
    }

    public void setAdStart(String adStart) {
        this.adStart = adStart;
    }

    public String getVideoStart() {
        return videoStart;
    }

    public void setVideoStart(String videoStart) {
        this.videoStart = videoStart;
    }
}
