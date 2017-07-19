package com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.varma.shopkeeper.shopkeeper.AddVendorActivity;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.ManageVendorsActivity;
import com.varma.shopkeeper.shopkeeper.Objects.Vendor;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_ManageVendors.ManageVendorsViewHolder;
import com.varma.shopkeeper.shopkeeper.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_ManageVendors extends RecyclerView.Adapter<ManageVendorsViewHolder> {

    private ManageVendorsActivity activity;
    private ArrayList<Vendor> vendors;
    private int longClickPosition;

    public RecyclerViewAdapter_ManageVendors(ManageVendorsActivity activity, ArrayList<Vendor> vendors) {
        this.activity = activity;
        this.vendors = vendors;

    }

    @Override
    public ManageVendorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(
                R.layout.recycler_view_manage_vendor, parent, false);

        return new ManageVendorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManageVendorsViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Vendor vendor = vendors.get(position);

        holder.vendorNameView.setText(vendor.getVendorName());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, AddVendorActivity.class);
                intent.putExtra(Constants.AddVendorActivity.isAdd_or_Edit,false);
                intent.putExtra(Constants.AddVendorActivity.editVendorId,vendor.getVendorId());
                activity.startActivity(intent);

            }
        });

        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                longClickPosition = position;
                activity.openContextMenu(v);

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return vendors.size();
    }

    public void onClickContextMenuItem(MenuItem menuItem) {

        Vendor vendor = vendors.get(longClickPosition);


        switch (menuItem.getTitle().toString()) {

            case "Edit": {

                Intent intent = new Intent(activity, AddVendorActivity.class);
                intent.putExtra(Constants.AddVendorActivity.isAdd_or_Edit,false);
                intent.putExtra(Constants.AddVendorActivity.editVendorId,vendor.getVendorId());
                activity.startActivity(intent);
                break;
            }

            case "Remove": {

                FirebaseDb.getVendorsDbReference().child(vendor.getVendorId()).removeValue();

                vendors.remove(longClickPosition);
                notifyDataSetChanged();

                Toast.makeText(activity, "Vendor removed", Toast.LENGTH_SHORT).show();

                break;
            }
        }

    }

    class ManageVendorsViewHolder extends RecyclerView.ViewHolder {

        public View v;
        private TextView vendorNameView;

        ManageVendorsViewHolder(View itemView) {
            super(itemView);

            v = itemView.findViewById(R.id.buttonVendor_recyclerView_manageVendor);

            vendorNameView = (TextView) itemView.findViewById(R.id.vendorName_recyclerView_manageVendor);

        }
    }
}
