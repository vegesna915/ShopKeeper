package com.varma.shopkeeper.shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_sale;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.Sale;

import java.util.ArrayList;

public class SaleActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerViewAdapter_sale adapter;
    private ArrayList<Sale> sales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSaleActivity);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar_sale);
        progressBar.setVisibility(View.VISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sale);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleActivity.this, AddSaleActivity.class);
                intent.putExtra(Constants.AddPSaleActivity.isAdd_or_Edit, true);
                intent.putExtra(Constants.AddPSaleActivity.editSaleId, "");
                startActivity(intent);
            }
        });

        setRecyclerView();

    }

    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_sale);
        adapter = new RecyclerViewAdapter_sale(this, sales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();


        Query reference = FirebaseDb.getSalesDbReference().orderByKey();
        reference.keepSynced(true);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sales.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Sale sale = snap.getValue(Sale.class);
                    sales.add(sale);
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);


    }
}
