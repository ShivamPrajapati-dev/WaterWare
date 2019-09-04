package com.shivamprajapati.waterware;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private List<NotificationHistory> notificationLists;
    Context context;

    NotificationRecyclerViewAdapter(List<NotificationHistory> notificationLists) {
        this.notificationLists = notificationLists;
    }

    @NonNull
    @Override
    public NotificationRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_list,viewGroup,false);

        context=viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRecyclerViewAdapter.ViewHolder viewHolder, int i) {

        viewHolder.notificationHeading.setTypeface(Typeface.createFromAsset(viewHolder.notificationHeading.getContext().getAssets(),"font/myfont.ttf"));
        viewHolder.notificationBody.setTypeface((Typeface.createFromAsset(viewHolder.notificationBody.getContext().getAssets(),"font/fonttwo.ttf")));
        viewHolder.notiDate.setTypeface((Typeface.createFromAsset(viewHolder.notiDate.getContext().getAssets(),"font/fonttwo.ttf")));
        viewHolder.notificationHeading.setText(notificationLists.get(i).getTitle());
        viewHolder.notificationBody.setText(notificationLists.get(i).getBody());
        viewHolder.notiDate.setText(notificationLists.get(i).getDate());


    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView notificationHeading,notificationBody,notiDate;
        RelativeLayout relativeLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationHeading=(TextView)itemView.findViewById(R.id.notificationHeading);
            notificationBody=(TextView)itemView.findViewById(R.id.notificationBody);
            notiDate=(TextView)itemView.findViewById(R.id.notiDate);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.rlN);


        }
    }
}
