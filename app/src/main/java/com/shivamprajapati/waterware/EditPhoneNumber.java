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


public class EditPhoneNumber extends AppCompatActivity {

    EditText newNumber;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_phone_number);


        newNumber=findViewById(R.id.newNumber);
        next=findViewById(R.id.next);
        ((TextView)findViewById(R.id.helper)).setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        newNumber.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        next.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(newNumber.getText().toString())){
                    Toast.makeText(EditPhoneNumber.this,"Enter valid number",Toast.LENGTH_LONG).show();
                }else{
                     Intent intent=new Intent(EditPhoneNumber.this,EditNumberVerify.class);
                    intent.putExtra("www",newNumber.getText().toString().trim());
                    finish();
                    startActivity(intent);

                }
            }
        });
        

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}
