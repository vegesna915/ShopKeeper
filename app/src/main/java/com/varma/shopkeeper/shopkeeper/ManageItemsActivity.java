package com.varma.shopkeeper.shopkeeper;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecycleViewAdapter_ManageItems;
import com.varma.shopkeeper.shopkeeper.Database.ItemsDb;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.Objects.Item;

import java.util.ArrayList;

public class ManageItemsActivity extends AppCompatActivity {



    RecycleViewAdapter_ManageItems adapter;
    ArrayList<Item> items = new  ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarManageItemsActivity);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab_manageItemsActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageItemsActivity.this,AddItemActivity.class);
                intent.putExtra(Constants.AddItemsActivity.isAdd_or_Edit,true);
                startActivity(intent);
            }
        });


        setRecyclerView();

    }

    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_manageItemsActivity);
        adapter = new RecycleViewAdapter_ManageItems(this,items);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.context_menu_manage_items_activty,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        adapter.onClickContextMenuItem(item);

        return true;
    }

    private void refreshItems(){

        ItemsDb itemsDb = new ItemsDb(this);
        items.clear();
        items.addAll(itemsDb.getAllItems());

    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshItems();
        adapter.notifyDataSetChanged();

    }



}
