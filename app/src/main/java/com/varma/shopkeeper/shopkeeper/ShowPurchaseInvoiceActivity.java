package com.varma.shopkeeper.shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_showPurchaseInvoice;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.PurchaseInvoice;

import java.util.ArrayList;
import java.util.Collections;

public class ShowPurchaseInvoiceActivity extends AppCompatActivity {

    RecyclerViewAdapter_showPurchaseInvoice adapter;
    private ArrayList<PurchaseInvoice> invoices = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_purchase_invoice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarShowPurchaseInvoiceActivity);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar_showPurchaseInvoice);
        progressBar.setVisibility(View.VISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_ShowPurchaseInvoiceActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPurchaseInvoiceActivity.this,AddPurchaseInvoiceActivity.class);
                intent.putExtra(Constants.AddPurchaseInvoiceActivity.isAdd_or_Edit,true);
                intent.putExtra(Constants.AddPurchaseInvoiceActivity.editPurchaseInvoiceId,"");
                startActivity(intent);
            }
        });

        setRecyclerView();


    }

    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_ShowPurchaseInvoiceActivity);
         adapter = new RecyclerViewAdapter_showPurchaseInvoice(this,invoices);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void syncInvoices() {


        Query query = FirebaseDb.getPurchaseInvoicesDbReference()
                .orderByKey().limitToLast(50);

        query.keepSynced(true);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                invoices.clear();

                for(DataSnapshot snap:dataSnapshot.getChildren()){

                    PurchaseInvoice purchaseInvoice = snap.getValue(PurchaseInvoice.class);
                    invoices.add(purchaseInvoice);
                }

                Collections.reverse(invoices);

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowPurchaseInvoiceActivity.this, "Database Sync Failed", Toast.LENGTH_SHORT).show();
            }
        };

        query.addValueEventListener(valueEventListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        syncInvoices();

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
