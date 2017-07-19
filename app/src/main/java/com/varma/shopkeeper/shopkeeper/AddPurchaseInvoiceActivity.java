package com.varma.shopkeeper.shopkeeper;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Adapters.RecyclerViewAdapters.RecyclerViewAdapter_AddPurchaseInvoice;
import com.varma.shopkeeper.shopkeeper.Database.InvoiceItemDb;
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
    float invoiceTax, invoiceTotalPrice,invoiceDiscount;
    private EditText vendorPhoneEdit, vendorEmailEdit, vendorAddressEdit, invoiceNumberEdit,
            invoicePONUmberEdit, invoiceSubTotalEdit, invoiceTaxEdit, invoiceDiscountEdit,invoiceTotalPriceEdit;
    private AutoCompleteTextView vendorNameEdit;
    private Button invoiceDateEdit;
    private String  invoiceDate;

    private long invoiceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase_invoice);

        invoiceId = System.currentTimeMillis();

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                createAddInvoiceItemDialog();
            }
        });

    }

    private void createAddInvoiceItemDialog(){
        final Dialog dialog = new Dialog(AddPurchaseInvoiceActivity.this);
        dialog.setContentView(R.layout.custom_add_invoice_item_dialog);
        dialog.setTitle("Add Item");

        final InvoiceItem invoiceItem = new InvoiceItem();

        final AutoCompleteTextView invoiceItemName,invoiceItemBrandName,invoiceItemSize;
        final EditText invoiceItemUnitPriceEdit,invoiceItemQtyEdit,invoiceItemPriceEdit;

        invoiceItemName = (AutoCompleteTextView) dialog.findViewById(R.id.itemName_addInvoiceItemActivity);
        invoiceItemBrandName = (AutoCompleteTextView) dialog.findViewById(R.id.itemBrand_addInvoiceItemActivity);
        invoiceItemSize = (AutoCompleteTextView) dialog.findViewById(R.id.itemSize_addInvoiceItemActivity);
        invoiceItemUnitPriceEdit = (EditText) dialog.findViewById(R.id.itemUnitPrice_addInvoiceItemActivity);
        invoiceItemQtyEdit = (EditText) dialog.findViewById(R.id.itemQty_addInvoiceItemActivity);
        invoiceItemPriceEdit = (EditText) dialog.findViewById(R.id.itemPrice_addInvoiceItemActivity);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                float invoiceItemUnitPrice,invoiceItemQty,invoiceItemPrice;

                if(invoiceItemUnitPriceEdit.getText().toString().equals("")){
                    invoiceItemUnitPrice = 0;
                }else{
                    invoiceItemUnitPrice = Integer.parseInt(invoiceItemUnitPriceEdit.getText().toString());
                }


                if(invoiceItemQtyEdit.getText().toString().equals("")){
                    invoiceItemQty = 1;
                }else{
                    invoiceItemQty = Float.parseFloat(invoiceItemQtyEdit.getText().toString());
                }

                invoiceItemPrice = invoiceItemUnitPrice*invoiceItemQty;
                invoiceItemPriceEdit.setText(invoiceItemPrice+"");
            }
        };

        invoiceItemUnitPriceEdit.addTextChangedListener(textWatcher);
        invoiceItemQtyEdit.addTextChangedListener(textWatcher);

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
                float invoiceItemUnitPrice,invoiceItemPrice;
                Long invoiceItemQty;
                if (invoiceItemName.getText().toString().equals("")){
                    invoiceItemName.setError("Enter Item Name");
                    return;
                }
                if(invoiceItemBrandName.getText().toString().equals("")){
                    invoiceItemBrandName.setError("Enter Brand Name");
                    return;
                }

                if(invoiceItemSize.getText().toString().equals("")){
                    invoiceItemSize.setError("Enter Item Size");
                    return;
                }

                if(invoiceItemUnitPriceEdit.getText().toString().equals("")){
                    invoiceItemUnitPriceEdit.setError("Enter Unit Price");
                    return;
                }
                invoiceItemUnitPrice = Integer.parseInt(invoiceItemUnitPriceEdit.getText().toString());

                if(invoiceItemUnitPrice==0){
                    invoiceItemUnitPriceEdit.setError("Unit Price can't be 0");
                    return;
                }

                if(invoiceItemQtyEdit.getText().toString().equals("")){
                    invoiceItemQty = (long)1;
                }else{
                    invoiceItemQty = Long.parseLong(invoiceItemQtyEdit.getText().toString());
                }

                invoiceItemPrice = invoiceItemUnitPrice*invoiceItemQty;
                invoiceItemPriceEdit.setText(invoiceItemPrice+"");

                invoiceItem.setInvoiceId(invoiceId+"");
                invoiceItem.setItemName(invoiceItemName.getText().toString());
                invoiceItem.setItemBrandName(invoiceItemBrandName.getText().toString());
                invoiceItem.setItemSize(invoiceItemSize.getText().toString());
                invoiceItem.setItemUnitPrice(((int)invoiceItemUnitPrice)+"");
                invoiceItem.setItemQty(invoiceItemQty);
                invoiceItem.setItemPrice(invoiceItemPrice+"");


                invoiceItem.setItemId(System.currentTimeMillis()+"");
                invoiceItems.add(invoiceItem);

                InvoiceItemDb invoiceItemDb = new InvoiceItemDb(AddPurchaseInvoiceActivity.this);
                invoiceItemDb.addInvoiceItem(invoiceItem);

                refreshInvoiceItems();
                refreshInputs();

                dialog.dismiss();

            }
        });

        dialog.show();

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

        invoiceTotalPrice = invoiceSubTotal + (invoiceSubTotal * invoiceTax/100) - (invoiceSubTotal * invoiceDiscount/100);

        invoiceSubTotalEdit.setText(invoiceSubTotal + "");
        invoiceTotalPriceEdit.setText(invoiceTotalPrice + "");

        invoiceTotalPriceEdit.setError(null);

    }

    private void saveButtonClicked() {

        String vendorName, vendorPhone, vendorEmail, vendorAddress, invoiceNumber,
                invoicePONUmber;

        refreshInputs();

        vendorName = vendorNameEdit.getText().toString();
        vendorPhone = vendorPhoneEdit.getText().toString();
        vendorEmail = vendorEmailEdit.getText().toString();
        vendorAddress = vendorAddressEdit.getText().toString();
        invoiceNumber = invoiceNumberEdit.getText().toString();
        invoicePONUmber = invoicePONUmberEdit.getText().toString();
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

        PurchaseInvoice invoice = new PurchaseInvoice();

        invoice.setInvoiceId(invoiceId+"");
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoicePONumber(invoicePONUmber);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setVendorName(vendorName);
        invoice.setVendorPhone(vendorPhone);
        invoice.setVendorEmail(vendorEmail);
        invoice.setVendorAddress(vendorAddress);
        invoice.setInvoiceSubTotal(invoiceSubTotal+"");
        invoice.setInvoiceTax(invoiceTax+"");
        invoice.setInvoiceDiscount(invoiceDiscount+"");
        invoice.setInvoiceTotalPrice(invoiceTotalPrice+"");

        invoice.setInvoiceItems(invoiceItems);
        FirebaseDb.savePurchaseInvoice(invoice);


        Toast.makeText(this, "save Clicked", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void refreshInvoiceItems() {

        adapterRecyclerView.notifyDataSetChanged();

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
        refreshInvoiceItems();
        refreshInputs();
    }




}
