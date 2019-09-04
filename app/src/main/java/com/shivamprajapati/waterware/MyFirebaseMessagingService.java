package com.shivamprajapati.waterware;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import android.media.RingtoneManager;
import android.os.Build;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.NotificationCompat;
import android.util.Log;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String token;
    Context context;
    List<PreviousHistory> historylist;
    int currentNotificationID=0;
    List<NotificationHistory> notificationHistories;
    NotificationHistory notificationHistory;
    ChildEventListener childEventListener;


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        context=this;
        token=s;

        FirebaseDatabase.getInstance().getReference().child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        try {

                            if (snapshot.child("uid").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                snapshot.child("myToken").getRef().setValue(token);
                                SharedPref.saveSharedSettingMyToken(context, "myToken", token);
                            }

                        }catch (Exception e){}
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        context=this;
        if(remoteMessage.getData().size()>0){
            Map<String,String> payload=remoteMessage.getData();

            Log.i("otp",payload.get("title"));
            showNotification(payload);
        }

    }


    private void showNotification(final Map<String, String> payload) {



        Intent intent=new Intent(context,MainActivity.class);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notifications_black_24dp))
                .setLights(Notification.DEFAULT_LIGHTS,2000,2000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(payload.get("body")))
                .setContentTitle(payload.get("title"))
                .setContentText(payload.get("body"));
        builder.setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("channel_id","name",NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.enableLights(true);
            builder.setChannelId("channel_id");
            notificationManager.createNotificationChannel(channel);
            Notification notification=builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            currentNotificationID++;
            int notificationId = currentNotificationID;
            if (notificationId == Integer.MAX_VALUE - 1)
                notificationId = 0;

            notificationManager.notify(notificationId, notification);

        }else{
            Notification notification=builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            currentNotificationID++;
            int notificationId = currentNotificationID;
            if (notificationId == Integer.MAX_VALUE - 1)
                notificationId = 0;

            notificationManager.notify(notificationId, notification);

        }

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d");
        String d = simpleDateFormat.format(date);
        notificationHistory = new NotificationHistory();
        notificationHistory.setDate(d);

        notificationHistory.setTitle(payload.get("title"));
        notificationHistory.setBody(payload.get("body"));


        Gson gson = new Gson();

        if (SharedPref.readSharedSettingsNotificationList(context, "nh", null) != null) {
            Type type = new TypeToken<ArrayList<NotificationHistory>>() {
            }.getType();
            notificationHistories = gson.fromJson(SharedPref.readSharedSettingsNotificationList(context, "nh", null), type);
        } else {
            notificationHistories = new ArrayList<>();
        }

        notificationHistories.add(notificationHistory);

        String json = gson.toJson(notificationHistories);

        SharedPref.saveSharedSettingNotificationList(context, "nh", json);




        if(payload.get("title").equals("Tank service completed")) {

           childEventListener= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    if (dataSnapshot.exists()) {


                        PreviousHistory previousHistory = new PreviousHistory();
                        previousHistory.setBill("0");
                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, ''yyyy");
                        String d = simpleDateFormat.format(date);
                        String cleaning = dataSnapshot.child("cleaning").getValue().toString();
                        String visiting = dataSnapshot.child("visiting").getValue().toString();
                        String total = dataSnapshot.child("total").getValue().toString();

                        String fromTime = dataSnapshot.child("fromTime").getValue().toString();
                        String toTime = dataSnapshot.child("toTime").getValue().toString();
                        String sun = dataSnapshot.child("SUNDAY").getValue().toString();
                        String mon = dataSnapshot.child("MONDAY").getValue().toString();
                        String tue = dataSnapshot.child("TUESDAY").getValue().toString();
                        String wed = dataSnapshot.child("WEDNESDAY").getValue().toString();
                        String thu = dataSnapshot.child("THURSDAY").getValue().toString();
                        String fri = dataSnapshot.child("FRIDAY").getValue().toString();
                        String sat = dataSnapshot.child("SATURDAY").getValue().toString();
                        String[] days = new String[]{sun, mon, tue, wed, thu, fri, sat};

                        previousHistory.setDate(d);
                        previousHistory.setCleaningCharges(cleaning);
                        previousHistory.setVisitingCharges(visiting);
                        previousHistory.setTotalCharges(total);
                        previousHistory.setDays(days);
                        previousHistory.setFromTime(fromTime);
                        previousHistory.setToTime(toTime);
                        previousHistory.setName(dataSnapshot.child("name").getValue().toString());
                        previousHistory.setAddress(dataSnapshot.child("address").getValue().toString());
                        previousHistory.setPhone(dataSnapshot.child("phone").getValue().toString());

                        Gson gson = new Gson();

                        if (SharedPref.readSharedSettingsPreviousHistoryList(context, "ph", null) != null) {
                            Type type = new TypeToken<ArrayList<PreviousHistory>>() {
                            }.getType();
                            historylist = gson.fromJson(SharedPref.readSharedSettingsPreviousHistoryList(context, "ph", null), type);
                        } else {
                            historylist = new ArrayList<>();
                        }


                        historylist.add(previousHistory);

                        String json = gson.toJson(historylist);

                        SharedPref.saveSharedSettingPreviousHistoryList(context, "ph", json);


                        dataSnapshot.child("status").getRef().setValue("Done");
                        SharedPref.saveSharedSettingsIsThereAPendingRequest(context, "check", false);


                    }





                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ((childEventListener!=null))
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").removeEventListener(childEventListener);

    }
}
