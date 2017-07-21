package com.varma.shopkeeper.shopkeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.varma.shopkeeper.shopkeeper.Database.ItemsDb;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.Extras.Utilis;
import com.varma.shopkeeper.shopkeeper.Objects.Item;

public class AddItemActivity extends AppCompatActivity {


    EditText itemNameEdit, itemBrandNameEdit, itemSizeEdit,
            itemActualPriceEdit, itemProfitEdit, itemTaxEdit, itemOtherTaxEdit, itemSalePriceEdit;

    String itemName, itemBrandName, itemSize;

    float  itemActualPrice, itemProfit, itemTax, itemOtherTax, itemSalePrice;

    boolean isAddOrEdit;
    String editItemId = "";

    Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddItemActivity);
        setSupportActionBar(toolbar);

        isAddOrEdit = getIntent().getBooleanExtra(Constants.AddItemsActivity.isAdd_or_Edit,true);


        editItemId = getIntent().getStringExtra(Constants.AddItemsActivity.editItemId);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        declarations();

        if(!isAddOrEdit){

            ItemsDb  itemsDb = new ItemsDb(this);
            item = itemsDb.getItemById(editItemId);

            itemNameEdit.setText(item.getItemName());
            itemBrandNameEdit.setText(item.getItemBrandName());
            itemSizeEdit.setText(item.getItemSize());
            itemActualPriceEdit.setText(item.getItemActualPrice());
            itemProfitEdit.setText(item.getItemProfit());
            itemTaxEdit.setText(item.getItemTax());
            itemOtherTaxEdit.setText(item.getItemOtherTax());

            refreshInputs();
        }else{
            item = new Item();
        }


        Utilis.closeKeyboard(this,toolbar);
    }


    private void declarations() {

        itemNameEdit = (EditText) findViewById(R.id.itemName_addItemActivity);
        itemBrandNameEdit = (EditText) findViewById(R.id.itemBrand_addItemActivity);
        itemSizeEdit = (EditText) findViewById(R.id.itemSize_addItemActivity);
        itemActualPriceEdit = (EditText) findViewById(R.id.itemActualPrice_addItemActivity);
        itemProfitEdit = (EditText) findViewById(R.id.itemProfit_addItemActivity);
        itemTaxEdit = (EditText) findViewById(R.id.itemTax_addItemActivity);
        itemOtherTaxEdit = (EditText) findViewById(R.id.itemOtherTax_addItemActivity);
        itemSalePriceEdit = (EditText) findViewById(R.id.itemSalePrice_addItemActivity);


        Button saveButton = (Button) findViewById(R.id.saveItemButton_addItemActivity);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilis.closeKeyboard(AddItemActivity.this,v);
                saveButtonClicked();

            }
        });

        View linearLayout = findViewById(R.id.linearLayout_main_addItemActivity);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Utilis.closeKeyboard(AddItemActivity.this, v2);
            }
        });


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


        itemActualPriceEdit.addTextChangedListener(textWatcher);
        itemProfitEdit.addTextChangedListener(textWatcher);
        itemTaxEdit.addTextChangedListener(textWatcher);
        itemOtherTaxEdit.addTextChangedListener(textWatcher);

    }

    private void refreshInputs() {

        if (itemActualPriceEdit.getText().toString().equals("")) {
            itemActualPrice = 0;
        } else {
            itemActualPrice = Float.parseFloat(itemActualPriceEdit.getText().toString());
        }

        if (itemProfitEdit.getText().toString().equals("")) {
            itemProfit = 0;
        } else {
            itemProfit = Float.parseFloat(itemProfitEdit.getText().toString());
        }

        if (itemTaxEdit.getText().toString().equals("")) {
            itemTax = 0;
        } else {
            itemTax = Float.parseFloat(itemTaxEdit.getText().toString());
        }

        if (itemOtherTaxEdit.getText().toString().equals("")) {
            itemOtherTax = 0;
        } else {
            itemOtherTax = Float.parseFloat(itemOtherTaxEdit.getText().toString());
        }

        itemSalePrice = itemActualPrice + itemProfit + (itemActualPrice * itemTax / 100) + (itemActualPrice * itemOtherTax / 100);
        itemSalePriceEdit.setText(itemSalePrice + "");

    }

    private void saveButtonClicked() {

        refreshInputs();


        itemName = itemNameEdit.getText().toString();
        itemBrandName = itemBrandNameEdit.getText().toString();
        itemSize = itemSizeEdit.getText().toString();


        if (itemName.equals("")) {
            itemNameEdit.setError("Enter Item Name");
            itemNameEdit.requestFocus();
            return;
        }
        if (itemBrandName.equals("")) {
            itemBrandNameEdit.setError("Enter Brand Name");
            itemBrandNameEdit.requestFocus();
            return;
        }


        if (itemSalePrice == 0) {
            itemSalePriceEdit.setError("Item Sale price can't be 0");
            itemSalePriceEdit.requestFocus();
            return;
        }





        item.setItemName(itemName);
        item.setItemBrandName(itemBrandName);
        item.setItemSize(itemSize);
        item.setItemActualPrice(itemActualPrice + "");
        item.setItemProfit(itemProfit + "");
        item.setItemTax(itemTax + "");
        item.setItemOtherTax(itemOtherTax + "");
        item.setItemSalePrice(itemSalePrice + "");
        item.setItemIsUpdatedToSever("0");

        ItemsDb itemDb = new ItemsDb(this);

        long row;

        if (isAddOrEdit) {

            item.setItemCode(System.currentTimeMillis() + "");
            row = itemDb.addItem(item);

            if (row < 0) {
                Toast.makeText(this, "Item entry failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            row = itemDb.updateItemById(item);

            if (row < 0) {
                Toast.makeText(this, "Item Update Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

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
