package com.varma.shopkeeper.shopkeeper.Extras;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utilis {

    public static void closeKeyboard(Context context, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }
    }




}
