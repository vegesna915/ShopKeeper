package com.varma.shopkeeper.shopkeeper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_AddPurchaseInvoice;
import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.Extras.DialogAddPurchaseInvoiceItem;
import com.varma.shopkeeper.shopkeeper.Extras.Utilis;
import com.varma.shopkeeper.shopkeeper.FirebaseDb.FirebaseDb;
import com.varma.shopkeeper.shopkeeper.Objects.InvoiceItem;
import com.varma.shopkeeper.shopkeeper.Objects.PurchaseInvoice;
import com.varma.shopkeeper.shopkeeper.Objects.Vendor;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class AddPurchaseInvoiceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    RecyclerViewAdapter_AddPurchaseInvoice adapterRecyclerView;
    ArrayAdapter<String> adapterVendorName;
    ArrayList<Vendor> vendors = new ArrayList<>();
    ArrayList<String> vendorNames = new ArrayList<>();
    ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
    float invoiceSubTotal = 0;
    float invoiceTax, invoiceDiscount;
    private EditText vendorPhoneEdit, vendorEmailEdit, vendorAddressEdit, invoiceNumberEdit,
            invoicePONUmberEdit, invoiceSubTotalEdit, invoiceTaxEdit, invoiceDiscountEdit,invoiceTotalPriceEdit;
    private AutoCompleteTextView vendorNameEdit;
    private Button invoiceDateEdit;
    private String  invoiceDate;
    private long invoiceId, invoiceTotalPrice;

    private boolean isAddOrEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase_invoice);

        isAddOrEdit = getIntent().getBooleanExtra(Constants.AddPurchaseInvoiceActivity.isAdd_or_Edit, true);

        if (isAddOrEdit) {
            invoiceId = System.currentTimeMillis();
        } else {
            invoiceId = Long.parseLong(getIntent().
                    getStringExtra(Constants.AddPurchaseInvoiceActivity.editPurchaseInvoiceId));
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddPurchaseInvoiceActivity);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        declarations();
        refreshVendors();
        setRecyclerView();


    }

    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(
                R.id.recyclerView_itemsList_addPurchaseInvoiceActivity);

        adapterRecyclerView = new RecyclerViewAdapter_AddPurchaseInvoice(this, invoiceItems);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterRecyclerView);

    }

    private void declarations() {

        vendorNameEdit = (AutoCompleteTextView) findViewById(R.id.vendorName_addPurchaseInvoiceActivity);
        vendorPhoneEdit = (EditText) findViewById(R.id.vendorPhoneNumber_addPurchaseInvoiceActivity);
        vendorEmailEdit = (EditText) findViewById(R.id.vendorEmail_addPurchaseInvoiceActivity);
        vendorAddressEdit = (EditText) findViewById(R.id.vendorAddress_addPurchaseInvoiceActivity);
        invoiceNumberEdit = (EditText) findViewById(R.id.invoiceNumber_addPurchaseInvoiceActivity);
        invoicePONUmberEdit = (EditText) findViewById(R.id.invoicePONumber_addPurchaseInvoiceActivity);
        invoiceDateEdit = (Button) findViewById(R.id.invoiceDate_addPurchaseInvoiceActivity);
        invoiceSubTotalEdit = (EditText) findViewById(R.id.invoiceSubTotal_addPurchaseInvoiceActivity);
        invoiceTaxEdit = (EditText) findViewById(R.id.invoiceTax_addPurchaseInvoiceActivity);
        invoiceDiscountEdit = (EditText) findViewById(R.id.invoiceDiscount_addPurchaseInvoiceActivity);
        invoiceTotalPriceEdit = (EditText) findViewById(R.id.invoiceTotalPrice_addPurchaseInvoiceActivity);

        Button saveButton = (Button) findViewById(R.id.savePurchaseInvoiceButton_addPurchaseInvoiceActivity);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilis.closeKeyboard(AddPurchaseInvoiceActivity.this, v);
                saveButtonClicked();
            }
        });

        View layout = findViewById(R.id.linearLayout_main_addPurchaseInvoiceActivity);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilis.closeKeyboard(AddPurchaseInvoiceActivity.this, v);
            }
        });


        final Calendar calender = Calendar.getInstance();
        invoiceDate = calender.get(Calendar.DAY_OF_MONTH) + "/" +
                (calender.get(Calendar.MONTH) + 1) + "/" + calender.get(Calendar.YEAR);
        invoiceDateEdit.setText(invoiceDate);
        invoiceDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = DatePickerDialog.newInstance(
                        AddPurchaseInvoiceActivity.this,
                        calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH));

                datePicker.vibrate(false);
                datePicker.dismissOnPause(false);
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        setVendorNameAdapter();

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

        invoiceTaxEdit.addTextChangedListener(textWatcher);
        invoiceDiscountEdit.addTextChangedListener(textWatcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addPurchaseInvoiceActivity);
        if (isAddOrEdit) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAddInvoiceItemDialog();
                }
            });
        } else {
            fab.setVisibility(View.GONE);
        }

    }

    private void createAddInvoiceItemDialog(){

        DialogAddPurchaseInvoiceItem dialogAddPurchaseInvoiceItem = new DialogAddPurchaseInvoiceItem(this, invoiceId);

        Dialog dialog = dialogAddPurchaseInvoiceItem.getAddItemDialog();

        dialog.show();
    }

    public void addInvoiceItem(InvoiceItem invoiceItem) {

        invoiceItems.add(invoiceItem);

        adapterRecyclerView.notifyDataSetChanged();
        refreshInputs();

    }


    private void setVendorNameAdapter() {

        adapterVendorName = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, vendorNames);

        vendorNameEdit.setAdapter(adapterVendorName);

        vendorNameEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setVendorDetails(position);
            }
        });

    }

    private void setVendorDetails(int position) {

        Vendor vendor = vendors.get(position);

        vendorPhoneEdit.setText(vendor.getVendorPhone());
        vendorEmailEdit.setText(vendor.getVendorEmail());
        vendorAddressEdit.setText(vendor.getVendorStreet() + ",\n"
                + vendor.getVendorCity() + ",\n"
                + vendor.getVendorState() + ",\n"
                + vendor.getVendorCountry() + ",\n"
                + "ZIP Code - " + vendor.getVendorZip());

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        invoiceDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;


        invoiceDateEdit.setText(invoiceDate);
    }

    private void refreshInputs() {


        if (invoiceTaxEdit.getText().toString().equals("")) {
            invoiceTax = 0;
        } else {
            invoiceTax = Float.parseFloat(invoiceTaxEdit.getText().toString());
        }

        if (invoiceDiscountEdit.getText().toString().equals("")) {
            invoiceDiscount = 0;
        } else {
            invoiceDiscount = Float.parseFloat(invoiceDiscountEdit.getText().toString());
        }

        invoiceSubTotal = 0;
        for (InvoiceItem i : invoiceItems) {
            invoiceSubTotal += Float.parseFloat(i.getItemPrice());
        }

        invoiceTotalPrice = (long) (invoiceSubTotal
                + (invoiceSubTotal * invoiceTax / 100) - (invoiceSubTotal * invoiceDiscount / 100));

        invoiceSubTotalEdit.setText(invoiceSubTotal + "");
        invoiceTotalPriceEdit.setText(invoiceTotalPrice + "");
        invoiceSubTotalEdit.setError(null);
        invoiceTotalPriceEdit.setError(null);

    }

    private void saveButtonClicked() {


        refreshInputs();

        final String vendorName = vendorNameEdit.getText().toString();
        final String vendorPhone = vendorPhoneEdit.getText().toString();
        final String vendorEmail = vendorEmailEdit.getText().toString();
        final String vendorAddress = vendorAddressEdit.getText().toString();
        final String invoiceNumber = invoiceNumberEdit.getText().toString();
        final String invoicePONUmber = invoicePONUmberEdit.getText().toString();
        invoiceDate = invoiceDateEdit.getText().toString();

        if(vendorName.equals("")){
            vendorNameEdit.setError("Enter vendor name");
            vendorNameEdit.requestFocus();
            return;
        }
        if(vendorPhone.equals("")){
            vendorPhoneEdit.setError("Enter vendor phone Number");
            vendorPhoneEdit.requestFocus();
            return;
        }
        if(vendorEmail.equals("")){
            vendorEmailEdit.setError("Enter vendor email");
            vendorEmailEdit.requestFocus();
            return;
        }
        if(vendorAddress.equals("")){
            vendorAddressEdit.setError("Enter vendor address");
            return;
        }
        if(invoiceNumber.equals("")){
            invoiceNumberEdit.setError("Enter invoice number");
            invoiceNumberEdit.requestFocus();
            return;
        }
        if(invoicePONUmber.equals("")){
            invoicePONUmberEdit.setError("Enter invoice PO number");
            invoicePONUmberEdit.requestFocus();
            return;
        }

        if(invoiceTotalPrice <= 0){
            invoiceTotalPriceEdit.setError("Total price can't be 0");
            invoiceTotalPriceEdit.requestFocus();
            return;
        }
        String message, title;
        if (isAddOrEdit) {
            title = "Save";
            message = "Check again, Once saved invoice items cannot be changed";
        } else {
            title = "Update";
            message = "Check again, Once update old data will be replaced";
        }


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PurchaseInvoice invoice = new PurchaseInvoice();

                        invoice.setInvoiceId(invoiceId + "");
                        invoice.setInvoiceNumber(invoiceNumber);
                        invoice.setInvoicePONumber(invoicePONUmber);
                        invoice.setInvoiceDate(invoiceDate);
                        invoice.setVendorName(vendorName);
                        invoice.setVendorPhone(vendorPhone);
                        invoice.setVendorEmail(vendorEmail);
                        invoice.setVendorAddress(vendorAddress);
                        invoice.setInvoiceSubTotal(invoiceSubTotal + "");
                        invoice.setInvoiceTax(invoiceTax + "");
                        invoice.setInvoiceDiscount(invoiceDiscount + "");
                        invoice.setInvoiceTotalPrice(invoiceTotalPrice + "");

                        invoice.setInvoiceItems(invoiceItems);
                        FirebaseDb.savePurchaseInvoice(invoice);

                        Toast.makeText(AddPurchaseInvoiceActivity.this,
                                "Purchase invoice saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).create();

        alertDialog.show();

    }



    private void refreshVendors() {

        DatabaseReference reference = FirebaseDb.getVendorsDbReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                vendors.clear();
                vendorNames.clear();
                for(DataSnapshot snap:dataSnapshot.getChildren()){

                    Vendor vendor = snap.getValue(Vendor.class);

                    vendors.add(vendor);
                    vendorNames.add(vendor.getVendorName());

                }
                adapterVendorName.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshInputs();
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

    @Override
    protected void onStart() {
        super.onStart();

        if (!isAddOrEdit) {
            Query query = FirebaseDb.getPurchaseInvoicesDbReference().child(invoiceId + "");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        PurchaseInvoice invoice = dataSnapshot.getValue(PurchaseInvoice.class);

                        vendorNameEdit.setText(invoice.getVendorName());
                        vendorPhoneEdit.setText(invoice.getVendorPhone());
                        vendorEmailEdit.setText(invoice.getVendorEmail());
                        vendorAddressEdit.setText(invoice.getVendorAddress());
                        invoiceNumberEdit.setText(invoice.getInvoiceNumber());
                        invoicePONUmberEdit.setText(invoice.getInvoicePONumber());
                        invoiceDateEdit.setText(invoice.getInvoiceDate());
                        invoiceSubTotalEdit.setText(invoice.getInvoiceSubTotal());
                        invoiceTaxEdit.setText(invoice.getInvoiceTax());
                        invoiceTaxEdit.setEnabled(false);
                        invoiceDiscountEdit.setText(invoice.getInvoiceDiscount());
                        invoiceDiscountEdit.setEnabled(false);
                        invoiceTotalPriceEdit.setText(invoice.getInvoiceTotalPrice());
                        invoiceItems.clear();
                        invoiceItems.addAll(invoice.getInvoiceItems());
                        adapterRecyclerView.notifyDataSetChanged();
                        refreshInputs();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}
