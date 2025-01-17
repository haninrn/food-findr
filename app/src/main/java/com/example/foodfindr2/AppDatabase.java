package com.example.foodfindr2;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import com.example.foodfindr2.model.Notifications;
import com.example.foodfindr2.model.Review;
import com.example.foodfindr2.model.User;
import com.example.foodfindr2.model.Donation;
import com.example.foodfindr2.model.Item;
import com.example.foodfindr2.dao.UserDao;
import com.example.foodfindr2.dao.DonationDao;
import com.example.foodfindr2.dao.ItemDao;
import com.example.foodfindr2.dao.NotificationDao;
import com.example.foodfindr2.dao.ReviewDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Donation.class, Item.class, Notifications.class, Review.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public abstract UserDao userDao();
    public abstract DonationDao donationDao();
    public abstract ItemDao itemDao();
    public abstract NotificationDao notificationDao();
    public abstract ReviewDao reviewDao();

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Delete the existing database if schema changes
                    context.getApplicationContext().deleteDatabase("app_database");

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .allowMainThreadQueries() // Not recommended for production
                            .fallbackToDestructiveMigration() // Handles schema changes
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
