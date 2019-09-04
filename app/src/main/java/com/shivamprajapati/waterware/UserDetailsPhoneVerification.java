package com.shivamprajapati.waterware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetailsPhoneVerification extends AppCompatActivity {

    EditText userName,address;
    TextView textView;
    String name,add;
    Button cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_phone_verification);

        userName=(EditText)findViewById(R.id.userNamePhone);
        address=(EditText)findViewById(R.id.userAddressPhone);
        userName.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        address.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        textView=(TextView)findViewById(R.id.textView11);
        cardView=(Button) findViewById(R.id.cardViewPhone);
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"font/myfont.ttf"));
        cardView.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=userName.getText().toString().trim();
                add=address.getText().toString().trim();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(add)){
                    Toast.makeText(UserDetailsPhoneVerification.this,"Enter Valid Details",Toast.LENGTH_LONG).show();
                }
                else
                {
                    finish();
                    SharedPref.saveSharedSettingsIsDataCreatedPhone(UserDetailsPhoneVerification.this,"dataPhone",true);
                    SharedPref.saveSharedSettingsName(UserDetailsPhoneVerification.this,"nameOfUser",name);
                    SharedPref.saveSharedSettingsAddress(UserDetailsPhoneVerification.this,"addressOfUser",add);
                    SharedPref.saveSharedSettingsPhoneNumber(UserDetailsPhoneVerification.this,"phoneNoOfUser",getIntent().getStringExtra("phoneNumber"));
                    startActivity(new Intent(UserDetailsPhoneVerification.this,MainActivity.class));
                }
            }
        });


    }
}
