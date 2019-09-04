package com.shivamprajapati.waterware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AlreadyLogin extends AppCompatActivity {

    EditText email,password;
    String mEmail,mPassword;
    TextView checkLogin;
    Button cardView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_login);

        email=(EditText)findViewById(R.id.userEmailLoggedIn);
        password=(EditText)findViewById(R.id.passwordLoggedIn);
        mEmail=email.getText().toString().trim();
        mPassword=password.getText().toString().trim();
        checkLogin=(TextView)findViewById(R.id.CheckLogin);
        firebaseAuth=FirebaseAuth.getInstance();




        cardView=(Button)findViewById(R.id.loginCardLoggedin);

        email.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        password.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        cardView.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        checkLogin.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        checkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AlreadyLogin.this,SelectLoginMethod.class));
            }
        });



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkSignIn();

            }
        });


    }

    public void checkSignIn()
    {


        if(!(TextUtils.isEmpty(email.getText().toString())&&TextUtils.isEmpty(password.getText().toString()))) {

            firebaseAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(AlreadyLogin.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AlreadyLogin.this, "Registration successful.", Toast.LENGTH_SHORT).show();

                        emailVerified();



                    } else {
                        Toast.makeText(AlreadyLogin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }

            });
        }
        else {
            Toast.makeText(AlreadyLogin.this,"Enter full details",Toast.LENGTH_LONG).show();
        }
    }

    private void emailVerified() {



        SharedPref.saveSharedSettingsIsLogin(AlreadyLogin.this, "setLogin", true);

        if(SharedPref.readSharedSettingsIsDataCreated(AlreadyLogin.this,"dataCreated",false)) {
            finish();
            startActivity(new Intent(AlreadyLogin.this, MainActivity.class));
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
