package com.example.foodfindr2.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodfindr2.R;
import com.example.foodfindr2.utils.CurrentUser;
import com.example.foodfindr2.viewmodel.DonationViewModel;

public class ClaimDonationFragment extends Fragment {

    private TextView tvTitle, tvStatus, tvDonor, tvLocation, tvDescription, tvPickupInstructions;
    private ImageView mainImageView;
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
        tvPickupInstructions = view.findViewById(R.id.pickupInstructions); // New field for pickup instructions
        mainImageView = view.findViewById(R.id.mainImageView);
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
                    tvPickupInstructions.setText(donation.getPickup_instructions() != null
                            ? donation.getPickup_instructions()
                            : "No instructions provided."); // Display pickup instructions

                    // Fetch and display donor name
                    donationViewModel.getDonorName(donation.getDonor_id()).observe(getViewLifecycleOwner(), donorName -> {
                        if (donorName != null) {
                            tvDonor.setText("Donor: " + donorName);
                        } else {
                            tvDonor.setText("Donor: Unknown");
                        }
                    });

                    // Fetch and display item name
                    donationViewModel.getItemNameByDonationId(donationId).observe(getViewLifecycleOwner(), itemName -> {
                        if (itemName != null) {
                            tvTitle.setText(itemName);
                        } else {
                            tvTitle.setText("Unknown Item");
                        }
                    });

                    // Display donation image
                    if (donation.image_blob != null) {
                        Glide.with(requireContext())
                                .asBitmap()
                                .load(donation.image_blob) // Load image from byte[]
                                .into(mainImageView);
                    } else {
                        mainImageView.setImageResource(R.drawable.apples); // Placeholder if no image
                    }

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
            // Update the donation status to "Claimed"
            donationViewModel.updateDonationStatus(donationId, "Claimed");
            Toast.makeText(requireContext(), "Donation released! Thank you for your contribution.", Toast.LENGTH_SHORT).show();

            // Fetch the current user's total given count
            int currentUserId = CurrentUser.getInstance().getUserId();
            donationViewModel.getTotalGivenCount(currentUserId).observe(getViewLifecycleOwner(), totalGiven -> {
                if (totalGiven == 1 || totalGiven == 2 || totalGiven == 3) {
                    // Trigger the achievement popup if a threshold is reached
                    showAchievementPopup(totalGiven);
                }
            });
        });

        return view;
    }

    // Achievement popup logic
    private void showAchievementPopup(int totalGiven) {
        Dialog dialog = new Dialog(requireContext());


        TextView title = dialog.findViewById(R.id.tvTitle);
        ImageView badgeIcon = dialog.findViewById(R.id.ivGold); // Adjust dynamically based on the badge


        // Customize the popup based on the threshold
        if (totalGiven == 3) {
            dialog.setContentView(R.layout.popup_achievement_gold);
        } else if (totalGiven == 2) {
            dialog.setContentView(R.layout.popup_achievement);
        } else if (totalGiven == 1) {
            dialog.setContentView(R.layout.popup_achievement_bronze);
        }

        // Display the dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        // Auto-dismiss after 6 seconds
        new Handler(Looper.getMainLooper()).postDelayed(dialog::dismiss, 10000);
    }
}
