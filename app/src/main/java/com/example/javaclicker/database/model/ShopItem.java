package com.example.javaclicker.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopitem")
public class ShopItem {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "modifier")
    public int modifier;

    @ColumnInfo(name = "amount")
    public int amount;

    public ShopItem(String name, int price,int modifier ,int amount){
        this.price = price;
        this.name = name;
        this.amount = amount;
        this.modifier = modifier;
    }

}
