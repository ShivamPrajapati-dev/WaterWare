package com.shivamprajapati.waterware;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FirstVerifyPhoneNumber extends AppCompatActivity {

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    FirebaseAuth firebaseAuth;
    EditText otp;
    TextView resend;
    ProgressDialog progressDialog;
    String verificationID;
    String phone;
    Button next;
    int i=29;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_verify_phone_number);

        firebaseAuth=FirebaseAuth.getInstance();
        otp=(EditText)findViewById(R.id.editText42);
        resend=(TextView)findViewById(R.id.textView5);
        next=(Button)findViewById(R.id.cardView4);

        phone=getIntent().getStringExtra("qqq");

        ((TextView)findViewById(R.id.textView4)).setTypeface(Typeface.createFromAsset(getAssets(),"font/myfont.ttf"));
        ((TextView)findViewById(R.id.textView10)).setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        ((TextView)findViewById(R.id.textView5)).setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        next.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getText().toString().length()<6|| TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(FirstVerifyPhoneNumber.this,"Enter valid OTP",Toast.LENGTH_LONG).show();
                }else {
                    verify(otp.getText().toString());
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone,30, TimeUnit.SECONDS,FirstVerifyPhoneNumber.this,mCallBacks);
                startCountDown();

            }
        });


        mCallBacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code=phoneAuthCredential.getSmsCode();
                if(code!=null){
                    otp.setText(code);
                    verify(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(FirstVerifyPhoneNumber.this,e.getMessage(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationID=s;
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone,30, TimeUnit.SECONDS,this,mCallBacks);

        startCountDown();


    }

    private void startCountDown() {

        new CountDownTimer(30000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

                resend.setClickable(false);
                resend.setText("00:"+check(i));
                i--;
                ;
            }

            @Override
            public void onFinish() {
                resend.setText("Resend OTP");
                resend.setClickable(true);
                i=30;
            }
        }.start();

    }

    private String check(int i) {

        if(i<=9)
            return "0"+String.valueOf(i);
        else
            return String.valueOf(i);

    }

    private void verify(String code) {

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        linkWithEmail(verificationID,code);

    }

    private void linkWithEmail(String verificationID, String code) {

        AuthCredential authCredential=PhoneAuthProvider.getCredential(verificationID,code);

        firebaseAuth.getCurrentUser().linkWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    SharedPref.saveSharedSettingsPhoneNumber(FirstVerifyPhoneNumber.this,"phoneNoOfUser",phone);

                    Toast.makeText(FirstVerifyPhoneNumber.this,"Verification successful",Toast.LENGTH_LONG).show();

                    SharedPref.saveSharedSettingIsPhoneVerified(FirstVerifyPhoneNumber.this,"isPhoneVerified","yes");
                    finish();

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(FirstVerifyPhoneNumber.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void finish() {

        Intent intent=new Intent();
        intent.putExtra("phoneNew",phone);
        setResult(1,intent);
        super.finish();
    }
}
