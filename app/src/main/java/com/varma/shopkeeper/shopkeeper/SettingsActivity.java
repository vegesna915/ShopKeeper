package com.varma.shopkeeper.shopkeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.varma.shopkeeper.shopkeeper.Extras.Constants;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        sharedPref = getSharedPreferences(Constants.SharedPreference.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSettingsActivity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        View changePinButton = findViewById(R.id.changePinButton_settingsActivity);
        changePinButton.setOnClickListener(this);

        View manageItemsButton =findViewById(R.id.manageItemsButton_settingsActivity);
        manageItemsButton.setOnClickListener(this);

        View manageVendorsButton =findViewById(R.id.manageVendorsButton_settingsActivity);
        manageVendorsButton.setOnClickListener(this);

        View logoutButton = findViewById(R.id.logoutButton_settingsActivity);
        logoutButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;


        switch (v.getId()){

            case R.id.changePinButton_settingsActivity:{
                Intent toStartActivity = new Intent(SettingsActivity.this, StartActivity.class);

                toStartActivity.putExtra(Constants.INTENT_FROM, Constants.StartActivity.INTENT_FROM_SETTINGS);
                startActivity(toStartActivity);
                break;
            }

            case R.id.manageItemsButton_settingsActivity:{
                intent = new Intent(SettingsActivity.this,ManageItemsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.manageVendorsButton_settingsActivity:{
                intent = new Intent(SettingsActivity.this,ManageVendorsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.logoutButton_settingsActivity:{
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(Constants.SharedPreference.IS_LOGIN, false);
                editor.putString(Constants.SharedPreference.PIN, "");
                editor.apply();

                Intent toStartActivity = new Intent(SettingsActivity.this, StartActivity.class);
                toStartActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toStartActivity);

                finish();
                break;
            }


        }
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

}
