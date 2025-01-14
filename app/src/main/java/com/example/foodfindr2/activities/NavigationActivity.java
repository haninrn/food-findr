package com.example.foodfindr2.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.foodfindr2.R;
import com.example.foodfindr2.fragments.FeedbackFragment;
import com.example.foodfindr2.fragments.HomeFragment;
import com.example.foodfindr2.fragments.PostDonationFragment;
import com.example.foodfindr2.fragments.ProfileFragment;
import com.example.foodfindr2.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set the default fragment to HomeFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        // Handle BottomNavigationView item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.menu_explore) {
                selectedFragment = new HomeFragment();
//            Toast.makeText(this, "Explore selected", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.menu_profile) {
                selectedFragment = new ProfileFragment();
//                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.menu_add_post) {
                selectedFragment = new PostDonationFragment();
//                Toast.makeText(this, "Add Post selected", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.menu_notifications) {
                selectedFragment = new FeedbackFragment();
//                Toast.makeText(this, "Notifications selected", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.menu_settings) {
                selectedFragment = new SettingsFragment();
//                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            Log.d("MenuID", "menu_explore: " + R.id.menu_explore);
            Log.d("MenuID", "menu_profile: " + R.id.menu_profile);
            Log.d("MenuID", "menu_add_post: " + R.id.menu_add_post);
            Log.d("MenuID", "menu_notifications: " + R.id.menu_notifications);
            Log.d("MenuID", "menu_settings: " + R.id.menu_settings);


//            if (selectedFragment != null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, selectedFragment)
//                        .commit();
//            }
            return true;
        });
    }
}
