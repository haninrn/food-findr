package com.example.foodfindr2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodfindr2.AppDatabase;
import com.example.foodfindr2.R;
import com.example.foodfindr2.dao.UserDao;
import com.example.foodfindr2.fragments.SettingsFragment;
import com.example.foodfindr2.model.User;
import com.example.foodfindr2.utils.CurrentUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;
    private UserDao userDao;
    private int currentUserId; // Assume this is passed when the user logs in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        // Initialize UI elements
        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnChangePassword = findViewById(R.id.btn_change_password);

        // Initialize the UserDao
        AppDatabase db = AppDatabase.getDatabase(this);
        userDao = db.userDao();

        // Assume currentUserId is set when the user logs in
        currentUserId = CurrentUser.getInstance().getUserId(); // Replace with your logic

        btnChangePassword.setOnClickListener(v -> handleChangePassword());
    }

    private void handleChangePassword() {
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        User user = userDao.getUserById(currentUserId);

        if (user == null) {
            runOnUiThread(() -> Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show());
            return;
        }

        if (!user.getPassword().equals(oldPassword)) {
            runOnUiThread(() -> Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show());
            return;
        }

        if (newPassword.isEmpty() || confirmPassword.isEmpty() || oldPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(oldPassword.equals(newPassword)){
            Toast.makeText(this, "New password cannot be the same as the old password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch the user from the database
        new Thread(() -> {

            if (user == null) {
                runOnUiThread(() -> Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show());
                return;
            }

            if (!user.getPassword().equals(oldPassword)) {
                runOnUiThread(() -> Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show());
                return;
            }

            // Update the password
            userDao.updatePassword(currentUserId, newPassword);

            runOnUiThread(() -> {
                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}

