package com.varma.shopkeeper.shopkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.Extras.Utilis;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.Vendor;

public class AddVendorActivity extends AppCompatActivity {


    private EditText vendorNameEdit, vendorPhoneEdit, vendorEmailEdit, vendorStreetEdit,
            vendorCityEdit, vendorStateEdit, vendorCountryEdit, vendorZipEdit;

    private String vendorName, vendorPhone, vendorEmail, vendorStreet, vendorCity, vendorState,
            vendorCountry, vendorZip;

    boolean isAddOrEdit;
    String editVendorId;

    Vendor vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddVendorActivity);
        setSupportActionBar(toolbar);

        isAddOrEdit = getIntent().getBooleanExtra(Constants.AddVendorActivity.isAdd_or_Edit,true);
        editVendorId = getIntent().getStringExtra(Constants.AddVendorActivity.editVendorId);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        declarations();

        if (!isAddOrEdit) {
            //VendorsDb vendorsDb = new VendorsDb(this);
           // vendor = vendorsDb.getVendorById(editVendorId);

            DatabaseReference reference = FirebaseDb.getVendorsDbReference().child(editVendorId);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    vendor = dataSnapshot.getValue(Vendor.class);

                    vendorNameEdit.setText(vendor.getVendorName());
                    vendorPhoneEdit.setText(vendor.getVendorPhone());
                    vendorEmailEdit.setText(vendor.getVendorEmail());
                    vendorStreetEdit.setText(vendor.getVendorStreet());
                    vendorCityEdit.setText(vendor.getVendorCity());
                    vendorStateEdit.setText(vendor.getVendorState());
                    vendorCountryEdit.setText(vendor.getVendorCountry());
                    vendorZipEdit.setText(vendor.getVendorZip());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            reference.addListenerForSingleValueEvent(valueEventListener);
            refreshInputs();
        }else{
            vendor = new Vendor();
        }

    }

    private void declarations() {

        vendorNameEdit = (EditText) findViewById(R.id.vendorName_addVendorActivity);
        vendorPhoneEdit = (EditText) findViewById(R.id.vendorPhoneNumber_addVendorActivity);
        vendorEmailEdit = (EditText) findViewById(R.id.vendorEmail_addVendorActivity);
        vendorStreetEdit = (EditText) findViewById(R.id.vendorStreet_addVendorActivity);
        vendorCityEdit = (EditText) findViewById(R.id.vendorTown_addVendorActivity);
        vendorStateEdit = (EditText) findViewById(R.id.vendorState_addVendorActivity);
        vendorCountryEdit = (EditText) findViewById(R.id.vendorCountry_addVendorActivity);
        vendorZipEdit = (EditText) findViewById(R.id.vendorZipCode_addVendorActivity);

        Button saveButton = (Button) findViewById(R.id.saveVendorButton_addVendorActivity);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilis.closeKeyboard(AddVendorActivity.this, v);
                onClickSaveButton();
            }

        });


        View linearLayout = findViewById(R.id.linearLayout_main_addVendorActivity);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Utilis.closeKeyboard(AddVendorActivity.this, v2);
            }
        });

    }


    private void refreshInputs() {
        vendorName = vendorNameEdit.getText().toString();
        vendorPhone = vendorPhoneEdit.getText().toString();
        vendorEmail = vendorEmailEdit.getText().toString();
        vendorStreet = vendorStreetEdit.getText().toString();
        vendorCity = vendorCityEdit.getText().toString();
        vendorState = vendorStateEdit.getText().toString();
        vendorCountry = vendorCountryEdit.getText().toString();
        vendorZip = vendorZipEdit.getText().toString();
    }
    private void onClickSaveButton() {

        refreshInputs();


        if (vendorName.equals("")) {
            vendorNameEdit.setError("Enter Name");
            vendorNameEdit.requestFocus();
            return;
        }
        if (vendorPhone.equals("")) {
            vendorPhoneEdit.setError("Enter Phone");
            vendorPhoneEdit.requestFocus();
            return;
        }
        if (vendorEmail.equals("")) {
            vendorEmailEdit.setError("Enter Email");
            vendorEmailEdit.requestFocus();
            return;
        }
        if (vendorStreet.equals("")) {
            vendorStreetEdit.setError("Enter Street/P0 box");
            vendorStreetEdit.requestFocus();
            return;
        }
        if (vendorCity.equals("")) {
            vendorCityEdit.setError("Enter Town/City");
            vendorCityEdit.requestFocus();
            return;
        }
        if (vendorState.equals("")) {
            vendorStateEdit.setError("Enter State");
            vendorStateEdit.requestFocus();
            return;
        }
        if (vendorCountry.equals("")) {
            vendorCountryEdit.setError("Enter Vendor Name");
            vendorCountryEdit.requestFocus();
            return;
        }
        if (vendorZip.equals("")) {
            vendorZipEdit.setError("Enter Vendor Name");
            vendorZipEdit.requestFocus();
            return;
        }


        vendor.setVendorName(vendorName);
        vendor.setVendorPhone(vendorPhone);
        vendor.setVendorEmail(vendorEmail);
        vendor.setVendorStreet(vendorStreet);
        vendor.setVendorCity(vendorCity);
        vendor.setVendorState(vendorState);
        vendor.setVendorCountry(vendorCountry);
        vendor.setVendorZip(vendorZip);

        if (isAddOrEdit) {
            vendor.setVendorId(System.currentTimeMillis() + "");
            FirebaseDb.saveVendor(vendor);
            Toast.makeText(this, "Vendor added successfully", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDb.saveVendor(vendor);
            Toast.makeText(this, "Vendor updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}
