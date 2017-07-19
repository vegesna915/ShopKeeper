package com.varma.shopkeeper.shopkeeper.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "StoreManager.db";

    DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ITEM_DETAILS_TABLE = "CREATE TABLE " + ItemsDb.TABLE_ITEM_DETAILS
                + "("
                + ItemsDb.ITEM_CODE + " TEXT PRIMARY KEY,"
                + ItemsDb.ITEM_NAME + " TEXT,"
                + ItemsDb.ITEM_BRAND_NAME + " TEXT,"
                + ItemsDb.ITEM_SIZE + " TEXT,"
                + ItemsDb.ITEM_ACTUAL_PRICE + " TEXT,"
                + ItemsDb.ITEM_PROFIT + " TEXT,"
                + ItemsDb.ITEM_TAX + " TEXT,"
                + ItemsDb.ITEM_OTHER_TAX + " TEXT,"
                + ItemsDb.ITEM_SALE_PRICE + " TEXT,"
                + ItemsDb.ITEM_IS_UPDATED_TO_SERVER + " TEXT"
                + ")";
        db.execSQL(CREATE_ITEM_DETAILS_TABLE);

        String CREATE_VENDOR_DETAILS_TABLE = "CREATE TABLE " + VendorsDb.TABLE_VENDORS_DETAILS
                + "("
                + VendorsDb.VENDOR_ID + " TEXT PRIMARY KEY,"
                + VendorsDb.VENDOR_NAME + " TEXT UNIQUE,"
                + VendorsDb.VENDOR_PHONE + " TEXT,"
                + VendorsDb.VENDOR_EMAIL + " TEXT,"
                + VendorsDb.VENDOR_STREET + " TEXT,"
                + VendorsDb.VENDOR_CITY + " TEXT,"
                + VendorsDb.VENDOR_STATE + " TEXT,"
                + VendorsDb.VENDOR_COUNTRY + " TEXT,"
                + VendorsDb.VENDOR_ZIP + " TEXT"
                + ")";
        db.execSQL(CREATE_VENDOR_DETAILS_TABLE);

        String CREATE_INVOICE_ITEM_DETAILS_TABLE = "CREATE TABLE " + InvoiceItemDb.TABLE_INVOICE_ITEM_DETAILS
                + "("
                + InvoiceItemDb.ITEM_ID + " TEXT PRIMARY KEY,"
                + InvoiceItemDb.INVOICE_ID + " TEXT,"
                + InvoiceItemDb.ITEM_NAME + " TEXT,"
                + InvoiceItemDb.ITEM_BRAND_NAME + " TEXT,"
                + InvoiceItemDb.ITEM_SIZE + " TEXT,"
                + InvoiceItemDb.ITEM_UNIT_PRICE + " TEXT,"
                + InvoiceItemDb.ITEM_QTY + " TEXT,"
                + InvoiceItemDb.ITEM_PRICE + " TEXT"
                + ")";
        db.execSQL(CREATE_INVOICE_ITEM_DETAILS_TABLE);


        String CREATE_PURCHASE_INVOICE_DETAILS_TABLE = "CREATE TABLE " + PurchaseInvoiceDb.TABLE_PURCHASE_INVOICE_DETAILS
                + "("
                + PurchaseInvoiceDb.INVOICE_ID + " TEXT PRIMARY KEY,"
                + PurchaseInvoiceDb.INVOICE_NUMBER + " TEXT,"
                + PurchaseInvoiceDb.INVOICE_PO_NUMBER + " TEXT,"
                + PurchaseInvoiceDb.INVOICE_DATE + " TEXT,"
                + PurchaseInvoiceDb.INVOICE_SUB_TOTAL + " TEXT,"
                + PurchaseInvoiceDb.INVOICE_TAX + " TEXT,"
                + PurchaseInvoiceDb.INVOICE_DISCOUNT + " TEXT,"
                + PurchaseInvoiceDb.INVOICE_TOTAL_PRICE + " TEXT,"
                + PurchaseInvoiceDb.VENDOR_NAME + " TEXT,"
                + PurchaseInvoiceDb.VENDOR_PHONE + " TEXT,"
                + PurchaseInvoiceDb.VENDOR_EMAIL + " TEXT,"
                + PurchaseInvoiceDb.VENDOR_ADDRESS + " TEXT"
                + ")";
        db.execSQL(CREATE_PURCHASE_INVOICE_DETAILS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ItemsDb.TABLE_ITEM_DETAILS);

        // Create tables again
        onCreate(db);
    }
}
