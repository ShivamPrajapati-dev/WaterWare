package com.shivamprajapati.waterware;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;


public class SplashScreen extends AppCompatActivity {

    TextView fade;
    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fade=findViewById(R.id.fade);
        fade.setTypeface(Typeface.createFromAsset(getAssets(),"font/myfont.ttf"));



        fadeIn.setStartOffset(10);
        fadeIn.setDuration(1200);
        fade.startAnimation(fadeIn);
        fadeIn.setFillAfter(true);

        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(500);



        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fade.startAnimation(fadeOut);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this,OnBoarding.class));
                SplashScreen.this.finish();

            }
        },2710);

    }
}
