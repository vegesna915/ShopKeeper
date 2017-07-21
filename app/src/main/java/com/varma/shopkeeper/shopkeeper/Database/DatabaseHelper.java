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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ItemsDb.TABLE_ITEM_DETAILS);

        // Create tables again
        onCreate(db);
    }
}
