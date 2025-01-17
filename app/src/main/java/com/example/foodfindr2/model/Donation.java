package com.example.foodfindr2.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(
        tableName = "donations",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "donor_id"),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "receiver_id")
        },
        indices = {
                @Index(value = "donor_id"),
                @Index(value = "receiver_id")
        }
)
@TypeConverters(DateConverter.class) // Converts Date to a usable format for Room
public class Donation {
    @PrimaryKey(autoGenerate = true)
    public int donation_id; // Primary key for donation

    public int donor_id; // Foreign key to User (Donor)
    @Nullable
    public Integer receiver_id; // Nullable foreign key to User (Receiver)

    public String status; // Status: Available, Claimed, Pending, Completed, etc.
    public Date date_posted; // Date the donation was posted
    @Nullable
    public Date date_claimed; // Nullable: when the donation was claimed
    @Nullable
    public Date date_completed; // Nullable: when the donation was completed

    public String description; // Description of the donation
    public String pickup_instructions; // Pickup instructions for the receiver

    public String address; // Address for the specific donation
    public String city; // City for the specific donation

    @Nullable
    public byte[] image_blob;

    @Nullable
    public Float rating;

    // Getters and Setters
    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public int getDonor_id() {
        return donor_id;
    }

    public void setDonor_id(int donor_id) {
        this.donor_id = donor_id;
    }

    @Nullable
    public Integer getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(@Nullable Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(Date date_posted) {
        this.date_posted = date_posted;
    }

    @Nullable
    public Date getDate_claimed() {
        return date_claimed;
    }

    public void setDate_claimed(@Nullable Date date_claimed) {
        this.date_claimed = date_claimed;
    }

    @Nullable
    public Date getDate_completed() {
        return date_completed;
    }

    public void setDate_completed(@Nullable Date date_completed) {
        this.date_completed = date_completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPickup_instructions() {
        return pickup_instructions;
    }

    public void setPickup_instructions(String pickup_instructions) {
        this.pickup_instructions = pickup_instructions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getRating() {
        return rating != null ? rating : 0.0f;
    }


    public void setRating(@Nullable Float rating) {
        this.rating = rating;
    }


}
