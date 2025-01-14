package com.example.foodfindr2.repositories;

import android.content.Context;

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
}
