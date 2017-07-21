package com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_showPurchaseInvoice.ShowPurchaseInvoiceViewHolder;
import com.varma.shopkeeper.shopkeeper.AddPurchaseInvoiceActivity;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.Objects.PurchaseInvoice;
import com.varma.shopkeeper.shopkeeper.R;
import com.varma.shopkeeper.shopkeeper.ShowPurchaseInvoiceActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter_showPurchaseInvoice extends RecyclerView.Adapter<ShowPurchaseInvoiceViewHolder>{

    private ShowPurchaseInvoiceActivity activity;
    private ArrayList<PurchaseInvoice> invoices;


    public RecyclerViewAdapter_showPurchaseInvoice(ShowPurchaseInvoiceActivity activity,
                                                   ArrayList<PurchaseInvoice> purchaseInvoices) {
        this.activity = activity;
        this.invoices = purchaseInvoices;
    }

    @Override
    public ShowPurchaseInvoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.recycler_view_show_purchase_invoice,parent,false);
        return new ShowPurchaseInvoiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShowPurchaseInvoiceViewHolder holder, int position) {

        final PurchaseInvoice invoice = invoices.get(position);

        holder.vendorNameEdit.setText(invoice.getVendorName());
        holder.invoiceNumberEdit.setText(invoice.getInvoiceNumber());
        holder.invoiceTotalPriceEdit.setText(invoice.getInvoiceTotalPrice());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, AddPurchaseInvoiceActivity.class);
                intent.putExtra(Constants.AddPurchaseInvoiceActivity.isAdd_or_Edit, false);
                intent.putExtra(Constants.AddPurchaseInvoiceActivity.editPurchaseInvoiceId, invoice.getInvoiceId());
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    class ShowPurchaseInvoiceViewHolder extends RecyclerView.ViewHolder{

        TextView invoiceNumberEdit,vendorNameEdit,invoiceTotalPriceEdit;
        View v;
        ShowPurchaseInvoiceViewHolder(View itemView) {
            super(itemView);

            v = itemView;

            invoiceNumberEdit = (TextView) v.findViewById(R.id.invoiceNumber_recyclerView_showPurchaseInvoice);
            invoiceTotalPriceEdit = (TextView) v.findViewById(R.id.invoiceTotalPrice_recyclerView_showPurchaseInvoice);
            vendorNameEdit = (TextView) v.findViewById(R.id.vendorName_recyclerView_showPurchaseInvoice);

        }
    }


}
