package com.example.foodfindr2.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodfindr2.AppDatabase;
import com.example.foodfindr2.dao.DonationDao;
import com.example.foodfindr2.dao.ItemDao;
import com.example.foodfindr2.model.Donation;
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

    public LiveData<List<Donation>> getDonationsByUser(int userId) {
        return donationDao.getDonationsByUser(userId);
    }

    public LiveData<List<Donation>> getClaimedDonationsByUser(int userId) {
        return donationDao.getClaimedDonationsByUser(userId);
    }


}
