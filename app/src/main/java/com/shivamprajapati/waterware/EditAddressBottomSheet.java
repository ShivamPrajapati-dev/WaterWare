package com.shivamprajapati.waterware;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditAddressBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener bottomSheetListener;

    ImageButton closeEditAddress;
    EditText editAddress;
    TextView save;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.edit_address,container,false);
        bottomSheetListener.onBottomSheetListener(view);


        context=view.getContext();
        closeEditAddress=view.findViewById(R.id.closeEditAddress);
        closeEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        editAddress=view.findViewById(R.id.newAddress);
        save=view.findViewById(R.id.saveNewAddress);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editAddress.getText().toString())){
                    Toast.makeText(context,"Enter valid address",Toast.LENGTH_LONG).show();
                }
                else {
                    assert getParentFragment() != null;
                    TextView x=getParentFragment().getView().findViewById(R.id.addressNotProvided);
                    x.setText(editAddress.getText().toString().trim());
                    SharedPref.saveSharedSettingsAddress(context,"addressOfUser",editAddress.getText().toString().trim());
                    dismiss();
                }
            }
        });
        return view;
    }
    public interface BottomSheetListener{
        void onBottomSheetListener(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            bottomSheetListener= (EditAddressBottomSheet.BottomSheetListener) getParentFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement BottomSheetListener");
        }

    }
}
