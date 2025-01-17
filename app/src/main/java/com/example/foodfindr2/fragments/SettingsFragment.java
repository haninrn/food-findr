package com.example.foodfindr2.fragments;

import android.content.Intent;
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
import com.example.foodfindr2.activities.ChangePasswordActivity;
import com.example.foodfindr2.activities.LoginActivity;
import com.example.foodfindr2.activities.MainActivity;
import com.example.foodfindr2.utils.CurrentUser;

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
            // Manually reset CurrentUser data
//            CurrentUser.getInstance().setUserId(null);
//            CurrentUser.getInstance().setUsername(null);

            // Redirect to LoginActivity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            // Show a confirmation message
            Toast.makeText(getActivity(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        });


        // Handle Edit Profile (Placeholder)
        view.findViewById(R.id.tv_edit_profile).setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle Change Password (Placeholder)
        view.findViewById(R.id.tv_change_password).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(getActivity(), "Change Password Clicked", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
