package com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_currentStockByBrand.CurrentStockByBrandViewHolder;
import com.varma.shopkeeper.shopkeeper.CurrentStockByBrandActivity;
import com.varma.shopkeeper.shopkeeper.Objects.StockItem;
import com.varma.shopkeeper.shopkeeper.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_currentStockByBrand extends RecyclerView.Adapter<CurrentStockByBrandViewHolder> {

    private ArrayList<StockItem> stockItems;
    private CurrentStockByBrandActivity activity;

    public RecyclerViewAdapter_currentStockByBrand(CurrentStockByBrandActivity activity
            , ArrayList<StockItem> stockItems) {
        this.stockItems = stockItems;
        this.activity = activity;
    }

    @Override
    public CurrentStockByBrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(
                R.layout.recycler_view_current_stock_by_brand, parent, false);

        return new CurrentStockByBrandViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CurrentStockByBrandViewHolder holder, int position) {

        StockItem stockItem = stockItems.get(position);

        holder.stockItemNameView.setText(stockItem.getItemName());
        holder.stockItemSizeView.setText("Size " + stockItem.getItemSize());
        holder.stockItemUnitPriceView.setText("Rs " + stockItem.getItemUnitPrice());
        holder.stockItemQtyView.setText("Qty " + stockItem.getItemQty());

    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    class CurrentStockByBrandViewHolder extends RecyclerView.ViewHolder {

        TextView stockItemNameView, stockItemSizeView, stockItemUnitPriceView, stockItemQtyView;

        CurrentStockByBrandViewHolder(View itemView) {
            super(itemView);
            stockItemNameView = (TextView) itemView.findViewById(R.id.stockItemName_currentStockByBrand);
            stockItemSizeView = (TextView) itemView.findViewById(R.id.stockItemSize_currentStockByBrand);
            stockItemQtyView = (TextView) itemView.findViewById(R.id.stockItemQty_currentStockByBrand);
            stockItemUnitPriceView = (TextView) itemView.findViewById(R.id.stockItemUnitPrice_currentStockByBrand);
        }
    }

}
