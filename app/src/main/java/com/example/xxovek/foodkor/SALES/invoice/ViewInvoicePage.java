package com.example.xxovek.foodkor.SALES.invoice;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xxovek.foodkor.MyRecyclerViewAdapter;
import com.example.xxovek.foodkor.R;
import com.example.xxovek.foodkor.URLs.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.valueOf;


/**
 * A simple {@link Fragment} subclass.
 */

public class ViewInvoicePage extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView rv_viewitem,recyclerView1;
    private String st_transactionid;
    public MyRecyclerViewAdapter adapter,adapter1;
    public StringRequest stringRequest,stringRequest1,stringRequest2,stringRequest3,stringRequest4,stringRequest5,stringRequest6,stringRequest7,stringRequest8,stringRequest11,stringRequest12;
    Spinner spin_customername,spin_terms,spinner1,spinner2,spin_tax;
    HashMap spinnerMap,spinnerMap1;
    String[] spinnerArray;
    String st_customervalue,st_spinner1,form_id;
    String company_id,st_termsvalue,etamount2,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
    int nosofdays;
    EditText et_invoicedate,et_duedate,et_billaddress;
    private int mYear, mMonth, mDay;
    FloatingActionButton fab_addinvoice;
    TextInputEditText etbqty,etrate,etdiscount,etamount,ethsn,etqty;
    private ArrayList al1,al2,al3,al4,al5,al6,al7,al8,ql1,ql2,ql3,ql4,ql5,ql6,ql7,ql8,a,zl1,zl2,ql7_copy,newStr,newStr_copy;
    public String[] spinnerArray3,spinnerArray4;
    HashMap spinnerMap2,spinnerMap4;
    EditText et_subtotal,et_lasttotal,et_lastfinaltotal,et_lastbalancedue,et_finaldiscount;
    public int check;
    int st_totalqty, st_unitpostion,st_subpackingqty,st_packingqty,current_edittext1,in_bqty;
    double st_price,rate_editext1,dou_bqty,dou_etdiscount,st_packingqtydouble,st_totalqtydouble,do_inbqty,bag,amount,sum1=0.0,total1;
    public boolean hasFocus1=true;
    String  st_taxvalue, st_quantitybyid,  st_unitid,amount_nos,no3,dis_amount,amount_temp;
    public Button btn_getallids;
    private String[] actual_amount_str;


    public ViewInvoicePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        st_transactionid = getArguments().getString("data");
        form_id = getArguments().getString("formid");

        Toast.makeText(getActivity(),"st_Transactionid"+st_transactionid,Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(),"form_id"+form_id,Toast.LENGTH_LONG).show();

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_invoice_page, container, false);
        rv_viewitem=view.findViewById(R.id.rv_addinvoice);
        recyclerView1=view.findViewById(R.id.rv_addgst);
        /*spin_customername=view.findViewById(R.id.spin_customernames);
        spin_terms=view.findViewById(R.id.spin_terms);
        et_invoicedate=view.findViewById(R.id.et_invoicedate);
        et_duedate=view.findViewById(R.id.et_duedate);
        et_billaddress=view.findViewById(R.id.et_billingaddress);
        fab_addinvoice=view.findViewById(R.id.fab_addinvoice);
*/
        fab_addinvoice = view.findViewById(R.id.showalertdialog);

        spin_customername = view.findViewById(R.id.customernames);
        spin_terms = view.findViewById(R.id.termss);
        et_invoicedate = view.findViewById(R.id.invoicedatee);
        et_duedate = view.findViewById(R.id.duedatee);
        et_billaddress=view.findViewById(R.id.billingaddresse);
        et_subtotal = view.findViewById(R.id.et_subtotal);
        btn_getallids = view.findViewById(R.id.btn_getdata);
        et_lasttotal = view.findViewById(R.id.et_lasttotal);
        et_subtotal = view.findViewById(R.id.et_subtotal);
        et_lastfinaltotal = view.findViewById(R.id.et_lastfinaltotal);
        et_lastbalancedue = view.findViewById(R.id.et_lastbalancedue);
        et_finaldiscount = view.findViewById(R.id.et_finaldiscount);



        //Loading all Product name in Spinner
        //LoadAllProductInSpin();


        //Updating Product Information Based Product Id which is get from LoadAllProductInSpin() Function
        getProductDetailsById();

        //Attaching PayTerms Value to Spinner...
        getPayTermsSpin();

        //Attaching CustomerName to Spinner....
        getCustomerNameSpin();



        final String date=new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        et_invoicedate.setText(date);

        al1 = new ArrayList<String>();
        al2 = new ArrayList<String>();
        al3 = new ArrayList<String>();
        al4 = new ArrayList<String>();
        al5 = new ArrayList<String>();
        al6 = new ArrayList<String>();
        al7 = new ArrayList<String>();
        al8 = new ArrayList<String>();

        ql1 = new ArrayList<String>();
        ql2 = new ArrayList<String>();
        ql3 = new ArrayList<String>();
        ql4 = new ArrayList<String>();
        ql5 = new ArrayList<String>();
        ql6 = new ArrayList<String>();
        ql7 = new ArrayList<String>();
        ql8 = new ArrayList<String>();

        zl1 = new ArrayList<String>();
        zl2 = new ArrayList<String>();

        ql7_copy = new ArrayList<String>();
        newStr = new ArrayList<String>();

        newStr_copy = new ArrayList<String>();


        a = new ArrayList<String>();


        et_invoicedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                et_invoicedate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                Calendar c = Calendar.getInstance();
                                try{
                                    //Setting the date to the given date
                                    c.setTime(sdf.parse(et_invoicedate.getText().toString()));
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }

                                c.add(Calendar.DAY_OF_MONTH, nosofdays);
                                String newDate = sdf.format(c.getTime());
                                Toast.makeText(getContext(), "New Date"+newDate.toString(), Toast.LENGTH_SHORT).show();

                                et_duedate.setText(newDate);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        fab_addinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                builder.setView(dialogView);
                final Dialog dialog = builder.create();
                spinner1 = dialogView.findViewById(R.id.spinner1);
                spinner2 = dialogView.findViewById(R.id.spinner2);
                spin_tax = dialogView.findViewById(R.id.spin_tax);

                getProductDataSpin();
                /*RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
*/
                getUnitData();
                /*RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                requestQueue1.add(stringRequest2);*/


                ethsn = dialogView.findViewById(R.id.ethsn);
                etqty = dialogView.findViewById(R.id.etqty);
                etqty.setText("1");
                etbqty = dialogView.findViewById(R.id.etbqty);
                etbqty.setText("1");
                etrate = dialogView.findViewById(R.id.etrate);
                etdiscount = dialogView.findViewById(R.id.etdiscount);
                etdiscount.setText("0.00");
                etamount = dialogView.findViewById(R.id.etamount);

                etqty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        hasFocus1=hasFocus;

                        //Setting quantity to qty and billing quantity....
                        setQuantityForFocus();
                    }

                });


                etrate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        //Working on amount and Discount using below function .....as rate and discount change function will call....
                        //setAmountAndDiscount();

                        try {
                            current_edittext1 = Integer.parseInt(etqty.getText().toString());
                            rate_editext1=Double.parseDouble(etrate.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        if (st_unitpostion==0) {

                            String no1 = Integer.toString(st_totalqty);

                            if (current_edittext1 > st_totalqty) {

                                etbqty.setText(no1);
                                etqty.setText(etbqty.getText().toString());
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());

                                           //calculating amount and setting to amount
                                           double amount=in_bqty*rate_editext1;
                                           //.makeText(getContext(), "amount"+amount, //.LENGTH_SHORT).show();*/
                                //amount_nos=Double.toString(amount);
                                // etamount.setText(amount_nos);
                                //.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), //.LENGTH_SHORT).show();



                            } else {

                                etbqty.setText(etqty.getText().toString());
                                etqty.setText(etbqty.getText().toString());

                                //calculating amount and setting to amount
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                           double amount=in_bqty*rate_editext1;
                                           amount_nos=Double.toString(amount);*/

                            }

                            int in_bqty=Integer.parseInt(etbqty.getText().toString());

                            //calculating amount and setting to amount
                            double amount=in_bqty*rate_editext1;
                            //calculating discount and updating amount
                            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                            //.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, //.LENGTH_SHORT).show();
                            double discout_amount=(amount*dou_etdiscount)/100;
                            //.makeText(getContext(), "discount"+discout_amount, //.LENGTH_SHORT).show();
                            double final_discount=amount-discout_amount;

                            String dis_amount=String.valueOf(final_discount);


                                etamount.setText(dis_amount);


                        }



                        else if(st_unitpostion==1) {

                            int packet=st_totalqty/st_subpackingqty;
                            String no2 = Integer.toString(packet);

                            if (current_edittext1 > st_totalqty) {

                                etbqty.setText(no2);
                                etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                //.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), //.LENGTH_SHORT).show();*/
                            } else {

                                etbqty.setText(etqty.getText().toString());
                                etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                //.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), //.LENGTH_SHORT).show();*/
                            }

                            int in_bqty=Integer.parseInt(etbqty.getText().toString());

                            //calculating amount and setting to amount
                            double amount=in_bqty*rate_editext1;
                            //calculating discount and updating amount
                            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                            //.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, //.LENGTH_SHORT).show();
                            double discout_amount=(amount*dou_etdiscount)/100;
                            //.makeText(getContext(), "discount"+discout_amount, //.LENGTH_SHORT).show();
                            double final_discount=amount-discout_amount;

                            String dis_amount=String.valueOf(final_discount);


                                etamount.setText(dis_amount);

                        }

                        else if(st_unitpostion==2){
                            double bag=st_totalqtydouble/st_packingqtydouble;
                            String no3 = Double.toString(bag);

                            if (current_edittext1 > st_totalqty) {
                                etbqty.setText(no3);
                                etqty.setText(etbqty.getText().toString());
                                try{
                                    dou_bqty=Integer.parseInt(etbqty.getText().toString());
                                }catch(NumberFormatException e){e.printStackTrace();}

                               /* //calculating amount and setting to amount
                                double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                //.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), //.LENGTH_SHORT).show();*/
                            } else {

                                etbqty.setText(etqty.getText().toString());
                                etqty.setText(etbqty.getText().toString());

                                try{
                                    dou_bqty=Integer.parseInt(etbqty.getText().toString());
                                }catch(NumberFormatException e){e.printStackTrace();}

//                                //calculating amount and setting to amount
                            /*    double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                //.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), //.LENGTH_SHORT).show();*/
                            }
                            try{do_inbqty=Double.parseDouble(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

                            //calculating amount and setting to amount
                            double amount=do_inbqty*rate_editext1*st_packingqtydouble;
                            //calculating discount and updating amount
                            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                            //.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, //.LENGTH_SHORT).show();
                            double discout_amount=(amount*dou_etdiscount)/100;
                            //.makeText(getContext(), "discount"+discout_amount, //.LENGTH_SHORT).show();
                            double final_discount=amount-discout_amount;

                            String dis_amount=String.valueOf(final_discount);

                            etamount.setText(dis_amount);
                        }
                    }
                });


                etdiscount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        //Working on amount and Discount using below function .....as rate and discount change function will call....
                        setAmountAndDiscount();
                    }
                });





                Button btnLogin = dialogView.findViewById(R.id.btnLogin);

