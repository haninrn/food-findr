package com.example.foodfindr2.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DonationWithItems {

    @Embedded
    public Donation donation; // Embeds the parent Donation entity

    @Relation(parentColumn = "donation_id", entityColumn = "donation_id")
    public List<Item> items; // List of associated items for the donation
}
