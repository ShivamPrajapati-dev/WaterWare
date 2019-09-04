package com.shivamprajapati.waterware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CompletedOrder extends AppCompatActivity {

    TextView name,phone,date,address,days,from,to,cleaning,visiting,total;
    Button relativeLayout;
    ConnectivityManager connectivityManager;
    DatabaseReference databaseReference;
    Context context;

   static OnRepeatRequestListener onRepeatRequestListener;
    String c="0",vi="0",t="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_order);
        relativeLayout=(Button) findViewById(R.id.qqq);

        if(!SharedPref.readSharedSettingsSecondOnBoarding(this,"secondOnBoarding",false)){

            TapTargetView.showFor(this,TapTarget.forView(relativeLayout,"Repeat this appointment","Click on this button to repeat this request.\nAll details except pricing will remain same")
            .outerCircleColor(R.color.colorAccent)
            .tintTarget(false)
            .cancelable(false)
            .textTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf")));


            SharedPref.saveSharedSettingsSecondOnBoarding(this,"secondOnBoarding",true);


        }


        FirebaseDatabase.getInstance().getReference().child("AnoopKumarPrajapati").child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    c=dataSnapshot.child("cleaning").getValue().toString();
                    vi=dataSnapshot.child("visiting").getValue().toString();
                    t=dataSnapshot.child("total").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                finish();

            }
        });


        name=(TextView)findViewById(R.id.nameOfUser);
        phone=(TextView)findViewById(R.id.phoneNumberUser);
        date=(TextView)findViewById(R.id.orderDate);
        address=(TextView)findViewById(R.id.id2);
        days=(TextView)findViewById(R.id.days);
        from=(TextView)findViewById(R.id.timeFrom);
        to = (TextView)findViewById(R.id.timeTo);

        cleaning=findViewById(R.id.Rs300);
        visiting=findViewById(R.id.Rs50);
        total=findViewById(R.id.total);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        context=this;

        name.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        phone.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

        date.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        days.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        from.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        to.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
        address.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));




        Intent intent=getIntent();

        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        date.setText(intent.getStringExtra("date"));
        address.setText(intent.getStringExtra("address"));
        cleaning.setText("Rs "+intent.getStringExtra("cleaning"));
        visiting.setText("Rs"+intent.getStringExtra("visiting"));
        total.setText("Rs"+intent.getStringExtra("total"));
        final String[] day=intent.getStringArrayExtra("days");

        String d="";

        for(int x=0;x<day.length;x++){

            if(!day[x].equals("false"))
                d=d+day[x]+"\n";


        }
        days.setText(d);
        from.setText(intent.getStringExtra("fromTime"));
        to.setText(intent.getStringExtra("toTime"));

        if(SharedPref.readSharedSettingsIsThereAPendingRequest(context,"check",false)){
            relativeLayout.setEnabled(false);
            relativeLayout.setBackgroundColor(Color.GRAY);
        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED ||connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED) {

                    Dialog dialogX=new Dialog(context);
                    dialogX.setContentView(R.layout.current_billing_layout);
                    dialogX.setCanceledOnTouchOutside(false);
                    WindowManager.LayoutParams lpX = new WindowManager.LayoutParams();
                    lpX.copyFrom(dialogX.getWindow().getAttributes());
                    lpX.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lpX.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lpX.gravity = Gravity.CENTER;
                    dialogX.getWindow().setAttributes(lpX);
                    TextView textView=dialogX.findViewById(R.id.textView25);
                    textView.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));

                    ((TextView)dialogX.findViewById(R.id.q)).setTypeface(Typeface.createFromAsset(getAssets(),"font/myfont.ttf"));
                    ((TextView)dialogX.findViewById(R.id.w)).setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CurrentBillBottomSheet dialog=new CurrentBillBottomSheet();
                            dialog.show(getSupportFragmentManager(),"currentBill");
                        }
                    });
                    Button buttonX=dialogX.findViewById(R.id.verifyNow);
                    buttonX.setTypeface(Typeface.createFromAsset(getAssets(),"font/fonttwo.ttf"));
                    buttonX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                    builder.setTitle("Are you sure to repeat this request")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            SharedPref.saveSharedSettingsIsThereAPendingRequest(context, "check", true);
                                            String help = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("helpId").push().getKey();


                                            Map adminMap = new HashMap();

                                            Map map = new HashMap();

                                            Calendar calendar=Calendar.getInstance();
                                            Date date=calendar.getTime();
                                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, MMM d, ''yyyy");
                                            String d=simpleDateFormat.format(date);




                                            map.put("name", name.getText().toString().trim());
                                            map.put("address", address.getText().toString().trim());
                                            map.put("phone", phone.getText().toString().trim());
                                            map.put("fromTime", from.getText().toString().trim());
                                            map.put("toTime", to.getText().toString().trim());
                                            map.put("status", "Yes");
                                            map.put("helpId", help);
                                            map.put("date",d);
                                            map.put("assigned","Not assigned yet");
                                            map.put("shopId","No id yet");
                                            map.put("cleaning",c);
                                            map.put("visiting",vi);
                                            map.put("total",t);

                                            for (int i = 0; i < day.length; i++) {

                                                if (i==0)
                                                    map.put("SUNDAY",day[i]);
                                                else if(i==1)
                                                    map.put("MONDAY",day[i]);
                                                else if(i==2)
                                                    map.put("TUESDAY",day[i]);
                                                else if(i==3)
                                                    map.put("WEDNESDAY",day[i]);
                                                else if(i==4)
                                                    map.put("THURSDAY",day[i]);
                                                else if(i==5)
                                                    map.put("FRIDAY",day[i]);
                                                else if(i==6)
                                                    map.put("SATURDAY",day[i]);


                                            }

                                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);

                                            adminMap.put("name", name.getText().toString().trim());
                                            adminMap.put("address", address.getText().toString().trim());
                                            adminMap.put("phone", phone.getText().toString().trim());
                                            adminMap.put("fromTime", from.getText().toString().trim());
                                            adminMap.put("toTime", to.getText().toString().trim());
                                            adminMap.put("status", "Yes");
                                            adminMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            adminMap.put("token", SharedPref.readSharedSettingsToken(context, "token", ""));
                                            adminMap.put("adminCheck","no");
                                            adminMap.put("date",d);
                                            adminMap.put("assigned","Not assigned yet");
                                            adminMap.put("shopId","No id yet");
                                            adminMap.put("cleaning",c);
                                            adminMap.put("visiting",vi);
                                            adminMap.put("total",t);


                                            for (int i = 0; i < day.length; i++) {

                                                if (i==0)
                                                    adminMap.put("SUNDAY",day[i]);
                                                else if(i==1)
                                                    adminMap.put("MONDAY",day[i]);
                                                else if(i==2)
                                                    adminMap.put("TUESDAY",day[i]);
                                                else if(i==3)
                                                    adminMap.put("WEDNESDAY",day[i]);
                                                else if(i==4)
                                                    adminMap.put("THURSDAY",day[i]);
                                                else if(i==5)
                                                    adminMap.put("FRIDAY",day[i]);
                                                else if(i==6)
                                                    adminMap.put("SATURDAY",day[i]);


                                            }


                                            databaseReference.child("admin").child("pending").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(adminMap);


                                            final Dialog dialogY=new Dialog(context);
                                            dialogY.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialogY.setContentView(R.layout.dialog_layout);

                                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                            lp.copyFrom(dialogY.getWindow().getAttributes());
                                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                            lp.gravity = Gravity.CENTER;
                                            dialogY.getWindow().setAttributes(lp);
                                            dialogY.setCanceledOnTouchOutside(false);
                                            dialogY.setCancelable(false);

                                            Button button=(Button)dialogY.findViewById(R.id.button34);
                                            button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    onRepeatRequestListener.onRepeated();
                                                    dialogY.dismiss();
                                                    finish();


                                                }
                                            });
                                            dialogY.show();

                                            FirebaseDatabase.getInstance().getReference().child("admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists() && dataSnapshot.child("waiting").getValue().toString().equals("yes")) {
                                                        dataSnapshot.child("waiting").getRef().setValue("no");
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });



                                        }
                                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    });
                    dialogX.show();


                }else {
                    Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();

                }



            }
        });




    }

    public interface OnRepeatRequestListener{
        void onRepeated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onRepeatRequestListener=null;
    }
}
