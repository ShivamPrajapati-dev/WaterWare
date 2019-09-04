package com.shivamprajapati.waterware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SelectLoginMethod extends AppCompatActivity {



    Button cardView1,cardView2;
    TextView textView7;
    SignInButton signIn;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_method);
        mAuth=FirebaseAuth.getInstance();

        if((SharedPref.readSharedSettingsIsDataCreated(this,"dataCreated",false)&&SharedPref.readSharedSettingsIsLogin(this,"setLogin",false))
        ||(SharedPref.readSharedSettingsIsLoginPhone(this,"setLoginPhone",false)&&SharedPref.readSharedSettingsIsDataCreatedPhone(this,"dataPhone",false))
        ||SharedPref.readSharedSettingsIsLoginGoogle(this,"setLoginGoogle",false)&&SharedPref.readSharedSettingsIsDataCreatedGoogle(this,"dataGoogle",false)){
            finish();
            startActivity(new Intent(SelectLoginMethod.this,MainActivity.class));
        }

        else if(SharedPref.readSharedSettingsIsLogin(this,"setLogin",false)&&(!(SharedPref.readSharedSettingsIsDataCreated(this,"dataCreated",false)))){
            finish();
            startActivity((new Intent(SelectLoginMethod.this,UserDetails.class)));
        }
        else if(SharedPref.readSharedSettingsIsLoginPhone(this,"setLoginPhone",false)&&(!(SharedPref.readSharedSettingsIsDataCreatedPhone(this,"dataPhone",false)))){
            finish();
            startActivity(new Intent(SelectLoginMethod.this,UserDetailsPhoneVerification.class));
        }
        else if(SharedPref.readSharedSettingsIsLoginGoogle(this,"setLoginGoogle",false)&&(!(SharedPref.readSharedSettingsIsDataCreatedGoogle(this,"dataGoogle",false)))){
            finish();
            startActivity(new Intent(SelectLoginMethod.this,UserDetailsGoogleSignIn.class));
        }
        else if(!(SharedPref.readSharedSettingsIsLogin(this,"setLogin",false))&&SharedPref.readSharedSettingsIsDataCreated(this,"dataPhone",false)){
            finish();
            startActivity(new Intent(SelectLoginMethod.this,AlreadyLogin.class));
        }

        cardView1=(Button) findViewById(R.id.cardView1);
        cardView2=(Button) findViewById(R.id.cardView2);
        textView7=(TextView)findViewById(R.id.textView7);
        signIn=(SignInButton)findViewById(R.id.signInButton);

        textView7.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        cardView1.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        cardView2.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SelectLoginMethod.this,UserLoginActivity.class));
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(SelectLoginMethod.this,LoginWithPhone.class));


            }
        });



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function();

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
    }

    private void function() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SelectLoginMethod.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signIn();
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                  Toast.makeText(SelectLoginMethod.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(SelectLoginMethod.this,"kmkkk",Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            finish();
                             SharedPref.saveSharedSettingsIsLoginGoogle(SelectLoginMethod.this,"setLoginGoogle",true);
                             SharedPref.saveSharedSettingsEmail(SelectLoginMethod.this,"email",account.getEmail());
                             startActivity(new Intent(SelectLoginMethod.this,UserDetailsGoogleSignIn.class));

                        } else {
                            Toast.makeText(SelectLoginMethod.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }


}








