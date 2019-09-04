package com.shivamprajapati.waterware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserDetailsGoogleSignIn extends AppCompatActivity {

    EditText name, address, phoneNumber;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_google_sign_in);

        name = findViewById(R.id.userName);
        address = findViewById(R.id.userAddress);
        phoneNumber = findViewById(R.id.userPhoneNumber);
        next = findViewById(R.id.cardView);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    Toast.makeText(UserDetailsGoogleSignIn.this, "Enter Valid Details", Toast.LENGTH_LONG).show();
                } else {
                    SharedPref.saveSharedSettingsIsDataCreatedGoogle(UserDetailsGoogleSignIn.this, "dataGoogle", true);
                    SharedPref.saveSharedSettingsName(UserDetailsGoogleSignIn.this, "nameOfUser", name.getText().toString().trim());
                    SharedPref.saveSharedSettingsPhoneNumber(UserDetailsGoogleSignIn.this, "phoneNoOfUser", phoneNumber.getText().toString().trim());
                    SharedPref.saveSharedSettingsAddress(UserDetailsGoogleSignIn.this, "addressOfUser", address.getText().toString().trim());

                    String id= UserDetailsGoogleSignIn.RandomString.getAlphaNumericString(20);
                    String pid= UserDetailsGoogleSignIn.RandomString.getAlphaNumericString(20);


                    SharedPref.saveSharedSettingsPendingId(UserDetailsGoogleSignIn.this,"id",id);
                    SharedPref.saveSharedSettingsId(UserDetailsGoogleSignIn.this,"pid",pid);

                    finish();
                    startActivity(new Intent(UserDetailsGoogleSignIn.this, MainActivity.class));

                }
            }
        });


    }


    public static class RandomString {

        // function to generate a random string of length n
        static String getAlphaNumericString(int n) {

            // chose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index
                        = (int) (AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            return sb.toString();
        }

    }
}
