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
}
