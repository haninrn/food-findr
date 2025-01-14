package com.example.foodfindr2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import java.util.Date;


@Entity(tableName = "notifications",
        foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int notification_id;
    public int user_id;
    public String title;
    public String message;
    public Date date_sent;
}

