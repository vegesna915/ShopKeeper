package com.varma.shopkeeper.shopkeeper.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.varma.shopkeeper.shopkeeper.Objects.InvoiceItem;

public class InvoiceItemDb {


    static final String TABLE_INVOICE_ITEM_DETAILS = "TABLE_INVOICE_ITEM_DETAILS";
    static final String ITEM_ID = "ITEM_ID";
    static final String INVOICE_ID = "INVOICE_ID";
    static final String ITEM_NAME = "ITEM_NAME";
    static final String ITEM_BRAND_NAME = "ITEM_BRAND_NAME";
    static final String ITEM_SIZE = "ITEM_SIZE";
    static final String ITEM_UNIT_PRICE = "ITEM_UNIT_PRICE";
    static final String ITEM_QTY = "ITEM_QTY";
    static final String ITEM_PRICE = "ITEM_PRICE";



    private Context context;

    public InvoiceItemDb(Context context) {
        this.context = context;
    }

    public long addInvoiceItem(InvoiceItem item) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_ID, item.getItemId());
        values.put(INVOICE_ID, item.getInvoiceId());
        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_BRAND_NAME, item.getItemBrandName());
        values.put(ITEM_SIZE, item.getItemSize());
        values.put(ITEM_UNIT_PRICE, item.getItemUnitPrice());
        values.put(ITEM_QTY, item.getItemQty());
        values.put(ITEM_PRICE, item.getItemPrice());

        // Inserting Row
        long row = db.insert(TABLE_INVOICE_ITEM_DETAILS, null, values);
        db.close();

        return row;

    }


}
