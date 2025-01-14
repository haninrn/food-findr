package com.example.foodfindr2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodfindr2.R;
import com.example.foodfindr2.utils.CurrentUser;
import com.example.foodfindr2.viewmodel.DonationViewModel;

public class ClaimDonationFragment extends Fragment {

    private TextView tvTitle, tvStatus, tvDonor, tvLocation, tvDescription;
    private Button btnClaim, btnRelease;
    private int donationId;
    private DonationViewModel donationViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_claim_donation, container, false);

        // Initialize views
        tvTitle = view.findViewById(R.id.itemTitle);
        tvStatus = view.findViewById(R.id.donationStatus);
        tvDonor = view.findViewById(R.id.ownerName);
        tvLocation = view.findViewById(R.id.donationLocation);
        tvDescription = view.findViewById(R.id.donationDescription);
        btnClaim = view.findViewById(R.id.claimButton);
        btnRelease = view.findViewById(R.id.releaseButton);

        donationViewModel = new ViewModelProvider(this).get(DonationViewModel.class);

        // Get passed donation ID
        donationId = getArguments() != null ? getArguments().getInt("donation_id") : -1;

        if (donationId != -1) {
            donationViewModel.getDonationById(donationId).observe(getViewLifecycleOwner(), donation -> {
                if (donation != null) {
                    // Populate UI with donation data
                    tvStatus.setText(donation.getStatus());
                    tvLocation.setText("Location: " + donation.getCity() + ", " + donation.getAddress());
                    tvDescription.setText(donation.getDescription());

                    // Fetch and display donor name
                    donationViewModel.getDonorName(donation.getDonor_id()).observe(getViewLifecycleOwner(), donorName -> {
                        if (donorName != null) {
                            tvDonor.setText("Donor: " + donorName);
                        } else {
                            tvDonor.setText("Donor: Unknown");
                        }
                    });

                    donationViewModel.getItemNameByDonationId(donationId).observe(getViewLifecycleOwner(), itemName -> {
                        if (itemName != null) {
                            tvTitle.setText(itemName);
                        } else {
                            tvTitle.setText("Unknown Item");
                        }
                    });


                    // Get current user ID
                    int currentUserId = CurrentUser.getInstance().getUserId();

                    // Update button visibility based on donation status and user role
                    if (donation.getDonor_id() == currentUserId) {
                        // Current user is the donor
                        btnClaim.setVisibility(View.GONE);
                        btnRelease.setVisibility(donation.getStatus().equals("Pending") ? View.VISIBLE : View.GONE);
                    } else {
                        // Current user is not the donor
                        btnClaim.setVisibility(donation.getStatus().equals("Available") ? View.VISIBLE : View.GONE);
                        btnRelease.setVisibility(View.GONE);
                    }
                }
            });
        }

        // Set up Claim button
        btnClaim.setOnClickListener(v -> {
            int currentUserId = CurrentUser.getInstance().getUserId();
            donationViewModel.updateDonationReceiver(donationId, currentUserId, "Pending");
            Toast.makeText(requireContext(), "Donation claimed! Waiting for donor approval.", Toast.LENGTH_SHORT).show();
        });

        // Set up Release button
        btnRelease.setOnClickListener(v -> {
            donationViewModel.updateDonationStatus(donationId, "Completed");
            Toast.makeText(requireContext(), "Donation released! Thank you for your contribution.", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
