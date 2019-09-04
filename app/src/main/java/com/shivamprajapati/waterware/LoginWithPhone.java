package com.shivamprajapati.waterware;

import android.content.Intent;
import android.graphics.Typeface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginWithPhone extends AppCompatActivity {

    EditText phoneNumber;
    String phone;

    Button cardView3;
    TextView enterOtp,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);


        cardView3 = (Button) findViewById(R.id.button2);
        enterOtp=(TextView)findViewById(R.id.textView8);
        msg=(TextView)findViewById(R.id.textView9);
        enterOtp.setTypeface(Typeface.createFromAsset(getAssets(),"font/myfont.ttf"));
        msg.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        cardView3.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        phoneNumber = (EditText) findViewById(R.id.editText22);
        ((TextView)findViewById(R.id.textView3)).setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        phoneNumber.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone = phoneNumber.getText().toString().trim();

                if (phoneNumber.getText().toString().isEmpty() || phoneNumber.getText().length() < 10) {

                    Toast.makeText(LoginWithPhone.this, "Enter a valid number" + phone + "ll", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                    Intent intent = new Intent(LoginWithPhone.this, EnterOTP.class);
                    // Toast.makeText(LoginWithPhone.this,"Enter a valid number"+phoneNumber.getText(),Toast.LENGTH_LONG).show();

                    intent.putExtra("ph", phone);
                    startActivity(intent);
                }
            }
        });

        String id=RandomString.getAlphaNumericString(20);
        String pid=RandomString.getAlphaNumericString(20);


        SharedPref.saveSharedSettingsPendingId(this,"id",id);
        SharedPref.saveSharedSettingsId(this,"pid",pid);



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