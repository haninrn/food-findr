package com.example.foodfindr2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodfindr2.R;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<RecommendationItem> recommendationList;
    private final Context context;
    private final OnItemClickListener listener;

    // Define an interface for item click handling
    public interface OnItemClickListener {
        void onRecommendationClick(RecommendationItem item); // Pass the object, not int
    }


    public RecommendationAdapter(Context context, List<RecommendationItem> recommendationList, OnItemClickListener listener) {
        this.context = context;
        this.recommendationList = recommendationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendationItem item = recommendationList.get(position);

        // Bind data to views
        holder.title.setText(item.getTitle());
        holder.location.setText(item.getLocation());
        holder.status.setText(item.getStatus());
        holder.owner.setText(item.getOwner());
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.appleImageView.setImageResource(item.getImageResId()); // Apple image

        // Set status background based on availability
        if (item.getStatus().equalsIgnoreCase("Available")) {
            holder.status.setBackgroundResource(R.drawable.rounded_background_available);
        } else if (item.getStatus().equalsIgnoreCase("Claimed")) {
            holder.status.setBackgroundResource(R.drawable.rounded_background);
        }

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecommendationClick(item); // Pass the item, not an ID
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, status, owner, rating;
        ImageView appleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TVItemTitle);
            location = itemView.findViewById(R.id.TVLocation);
            status = itemView.findViewById(R.id.TVStatus);
            owner = itemView.findViewById(R.id.TVOwner);
            rating = itemView.findViewById(R.id.TVRating);
            appleImageView = itemView.findViewById(R.id.IVApple); // Apple image
        }
    }

    // Update RecyclerView items
    public void updateList(List<RecommendationItem> updatedList) {
        recommendationList.clear(); // Clear existing items
        recommendationList.addAll(updatedList); // Add new items
        notifyDataSetChanged(); // Notify adapter about data changes
    }
}
