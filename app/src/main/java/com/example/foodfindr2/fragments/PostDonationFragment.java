package com.example.foodfindr2.fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodfindr2.R;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.Item;
import com.example.foodfindr2.utils.CurrentUser;
import com.example.foodfindr2.viewmodel.DonationViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.android.material.textfield.TextInputEditText;
import android.widget.ImageView;
import com.google.android.material.textfield.TextInputLayout;

public class PostDonationFragment extends Fragment {

    private Spinner foodTypeSpinner;

    private Spinner citySpinner;
    private TextInputLayout otherCategoryInputLayout, itemNameInput, quantityInput, addressInput, descriptionInput, pickupInstructionsInput, manufacturingDateInput, expiryDateInput;
    private TextInputEditText itemNameEditText, otherCategoryEditText, quantityEditText, addressEditText, descriptionEditText, pickupInstructionsEditText, manufacturingDateEditText, expiryDateEditText;
    private Button submitButton, uploadImageButton;

    private ImageView imageUploadView;



    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri imageUri;

    private DonationViewModel donationViewModel;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_donation, container, false);


        // Set up city spinner
        // Initialize views
        foodTypeSpinner = view.findViewById(R.id.foodTypeSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        otherCategoryInputLayout = view.findViewById(R.id.otherCategoryInputLayout);
        otherCategoryEditText = view.findViewById(R.id.otherCategoryEditText);
        itemNameInput = view.findViewById(R.id.itemNameInput);
        quantityInput = view.findViewById(R.id.quantityInput);
        addressInput = view.findViewById(R.id.addressInput);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        pickupInstructionsInput = view.findViewById(R.id.pickupInstructionsInput);
        manufacturingDateInput = view.findViewById(R.id.manufacturingDateInput);
        expiryDateInput = view.findViewById(R.id.expiryDateInput);
        submitButton = view.findViewById(R.id.submitButton);

        uploadImageButton = view.findViewById(R.id.uploadImageButton);
        imageUploadView = view.findViewById(R.id.imageUploadView);

        itemNameEditText = (TextInputEditText) itemNameInput.getEditText();
        quantityEditText = (TextInputEditText) quantityInput.getEditText();
        addressEditText = (TextInputEditText) addressInput.getEditText();
        descriptionEditText = (TextInputEditText) descriptionInput.getEditText();
        pickupInstructionsEditText = (TextInputEditText) pickupInstructionsInput.getEditText();
        manufacturingDateEditText = (TextInputEditText) manufacturingDateInput.getEditText();
        expiryDateEditText = (TextInputEditText) expiryDateInput.getEditText();

        donationViewModel = new ViewModelProvider(this).get(DonationViewModel.class);

        setupFoodTypeSpinner();
        setupCitySpinner();
        setupDatePickers();
        setupImagePicker();
        setupSubmitButton();


        return view;
    }

    private void setupFoodTypeSpinner() {
        String[] categories = {"Packaged Food", "Prepared Meals", "Fruits & Vegetables", "Baked Goods", "Baby Food", "Dairy", "Meat", "Beverages", "Frozen Food", "Other"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodTypeSpinner.setAdapter(spinnerAdapter);

        foodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                otherCategoryInputLayout.setVisibility(selectedCategory.equals("Other") ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupCitySpinner() {
        String[] cities = {
                "Kuala Lumpur", "Selangor", "Johor", "Kelantan", "Kedah",
                "Melaka", "Negeri Sembilan", "Perlis", "Putrajaya", "Perak"
        };

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                cities
        );
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        // Optional: Set a default city selection
        citySpinner.setSelection(0);

        // Optional: Add a listener for when the user selects a city
//        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedCity = cities[position];
//                Toast.makeText(requireContext(), "Selected city: " + selectedCity, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Handle case when nothing is selected, if needed
//            }
//        });
        }

    private void setupDatePickers() {
        manufacturingDateEditText.setOnClickListener(v -> showDatePickerDialog(manufacturingDateEditText));
        expiryDateEditText.setOnClickListener(v -> showDatePickerDialog(expiryDateEditText));
    }

    private void showDatePickerDialog(TextInputEditText targetEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    targetEditText.setText(selectedDate);
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    private void setupImagePicker() {
        uploadImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // Allow all file types
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK) {
            if (data != null && data.getData() != null) {
                imageUri = data.getData();
                imageUploadView.setImageURI(imageUri); // Display selected image in ImageView
                // Optional: Log the file name for debugging
                logFileName(imageUri);

            } else {

                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();

            }
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                imageUploadView.setImageURI(imageUri);
            }else {

                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private byte[] getImageBytes(Uri uri) throws IOException {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> {
            String itemName = itemNameEditText.getText().toString().trim();
            String quantity = quantityEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String city = citySpinner.getSelectedItem().toString(); // new city
            String description = descriptionEditText.getText().toString().trim();
            String pickupInstructions = pickupInstructionsEditText.getText().toString().trim();
            String category = foodTypeSpinner.getSelectedItem().toString();
            String manufacturingDateStr = manufacturingDateEditText.getText().toString().trim();
            String expiryDateStr = expiryDateEditText.getText().toString().trim();

            if (category.equals("Other")) {
                category = otherCategoryEditText.getText().toString().trim();
            }

            if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(quantity) || TextUtils.isEmpty(address)) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse manufacturing and expiry dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date manufacturingDate = null;
            Date expiryDate = null;
            try {
                manufacturingDate = dateFormat.parse(manufacturingDateStr);
                expiryDate = dateFormat.parse(expiryDateStr);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            Donation donation = new Donation();
            int currentUserId = CurrentUser.getInstance().getUserId();

            if (currentUserId == -1) {
                Toast.makeText(requireContext(), "Please log in to post a donation.", Toast.LENGTH_SHORT).show();
                return;
            }

            donation.donor_id = currentUserId;
            donation.status = "Available";
            donation.date_posted = new Date();
            donation.description = description;
            donation.pickup_instructions = pickupInstructions;
            donation.address = address;
            donation.city = city;
            donation.rating = 0.0F;

            Item item = new Item();
            item.item_name = itemName;
            item.category = category;
            item.manufacture_date = manufacturingDate;
            item.expiry_date = expiryDate;


            if (imageUri != null) {
                try {
                    donation.image_blob = getImageBytes(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Failed to process image.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(requireContext(), "Please select an image.", Toast.LENGTH_SHORT).show();
                return;
            }

            List<Item> items = new ArrayList<>();
            items.add(item);

            donationViewModel.addDonationWithItems(donation, items);
            Toast.makeText(requireContext(), "Donation added successfully!", Toast.LENGTH_SHORT).show();
        });
    }

    private void logFileName(Uri uri) {
        try {
            Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
                Log.d("File Picker", "Selected file: " + fileName);
            }
        } catch (Exception e) {

            Log.e("File Picker", "Failed to get file name", e);

        }

    }

}
