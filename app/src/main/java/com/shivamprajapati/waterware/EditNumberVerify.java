package com.shivamprajapati.waterware;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.CountDownTimer;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class EditNumberVerify extends AppCompatActivity {

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String verificationID,phone;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView resend;
    EditText otp;
    Button next;
    int i=29;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_number_verify);



        firebaseAuth=FirebaseAuth.getInstance();
        resend=findViewById(R.id.textView5);
        otp=findViewById(R.id.editText42);
        next=findViewById(R.id.cardView4);
        ((TextView)findViewById(R.id.textView4)).setTypeface(Typeface.createFromAsset(getAssets(),"font/myfont.ttf"));
        ((TextView)findViewById(R.id.textView10)).setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        ((TextView)findViewById(R.id.textView5)).setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        next.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));


        phone=getIntent().getStringExtra("www");



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

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationID=s;
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone,30, TimeUnit.SECONDS,this,mCallBacks);
        startCountDown();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otp.getText().toString().length()<6|| TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(EditNumberVerify.this,"Enter valid OTP",Toast.LENGTH_LONG).show();
                }else {
                    verify(otp.getText().toString());
                }


            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone,30, TimeUnit.SECONDS,EditNumberVerify.this,mCallBacks);
                startCountDown();

            }
        });

    }

    private void startCountDown() {

        new CountDownTimer(30000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

                resend.setClickable(false);
                resend.setText("00:"+check(i));
                i--;

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

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);

        updatePhoneNumber(credential);

    }

    private void updatePhoneNumber(PhoneAuthCredential credential) {

        firebaseAuth.getCurrentUser().updatePhoneNumber(credential).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    progressDialog.dismiss();
                    Toast.makeText(EditNumberVerify.this,"Phone number changed successfully",Toast.LENGTH_LONG).show();
                    SharedPref.saveSharedSettingsPhoneNumber(EditNumberVerify.this,"phoneNoOfUser",phone);
                    FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    snapshot.child("phone").getRef().setValue(phone);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    FirebaseDatabase.getInstance().getReference().child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                                    if (snapshot.child("uid").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                                        snapshot.child("phone").getRef().setValue(phone);

                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    finish();
                }
                else{
                    Toast.makeText(EditNumberVerify.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        });
    }
}
