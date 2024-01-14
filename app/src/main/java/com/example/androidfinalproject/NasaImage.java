package com.example.androidfinalproject;


public class NasaImage {

    // use builder pattern for creating NasaImage objects?
    // store variables of Nasa Images from JSON payload
    private String date;
    private String url;
    private String hdUrl;
    private String title;
    private String imageFilePath;

    // NasaImage Setters
    public void setDate(String date) {
        this.date = date;
    }
    public void setTitle(String title) { this.title = title; }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHdUrl(String hdUrl) {
        this.hdUrl = hdUrl;
    }

    public void setImageFilePath(String filePath) { this.imageFilePath = imageFilePath; }

    // NasaImage Getters
    public String getDate() { return date; }
    public String getUrl() { return url; }
    public String getHdUrl() { return hdUrl; }
    public String getTitle() { return title; }
    public String getImageFilePath() { return imageFilePath;}


}
