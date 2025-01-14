package com.example.foodfindr2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodfindr2.R;

public class FeedbackFragment extends Fragment {

    private RatingBar ratingBar;
    private EditText etFeedback;
    private RadioButton rbYes, rbNo;
    private Button btnSendFeedback, btnCancelFeedback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        // Initialize views
        ratingBar = view.findViewById(R.id.RBStar);
        etFeedback = view.findViewById(R.id.ETFeedback);
        rbYes = view.findViewById(R.id.RBYes);
        rbNo = view.findViewById(R.id.RBNo);
        btnSendFeedback = view.findViewById(R.id.BtnSendFeedback);
        btnCancelFeedback = view.findViewById(R.id.BtnCancelFeedback);

        // Set up RatingBar listener
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            // Display rating as a toast
            Toast.makeText(getContext(), "You rated: " + rating + " stars", Toast.LENGTH_SHORT).show();
        });

        // Set up Send button listener
        btnSendFeedback.setOnClickListener(v -> {
            String feedbackText = etFeedback.getText().toString().trim();
            boolean followUp = rbYes.isChecked();

            if (feedbackText.isEmpty()) {
                Toast.makeText(getContext(), "Please enter your feedback.", Toast.LENGTH_SHORT).show();
            } else {
                // Simulate sending feedback
                Toast.makeText(getContext(), "Feedback sent!\nFollow-up: " + (followUp ? "Yes" : "No"), Toast.LENGTH_SHORT).show();
                // Clear input fields
                etFeedback.setText("");
                ratingBar.setRating(0);
                rbYes.setChecked(true);
            }
        });

        // Set up Cancel button listener
        btnCancelFeedback.setOnClickListener(v -> {
            // Clear input fields
            etFeedback.setText("");
            ratingBar.setRating(0);
            rbYes.setChecked(true);
            rbNo.setChecked(false);

            Toast.makeText(getContext(), "Feedback cancelled.", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}