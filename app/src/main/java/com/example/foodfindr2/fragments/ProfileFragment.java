package com.example.foodfindr2.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodfindr2.R;
import com.example.foodfindr2.adapter.ProfilePagerAdapter;
import com.example.foodfindr2.utils.CurrentUser;
import com.example.foodfindr2.viewmodel.DonationViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {

    private DonationViewModel donationViewModel;

    // UI elements for profile data
    private TextView profileName, profileRating, profileTotalDonated, profileTaken, profileGiven;
    private ImageView profileBadge;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        donationViewModel = new ViewModelProvider(requireActivity()).get(DonationViewModel.class);

        // Initialize UI elements
        profileName = view.findViewById(R.id.profileName);
        profileRating = view.findViewById(R.id.profileRating);
        profileTotalDonated = view.findViewById(R.id.profileTotalDonated);
        profileTaken = view.findViewById(R.id.profileTaken);
        profileGiven = view.findViewById(R.id.profileGiven);
        profileBadge = view.findViewById(R.id.profileBadge);

        // Populate profile data
        populateProfileData();

        // Set up TabLayout and ViewPager2
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        ProfilePagerAdapter adapter = new ProfilePagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("My Posts");
                    break;
                case 1:
                    tab.setText("Pending Request");
                    break;
                case 2:
                    tab.setText("Claimed");
                    break;
            }
        }).attach();
    }

    private void populateProfileData() {
        int currentUserId = CurrentUser.getInstance().getUserId();
        Log.d("ProfileFragment", "Current User ID: " + currentUserId);

        if (donationViewModel == null) {
            Log.e("ProfileFragment", "DonationViewModel is null!");
            return;
        }

        donationViewModel.getUserName(currentUserId).observe(getViewLifecycleOwner(), name -> {
            Log.d("ProfileFragment", "Fetched username: " + name);
            profileName.setText(name != null ? name : "Unknown User");
        });

        donationViewModel.getTotalDonatedCount(currentUserId).observe(getViewLifecycleOwner(), totalDonated -> {
            Log.d("ProfileFragment", "Total Donated Count: " + totalDonated);
            profileTotalDonated.setText("Total Donated: " + totalDonated);
        });

        donationViewModel.getTotalTakenCount(currentUserId).observe(getViewLifecycleOwner(), totalTaken -> {
            Log.d("ProfileFragment", "Total Taken Count: " + totalTaken);
            profileTaken.setText(String.valueOf(totalTaken));
        });

        donationViewModel.getTotalGivenCount(currentUserId).observe(getViewLifecycleOwner(), totalGiven -> {
            Log.d("ProfileFragment", "Total Given Count: " + totalGiven);
            profileGiven.setText(String.valueOf(totalGiven));
            // Set badge based on total donated items
            if (totalGiven >= 3) {
                profileBadge.setImageResource(R.drawable.gold_medal);
                profileBadge.setVisibility(View.VISIBLE);
            } else if (totalGiven >= 2) {
                profileBadge.setImageResource(R.drawable.silver_medal);
                profileBadge.setVisibility(View.VISIBLE);
            } else if (totalGiven >= 1) {
                profileBadge.setImageResource(R.drawable.bronze_medal);
                profileBadge.setVisibility(View.VISIBLE);
            } else {
                profileBadge.setVisibility(View.GONE);
            }
        });

        donationViewModel.getAverageRatingForUser(currentUserId).observe(getViewLifecycleOwner(), rating -> {
            Log.d("ProfileFragment", "Average Rating: " + rating);
            profileRating.setText(rating != null ? "⭐ " + String.format("%.1f", rating) : "⭐ No Ratings Yet");
        });
    }
}
