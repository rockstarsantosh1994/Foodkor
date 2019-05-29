package com.example.xxovek.foodkor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData1,mData2,mData3,mData4,mData5,mData6,mData7,mData8;
    private LayoutInflater mInflater;
    private static ItemClickListener mClickListener;
    private String mData;
    private String flag="0";

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<String> data1, List<String> data2, List<String> data3, List<String> data4, List<String> data5,
                                 List<String> data6, List<String> data7, List<String> data8, String mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData1 = data1;
        this.mData2 = data2;
        this.mData3 = data3;
        this.mData4 = data4;
        this.mData5 = data5;
        this.mData6 = data6;
        this.mData7 = data7;
        this.mData8 = data8;
        this.mData = mData;




    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String t1 = mData1.get(position);
        String t2 = mData2.get(position);
        String t3 = mData3.get(position);
        String t4 = mData4.get(position);
        String t5 = mData5.get(position);
        String t6 = mData6.get(position);
        String t7 = mData7.get(position);
        String t8 = mData8.get(position);


        flag = mData;



        holder.myTextView1.setText(t1);
        holder.myTextView2.setText(t2);
        holder.myTextView3.setText(t3);
        holder.myTextView4.setText(t4);
        holder.myTextView5.setText(t5);
        holder.myTextView6.setText(t6);
        holder.myTextView7.setText(t7);
        holder.myTextView8.setText(t8);


        if (flag.equals("0")){
            holder.myTextView6.setVisibility(View.GONE);
            holder.myTextView7.setVisibility(View.GONE);
            holder.myTextView8.setVisibility(View.GONE);
        }
        else if(flag.equals("1")){
            holder.myTextView5.setVisibility(View.GONE);
            holder.myTextView6.setVisibility(View.GONE);
            holder.myTextView7.setVisibility(View.GONE);
            holder.myTextView8.setVisibility(View.GONE);

        }else if(flag.equals("2")){
            holder.myTextView1.setVisibility(View.GONE);
            holder.myTextView2.setVisibility(View.GONE);
            holder.myTextView3.setVisibility(View.GONE);

        }
        else if(flag.equals("4")){
            holder.ib_delete.setVisibility(View.GONE);

        }
        else if(flag.equals("5")){

        }



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData1.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView1,myTextView2,myTextView3,myTextView4,myTextView5,myTextView6,myTextView7,myTextView8;
        //public Spinner siteSpinner;
        ImageButton ib_delete;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView1 = itemView.findViewById(R.id.t1);
            myTextView2 = itemView.findViewById(R.id.t2);
            myTextView3 = itemView.findViewById(R.id.t3);
            myTextView4 = itemView.findViewById(R.id.t4);
            myTextView5 = itemView.findViewById(R.id.t5);
            myTextView6 = itemView.findViewById(R.id.t6);
            myTextView7 = itemView.findViewById(R.id.t7);
            myTextView8 = itemView.findViewById(R.id.t8);
               ib_delete=itemView.findViewById(R.id.ib_delete);


            myTextView1.setOnClickListener(this);
            myTextView2.setOnClickListener(this);
            ib_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {mClickListener.onItemClick(view, getAdapterPosition());}
        }


    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData1.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}