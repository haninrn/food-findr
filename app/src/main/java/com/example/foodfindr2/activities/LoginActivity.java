package com.example.foodfindr2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodfindr2.AppDatabase;
import com.example.foodfindr2.R;
import com.example.foodfindr2.dao.UserDao;
import com.example.foodfindr2.model.User;
import com.example.foodfindr2.utils.CurrentUser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.login_button);

        AppDatabase db = AppDatabase.getDatabase(this);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    User user = db.userDao().login(email, password);
                    runOnUiThread(() -> {
                        if (user != null) {
                            Toast.makeText(this, "User found! Welcome, " + user.getUsername(), Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(this, HomeActivity.class);
//                            startActivity(intent);
                            Intent intent = new Intent(this, NavigationActivity.class);
                            startActivity(intent);

                            CurrentUser.getInstance().setUserId(user.getId()); // Replace `1` with the actual user ID
                            CurrentUser.getInstance().setUsername(user.getUsername()); // Replace with the actual username

                            finish();

                            // Navigate to another activity if needed
                        } else {
                            Toast.makeText(this, "User not found. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        });


//        CurrentUser.getInstance().setUserId(-1); // Reset to default
//        CurrentUser.getInstance().setUsername(null);


    }
}

