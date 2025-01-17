package com.example.foodfindr2.adapter;

public class RecommendationItem {
    private String title;
    private String location;
    private String status;
    private String owner;
    private double rating;
    private byte[] imageBlob;
    private int donationId;

    public RecommendationItem(String title, String location, String status, String owner, double rating, byte[] imageBlob, int donationId) {
        this.title = title;
        this.location = location;
        this.status = status;
        this.owner = owner;
        this.rating = rating;
        this.imageBlob = imageBlob;
        this.donationId = donationId;
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

    public byte[] getImageResId() {
        return imageBlob;
    }

    public int getDonationId() {
        return donationId;
    }
}