package com.shivamprajapati.waterware;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnBoarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        if(!SharedPref.readSharedSettingsIntroSlider(this,"introSlider",false)) {

            PaperOnboardingPage screen1 = new PaperOnboardingPage("Welcome", "Get your water tank cleaning done with WaterWare. Drink clean and stay healthy.",
                    Color.parseColor("#678FB4"), R.drawable.scaled_waterdrop, R.drawable.ic_settings_ethernet_black_24dp);
            PaperOnboardingPage screen2 = new PaperOnboardingPage("Easy steps", "Book an appointment with few simple steps",
                    Color.parseColor("#65B0B4"), R.drawable.click, R.drawable.ic_settings_ethernet_black_24dp);
            PaperOnboardingPage screen3 = new PaperOnboardingPage("Let's go", "So lets begin with the mindset of\nSWACH JAL SWACH JEEVAN",
                    Color.parseColor("#9B90BC"), R.drawable.cleaner, R.drawable.ic_settings_ethernet_black_24dp);


            ArrayList<PaperOnboardingPage> paperOnboardingPages = new ArrayList<>();
            paperOnboardingPages.add(screen1);
            paperOnboardingPages.add(screen2);
            paperOnboardingPages.add(screen3);

            PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(paperOnboardingPages);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.onBoardingContainer, paperOnboardingFragment).commit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(Color.parseColor("#678FB4"));
            }

            paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
                @Override
                public void onRightOut() {
                    SharedPref.saveSharedSettingsIntroSlider(OnBoarding.this,"introSlider",true);
                    startActivity(new Intent(OnBoarding.this, SelectLoginMethod.class));
                    OnBoarding.this.finish();

                }
            });
            paperOnboardingFragment.setOnChangeListener(new PaperOnboardingOnChangeListener() {
                @Override
                public void onPageChanged(int i, int i1) {
                    switch (i1) {
                        case 0:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getWindow().setNavigationBarColor(Color.parseColor("#678FB4"));
                            }
                            break;
                        case 1:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getWindow().setNavigationBarColor(Color.parseColor("#65B0B4"));
                            }
                            break;
                        case 2:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getWindow().setNavigationBarColor(Color.parseColor("#9B90BC"));
                            }
                            break;
                    }
                }
            });


        }else{
            startActivity(new Intent(OnBoarding.this, SelectLoginMethod.class));
            OnBoarding.this.finish();
        }


    }
}
