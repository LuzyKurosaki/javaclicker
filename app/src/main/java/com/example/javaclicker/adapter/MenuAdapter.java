package com.example.javaclicker.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaclicker.R;
import com.example.javaclicker.database.model.ShopItem;
import com.example.javaclicker.fragment.ShopFragment;
import com.example.javaclicker.lib.Formatter;
import com.example.javaclicker.model.GameData;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ShopItem[] localDataSet;
    private GameData gameData;
    private MenuAdapter adapter;

    public MenuAdapter(GameData gameData){
        this.gameData = gameData;
        this.adapter = this;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView price;
        private final ImageButton buy;
        private final TextView production;

        public ViewHolder(View view){
            super(view);
            name =  view.findViewById(R.id.name);
            price =  view.findViewById(R.id.price);
            buy = view.findViewById(R.id.buy);
            production = view.findViewById(R.id.production);
        }
    }

    public void setDataset(ShopItem[] dataset) {
       localDataSet = dataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        int price = localDataSet[position].price;
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.price.setText(Formatter.formatBigNum(localDataSet[position].price));
        viewHolder.name.setText(localDataSet[position].name);
        viewHolder.production.setText(Integer.toString((localDataSet[position].modifier * localDataSet[position].amount)));
        viewHolder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(price <= gameData.cash()){
                    gameData.cash(gameData.cash() - price);
                    gameData.shopItems[position].amount ++;
                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

}
