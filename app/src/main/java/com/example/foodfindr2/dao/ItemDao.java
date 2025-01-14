package com.example.foodfindr2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodfindr2.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    // Insert an item
    @Insert
    long insertItem(Item item);

    // Insert multiple items for a donation
    @Insert
    List<Long> insertItems(List<Item> items);

    // Update an item
    @Update
    void updateItem(Item item);

    // Get all items for a specific donation
    @Query("SELECT * FROM items WHERE donation_id = :donationId")
    List<Item> getItemsByDonation(int donationId);

    // Get all items
    @Query("SELECT * FROM items")
    List<Item> getAllItems();

    // Get items by category
    @Query("SELECT * FROM items WHERE category = :category")
    List<Item> getItemsByCategory(String category);

    @Query("SELECT item_name FROM items WHERE item_id = :itemId")
    LiveData<String> getItemNameById(int itemId);

    @Query("SELECT item_name FROM items WHERE donation_id = :donationId")
    LiveData<String> getItemNameByDonationId(int donationId);


}
