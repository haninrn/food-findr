package com.example.foodfindr2.adapter;

import android.util.Log;
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
import com.example.foodfindr2.viewmodel.DonationViewModel;

public class DonationAdapter extends ListAdapter<DonationWithItems, DonationAdapter.DonationViewHolder> {

    private final OnClaimButtonClickListener claimButtonClickListener;
    private DonationViewModel donationViewModel;

    // Constructor accepts a callback for claim button clicks
    public DonationAdapter(OnClaimButtonClickListener claimButtonClickListener, DonationViewModel donationViewModel) {
        super(DIFF_CALLBACK);
        this.claimButtonClickListener = claimButtonClickListener;
        this.donationViewModel = donationViewModel;
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

        Log.d("DonationAdapter", "Binding position: " + position);
        Log.d("DonationAdapter", "Donation: " + donationWithItems.donation.description);
        Log.d("DonationAdapter", "Items size: " + (donationWithItems.items != null ? donationWithItems.items.size() : 0));

        // Check if items list is not null or empty before accessing it
        if (donationWithItems.donation != null) {
            // Set item name from the first item, or use a fallback
            holder.itemName.setText(donationWithItems.donation.description != null
                    ? donationWithItems.donation.description
                    : "No description available");
            if (donationWithItems.donation.image_blob != null) {
                // Load image_blob from the donations table
                byte[] imageBlob = donationWithItems.donation.image_blob;
                Glide.with(holder.itemImage.getContext())
                        .asBitmap()
                        .load(imageBlob)
                        .into(holder.itemImage); // Load the donation's image
            } else {
                Log.d("DonationAdapter", "Image blob is null for donation ID: " + donationWithItems.donation.donation_id);
                holder.itemImage.setImageResource(R.drawable.bronze_medal); // Placeholder image
            }
        } else {
            holder.itemName.setText("No donation available"); // Fallback for empty donation
            holder.itemImage.setImageResource(R.drawable.bronze_medal); // Placeholder image
        }

        // Set donor name and status
        // Set placeholder while fetching username
        holder.donorName.setText("Loading donor name...");

        // Fetch and display the username
        donationViewModel.getUserName(donation.donor_id).observeForever(username -> {
            holder.donorName.setText(username != null ? username : "Unknown Donor");
        });


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
