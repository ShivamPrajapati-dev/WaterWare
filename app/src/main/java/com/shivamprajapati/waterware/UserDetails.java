package com.shivamprajapati.waterware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDetails extends AppCompatActivity {

    String email,mPassword,phoneNumber;
    EditText name,phone,password,address;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button next;
    Intent intent;
    ProgressDialog progressDialog;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name=(EditText)findViewById(R.id.userName);
        phone=(EditText)findViewById(R.id.userPhoneNumber);
        password=(EditText)findViewById(R.id.editText);
        address=(EditText)findViewById(R.id.userAddress);

        textView=(TextView)findViewById(R.id.textView2);

        intent=getIntent();

        firebaseAuth=FirebaseAuth.getInstance();
        email=intent.getStringExtra("email");
        next=(Button) findViewById(R.id.cardView);

        name.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        phone.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        password.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        address.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        next.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(UserDetails.this,UserLoginActivity.class));
                firebaseAuth.signInWithEmailAndPassword(email,"123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                              firebaseAuth.getCurrentUser().delete();
                          }
                    }
                });
            }
        });


    }

    private void function() {



        mPassword=password.getText().toString().trim();

        if(phoneNumber!=null){
            phone.setText("+91"+phoneNumber);
        }



        progressDialog=new ProgressDialog(UserDetails.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,"123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    firebaseUser=firebaseAuth.getCurrentUser();
                    if(TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(password.getText().toString())||TextUtils.isEmpty(phone.getText().toString())||TextUtils.isEmpty(address.getText().toString())){
                        progressDialog.dismiss();
                        Toast.makeText(UserDetails.this,"Enter all details",Toast.LENGTH_LONG).show();
                    }

                    else if(!(firebaseUser.isEmailVerified())){
                        progressDialog.dismiss();
                        Toast.makeText(UserDetails.this,"Email address not verified",Toast.LENGTH_LONG).show();
                    }

                    else if(firebaseUser.isEmailVerified()){

                        AuthCredential authCredential= EmailAuthProvider.getCredential(email,"123456");

                        firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    firebaseUser.updatePassword(mPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                firebaseAuth.signOut();
                                                againSignIn();
                                            }
                                        }
                                    });
                                }
                            }
                        });



                    }

                }
            }
        });




    }

    private void againSignIn() {


        firebaseAuth.signInWithEmailAndPassword(email,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    finish();
                    SharedPref.saveSharedSettingsEmail(UserDetails.this,"email",email);
                    SharedPref.saveSharedSettingsIsLogin(UserDetails.this, "setLogin", true);
                    SharedPref.saveSharedSettingsIsDataCreated(UserDetails.this,"dataCreated",true);
                    SharedPref.saveSharedSettingsName(UserDetails.this,"nameOfUser",name.getText().toString().trim());
                    SharedPref.saveSharedSettingsPhoneNumber(UserDetails.this,"phoneNoOfUser",phone.getText().toString().trim());
                    SharedPref.saveSharedSettingsAddress(UserDetails.this,"addressOfUser",address.getText().toString().trim());

                    startActivity(new Intent(UserDetails.this,MainActivity.class));
                }
                else {
                    Toast.makeText(UserDetails.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
