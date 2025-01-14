package com.example.foodfindr2.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodfindr2.R;
import com.example.foodfindr2.adapter.ProfilePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {

    // UI elements for profile data
    private TextView profileName, profileRating, profileTotalDonated, profileTaken, profileGiven;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        profileName = view.findViewById(R.id.profileName);
        profileRating = view.findViewById(R.id.profileRating);
        profileTotalDonated = view.findViewById(R.id.profileTotalDonated);
        profileTaken = view.findViewById(R.id.profileTaken);
        profileGiven = view.findViewById(R.id.profileGiven);

        // Populate profile data (mock data for now)
        populateProfileData();

        // Set up TabLayout and ViewPager2 for filtered lists
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        // Create and set the adapter
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        // Link TabLayout with ViewPager2 using TabLayoutMediator
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
        // TODO: Replace mock data with actual data fetched from the ViewModel
        profileName.setText("Emily");  // Replace with actual user's name
        profileRating.setText("⭐ 5.0");  // Replace with actual user's rating
        profileTotalDonated.setText("Total Donated: 0");  // Replace with actual donated count
        profileTaken.setText("0");  // Replace with actual number of items taken
        profileGiven.setText("0");  // Replace with actual number of items given
    }
}
