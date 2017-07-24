package com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_addSale.AddSale_viewHolder;
import com.varma.shopkeeper.shopkeeper.AddSaleActivity;
import com.varma.shopkeeper.shopkeeper.Objects.SaleItem;
import com.varma.shopkeeper.shopkeeper.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_addSale extends RecyclerView.Adapter<AddSale_viewHolder> {

    private AddSaleActivity activity;
    private ArrayList<SaleItem> saleItems;

    public RecyclerViewAdapter_addSale(AddSaleActivity activity, ArrayList<SaleItem> items) {
        this.activity = activity;
        this.saleItems = items;
    }

    @Override
    public AddSale_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(
                R.layout.recycler_view_add_sale, parent, false);

        return new AddSale_viewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddSale_viewHolder holder, int position) {

        SaleItem saleItem = saleItems.get(position);

        holder.itemNameView.setText(saleItem.getItemBrand() + " " + saleItem.getItemName());
        holder.itemSizeView.setText("Size " + saleItem.getItemSize());
        holder.itemQtyView.setText(saleItem.getItemQty() + " x " + saleItem.getItemUnitPrice());
        holder.itemTotalPriceView.setText(saleItem.getItemPrice() + "");

    }

    @Override
    public int getItemCount() {
        return saleItems.size();
    }

    class AddSale_viewHolder extends RecyclerView.ViewHolder {

        View v;
        TextView itemNameView, itemSizeView, itemQtyView, itemTotalPriceView;


        AddSale_viewHolder(View itemView) {
            super(itemView);

            v = itemView;

            itemNameView = (TextView) v.findViewById(R.id.itemName_recyclerView_addSale);
            itemSizeView = (TextView) v.findViewById(R.id.itemSize_recyclerView_addSale);
            itemQtyView = (TextView) v.findViewById(R.id.itemQty_recyclerView_addSale);
            itemTotalPriceView = (TextView) v.findViewById(R.id.itemTotalPrice_recyclerView_addSale);

        }
    }


}
