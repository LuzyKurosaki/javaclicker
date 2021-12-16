package com.example.javaclicker.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.javaclicker.MainActivity;
import com.example.javaclicker.R;

import java.util.Observable;
import java.util.Observer;

public class FactoryFragment extends Fragment implements Observer {
    TextView oilCount;
    TextView gasCount;
    ImageView sellOil;
    ImageView sellGas;
    MainActivity activity;


    public FactoryFragment(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_factory, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        oilCount = view.findViewById(R.id.factoryOilCount);
        gasCount = view.findViewById(R.id.factoryGasCount);
        sellOil = view.findViewById(R.id.sellOil);
        sellGas = view.findViewById(R.id.sellGas);



        sellOil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.gameData.cash(activity.gameData.cash() + (activity.gameData.barrels() * 1));
                activity.gameData.barrels(0);
            }
        });

        sellGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.gameData.cash(activity.gameData.cash() + (activity.gameData.gas() * 4));
                activity.gameData.gas(0);
            }
        });

        oilCount.setText(Integer.toString(activity.gameData.barrels()));
        gasCount.setText(Integer.toString(activity.gameData.gas()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update(Observable observable, Object o) {
        if(oilCount != null || gasCount != null){
            oilCount.setText(Integer.toString(activity.gameData.barrels()));
            gasCount.setText(Integer.toString(activity.gameData.gas()));
        }
    }
}
