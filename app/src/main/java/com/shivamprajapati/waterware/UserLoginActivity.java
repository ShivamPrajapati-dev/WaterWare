package com.shivamprajapati.waterware;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginActivity extends AppCompatActivity {

    EditText email;
    TextView checkSignIn;
    Button cardViewSignin;
    String mEmail;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ProgressDialog progressDialog;
    String pass="123456";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        if(SharedPref.readSharedSettingsIsLogin(this,"setLogin",false)&&SharedPref.readSharedSettingsIsDataCreated(this,"dataCreated",false)) {
            finish();
            startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
        }
        else if(SharedPref.readSharedSettingsIsLogin(this,"setLogin",false)&&(!(SharedPref.readSharedSettingsIsDataCreated(this,"dataCreated",false)))){
            finish();
            startActivity((new Intent(UserLoginActivity.this,UserDetails.class)));
        }


        email=(EditText)findViewById(R.id.userEmail);

        mEmail=email.getText().toString().trim();

        checkSignIn=(TextView)findViewById(R.id.checkSignin);




        cardViewSignin=(Button) findViewById(R.id.loginCard);
        cardViewSignin.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        checkSignIn.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        email.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        ((TextView)findViewById(R.id.textView12)).setTypeface(Typeface.createFromAsset(getAssets(),"font/myfont.ttf"));


        firebaseAuth=FirebaseAuth.getInstance();

        firebaseUser=firebaseAuth.getCurrentUser();

        checkSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser!=null&&firebaseUser.isEmailVerified()) {
                    Intent intent = new Intent(UserLoginActivity.this, AlreadyLogin.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserLoginActivity.this,"Your Email is not Verified.",Toast.LENGTH_LONG).show();
                }
            }
        });
        cardViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });


        String id= LoginWithPhone.RandomString.getAlphaNumericString(20);
        String pid=LoginWithPhone.RandomString.getAlphaNumericString(20);


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


        public void createAccount()
    {
        firebaseAuth=FirebaseAuth.getInstance();





        mEmail=email.getText().toString().trim();

        if(!(TextUtils.isEmpty(email.getText().toString()))) {


            firebaseAuth.createUserWithEmailAndPassword(mEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        verify();
                    }
                    else{
                        Toast.makeText(UserLoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });



    }

              else
                {
                Toast.makeText(UserLoginActivity.this,"Enter Full Details",Toast.LENGTH_LONG).show();
                }


}

    private void verify() {

        firebaseUser=firebaseAuth.getCurrentUser();

        progressDialog=new ProgressDialog(UserLoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Sending verification mail");
        progressDialog.show();
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    progressDialog.dismiss();

                    new AlertDialog.Builder(UserLoginActivity.this).setTitle("Verify Email")
                            .setMessage("Please click on the verification link sent to your email address").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            Intent intent=new Intent(UserLoginActivity.this,UserDetails.class);
                            intent.putExtra("email",mEmail);
                            firebaseAuth.signOut();
                            startActivity(intent);
                        }
                    }).show();



                }
                else{
                    Toast.makeText(UserLoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}


