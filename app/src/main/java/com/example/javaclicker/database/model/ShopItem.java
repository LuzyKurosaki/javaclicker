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

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "is_selling")
    public Boolean isSelling;

    @ColumnInfo(name = "amount")
    public int amount;

    public ShopItem(String name, int price,int modifier ,String type,int amount, boolean isSelling){
        this.price = price;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.modifier = modifier;
        this.isSelling = isSelling;
    }

}