//                //.makeText(AddNewInvoice.this.getContext(), a1.toString(), //.LENGTH_SHORT).show();

//                etUsername.setText(a1.toString());
//                etPassword.setText(a2.toString());

                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
//                        //.makeText(MainActivity.this, "User name : "+etUsername.getText().toString()+"\nPassword : "+etPassword.getText().toString(), //.LENGTH_LONG).show();

                        zl2.clear();
                        ql1.add(spinner1.getSelectedItem().toString());
                        ql2.add(spinner1.getSelectedItem().toString());
                        ql3.add(etqty.getText().toString());
                        ql4.add(etbqty.getText().toString());
                        ql5.add(etrate.getText().toString());
                        ql6.add(etdiscount.getText().toString());
                        ql7.add(etamount.getText().toString());
                        ql7_copy.add(etamount.getText().toString());
                        ql8.add(spin_tax.getSelectedItem().toString());
                        newStr.add(spin_tax.getSelectedItem().toString().substring(0, spin_tax.getSelectedItem().toString().indexOf("%")));
                        newStr_copy.add(spin_tax.getSelectedItem().toString().substring(0, spin_tax.getSelectedItem().toString().indexOf("%")));
                        amount_temp = etamount.getText().toString();

                        double al77_int = Double.parseDouble(amount_temp.trim());
                        sum1 = sum1+al77_int;

                        String sum2 = String.valueOf(sum1);
                        et_subtotal.setText(sum2);

                        Toast.makeText(getContext(), "tax is  " +  newStr.toString(), Toast.LENGTH_SHORT).show();

                        //.makeText(AddNewInvoice.this.getContext(), "QL7 IS\n\n" + ql7.toString() , //.LENGTH_SHORT).show();


                        try {

                            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewInvoicePage.this.getContext());

                            rv_viewitem.setLayoutManager(mLayoutManager);

                            rv_viewitem.setItemAnimator(new DefaultItemAnimator());
                            adapter = new MyRecyclerViewAdapter(getContext(), ql1, ql2, ql3, ql7, ql5, ql6, ql7, ql8, "11");
                            adapter.setClickListener(ViewInvoicePage.this);
                            rv_viewitem.setAdapter(adapter);
                            rv_viewitem.addItemDecoration(new DividerItemDecoration(getContext(),
                                    DividerItemDecoration.VERTICAL));
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {

                            final RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(ViewInvoicePage.this.getContext());

                            recyclerView1.setLayoutManager(mLayoutManager1);

                            int user_id2 = adapter.getItemCount();
                            String user_id21 = valueOf(user_id2);

                            //.makeText(getContext(), "id is  " + user_id21.toString() , //.LENGTH_SHORT).show();

                            int newstr_size = newStr.size();
                            for(int j=0;j<newstr_size-1;j++){



                                if(newStr.get(j).toString().equals(st_taxvalue.toString()) && (!ql7_copy.get(j).toString().equals("0"))){


                                    Toast.makeText(getContext(), "newstr is  " +  newStr.get(j).toString(), Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getContext(), "same tax  " + st_taxvalue.toString() , Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getContext(), "ql7 is  " + ql7_copy.get(j).toString().trim(), Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getContext(), "amount is  " + amount_temp.trim(), Toast.LENGTH_SHORT).show();


                                    double amount_temp_int = Double.parseDouble((amount_temp.trim()));
                                    double al7_int = Double.parseDouble(ql7_copy.get(j).toString().trim());
                                    double sum = al7_int+amount_temp_int;


                                    String sum_str= String.valueOf(sum);



                                    ql7_copy.set(j,sum_str);
                                    ql7_copy.set(ql7_copy.size()-1,"0");
                                    Toast.makeText(getContext(), "sum is  " + sum_str.toString(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "ql7 is  " + ql7_copy.toString(), Toast.LENGTH_SHORT).show();



                                }else {




                                }


                                Toast.makeText(getContext(), "tax is  " +  newStr.get(j).toString(), Toast.LENGTH_SHORT).show();



                            }

                            for(int l=0;l<newStr.size();l++) {


                                if (!(ql7_copy.get(l).toString().equals("0"))) {

                                    double amount_int_set = Double.parseDouble(ql7_copy.get(l).toString());
                                    double tax_int_set = Double.parseDouble(newStr.get(l).toString());
                                    double tax_int_set_half = tax_int_set / 2;

                                    double actual_amount = amount_int_set * 0.01 * tax_int_set_half;
                                    actual_amount_str[l] = String.valueOf(actual_amount);

                                } else {

                                    actual_amount_str[l] = "0";

                                }

                                Toast.makeText(getContext(), "actual value to be set is loop 1 " + actual_amount_str[l].toString(), Toast.LENGTH_SHORT).show();




                            }

                            for(int l=0;l<newStr.size();l++) {

                                /*double al77_int = Double.parseDouble(amount_temp.trim());
                                sum1 = sum1+al77_int;

                                String sum2 = String.valueOf(sum1);
                                et_lasttotal.setText(sum2);*/

                                if (!(ql7_copy.get(l).toString().equals("0"))) {

                                    zl2.add(actual_amount_str[l]);
                                    Toast.makeText(getContext(), "zl2 is " + zl2.toString(), Toast.LENGTH_SHORT).show();


                                }


                            }

                            for(int e=0;e<zl2.size();e++) {

                                double total = Double.parseDouble(zl2.get(e).toString().trim());
                                total1 = sum1+(total*2);

                            }

                            String total2 = String.valueOf(total1);
                            et_lasttotal.setText(total2);
                            et_lastfinaltotal.setText(total2);
                            et_lastbalancedue.setText(total2);

                            et_finaldiscount.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                    if(!s.toString().equals(null)){

                                        double final_discount= Double.parseDouble(s.toString().trim());
                                        double final_total= Double.parseDouble(et_lasttotal.getText().toString().trim());

                                        double last_final_total = final_total-(final_total*0.01*final_discount);

                                        String last_final_total1 = String.valueOf(last_final_total);

                                        et_lastfinaltotal.setText(last_final_total1);

                                    }

                                }
                            });


                            /*for(int l=0;l<ql7_copy.size();l++){

                                if(!(ql7_copy.get(l).toString().equals("0"))) {

                                    int aa = Integer.valueOf(newStr.get(l).toString());

                                    double amount_int_set = Double.parseDouble(ql7_copy.get(l).toString());
                                    double tax_int_set = Double.parseDouble(newStr.get(l).toString());
                                    double tax_int_set_half = tax_int_set / 2;

                                    double actual_amount = amount_int_set * 0.01 * tax_int_set_half;
                                    actual_amount_str[aa] = String.valueOf(actual_amount);

                                    if(newStr.get(l).toString().equals(newStr.get(newStr.size()-1).toString())){

                                        zl2.add((newStr.size()-1),"1");
                                        zl2.add(l,actual_amount_str[aa]);


                                    }
                                    else{
                                        zl2.add(l,"0");

                                    }



                                    Toast.makeText(getContext(), "actual value to be set is loop 1 " + actual_amount_str[aa].toString(), Toast.LENGTH_SHORT).show();



                                    Toast.makeText(getContext(), "new ql7 is  " + ql7_copy.get(l).toString(), Toast.LENGTH_SHORT).show();
                                }

                            }*/

                            /*for(int p=0;p<100;p++){
                                //if(actual_amount_str[l]!=null) {
                                Toast.makeText(getContext(), "actual value to be set is loop 2  " + actual_amount_str[p].toString(), Toast.LENGTH_SHORT).show();

                                zl2.add(actual_amount_str[p]);
                                //}
                            }*/


                            /*for(int q=0;q<newStr.size();q++){
                                for(int e=1;e<newStr.size()-1;e++){
                                    if(newStr.get(q).equals(newStr.get(e))){

                                        zl2.add(zl1.get(q));

                                    }
                                    else{

                                        zl2.add("0");

                                    }
                                }
                            }*/

                            recyclerView1.setItemAnimator(new DefaultItemAnimator());
                            adapter1 = new MyRecyclerViewAdapter(getContext(), zl2, zl2, zl2, zl2, zl2, zl2, zl2, zl2, "12");
                            adapter1.setClickListener(ViewInvoicePage.this);
                            recyclerView1.setAdapter(adapter1);
                            recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(),
                                    DividerItemDecoration.VERTICAL));
                            adapter1.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        btn_getallids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.clear();

                Toast.makeText(ViewInvoicePage.this.getContext(), "List abc is\n\n" + ql2.toString() + ql3.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_APPEND_ITEM_TABLE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to find data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();

                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();
                                List<String> al3 = new ArrayList<String>();
                                List<String> al4 = new ArrayList<String>();
                                List<String> al5 = new ArrayList<String>();
                                List<String> al6 = new ArrayList<String>();
                                List<String> al7 = new ArrayList<String>();
                                List<String> al8 = new ArrayList<String>();
                                List<String> al9 = new ArrayList<String>();
                                List<String> al10 =new ArrayList<String>();
                                List<String> al11 =new ArrayList<String>();
                                List<String> al12 =new ArrayList<String>();
                                List<String> al13 =new ArrayList<String>();
                                List<String> al14= new ArrayList<String>();
                                List<String> al15= new ArrayList<String>();
                                List<String> al16 =new ArrayList<String>();
                                List<String> al17= new ArrayList<String>();
                                List<String> al18= new ArrayList<String>();
                                List<String> al19= new ArrayList<String>();
                                List<String> al20= new ArrayList<String>();
                                List<String> al21= new ArrayList<String>();
                                List<String> al22= new ArrayList<String>();
                                List<String> al23= new ArrayList<String>();
                                List<String> al24= new ArrayList<String>();



                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                //Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();--

                                for(int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    ql1.add(json.getString("itemDetailId"));//al1
                                    ql3.add(json.getString("qty"));//al2
                                    al3.add(json.getString("BillQty"));
                                    ql5.add(json.getString("rate"));//al4
                                    al5.add(json.getString("itemunitval"));
                                    al6.add(json.getString("TaxType"));
                                    ql7.add(json.getString("TaxPercent"));//al7
                                    ql8.add(json.getString("itemdiscount"));//al8
                                    al9.add(json.getString("description"));
                                    al10.add(json.getString("TransactionId"));
                                    ql6.add(json.getString("discount"));//al11
                                    ql4.add(json.getString("FinancialYear"));//al12
                                    al13.add(json.getString("TransactionNumber"));
                                    al14.add(json.getString("DateCreated"));
                                    al15.add(json.getString("DueDate"));
                                    al16.add(json.getString("PersonId"));
                                    al17.add(json.getString("itemid"));
                                    al18.add(json.getString("remarks"));
                                    al19.add(json.getString("contactId"));
                                    al20.add(json.getString("PaytermsId"));
                                    ql2.add(json.getString("itemName"));//al21
                                    al21.add(json.getString("SubPacking"));
                                    al22.add(json.getString("TotalQty"));
                                    al23.add(json.getString("Quantity"));
                                    al24.add(json.getString("PackingQty"));

                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }

                                //Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("mytag", "onResponse: "+al1.toString()+"\n"+al2.toString()+"\n"+al3.toString()+"\n"+al4.toString()+"\n"
                                        +al5.toString()+"\n"+al6.toString()+"\n"+al7.toString()+"\n"+al8.toString()+"\n");

                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                                rv_viewitem.setLayoutManager(mLayoutManager);

                                rv_viewitem.setItemAnimator(new DefaultItemAnimator());
                                adapter = new MyRecyclerViewAdapter(getContext(), ql1,ql2,ql3,ql4,ql5,ql6,ql7,ql8,"5");
                                adapter.setClickListener(ViewInvoicePage.this);
                                rv_viewitem.setAdapter(adapter);
                                rv_viewitem.addItemDecoration(new DividerItemDecoration(getContext(),
                                        DividerItemDecoration.VERTICAL));
                                //al = Arrays.asList(n);

                                Toast.makeText(getContext(), "Response"+rv_viewitem.toString(), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id",company_id);
                params.put("transactionno", st_transactionid);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return view;
    }


    public void setQuantityForFocus(){
        try {
            current_edittext1 = Integer.parseInt(etqty.getText().toString());
            Log.d("mytag", "setQuantityForFocus: CurrentEditText"+current_edittext1);
            rate_editext1=Double.parseDouble(etrate.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if(!hasFocus1 && st_unitpostion==0) {

            String no1 = Integer.toString(st_totalqty);

            if (current_edittext1 > st_totalqty) {

                etbqty.setText(no1);
                etqty.setText(etbqty.getText().toString());
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());

                                           //calculating amount and setting to amount
                                           double amount=in_bqty*rate_editext1;
                                           Toast.makeText(getContext(), "amount"+amount, Toast.LENGTH_SHORT).show();*/
                //amount_nos=Double.toString(amount);
                // etamount.setText(amount_nos);
                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();



            } else {

                etbqty.setText(etqty.getText().toString());
                etqty.setText(etbqty.getText().toString());

            }

            try{in_bqty=Integer.parseInt(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

            //calculating amount and setting to amount
            double amount=in_bqty*rate_editext1;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            String dis_amount=String.valueOf(final_discount);


                etamount.setText(dis_amount);



        }

        else if(!hasFocus1 && st_unitpostion==1) {

            int packet=st_totalqty/st_subpackingqty;
            String no2 = Integer.toString(packet);
            Log.d("mytag", "setQuantityForFocus: packet"+packet+"\nno2"+no2);
            if (current_edittext1 > st_totalqty) {

                etbqty.setText(no2);
                etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            } else {

                etbqty.setText(etqty.getText().toString());
                etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            }

            int in_bqty=Integer.parseInt(etbqty.getText().toString());

            //calculating amount and setting to amount
            amount=in_bqty*rate_editext1*st_subpackingqty;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            dis_amount=String.valueOf(final_discount);


                etamount.setText(dis_amount);


        }

        else if(!hasFocus1 && st_unitpostion==2){
            bag=st_totalqtydouble/st_packingqtydouble;
            no3= Double.toString(bag);


            if (current_edittext1 > st_totalqty) {
                etbqty.setText(no3);
                etqty.setText(etbqty.getText().toString());
                try{
                    dou_bqty=Integer.parseInt(etbqty.getText().toString());
                }catch(NumberFormatException e){e.printStackTrace();}

                               /* //calculating amount and setting to amount
                                double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            }else{

                etbqty.setText(etqty.getText().toString());
                etqty.setText(etbqty.getText().toString());

                try{
                    dou_bqty=Integer.parseInt(etbqty.getText().toString());
                }catch(NumberFormatException e){e.printStackTrace();}

//                                //calculating amount and setting to amount
                            /*    double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            }
            try{do_inbqty=Double.parseDouble(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

            //calculating amount and setting to amount
            double amount=do_inbqty*rate_editext1*st_packingqtydouble;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            String dis_amount=String.valueOf(final_discount);


                etamount.setText(dis_amount);

        }
    }

    public void setAmountAndDiscount(){
        try {
            current_edittext1 = Integer.parseInt(etqty.getText().toString());
            rate_editext1=Double.parseDouble(etrate.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (st_unitpostion==0) {

            String no1 = Integer.toString(st_totalqty);

            if (current_edittext1 > st_totalqty) {

                etbqty.setText(no1);
                etqty.setText(etbqty.getText().toString());
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());

                                           //calculating amount and setting to amount
                                           double amount=in_bqty*rate_editext1;
                                           Toast.makeText(getContext(), "amount"+amount, Toast.LENGTH_SHORT).show();*/
                //amount_nos=Double.toString(amount);
                // etamount.setText(amount_nos);
                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();



            } else {

                etbqty.setText(etqty.getText().toString());
                etqty.setText(etbqty.getText().toString());

                //calculating amount and setting to amount
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                           double amount=in_bqty*rate_editext1;
                                           amount_nos=Double.toString(amount);*/

            }

            int in_bqty=Integer.parseInt(etbqty.getText().toString());

            //calculating amount and setting to amount
            double amount=in_bqty*rate_editext1;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            String dis_amount=String.valueOf(final_discount);


            etamount.setText(dis_amount);


        }



        else if(st_unitpostion==1) {

            int packet=st_totalqty/st_subpackingqty;
            String no2 = Integer.toString(packet);

            if (current_edittext1 > st_totalqty) {

                etbqty.setText(no2);
                etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            } else {

                etbqty.setText(etqty.getText().toString());
                etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            }

            int in_bqty=Integer.parseInt(etbqty.getText().toString());

            //calculating amount and setting to amount
            double amount=in_bqty*rate_editext1;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            String dis_amount=String.valueOf(final_discount);

            etamount.setText(dis_amount);


        }

        else if(st_unitpostion==2){
            double bag=st_totalqtydouble/st_packingqtydouble;
            String no3 = Double.toString(bag);

            if (current_edittext1 > st_totalqty) {
                etbqty.setText(no3);
                etqty.setText(etbqty.getText().toString());
                try{
                    dou_bqty=Integer.parseInt(etbqty.getText().toString());
                }catch(NumberFormatException e){e.printStackTrace();}

                               /* //calculating amount and setting to amount
                                double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            } else {

                etbqty.setText(etqty.getText().toString());
                etqty.setText(etbqty.getText().toString());

                try{
                    dou_bqty=Integer.parseInt(etbqty.getText().toString());
                }catch(NumberFormatException e){e.printStackTrace();}

//                                //calculating amount and setting to amount
                            /*    double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
            }
            try{do_inbqty=Double.parseDouble(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

            //calculating amount and setting to amount
            double amount=do_inbqty*rate_editext1*st_packingqtydouble;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            String dis_amount=String.valueOf(final_discount);
/*
            if(etdiscount.getText().toString().equals("0"))
                etamount.setText(amount_nos);
            else{*/
            etamount.setText(dis_amount);


        }

    }

    //Fetching Unit Data..
    public void getUnitData(){
        stringRequest2 = new StringRequest(Request.Method.POST, Config.FETCH_UNIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //bl2.clear();

                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Error in fetching data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{


                            try {

                                Toast.makeText(getContext(), "161"+response.toString(), Toast.LENGTH_SHORT).show();
                                List<String> bl1=new ArrayList<String>();
                                List<String> bl2=new ArrayList<String>();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();--
                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    bl1.add(json.getString("PackingId"));
                                    bl2.add(json.getString("PackingType"));



                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }

                                Integer a1 = bl1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray3 = new String[bl1.size()];
                                spinnerMap2 = new HashMap<Integer, String>();

                                // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <bl1.size(); i++)
                                {
                                    spinnerMap2.put(i,bl1.get(i));
                                    spinnerArray3[i] = bl1.get(i);
                                }


                                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(ViewInvoicePage.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, bl2);

                                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(dataAdapter1);



                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        st_unitpostion=spinner2.getSelectedItemPosition();
                                        String st_termsvalue1 = (String) spinnerMap2.get(spinner2.getSelectedItemPosition());

                                        Toast.makeText(getContext(),"the selected item is\n"+ st_termsvalue1, Toast.LENGTH_SHORT).show();

                                        //Getting Item Unit Url Data..
                                        getItemUnitUrl();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                                st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItem());
//
//                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
//                                requestQueue1.add(stringRequest1);


                                Toast.makeText(getContext(),"santosh\n"+ bl2.toString(), Toast.LENGTH_SHORT).show();


                                //al = Arrays.asList(n);


                            } catch (JSONException e){
                                e.printStackTrace();
                            }




                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id",company_id);


                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
        requestQueue3.add(stringRequest2);
    }


    //Getting Product Data
    public void getProductDataSpin(){
        stringRequest8 = new StringRequest(Request.Method.POST, Config.FETCH_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                al2.clear();
                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();

                                //getting taxes in spinner..
                                getTaxesInSpin();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();--
                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("ItemId").trim());
                                    al2.add(json.getString("ItemName").trim());
                                    al3.add("Rs ".concat(json.getString("price")));
                                    al4.add("Quantity #".concat(json.getString( "Quantity")));
                                    al5.add("Recorded LAbel : ".concat(json.getString("ReorderLabel")));
                                    al6.add(json.getString("SKU"));
                                    al7.add(json.getString("HSN"));
                                    al8.add(json.getString("Description"));



                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }

                                Integer a1 = al1.size();
                                spinnerArray = new String[al1.size()];
                                spinnerMap = new HashMap<Integer,String>();

                                for(int p=0;p<al1.size();p++){

                                    spinnerMap.put(p, (String) al1.get(p));
                                    spinnerArray[p] = (String) al1.get(p);
                                }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewInvoicePage.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, al2);

                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(dataAdapter);

                                st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItem());

                                //getting Updated Product..
                                getUpdateProduct();
                                /*RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                requestQueue1.add(stringRequest1);*/


                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItemPosition());

                                        Log.d("mytag", "onItemSelected: st_spinner1"+st_spinner1);
                                        Toast.makeText(ViewInvoicePage.this.getContext(), st_spinner1.toString(), Toast.LENGTH_SHORT).show();

                                        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                        requestQueue1.add(stringRequest1);



                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id", company_id);

                params.put("Ttype", String.valueOf(1));
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest8);
    }


    public void getUpdateProduct(){
        stringRequest7 = new StringRequest(Request.Method.POST, Config.UPDATE_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Error in fetching data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{


                            try {

                                Toast.makeText(getContext(), "161"+response.toString(), Toast.LENGTH_SHORT).show();

                                JSONObject json=new JSONObject(response);
                                Toast.makeText(getContext(), "162"+json, Toast.LENGTH_SHORT).show();

                                a1 = (json.getString("ItemName"));
                                a2 = (json.getString("SKU"));
                                a3 = (json.getString("HSN"));
                                a4 = (json.getString("Quantity"));
                                a5 = (json.getString("ReorderLabel"));
                                a6 = (json.getString("PackingQty"));
                                a7 = (json.getString("SubPacking"));
                                a8 = (json.getString("Description"));
                                a9 = (json.getString("price"));


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                ethsn.setText(a3.toString());
                                //etqty.setText("0");
                                etrate.setText(a9.toString());
                                etamount.setText(etrate.getText().toString());






//                                                hsncodee.setText(a3.toString());
//                                                initialquantityinhande.setText(a4.toString());
//                                                lowstockalerte.setText(a5.toString());
//                                                salespricerate.setText(a9.toString());
//                                                packingquantity.setText(a6.toString());
//                                                subpackingquantitye.setText(a7.toString());
//                                                descriptione.setText(a8.toString());


                                //Toast.makeText(getContext(),"santosh\n"+ a1.toString()+a2.toString()+a3.toString(), Toast.LENGTH_SHORT).show();


                                //al = Arrays.asList(n);

                            } catch (JSONException e){
                                e.printStackTrace();
                            }




                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("productId", st_spinner1);
                params.put("company_id",company_id);


                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest7);
    }

   /* public void LoadAllProductInSpin() {
        stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            //.makeText(getContext(), "Invalid username or password" + response.toString(), //.LENGTH_LONG).show();

                        } else {

                            try {

                                al2.clear();
                                //.makeText(getContext(), "111" + response.toString(), //.LENGTH_SHORT).show();

                                //getting taxes in spinner..
                                getTaxesInSpin();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();--
                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("ItemId").trim());
                                    al2.add(json.getString("ItemName").trim());
                                    al3.add("Rs ".concat(json.getString("price")));
                                    al4.add("Quantity #".concat(json.getString("Quantity")));
                                    al5.add("Recorded LAbel : ".concat(json.getString("ReorderLabel")));
                                    al6.add(json.getString("SKU"));
                                    al7.add(json.getString("HSN"));
                                    al8.add(json.getString("Description"));


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }

                                Integer a1 = al1.size();
                                spinnerArray = new String[al1.size()];
                                spinnerMap = new HashMap<Integer, String>();

                                for (int p = 0; p < al1.size(); p++) {

                                    spinnerMap.put(p, (String) al1.get(p));
                                    spinnerArray[p] = (String) al1.get(p);
                                }
//                    //.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewInvoicePage.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, al2);

                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(dataAdapter);

                                st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItem());

                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                requestQueue1.add(stringRequest1);


                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItemPosition());
                                        //.makeText(AddNewInvoice.this.getContext(), st_spinner1.toString(), //.LENGTH_SHORT).show();

                                        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                        requestQueue1.add(stringRequest1);


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id", company_id);
                int type = 1;
                params.put("Ttype", valueOf(type));
//                params.put("password", password);

                //returning parameter
                return params;
            }

        };
    }*/

    public void getProductDetailsById() {
        stringRequest1 = new StringRequest(Request.Method.POST, Config.FETCH_ITEMNAME_INFO_BY_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            //.makeText(getContext(), "Error in fetching data" + response.toString(), //.LENGTH_LONG).show();

                        } else {


                            try {


                                //.makeText(getContext(), "161" + response.toString(), //.LENGTH_SHORT).show();

                                JSONObject json = new JSONObject(response);
                                //.makeText(getContext(), "162" + json, //.LENGTH_SHORT).show();

                                a1 = (json.getString("itemDetailId"));
                                a2 = (json.getString("TaxId"));
                                a3 = (json.getString("HSN"));
                                a4 = (json.getString("Quantity"));
                                a5 = (json.getString("ReorderLabel"));
                                a6 = (json.getString("PackingQty"));
                                a7 = (json.getString("SubPacking"));
                                a8 = (json.getString("Description"));
                                a9 = (json.getString("price"));
                                a10 = (json.getString("totalqty"));


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                st_quantitybyid = a4;
                                st_packingqty = Integer.parseInt(a6);
                                st_packingqtydouble=Double.parseDouble(a6);
                                st_subpackingqty = Integer.parseInt(a7);
                                st_totalqty = Integer.parseInt(a10);
                                st_totalqtydouble=Double.parseDouble(a10);
                                st_price=Double.parseDouble(a9);

                                Log.d("mytag", "onResponse:getProductsDetailsById st_packingqty "+st_packingqty+"\nst_packingqtydouble "
                                +st_packingqtydouble+"\nst_subpackingqty"+st_subpackingqty+"\nst_totalqty"+st_totalqty+
                                        "\nst_totalqtydouble"+st_totalqtydouble+"\nstprice"+st_price);


                                //.makeText(getContext(), "Quantity Id" + st_quantitybyid + "\nPacking_Qty" + st_packingqty
                                //+ "\nSub_PackingQty" + st_subpackingqty + "\nTotalQty" + st_totalqty+"\nst_price"+st_price, //.LENGTH_SHORT).show();

                                ethsn.setText(a3.toString());

                                etrate.setText(a9.toString());
                                etamount.setText(etrate.getText().toString());







                                //al = Arrays.asList(n);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("itemname", st_spinner1);
                params.put("company_id", company_id);


                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest1);

    }


    //fetching customername data
    public void getCustomerNameSpin(){
        stringRequest3 = new StringRequest(Request.Method.POST, Config.FETCH_CUSTOMER_FOR_VIEW_INVOICE_URL,
                            new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch customer data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "101"+response.toString(), Toast.LENGTH_SHORT).show();

                                List<String> cl1 = new ArrayList<String>();
                                List<String> cl2 = new ArrayList<String>();
                                List<String> cl3 = new ArrayList<String>();
                                List<String> cl4 = new ArrayList<String>();
                                List<String> cl5 = new ArrayList<String>();
                                List<String> cl6 = new ArrayList<String>();
                                List<String> cl7 = new ArrayList<String>();
                                List<String> cl8 = new ArrayList<String>();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);

                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    cl1.add(json.getString("PersonId"));
                                    cl2.add((json.getString("name")));
                                }

                                Integer a1 = cl1.size();
                                String a2 = String.valueOf(a1);
                                String[] spinnerArray = new String[cl1.size()];
                                spinnerMap = new HashMap<Integer, String>();

                                 Toast.makeText(getContext(), "the size is" + cl2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <cl1.size(); i++)
                                {
                                    spinnerMap.put(i,cl1.get(i));
                                    spinnerArray[i] = cl1.get(i);
                                }

                                                /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                                        android.R.layout.simple_spinner_dropdown_item, al2);
            */
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, cl2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_customername.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                //


                                spin_customername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_customervalue = (String) spinnerMap.get(spin_customername.getSelectedItemPosition());

                                        //Calling functionf for getting Customer Address based on their Id...
                                        getAddressById();

                                        Toast.makeText(getContext(), "Customer Name value"+st_customervalue, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put("company_id", company_id);
                        params.put("formid", form_id);
            //                params.put("password", password);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
                    requestQueue3.add(stringRequest3);

    }

    public void getPayTermsSpin(){
        stringRequest4 = new StringRequest(Request.Method.POST, Config.FETCH_PAY_TERMS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch terms data"+response.toString(), Toast.LENGTH_LONG).show();

                        }
                        //If we are getting success from server
                        else{

                            try {

                                Toast.makeText(getContext(), "PayTerms Data Response \n\n"+response.toString(), Toast.LENGTH_SHORT).show();

                                List<String> al1 = new ArrayList<String>();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add("NET-".concat(json.getString("Paytermval")));

                                }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();

                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray = new String[al1.size()];
                                spinnerMap1 = new HashMap<Integer, String>();

                                // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    spinnerMap1.put(i,al1.get(i));
                                    spinnerArray[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_terms.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                //


                                spin_terms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_termsvalue = (String) spinnerMap1.get(spin_terms.getSelectedItemPosition());
                                        String[] items = st_termsvalue.split("[a-zA-Z]+");
                                        String[] items1 = items[1].split("-");
                                        nosofdays = Integer.parseInt((items1[1]));


                                        Toast.makeText(getContext(), "Term Value"+items1[1].toString(), Toast.LENGTH_SHORT).show();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                        Calendar c = Calendar.getInstance();

                                            //Setting the date to the given date
                                        try {
                                            c.setTime(sdf.parse(et_invoicedate.getText().toString()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getContext(), "Et_invoice date is"+et_invoicedate.getText().toString(), Toast.LENGTH_SHORT).show();


                                        c.add(Calendar.DAY_OF_MONTH, nosofdays);
                                        String newDate = sdf.format(c.getTime());
                                        Toast.makeText(getContext(), "New Date"+newDate.toString(), Toast.LENGTH_SHORT).show();
                                        et_duedate.setText(newDate);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id", company_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
        requestQueue3.add(stringRequest4);
    }

    public void getItemUnitUrl(){
        stringRequest6 = new StringRequest(Request.Method.POST, Config.FETCH_ITEM_UNIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "1110000"+response.toString(), Toast.LENGTH_SHORT).show();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();--





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id", company_id);
                params.put("itemunitid", spinner2.getSelectedItem().toString());
                params.put("itemnameid", st_spinner1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          );
//                params.put("password", password);

                //returning parameter
               return params;
            }

        };

        RequestQueue requestQueue10 = Volley.newRequestQueue(getContext());
        requestQueue10.add(stringRequest6);

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              }
    //fetching address by customer id..
    public void getAddressById(){
        stringRequest5 = new StringRequest(Request.Method.POST, Config.FETCH_INFO_ADDRESS_BY_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch address data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {
                                JSONObject json = new JSONObject(response);
                                Toast.makeText(getContext(), "162" + json, Toast.LENGTH_SHORT).show();

                                a1 = (json.getString("contactId"));
                                a2 = (json.getString("contactAddress"));

                                et_billaddress.setText(a2);
                                Toast.makeText(getContext(), "A2\n\n"+a2, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(MainActivity.this, "Data from Json\n"+a1+"\n"+a2+"\n"+a3+"\n"+a4+"\n"+a5 , Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id", company_id);
                params.put("custid",st_customervalue);
                //                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue4 = Volley.newRequestQueue(getContext());
        requestQueue4.add(stringRequest5);
    }



    //Fetching all taxes
    public void getTaxesInSpin() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_ITEM_TAX1_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch tax data" + response.toString(), Toast.LENGTH_LONG).show();

                        } else {

                            try {

                                Toast.makeText(getContext(), "getTaxValue Response \n\n\n" + response.toString(), Toast.LENGTH_SHORT).show();


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("TaxPercent"));
                                    al2.add(json.getString("TaxName"));

                                }
                                Integer a1 = al1.size();
                                String a2 = valueOf(a1);
                                spinnerArray4 = new String[al1.size()];
                                spinnerMap4 = new HashMap<Integer, String>();

                                // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i < al1.size(); i++) {
                                    spinnerMap4.put(i, al1.get(i));
                                    spinnerArray4[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_tax.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()

                                //


                                spin_tax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_taxvalue = (String) spinnerMap4.get(spin_tax.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "Size Value" + st_taxvalue, Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                //params.put("ItemId", "ItemId");
                //params.put("ItemName", "ItemName");
                params.put("company_id", company_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);
    }

    @Override
    public void onItemClick(View view, final int position) {

       /* Toast.makeText(getContext(), "Hello World", Toast.LENGTH_LONG).show();

        String user_id1 = adapter.getItem(position);
        int user_id2 = adapter.getItemCount();
        String user_id21 = valueOf(user_id2);

        Toast.makeText(getContext(), "getitem is  " + user_id1.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "count is  " + user_id21.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();
        spinner1 = dialogView.findViewById(R.id.spinner1);
        spinner2 = dialogView.findViewById(R.id.spinner2);


//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);
//
//        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
//        requestQueue1.add(stringRequest2);


        final EditText ethsn = dialogView.findViewById(R.id.ethsn);
        final EditText etqty = dialogView.findViewById(R.id.etqty);

        ethsn.setText(user_id1.toString());
        Button btnLogin = dialogView.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String a1 = ethsn.getText().toString();
                String newValue = "I like sheep.";
                int updateIndex = 0;
                al2.set(updateIndex, a1);
                Toast.makeText(ViewInvoicePage.this.getContext(), al2.toString(), Toast.LENGTH_SHORT).show();
                adapter.notifyItemChanged(updateIndex);
                adapter.notifyDataSetChanged();

            }
        });

        Toast.makeText(ViewInvoicePage.this.getContext(), al1.toString(), Toast.LENGTH_SHORT).show();

        dialog.show();
*/


        String user_id1 = adapter.getItem(position);
        int user_id2 = adapter.getItemCount();
        String user_id21 = valueOf(user_id2);
        check = 0;
        Toast.makeText(getContext(), "getitem is  " + user_id1.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "count is  " + user_id21.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();
        spinner1 = dialogView.findViewById(R.id.spinner1);
        spinner2 = dialogView.findViewById(R.id.spinner2);
        spin_tax = dialogView.findViewById(R.id.spin_tax);


        getUnitData();
      /*  RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest2);*/


        final EditText ethsn = dialogView.findViewById(R.id.ethsn);
        final EditText etqty = dialogView.findViewById(R.id.etqty);
        final EditText etbqty = dialogView.findViewById(R.id.etbqty);
        final EditText etrate = dialogView.findViewById(R.id.etrate);
        final EditText etdiscount = dialogView.findViewById(R.id.etdiscount);
        final EditText etamount= dialogView.findViewById(R.id.etamount);

        ethsn.setText(ql8.get(position).toString());
        etqty.setText(ql3.get(position).toString());
        etbqty.setText(ql4.get(position).toString());

        etrate.setText(ql5.get(position).toString());
        etdiscount.setText(ql6.get(position).toString());
        etamount.setText(ql7.get(position).toString());
        Toast.makeText(getContext(), "ql5"+ql5.get(position).toString()+"\nql6 "+ql6.get(position).toString(), Toast.LENGTH_SHORT).show();

        stringRequest12 = new StringRequest(Request.Method.POST, Config.FETCH_ITEMNAME_INFO_BY_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Error in fetching data" + response.toString(), Toast.LENGTH_LONG).show();

                        } else {


                            try {


                                Toast.makeText(getContext(), "161" + response.toString(), Toast.LENGTH_SHORT).show();

                                JSONObject json = new JSONObject(response);
                                Toast.makeText(getContext(), "162" + json, Toast.LENGTH_SHORT).show();

                                a1 = (json.getString("itemDetailId"));
                                a2 = (json.getString("TaxId"));
                                a3 = (json.getString("HSN"));
                                a4 = (json.getString("Quantity"));
                                a5 = (json.getString("ReorderLabel"));
                                a6 = (json.getString("PackingQty"));
                                a7 = (json.getString("SubPacking"));
                                a8 = (json.getString("Description"));
                                a9 = (json.getString("price"));
                                a10 = (json.getString("totalqty"));


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                st_quantitybyid = a4;
                                st_packingqty = Integer.parseInt(a6);
                                st_packingqtydouble=Double.parseDouble(a6);
                                st_subpackingqty = Integer.parseInt(a7);
                                st_totalqty = Integer.parseInt(a10);
                                st_totalqtydouble=Double.parseDouble(a10);
                                st_price=Double.parseDouble(a9);

                                Toast.makeText(getContext(), "Quantity Id" + st_quantitybyid + "\nPacking_Qty" + st_packingqty
                                        + "\nSub_PackingQty" + st_subpackingqty + "\nTotalQty" + st_totalqty+"\nst_price"+st_price, Toast.LENGTH_SHORT).show();


                                final EditText etrate = dialogView.findViewById(R.id.etrate);
                                final EditText etdiscount = dialogView.findViewById(R.id.etdiscount);
                                final EditText etamount= dialogView.findViewById(R.id.etamount);

                                ethsn.setText(a3);

                                etrate.setText(a9);

                                etamount.setText(etrate.getText().toString());
                                //al = Arrays.asList(n);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("itemname", st_spinner1);
                params.put("company_id", company_id);


                //returning parameter
                return params;
            }
        };



        stringRequest11 = new StringRequest(Request.Method.POST, Config.FETCH_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Invalid username or password" + response.toString(), Toast.LENGTH_LONG).show();

                        } else {

                            try {

                                al2.clear();
                                Toast.makeText(getContext(), "111" + response.toString(), Toast.LENGTH_SHORT).show();

                                //getting taxes in spinner..
                                getTaxesInSpin();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();--
                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("ItemId").trim());
                                    al2.add(json.getString("ItemName").trim());
                                    al3.add("Rs ".concat(json.getString("price")));
                                    al4.add("Quantity #".concat(json.getString("Quantity")));
                                    al5.add("Recorded LAbel : ".concat(json.getString("ReorderLabel")));
                                    al6.add(json.getString("SKU"));
                                    al7.add(json.getString("HSN"));
                                    al8.add(json.getString("Description"));


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }

                                Integer a1 = al1.size();
                                spinnerArray = new String[al1.size()];
                                spinnerMap = new HashMap<Integer, String>();

                                for (int p = 0; p < al1.size(); p++) {

                                    spinnerMap.put(p, (String) al1.get(p));
                                    spinnerArray[p] = (String) al1.get(p);
                                }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewInvoicePage.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, al2);

                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(dataAdapter);
                                int spinnerPosition = dataAdapter.getPosition(String.valueOf(ql2.get(position)));
                                spinner1.setSelection(spinnerPosition);

//                                st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItem());
//                                Toast.makeText(AddNewInvoice.this.getContext(), "st spinner1 "+st_spinner1.toString(), Toast.LENGTH_SHORT).show();



                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItemPosition());
                                        Toast.makeText(ViewInvoicePage.this.getContext(), "st spinner1 "+st_spinner1.toString(), Toast.LENGTH_SHORT).show();

                                        if(++check > 1) {
                                            RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                            requestQueue1.add(stringRequest12);
                                        }


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id", company_id);
                int type = 1;
                params.put("Ttype", valueOf(type));
//                params.put("password", password);

                //returning parameter
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest11);

        etqty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                try {
                    current_edittext1 = Integer.parseInt(etqty.getText().toString());
                    rate_editext1=Double.parseDouble(etrate.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if(!hasFocus1 && st_unitpostion==0) {

                    String no1 = Integer.toString(st_totalqty);

                    if (current_edittext1 > st_totalqty) {

                        etbqty.setText(no1);
                        etqty.setText(etbqty.getText().toString());
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());

                                           //calculating amount and setting to amount
                                           double amount=in_bqty*rate_editext1;
                                           Toast.makeText(getContext(), "amount"+amount, Toast.LENGTH_SHORT).show();*/
                        //amount_nos=Double.toString(amount);
                        // etamount.setText(amount_nos);
                        Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();



                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                    }

                    try{in_bqty=Integer.parseInt(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

                    //calculating amount and setting to amount
                    double amount=in_bqty*rate_editext1;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);


                    etamount.setText(dis_amount);


                }

                else if(!hasFocus1 && st_unitpostion==1) {

                    int packet=st_totalqty/st_subpackingqty;
                    String no2 = Integer.toString(packet);

                    if (current_edittext1 > st_totalqty) {

                        etbqty.setText(no2);
                        etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    }

                    int in_bqty=Integer.parseInt(etbqty.getText().toString());

                    //calculating amount and setting to amount
                    amount=in_bqty*rate_editext1*st_subpackingqty;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    dis_amount=String.valueOf(final_discount);


                    etamount.setText(dis_amount);


                }

                else if(!hasFocus1 && st_unitpostion==2){
                    bag=st_totalqtydouble/st_packingqtydouble;
                    no3= Double.toString(bag);


                    if (current_edittext1 > st_totalqty) {
                        etbqty.setText(no3);
                        etqty.setText(etbqty.getText().toString());
                        try{
                            dou_bqty=Integer.parseInt(etbqty.getText().toString());
                        }catch(NumberFormatException e){e.printStackTrace();}

                               /* //calculating amount and setting to amount
                                double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                        try{
                            dou_bqty=Integer.parseInt(etbqty.getText().toString());
                        }catch(NumberFormatException e){e.printStackTrace();}

//                                //calculating amount and setting to amount
                            /*    double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    }
                    try{do_inbqty=Double.parseDouble(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

                    //calculating amount and setting to amount
                    double amount=do_inbqty*rate_editext1*st_packingqtydouble;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);

                    etamount.setText(dis_amount);


                }
            }

        });

        etrate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //Working on amount and Discount using below function .....as rate and discount change function will call....
                //setAmountAndDiscount();

                try {
                    current_edittext1 = Integer.parseInt(etqty.getText().toString());
                    rate_editext1=Double.parseDouble(etrate.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (st_unitpostion==0) {

                    String no1 = Integer.toString(st_totalqty);

                    if (current_edittext1 > st_totalqty) {

                        etbqty.setText(no1);
                        etqty.setText(etbqty.getText().toString());
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());

                                           //calculating amount and setting to amount
                                           double amount=in_bqty*rate_editext1;
                                           Toast.makeText(getContext(), "amount"+amount, Toast.LENGTH_SHORT).show();*/
                        //amount_nos=Double.toString(amount);
                        // etamount.setText(amount_nos);
                        Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();



                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                        //calculating amount and setting to amount
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                           double amount=in_bqty*rate_editext1;
                                           amount_nos=Double.toString(amount);*/

                    }

                    int in_bqty=Integer.parseInt(etbqty.getText().toString());

                    //calculating amount and setting to amount
                    double amount=in_bqty*rate_editext1;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);


                    etamount.setText(dis_amount);


                }



                else if(st_unitpostion==1) {

                    int packet=st_totalqty/st_subpackingqty;
                    String no2 = Integer.toString(packet);

                    if (current_edittext1 > st_totalqty) {

                        etbqty.setText(no2);
                        etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    }

                    int in_bqty=Integer.parseInt(etbqty.getText().toString());

                    //calculating amount and setting to amount
                    double amount=in_bqty*rate_editext1;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);


                    etamount.setText(dis_amount);


                }

                else if(st_unitpostion==2){
                    double bag=st_totalqtydouble/st_packingqtydouble;
                    String no3 = Double.toString(bag);

                    if (current_edittext1 > st_totalqty) {
                        etbqty.setText(no3);
                        etqty.setText(etbqty.getText().toString());
                        try{
                            dou_bqty=Integer.parseInt(etbqty.getText().toString());
                        }catch(NumberFormatException e){e.printStackTrace();}

                               /* //calculating amount and setting to amount
                                double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                        try{
                            dou_bqty=Integer.parseInt(etbqty.getText().toString());
                        }catch(NumberFormatException e){e.printStackTrace();}

//                                //calculating amount and setting to amount
                            /*    double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    }
                    try{do_inbqty=Double.parseDouble(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

                    //calculating amount and setting to amount
                    double amount=do_inbqty*rate_editext1*st_packingqtydouble;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);


                    etamount.setText(dis_amount);


                }
            }
        });

        etdiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //Working on amount and Discount using below function .....as rate and discount change function will call....
                //setAmountAndDiscount();

                try {
                    current_edittext1 = Integer.parseInt(etqty.getText().toString());
                    rate_editext1=Double.parseDouble(etrate.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if  (st_unitpostion==0) {

                    String no1 = Integer.toString(st_totalqty);

                    if (current_edittext1 > st_totalqty) {

                        etbqty.setText(no1);
                        etqty.setText(etbqty.getText().toString());
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());

                                           //calculating amount and setting to amount
                                           double amount=in_bqty*rate_editext1;
                                           Toast.makeText(getContext(), "amount"+amount, Toast.LENGTH_SHORT).show();*/
                        //amount_nos=Double.toString(amount);
                        // etamount.setText(amount_nos);
                        Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();



                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                        //calculating amount and setting to amount
                                           /*int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                           double amount=in_bqty*rate_editext1;
                                           amount_nos=Double.toString(amount);*/

                    }

                    int in_bqty=Integer.parseInt(etbqty.getText().toString());

                    //calculating amount and setting to amount
                    double amount=in_bqty*rate_editext1;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);


                    etamount.setText(dis_amount);


                }



                else if(st_unitpostion==1) {

                    int packet=st_totalqty/st_subpackingqty;
                    String no2 = Integer.toString(packet);

                    if (current_edittext1 > st_totalqty) {

                        etbqty.setText(no2);
                        etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                              /*  //calculating amount and setting to amount
                                int in_bqty=Integer.parseInt(etbqty.getText().toString());
                                double amount_packet=in_bqty*rate_editext1*st_subpackingqty;
                                String amount1_packet=Double.toString(amount_packet);
                                etamount.setText(amount1_packet);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    }

                    int in_bqty=Integer.parseInt(etbqty.getText().toString());

                    //calculating amount and setting to amount
                    double amount=in_bqty*rate_editext1;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);

                    if(etdiscount.getText().toString().equals("0"))
                        etamount.setText(amount_nos);
                    else{
                        etamount.setText(dis_amount);
                    }

                }

                else if(st_unitpostion==2){
                    double bag=st_totalqtydouble/st_packingqtydouble;
                    String no3 = Double.toString(bag);

                    if (current_edittext1 > st_totalqty) {
                        etbqty.setText(no3);
                        etqty.setText(etbqty.getText().toString());
                        try{
                            dou_bqty=Integer.parseInt(etbqty.getText().toString());
                        }catch(NumberFormatException e){e.printStackTrace();}

                               /* //calculating amount and setting to amount
                                double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                        try{
                            dou_bqty=Integer.parseInt(etbqty.getText().toString());
                        }catch(NumberFormatException e){e.printStackTrace();}

//                                //calculating amount and setting to amount
                            /*    double amount_bag=dou_bqty*rate_editext1*st_packingqty;
                                String amount1_bag=Double.toString(amount_bag);
                                etamount.setText(amount1_bag);
                                Toast.makeText(getContext(), "Inside else loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    }
                    try{do_inbqty=Double.parseDouble(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

                    //calculating amount and setting to amount
                    double amount=do_inbqty*rate_editext1*st_packingqtydouble;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    Toast.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, Toast.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    Toast.makeText(getContext(), "discount"+discout_amount, Toast.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);


                    etamount.setText(dis_amount);


                }
            }
        });



        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_ITEM_TAX1_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch tax data" + response.toString(), Toast.LENGTH_LONG).show();

                        } else {

                            try {

                                Toast.makeText(getContext(), "getTaxValue Response \n\n\n" + response.toString(), Toast.LENGTH_SHORT).show();


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("TaxPercent"));
                                    al2.add(json.getString("TaxName"));

                                }
                                Integer a1 = al1.size();
                                String a2 = valueOf(a1);
                                spinnerArray4 = new String[al1.size()];
                                spinnerMap4 = new HashMap<Integer, String>();

                                // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i < al1.size(); i++) {
                                    spinnerMap4.put(i, al1.get(i));
                                    spinnerArray4[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_tax.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                //


                                spin_tax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_taxvalue = (String) spinnerMap4.get(spin_tax.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "Size Value" + st_taxvalue, Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                //params.put("ItemId", "ItemId");
                //params.put("ItemName", "ItemName");
                params.put("company_id", company_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(stringRequest);

        Button btnLogin = dialogView.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String a1 = spinner1.getSelectedItem().toString();
                String a2 = etqty.getText().toString();
                String a3 = etbqty.getText().toString();
                String a4 = etrate.getText().toString();
                String a5 = etdiscount.getText().toString();
                String a6 = etamount.getText().toString();
                String a7 = ethsn.getText().toString();

                int updateIndex = position;
                ql2.set(updateIndex, a1);
                ql3.set(updateIndex, a2);
                ql4.set(updateIndex, a3);
                ql5.set(updateIndex, a4);
                ql6.set(updateIndex, a5);
                ql7.set(updateIndex, a6);
                ql8.set(updateIndex, a7);

                Toast.makeText(ViewInvoicePage.this.getContext(), al3.toString(), Toast.LENGTH_SHORT).show();
                adapter.notifyItemChanged(updateIndex);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        Toast.makeText(ViewInvoicePage.this.getContext(), al1.toString(), Toast.LENGTH_SHORT).show();

        dialog.show();


    }
}
