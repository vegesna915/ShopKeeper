package com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_currentStock.CurrentStockViewHolder;
import com.varma.shopkeeper.shopkeeper.CurrentStockActivity;
import com.varma.shopkeeper.shopkeeper.CurrentStockByBrandActivity;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.R;

import java.util.ArrayList;


public class RecyclerViewAdapter_currentStock extends RecyclerView.Adapter<CurrentStockViewHolder> {


    private CurrentStockActivity activity;
    private ArrayList<String> brands;

    public RecyclerViewAdapter_currentStock(CurrentStockActivity activity, ArrayList<String> brands) {
        this.activity = activity;
        this.brands = brands;
    }

    @Override
    public CurrentStockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(R.layout.recycler_view_current_stock,parent,false);

        return new CurrentStockViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CurrentStockViewHolder holder, final int position) {

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CurrentStockByBrandActivity.class);
                intent.putExtra(Constants.CurrentStockByBrand.brandName, brands.get(position));
                activity.startActivity(intent);
            }
        });

        holder.brandNameView.setText(brands.get(position));

    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    class CurrentStockViewHolder extends RecyclerView.ViewHolder{

        View button;
        TextView brandNameView;

        CurrentStockViewHolder(View itemView) {
            super(itemView);
            button =itemView.findViewById(R.id.stockButton_recyclerView_currentStock);
            brandNameView = (TextView) itemView.findViewById(R.id.brandName_recyclerView_currentStock);
        }
    }

}
