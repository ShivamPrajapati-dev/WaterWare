package com.shivamprajapati.waterware;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {

    Context context;
    private List<DaySelected> daySelectedList;



    DayListAdapter(List<DaySelected> objects) {

        this.daySelectedList=objects;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.day_selector_layout,viewGroup,false);
        context=viewGroup.getContext();
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        viewHolder.textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/fonttwo.ttf"));
        viewHolder.textView.setText(daySelectedList.get(i).getDay());
        viewHolder.checkBox.setChecked(daySelectedList.get(i).isSeleted());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                daySelectedList.get(i).setSeleted(isChecked);

            }
        });


    }

    @Override
    public int getItemCount() {
        return daySelectedList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.nameofday);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkboxday);

        }
    }

}
