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

public class EditNameBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener bottomSheetListener;

    ImageButton closeEditName;
    EditText editName;
    TextView save;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.edit_name,container,false);
        bottomSheetListener.onBottomSheetListener(view);

        context=view.getContext();

        closeEditName=view.findViewById(R.id.closeEditName);

        closeEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        editName=view.findViewById(R.id.editName);
        save=view.findViewById(R.id.saveNewName);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editName.getText().toString())){
                    Toast.makeText(context,"Enter valid name",Toast.LENGTH_LONG).show();
                }else{
                    assert getParentFragment() != null;
                    TextView x=getParentFragment().getView().findViewById(R.id.savedName);
                    x.setText(editName.getText().toString().trim());
                    SharedPref.saveSharedSettingsName(context,"nameOfUser",editName.getText().toString().trim());
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
            bottomSheetListener= (EditNameBottomSheet.BottomSheetListener) getParentFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement BottomSheetListener");
        }

    }
}
