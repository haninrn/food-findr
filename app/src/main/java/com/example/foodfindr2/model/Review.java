package com.example.foodfindr2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reviews")
public class Review {

    @PrimaryKey(autoGenerate = true)
    private int reviewId;
    private int donationId; // Foreign key to donations table
    private int reviewerId; // Foreign key to users table
    private int rating; // Star rating (1 to 5)
    private String comment; // Feedback message
    private String dateReviewed;

    // Getters and setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(String dateReviewed) {
        this.dateReviewed = dateReviewed;
    }
}
