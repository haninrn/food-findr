package com.example.foodfindr2.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.foodfindr2.AppDatabase;
import com.example.foodfindr2.dao.DonationDao;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;
import com.example.foodfindr2.model.Item;
import com.example.foodfindr2.repositories.DonationRepository;
import com.example.foodfindr2.utils.CurrentUser;

import java.util.ArrayList;
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

    public LiveData<Donation> getDonationById(int donationId) {
        return donationDao.getDonationById(donationId);
    }


    public void addDonationWithItems(Donation donation, List<Item> items) {
        donationRepository.insertDonationWithItems(donation, items);
    }

    public void updateDonationStatus(int donationId, String newStatus) {
        donationRepository.updateDonationStatus(donationId, newStatus);
    }

    public LiveData<String> getDonorName(int donationId) {
        return donationDao.getDonorName(donationId);


    }

    public void updateDonationReceiver(int donationId, int receiverId, String newStatus) {
        // Assuming you have a repository to handle database operations
        donationRepository.updateDonationReceiver(donationId, receiverId, newStatus);
    }

    public LiveData<String> getItemName(int itemId) {
        return donationRepository.getItemNameById(itemId);
    }

    public LiveData<String> getItemNameByDonationId(int donationId) {
        return donationRepository.getItemNameByDonationId(donationId);
    }

    public LiveData<List<Donation>> getMyPosts() {
        return donationRepository.getDonationsByUser(CurrentUser.getInstance().getUserId());
    }



    public LiveData<List<Donation>> getPendingRequests() {
        int currentUserId = CurrentUser.getInstance().getUserId();
        return Transformations.map(getMyPosts(), donations -> {
            List<Donation> filtered = new ArrayList<>();
            for (Donation donation : donations) {
                if ("Pending".equals(donation.getStatus()) && donation.getDonor_id() == currentUserId) {
                    filtered.add(donation);
                }
            }
            return filtered;
        });
    }

    public LiveData<List<Donation>> getClaimedDonations() {
        int currentUserId = CurrentUser.getInstance().getUserId();
        return Transformations.map(getMyPosts(), donations -> {
            List<Donation> claimedDonations = new ArrayList<>();
            for (Donation donation : donations) {
                // Add null check for receiver_id
                if (donation.getReceiver_id() != null && donation.getReceiver_id() == currentUserId && "Claimed".equals(donation.getStatus())) {
                    claimedDonations.add(donation);
                }
            }
            return claimedDonations;
        });
    }









}
