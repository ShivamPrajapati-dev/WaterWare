package com.shivamprajapati.waterware;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class UserProfileFragment extends Fragment implements EditNameBottomSheet.BottomSheetListener,EditAddressBottomSheet.BottomSheetListener,EditEmailBottomSheet.BottomSheetListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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

    TextView name,phoneNumber,address,email,ph,ad,em;
    ImageView editName,editAddress,editPhoneNumber,editEmail;
    ImageButton closeEditName,closeEditAdress;
    DrawerLayout drawerLayout;
    Context context;
    EditNameBottomSheet bottomSheet1;
    EditAddressBottomSheet bottomSheet2;
    EditEmailBottomSheet bottomSheet3;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_user_profile, container, false);
        name=(TextView)view.findViewById(R.id.savedName);
        phoneNumber=(TextView)view.findViewById(R.id.textView15);
        address=(TextView)view.findViewById(R.id.textView16);
        email=(TextView)view.findViewById(R.id.textView166);
        editName=view.findViewById(R.id.editName);
        editAddress=view.findViewById(R.id.editAddress);
        editPhoneNumber=view.findViewById(R.id.editPhoneNumber);
        editEmail=view.findViewById(R.id.editEmail);
        context=getContext();

        assert context != null;
        if(SharedPref.readSharedSettingsIsThereAPendingRequest(context,"check",false)) {
            editName.setVisibility(View.GONE);
            editAddress.setVisibility(View.GONE);
            editPhoneNumber.setVisibility(View.GONE);
        }


            name.setText(SharedPref.readSharedSettingsName(context,"nameOfUser",""));


        ph=(TextView)view.findViewById(R.id.textView14);
        ad=(TextView)view.findViewById(R.id.addressNotProvided);
        em=(TextView)view.findViewById(R.id.emailNotProvided);

        ph.setText(SharedPref.readSharedSettingsPhoneNumber(context,"phoneNoOfUser",""));
        ad.setText(SharedPref.readSharedSettingsAddress(context,"addressOfUser",""));
        if(!SharedPref.readSharedSettingsEmail(context,"email","").equals("")){
            em.setText(SharedPref.readSharedSettingsEmail(context,"email",""));
        }

        if(SharedPref.readSharedSettingsIsLoginPhone(context,"setLoginPhone",false)){
            editEmail.setVisibility(View.VISIBLE);
        }
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"font/myfont.ttf");
        Typeface typeface1=Typeface.createFromAsset(getActivity().getAssets(),"font/fonttwo.ttf");
        name.setTypeface(typeface);
        phoneNumber.setTypeface(typeface);
        address.setTypeface(typeface);
        email.setTypeface(typeface);

        ph.setTypeface(typeface1);
        ad.setTypeface(typeface1);
        em.setTypeface(typeface1);


        Toolbar toolbar = view.findViewById(R.id.profileToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        drawerLayout=getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bottomSheet1=new EditNameBottomSheet();
        bottomSheet2=new EditAddressBottomSheet();
        bottomSheet3=new EditEmailBottomSheet();



        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheet1.show(getChildFragmentManager(),"editName");



            }
        });
        editAddress.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                bottomSheet2.show(getChildFragmentManager(),"editAddress");



            }
        });

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheet3.show(getChildFragmentManager(),"editEmail");
            }
        });




        editPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context,EditPhoneNumber.class));

            }
        });



        return view;
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
    }

    @Override
    public void onBottomSheetListener(View view){


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
