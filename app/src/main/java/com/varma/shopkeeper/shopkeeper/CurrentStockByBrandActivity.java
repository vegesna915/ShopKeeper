package com.varma.shopkeeper.shopkeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_currentStockByBrand;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.StockItem;

import java.util.ArrayList;

public class CurrentStockByBrandActivity extends AppCompatActivity {

    RecyclerViewAdapter_currentStockByBrand adapter;
    ArrayList<StockItem> stockItems = new ArrayList<>();
    ProgressBar progressBar;
    String brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stock_by_brand);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCurrentStockByBrand);
        setSupportActionBar(toolbar);

        brandName = getIntent().getStringExtra(Constants.CurrentStockByBrand.brandName);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(brandName);
        }

        declarations();

        setRecyclerView();

    }

    private void declarations() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar_currentStockByBrand);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_currentStockByBrand);
        adapter = new RecyclerViewAdapter_currentStockByBrand(this, stockItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDb.getCurrentStockDbReference()
                .orderByChild("itemBrandName")
                .equalTo(brandName)
                .limitToFirst(50);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stockItems.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    StockItem stockItem = snap.getValue(StockItem.class);
                    stockItems.add(stockItem);
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {
                onBackPressed();
                break;
            }

        }

        return true;
    }

}
