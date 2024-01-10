package com.example.androidfinalproject;

public class NasaImage {

    // use builder pattern for creating NasaImage objects?
    // store variables of Nasa Images from JSON payload
    private String Date;
    private String Url;
    private String HdUrl;

    // NasaImage Setters
    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void setHdUrl(String HdUrl) {
        this.HdUrl = HdUrl;
    }

    // NasaImage Getters
    public String getDate() { return Date; }
    public String getUrl() { return Url; }
    public String getHdUrl() { return HdUrl; }


}
