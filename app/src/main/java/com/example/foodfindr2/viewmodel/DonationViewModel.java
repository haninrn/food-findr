package com.example.foodfindr2.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodfindr2.AppDatabase;
import com.example.foodfindr2.dao.DonationDao;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;
import com.example.foodfindr2.model.Item;
import com.example.foodfindr2.repositories.DonationRepository;

import java.util.List;
import java.util.concurrent.Executors;

public class DonationViewModel extends AndroidViewModel {
    private final DonationDao donationDao;
    private final LiveData<List<DonationWithItems>> availableDonations;

    private final DonationRepository donationRepository;

    public DonationViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        donationDao = db.donationDao();
        availableDonations = donationDao.getAllDonationsWithItems();
        donationRepository = new DonationRepository(application, Executors.newSingleThreadExecutor());
    }


    public LiveData<List<DonationWithItems>> getAvailableDonations() {
        return availableDonations;
    }

    public void addDonationWithItems(Donation donation, List<Item> items) {
        donationRepository.insertDonationWithItems(donation, items);
    }
}
