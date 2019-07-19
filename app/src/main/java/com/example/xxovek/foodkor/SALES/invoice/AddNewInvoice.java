package com.example.xxovek.foodkor.SALES.invoice;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.xxovek.foodkor.AddNewInvoiceRecyclerViewAdapter;
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

import static java.lang.String.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewInvoice extends Fragment implements MyRecyclerViewAdapter.ItemClickListener, AddNewInvoiceRecyclerViewAdapter.ItemClickListener {

    public int i;
    public MyRecyclerViewAdapter adapter,adapter1;
    public AddNewInvoiceRecyclerViewAdapter addinvoice_adapter;
    RecyclerView recyclerView, recyclerView1;
    AddNewInvoice context;
    View view;

    Spinner spinner1, spinner2, spin_customername, spin_terms, spin_tax;
    String  st_customervalue, st_termsvalue;
    EditText et_invoicedate, et_duedate,et_lasttotal,et_subtotal,et_lastfinaltotal,et_lastbalancedue,et_finaldiscount,et_message,et_billingaddress;
    int nosofdays;

    FloatingActionButton fab_dialog;
    Button btn_getallids;
    ArrayList<String> n, e1, e2, e3, e4, e5, e6, a;
    String a1, a2, a3, a4, a5, a6, a7, a8, a9, a10,form_type="N";
    private ArrayList al1, al2, al3, al4, al5, al6, al7, al8, ql1, ql2, ql3, ql4, ql5, ql6, ql7, ql8,ql7_copy,zl1,zl2;

    private int mYear, mMonth, mDay;
    JSONObject item2;
    JSONObject jo;
    public String[] spinnerArray, spinnerArray3, spinnerArray4;
    HashMap spinnerMap, spinnerMap1, spinnerMap2, spinnerMap4;
    StringRequest stringRequest1, stringRequest2, stringRequest3, stringRequest4, stringRequest5,saveTransactionDetails;
    String st_spinner1, st_taxvalue, company_id, st_quantitybyid,  st_unitid,amount_nos,no3,dis_amount;
    int st_totalqty, st_unitpostion,st_subpackingqty,st_packingqty;
    double st_price,rate_editext1,dou_bqty,dou_etdiscount,st_packingqtydouble,st_totalqtydouble,do_inbqty,bag,amount;
    int check =0;
    StringRequest stringRequest;
    TextInputEditText ethsn;
    TextInputEditText etqty, etbqty, etrate, etdiscount, etamount,etqty1, etbqty1, etrate1, etdiscount1, etamount1;
    int current_edittext1,user_id2;
    private int in_bqty,hidetransactionid=0;
    public boolean hasFocus1=true;
    public StringRequest stringRequest11,stringRequest12;
    public ArrayList newStr,newStr_copy;
    public String amount_temp,st_transactionid,form_id,itemdetailid,contactId;
    private String[] actual_amount_str;
    private double sum1=0.0;
    private double total1;
    public ArrayList itemdetailid_arr,st_spinner1_arr,etqty_arr,etbqty_arr,etrate_arr,et_discountarr,stunitid_arr,st_subpackingqty_arr,st_packingqty_arr,
                            st_totalqty_arr,st_quantitybyid_arr,st_taxvalue_arr;
    private int r;


    public AddNewInvoice() {
        // Required empty public constructor
        try {

            ql1.clear();
            ql2.clear();
            ql3.clear();
            ql4.clear();
            ql5.clear();
            ql6.clear();
            ql7.clear();
            ql8.clear();
            itemdetailid_arr.clear();
            st_spinner1_arr.clear();
            etqty_arr.clear();
            etbqty_arr.clear();
            etrate_arr.clear();
            et_discountarr.clear();
            stunitid_arr.clear();
            st_subpackingqty_arr.clear();
            st_packingqty_arr.clear();
            st_totalqty_arr.clear();
            st_quantitybyid_arr.clear();
            st_taxvalue_arr.clear();
        }catch(NullPointerException e){e.printStackTrace();}
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_invoice, container, false);
        context = this;


        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id = prf.getString("person_id", "");
        company_id = prf.getString("company_id", "");
        final String company_flag = prf.getString("company_flag", "");
        final String isAdmin = prf.getString("isAdmin", "");
        actual_amount_str = new String[100];
        form_id = getArguments().getString("data1");
        //Toast.makeText(getActivity(),"st_Transactionid"+st_transactionid,Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(),"form_id"+form_id,Toast.LENGTH_LONG).show();



        //Loading all Product name in Spinner
        LoadAllProductInSpin();


        //Updating Product Information Based Product Id which is get from LoadAllProductInSpin() Function
        getProductDetailsById();


        //Loading Unit Data in spinner...
        getUnitDataInSpin();


        //DisplayCustomerData..
        getCustomerNameSpin();


        //Loading Payterms Data in spinner...
        getPaytermsInSpin();


        //Getting Item data like packing_qty,subpacking_qty,Quantity.....
        getItemDataByUnitId();


         n = new ArrayList<String>();
        e1 = new ArrayList<String>();
        e2 = new ArrayList<String>();
        e3 = new ArrayList<String>();
        e4 = new ArrayList<String>();
        e5 = new ArrayList<String>();
        e6 = new ArrayList<String>();

        newStr = new ArrayList<String>();
        newStr_copy = new ArrayList<String>();

        jo = new JSONObject();
        final ArrayList<JSONObject> items = new ArrayList<JSONObject>();
        item2 = new JSONObject();

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
        ql7_copy = new ArrayList<String>();
        ql8 = new ArrayList<String>();
        zl1 = new ArrayList<String>();
        zl2 = new ArrayList<String>();

        a = new ArrayList<String>();


        itemdetailid_arr = new ArrayList();
        st_spinner1_arr = new ArrayList();
        etqty_arr = new ArrayList();
        etbqty_arr = new ArrayList();
        etrate_arr = new ArrayList();
        et_discountarr = new ArrayList();
        stunitid_arr = new ArrayList();
        st_subpackingqty_arr = new ArrayList();
        st_packingqty_arr = new ArrayList();
        st_totalqty_arr = new ArrayList();
        st_quantitybyid_arr = new ArrayList();
        st_taxvalue_arr = new ArrayList();

        fab_dialog = view.findViewById(R.id.showalertdialog);

        spin_customername = view.findViewById(R.id.customernames);
        spin_terms = view.findViewById(R.id.termss);
        et_invoicedate = view.findViewById(R.id.invoicedatee);
        et_duedate = view.findViewById(R.id.duedatee);
        et_lasttotal = view.findViewById(R.id.et_lasttotal);
        et_subtotal = view.findViewById(R.id.et_subtotal);
        et_lastfinaltotal = view.findViewById(R.id.et_lastfinaltotal);
        et_lastbalancedue = view.findViewById(R.id.et_lastbalancedue);
        et_finaldiscount = view.findViewById(R.id.et_finaldiscount);
        et_message = view.findViewById(R.id.et_message);
        et_billingaddress = view.findViewById(R.id.billingaddresse);

        btn_getallids = view.findViewById(R.id.btn_getdata);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_addinvoice);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.rv_addgst);

        final String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());


        et_invoicedate.setText(date);


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
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                c.add(Calendar.DAY_OF_MONTH, nosofdays);
                                String newDate = sdf.format(c.getTime());
                                //.makeText(getContext(), "New Date" + newDate.toString(), //.LENGTH_SHORT).show();

                                et_duedate.setText(newDate);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        fab_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Adding the string request to the queue
                //RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                //requestQueue.add(stringRequest);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                builder.setView(dialogView);
                final Dialog dialog = builder.create();
                spinner1 = dialogView.findViewById(R.id.spinner1);
                spinner2 = dialogView.findViewById(R.id.spinner2);
                spin_tax = dialogView.findViewById(R.id.spin_tax);

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                requestQueue1.add(stringRequest2);


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

                            if(etdiscount.getText().toString().equals("0"))
                                etamount.setText(amount_nos);
                            else{
                                etamount.setText(dis_amount);
                            }
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
                            }else {

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

                            if(etdiscount.getText().toString().equals("0"))
                                etamount.setText(amount_nos);
                            else{
                                etamount.setText(dis_amount);
                            }

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
                        try {
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
                        }catch(ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                        catch(IndexOutOfBoundsException e){e.printStackTrace();}
                        double al77_int = Double.parseDouble(amount_temp.trim());
                        sum1 = sum1+al77_int;

                        String sum2 = String.valueOf(sum1);
                        et_subtotal.setText(sum2);

                        Toast.makeText(getContext(), "tax is  " +  newStr.toString(), Toast.LENGTH_SHORT).show();

                        try {

                            etqty_arr.add(etqty.getText().toString());
                            etbqty_arr.add(etbqty.getText().toString());
                            etrate_arr.add(etrate.getText().toString());
                            et_discountarr.add(etdiscount.getText().toString());

                        }catch(ArithmeticException e)  {e.printStackTrace();}
                         catch(NumberFormatException e){e.printStackTrace();}
                         catch(NullPointerException e) {e.printStackTrace();}


                        //.makeText(AddNewInvoice.this.getContext(), "QL7 IS\n\n" + ql7.toString() , //.LENGTH_SHORT).show();

                        try{
                            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddNewInvoice.this.getContext());

                            recyclerView.setLayoutManager(mLayoutManager);

                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                           /* addinvoice_adapter = new AddNewInvoiceRecyclerViewAdapter(getContext(), ql1, ql2, ql3, ql7, ql5, ql6, ql7, ql8,st_spinner1_arr,stunitid_arr,st_subpackingqty_arr,st_packingqty_arr,st_totalqty_arr,st_quantitybyid_arr,st_taxvalue_arr, "1");
                            addinvoice_adapter.setClickListener(AddNewInvoice.this);
                            recyclerView.setAdapter(addinvoice_adapter);
                            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                    DividerItemDecoration.VERTICAL));
                            addinvoice_adapter.notifyDataSetChanged();*/

                            adapter = new MyRecyclerViewAdapter(getContext(), ql1, ql2, ql3, ql7, ql5, ql6, ql7, ql8, "1");
                            adapter.setClickListener(AddNewInvoice.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                    DividerItemDecoration.VERTICAL));
                            addinvoice_adapter.notifyDataSetChanged();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        try{

                            final RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(AddNewInvoice.this.getContext());

                            recyclerView1.setLayoutManager(mLayoutManager1);

                           // int user_id2 = addinvoice_adapter.getItemCount();
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
                            adapter1.setClickListener(AddNewInvoice.this);
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

                final StringRequest saveTransactionMaster = new StringRequest(Request.Method.POST, Config.SAVE_TRANSACTION_MASTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                if (TextUtils.isEmpty(response)) {
                                    //Creating a shared preference
                                    //.makeText(getContext(), "Unable to fetch tax data" + response.toString(), //.LENGTH_LONG).show();

                                } else {
                                    try {

                                        //itemdetailid = new ArrayList<String>();
                                        Toast.makeText(getContext(), "Response \n" + response, Toast.LENGTH_SHORT).show();
                                        JSONObject json = new JSONObject(response);
                                        itemdetailid=(json.getString("ItemDetailId"));

                                        itemdetailid_arr.add(json.getString("ItemDetailId"));
                                        Log.d("mytag", "onResponse: ItemDetailId in array"+itemdetailid_arr);
                                        Log.d("mytag", "onResponse: SAVE_TRANSACTION_MASTER_URL"+response);

                                        /*Log.d("mytag", "onResponse: SAVE_TRANSACTION_MASTER_URL itemDetailId"+itemdetailid);
                                        Toast.makeText(getContext(), "itemDetailId"+itemdetailid.toString(), Toast.LENGTH_LONG).show();
                                        Log.d("mytag", "array list itemdetailId: "+itemdetailid.toString());*/

                                        saveTransacactionDetails();

                                    }catch(JSONException e) {e.printStackTrace();}
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                Toast.makeText(getContext(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
                            }
                        }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        //params.put("ItemId", "ItemId");
                        //params.put("ItemName", "ItemName");

                        params.put("formid", form_id);
                        params.put("formtype", "N");
                        params.put("htransactionid", String.valueOf(0));
                        params.put("personId", st_customervalue);
                        params.put("contactId", contactId);
                        params.put("discount", et_finaldiscount.getText().toString());
                        params.put("datecreated", et_invoicedate.getText().toString());
                        params.put("duedate", et_duedate.getText().toString());
                        params.put("paytermval",st_termsvalue);
                        params.put("remainamount",String.valueOf(0));
                        params.put("finaltotal",et_lastfinaltotal.getText().toString());
                        params.put("remark",et_message.getText().toString());
                        params.put("company_id",company_id);

                        //returning parameter
                        return params;
                    }
                };

                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                requestQueue1.add(saveTransactionMaster);

            }
        });
        return view;
    }

    public void saveTransacactionDetails(){

        try {
            int count = adapter.getItemCount();
            Log.d("mytag1", "saveTransacactionDetails: COunt" + count);


            //for(i=0;i<count;i++) {

            try {


                for (r = 0; r < count; r++) {

                    Log.d("mytag1", "ql3 of 0: COunt\n" + ql3.get(r));

                    final String qty = ql3.get(r).toString();
                    final String billqty = ql4.get(r).toString();
                    final String rate = ql5.get(r).toString();
                    final String discount = ql6.get(r).toString();
                    final String spinner1_arr=st_spinner1_arr.get(r).toString();
                    final String unitid_arr=stunitid_arr.get(r).toString();
                    final String stsubpacking=st_subpackingqty_arr.get(r).toString();
                    final String stpackingqty=st_packingqty_arr.get(r).toString();
                    final String sttotalqty=st_totalqty_arr.get(r).toString();
                     final String stqtybyid=st_quantitybyid_arr.get(r).toString();
                    final String sttaxvalue=st_taxvalue_arr.get(r).toString();

                    Log.d("mytag", "\n\nGetting Values: Intialising For Loop\n formid" + form_id +
                            "\ntransactionId" + itemdetailid + "\nitemdetailid" + spinner1_arr + "\nqty" + qty +
                            "\nbilling qty" + billqty + "\nrate" + rate + "\nitemdiscount" + discount +
                            "\nitemunits" + unitid_arr + "\nunitsubpackingqty" + stsubpacking +
                            "\nunitremainqty" + sttotalqty + "\nunitpackingqty" + stpackingqty +
                            "\nhiddenqtyonhand" + stqtybyid + "\ntax" + sttaxvalue + "\ncompany_id" + company_id);
                    // final String st_spinner1_arr = ;


                    saveTransactionDetails = new StringRequest(Request.Method.POST, Config.SAVE_TRANSACTION_DETAILS_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //If we are getting success from server
                                    if (TextUtils.isEmpty(response)) {
                                        //Creating a shared preference
                                        //.makeText(getContext(), "Unable to fetch tax data" + response.toString(), //.LENGTH_LONG).show();


                                    } else {
                                        Fragment fragment = new Invoices();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.main_container, fragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                        Toast.makeText(getContext(), "Response \n" + response, Toast.LENGTH_SHORT).show();
                                        Log.d("mytag", "onResponse: SAVE_TRANSACTION_DETAILS_URL" + response);
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


                            params.put("formid", form_id);
                            params.put("formtype", form_type);
                            params.put("hidetransactionid", String.valueOf(hidetransactionid));

                            params.put("transactionId", itemdetailid);
                            params.put("itemdetailid", spinner1_arr);
                            params.put("qty", qty);
                            params.put("billingqty", billqty);
                            params.put("rate", rate);
                            params.put("itemdiscount",discount);
                            params.put("itemunits", unitid_arr);
                            params.put("unitsubpackingqty", stsubpacking);
                            params.put("unitpackingqty", stpackingqty);
                            params.put("unitremainqty", sttotalqty);
                            params.put("hiddenqtyonhand", stqtybyid);
                            params.put("tax", sttaxvalue);

                            params.put("company_id", company_id);


                            Log.d("mytag", "getParams: SAVE_TRANSACTION_DETAILS_URL\n formid" + form_id +
                                    "\ntransactionId" + itemdetailid + "\nitemdetailid" + spinner1_arr + "\nqty" + qty +
                                    "\nbilling qty" + billqty + "\nrate" + rate + "\nitemdiscount" + discount +
                                    "\nitemunits" + unitid_arr + "\nunitsubpackingqty" + stsubpacking +
                                    "\nunitremainqty" + sttotalqty + "\nunitpackingqty" + stpackingqty +
                                    "\nhiddenqtyonhand" + stqtybyid + "\ntax" + sttaxvalue + "\ncompany_id" + company_id);


                            //returning parameter
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(saveTransactionDetails);

                }
            }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
            catch (IndexOutOfBoundsException e){e.printStackTrace();}
        }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
        catch (IndexOutOfBoundsException e){e.printStackTrace();}
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
                            //.makeText(getContext(), "Unable to fetch tax data" + response.toString(), //.LENGTH_LONG).show();

                        } else {

                            try {

                                //.makeText(getContext(), "getTaxValue Response \n\n\n" + response.toString(), //.LENGTH_SHORT).show();

                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();
                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("TaxPercent"));
                                    al2.add(json.getString("TaxName"));
                                }

                                Integer a1 = al1.size();
                                String a2 = valueOf(a1);
                                spinnerArray4 = new String[al1.size()];
                                spinnerMap4 = new HashMap<Integer, String>();


                                //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


                                for (int i = 0; i < al1.size(); i++) {
                                    spinnerMap4.put(i, al1.get(i));
                                    spinnerArray4[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);*/

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_tax.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // //.makeText(getContext(), "Value"+value, //.LENGTH_SHORT).show()
                                //


                                spin_tax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_taxvalue = (String) spinnerMap4.get(spin_tax.getSelectedItemPosition());

                                        st_taxvalue_arr.add(st_taxvalue);

                                        //taxvalue_arr.add((String) spinnerMap4.get(spin_tax.getSelectedItemPosition()));


                                        //.makeText(getContext(), "Size Value" + st_taxvalue, //.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    //.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();


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


    public void LoadAllProductInSpin() {
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

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewInvoice.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, al2);

                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(dataAdapter);

                                //st_spinner1arr.add((String) spinnerMap.get(spinner1.getSelectedItem()));
                             //   Log.d("mytag", "st_spinner1arr SpinnerMap"+st_spinner1arr.toString());
                                st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItem());

                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                requestQueue1.add(stringRequest1);


                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                       // st_spinner1_arr.clear();

                                        st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItemPosition());

                                        try{
                                        st_spinner1_arr.add(st_spinner1);}
                                        catch(IndexOutOfBoundsException e){e.printStackTrace();}
                                        //.makeText(AddNewInvoice.this.getContext(), st_spinner1.toString(), //.LENGTH_SHORT).show();
                                        Log.d("mytag", "onItemSelected: st_spinner1 array"+st_spinner1_arr);
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
    }

   /* public void getAddressInfoById(){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Config.FETCH_INFO_ADDRESS_BY_ID_URL,
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


                                //Storing values in array
                                *//*try {
                                    subpackingqty_arr.add(String.valueOf(st_subpackingqty));
                                    packingqty_arr.add(String.valueOf(st_packingqty));
                                    totalqty_arr.add(String.valueOf(st_totalqtydouble));
                                    quantitybyid_arr.add(String.valueOf(st_quantitybyid));
                                }catch(ArithmeticException e){e.printStackTrace();}
                                catch (NumberFormatException e){e.printStackTrace();}*//*

                                //.makeText(getContext(), "Quantity Id" + st_quantitybyid + "\nPacking_Qty" + st_packingqty
                                //+ "\nSub_PackingQty" + st_subpackingqty + "\nTotalQty" + st_totalqty+"\nst_price"+st_price, //.LENGTH_SHORT).show();

                                ethsn.setText(a3.toString());

                                etrate.setText(a9.toString());
                                etamount.setText(etrate.getText().toString());




                                Log.d("mytag", "AddNewInvoice onResponse:getProductsDetailsById st_packingqty "+st_packingqty+"\nst_packingqtydouble "
                                        +st_packingqtydouble+"\nst_subpackingqty"+st_subpackingqty+"\nst_totalqty"+st_totalqty+
                                        "\nst_totalqtydouble"+st_totalqtydouble+"\nstprice"+st_price);



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
    }*/

    public void getAddressInfoById(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_INFO_ADDRESS_BY_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            //.makeText(getContext(), "Error in fetching data" + response.toString(), //.LENGTH_LONG).show();

                        } else {


                            try {




                                JSONObject json = new JSONObject(response);


                                contactId = (json.getString("contactId"));
                                et_billingaddress.setText(json.getString("contactAddress"));

                                Toast.makeText(getContext(), "ContactId"+contactId, Toast.LENGTH_SHORT).show();



                                //Storing values in array
                                /*try {
                                    subpackingqty_arr.add(String.valueOf(st_subpackingqty));
                                    packingqty_arr.add(String.valueOf(st_packingqty));
                                    totalqty_arr.add(String.valueOf(st_totalqtydouble));
                                    quantitybyid_arr.add(String.valueOf(st_quantitybyid));
                                }catch(ArithmeticException e){e.printStackTrace();}
                                catch (NumberFormatException e){e.printStackTrace();}*/

                                //.makeText(getContext(), "Quantity Id" + st_quantitybyid + "\nPacking_Qty" + st_packingqty
                                //+ "\nSub_PackingQty" + st_subpackingqty + "\nTotalQty" + st_totalqty+"\nst_price"+st_price, //.LENGTH_SHORT).show();



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
                params.put("custid", st_customervalue);
                params.put("company_id", company_id);


                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);
    }

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

                                try {
                                    st_quantitybyid = String.valueOf(Integer.parseInt(a4));
                                    st_packingqty = Integer.parseInt(String.valueOf(Integer.parseInt(a6)));
                                    st_packingqtydouble = Double.parseDouble(String.valueOf(Double.parseDouble(a6)));
                                    st_subpackingqty = Integer.parseInt(String.valueOf(Integer.parseInt(a7)));

                                    st_totalqty = Integer.parseInt(a10);
                                    st_totalqtydouble = Double.parseDouble(a10);
                                    st_price = Double.parseDouble(a9);
                                }catch (ArithmeticException e){
                                    e.printStackTrace();
                                }catch (NumberFormatException e){
                                    e.printStackTrace();
                                }

                                try {
                                    st_packingqty_arr.add(a6);
                                    st_subpackingqty_arr.add(a7);
                                    st_totalqty_arr.add(a10);
                                    st_quantitybyid_arr.add(a4);
                                }catch(ArithmeticException e){e.printStackTrace();
                                    }
                                catch(NumberFormatException e){e.printStackTrace();}
                                catch (ClassCastException e){e.printStackTrace();}



                                //Storing values in array
                                /*try {
                                    subpackingqty_arr.add(String.valueOf(st_subpackingqty));
                                    packingqty_arr.add(String.valueOf(st_packingqty));
                                    totalqty_arr.add(String.valueOf(st_totalqtydouble));
                                    quantitybyid_arr.add(String.valueOf(st_quantitybyid));
                                }catch(ArithmeticException e){e.printStackTrace();}
                                catch (NumberFormatException e){e.printStackTrace();}*/

                                //.makeText(getContext(), "Quantity Id" + st_quantitybyid + "\nPacking_Qty" + st_packingqty
                                        //+ "\nSub_PackingQty" + st_subpackingqty + "\nTotalQty" + st_totalqty+"\nst_price"+st_price, //.LENGTH_SHORT).show();

                                ethsn.setText(a3.toString());

                                etrate.setText(a9.toString());
                                etamount.setText(etrate.getText().toString());




                                Log.d("mytag", "AddNewInvoice onResponse:getProductsDetailsById st_packingqty "+st_packingqty+"\nst_packingqtydouble "
                                        +st_packingqtydouble+"\nst_subpackingqty"+st_subpackingqty+"\nst_totalqty"+st_totalqty+
                                        "\nst_totalqtydouble"+st_totalqtydouble+"\nstprice"+st_price);



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

    public void getCustomerNameSpin() {
        stringRequest3 = new StringRequest(Request.Method.POST, Config.DISPLAY_CUSTOMERS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            //.makeText(getContext(), "Unable to fetch customer data" + response.toString(), //.LENGTH_LONG).show();

                        } else {

                            try {

                                //.makeText(getContext(), "101" + response.toString(), //.LENGTH_SHORT).show();

                                List<String> cl1 = new ArrayList<String>();
                                List<String> cl2 = new ArrayList<String>();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    cl1.add(json.getString("pid"));
                                    cl2.add((json.getString("name")));


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
//                    //.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();

                                Integer a1 = cl1.size();
                                String a2 = valueOf(a1);
                                spinnerArray = new String[cl1.size()];
                                spinnerMap = new HashMap<Integer, String>();

                                // //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


                                for (int i = 0; i < cl1.size(); i++) {
                                    spinnerMap.put(i, cl1.get(i));
                                    spinnerArray[i] = cl1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cl2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_customername.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // //.makeText(getContext(), "Value"+value, //.LENGTH_SHORT).show()
                                //


                                spin_customername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_customervalue = (String) spinnerMap.get(spin_customername.getSelectedItemPosition());

                                        //getting address of particular customer based on id;
                                        getAddressInfoById();

                                        //.makeText(getContext(), "Size Value" + st_customervalue, //.LENGTH_SHORT).show();
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

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
        requestQueue3.add(stringRequest3);

    }

    public void getPaytermsInSpin() {
        stringRequest4 = new StringRequest(Request.Method.POST, Config.FETCH_PAY_TERMS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            //.makeText(getContext(), "Unable to fetch terms data" + response.toString(), //.LENGTH_LONG).show();

                        } else {

                            try {

                                //.makeText(getContext(), "111" + response.toString(), //.LENGTH_SHORT).show();

                                List<String> al1 = new ArrayList<String>();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add("NET-".concat(json.getString("Paytermval")));


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
//                    //.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();

                                Integer a1 = al1.size();
                                String a2 = valueOf(a1);
                                spinnerArray = new String[al1.size()];
                                spinnerMap1 = new HashMap<Integer, String>();

                                // //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


                                for (int i = 0; i < al1.size(); i++) {
                                    spinnerMap1.put(i, al1.get(i));
                                    spinnerArray[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, al1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_terms.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // //.makeText(getContext(), "Value"+value, //.LENGTH_SHORT).show()
                                //


                                spin_terms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_termsvalue = (String) spinnerMap1.get(spin_terms.getSelectedItemPosition());
                                        String[] items = st_termsvalue.split("[a-zA-Z]+");
                                        String[] items1 = items[1].split("-");
                                        nosofdays = Integer.parseInt((items1[1]));


                                        //.makeText(getContext(), "Term Value" + items1[1].toString(), //.LENGTH_SHORT).show();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                        Calendar c = Calendar.getInstance();
                                        try {
                                            //Setting the date to the given date
                                            c.setTime(sdf.parse(et_invoicedate.getText().toString()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        c.add(Calendar.DAY_OF_MONTH, nosofdays);
                                        String newDate = sdf.format(c.getTime());
                                        //.makeText(getContext(), "New Date" + newDate.toString(), //.LENGTH_SHORT).show();

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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("company_id", company_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue4 = Volley.newRequestQueue(getContext());
        requestQueue4.add(stringRequest4);
    }


    public void getUnitDataInSpin(){
        stringRequest2 = new StringRequest(Request.Method.POST, Config.FETCH_UNIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //bl2.clear();

                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(getContext(), "Error in fetching data"+response.toString(), //.LENGTH_LONG).show();

                        }else{


                            try {

                                //.makeText(getContext(), "161"+response.toString(), //.LENGTH_SHORT).show();
                                List<String> bl1=new ArrayList<String>();
                                List<String> bl2=new ArrayList<String>();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();--
                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    bl1.add(json.getString("PackingId"));
                                    bl2.add(json.getString("PackingType"));



                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }

                                Integer a1 = bl1.size();
                                String a2 = valueOf(a1);
                                spinnerArray3 = new String[bl1.size()];
                                spinnerMap2 = new HashMap<Integer, String>();

                                // //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


                                for (int i = 0; i <bl1.size(); i++)
                                {
                                    spinnerMap2.put(i,bl1.get(i));
                                    spinnerArray3[i] = bl1.get(i);
                                }


                                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddNewInvoice.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, bl2);

                                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(dataAdapter1);



                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        st_unitpostion=spinner2.getSelectedItemPosition();
                                        st_unitid= (String) spinnerMap2.get(spinner2.getSelectedItemPosition());

                                        stunitid_arr.add(st_unitid);

                                       //    unitid_arr.clear();
                                       /// unitid_arr.add(st_unitid);
                                        //Log.d("mytag", "onItemSelected: unitid_Arr"+unitid_arr);

                                        //.makeText(getContext(), "Unit Position"+st_unitpostion, //.LENGTH_SHORT).show();
                                        //.makeText(getContext(),"the selected item is\n"+ st_unitid, //.LENGTH_SHORT).show();

                                        /*double do_bqty=Double.parseDouble(etbqty.getText().toString());

                                        if(st_unitpostion==1){
                                            if(do_bqty > amount){
                                                etqty.setText(dis_amount);
                                                etbqty.setText(dis_amount);
                                            }
                                        }
                                        else if(st_unitpostion==2) {
                                            if(do_bqty > bag){
                                                etqty.setText(no3);
                                                etbqty.setText(no3);
                                            }
                                        }
                                        RequestQueue requestQueue10 = Volley.newRequestQueue(getContext());
                                        requestQueue10.add(stringRequest5);*/

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                                st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItem());
//
//                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
//                                requestQueue1.add(stringRequest1);


                                //.makeText(getContext(),"santosh\n"+ bl2.toString(), //.LENGTH_SHORT).show();


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


    }



    //Fetching unit data like packingqty,subpackingqty,Quantity...
    public void getItemDataByUnitId(){
        stringRequest5 = new StringRequest(Request.Method.POST, Config.FETCH_ITEM_UNIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(getContext(), "Invalid username or password"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            try {

                                //.makeText(getContext(), "1110000"+response.toString(), //.LENGTH_SHORT).show();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();--


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
                params.put("itemnameid", st_spinner1.toString());
                //                params.put("password", password);

                //returning parameter
                return params;
            }
        };
    }

    public void setQuantityForFocus(){
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
                                           //.makeText(getContext(), "amount"+amount, //.LENGTH_SHORT).show();*/
                //amount_nos=Double.toString(amount);
                // etamount.setText(amount_nos);
                //.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), //.LENGTH_SHORT).show();



            } else {

                etbqty.setText(etqty.getText().toString());
                etqty.setText(etbqty.getText().toString());

            }

            try{in_bqty=Integer.parseInt(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

            //calculating amount and setting to amount
            double amount=in_bqty*rate_editext1;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            //.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, //.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            //.makeText(getContext(), "discount"+discout_amount, //.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            String dis_amount=String.valueOf(final_discount);

            if(etdiscount.getText().toString().equals("0"))
                etamount.setText(amount_nos);
            else{
                etamount.setText(dis_amount);
            }

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
            amount=in_bqty*rate_editext1*st_subpackingqty;
            //calculating discount and updating amount
            try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
            //.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, //.LENGTH_SHORT).show();
            double discout_amount=(amount*dou_etdiscount)/100;
            //.makeText(getContext(), "discount"+discout_amount, //.LENGTH_SHORT).show();
            double final_discount=amount-discout_amount;

            dis_amount=String.valueOf(final_discount);

            if(etdiscount.getText().toString().equals("0"))
                etamount.setText(amount_nos);
            else{
                etamount.setText(dis_amount);
            }

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

            if(etdiscount.getText().toString().equals("0"))
                etamount.setText(amount_nos);
            else{
                etamount.setText(dis_amount);
            }

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

            if(etdiscount.getText().toString().equals("0"))
                etamount.setText(amount_nos);
            else{
                etamount.setText(dis_amount);
            }

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

            if(etdiscount.getText().toString().equals("0"))
                etamount.setText(amount_nos);
            else{
                etamount.setText(dis_amount);
            }

        }

    }

    @Override
    public void onItemClick(View view, final int position) {

        String user_id1 = adapter.getItem(position);
         user_id2 = adapter.getItemCount();
        String user_id21 = valueOf(user_id2);


        //.makeText(getContext(), "getitem is  " + user_id1.toString() + " on row number " + position, //.LENGTH_SHORT).show();
        //.makeText(getContext(), "count is  " + user_id21.toString() + " on row number " + position, //.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();
        spinner1 = dialogView.findViewById(R.id.spinner1);
        spinner2 = dialogView.findViewById(R.id.spinner2);
        spin_tax = dialogView.findViewById(R.id.spin_tax);

        stringRequest12 = new StringRequest(Request.Method.POST, Config.FETCH_ITEMNAME_INFO_BY_ID_URL,
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

                                //.makeText(getContext(), "Quantity Id" + st_quantitybyid + "\nPacking_Qty" + st_packingqty
                                      //  + "\nSub_PackingQty" + st_subpackingqty + "\nTotalQty" + st_totalqty+"\nst_price"+st_price, //.LENGTH_SHORT).show();

                                final EditText ethsn = dialogView.findViewById(R.id.ethsn);
                                final EditText etqty = dialogView.findViewById(R.id.etqty);
                                final EditText etbqty = dialogView.findViewById(R.id.etbqty);
                                final EditText etrate = dialogView.findViewById(R.id.etrate);
                                final EditText etdiscount =dialogView.findViewById(R.id.etdiscount);
                                final EditText etamount= dialogView.findViewById(R.id.etamount);

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



        stringRequest11 = new StringRequest(Request.Method.POST, Config.FETCH_PRODUCT_URL,
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
                                    al5.add("Recorded Label:".concat(json.getString("ReorderLabel")));
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

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewInvoice.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, al2);

                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(dataAdapter);
                                int spinnerPosition = dataAdapter.getPosition(String.valueOf(ql2.get(position)));
                                spinner1.setSelection(spinnerPosition);

//                                st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItem());
//                                //.makeText(AddNewInvoice.this.getContext(), "st spinner1 "+st_spinner1.toString(), //.LENGTH_SHORT).show();



                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        st_spinner1 = (String) spinnerMap.get(spinner1.getSelectedItemPosition());
                                        //.makeText(AddNewInvoice.this.getContext(), "st spinner1 "+st_spinner1.toString(), //.LENGTH_SHORT).show();

                                        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                        requestQueue1.add(stringRequest12);



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

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest2);


        final EditText ethsn = dialogView.findViewById(R.id.ethsn);
        final EditText etqty = dialogView.findViewById(R.id.etqty);
        final EditText etbqty = dialogView.findViewById(R.id.etbqty);
        final EditText etrate = dialogView.findViewById(R.id.etrate);
        final EditText etdiscount = dialogView.findViewById(R.id.etdiscount);
        final EditText etamount= dialogView.findViewById(R.id.etamount);

        etqty.setText(ql3.get(position).toString());
        etbqty.setText(ql4.get(position).toString());

        //.makeText(AddNewInvoice.this.getContext(), "QL7 IS\n\n" + ql7.get(position).toString() , //.LENGTH_SHORT).show();



       /* ethsn.setText(ql8.get(position).toString());
        etqty.setText(ql3.get(position).toString());
        etbqty.setText(ql4.get(position).toString());
        etrate.setText(ql5.get(position).toString());
        etdiscount.setText(ql6.get(position).toString());
        etamount.setText(ql7.get(position).toString());*/


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
                                           //.makeText(getContext(), "amount"+amount, //.LENGTH_SHORT).show();*/
                        //amount_nos=Double.toString(amount);
                        // etamount.setText(amount_nos);
                        //.makeText(getContext(), "Inside if loop etbqty"+etbqty.getText().toString()+"\netqty"+etqty.getText().toString(), //.LENGTH_SHORT).show();



                    } else {

                        etbqty.setText(etqty.getText().toString());
                        etqty.setText(etbqty.getText().toString());

                    }

                    try{in_bqty=Integer.parseInt(etbqty.getText().toString());}catch(NumberFormatException e){e.printStackTrace();}

                    //calculating amount and setting to amount
                    double amount=in_bqty*rate_editext1;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    //.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, //.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    //.makeText(getContext(), "discount"+discout_amount, //.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    String dis_amount=String.valueOf(final_discount);

                    if(etdiscount.getText().toString().equals("0"))
                        etamount.setText(amount_nos);
                    else{
                        etamount.setText(dis_amount);
                    }

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
                    amount=in_bqty*rate_editext1*st_subpackingqty;
                    //calculating discount and updating amount
                    try { dou_etdiscount=Double.parseDouble(etdiscount.getText().toString());}catch (NumberFormatException e){e.printStackTrace();}
                    //.makeText(getContext(), "amount"+amount+"\ndou_etdiscount"+dou_etdiscount, //.LENGTH_SHORT).show();
                    double discout_amount=(amount*dou_etdiscount)/100;
                    //.makeText(getContext(), "discount"+discout_amount, //.LENGTH_SHORT).show();
                    double final_discount=amount-discout_amount;

                    dis_amount=String.valueOf(final_discount);

                    if(etdiscount.getText().toString().equals("0"))
                        etamount.setText(amount_nos);
                    else{
                        etamount.setText(dis_amount);
                    }

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

                    if(etdiscount.getText().toString().equals("0"))
                        etamount.setText(amount_nos);
                    else{
                        etamount.setText(dis_amount);
                    }

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

                    if(etdiscount.getText().toString().equals("0"))
                        etamount.setText(amount_nos);
                    else{
                        etamount.setText(dis_amount);
                    }

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

                    if(etdiscount.getText().toString().equals("0"))
                        etamount.setText(amount_nos);
                    else{
                        etamount.setText(dis_amount);
                    }

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



        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_ITEM_TAX1_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (TextUtils.isEmpty(response)) {
                            //Creating a shared preference
                            //.makeText(getContext(), "Unable to fetch tax data" + response.toString(), //.LENGTH_LONG).show();

                        } else {

                            try {

                                //.makeText(getContext(), "getTaxValue Response \n\n\n" + response.toString(), //.LENGTH_SHORT).show();


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("TaxPercent"));
                                    al2.add(json.getString("TaxName"));

                                }
                                Integer a1 = al1.size();
                                String a2 = valueOf(a1);
                                spinnerArray4 = new String[al1.size()];
                                spinnerMap4 = new HashMap<Integer, String>();

                                // //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


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

                                // //.makeText(getContext(), "Value"+value, //.LENGTH_SHORT).show()
                                //


                                spin_tax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_taxvalue = (String) spinnerMap4.get(spin_tax.getSelectedItemPosition());


                                        //.makeText(getContext(), "Size Value" + st_taxvalue, //.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    //.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();


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

        ethsn.setText(ql8.get(position).toString());

        etrate.setText(ql5.get(position).toString());
        etdiscount.setText(ql6.get(position).toString());
        etamount.setText(ql7.get(position).toString());

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

                //.makeText(AddNewInvoice.this.getContext(), al3.toString(), //.LENGTH_SHORT).show();
                adapter.notifyItemChanged(updateIndex);
                adapter.notifyDataSetChanged();

            }
        });

        //.makeText(AddNewInvoice.this.getContext(), al1.toString(), //.LENGTH_SHORT).show();

        dialog.show();

    }


    @Override
    public void onDestroy() {
                super.onDestroy();
      /*  try {
            itemdetailid_arr.clear();
            st_spinner1_arr.clear();
            etqty_arr.clear();
            etbqty_arr.clear();
            etrate_arr.clear();
            et_discountarr.clear();
            stunitid_arr.clear();
            st_subpackingqty_arr.clear();
            st_packingqty_arr.clear();
            st_totalqty_arr.clear();
            st_quantitybyid_arr.clear();
            st_taxvalue_arr.clear();
        }catch(NullPointerException e){e.printStackTrace();}*/
    }

}