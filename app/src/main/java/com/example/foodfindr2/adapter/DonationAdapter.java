package com.example.foodfindr2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfindr2.R;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;

import java.util.ArrayList;
import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private List<DonationWithItems> donationsWithItems = new ArrayList<>();

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donation, parent, false);
        return new DonationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        DonationWithItems donationWithItems = donationsWithItems.get(position);
        Donation donation = donationWithItems.donation;

        // Bind data
        holder.itemName.setText(donationWithItems.items.get(0).item_name); // Display first item name
        holder.donorName.setText("Donor ID: " + donation.donor_id); // Replace with actual donor name if available
        holder.status.setText(donation.status);

        // Display image (optional)
        if (!donationWithItems.items.isEmpty() && donationWithItems.items.get(0).image_blob != null) {
            byte[] imageBlob = donationWithItems.items.get(0).image_blob;
            Glide.with(holder.itemImage.getContext()) // Using Glide for image loading
                    .asBitmap()
                    .load(imageBlob)
                    .into(holder.itemImage);
        }

        // "Claim" button functionality
        holder.claimButton.setOnClickListener(v -> {
            // Handle claim logic (e.g., mark as claimed, update database)
        });
    }

    @Override
    public int getItemCount() {
        return donationsWithItems.size();
    }

    public void setDonations(List<DonationWithItems> donationsWithItems) {
        this.donationsWithItems = donationsWithItems;
        notifyDataSetChanged();
    }

    static class DonationViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, donorName, status;
        private final Button claimButton;
        private final ImageView itemImage;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            donorName = itemView.findViewById(R.id.donor_name);
            status = itemView.findViewById(R.id.item_status);
            claimButton = itemView.findViewById(R.id.claim_button);
            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}

