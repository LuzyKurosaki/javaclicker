package com.example.javaclicker.model;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.javaclicker.database.Database;
import com.example.javaclicker.database.dao.ShopItemDao;
import com.example.javaclicker.database.model.ShopItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationStart extends Application {
    Database db;

    public ApplicationStart(Database db){this.db = db;}

    public void applicationBoot(GameData gameData){
        gameData.shopItems =  db.shopItemDao().getAll();
    }

    public void firstApplicationRun(){
        ShopItemDao shopItemDao = db.shopItemDao();
        ShopItem[] shopItems = new ShopItem[]{
                new ShopItem("Worker",100,1,"oil",0),
                new ShopItem("Well",15000,10,"oil",0),
                new ShopItem("Rig",150000,100,"oil",0),
                new ShopItem("Gasoline Factory",300000,10,"gas",0)
        };
        shopItemDao.insertAll(shopItems);
    }
}
