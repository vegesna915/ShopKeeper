package com.varma.shopkeeper.shopkeeper.Fragments.StartActivity;


import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.varma.shopkeeper.shopkeeper.Extras.Constants;
import com.varma.shopkeeper.shopkeeper.Extras.Utilis;
import com.varma.shopkeeper.shopkeeper.R;
import com.varma.shopkeeper.shopkeeper.StartActivity;

public class LoginFragment extends Fragment {

    StartActivity startActivity;
    EditText userEmailView, passwordView;
    Button loginButton, forgotButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        startActivity = (StartActivity) getActivity();

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        userEmailView = (EditText) v.findViewById(R.id.userName_login);
        passwordView = (EditText) v.findViewById(R.id.password_login);

        loginButton = (Button) v.findViewById(R.id.loginButton_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilis.closeKeyboard(startActivity, v);

                String email = userEmailView.getText().toString();
                String password = passwordView.getText().toString();

                if (email.equals("")) {
                    userEmailView.setError("Enter Email Address");
                }

                if (password.equals("")) {
                    passwordView.setError("Enter password");
                }

                if (Constants.Login.isLogin(email, password)) {


                    startActivity.changeFragmentToSetPin();
                }else{

                    passwordView.setText("");

                    Toast.makeText(startActivity, "Invalid email/password", Toast.LENGTH_SHORT).show();

                    Vibrator vibrator = (Vibrator) startActivity.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(250);
                }

            }
        });

        forgotButton = (Button) v.findViewById(R.id.forgotPassword_login);
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(startActivity, "Sorry, can't do anything", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
