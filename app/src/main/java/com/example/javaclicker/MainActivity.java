package com.example.javaclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.javaclicker.fragment.FactoryFragment;
import com.example.javaclicker.fragment.ShopFragment;
import com.example.javaclicker.lib.Formatter;
import com.example.javaclicker.model.GameData;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements Observer{
    Boolean menuState = false;
    public GameData gameData;

    public ShopFragment shopFragment;
    private FactoryFragment factoryFragment;

    public ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gameData = new GameData(this);
        this.shopFragment = new ShopFragment(this);
        this.factoryFragment =  new FactoryFragment(this);

        findViewById(R.id.MainFrameLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
            }
        });

        findViewById(R.id.clicker_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameData.Clicked();
                CloseMenu();
            }
        });


        findViewById(R.id.menuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.menu, shopFragment, null)
                        .commit();
                findViewById(R.id.menu).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.factoryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.menu, factoryFragment, null)
                        .commit();
                findViewById(R.id.menu).setVisibility(View.VISIBLE);
            }
        });

        this.gameData.addObserver(shopFragment);
        this.gameData.addObserver(this);
        this.gameData.addObserver(factoryFragment);
        this.gameData.Dispatch(executorService);

        setPrices();
    }

    private void setPrices(){
        TextView cashCount = findViewById(R.id.cashCount);
        TextView oilCount = findViewById(R.id.oilCount);
        TextView gasCount = findViewById(R.id.gasCount);

        gasCount.setText(Formatter.formatBigNum(gameData.gas()));
        oilCount.setText(Formatter.formatBigNum(gameData.barrels()));
        cashCount.setText(Formatter.formatBigNum(gameData.cash()));
    }

    private void CloseMenu(){
            View menu = findViewById(R.id.menu);
            menu.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update(Observable observable, Object o) {
        setPrices();
    }
}