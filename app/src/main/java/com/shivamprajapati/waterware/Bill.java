package com.shivamprajapati.waterware;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bill extends BottomSheetDialogFragment {

    private BottomSheetListener bottomSheetListener;


    TextView cleaning,visiting,total,mainTotal;
    RelativeLayout rll;
    CardView cvv;
    ProgressBar progressBar;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bill,container,false);

        cleaning=view.findViewById(R.id.Rs300);
        visiting=view.findViewById(R.id.Rs50);
        total=view.findViewById(R.id.totalRs);
        mainTotal=view.findViewById(R.id.tc);
        cvv=view.findViewById(R.id.cvv);
        rll=view.findViewById(R.id.rll);
        progressBar=view.findViewById(R.id.progress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        databaseReference=FirebaseDatabase.getInstance().getReference();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        databaseReference.child(firebaseUser.getUid()).child("pending request").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            cleaning.setText(dataSnapshot.child("cleaning").getValue().toString());
                            visiting.setText(dataSnapshot.child("visiting").getValue().toString());
                            total.setText(dataSnapshot.child("total").getValue().toString());
                            mainTotal.setText(dataSnapshot.child("total").getValue().toString());
                            progressBar.setVisibility(View.INVISIBLE);
                            rll.setVisibility(View.VISIBLE);
                            cvv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });







        bottomSheetListener.onBottomSheetListener(view);
        return view;

    }

    public interface BottomSheetListener{
        void onBottomSheetListener(View v);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            bottomSheetListener= (BottomSheetListener) getParentFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement BottomSheetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
