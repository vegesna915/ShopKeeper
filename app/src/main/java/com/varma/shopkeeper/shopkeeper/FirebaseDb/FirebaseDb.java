package com.varma.shopkeeper.shopkeeper.FirebaseDb;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.varma.shopkeeper.shopkeeper.Objects.InvoiceItem;
import com.varma.shopkeeper.shopkeeper.Objects.PurchaseInvoice;
import com.varma.shopkeeper.shopkeeper.Objects.StockItem;
import com.varma.shopkeeper.shopkeeper.Objects.Vendor;


public class FirebaseDb {

    private static FirebaseDatabase firebaseDatabase;

    private static FirebaseDatabase getFirebaseDatabase(){
        if(firebaseDatabase==null){
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
        return firebaseDatabase;
    }

    public static DatabaseReference getVendorsDbReference(){
        return getFirebaseDatabase().getReference("Vendors");
    }

    public static DatabaseReference getPurchaseInvoicesDbReference(){
        return getFirebaseDatabase().getReference("PurchaseInvoices");
    }

    public static DatabaseReference getCurrentStockDbReference(){
        return getFirebaseDatabase().getReference("Current Stock");
    }



    public static void saveVendor(Vendor vendor){
        getVendorsDbReference().child(vendor.getVendorId()).setValue(vendor);
    }


    public static void savePurchaseInvoice(PurchaseInvoice purchaseInvoice){

        for(InvoiceItem invoiceItem:purchaseInvoice.getInvoiceItems()){

            final StockItem stockItem = new StockItem();

            stockItem.setItemName(invoiceItem.getItemName());
            stockItem.setItemBrandName(invoiceItem.getItemBrandName());
            stockItem.setItemQty(invoiceItem.getItemQty());
            stockItem.setItemSize(invoiceItem.getItemSize());
            stockItem.setItemUnitPrice(invoiceItem.getItemUnitPrice());
            stockItem.setItemProfit(invoiceItem.getItemProfit());
            stockItem.setItemSellingPrice(invoiceItem.getItemSellingPrice());
            stockItem.setItemId(invoiceItem.getItemBrandName() + " "
                    + invoiceItem.getItemName() + " "
                    + invoiceItem.getItemSize() + " "
                    + invoiceItem.getItemSellingPrice());
            Query query = getCurrentStockDbReference().orderByKey().equalTo(stockItem.getItemId()).limitToFirst(1);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                Long qty = dataSnapshot.child(stockItem.getItemId()).child("itemQty").getValue(Long.class);
                                stockItem.setItemQty(stockItem.getItemQty()+qty);

                            }

                            getCurrentStockDbReference().child(stockItem.getItemId()).setValue(stockItem);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }


        getPurchaseInvoicesDbReference().child(purchaseInvoice.getInvoiceId()).setValue(purchaseInvoice);
    }


}
