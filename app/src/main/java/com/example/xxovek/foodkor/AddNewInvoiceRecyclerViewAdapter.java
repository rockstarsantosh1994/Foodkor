package com.example.xxovek.foodkor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class AddNewInvoiceRecyclerViewAdapter extends RecyclerView.Adapter<AddNewInvoiceRecyclerViewAdapter.ViewHolder> {

    public List<String> mData1,mData2,mData3,mData4,mData5,mData6,mData7,mData8;
    public List<String> mData9,mData10,mData11,mData12,mData13,mData14,mData15;
    private LayoutInflater mInflater;
    private static ItemClickListener mClickListener;
    private String mData;
    private String flag="0";

    // data is passed into the constructor
    /*public AddNewInvoiceRecyclerViewAdapter(Context context, List<String> data1, List<String> data2, List<String> data3, List<String> data4, List<String> data5,
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
    }*/

    public AddNewInvoiceRecyclerViewAdapter(Context context, List<String> mData1, List<String> mData2, List<String> mData3,
                                            List<String> mData4, List<String> mData5, List<String> mData6, List<String> mData7,
                                            List<String> mData8, List<String> mData9, List<String> mData10, List<String> mData11,
                                            List<String> mData12, List<String> mData13, List<String> mData14, List<String> mData15,String mData) {

        this.mInflater = LayoutInflater.from(context);
        this.mData1 = mData1;
        this.mData2 = mData2;
        this.mData3 = mData3;
        this.mData4 = mData4;
        this.mData5 = mData5;
        this.mData6 = mData6;
        this.mData7 = mData7;
        this.mData8 = mData8;
        this.mData9 = mData9;
        this.mData10 = mData10;
        this.mData11 = mData11;
        this.mData12 = mData12;
        this.mData13 = mData13;
        this.mData14 = mData14;
        this.mData15 = mData15;

        this.mData = mData;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_add_new_invoice, parent, false);

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
        String t9 = mData9.get(position);
        String t10= mData10.get(position);
        String t11= mData11.get(position);
        String t12= mData12.get(position);
        String t13= mData13.get(position);
        String t14= mData14.get(position);
        String t15= mData15.get(position);



        flag = mData;



        holder.myTextView1.setText(t1);
        holder.myTextView2.setText(t2);
        holder.myTextView3.setText(t3);
        holder.myTextView4.setText(t4);
        holder.myTextView5.setText(t5);
        holder.myTextView6.setText(t6);
        holder.myTextView7.setText(t7);
        holder.myTextView8.setText(t8);
        holder.myTextView9.setText(t9);
        holder.myTextView10.setText(t10);
        holder.myTextView11.setText(t11);
        holder.myTextView12.setText(t12);
        holder.myTextView13.setText(t13);
        holder.myTextView14.setText(t14);
        holder.myTextView15.setText(t15);


        if (flag.equals("0")){
            holder.myTextView6.setVisibility(View.GONE);
            holder.myTextView7.setVisibility(View.GONE);
            holder.myTextView8.setVisibility(View.GONE);
            holder.myTextView9.setVisibility(View.GONE);
            holder.myTextView10.setVisibility(View.GONE);
            holder.myTextView11.setVisibility(View.GONE);
            holder.myTextView12.setVisibility(View.GONE);
            holder.myTextView13.setVisibility(View.GONE);
            holder.myTextView14.setVisibility(View.GONE);
            holder.myTextView15.setVisibility(View.GONE);

        }
        else if(flag.equals("1")){


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
        TextView myTextView9,myTextView10,myTextView11,myTextView12,myTextView13,myTextView14,myTextView15;

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
            myTextView9 = itemView.findViewById(R.id.t9);
            myTextView10= itemView.findViewById(R.id.t10);
            myTextView11= itemView.findViewById(R.id.t11);
            myTextView12= itemView.findViewById(R.id.t12);
            myTextView13= itemView.findViewById(R.id.t13);
            myTextView14= itemView.findViewById(R.id.t14);
            myTextView15= itemView.findViewById(R.id.t15);
               ib_delete=itemView.findViewById(R.id.ib_delete);


            myTextView1.setOnClickListener(this);
            myTextView2.setOnClickListener(this);
            myTextView3.setOnClickListener(this);
            myTextView4.setOnClickListener(this);
            myTextView5.setOnClickListener(this);
            myTextView6.setOnClickListener(this);
            myTextView7.setOnClickListener(this);
            myTextView8.setOnClickListener(this);
            ib_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {mClickListener.onItemClick(view, getAdapterPosition());}
        }


    }

    // convenience method for getting data at click positionn
    public String getItemDetailId(int id) { return mData9.get(id); }

    public String getQty(int id) {
        return mData3.get(id);
    }

    public String getBillingQty(int id) {
        return mData4.get(id);
    }

    public String getRate(int id) {
        return mData5.get(id);
    }

    public String getDiscount(int id) {
        return mData6.get(id);
    }

    public String getUnitId(int id) {
        return mData10.get(id);
    }

    public String getSubPackingQty(int id) {
        return mData11.get(id);
    }

    public String getPackingQty(int id) {
        return mData12.get(id);
    }

    public String getTotalQty(int id) {
        return mData13.get(id);
    }

    public String getQuantityById(int id) { return mData14.get(id); }

    public String getTax(int id) {
        return mData15.get(id);
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