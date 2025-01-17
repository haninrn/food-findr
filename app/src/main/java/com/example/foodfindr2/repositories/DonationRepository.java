package com.example.foodfindr2.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodfindr2.AppDatabase;
import com.example.foodfindr2.dao.DonationDao;
import com.example.foodfindr2.dao.ItemDao;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;
import com.example.foodfindr2.model.Item;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class DonationRepository {
    private final DonationDao donationDao;
    private final ItemDao itemDao;
    private final ExecutorService executorService;

    public DonationRepository(Context context, ExecutorService executorService) {
        AppDatabase db = AppDatabase.getDatabase(context);
        this.donationDao = db.donationDao();
        this.itemDao = db.itemDao();
        this.executorService = executorService;
    }

    public void insertDonationWithItems(Donation donation, List<Item> items) {
        executorService.execute(() -> {
            long donationId = donationDao.insertDonation(donation);
            for (Item item : items) {
                item.donation_id = (int) donationId;
                itemDao.insertItem(item);
            }
        });
    }

    public void updateDonationStatus(int donationId, String newStatus) {
        executorService.execute(() -> donationDao.updateDonationStatus(donationId, newStatus));
    }

    public void updateDonationReceiver(int donationId, int receiverId, String newStatus) {
        // Assuming you are using Room or a similar library
        executorService.execute(() -> donationDao.updateReceiverAndStatus(donationId, receiverId, newStatus));
    }

    public LiveData<String> getItemNameById(int itemId) {
        return itemDao.getItemNameById(itemId);
    }

    public LiveData<String> getItemNameByDonationId(int donationId) {
        return itemDao.getItemNameByDonationId(donationId);
    }

    public LiveData<List<DonationWithItems>> getDonationsByUser(int userId) {
        return donationDao.getDonationsByUser(userId); // Correctly matches the DAO method's return type
    }


    public LiveData<List<Donation>> getClaimedDonationsByUser(int userId) {
        return donationDao.getClaimedDonationsByUser(userId);
    }

    public void updateDonationRating(int donationId, float rating) {
        executorService.execute(() -> donationDao.updateDonationRating(donationId, rating));
    }

    public LiveData<Float> getAverageRatingForUser(int userId) {
        return donationDao.getAverageRatingForUser(userId);
    }

    public LiveData<String> getUserName(int userId) {
        return donationDao.getUserName(userId);
    }

    public LiveData<Integer> getTotalDonatedCount(int userId) {
        return donationDao.getTotalDonatedCount(userId);
    }

    public LiveData<Integer> getTotalTakenCount(int userId) {
        return donationDao.getTotalTakenCount(userId);
    }

    public LiveData<Integer> getTotalGivenCount(int userId) {
        return donationDao.getTotalGivenCount(userId);
    }

    public LiveData<String> getUsernameById(int userId) {
        return donationDao.getUsernameById(userId);
    }





}
