package com.example.foodfindr2.dao;

import com.example.foodfindr2.model.Notifications;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void insertNotification(Notifications notification);

    @Query("SELECT * FROM notifications WHERE user_id = :userId")
    List<Notifications> getUserNotifications(int userId);

    @Query("DELETE FROM notifications WHERE notificationId = :notificationId")
    void deleteNotification(int notificationId);

    @Query("DELETE FROM notifications WHERE user_id = :userId")
    void deleteAllUserNotifications(int userId);
}
