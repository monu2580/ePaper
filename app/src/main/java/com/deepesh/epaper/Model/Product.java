package com.deepesh.epaper.Model;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class Product {
    private int imageId;
    private String title;
    private String epaperUrl;
    private String paperUrl;

    public Product() {
    }

    public Product(int imageId, String title, String epaperUrl, String paperUrl) {
        this.imageId = imageId;
        this.title = title;
        this.epaperUrl = epaperUrl;
        this.paperUrl = paperUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpaperUrl() {
        return epaperUrl;
    }

    public void setEpaperUrl(String epaperUrl) {
        this.epaperUrl = epaperUrl;
    }

    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }
}
