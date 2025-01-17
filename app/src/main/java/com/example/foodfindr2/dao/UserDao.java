package com.example.foodfindr2.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import java.util.List;
import com.example.foodfindr2.model.User;



@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Insert
    void insertUsers(List<User> users);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    // Add this method to fetch all users
    @Query("SELECT * FROM users")
    List<User> getAllUsers();


    // Get a user by ID
    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);

    // Get a user by email
    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    // Delete all users
    @Query("DELETE FROM users")
    void deleteAllUsers();

    // Count the number of users
    @Query("SELECT COUNT(*) FROM users")
    int countUsers();

    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    void updatePassword(int userId, String newPassword);

}


