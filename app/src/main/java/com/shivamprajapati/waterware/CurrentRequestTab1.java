package com.shivamprajapati.waterware;

import android.annotation.SuppressLint;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.ads.AdRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class CurrentRequestTab1 extends Fragment implements Bill.BottomSheetListener,FullScreenDialog.OnRequestSentListener,CompletedOrder.OnRepeatRequestListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;





    Button button;

    FirebaseUser firebaseUser;
    FullScreenDialog fullScreenDialog;
    DatabaseReference databaseReference,databaseReference1;
    SwipeRefreshLayout refreshLayout;
    TextView cancelRequest,info,bill,displayPrice;
    ConnectivityManager connectivityManager;
    ConstraintLayout relativeLayout;
    CardView cardView1,cardView2;
    RelativeLayout layout;
    AppBarLayout appBarLayout;
    Handler handle;
    ShimmerFrameLayout shimmerFrameLayout;

    private  final int TAB_POS=1;



    Context context;


    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmerAnimation();
    }

    public CurrentRequestTab1() {
        // Required empty public constructor
    }


    public static CurrentRequestTab1 newInstance(String param1, String param2) {
        CurrentRequestTab1 fragment = new CurrentRequestTab1();
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

    private Handler handler;
    private boolean isCountDownTimerActive = false;
    private int currentPage=0;
    private ViewPager viewPager;
    Runnable runnable=new Runnable() {
        @Override
        public void run() {


            if(!isCountDownTimerActive)
            automateSlider();
            handler.postDelayed(runnable,3000);
        }

    };

    @Override
    public void onResume() {
        super.onResume();

        shimmerFrameLayout.startShimmerAnimation();
        handler=new Handler();
        handler.postDelayed(runnable, 3000);
        runnable.run();


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

    }

     View view;
    Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_current_request_tab1, container, false);


        shimmerFrameLayout=view.findViewById(R.id.shimmer);


        CompletedOrder.onRepeatRequestListener=CurrentRequestTab1.this;


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        bill=(TextView)view.findViewById(R.id.viewBill);
        ((TextView)view.findViewById(R.id.displayPrice)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/myfont.ttf"));
        ((TextView)view.findViewById(R.id.qw)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        cardView2=(CardView)view.findViewById(R.id.simple);
        viewPager=(ViewPager)view.findViewById(R.id.autoSliderViewPager);
        TabLayout tabLayout=view.findViewById(R.id.tl);
        cardView1=(CardView)view.findViewById(R.id.infoCard);
        layout=view.findViewById(R.id.rl);
        displayPrice=view.findViewById(R.id.displayPrice);

        relativeLayout=(ConstraintLayout) view.findViewById(R.id.relativeLayout);
        refreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe);

        ImageSliderPagerAdapter imageSliderPagerAdapter=new ImageSliderPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(imageSliderPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        ((TextView)view.findViewById(R.id.mmm)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));







        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if(i==0)
                    currentPage=0;
                else if(i==1)
                    currentPage=1;
                else if(i==2)
                    currentPage=2;

            }

            @Override
            public void onPageSelected(int i) {

                if(i==0)
                    currentPage=0;
                else if(i==1)
                    currentPage=1;
                else if(i==2)
                    currentPage=2;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        fullScreenDialog=new FullScreenDialog().newInstance();
        button=(Button)view.findViewById(R.id.button);
        button.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));

        info=(TextView)view.findViewById(R.id.textView6);

        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf");
        info.setTypeface(typeface);
        ((TextView)view.findViewById(R.id.xxxx)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));
        ((TextView)view.findViewById(R.id.displayDate)).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/myfont.ttf"));




        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference1 = FirebaseDatabase.getInstance().getReference();


         context=getContext();





        refreshLayout.post(new Runnable() {
            @Override
            public void run() {

                refreshLayout.setRefreshing(true);

                connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()==NetworkInfo.State.CONNECTED||connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED) {


                    databaseReference.child(firebaseUser.getUid()).child("pending request")
                            .child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {


                                if (dataSnapshot.child("status").getValue().toString().equals("Yes")) {



                                    String s1="";
                                    if(dataSnapshot.child("MONDAY").getValue().toString().equals("MONDAY")){
                                        s1=s1+"MONDAY\n";
                                    } if(dataSnapshot.child("TUESDAY").getValue().toString().equals("TUESDAY")){
                                        s1=s1+"TUESDAY\n";
                                    } if(dataSnapshot.child("WEDNESDAY").getValue().toString().equals("WEDNESDAY")){
                                        s1=s1+"WEDNESDAY\n";
                                    } if(dataSnapshot.child("THURSDAY").getValue().toString().equals("THURSDAY")){
                                        s1=s1+"THURSDAY\n";
                                    } if(dataSnapshot.child("FRIDAY").getValue().toString().equals("FRIDAY")){
                                        s1=s1+"FRIDAY\n";
                                    } if(dataSnapshot.child("SATURDAY").getValue().toString().equals("SATURDAY")){
                                        s1=s1+"SATURDAY\n";
                                    } if(dataSnapshot.child("SUNDAY").getValue().toString().equals("SUNDAY")){
                                        s1=s1+"SUNDAY\n";
                                    }
                                    TextView z=(TextView)view.findViewById(R.id.days);

                                    z.setText(s1);
                                    z.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));

                                    TextView from=(TextView)view.findViewById(R.id.timeFrom);
                                    from.setText(dataSnapshot.child("fromTime").getValue().toString());
                                    from.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));
                                    TextView to=(TextView)view.findViewById(R.id.timeTo);
                                    to.setText(dataSnapshot.child("toTime").getValue().toString());
                                    to.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));


                                    shimmerFrameLayout.stopShimmerAnimation();
                                    shimmerFrameLayout.setVisibility(View.GONE);
                                    button.setVisibility(View.GONE);
                                    info.setVisibility(View.GONE);

                                    relativeLayout.setVisibility(View.GONE);


                                    layout.setVisibility(View.VISIBLE);




                                    ((TextView)view.findViewById(R.id.displayDate)).setText(dataSnapshot.child("date").getValue().toString());
                                    displayPrice.setText("Rs "+dataSnapshot.child("total").getValue().toString());

                                    refreshLayout.setRefreshing(false);



                                }else{
                                    shimmerFrameLayout.stopShimmerAnimation();
                                    shimmerFrameLayout.setVisibility(View.GONE);
                                    button.setVisibility(View.VISIBLE);
                                    info.setVisibility(View.VISIBLE);

                                    relativeLayout.setVisibility(View.VISIBLE);
                                    refreshLayout.setRefreshing(false);
                                }

                            }else{
                                shimmerFrameLayout.stopShimmerAnimation();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                button.setVisibility(View.VISIBLE);
                                info.setVisibility(View.VISIBLE);
                                relativeLayout.setVisibility(View.VISIBLE);
                                refreshLayout.setRefreshing(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }else{


                    refreshLayout.setRefreshing(false);
                    Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();


                }
                if(refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);


            }
        });

        if(SharedPref.readSharedSettingsIsThereAPendingRequest(context,"check",false)) {


            String help = databaseReference.child(firebaseUser.getUid()).child("pending request").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("helpId").push().getKey();
            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pending request").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("helpId").setValue(help);

        }else{
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()==NetworkInfo.State.CONNECTED||connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED) {


                    databaseReference.child(firebaseUser.getUid()).child("pending request")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {


                                if (dataSnapshot.child("status").getValue().toString().equals("Yes")) {



                                    String s1="";
                                    if(dataSnapshot.child("MONDAY").getValue().toString().equals("MONDAY")){
                                        s1=s1+"MONDAY\n";
                                    } if(dataSnapshot.child("TUESDAY").getValue().toString().equals("TUESDAY")){
                                        s1=s1+"TUESDAY\n";
                                    } if(dataSnapshot.child("WEDNESDAY").getValue().toString().equals("WEDNESDAY")){
                                        s1=s1+"WEDNESDAY\n";
                                    } if(dataSnapshot.child("THURSDAY").getValue().toString().equals("THURSDAY")){
                                        s1=s1+"THURSDAY\n";
                                    } if(dataSnapshot.child("FRIDAY").getValue().toString().equals("FRIDAY")){
                                        s1=s1+"FRIDAY\n";
                                    } if(dataSnapshot.child("SATURDAY").getValue().toString().equals("SATURDAY")){
                                        s1=s1+"SATURDAY\n";
                                    } if(dataSnapshot.child("SUNDAY").getValue().toString().equals("SUNDAY")){
                                        s1=s1+"SUNDAY\n";
                                    }
                                    TextView z=(TextView)view.findViewById(R.id.days);

                                    z.setText(s1);
                                    z.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));

                                    TextView from=(TextView)view.findViewById(R.id.timeFrom);
                                    from.setText(dataSnapshot.child("fromTime").getValue().toString());
                                    from.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));
                                    TextView to=(TextView)view.findViewById(R.id.timeTo);
                                    to.setText(dataSnapshot.child("toTime").getValue().toString());
                                    to.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));


                                    shimmerFrameLayout.stopShimmerAnimation();
                                    button.setVisibility(View.GONE);
                                    info.setVisibility(View.GONE);
                                    relativeLayout.setVisibility(View.GONE);
                                   shimmerFrameLayout.setVisibility(View.GONE);
                                    layout.setVisibility(View.VISIBLE);


                                    ((TextView)view.findViewById(R.id.displayDate)).setText(dataSnapshot.child("date").getValue().toString());
                                    displayPrice.setText("Rs "+dataSnapshot.child("total").getValue().toString());

                                    refreshLayout.setRefreshing(false);

                                }else{
                                    shimmerFrameLayout.stopShimmerAnimation();
                                    shimmerFrameLayout.setVisibility(View.GONE);
                                    button.setVisibility(View.VISIBLE);
                                    info.setVisibility(View.VISIBLE);
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    refreshLayout.setRefreshing(false);
                                }

                            }else{
                                shimmerFrameLayout.stopShimmerAnimation();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                button.setVisibility(View.VISIBLE);
                                info.setVisibility(View.VISIBLE);
                                relativeLayout.setVisibility(View.VISIBLE);
                                refreshLayout.setRefreshing(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }else{


                    refreshLayout.setRefreshing(false);
                    Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();


                }

                if(refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);




            }
        });
        assert getParentFragment() != null;
        final Toolbar toolbar=getParentFragment().getView().findViewById(R.id.toolbar1);
        TabLayout tabLayout1= getParentFragment().getView().findViewById(R.id.userTabLayout);
        appBarLayout=getParentFragment().getView().findViewById(R.id.abl);
      final View tab= tabLayout1.getTabAt(TAB_POS).view;



      handle=new Handler();

      activity=getActivity();
      if(!SharedPref.readSharedSettingsFirstOnBoarding(context,"firstOnBoarding",false)) {



          Runnable runnable=new Runnable() {
              @Override
              public void run() {

                  new TapTargetSequence(activity)
                          .targets(
                                  TapTarget.forView(button, "Book appointment", "Click to book your water tank cleaning appointment any time.")
                                          .tintTarget(false)
                                          .id(0)
                                          .outerCircleColor(R.color.colorAccent)
                                          .textTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf")),
                                  TapTarget.forToolbarNavigationIcon(toolbar, "Other options","Some more app features here.")
                                          .tintTarget(false)
                                          .id(1)
                                          .outerCircleColor(R.color.colorAccent)
                                          .icon(getResources().getDrawable(R.drawable.ic_menu))
                                          .textTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf")),
                                  TapTarget.forView(tab, "Previous history","You can view your previous request history from here.")
                                          .outerCircleColor(R.color.colorAccent)
                                          .id(2)
                                          .textTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"))

                          ).continueOnCancel(true).listener(new TapTargetSequence.Listener() {
                      @Override
                      public void onSequenceFinish() {

                          SharedPref.saveSharedSettingsFirstOnBoarding(context, "firstOnBoarding", true);

                      }

                      @Override
                      public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {


                          if(lastTarget.id()==0)
                              appBarLayout.setExpanded(true);



                      }


                      @Override
                      public void onSequenceCanceled(TapTarget lastTarget) {

                      }
                  }).start();
              }
          };
          handle.postDelayed(runnable,100);

      }



        button.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {

                FullScreenDialog dialogFragment=FullScreenDialog.newInstance();
                dialogFragment.setContext(CurrentRequestTab1.this);

                FragmentTransaction fragmentTransaction=getChildFragmentManager().beginTransaction();
                dialogFragment.show(fragmentTransaction,"tag");


            }
        });




        cancelRequest=view.findViewById(R.id.cancelREquest);


        cancelRequest.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf"));






        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function1();
                function();


            }
        });


        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bill bill=new Bill();
                bill.show(getChildFragmentManager(),"bill");


            }
        });


        FirebaseDatabase.getInstance().getReference().child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if (snapshot.child("uid").getValue().toString().equals(firebaseUser.getUid())) {

                        if(snapshot.child("status").getValue().toString().equals("Done")){

                            shimmerFrameLayout.stopShimmerAnimation();
                            layout.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.GONE);
                            button.setVisibility(View.VISIBLE);
                            info.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.VISIBLE);


                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }


    private void automateSlider() {

        isCountDownTimerActive=true;
        new CountDownTimer(3000,3000){

            @Override
            public void onTick(long millisUntilFinished) {
                int nextPage=currentPage+1;
                if(nextPage==3){
                    nextPage=0;
                }
                viewPager.setCurrentItem(nextPage);

            }

            @Override
            public void onFinish() {
                isCountDownTimerActive=false;
            }
        }.start();

    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);

    }


    private void function() {

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
      databaseReference.child("admin").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()&&dataSnapshot.child("waiting").getValue().toString().equals("yes")) {

                    dataSnapshot.child("waiting").getRef().setValue("no");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    ChildEventListener childEventListener;

    public void function1(){

        databaseReference.child(firebaseUser.getUid()).child("pending request").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){

                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure to cancel cleaning request? ");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            layout.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.GONE);
                            button.setVisibility(View.VISIBLE);
                            info.setVisibility(View.VISIBLE);
                           relativeLayout.setVisibility(View.VISIBLE);
                           shimmerFrameLayout.stopShimmerAnimation();


                            dataSnapshot.child("status").getRef().setValue("cancel");
                            SharedPref.saveSharedSettingsIsThereAPendingRequest(context,"check",false);

                      childEventListener= databaseReference1.child("admin").child("pending").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    if(dataSnapshot.exists()) {



                                            if (dataSnapshot.child("uid").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                                                dataSnapshot.child("status").getRef().setValue("cancel");



                                        }
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
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

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
        if(childEventListener!=null)
        databaseReference1.child("admin").child("pending").removeEventListener(childEventListener);
    }

    @Override
    public void onBottomSheetListener(View v) {

    }

    @Override
    public void onRequestSent() {

        databaseReference.child(firebaseUser.getUid()).child("pending request")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    if (dataSnapshot.child("status").getValue().toString().equals("Yes")) {



                        String s1="";
                        if(dataSnapshot.child("MONDAY").getValue().toString().equals("MONDAY")){
                            s1=s1+"MONDAY\n";
                        } if(dataSnapshot.child("TUESDAY").getValue().toString().equals("TUESDAY")){
                            s1=s1+"TUESDAY\n";
                        } if(dataSnapshot.child("WEDNESDAY").getValue().toString().equals("WEDNESDAY")){
                            s1=s1+"WEDNESDAY\n";
                        } if(dataSnapshot.child("THURSDAY").getValue().toString().equals("THURSDAY")){
                            s1=s1+"THURSDAY\n";
                        } if(dataSnapshot.child("FRIDAY").getValue().toString().equals("FRIDAY")){
                            s1=s1+"FRIDAY\n";
                        } if(dataSnapshot.child("SATURDAY").getValue().toString().equals("SATURDAY")){
                            s1=s1+"SATURDAY\n";
                        } if(dataSnapshot.child("SUNDAY").getValue().toString().equals("SUNDAY")){
                            s1=s1+"SUNDAY\n";
                        }
                        TextView z=(TextView)view.findViewById(R.id.days);

                        z.setText(s1);
                        z.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));

                        TextView from=(TextView)view.findViewById(R.id.timeFrom);
                        from.setText(dataSnapshot.child("fromTime").getValue().toString());
                        from.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));
                        TextView to=(TextView)view.findViewById(R.id.timeTo);
                        to.setText(dataSnapshot.child("toTime").getValue().toString());
                        to.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));


                        shimmerFrameLayout.stopShimmerAnimation();
                        button.setVisibility(View.GONE);
                        info.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);


                        ((TextView)view.findViewById(R.id.displayDate)).setText(dataSnapshot.child("date").getValue().toString());
                        displayPrice.setText("Rs "+dataSnapshot.child("total").getValue().toString());

                        refreshLayout.setRefreshing(false);

                    }else{
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        info.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                    }

                }else{
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onRepeated() {

        databaseReference.child(firebaseUser.getUid()).child("pending request")
                .child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    if (dataSnapshot.child("status").getValue().toString().equals("Yes")) {



                        String s1="";
                        if(dataSnapshot.child("MONDAY").getValue().toString().equals("MONDAY")){
                            s1=s1+"MONDAY\n";
                        } if(dataSnapshot.child("TUESDAY").getValue().toString().equals("TUESDAY")){
                            s1=s1+"TUESDAY\n";
                        } if(dataSnapshot.child("WEDNESDAY").getValue().toString().equals("WEDNESDAY")){
                            s1=s1+"WEDNESDAY\n";
                        } if(dataSnapshot.child("THURSDAY").getValue().toString().equals("THURSDAY")){
                            s1=s1+"THURSDAY\n";
                        } if(dataSnapshot.child("FRIDAY").getValue().toString().equals("FRIDAY")){
                            s1=s1+"FRIDAY\n";
                        } if(dataSnapshot.child("SATURDAY").getValue().toString().equals("SATURDAY")){
                            s1=s1+"SATURDAY\n";
                        } if(dataSnapshot.child("SUNDAY").getValue().toString().equals("SUNDAY")){
                            s1=s1+"SUNDAY\n";
                        }
                        TextView z=(TextView)view.findViewById(R.id.days);

                        z.setText(s1);
                        z.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));

                        TextView from=(TextView)view.findViewById(R.id.timeFrom);
                        from.setText(dataSnapshot.child("fromTime").getValue().toString());
                        from.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));
                        TextView to=(TextView)view.findViewById(R.id.timeTo);
                        to.setText(dataSnapshot.child("toTime").getValue().toString());
                        to.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));


                        shimmerFrameLayout.stopShimmerAnimation();
                        button.setVisibility(View.GONE);
                        info.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);


                        ((TextView)view.findViewById(R.id.displayDate)).setText(dataSnapshot.child("date").getValue().toString());
                        displayPrice.setText("Rs "+dataSnapshot.child("total").getValue().toString());

                        refreshLayout.setRefreshing(false);

                    }else{
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        info.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                    }

                }else{
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
