package com.example.foodfindr2.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import android.util.Log;

import java.util.*;
import java.io.*;

import com.example.foodfindr2.AppDatabase;
import com.example.foodfindr2.R;
import com.example.foodfindr2.model.User;
import com.example.foodfindr2.dao.UserDao;

import com.example.foodfindr2.model.Item;
import com.example.foodfindr2.dao.ItemDao;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText, emailEditText, phoneEditText;
    private Button registerButton;
    private UserDao userDao;
    private ItemDao itemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        registerButton = findViewById(R.id.register);

        AppDatabase db = AppDatabase.getDatabase(this);
        userDao = db.userDao();
        itemDao = db.itemDao();

        registerButton.setOnClickListener(v -> {
            Toast.makeText(this, "Register button clicked!", Toast.LENGTH_SHORT).show();
            registerUser();
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userDao.getUserByUsername(username) != null) {
            Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);

        userDao.insertUser(user);
        logAllUsers();
//        exportDatabase();
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void logAllUsers() {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            Log.d("RegisterActivity", "User: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
    }

//    private void exportDatabase() {
//        try {
//            File dbFile = getDatabasePath("user_database");
//            File exportFile = new File(getExternalFilesDir(null), "exported_user_database.db");
//
//            FileInputStream fis = new FileInputStream(dbFile);
//            FileOutputStream fos = new FileOutputStream(exportFile);
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = fis.read(buffer)) > 0) {
//                fos.write(buffer, 0, length);
//            }
//
//            fis.close();
//            fos.close();
//
//            Log.d("DatabaseExport", "Database exported to: " + exportFile.getAbsolutePath());
//        } catch (IOException e) {
//            Log.e("DatabaseExport", "Error exporting database: " + e.getMessage(), e);
//        }
//    }
}
