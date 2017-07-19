package com.varma.shopkeeper.shopkeeper.Fragments.StartActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.HomeActivity;
import com.varma.shopkeeper.shopkeeper.R;
import com.varma.shopkeeper.shopkeeper.StartActivity;

import java.util.ArrayList;


public class SetPinFragment extends Fragment implements View.OnClickListener {

    Button button1, button2, button3, button4, button5,
            button6, button7, button8, button9, button0, forgot_button;
    ImageButton back_button;
    ArrayList<ImageView> pinImages = new ArrayList<>();
    TextView textView;
    StartActivity startActivity;
    String pinString = "";
    String pinOriginal = "";
    String intentFrom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        startActivity = (StartActivity) getActivity();

        intentFrom = startActivity.getIntent().getStringExtra(Constants.INTENT_FROM);
        if (intentFrom == null) {
            intentFrom = "";
        }

        View v = inflater.inflate(R.layout.fragment_entry_pin, container, false);

        textView = (TextView) v.findViewById(R.id.texView_pinFragment);

        textView.setText("Set new 4-digit pin");

        button1 = (Button) v.findViewById(R.id.number_1);
        button1.setOnClickListener(this);
        button2 = (Button) v.findViewById(R.id.number_2);
        button2.setOnClickListener(this);
        button3 = (Button) v.findViewById(R.id.number_3);
        button3.setOnClickListener(this);
        button4 = (Button) v.findViewById(R.id.number_4);
        button4.setOnClickListener(this);
        button5 = (Button) v.findViewById(R.id.number_5);
        button5.setOnClickListener(this);
        button6 = (Button) v.findViewById(R.id.number_6);
        button6.setOnClickListener(this);
        button7 = (Button) v.findViewById(R.id.number_7);
        button7.setOnClickListener(this);
        button8 = (Button) v.findViewById(R.id.number_8);
        button8.setOnClickListener(this);
        button9 = (Button) v.findViewById(R.id.number_9);
        button9.setOnClickListener(this);
        button0 = (Button) v.findViewById(R.id.number_0);
        button0.setOnClickListener(this);
        forgot_button = (Button) v.findViewById(R.id.forgotButton_pin);
        forgot_button.setVisibility(View.GONE);
        back_button = (ImageButton) v.findViewById(R.id.back_pin);
        back_button.setOnClickListener(this);

        pinImages.add((ImageView) v.findViewById(R.id.pinImage1));
        pinImages.add((ImageView) v.findViewById(R.id.pinImage2));
        pinImages.add((ImageView) v.findViewById(R.id.pinImage3));
        pinImages.add((ImageView) v.findViewById(R.id.pinImage4));
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.forgotButton_pin: {
                startActivity.changeFragmentToLogin();
                break;
            }
            case R.id.back_pin: {
                if (pinString.length() > 0) {
                    pinString = pinString.substring(0, pinString.length() - 1);
                }
                break;
            }
            default: {

                if (pinString.length() < 4) {
                    Button button = (Button) v;
                    pinString += button.getText().toString();
                }
                break;
            }
        }

        setPinImage();

        if (pinString.length() >= 4) {

            if (pinOriginal.equals("")) {
                pinOriginal = pinString;
                pinString = "";
                setPinImage();
                textView.setText("Confirm your 4-digit pin");
            } else {

                if (pinString.equals(pinOriginal)) {

                    if (!intentFrom.equals(Constants.StartActivity.INTENT_FROM_SETTINGS)) {
                        Intent toHomeActivity = new Intent(startActivity, HomeActivity.class);
                        startActivity(toHomeActivity);
                    }

                    SharedPreferences sharedPreferences = startActivity.getSharedPreferences(
                            Constants.SharedPreference.SHARED_PREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constants.SharedPreference.IS_LOGIN, true);
                    editor.putString(Constants.SharedPreference.PIN, pinOriginal);
                    editor.apply();

                    startActivity.finish();
                } else {

                    pinString = "";
                    pinOriginal = "";
                    setPinImage();
                    Vibrator vibrator = (Vibrator) startActivity.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(250);
                    textView.setText("Pin mismatch \n Enter 4-digit pin again");

                }

            }
        }


    }

    private void setPinImage() {

        for (int i = 0; i < pinImages.size(); i++) {

            if (i >= pinString.length()) {
                pinImages.get(i).setImageResource(R.drawable.ic_pin_normal);
            } else {
                pinImages.get(i).setImageResource(R.drawable.ic_pin_large);
            }

        }

    }


}
