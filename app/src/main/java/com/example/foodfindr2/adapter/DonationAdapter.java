package com.example.foodfindr2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfindr2.R;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.DonationWithItems;

public class DonationAdapter extends ListAdapter<DonationWithItems, DonationAdapter.DonationViewHolder> {

    private final OnClaimButtonClickListener claimButtonClickListener;

    // Constructor accepts a callback for claim button clicks
    public DonationAdapter(OnClaimButtonClickListener claimButtonClickListener) {
        super(DIFF_CALLBACK);
        this.claimButtonClickListener = claimButtonClickListener;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donation, parent, false);
        return new DonationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        DonationWithItems donationWithItems = getItem(position);
        Donation donation = donationWithItems.donation;

        // Check if items list is not null or empty before accessing it
        if (donationWithItems.items != null && !donationWithItems.items.isEmpty()) {
            holder.itemName.setText(donationWithItems.items.get(0).item_name); // Display first item's name
            if (donationWithItems.items.get(0).image_blob != null) {
                byte[] imageBlob = donationWithItems.items.get(0).image_blob;
                Glide.with(holder.itemImage.getContext())
                        .asBitmap()
                        .load(imageBlob)
                        .into(holder.itemImage); // Load the item's image
            } else {
                holder.itemImage.setImageResource(R.drawable.bronze_medal); // Placeholder image
            }
        } else {
            holder.itemName.setText("No items available"); // Fallback for empty list
            holder.itemImage.setImageResource(R.drawable.bronze_medal); // Placeholder image
        }

        // Set donor name and status
        holder.donorName.setText("Donor ID: " + donation.donor_id); // Replace with actual donor name if available
        holder.status.setText(donation.status);

        // Handle claim button click
        holder.claimButton.setOnClickListener(v -> {
            if (claimButtonClickListener != null) {
                claimButtonClickListener.onClaimClicked(donation);
            }
        });
    }

    // DiffUtil for efficient list updates
    public static final DiffUtil.ItemCallback<DonationWithItems> DIFF_CALLBACK = new DiffUtil.ItemCallback<DonationWithItems>() {
        @Override
        public boolean areItemsTheSame(@NonNull DonationWithItems oldItem, @NonNull DonationWithItems newItem) {
            return oldItem.donation.donation_id == newItem.donation.donation_id; // Compare by unique ID
        }

        @Override
        public boolean areContentsTheSame(@NonNull DonationWithItems oldItem, @NonNull DonationWithItems newItem) {
            return oldItem.equals(newItem); // Compare entire content for changes
        }
    };

    // ViewHolder for donation items
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

    // Callback interface for claim button clicks
    public interface OnClaimButtonClickListener {
        void onClaimClicked(Donation donation);
    }
}
