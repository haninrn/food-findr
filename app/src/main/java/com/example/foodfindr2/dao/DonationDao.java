package com.example.foodfindr2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;

import java.util.List;

@Dao
public interface DonationDao {

    // Insert a donation
    @Insert
    long insertDonation(Donation donation);

    // Insert multiple donations (if required)
    @Insert
    List<Long> insertDonations(List<Donation> donations);

    // Update a donation
    @Update
    void updateDonation(Donation donation);

    // Get all donations
    @Query("SELECT * FROM donations")
    List<Donation> getAllDonations();

    // Get donations by status
    @Query("SELECT * FROM donations WHERE status = :status")
    List<Donation> getDonationsByStatus(String status);

    // Get donation with items (join query)
    @Transaction
    @Query("SELECT * FROM donations WHERE donation_id = :donationId")
    DonationWithItems getDonationWithItems(int donationId);

    // Get all donations with their items
    @Transaction
    @Query("SELECT * FROM donations")
    LiveData<List<DonationWithItems>> getAllDonationsWithItems();

    //get donation by ID
    @Query("SELECT * FROM donations WHERE donation_id = :donationId")
    LiveData<Donation> getDonationById(int donationId);


    @Query("UPDATE donations SET status = :status WHERE donation_id = :donationId")
    void updateDonationStatus(int donationId, String status);

    @Query("SELECT users.username FROM users INNER JOIN donations ON users.id = donations.donor_id WHERE donations.donation_id = :donationId")
    LiveData<String> getDonorName(int donationId);

    @Query("UPDATE donations SET receiver_id = :receiverId, status = :newStatus WHERE donation_id = :donationId")
    void updateReceiverAndStatus(int donationId, int receiverId, String newStatus);

    @Query("SELECT * FROM donations WHERE donor_id = :userId")
    LiveData<List<Donation>> getDonationsByUser(int userId);


    @Query("SELECT * FROM donations WHERE receiver_id = :userId")
    LiveData<List<Donation>> getClaimedDonationsByUser(int userId);




}