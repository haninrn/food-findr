package com.example.foodfindr2.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(
        tableName = "items",
        foreignKeys = {
                @ForeignKey(entity = Donation.class, parentColumns = "donation_id", childColumns = "donation_id")
        },
        indices = {
                @Index(value = "donation_id")
        }
)
@TypeConverters(DateConverter.class)
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int item_id; // Primary key for the item

    public int donation_id; // Foreign key linking the item to its donation
    public String item_name; // Name of the item
    public String category; // Category: Fruits, Vegetables, etc.
    @Nullable
    public Date manufacture_date; // Nullable: manufacture date
    @Nullable
    public Date expiry_date; // Nullable: expiry date
    @Nullable
    public byte[] image_blob; // Image stored as a byte array (nullable)
}
