package com.example.foodfindr2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import java.util.Date;

@Entity(tableName = "reviews",
        foreignKeys = @ForeignKey(entity = Donation.class, parentColumns = "donation_id", childColumns = "donation_id"))
public class Review {
    @PrimaryKey(autoGenerate = true)
    public int review_id;
    public int donation_id;
    public int reviewer_id;
    public int rating;
    public String comment;
    public Date date_reviewed;
}
