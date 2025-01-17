package com.example.foodfindr2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfindr2.R;
import com.example.foodfindr2.model.NotificationWithUser;
import com.example.foodfindr2.model.Notifications;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodfindr2.model.Notifications;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.example.foodfindr2.R;
import com.example.foodfindr2.model.NotificationWithUser;

public class NotificationAdapter extends ListAdapter<NotificationWithUser, NotificationAdapter.NotificationViewHolder> {

    private final Context context;

    public NotificationAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationWithUser notification = getItem(position);

        holder.tvTitle.setText(notification.getTitle());
        holder.tvMessage.setText(notification.getMessage());

        if (notification.getUserName() != null) {
            holder.tvMessage.append(" by " + notification.getUserName());
        }
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMessage;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }

    private static final DiffUtil.ItemCallback<NotificationWithUser> DIFF_CALLBACK = new DiffUtil.ItemCallback<NotificationWithUser>() {
        @Override
        public boolean areItemsTheSame(@NonNull NotificationWithUser oldItem, @NonNull NotificationWithUser newItem) {
            return oldItem.getNotificationId() == newItem.getNotificationId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotificationWithUser oldItem, @NonNull NotificationWithUser newItem) {
            return oldItem.equals(newItem);
        }
    };
}
