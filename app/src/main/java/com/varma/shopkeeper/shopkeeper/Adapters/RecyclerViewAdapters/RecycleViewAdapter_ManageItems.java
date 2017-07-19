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


import com.varma.shopkeeper.shopkeeper.AddItemActivity;
import com.varma.shopkeeper.shopkeeper.Database.ItemsDb;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.ManageItemsActivity;
import com.varma.shopkeeper.shopkeeper.Objects.Item;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecycleViewAdapter_ManageItems.ManageItemsViewHolder;
import com.varma.shopkeeper.shopkeeper.R;

import java.util.ArrayList;

public class RecycleViewAdapter_ManageItems extends RecyclerView.Adapter<ManageItemsViewHolder> {

    private ManageItemsActivity activity;
    private ArrayList<Item> items;
    private int longClickPosition;

    public RecycleViewAdapter_ManageItems(ManageItemsActivity activity, ArrayList<Item> items) {
        this.activity = activity;
        this.items = items;



    }

    @Override
    public ManageItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(
                R.layout.recycler_view_manage_items, parent, false);

        return new ManageItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManageItemsViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Item item = items.get(position);

        holder.itemName.setText(item.getItemBrandName()+" "+item.getItemName());
        holder.itemPrice.setText(item.getItemSalePrice());
        holder.itemBrandName.setText("Size "+item.getItemSize());


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddItemActivity.class);
                intent.putExtra(Constants.AddItemsActivity.isAdd_or_Edit,false);
                intent.putExtra(Constants.AddItemsActivity.editItemId,item.getItemCode());
                activity.startActivity(intent);
            }
        });

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
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
        return items.size();
    }

    public void onClickContextMenuItem(MenuItem menuItem) {

        Item item = items.get(longClickPosition);

        switch (menuItem.getTitle().toString()) {

            case "Edit": {


                Intent intent = new Intent(activity, AddItemActivity.class);
                intent.putExtra(Constants.AddItemsActivity.isAdd_or_Edit,false);
                intent.putExtra(Constants.AddItemsActivity.editItemId,item.getItemCode());
                activity.startActivity(intent);

                break;
            }

            case "Remove": {

                ItemsDb itemDb = new ItemsDb(activity);
                boolean isDeleted = itemDb.deleteItemById(item.getItemCode());

                if (isDeleted) {

                    items.remove(longClickPosition);
                    notifyDataSetChanged();

                    Toast.makeText(activity, "Item removed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Failed to remove item", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    class ManageItemsViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView itemName, itemBrandName, itemPrice;

        ManageItemsViewHolder(View v) {
            super(v);

            view = v;

            itemName = (TextView) v.findViewById(R.id.itemName_recyclerView_manageItemActivity);
            itemPrice = (TextView) v.findViewById(R.id.itemPrice_recyclerView_manageItemActivity);
            itemBrandName = (TextView) v.findViewById(R.id.itemBrandName_recyclerView_manageItemActivity);

        }
    }


}
