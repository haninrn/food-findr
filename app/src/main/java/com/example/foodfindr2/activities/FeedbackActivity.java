package com.example.foodfindr2.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodfindr2.R;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText etFeedback;
    private RadioButton rbYes, rbNo;
    private Button btnSendFeedback, btnCancelFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize views
        ratingBar = findViewById(R.id.RBStar);
        etFeedback = findViewById(R.id.ETFeedback);
        btnSendFeedback = findViewById(R.id.BtnSendFeedback);
        btnCancelFeedback = findViewById(R.id.BtnCancelFeedback);

        // Set up RatingBar listener
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            // Display rating as a toast
            Toast.makeText(this, "You rated: " + rating + " stars", Toast.LENGTH_SHORT).show();
        });

        // Set up Send button listener
        btnSendFeedback.setOnClickListener(v -> {
            String feedbackText = etFeedback.getText().toString().trim();
            boolean followUp = rbYes.isChecked();

            if (feedbackText.isEmpty()) {
                Toast.makeText(this, "Please enter your feedback.", Toast.LENGTH_SHORT).show();
            } else {
                // Simulate sending feedback
                Toast.makeText(this, "Feedback sent!\nFollow-up: " + (followUp ? "Yes" : "No"), Toast.LENGTH_SHORT).show();
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

            Toast.makeText(this, "Feedback cancelled.", Toast.LENGTH_SHORT).show();
        });
    }
}
