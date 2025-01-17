package com.example.foodfindr2.utils;

public class CurrentUser {
    private static CurrentUser instance; // Singleton instance
    private int userId; // Current user's ID
    private String username; // Add more fields as needed

    // Private constructor to prevent instantiation
    private CurrentUser() {
        userId = -1; // Default value indicating no user is logged in
    }

    // Public method to get the Singleton instance
    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    // Setters and getters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
