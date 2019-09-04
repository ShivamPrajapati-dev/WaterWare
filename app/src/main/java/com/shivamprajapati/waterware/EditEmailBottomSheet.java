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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditEmailBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener bottomSheetListener;
    ImageView close;
    TextView save;
    EditText editText;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.edit_email,container,false);

        bottomSheetListener.onBottomSheetListener(view);
        context=getContext();
        close=view.findViewById(R.id.closeEditEmail);
        save=view.findViewById(R.id.saveNewEmail);
        editText=view.findViewById(R.id.newEmail);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(context,"Enter valid address",Toast.LENGTH_LONG).show();
                }
                else {
                    assert getParentFragment() != null;
                    TextView x=getParentFragment().getView().findViewById(R.id.emailNotProvided);
                    x.setText(editText.getText().toString().trim());
                    SharedPref.saveSharedSettingsEmail(context,"email",editText.getText().toString().trim());
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
            bottomSheetListener= (EditEmailBottomSheet.BottomSheetListener) getParentFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement BottomSheetListener");
        }

    }
}
