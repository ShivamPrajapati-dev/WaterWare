package com.shivamprajapati.waterware;

import android.content.Intent;

import android.graphics.Typeface;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;

import android.content.Context;

import java.util.List;

public class PreviousHistoryRecyclerViewAdapter extends RecyclerView.Adapter<PreviousHistoryRecyclerViewAdapter.ViewHolder> {

    private List<PreviousHistory> previousHistoryList;
    Intent intent;
    Context context;



    PreviousHistoryRecyclerViewAdapter(List<PreviousHistory> previousHistoryList) {
        this.previousHistoryList = previousHistoryList;


    }


    @NonNull
    @Override
    public PreviousHistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.previous_history_list_layout,viewGroup,false);

        context=viewGroup.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PreviousHistoryRecyclerViewAdapter.ViewHolder viewHolder, final int i) {


        viewHolder.textView.setText(previousHistoryList.get(i).getDate());
        viewHolder.name.setText(previousHistoryList.get(i).getName());
        viewHolder.phone.setText(previousHistoryList.get(i).getPhone());
        viewHolder.cost.setText("Rs "+previousHistoryList.get(i).getTotalCharges());


        viewHolder.name.setTypeface(Typeface.createFromAsset(viewHolder.name.getContext().getAssets(),"font/fonttwo.ttf"));

        viewHolder.textView.setTypeface(Typeface.createFromAsset(viewHolder.textView.getContext().getAssets(),"font/myfont.ttf"));

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent=new Intent(context,CompletedOrder.class);
                intent.putExtra("name",previousHistoryList.get(i).getName());
                intent.putExtra("phone",previousHistoryList.get(i).getPhone());
                intent.putExtra("address",previousHistoryList.get(i).getAddress());
                intent.putExtra("date",previousHistoryList.get(i).getDate());
                intent.putExtra("days",previousHistoryList.get(i).getDays());
                intent.putExtra("fromTime",previousHistoryList.get(i).getFromTime());
                intent.putExtra("toTime",previousHistoryList.get(i).getToTime());
                intent.putExtra("cleaning",previousHistoryList.get(i).getCleaningCharges());
                intent.putExtra("visiting",previousHistoryList.get(i).getVisitingCharges());
                intent.putExtra("total",previousHistoryList.get(i).getTotalCharges());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return previousHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView,name,add,phone,cost;
        RelativeLayout relativeLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.DOC);
            textView = (TextView) itemView.findViewById(R.id.date);
            phone=(TextView)itemView.findViewById(R.id.idone);
            cost=itemView.findViewById(R.id.tcost);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.shortDetails);



        }


    }

}
