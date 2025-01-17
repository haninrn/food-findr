package com.example.foodfindr2.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodfindr2.model.Review;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    void insertReview(Review review);

    @Query("SELECT * FROM reviews WHERE donationId = :donationId")
    List<Review> getReviewsForDonation(int donationId);

    @Query("SELECT * FROM reviews WHERE reviewerId = :reviewerId")
    List<Review> getReviewsByUser(int reviewerId);

    @Query("SELECT AVG(rating) FROM reviews WHERE donationId = :donationId")
    float getAverageRatingForDonation(int donationId);

    @Query("DELETE FROM reviews WHERE reviewId = :reviewId")
    void deleteReview(int reviewId);
}

