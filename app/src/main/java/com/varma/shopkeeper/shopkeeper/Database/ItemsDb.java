package com.varma.shopkeeper.shopkeeper.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.varma.shopkeeper.shopkeeper.Objects.Item;

import java.util.ArrayList;

public class ItemsDb {

    static final String TABLE_ITEM_DETAILS = "ITEM_DETAILS_TABLE";
    static final String ITEM_CODE = "ITEM_CODE";
    static final String ITEM_NAME = "ITEM_NAME";
    static final String ITEM_BRAND_NAME = "ITEM_BRAND_NAME";
    static final String ITEM_SIZE = "ITEM_SIZE";
    static final String ITEM_ACTUAL_PRICE = "ITEM_ACTUAL_PRICE";
    static final String ITEM_PROFIT = "ITEM_PROFIT";
    static final String ITEM_TAX = "ITEM_TAX";
    static final String ITEM_OTHER_TAX = "ITEM_OTHER_TAX";
    static final String ITEM_SALE_PRICE = "ITEM_SALE_PRICE";
    static final String ITEM_IS_UPDATED_TO_SERVER = "ITEM_IS_UPDATED_TO_SERVER";


    private Context context;

    public ItemsDb(Context context) {
        this.context = context;
    }

    public long addItem(Item item) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_CODE, item.getItemCode());
        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_BRAND_NAME, item.getItemBrandName());
        values.put(ITEM_SIZE, item.getItemSize());
        values.put(ITEM_ACTUAL_PRICE, item.getItemActualPrice());
        values.put(ITEM_PROFIT, item.getItemProfit());
        values.put(ITEM_TAX, item.getItemTax());
        values.put(ITEM_OTHER_TAX, item.getItemOtherTax());
        values.put(ITEM_SALE_PRICE, item.getItemSalePrice());
        values.put(ITEM_IS_UPDATED_TO_SERVER, item.getItemIsUpdatedToSever());

        // Inserting Row
        long row = db.insert(TABLE_ITEM_DETAILS, null, values);
        db.close();

        return row;

    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();

        Item item;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ITEM_DETAILS, null, null, null, null, null, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                item = new Item();


                item.setItemCode(cursor.getString(cursor.getColumnIndex(ITEM_CODE)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(ITEM_NAME)));
                item.setItemBrandName(cursor.getString(cursor.getColumnIndex(ITEM_BRAND_NAME)));
                item.setItemSize(cursor.getString(cursor.getColumnIndex(ITEM_SIZE)));
                item.setItemActualPrice(cursor.getString(cursor.getColumnIndex(ITEM_ACTUAL_PRICE)));
                item.setItemProfit(cursor.getString(cursor.getColumnIndex(ITEM_PROFIT)));
                item.setItemTax(cursor.getString(cursor.getColumnIndex(ITEM_TAX)));
                item.setItemOtherTax(cursor.getString(cursor.getColumnIndex(ITEM_OTHER_TAX)));
                item.setItemSalePrice(cursor.getString(cursor.getColumnIndex(ITEM_SALE_PRICE)));
                item.setItemIsUpdatedToSever(cursor.getString(cursor.getColumnIndex(ITEM_IS_UPDATED_TO_SERVER)));
                items.add(item);
            }
            cursor.close();
        }
        db.close();


        return items;
    }

    public Item getItemById(String id) {

        Item item = new Item();

        // Select All Query
        String where = ITEM_CODE + " = ?";
        String[] selectionArgs = {id};

        Log.e("id", id);

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ITEM_DETAILS, null, where, selectionArgs, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor != null) {

            while (cursor.moveToNext()) {

                item.setItemCode(cursor.getString(cursor.getColumnIndex(ITEM_CODE)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(ITEM_NAME)));
                item.setItemBrandName(cursor.getString(cursor.getColumnIndex(ITEM_BRAND_NAME)));
                item.setItemSize(cursor.getString(cursor.getColumnIndex(ITEM_SIZE)));
                item.setItemActualPrice(cursor.getString(cursor.getColumnIndex(ITEM_ACTUAL_PRICE)));
                item.setItemProfit(cursor.getString(cursor.getColumnIndex(ITEM_PROFIT)));
                item.setItemTax(cursor.getString(cursor.getColumnIndex(ITEM_TAX)));
                item.setItemOtherTax(cursor.getString(cursor.getColumnIndex(ITEM_OTHER_TAX)));
                item.setItemSalePrice(cursor.getString(cursor.getColumnIndex(ITEM_SALE_PRICE)));
                item.setItemIsUpdatedToSever(cursor.getString(cursor.getColumnIndex(ITEM_IS_UPDATED_TO_SERVER)));


            }
            cursor.close();
        }
        db.close();

        return item;
    }

    public long updateItemById(Item item) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_BRAND_NAME, item.getItemBrandName());
        values.put(ITEM_SIZE, item.getItemSize());
        values.put(ITEM_ACTUAL_PRICE, item.getItemActualPrice());
        values.put(ITEM_PROFIT, item.getItemProfit());
        values.put(ITEM_TAX, item.getItemTax());
        values.put(ITEM_OTHER_TAX, item.getItemOtherTax());
        values.put(ITEM_SALE_PRICE, item.getItemSalePrice());
        values.put(ITEM_IS_UPDATED_TO_SERVER, item.getItemIsUpdatedToSever());


        long row = db.update(TABLE_ITEM_DETAILS, values, ITEM_CODE + " =?", new String[]{item.getItemCode()});
        db.close();

        return row;

    }

    public boolean deleteItemById(String id) {

        int deleted;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        deleted = db.delete(TABLE_ITEM_DETAILS, ITEM_CODE + " = ?", new String[]{id});

        db.close();

        return deleted > 0;
    }


}
