package com.shivamprajapati.waterware;


import android.graphics.Typeface;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.text.Spannable;
import android.text.SpannableString;
import android.view.SubMenu;
import android.view.View;
import android.support.v4.view.GravityCompat;

import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;

import android.view.Menu;

import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CurrentRequestTab1.OnFragmentInteractionListener,PreviousHistoryTab2.OnFragmentInteractionListener,NotificationFragment.OnFragmentInteractionListener ,UserProfileFragment.OnFragmentInteractionListener,MainFragment.OnFragmentInteractionListener,AboutUsFragment.OnFragmentInteractionListener,ContactUsFragment.OnFragmentInteractionListener,ImageOne.OnFragmentInteractionListener,ImageTwo.OnFragmentInteractionListener,ImageThree.OnFragmentInteractionListener,Bill.BottomSheetListener,LogoutFragment.OnFragmentInteractionListener{


    NavigationView navigationView;
    DrawerLayout drawer;
    DatabaseReference databaseReference1;
    boolean doubleBackToExitPressedOnce = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseReference1=FirebaseDatabase.getInstance().getReference();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                SharedPref.saveSharedSettingMyToken(MainActivity.this,"myToken",instanceIdResult.getToken());

            }
        });

        try {
            databaseReference1.child("AnoopKumarPrajapati").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    SharedPref.saveSharedSettingsToken(MainActivity.this, "token", dataSnapshot.child("token").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){}

         drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        MenuItem menuItem=navigationView.getMenu().getItem(0);
        menuItem.setChecked(true);


        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            applyFontToMenuItem(mi);
        }

        navigationView.setNavigationItemSelectedListener(this);


        Fragment fragment=new MainFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment,"main").commit();

        if(fragment.isVisible()){
            MenuItem item=navigationView.getMenu().getItem(0);
            item.setChecked(true);
        }

    }




    SpannableString mNewTitle;
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "font/fonttwo.ttf");
         mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeFace("" , font,this), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);


        mi.setTitle(mNewTitle);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Fragment focusedFragment=getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assert focusedFragment != null;
        assert focusedFragment.getTag() != null;
        if(!(focusedFragment.isVisible() && focusedFragment.getTag().equals("main"))){
            MenuItem item=navigationView.getMenu().getItem(0);
            item.setChecked(true);

            Fragment fragment=new MainFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment,"main").commit();
        }else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if(id==R.id.nav_home){

            if(item.isChecked()){

                drawer.closeDrawer(GravityCompat.START);
            }
            else{


                Fragment fragment=new MainFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment,"main").commit();

                drawer.closeDrawer(GravityCompat.START);
            }


        }

        else if (id == R.id.nav_notification) {

            if(item.isChecked()){

                drawer.closeDrawer(GravityCompat.START);
            }else {

                    Fragment fragment = new NotificationFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment,"x").commit();
                    drawer.closeDrawer(GravityCompat.START);

            }

        } else if (id == R.id.nav_profile) {



            if(item.isChecked()){

                drawer.closeDrawer(GravityCompat.START);
            }else {



                    Fragment fragment = new UserProfileFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment,"y").commit();
                    drawer.closeDrawer(GravityCompat.START);


            }

        }  else if (id == R.id.nav_about_us) {
            if(item.isChecked()){

                drawer.closeDrawer(GravityCompat.START);
            }else {

                    Fragment fragment = new AboutUsFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment,"z").commit();
                    drawer.closeDrawer(GravityCompat.START);


            }

        } else if(id==R.id.nav_contact_us){

            if(item.isChecked()){

                drawer.closeDrawer(GravityCompat.START);
            }else {

                    Fragment fragment = new ContactUsFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment,"a").commit();
                    drawer.closeDrawer(GravityCompat.START);


            }
        }else if(id==R.id.nav_logout){

            if(item.isChecked()){

                drawer.closeDrawer(GravityCompat.START);
            }else {

                Fragment fragment = new LogoutFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment,"b").commit();
                drawer.closeDrawer(GravityCompat.START);

            }
        }


        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onBottomSheetListener(View v) {

    }


}
