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
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_ManageVendors;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.Vendor;

import java.util.ArrayList;

public class ManageVendorsActivity extends AppCompatActivity {

    RecyclerViewAdapter_ManageVendors adapter;
    ArrayList<Vendor> vendors = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vendors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarManageVendorsActivity);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar_manageVendors);
        progressBar.setVisibility(View.VISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_manageVendorsActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageVendorsActivity.this,AddVendorActivity.class);
                intent.putExtra(Constants.AddVendorActivity.isAdd_or_Edit,true);
                intent.putExtra(Constants.AddVendorActivity.editVendorId,"");
                startActivity(intent);
            }
        });

        setRecyclerView();

    }

    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_manageVendorsActivity);

        adapter = new RecyclerViewAdapter_ManageVendors(this,vendors);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu_manage_vendors_activity,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        adapter.onClickContextMenuItem(item);

        return true;
    }

    private void syncVendors(){


        Query reference = FirebaseDb.getVendorsDbReference().orderByChild("vendorName");
        reference.keepSynced(true);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vendors.clear();

                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    Vendor vendor = snap.getValue(Vendor.class);
                    vendors.add(vendor);
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

    @Override
    protected void onStart() {
        super.onStart();

        syncVendors();
    }
}
