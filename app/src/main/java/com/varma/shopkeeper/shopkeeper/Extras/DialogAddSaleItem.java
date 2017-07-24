package com.varma.shopkeeper.shopkeeper.Extras;


import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.AddSaleActivity;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.SaleItem;
import com.varma.shopkeeper.shopkeeper.R;

import java.util.ArrayList;
import java.util.HashSet;

public class DialogAddSaleItem {

    private AddSaleActivity activity;
    private EditText saleItemQtyEdit, saleItemPriceEdit;
    private Spinner saleItemBrandName, saleItemName, saleItemUnitPriceEdit, saleItemSize;
    private ArrayAdapter<String> adapterBrandName;
    private ArrayAdapter<String> adapterNames;
    private ArrayAdapter<String> adapterSizes;
    private ArrayAdapter<String> adapterPrices;
    private String itemBrand = "";
    private String itemName = "";
    private String itemSize = "";
    private int itemUnitPrice = 0;
    private int itemQty = 0;
    private int itemPrice = 0;
    private ArrayList<String> brands = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> sizes = new ArrayList<>();
    private ArrayList<String> prices = new ArrayList<>();

    public DialogAddSaleItem(AddSaleActivity activity) {
        this.activity = activity;
    }

    public Dialog getAddSaleItemDialog() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.custom_add_sale_item_dailog);
        dialog.setTitle("Add Item");

        saleItemName = (Spinner) dialog.findViewById(R.id.saleName_addSaleActivity);
        saleItemBrandName = (Spinner) dialog.findViewById(R.id.saleBrand_addSaleActivity);
        saleItemSize = (Spinner) dialog.findViewById(R.id.saleSize_addSaleActivity);
        saleItemUnitPriceEdit = (Spinner) dialog.findViewById(R.id.saleUnitPrice_addSaleActivity);
        saleItemQtyEdit = (EditText) dialog.findViewById(R.id.saleQty_addSaleActivity);
        saleItemPriceEdit = (EditText) dialog.findViewById(R.id.salePrice_addSaleActivity);

        setSpinners();

        TextWatcher textWatcherPrice = getTextWatcherPrice();

        saleItemQtyEdit.addTextChangedListener(textWatcherPrice);

        Button cancelButton = (Button) dialog.findViewById(R.id.cancelSaleItemButton_addSaleActivity);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button addButton = (Button) dialog.findViewById(R.id.addSaleItemButton_addSaleActivity);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();

                if (itemQty == 0) {
                    saleItemQtyEdit.setError("Quantity can't be 0");
                    return;
                }
                SaleItem saleItem = new SaleItem();
                saleItem.setItemName(itemName);
                saleItem.setItemBrand(itemBrand);
                saleItem.setItemSize(itemSize);
                saleItem.setItemUnitPrice(itemUnitPrice);
                saleItem.setItemQty(itemQty);
                saleItem.setItemPrice(itemPrice);
                saleItem.setItemId(System.currentTimeMillis() + "");

                activity.addSaleItem(saleItem);

                dialog.dismiss();

            }
        });
        return dialog;
    }


    private TextWatcher getTextWatcherPrice() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                getValues();

                itemPrice = itemQty * (Integer.parseInt(
                        prices.get(saleItemUnitPriceEdit.getSelectedItemPosition())));

                saleItemPriceEdit.setText(itemPrice + "");

            }
        };
    }

    private void getValues() {
        if (saleItemQtyEdit.getText().toString().equals("")) {
            itemQty = 1;
        } else {
            itemQty = Integer.parseInt(saleItemQtyEdit.getText().toString());
        }
    }

    private void getStockByBrands() {

        Query query = FirebaseDb.getCurrentStockDbReference().orderByChild("itemBrandName");
        query.keepSynced(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                brands.clear();
                String temp = "";

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String brand = snap.child("itemBrandName").getValue(String.class);
                    if (!temp.equals(brand)) {
                        brands.add(brand);
                        temp = brand;
                    }
                }

                adapterBrandName.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setSpinners() {

        adapterBrandName = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, brands);
        saleItemBrandName.setAdapter(adapterBrandName);
        getStockByBrands();

        adapterNames = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, names);
        saleItemName.setAdapter(adapterNames);
        adapterSizes = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, sizes);
        saleItemSize.setAdapter(adapterSizes);
        adapterPrices = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, prices);
        saleItemUnitPriceEdit.setAdapter(adapterPrices);

        saleItemBrandName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                itemBrand = brands.get(position);

                String itemId = itemBrand + "_";

                Query query = FirebaseDb.getCurrentStockDbReference()
                        .orderByChild("itemId").startAt(itemId).endAt(itemId + "\uf8ff");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        names.clear();
                        HashSet<String> temp = new HashSet<>();

                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            String name = snap.child("itemName").getValue(String.class);
                            if (!(temp.contains(name))) {
                                names.add(name);
                                temp.add(name);
                            }
                        }

                        adapterNames.notifyDataSetChanged();
                        temp.clear();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saleItemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                itemName = names.get(position);

                String itemId = itemBrand + "_" + itemName + "_";

                Query query = FirebaseDb.getCurrentStockDbReference()
                        .orderByChild("itemId").startAt(itemId).endAt(itemId + "\uf8ff");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        sizes.clear();
                        HashSet<String> temp = new HashSet<>();
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            String size = snap.child("itemSize").getValue(String.class);
                            if (!(temp.contains(size))) {
                                sizes.add(size);
                                temp.add(size);
                            }
                        }

                        adapterSizes.notifyDataSetChanged();
                        temp.clear();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saleItemSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {

                itemSize = sizes.get(position);

                String itemId = itemBrand + "_" + itemName + "_" + itemSize + "_";

                Query query = FirebaseDb.getCurrentStockDbReference()
                        .orderByChild("itemId").startAt(itemId).endAt(itemId + "\uf8ff");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        prices.clear();
                        HashSet<String> temp = new HashSet<>();
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            String price = snap.child("itemSellingPrice").getValue(String.class);
                            if (!(temp.contains(price))) {
                                prices.add(price);
                                temp.add(price);
                            }
                        }

                        adapterPrices.notifyDataSetChanged();
                        temp.clear();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saleItemUnitPriceEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getValues();
                itemUnitPrice = Integer.parseInt(prices.get(position));
                itemPrice = itemQty * itemUnitPrice;
                saleItemPriceEdit.setText(itemPrice + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
