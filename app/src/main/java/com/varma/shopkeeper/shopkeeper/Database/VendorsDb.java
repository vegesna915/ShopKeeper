package com.varma.shopkeeper.shopkeeper.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.varma.shopkeeper.shopkeeper.Objects.Vendor;

import java.util.ArrayList;

public class VendorsDb {

    static final String TABLE_VENDORS_DETAILS = "VENDORS_DETAILS_TABLE";
    static final String VENDOR_ID = "VENDOR_ID";
    static final String VENDOR_NAME = "VENDOR_NAME";
    static final String VENDOR_PHONE = "VENDOR_PHONE";
    static final String VENDOR_EMAIL = "VENDOR_EMAIL";
    static final String VENDOR_STREET = "VENDOR_STREET";
    static final String VENDOR_CITY = "VENDOR_CITY";
    static final String VENDOR_STATE = "VENDOR_STATE";
    static final String VENDOR_COUNTRY = "VENDOR_COUNTRY";
    static final String VENDOR_ZIP = "VENDOR_ZIP";


    private Context context;

    public VendorsDb(Context context) {
        this.context = context;
    }

    public long addVendor(Vendor vendor) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(VENDOR_ID, vendor.getVendorId());
        values.put(VENDOR_NAME, vendor.getVendorName());
        values.put(VENDOR_PHONE, vendor.getVendorPhone());
        values.put(VENDOR_EMAIL, vendor.getVendorEmail());
        values.put(VENDOR_STREET, vendor.getVendorStreet());
        values.put(VENDOR_CITY, vendor.getVendorCity());
        values.put(VENDOR_STATE, vendor.getVendorState());
        values.put(VENDOR_COUNTRY, vendor.getVendorCountry());
        values.put(VENDOR_ZIP, vendor.getVendorZip());

        // Inserting Row
        long row = db.insert(TABLE_VENDORS_DETAILS, null, values);
        db.close();

        return row;

    }

    public void addAllVendors(ArrayList<Vendor> vendors){

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (Vendor vendor:vendors){
            values.put(VENDOR_ID, vendor.getVendorId());
            values.put(VENDOR_NAME, vendor.getVendorName());
            values.put(VENDOR_PHONE, vendor.getVendorPhone());
            values.put(VENDOR_EMAIL, vendor.getVendorEmail());
            values.put(VENDOR_STREET, vendor.getVendorStreet());
            values.put(VENDOR_CITY, vendor.getVendorCity());
            values.put(VENDOR_STATE, vendor.getVendorState());
            values.put(VENDOR_COUNTRY, vendor.getVendorCountry());
            values.put(VENDOR_ZIP, vendor.getVendorZip());

            db.insert(TABLE_VENDORS_DETAILS, null, values);
        }

        // Inserting Row

        db.close();

    }


    public ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> vendors = new ArrayList<>();

        Vendor vendor;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_VENDORS_DETAILS, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                vendor = new Vendor();

                vendor.setVendorId(cursor.getString(cursor.getColumnIndex(VENDOR_ID)));
                vendor.setVendorName(cursor.getString(cursor.getColumnIndex(VENDOR_NAME)));
                vendor.setVendorPhone(cursor.getString(cursor.getColumnIndex(VENDOR_PHONE)));
                vendor.setVendorEmail(cursor.getString(cursor.getColumnIndex(VENDOR_EMAIL)));
                vendor.setVendorStreet(cursor.getString(cursor.getColumnIndex(VENDOR_STREET)));
                vendor.setVendorCity(cursor.getString(cursor.getColumnIndex(VENDOR_CITY)));
                vendor.setVendorState(cursor.getString(cursor.getColumnIndex(VENDOR_STATE)));
                vendor.setVendorCountry(cursor.getString(cursor.getColumnIndex(VENDOR_COUNTRY)));
                vendor.setVendorZip(cursor.getString(cursor.getColumnIndex(VENDOR_ZIP)));

                vendors.add(vendor);
            }
            cursor.close();
        }
        db.close();


        return vendors;
    }



    public Vendor getVendorById(String id) {

        Vendor vendor = new Vendor();

        // Select All Query
        String where = VENDOR_ID + " = ?";
        String[] selectionArgs = {id};

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_VENDORS_DETAILS, null, where, selectionArgs, null, null, null, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {

                vendor.setVendorId(cursor.getString(cursor.getColumnIndex(VENDOR_ID)));
                vendor.setVendorName(cursor.getString(cursor.getColumnIndex(VENDOR_NAME)));
                vendor.setVendorPhone(cursor.getString(cursor.getColumnIndex(VENDOR_PHONE)));
                vendor.setVendorEmail(cursor.getString(cursor.getColumnIndex(VENDOR_EMAIL)));
                vendor.setVendorStreet(cursor.getString(cursor.getColumnIndex(VENDOR_STREET)));
                vendor.setVendorCity(cursor.getString(cursor.getColumnIndex(VENDOR_CITY)));
                vendor.setVendorState(cursor.getString(cursor.getColumnIndex(VENDOR_STATE)));
                vendor.setVendorCountry(cursor.getString(cursor.getColumnIndex(VENDOR_COUNTRY)));
                vendor.setVendorZip(cursor.getString(cursor.getColumnIndex(VENDOR_ZIP)));
            }
            cursor.close();
        }
        db.close();

        return vendor;
    }

    public long updateVendorById(Vendor vendor) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(VENDOR_NAME, vendor.getVendorName());
        values.put(VENDOR_PHONE, vendor.getVendorPhone());
        values.put(VENDOR_EMAIL, vendor.getVendorEmail());
        values.put(VENDOR_STREET, vendor.getVendorStreet());
        values.put(VENDOR_CITY, vendor.getVendorCity());
        values.put(VENDOR_STATE, vendor.getVendorState());
        values.put(VENDOR_COUNTRY, vendor.getVendorCountry());
        values.put(VENDOR_ZIP, vendor.getVendorZip());


        long row = db.update(TABLE_VENDORS_DETAILS, values, VENDOR_ID + " =?", new String[]{vendor.getVendorId()});
        db.close();

        return row;

    }


    public boolean deleteVendorById(String id) {

        int deleted;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        deleted = db.delete(TABLE_VENDORS_DETAILS, VENDOR_ID + " = ?", new String[]{id});

        db.close();

        return deleted > 0;
    }



}
