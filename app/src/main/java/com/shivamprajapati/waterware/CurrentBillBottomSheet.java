package com.shivamprajapati.waterware;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CurrentBillBottomSheet extends BottomSheetDialogFragment {

   TextView total,cleaning,visiting,smallTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.current_bill,container,false);
        total=view.findViewById(R.id.tc);
        cleaning=view.findViewById(R.id.Rs300);
        visiting=view.findViewById(R.id.Rs50);
        smallTotal=view.findViewById(R.id.totalRs);
        FirebaseDatabase.getInstance().getReference().child("AnoopKumarPrajapati").child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cleaning.setText(dataSnapshot.child("cleaning").getValue().toString().trim());
                    visiting.setText(dataSnapshot.child("visiting").getValue().toString().trim());
                    total.setText(dataSnapshot.child("total").getValue().toString().trim());
                    smallTotal.setText(dataSnapshot.child("total").getValue().toString().trim());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}
