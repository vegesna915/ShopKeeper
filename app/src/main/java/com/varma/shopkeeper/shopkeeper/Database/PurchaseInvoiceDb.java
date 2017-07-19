package com.varma.shopkeeper.shopkeeper.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.varma.shopkeeper.shopkeeper.Objects.PurchaseInvoice;

import java.util.ArrayList;

public class PurchaseInvoiceDb {

    static final String TABLE_PURCHASE_INVOICE_DETAILS = "TABLE_PURCHASE_INVOICE_DETAILS";

    static final String INVOICE_ID = "INVOICE_ID";
    static final String INVOICE_NUMBER = "INVOICE_NUMBER";
    static final String INVOICE_PO_NUMBER = "INVOICE_PO_NUMBER";
    static final String INVOICE_DATE = "INVOICE_DATE";
    static final String INVOICE_SUB_TOTAL = "INVOICE_SUB_TOTAL";
    static final String INVOICE_TAX = "INVOICE_TAX";
    static final String INVOICE_DISCOUNT = "INVOICE_DISCOUNT";
    static final String INVOICE_TOTAL_PRICE = "INVOICE_TOTAL_PRICE";
    static final String VENDOR_NAME = "VENDOR_NAME";
    static final String VENDOR_PHONE = "VENDOR_PHONE";
    static final String VENDOR_EMAIL = "VENDOR_EMAIL";
    static final String VENDOR_ADDRESS = "VENDOR_ADDRESS";

    private Context context;

    public PurchaseInvoiceDb(Context context) {
        this.context = context;
    }

    public long addInvoiceItem(PurchaseInvoice invoice) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(INVOICE_ID, invoice.getInvoiceId());
        values.put(INVOICE_NUMBER, invoice.getInvoiceNumber());
        values.put(INVOICE_PO_NUMBER, invoice.getInvoicePONumber());
        values.put(INVOICE_DATE, invoice.getInvoiceDate());
        values.put(INVOICE_SUB_TOTAL, invoice.getInvoiceSubTotal());
        values.put(INVOICE_TAX, invoice.getInvoiceTax());
        values.put(INVOICE_DISCOUNT, invoice.getInvoiceDiscount());
        values.put(INVOICE_TOTAL_PRICE, invoice.getInvoiceTotalPrice());
        values.put(VENDOR_NAME, invoice.getVendorName());
        values.put(VENDOR_PHONE, invoice.getVendorPhone());
        values.put(VENDOR_EMAIL, invoice.getVendorEmail());
        values.put(VENDOR_ADDRESS, invoice.getVendorAddress());

        // Inserting Row
        long row = db.insert(TABLE_PURCHASE_INVOICE_DETAILS, null, values);
        db.close();

        return row;

    }


}
