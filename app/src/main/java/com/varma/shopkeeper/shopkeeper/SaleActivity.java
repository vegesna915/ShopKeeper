package com.varma.shopkeeper.shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.varma.shopkeeper.shopkeeper.Extras.Constants;

public class SaleActivity extends AppCompatActivity {

    private ProgressBar progressBar;

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



    }
}
