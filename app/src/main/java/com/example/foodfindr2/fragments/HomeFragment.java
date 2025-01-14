package com.example.foodfindr2.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodfindr2.R;
import com.example.foodfindr2.adapter.RecommendationAdapter;
import com.example.foodfindr2.adapter.RecommendationItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecommendationAdapter adapter;
    private List<RecommendationItem> recommendationList;
    private EditText searchEditText;
    private Button btnType, btnLocation, btnFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recycler_view);
        searchEditText = view.findViewById(R.id.ETSearch);
        btnType = view.findViewById(R.id.BtnType);
        btnLocation = view.findViewById(R.id.BtnLocation);
        btnFilter = view.findViewById(R.id.BtnFilter);

        // Set up button click listeners
        btnType.setOnClickListener(v -> showFilterDialog("Type"));
        btnLocation.setOnClickListener(v -> showFilterDialog("Location"));
        btnFilter.setOnClickListener(v -> showAdvancedFilter());

        // Initialize recommendation list
        recommendationList = new ArrayList<>();
        populateRecommendationList();

        // Set up RecyclerView
        adapter = new RecommendationAdapter(getContext(), recommendationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRecyclerView(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    // Populate recommendation list with sample data
    private void populateRecommendationList() {
        recommendationList.add(new RecommendationItem("Pack of 6 apples", "Kuala Lumpur", "Available", "Joney", 5.0, R.drawable.apples));
        recommendationList.add(new RecommendationItem("Pack of 6 apples", "Selangor", "Available", "Adam", 5.0, R.drawable.apples));
        recommendationList.add(new RecommendationItem("Pack of 6 apples", "Kuala Lumpur", "Claimed", "Yana", 4.9, R.drawable.apples));
        recommendationList.add(new RecommendationItem("Pack of 12 oranges", "Kuala Lumpur", "Available", "Sarah", 4.8, R.drawable.apples));
    }

    // Filter RecyclerView by search query
    private void filterRecyclerView(String query) {
        List<RecommendationItem> filteredList = new ArrayList<>();
        for (RecommendationItem item : recommendationList) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

    // Show filter dialog
    private void showFilterDialog(String filterType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select " + filterType);

        String[] options;
        if (filterType.equals("Type")) {
            options = new String[]{"Fruits", "Vegetables", "Dairy"};
        } else {
            options = new String[]{"Kuala Lumpur", "Selangor", "Johor"};
        }

        builder.setItems(options, (dialog, which) -> {
            Toast.makeText(requireContext(), filterType + ": " + options[which], Toast.LENGTH_SHORT).show();
        });

        builder.create().show();
    }

    // Placeholder for advanced filter
    private void showAdvancedFilter() {
        Toast.makeText(getContext(), "Advanced filter clicked", Toast.LENGTH_SHORT).show();
    }
}
