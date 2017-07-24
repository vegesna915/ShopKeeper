package com.varma.shopkeeper.shopkeeper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_addSale;
import com.varma.shopkeeper.shopkeeper.Extras.DialogAddSaleItem;
import com.varma.shopkeeper.shopkeeper.Extras.Utilis;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.Sale;
import com.varma.shopkeeper.shopkeeper.Objects.SaleItem;

import java.util.ArrayList;

public class AddSaleActivity extends AppCompatActivity {

    RecyclerViewAdapter_addSale adapterRecyclerView;
    ArrayList<SaleItem> saleItems = new ArrayList<>();
    private long saleSubTotal = 0;
    private float saleTax, saleDiscount;
    private EditText saleSubTotalEdit, saleTaxEdit, saleDiscountEdit, saleTotalPriceEdit;
    private TextView saleDateView, saleTimeView;
    private long saleId, saleTotalPrice;


    private boolean isAddOrEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddSaleActivity);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        saleId = System.currentTimeMillis();

        declarations();
        setRecyclerView();
    }


    private void declarations() {

        saleTimeView = (TextView) findViewById(R.id.saleTime_addSaleActivity);
        saleTimeView.setText(Utilis.getCurrentTime());
        saleDateView = (TextView) findViewById(R.id.saleDate_addSaleActivity);
        saleDateView.setText(Utilis.getCurrentDate());
        saleSubTotalEdit = (EditText) findViewById(R.id.saleSubTotal_addSaleActivity);
        saleTaxEdit = (EditText) findViewById(R.id.saleTax_addSaleActivity);
        saleDiscountEdit = (EditText) findViewById(R.id.saleDiscount_addSaleActivity);
        saleTotalPriceEdit = (EditText) findViewById(R.id.saleTotalPrice_addSaleActivity);

        setListeners();
    }

    private void setListeners() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshInputs();
            }
        };

        saleTaxEdit.addTextChangedListener(textWatcher);
        saleDiscountEdit.addTextChangedListener(textWatcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addSale);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddSaleItemDialog();
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveSaleButton_addSaleActivity);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilis.closeKeyboard(AddSaleActivity.this, v);
                saveButtonClicked();
            }
        });

        View layout = findViewById(R.id.linearLayout_main_addSaleActivity);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilis.closeKeyboard(AddSaleActivity.this, v);
            }
        });

    }

    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_addSaleActivity);
        adapterRecyclerView = new RecyclerViewAdapter_addSale(this, saleItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterRecyclerView);

    }

    private void createAddSaleItemDialog() {

        DialogAddSaleItem dialogAddSaleItem = new DialogAddSaleItem(this);
        Dialog dialog = dialogAddSaleItem.getAddSaleItemDialog();

        dialog.show();
    }

    public void addSaleItem(SaleItem saleItem) {

        saleItems.add(saleItem);
        adapterRecyclerView.notifyDataSetChanged();
        refreshInputs();

    }

    private void refreshInputs() {

        if (saleTaxEdit.getText().toString().equals("")) {
            saleTax = 0;
        } else {
            saleTax = Float.parseFloat(saleTaxEdit.getText().toString());
        }

        if (saleDiscountEdit.getText().toString().equals("")) {
            saleDiscount = 0;
        } else {
            saleDiscount = Float.parseFloat(saleDiscountEdit.getText().toString());
        }

        saleSubTotal = 0;
        for (SaleItem i : saleItems) {
            saleSubTotal += i.getItemPrice();
        }

        saleTotalPrice = (long) (saleSubTotal + (saleSubTotal * saleTax / 100) - (saleSubTotal * saleDiscount / 100));

        saleSubTotalEdit.setText(saleSubTotal + "");
        saleTotalPriceEdit.setText(saleTotalPrice + "");
        saleSubTotalEdit.setError(null);
        saleTotalPriceEdit.setError(null);

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

    private void saveButtonClicked() {

        refreshInputs();

        if (saleSubTotal <= 0) {
            saleSubTotalEdit.setError("Sub Total has to be greater than 0");
            saleSubTotalEdit.requestFocus();
            return;
        }
        if (saleTotalPrice <= 0) {
            saleTotalPriceEdit.setError("Total has to be greater than 0");
            saleTotalPriceEdit.requestFocus();
            return;
        }

        String message, title;
        if (isAddOrEdit) {
            title = "Save";
            message = "Check again, Once saved sale items cannot be changed";
        } else {
            title = "Update";
            message = "Check again, Once update old data will be replaced";
        }


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Sale sale = new Sale();

                        sale.setSaleId(saleId + "");
                        sale.setSaleDate(saleDateView.getText().toString());
                        sale.setSaleTime(saleTimeView.getText().toString());
                        sale.setSaleSubTotal(saleSubTotal);
                        sale.setSaleTax(saleTax + "");
                        sale.setSaleDiscount(saleDiscount + "");
                        sale.setSaleTotalPrice(saleTotalPrice);

                        sale.setSaleItems(saleItems);
                        FirebaseDb.saveSale(sale);

                        Toast.makeText(AddSaleActivity.this,
                                "Sale saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).create();

        alertDialog.show();

    }


}
