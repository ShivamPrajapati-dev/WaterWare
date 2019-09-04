package com.shivamprajapati.waterware;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreviousHistoryTab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreviousHistoryTab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousHistoryTab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;
    List<PreviousHistory> previousHistoryList;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;

    Context context;
    private OnFragmentInteractionListener mListener;

    public PreviousHistoryTab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreviousHistoryTab2.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviousHistoryTab2 newInstance(String param1, String param2) {
        PreviousHistoryTab2 fragment = new PreviousHistoryTab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View view;
    TextView textView;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view= inflater.inflate(R.layout.fragment_previous_history_tab2, container, false);

         textView=view.findViewById(R.id.textView26);
         textView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
         imageView=view.findViewById(R.id.img);
        recyclerView=(RecyclerView)view.findViewById(R.id.PreviousHistoryRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);

         context=getContext();


        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<PreviousHistory>>(){}.getType();
        if(SharedPref.readSharedSettingsPreviousHistoryList(getContext(),"ph",null)!=null) {
            previousHistoryList = gson.fromJson(SharedPref.readSharedSettingsPreviousHistoryList(getContext(), "ph", ""), type);

            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            PreviousHistoryRecyclerViewAdapter viewAdapter = new PreviousHistoryRecyclerViewAdapter(previousHistoryList);
            recyclerView.setAdapter(viewAdapter);
        }




        databaseReference= FirebaseDatabase.getInstance().getReference();
       childEventListener= databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.child("status").getValue().toString().equals("Done"));
                    {
                        function();
                    }

                }

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


        return  view;
    }

    public void function(){


        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<PreviousHistory>>(){}.getType();
        try {
            if(SharedPref.readSharedSettingsPreviousHistoryList(context,"ph",null)!=null) {
                previousHistoryList = gson.fromJson(SharedPref.readSharedSettingsPreviousHistoryList(context, "ph", ""), type);

                PreviousHistoryRecyclerViewAdapter viewAdapter = new PreviousHistoryRecyclerViewAdapter(previousHistoryList);
                recyclerView.setAdapter(viewAdapter);
            }
        }catch (Exception e){

        }





    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (childEventListener!=null)
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").removeEventListener(childEventListener);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
