package com.varma.shopkeeper.shopkeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.varma.shopkeeper.shopkeeper.Extras.Constants;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{


    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPref = getSharedPreferences(Constants.SharedPreference.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHomeActivity);
        setSupportActionBar(toolbar);


        View currentStockButton = findViewById(R.id.currentStockButton_homeActivity);
        currentStockButton.setOnClickListener(this);

        View purchaseInvoiceButton =findViewById(R.id.addPurchaseInvoiceButton_homeActivity);
        purchaseInvoiceButton.setOnClickListener(this);

        View saleInvoiceButton =findViewById(R.id.salesButton_homeActivity);
        saleInvoiceButton.setOnClickListener(this);

        View expireItemsButton = findViewById(R.id.damageButton_homeActivity);
        expireItemsButton.setOnClickListener(this);


        View returnButton =findViewById(R.id.returnItemButton_homeActivity);
        returnButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent;


        switch (v.getId()){

            case R.id.currentStockButton_homeActivity:{
                intent = new Intent(HomeActivity.this,CurrentStockActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.addPurchaseInvoiceButton_homeActivity:{
                intent = new Intent(HomeActivity.this,ShowPurchaseInvoiceActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.salesButton_homeActivity:{
                intent = new Intent(HomeActivity.this, SaleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.returnItemButton_homeActivity:{

                break;
            }

            case R.id.damageButton_homeActivity:{

                break;
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu_home_activity,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.settings_MainMenu_homeActivity: {

                Intent toSettingActivity = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(toSettingActivity);

                break;
            }

            case R.id.logOut_MainMenu_homeActivity: {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(Constants.SharedPreference.IS_LOGIN, false);
                editor.putString(Constants.SharedPreference.PIN, "");
                editor.apply();


                Intent toStartActivity = new Intent(HomeActivity.this, StartActivity.class);
                startActivity(toStartActivity);

                finish();
                break;
            }


        }

        return true;
    }
}
