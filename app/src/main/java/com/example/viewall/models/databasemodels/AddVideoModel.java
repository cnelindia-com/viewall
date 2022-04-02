package com.example.viewall.models.databasemodels;

public class AddVideoModel {

    int _id;
    String addvideoUrl;

    public AddVideoModel() {
    }

    public AddVideoModel(String addvideoUrl) {
        this.addvideoUrl = addvideoUrl;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddvideoUrl() {
        return addvideoUrl;
    }

    public void setAddvideoUrl(String addvideoUrl) {
        this.addvideoUrl = addvideoUrl;
    }
}
