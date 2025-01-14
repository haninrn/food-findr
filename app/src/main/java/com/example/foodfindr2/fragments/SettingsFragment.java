package com.example.foodfindr2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodfindr2.R;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Handle Notifications Toggle
        Switch notificationsSwitch = view.findViewById(R.id.switch_notifications);
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getActivity(), "Notifications Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Notifications Disabled", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Log Out
        view.findViewById(R.id.tv_log_out).setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
            // Add actual log-out logic here
        });

        // Handle Edit Profile (Placeholder)
        view.findViewById(R.id.tv_edit_profile).setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle Change Password (Placeholder)
        view.findViewById(R.id.tv_change_password).setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Change Password Clicked", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
