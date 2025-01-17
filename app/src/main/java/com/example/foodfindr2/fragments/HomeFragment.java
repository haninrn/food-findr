package com.example.foodfindr2.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodfindr2.R;
import com.example.foodfindr2.adapter.RecommendationAdapter;
import com.example.foodfindr2.adapter.RecommendationItem;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;
import com.example.foodfindr2.model.Item;
import com.example.foodfindr2.viewmodel.DonationViewModel;

import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecommendationAdapter adapter;
    private List<RecommendationItem> recommendationList;
    private EditText searchEditText;
    private Button btnType, btnLocation, btnFilter;

    private DonationViewModel donationViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recycler_view);
        searchEditText = view.findViewById(R.id.ETSearch);
        btnType = view.findViewById(R.id.BtnType);
        btnLocation = view.findViewById(R.id.BtnLocation);
        btnFilter = view.findViewById(R.id.BtnFilter);

        // Set up RecyclerView
        recommendationList = new ArrayList<>();
        adapter = new RecommendationAdapter(getContext(), recommendationList, item -> {
            Bundle bundle = new Bundle();
            bundle.putInt("donation_id", item.getDonationId()); // Pass the ID or any relevant data

            // Create a new instance of ClaimDonationFragment and set arguments
            ClaimDonationFragment claimDonationFragment = new ClaimDonationFragment();
            claimDonationFragment.setArguments(bundle);

            // Perform the fragment transaction
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, claimDonationFragment) // Replace with your container ID
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Attach LayoutManager
        recyclerView.setAdapter(adapter); // Attach Adapter

        // Initialize ViewModel
        donationViewModel = new ViewModelProvider(this).get(DonationViewModel.class);

        // Observe LiveData from ViewModel
        donationViewModel.getAvailableDonations().observe(getViewLifecycleOwner(), this::populateRecommendationList);

        // Set up button click listeners
        btnType.setOnClickListener(v -> showFilterDialog("Type"));
        btnLocation.setOnClickListener(v -> showFilterDialog("Location"));
        btnFilter.setOnClickListener(v -> showAdvancedFilter());

        // Search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRecyclerView(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    // Populate RecyclerView with donations
    private void populateRecommendationList(List<DonationWithItems> donationWithItemsList) {
        List<RecommendationItem> recommendations = new ArrayList<>();
        for (DonationWithItems donationWithItems : donationWithItemsList) {
            Donation donation = donationWithItems.donation;
            Log.d("HomeFragment", "Donation ID: " + donation.donation_id);
            Log.d("HomeFragment", "Description: " + donation.description);
            Log.d("HomeFragment", "Status: " + donation.status);

            // Fetch donor name dynamically
            donationViewModel.getUsernameById(donation.donor_id).observe(getViewLifecycleOwner(), username -> {
                for (Item item : donationWithItems.items) {
                    Log.d("HomeFragment", "Item Name: " + item.item_name);
                    Log.d("HomeFragment", "Category: " + item.category);
                    Log.d("HomeFragment", "Image blob length: " + (donation.image_blob != null ? donation.image_blob.length : "null"));

                    recommendations.add(new RecommendationItem(
                            item.item_name,
                            donation.city,
                            donation.status,
                            username != null ? username : "hani", // Replace with fetched username
                            5.0, // Replace with actual rating if applicable
                            donation.image_blob, // Replace with actual image
                            donation.getDonation_id()
                    ));
                }

                // Update adapter list once recommendations are updated
                adapter.updateList(recommendations);
            });
        }
    }


    public void onRecommendationClick(int donationId) {
        // Navigate to the DonationDetailFragment
        Bundle bundle = new Bundle();
        bundle.putInt("donation_id", donationId);
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_donationDetailFragment, bundle);
    }


    // Filter RecyclerView by search query
    private void filterRecyclerView(String query) {
        List<RecommendationItem> filteredList = new ArrayList<>();
        for (RecommendationItem item : recommendationList) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

    // Show filter dialog
    private void showFilterDialog(String filterType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select " + filterType);

        String[] options;
        if (filterType.equals("Type")) {
            options = new String[]{"Fruits", "Vegetables", "Dairy"};
        } else {
            options = new String[]{"Kuala Lumpur", "Selangor", "Johor"};
        }

        builder.setItems(options, (dialog, which) -> {
            Toast.makeText(requireContext(), filterType + ": " + options[which], Toast.LENGTH_SHORT).show();
        });

        builder.create().show();
    }

    // Placeholder for advanced filter
    private void showAdvancedFilter() {
        Toast.makeText(getContext(), "Advanced filter clicked", Toast.LENGTH_SHORT).show();
    }
}
