package com.varma.shopkeeper.shopkeeper.Extras;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utilis {

    public static void closeKeyboard(Context context, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }
    }

    public static String getCurrentDate() {

        java.text.SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = Calendar.getInstance().getTime();

        return sdf.format(currentDate);
    }

    public static String getCurrentTime() {

        java.text.SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        Date currentDate = Calendar.getInstance().getTime();

        return sdf.format(currentDate);
    }

}
