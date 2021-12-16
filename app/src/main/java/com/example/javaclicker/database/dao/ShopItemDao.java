package com.example.javaclicker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.javaclicker.database.model.ShopItem;

import java.util.List;

@Dao
public interface ShopItemDao {


    @Query("SELECT * FROM shopitem")
    public ShopItem[] getAll();

    @Query("SELECT * FROM shopitem WHERE uid LIKE :uid LIMIT 1")
    public ShopItem findById(int uid);

    @Insert()
    void insertAll(ShopItem... shopItems);

    @Update
    public void update(ShopItem shopItem);

    @Update
    public void updateAll(ShopItem... shopItems);

    @Delete
    public void delete(ShopItem shopItem);

    @Delete
    public void deleteAll(ShopItem... shopItems);

}
