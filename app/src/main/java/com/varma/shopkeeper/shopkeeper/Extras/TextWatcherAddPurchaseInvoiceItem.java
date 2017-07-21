package com.varma.shopkeeper.shopkeeper.Extras;


import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.varma.shopkeeper.shopkeeper.AddPurchaseInvoiceActivity;
import com.varma.shopkeeper.shopkeeper.Objects.InvoiceItem;
import com.varma.shopkeeper.shopkeeper.R;

public class TextWatcherAddPurchaseInvoiceItem {

    private AddPurchaseInvoiceActivity activity;
    private long invoiceId;
    private boolean ignore = false;


    private AutoCompleteTextView invoiceItemName, invoiceItemBrandName, invoiceItemSize;
    private EditText invoiceItemUnitPriceEdit, invoiceItemQtyEdit, invoiceItemPriceEdit,
            invoiceItemProfitEdit, invoiceItemSellingPriceEdit;
    private int invoiceItemUnitPrice, invoiceItemQty, invoiceItemPrice,
            invoiceItemProfit, invoiceItemSellingProfit;

    public TextWatcherAddPurchaseInvoiceItem(AddPurchaseInvoiceActivity activity, long invoiceId) {
        this.activity = activity;
        this.invoiceId = invoiceId;
    }

    public Dialog getAddItemDialog() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.custom_add_invoice_item_dialog);
        dialog.setTitle("Add Item");


        invoiceItemName = (AutoCompleteTextView) dialog.findViewById(R.id.itemName_addInvoiceItemActivity);
        invoiceItemBrandName = (AutoCompleteTextView) dialog.findViewById(R.id.itemBrand_addInvoiceItemActivity);
        invoiceItemSize = (AutoCompleteTextView) dialog.findViewById(R.id.itemSize_addInvoiceItemActivity);
        invoiceItemUnitPriceEdit = (EditText) dialog.findViewById(R.id.itemUnitPrice_addInvoiceItemActivity);
        invoiceItemQtyEdit = (EditText) dialog.findViewById(R.id.itemQty_addInvoiceItemActivity);
        invoiceItemPriceEdit = (EditText) dialog.findViewById(R.id.itemPrice_addInvoiceItemActivity);
        invoiceItemProfitEdit = (EditText) dialog.findViewById(R.id.itemProfit_addInvoiceItemActivity);
        invoiceItemSellingPriceEdit = (EditText) dialog.findViewById(R.id.itemSellingPrice_addInvoiceItemActivity);


        TextWatcher textWatcherPrice = getTextWatcherPrice();
        TextWatcher textWatcherProfit = getTextWatcherProfit();
        TextWatcher textWatcherSellingPrice = getTextWatcherSellingPrice();

        invoiceItemProfitEdit.addTextChangedListener(textWatcherProfit);
        invoiceItemSellingPriceEdit.addTextChangedListener(textWatcherSellingPrice);
        invoiceItemUnitPriceEdit.addTextChangedListener(textWatcherPrice);
        invoiceItemQtyEdit.addTextChangedListener(textWatcherPrice);

        Button cancelButton = (Button) dialog.findViewById(R.id.cancelItemButton_addInvoiceItemActivity);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button addButton = (Button) dialog.findViewById(R.id.addItemButton_addInvoiceItemActivity);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                if (invoiceItemName.getText().toString().equals("")) {
                    invoiceItemName.setError("Enter Item Name");
                    return;
                }
                if (invoiceItemBrandName.getText().toString().equals("")) {
                    invoiceItemBrandName.setError("Enter Brand Name");
                    return;
                }
                if (invoiceItemSize.getText().toString().equals("")) {
                    invoiceItemSize.setError("Enter Item Size");
                    return;
                }
                if (invoiceItemUnitPriceEdit.getText().toString().equals("")) {
                    invoiceItemUnitPriceEdit.setError("Enter Unit Price");
                    return;
                }
                if (invoiceItemProfitEdit.getText().toString().equals("")) {
                    invoiceItemProfitEdit.setError("Profit can't be empty");
                    return;
                }

                if (invoiceItemUnitPrice == 0) {
                    invoiceItemUnitPriceEdit.setError("Unit Price can't be 0");
                    return;
                }

                if (invoiceItemProfit <= 0) {
                    invoiceItemProfitEdit.setError("Profit should be above 0");
                    return;
                }

                if (invoiceItemQty == 0) {
                    invoiceItemQtyEdit.setError("Quantity can't be 0");
                    return;
                }

                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.setInvoiceId(invoiceId + "");
                invoiceItem.setItemName(invoiceItemName.getText().toString());
                invoiceItem.setItemBrandName(invoiceItemBrandName.getText().toString());
                invoiceItem.setItemSize(invoiceItemSize.getText().toString());
                invoiceItem.setItemUnitPrice((invoiceItemUnitPrice) + "");
                invoiceItem.setItemQty((long) invoiceItemQty);
                invoiceItem.setItemPrice(invoiceItemPrice + "");
                invoiceItem.setItemProfit(invoiceItemProfit + "");
                invoiceItem.setItemSellingPrice(invoiceItemSellingProfit + "");
                invoiceItem.setItemId(System.currentTimeMillis() + "");

                activity.addInvoiceItem(invoiceItem);

                dialog.dismiss();

            }
        });
        return dialog;
    }

    private TextWatcher getTextWatcherProfit() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ignore) {
                    ignore = false;
                    return;
                }
                ignore = true;
                getValues();
                invoiceItemSellingProfit = invoiceItemUnitPrice + invoiceItemProfit;
                invoiceItemSellingPriceEdit.setText(invoiceItemSellingProfit + "");
            }
        };

    }

    private TextWatcher getTextWatcherSellingPrice() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ignore) {
                    ignore = false;
                    return;
                }
                ignore = true;
                getValues();

                invoiceItemProfit = invoiceItemSellingProfit - invoiceItemUnitPrice;
                invoiceItemProfitEdit.setText(invoiceItemProfit + "");

            }
        };
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

                if (ignore) {
                    ignore = false;
                    return;
                }
                ignore = true;
                getValues();

                invoiceItemPrice = invoiceItemUnitPrice * invoiceItemQty;

                invoiceItemPriceEdit.setText(invoiceItemPrice + "");
                Toast.makeText(activity, invoiceItemUnitPrice + " - " + invoiceItemPrice, Toast.LENGTH_SHORT).show();
                if (invoiceItemProfit != 0) {
                    invoiceItemSellingProfit = invoiceItemUnitPrice + invoiceItemProfit;
                    invoiceItemSellingPriceEdit.setText(invoiceItemSellingProfit + "");
                }


            }
        };

    }

    private void getValues() {


        if (invoiceItemProfitEdit.getText().toString().equals("")) {
            invoiceItemProfit = 0;
        } else {
            invoiceItemProfit = Integer.parseInt(invoiceItemProfitEdit.getText().toString());
        }

        if (invoiceItemSellingPriceEdit.getText().toString().equals("")) {
            invoiceItemSellingProfit = 0;
        } else {
            invoiceItemSellingProfit = Integer.parseInt(invoiceItemSellingPriceEdit.getText().toString());
        }

        if (invoiceItemUnitPriceEdit.getText().toString().equals("")) {
            invoiceItemUnitPrice = 0;
        } else {
            invoiceItemUnitPrice = Integer.parseInt(invoiceItemUnitPriceEdit.getText().toString());
        }

        if (invoiceItemQtyEdit.getText().toString().equals("")) {
            invoiceItemQty = 1;
        } else {
            invoiceItemQty = Integer.parseInt(invoiceItemQtyEdit.getText().toString());
        }

    }


}
