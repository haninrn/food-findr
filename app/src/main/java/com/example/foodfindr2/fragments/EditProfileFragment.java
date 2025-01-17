//package com.example.foodfindr2.fragments;
//
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import com.example.foodfindr2.R;
//import com.example.foodfindr2.viewmodel.ProfileViewModel;
//import com.example.foodfindr2.model.UserProfile;
//import java.util.ArrayList;
//
//public class EditProfileFragment extends Fragment {
//
//    private ImageView profileImageView;
//    private EditText nameEditText, phoneEditText, emailEditText;
//    private Spinner citySpinner;
//    private Button saveButton;
//    private ProfileViewModel profileViewModel;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
//
//        // Initialize UI components
//        profileImageView = view.findViewById(R.id.profileImageView);
//        nameEditText = view.findViewById(R.id.editTextName);
//        phoneEditText = view.findViewById(R.id.editTextPhone);
//        emailEditText = view.findViewById(R.id.editTextEmail);
//        citySpinner = view.findViewById(R.id.citySpinner);
//        saveButton = view.findViewById(R.id.saveButton);
//
//        // Initialize ViewModel
//        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
//
//        // Set up the city spinner
//        setupCitySpinner();
//
//        // Load user profile data
//        loadUserProfile();
//
//        // Save button click listener
//        saveButton.setOnClickListener(v -> saveProfileData());
//
//        return view;
//    }
//
//    private void setupCitySpinner() {
//        String[] cities = {
//                "Kuala Lumpur", "Selangor", "Johor", "Kelantan", "Kedah",
//                "Melaka", "Negeri Sembilan", "Perlis", "Putrajaya", "Perak"
//        };
//
//        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
//                requireContext(),
//                android.R.layout.simple_spinner_item,
//                cities
//        );
//        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        citySpinner.setAdapter(cityAdapter);
//    }
//
//    private void loadUserProfile() {
//        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), userProfile -> {
//            if (userProfile != null) {
//                nameEditText.setText(userProfile.getName());
//                phoneEditText.setText(userProfile.getPhone());
//                emailEditText.setText(userProfile.getEmail());
//
//                // Set the spinner to the user's current city
//                String userCity = userProfile.getCity();
//                if (userCity != null) {
//                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) citySpinner.getAdapter();
//                    int position = adapter.getPosition(userCity);
//                    citySpinner.setSelection(position);
//                }
//            }
//        });
//    }
//
//    private void saveProfileData() {
//        // Validate inputs
//        String name = nameEditText.getText().toString().trim();
//        String phone = phoneEditText.getText().toString().trim();
//        String email = emailEditText.getText().toString().trim();
//        String city = citySpinner.getSelectedItem().toString();
//
//        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
//            Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Create a UserProfile object
//        UserProfile updatedProfile = new UserProfile();
//        updatedProfile.setName(name);
//        updatedProfile.setPhone(phone);
//        updatedProfile.setEmail(email);
//        updatedProfile.setCity(city);
//
//        // Update the profile in the database
//        profileViewModel.updateUserProfile(updatedProfile);
//
//        Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
//    }
//}
