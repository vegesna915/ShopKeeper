package com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.varma.shopkeeper.shopkeeper.Objects.Sale;
import com.varma.shopkeeper.shopkeeper.R;
import com.varma.shopkeeper.shopkeeper.SaleActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter_sale extends RecyclerView.Adapter<RecyclerViewAdapter_sale.SaleViewHolder> {

    private SaleActivity activity;
    private ArrayList<Sale> sales;

    public RecyclerViewAdapter_sale(SaleActivity activity, ArrayList<Sale> sales) {
        this.activity = activity;
        this.sales = sales;
    }

    @Override
    public SaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(R.layout.recycler_view_sale, parent, false);

        return new SaleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SaleViewHolder holder, int position) {

        Sale sale = sales.get(position);

        holder.saleDateView.setText(sale.getSaleDate());
        holder.saleTimeView.setText(sale.getSaleTime());
        holder.salePriceView.setText("Rs " + sale.getSaleTotalPrice());

    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    class SaleViewHolder extends RecyclerView.ViewHolder {

        TextView saleDateView, saleTimeView, salePriceView;
        View v;

        SaleViewHolder(View itemView) {
            super(itemView);

            v = itemView;
            saleDateView = (TextView) v.findViewById(R.id.saleDate_SaleActivity);
            saleTimeView = (TextView) v.findViewById(R.id.saleTime_SaleActivity);
            salePriceView = (TextView) v.findViewById(R.id.saleTotalPrice_SaleActivity);

        }
    }

}
