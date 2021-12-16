package com.example.javaclicker.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.room.Room;

import com.example.javaclicker.MainActivity;
import com.example.javaclicker.database.Database;
import com.example.javaclicker.database.model.ShopItem;
import com.example.javaclicker.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.logging.Handler;

public class GameData extends Observable {
    SharedPreferences sharedPreferences;
    private int _barrels;
    private int _cash;
    private int _gas;

    private GameData gameData;
    public ShopFragment shopFragment;
    public ShopItem[] shopItems;

    protected MainActivity activity;

    public synchronized int barrels(){
        return this._barrels;
    }
    public void barrels(int barrels){
        synchronized (this){
            this._barrels = barrels;
        }
        setChanged();
        notifyObservers();
    }
    public synchronized int cash(){
        return this._cash;
    }
    public void cash(int cash){
        synchronized (this){
            this._cash = cash;
        }
        setChanged();
        notifyObservers();
    }
    public synchronized int gas(){
        return this._gas;
    }
    public void gas(int gas){
        synchronized (this){
            this._gas = gas;
        }
        setChanged();
        notifyObservers();
    }


    public GameData(MainActivity activity) {
        this.activity = activity;
         sharedPreferences = activity.getSharedPreferences(
                "com.javaclicker.gamedata", Context.MODE_PRIVATE
        );
            this._barrels = sharedPreferences.getInt("barrels", 0);
            this._gas= sharedPreferences.getInt("gas", 0);
            this._cash = sharedPreferences.getInt("cash", 0);

        this.shopItems = new ShopItem[]{};
        this.gameData = this;
        AppStart(activity.executorService);
    }

    private void AppStart(Executor executor){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Database db = Room.databaseBuilder(activity.getApplicationContext(), Database.class, "Javaclicker").build();
                ApplicationStart applicationStart = new ApplicationStart(db);
                if(sharedPreferences.getBoolean("first_run",true)){
                    applicationStart.firstApplicationRun();
                    sharedPreferences.edit()
                            .putBoolean("first_run", false)
                            .apply();
                }
                applicationStart.applicationBoot(gameData);
                activity.shopFragment.updateRecylerview();
            }
        });

    }

    public void Dispatch(Executor executor){
        new Runtime(executor).Start();
    }

    class Runtime{
        private final Executor executor;
        private Database db = Room.databaseBuilder(activity.getApplicationContext(), Database.class, "Javaclicker").build();
        public Runtime(Executor executor){
            this.executor = executor;
        }

        public void Start(){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Handler();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        SaveHandler();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        private void Handler() throws InterruptedException {
            while(true){
                Thread.sleep(1000);
            }
        }

        private void SaveHandler() throws InterruptedException {
            while(true){
                Thread.sleep(30000);
                SavePref();
                Save(db);
                activity.runOnUiThread(()->{
                    Toast.makeText(activity, "Game Saved", Toast.LENGTH_SHORT).show();
                });
            }
        }

    }

    public Boolean SavePref() throws InterruptedException {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("barrels", this._barrels);
        editor.putInt("cash", this._cash);
        editor.putInt("gas", this._gas);
        editor.apply();
        return true;
    }

    public void Save(Database db){
        db.shopItemDao().updateAll(shopItems);
    }

    public void Clicked(){
        synchronized (this){
            this._barrels ++;
        }
        setChanged();
        notifyObservers();
    }

}

