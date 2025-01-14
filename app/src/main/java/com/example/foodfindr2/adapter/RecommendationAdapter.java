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
    private Context context;

    public RecommendationAdapter(Context context, List<RecommendationItem> recommendationList) {
        this.context = context;
        this.recommendationList = recommendationList;
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

        holder.title.setText(item.getTitle());
        holder.location.setText(item.getLocation());
        holder.status.setText(item.getStatus());
        holder.owner.setText(item.getOwner());
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.imageView.setImageResource(item.getImageResId());
        holder.imageView.setImageResource(R.drawable.star);

        if (item.getStatus().equals("Available")) {
            holder.status.setBackgroundResource(R.drawable.rounded_background_available);
        } else if (item.getStatus().equals("Claimed")) {
            holder.status.setBackgroundResource(R.drawable.rounded_background);
        }


    }

    @Override
    public int getItemCount() {
        return recommendationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, status, owner, rating;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TVItemTitle);
            location = itemView.findViewById(R.id.TVLocation);
            status = itemView.findViewById(R.id.TVStatus);
            owner = itemView.findViewById(R.id.TVOwner);
            rating = itemView.findViewById(R.id.TVRating);
            imageView = itemView.findViewById(R.id.IVApple);
            imageView = itemView.findViewById(R.id.IVStar);
        }
    }
    public void updateList(List<RecommendationItem> updatedList) {
        this.recommendationList = updatedList;
        recommendationList.clear();
        recommendationList.addAll(updatedList);
        notifyDataSetChanged();
    }

}