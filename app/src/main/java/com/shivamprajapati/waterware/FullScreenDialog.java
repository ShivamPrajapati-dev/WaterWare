package com.shivamprajapati.waterware;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FullScreenDialog extends DialogFragment implements View.OnClickListener{


      CurrentRequestTab1 cont;
    static FullScreenDialog newInstance() {

        return new FullScreenDialog();

    }



    public interface OnRequestSentListener{
        void onRequestSent();
    }
    OnRequestSentListener onRequestSentListener;


    public  void setContext(CurrentRequestTab1 cont){
        this.cont=cont;
    }




    int x=0;
    TextView fromTime,toTime,name,address,phoneNumber,cleaning,visiting,total,pd,pt;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView listView;
    List<DaySelected> daySelectedList;
    String selectDays[];
    ConnectivityManager connectivityManager;
    Context context;
    CoordinatorLayout coordinatorLayout;
    NotificationHistory notificationHistory;
    ImageView changeAddress;
    NestedScrollView nestedScrollView;
    Handler handler;


    Activity activity;
    ImageView changeName;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();

        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.full_sreen_dialog, container, false);
        TextView send = view.findViewById(R.id.fullscreen_dialog_action);
        TextView cancel=view.findViewById(R.id.fullscreen_dialog_cancel);

        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        pd=view.findViewById(R.id.pd);
        pt=view.findViewById(R.id.aaa);
        context=getContext();

        nestedScrollView=view.findViewById(R.id.scrollView);
        nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        handler=new Handler();
        final AssetManager assetManager=context.getAssets();

        if(!SharedPref.readSharedSettingsThirdOnBoarding(context,"thirdOnBoarding",false)) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {


                    new TapTargetSequence(getDialog())
                            .targets(

                                    TapTarget.forView(pd, "Days preferred by you", "Select days as per your comfort for tank cleaning")
                                            .textTypeface(Typeface.createFromAsset(assetManager, "font/fonttwo.ttf"))
                                            .outerCircleColor(R.color.colorAccent)
                                            .tintTarget(false),
                                    TapTarget.forView(pt, "Preferred timings", "Select the time range in which your water tank may be empty")
                                            .tintTarget(false)
                                            .outerCircleColor(R.color.colorAccent)
                                            .textTypeface(Typeface.createFromAsset(assetManager, "font/fonttwo.ttf"))

                            ).continueOnCancel(true)
                            .listener(new TapTargetSequence.Listener() {
                                @Override
                                public void onSequenceFinish() {

                                    SharedPref.saveSharedSettingsThirdOnBoarding(context,"thirdOnBoarding",true);
                                }

                                @Override
                                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                                }

                                @Override
                                public void onSequenceCanceled(TapTarget lastTarget) {

                                }
                            }).start();

                }
            };

            handler.postDelayed(runnable, 500);

        }








        cleaning=view.findViewById(R.id.Rs300);
        visiting=view.findViewById(R.id.Rs50);
        total=view.findViewById(R.id.total);




        FirebaseDatabase.getInstance().getReference().child("AnoopKumarPrajapti").child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cleaning.setText(dataSnapshot.child("cleaning").getValue().toString().trim());
                    visiting.setText(dataSnapshot.child("visiting").getValue().toString().trim());
                    total.setText(dataSnapshot.child("total").getValue().toString().trim());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        name=(TextView)view.findViewById(R.id.nameOfUser);
        address=(TextView)view.findViewById(R.id.id2);
        phoneNumber=(TextView) view.findViewById(R.id.phoneNumberUser);
        fromTime=(TextView) view.findViewById(R.id.timeFrom);
        toTime=(TextView) view.findViewById(R.id.timeTo);
        changeAddress=(ImageView) view.findViewById(R.id.edit);
        changeName=(ImageView)view.findViewById(R.id.editName);



        listView=(RecyclerView) view.findViewById(R.id.lv);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        name.setText(SharedPref.readSharedSettingsName(view.getContext(),"nameOfUser",""));
        address.setText(SharedPref.readSharedSettingsAddress(view.getContext(),"addressOfUser",""));
        phoneNumber.setText(SharedPref.readSharedSettingsPhoneNumber(view.getContext(),"phoneNoOfUser",""));

        notificationHistory=new NotificationHistory();

        ((TextView)view.findViewById(R.id.mmm)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        send.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        cancel.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        ((TextView)view.findViewById(R.id.textView)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/myfont.ttf"));
        name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        address.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        fromTime.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        toTime.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        phoneNumber.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));


         selectDays=new String[]{"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};

        daySelectedList=new ArrayList<>();
        for(int i=0;i<selectDays.length;i++)
        {
            DaySelected daySelected=new DaySelected();
            daySelected.setDay(selectDays[i]);
            daySelected.setSeleted(false);
            daySelectedList.add(daySelected);
        }

        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(context));
        DayListAdapter dayListAdapter=new DayListAdapter(daySelectedList);
        listView.setAdapter(dayListAdapter);





        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                int hour = 9;
                int minute =0;


                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        fromTime.setText(hourOfDay+" : "+minute);

                    }
                };
                CustomStartTimePickerDialog customTimePickerDialog=new CustomStartTimePickerDialog(context,onTimeSetListener ,hour,minute,true);
                customTimePickerDialog.setMessage("SELECT START TIME");
                customTimePickerDialog.show();


            }

        });

        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                int hour = 12;
                int minute =0;


                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        toTime.setText(hourOfDay+" : "+minute);

                    }
                };
                CustomEndTimePickerDialog customTimePickerDialog=new CustomEndTimePickerDialog(context,onTimeSetListener ,hour,minute,true);
                customTimePickerDialog.setMessage("SELECT END TIME");
                customTimePickerDialog.show();


            }
        });
        final Context context=getContext();


        send.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                for (int i = 0; i < selectDays.length; i++) {
                    if (daySelectedList.get(i).isSeleted()) {
                        x++;
                    }
                }

                if (x != 0) {
                    if (!(fromTime.getText().toString().equals("Time") || toTime.getText().toString().equals("Time"))) {
                        assert context != null;
                        if (SharedPref.readSharedSettingsIsPhoneVerified(context, "isPhoneVerified", null) != null) {

                            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                        builder.setTitle("Are you sure")
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                SharedPref.saveSharedSettingsIsThereAPendingRequest(context, "check", true);
                                                String help = databaseReference.child(firebaseUser.getUid()).child("pending request").child(SharedPref.readSharedSettingsPendingId(context, "id", "")).child("helpId").push().getKey();

                                                Calendar calendar = Calendar.getInstance();
                                                Date date = calendar.getTime();
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, ''yyyy");
                                                String d = simpleDateFormat.format(date);

                                                Map adminMap = new HashMap();

                                                Map map = new HashMap();

                                                map.put("name", name.getText().toString().trim());
                                                map.put("address", address.getText().toString().trim());
                                                map.put("phone", phoneNumber.getText().toString().trim());
                                                map.put("fromTime", fromTime.getText().toString().trim());
                                                map.put("toTime", toTime.getText().toString().trim());
                                                map.put("status", "Yes");
                                                map.put("helpId", help);
                                                map.put("date", d);
                                                map.put("cleaning", cleaning.getText().toString());
                                                map.put("visiting", visiting.getText().toString());
                                                map.put("total", total.getText().toString());

                                                for (int i = 0; i < selectDays.length; i++) {

                                                    if (daySelectedList.get(i).isSeleted())
                                                        map.put(daySelectedList.get(i).getDay(), daySelectedList.get(i).getDay());
                                                    else
                                                        map.put(daySelectedList.get(i).getDay(), daySelectedList.get(i).isSeleted());

                                                }

                                                databaseReference.child(firebaseUser.getUid()).child("pending request").child(firebaseUser.getUid()).updateChildren(map);

                                                adminMap.put("name", name.getText().toString().trim());
                                                adminMap.put("address", address.getText().toString().trim());
                                                adminMap.put("phone", phoneNumber.getText().toString().trim());
                                                adminMap.put("fromTime", fromTime.getText().toString().trim());
                                                adminMap.put("toTime", toTime.getText().toString().trim());
                                                adminMap.put("status", "Yes");
                                                adminMap.put("uid", firebaseUser.getUid());
                                                adminMap.put("token", SharedPref.readSharedSettingsToken(context, "token", ""));
                                                adminMap.put("adminCheck", "no");
                                                adminMap.put("date", d);
                                                adminMap.put("cleaning", cleaning.getText().toString());
                                                adminMap.put("visiting", visiting.getText().toString());
                                                adminMap.put("total", total.getText().toString());
                                                adminMap.put("assigned", "Not assigned yet");
                                                adminMap.put("rating", 0.0f);
                                                adminMap.put("review", "No review");
                                                adminMap.put("shopId", "No id yet");
                                                adminMap.put("myToken", SharedPref.readSharedSettingsMyToken(context, "myToken", ""));


                                                for (int i = 0; i < selectDays.length; i++) {

                                                    if (daySelectedList.get(i).isSeleted())
                                                        adminMap.put(daySelectedList.get(i).getDay(), daySelectedList.get(i).getDay());
                                                    else
                                                        adminMap.put(daySelectedList.get(i).getDay(), daySelectedList.get(i).isSeleted());

                                                }


                                                databaseReference.child("admin").child("pending").child(firebaseUser.getUid()).updateChildren(adminMap);

                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                databaseReference.child("admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                final Dialog dialogX = new Dialog(context);
                                                dialogX.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                dialogX.setContentView(R.layout.dialog_layout);

                                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                                lp.copyFrom(dialogX.getWindow().getAttributes());
                                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                                lp.gravity = Gravity.CENTER;
                                                dialogX.getWindow().setAttributes(lp);
                                                dialogX.setCanceledOnTouchOutside(false);
                                                dialogX.setCancelable(false);

                                                Button button = (Button) dialogX.findViewById(R.id.button34);
                                                button.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        onRequestSentListener = cont;
                                                        onRequestSentListener.onRequestSent();
                                                        dialogX.dismiss();
                                                        dismiss();
                                                    }
                                                });
                                                dialogX.show();

                                            }
                                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();




                            } else {
                                Snackbar.make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_LONG).show();

                            }
                        } else {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.first_verify_phone);

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            lp.gravity = Gravity.CENTER;
                            dialog.getWindow().setAttributes(lp);
                            dialog.setCanceledOnTouchOutside(false);

                            ((TextView) dialog.findViewById(R.id.q)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/myfont.ttf"));
                            ((TextView) dialog.findViewById(R.id.w)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/fonttwo.ttf"));
                            final EditText e = (EditText) dialog.findViewById(R.id.number);
                            e.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/fonttwo.ttf"));
                            TextView changeNumber = (TextView) dialog.findViewById(R.id.textView24);
                            changeNumber.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/fonttwo.ttf"));
                            e.setText(SharedPref.readSharedSettingsPhoneNumber(context, "phoneNoOfUser", ""));


                            changeNumber.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    e.setFocusable(true);
                                    e.setEnabled(true);


                                }
                            });

                            ((Button) dialog.findViewById(R.id.verifyNow)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/fonttwo.ttf"));
                            ((Button) dialog.findViewById(R.id.verifyNow)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!TextUtils.isEmpty(e.getText().toString())) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(dialog.getContext(), FirstVerifyPhoneNumber.class);
                                        intent.putExtra("qqq", e.getText().toString().trim());
                                        startActivityForResult(intent,1);

                                    } else {
                                        Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            ((TextView) dialog.findViewById(R.id.textView25)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }else{
                        Toast.makeText(context, "Please select proper time range", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(context, "Please select any day", Toast.LENGTH_LONG).show();
                }
            }
        });

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert context != null;
                final Dialog dialog=new Dialog(context);
                dialog.setCanceledOnTouchOutside(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_address);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
               final EditText add=(EditText)dialog.findViewById(R.id.newAddress);
                final TextView save=(TextView)dialog.findViewById(R.id.saveNewAddress);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!TextUtils.isEmpty(add.getText().toString())){

                            address.setText(add.getText().toString());
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(dialog.getContext(),"Enter valid address",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                ImageButton imageButton=(ImageButton)dialog.findViewById(R.id.closeEditAddress);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert context != null;
                final Dialog dialog=new Dialog(context);
                dialog.setCanceledOnTouchOutside(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_name);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                final EditText n=(EditText)dialog.findViewById(R.id.editName);
                final TextView save=(TextView)dialog.findViewById(R.id.saveNewName);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!TextUtils.isEmpty(n.getText().toString())){

                            name.setText(n.getText().toString());
                            dialog.dismiss();
                        }
                        else{

                            Toast.makeText(dialog.getContext(),"Enter valid name",Toast.LENGTH_LONG).show();

                        }
                    }
                });

                ImageButton imageButton=(ImageButton)dialog.findViewById(R.id.closeEditName);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



                dialog.show();


            }
        });




        cancel.setOnClickListener(this);

        return view;
    }






    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fullscreen_dialog_action:
            {

                break;
            }
            case R.id.fullscreen_dialog_cancel:
            {
                dismiss();
                break;
            }


        }


    }
    public class CustomEndTimePickerDialog extends TimePickerDialog {

        private TimePicker mTimePicker;
        private final OnTimeSetListener mTimeSetListener;

        public CustomEndTimePickerDialog(Context context, OnTimeSetListener listener,
                                      int hourOfDay, int minute, boolean is24HourView) {
            super(context, TimePickerDialog.THEME_HOLO_DARK, null, hourOfDay,
                    minute , is24HourView);
            mTimeSetListener = listener;
        }

        @Override
        public void updateTime(int hourOfDay, int minuteOfHour) {
            mTimePicker.setCurrentHour(hourOfDay);

        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    if (mTimeSetListener != null) {
                        mTimeSetListener.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                                mTimePicker.getCurrentMinute());
                    }
                    break;
                case BUTTON_NEGATIVE:
                    cancel();
                    break;
            }
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");
                Field timePickerField = classForid.getField("timePicker");
                mTimePicker = (TimePicker) findViewById(timePickerField.getInt(null));
                Field field = classForid.getField("hour");

                NumberPicker hourSpinner = (NumberPicker) mTimePicker
                        .findViewById(field.getInt(null));
                hourSpinner.setMinValue(12);
                hourSpinner.setMaxValue(17);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class CustomStartTimePickerDialog extends TimePickerDialog {

        private TimePicker mTimePicker;
        private final OnTimeSetListener mTimeSetListener;

        public CustomStartTimePickerDialog(Context context, OnTimeSetListener listener,
                                           int hourOfDay, int minute, boolean is24HourView) {
            super(context, TimePickerDialog.THEME_HOLO_DARK, null, hourOfDay,
                    minute , is24HourView);
            mTimeSetListener = listener;
        }

        @Override
        public void updateTime(int hourOfDay, int minuteOfHour) {
            mTimePicker.setCurrentHour(hourOfDay);

        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    if (mTimeSetListener != null) {
                        mTimeSetListener.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                                mTimePicker.getCurrentMinute());
                    }
                    break;
                case BUTTON_NEGATIVE:
                    cancel();
                    break;
            }
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");
                Field timePickerField = classForid.getField("timePicker");
                mTimePicker = (TimePicker) findViewById(timePickerField.getInt(null));
                Field field = classForid.getField("hour");

                NumberPicker hourSpinner = (NumberPicker) mTimePicker
                        .findViewById(field.getInt(null));
                hourSpinner.setMinValue(9);
                hourSpinner.setMaxValue(14);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==1){
            phoneNumber.setText(data.getStringExtra("phoneNew"));

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onRequestSentListener=null;
    }
}
