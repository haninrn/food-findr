package com.example.foodfindr2.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodfindr2.R;
import com.example.foodfindr2.adapter.DonationAdapter;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;
import com.example.foodfindr2.utils.CurrentUser;
import com.example.foodfindr2.viewmodel.DonationViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostListFragment extends Fragment {

    private static final String ARG_LIST_TYPE = "list_type";

    public static final int LIST_TYPE_MY_POSTS = 0;
    public static final int LIST_TYPE_PENDING_REQUESTS = 1;
    public static final int LIST_TYPE_CLAIMED = 2;

    private int listType;
    private DonationViewModel donationViewModel;
    private DonationAdapter adapter;

    public static PostListFragment newInstance(int listType) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LIST_TYPE, listType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listType = getArguments().getInt(ARG_LIST_TYPE, LIST_TYPE_MY_POSTS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new DonationAdapter(donation -> {
            // Handle claim button click logic here
            Toast.makeText(requireContext(), "Claimed donation: " + donation.getDescription(), Toast.LENGTH_SHORT).show();

            // Example: Notify ViewModel to update the donation's status
            donationViewModel.updateDonationStatus(donation.getDonation_id(), "Claimed");
        });

        recyclerView.setAdapter(adapter);

        donationViewModel = new ViewModelProvider(requireActivity()).get(DonationViewModel.class);

        // Load the appropriate list based on listType
        loadListData();
    }

    private void loadListData() {
        switch (listType) {
            case LIST_TYPE_MY_POSTS:
                donationViewModel.getMyPosts().observe(getViewLifecycleOwner(), donations -> {
                    List<DonationWithItems> donationWithItemsList = transformToDonationWithItems(donations);
                    adapter.submitList(donationWithItemsList); // Submit transformed list
                });
                break;

            case LIST_TYPE_PENDING_REQUESTS:
                donationViewModel.getPendingRequests().observe(getViewLifecycleOwner(), donations -> {
                    List<DonationWithItems> donationWithItemsList = transformToDonationWithItems(donations);
                    adapter.submitList(donationWithItemsList); // Submit transformed list
                });
                break;

            case LIST_TYPE_CLAIMED:
                donationViewModel.getClaimedDonations().observe(getViewLifecycleOwner(), donations -> {
                    List<DonationWithItems> donationWithItemsList = transformToDonationWithItems(donations);
                    adapter.submitList(donationWithItemsList); // Submit transformed list
                });
                break;
        }
    }

    private List<DonationWithItems> transformToDonationWithItems(List<Donation> donations) {
        List<DonationWithItems> donationWithItemsList = new ArrayList<>();
        for (Donation donation : donations) {
            DonationWithItems donationWithItems = new DonationWithItems();
            donationWithItems.donation = donation;
            donationWithItems.items = new ArrayList<>(); // Add placeholder empty list or fetch actual items if needed
            donationWithItemsList.add(donationWithItems);
        }
        return donationWithItemsList;
    }



}
