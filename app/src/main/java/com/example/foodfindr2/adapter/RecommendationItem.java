package com.example.foodfindr2.adapter;

public class RecommendationItem {
    private String title;
    private String location;
    private String status;
    private String owner;
    private double rating;
    private int imageResId;

    public RecommendationItem(String title, String location, String status, String owner, double rating, int imageResId) {
        this.title = title;
        this.location = location;
        this.status = status;
        this.owner = owner;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }

    public double getRating() {
        return rating;
    }

    public int getImageResId() {
        return imageResId;
    }
}