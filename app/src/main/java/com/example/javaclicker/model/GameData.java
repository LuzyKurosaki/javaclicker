package com.example.javaclicker.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.javaclicker.MainActivity;
import com.example.javaclicker.database.Database;
import com.example.javaclicker.database.model.ShopItem;
import com.example.javaclicker.fragment.FactoryFragment;
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
    public ShopItem[] shopItems;

    protected MainActivity activity;

    public synchronized int barrels(){
        return this._barrels;
    }
    public void barrels(int barrels){
        synchronized (this){
            if(barrels < 2000000000){
                this._barrels = barrels;
            }
        }
        setChanged();
        notifyObservers();
    }
    public synchronized int cash(){
        return this._cash;
    }
    public void cash(int cash){
        synchronized (this){
            if(cash < 2000000000){
                this._cash = cash;
            }
        }
        setChanged();
        notifyObservers();
    }
    public synchronized int gas(){
        return this._gas;
    }
    public void gas(int gas){
        synchronized (this){
            if(gas < 2000000000){
                this._gas = gas;
            }
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
                        while(true){
                            Handler();
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true){
                            Thread.sleep(30000);
                            SaveHandler();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void Handler() throws InterruptedException {
            int oProd = 0;
            int gProd = 0;
            for (ShopItem item : shopItems){
                switch (item.type){
                    case "oil" :
                        barrels(barrels() + (item.amount * item.modifier));
                        oProd = oProd + (item.amount * item.modifier);
                        break;
                    case "gas" :
                        if(barrels() <= (item.amount * item.modifier)){
                            gas(gas() + barrels());
                            barrels(0);
                            gProd = gProd + barrels();
                        }else{
                            barrels(barrels() - (item.amount * item.modifier));
                            gas(gas() + (item.amount * item.modifier));
                            gProd = gProd + (item.amount * item.modifier);
                        }
                        break;
                }
            }
            activity.factoryFragment.update(oProd, gProd);
        }

        private void SaveHandler() throws InterruptedException {
            SavePref();
            Save(db);
            activity.runOnUiThread(()->{
                Toast.makeText(activity, "Game Saved", Toast.LENGTH_SHORT).show();

            });
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

