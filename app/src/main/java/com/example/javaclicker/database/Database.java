package com.example.javaclicker.database;

import androidx.room.AutoMigration;
import androidx.room.RoomDatabase;

import com.example.javaclicker.database.dao.ShopItemDao;
import com.example.javaclicker.database.model.ShopItem;

@androidx.room.Database(entities = {ShopItem.class}, version = 1
)
public abstract class Database extends RoomDatabase {
    public abstract ShopItemDao shopItemDao();
}
