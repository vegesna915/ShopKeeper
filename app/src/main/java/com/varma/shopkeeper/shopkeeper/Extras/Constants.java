package com.varma.shopkeeper.shopkeeper.Extras;


public class Constants {

    public static final String INTENT_FROM = "INTENT_FROM";

    public static class Login {
        static String Email = "store.manager@gmail.com";
        static String Password = "jayambica";

        public static boolean isLogin(String email, String password) {
            return Email.equals(email) && Password.equals(password);
        }

    }

    public static class SharedPreference {
        public static final String SHARED_PREFERENCE = "com.generalstore.jayambica.storemanager";
        public static final String PIN = "pin_shared_preference";
        public static final String IS_LOGIN = "is_login";

    }

    public static class StartActivity {
        public static final String INTENT_FROM_SETTINGS = "INTENT_FROM_SETTINGS";

    }

    public static class AddItemsActivity {
        public static final String isAdd_or_Edit = "isAdd_or_Edit";
        public static final String editItemId = "editItemId";
    }



    public static class AddVendorActivity {
        public static final String isAdd_or_Edit = "isAdd_or_Edit";
        public static final String editVendorId = "editVendorId";
    }



    public static class AddPurchaseInvoiceActivity {
        public static final String isAdd_or_Edit = "isAdd_or_Edit";
        public static final String editPurchaseInvoiceId = "editPurchaseInvoiceId";
    }


    public static class AddPSaleActivity {
        public static final String isAdd_or_Edit = "isAdd_or_Edit";
        public static final String editSaleId = "editPurchaseInvoiceId";
    }

    public static class CurrentStockByBrand {
        public static final String brandName = "brandName";
    }

}
