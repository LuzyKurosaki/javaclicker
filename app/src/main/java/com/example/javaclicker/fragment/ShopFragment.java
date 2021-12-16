package com.example.javaclicker.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.javaclicker.MainActivity;
import com.example.javaclicker.R;
import com.example.javaclicker.adapter.MenuAdapter;
import com.example.javaclicker.database.Database;

import java.util.Observable;
import java.util.Observer;

public class ShopFragment extends Fragment implements Observer {
    MenuAdapter adapter;
    MainActivity activity;
    TextView cashCount;

    public ShopFragment(MainActivity activity){
        this.activity = activity;
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_bottom_menu, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        RecyclerView recyclerView =  view.findViewById(R.id.menuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MenuAdapter(activity.gameData);
        adapter.setDataset(activity.gameData.shopItems);
        recyclerView.setAdapter(adapter);
        this.cashCount = view.findViewById(R.id.cashCountMenu);
        cashCount.setText(Integer.toString(activity.gameData.cash()));
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateRecylerview()
    {

       // adapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update(Observable observable, Object o) {
        if(cashCount != null){
            cashCount.setText(Integer.toString(activity.gameData.cash()));
        }
    }
}
