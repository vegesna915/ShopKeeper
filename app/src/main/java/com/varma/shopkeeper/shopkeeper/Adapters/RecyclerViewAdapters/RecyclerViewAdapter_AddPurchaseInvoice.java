package com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.varma.shopkeeper.shopkeeper.AddPurchaseInvoiceActivity;
import com.varma.shopkeeper.shopkeeper.Objects.InvoiceItem;
import com.varma.shopkeeper.shopkeeper.R;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters
        .RecyclerViewAdapter_AddPurchaseInvoice.AddPurchaseInvoice_viewHolder;

import java.util.ArrayList;

public class RecyclerViewAdapter_AddPurchaseInvoice extends RecyclerView.Adapter<AddPurchaseInvoice_viewHolder> {

    private AddPurchaseInvoiceActivity activity;
    private ArrayList<InvoiceItem> invoiceItems;

    public RecyclerViewAdapter_AddPurchaseInvoice(AddPurchaseInvoiceActivity activity, ArrayList<InvoiceItem> items) {
        this.activity = activity;
        this.invoiceItems = items;
    }

    @Override
    public AddPurchaseInvoice_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(
                R.layout.recycler_view_add_purchase_invoice, parent, false);

        return new AddPurchaseInvoice_viewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddPurchaseInvoice_viewHolder holder, int position) {

        InvoiceItem invoiceItem = invoiceItems.get(position);

        holder.itemNameView.setText(invoiceItem.getItemBrandName()+" "+invoiceItem.getItemName());
        holder.itemSizeView.setText("Size "+invoiceItem.getItemSize());
        holder.itemQtyView.setText(invoiceItem.getItemQty()+" x " + invoiceItem.getItemUnitPrice());
        holder.itemTotalPriceView.setText(invoiceItem.getItemPrice());

    }

    @Override
    public int getItemCount() {
        return invoiceItems.size();
    }

    class AddPurchaseInvoice_viewHolder extends RecyclerView.ViewHolder {

        View v;
        TextView itemNameView, itemSizeView, itemQtyView, itemTotalPriceView;


        AddPurchaseInvoice_viewHolder(View itemView) {
            super(itemView);

            v = itemView;

            itemNameView = (TextView) v.findViewById(R.id.itemName_recyclerView_addPurchaseInvoice);
            itemSizeView = (TextView) v.findViewById(R.id.itemSize_recyclerView_addPurchaseInvoice);
            itemQtyView = (TextView) v.findViewById(R.id.itemQty_recyclerView_addPurchaseInvoice);
            itemTotalPriceView = (TextView) v.findViewById(R.id.itemTotalPrice_recyclerView_addPurchaseInvoice);

        }
    }


}
