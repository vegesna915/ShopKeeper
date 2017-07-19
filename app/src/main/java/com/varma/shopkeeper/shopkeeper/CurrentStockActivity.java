package com.varma.shopkeeper.shopkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_currentStock;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;

import java.util.ArrayList;

public class CurrentStockActivity extends AppCompatActivity {

    RecyclerViewAdapter_currentStock adapter;
    ArrayList<String> brands = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCurrentStock);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        declarations();

        setRecyclerView();

    }

    private void declarations() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar_currentStock);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_currentStock);
        adapter = new RecyclerViewAdapter_currentStock(this,brands);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDb.getCurrentStockDbReference().orderByChild("itemBrandName");
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                brands.clear();
                String temp = "";

                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    String brand = snap.child("itemBrandName").getValue(String.class);
                    if(!temp.equals(brand)){
                        brands.add(brand);
                        temp = brand;
                    }
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
