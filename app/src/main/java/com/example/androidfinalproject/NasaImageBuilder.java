package com.example.androidfinalproject;


/**
 *  This class helps with the creation of NasaImage objects
 *
 * @author Byron, Alycia
 */
public class NasaImageBuilder {
    private String date;
    private String url;
    private String hdUrl;
    private String title;
    private String filePath;

    public NasaImageBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public NasaImageBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public NasaImageBuilder setHdUrl(String hdUrl) {
        this.hdUrl = hdUrl;
        return this;
    }

    public NasaImageBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public NasaImageBuilder setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public NasaImage createNasaImage() {
        return new NasaImage(date, url, hdUrl, title, filePath);
    }
}