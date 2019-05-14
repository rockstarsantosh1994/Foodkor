package com.example.xxovek.foodkor.SALES.invoice;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xxovek.foodkor.CardViewAdapter;
import com.example.xxovek.foodkor.DynamicViews;
import com.example.xxovek.foodkor.MyRecyclerViewAdapter;
import com.example.xxovek.foodkor.R;
import com.example.xxovek.foodkor.RecyclerAdapter2;
import com.example.xxovek.foodkor.SALES.customer.Customers;
import com.example.xxovek.foodkor.URLs.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewInvoice extends Fragment implements View.OnClickListener{
    public int i;
    public RecyclerAdapter2 adapter;
    RecyclerView recyclerView;
    Button button12;
    public CardView cardView3;
    DynamicViews dnv;
    AddNewInvoice context;
    RelativeLayout myRoot,relativeLayout;
    View view;
    TextView textview12;
    ViewGroup parent;
    GridLayout mlayout;
    private CardViewAdapter mAdapter;
   // MainActivity main;


    FloatingActionButton fab_dialog;
    EditText textIn,textIn1,et_invoicedate,et_duedate;
    Button buttonAdd, btn_getallids;
    LinearLayout container1;
    ArrayList<String> n,e1,e2,e3,e4,e5,e6,a;
    private EditText textOut,textOut1;
    private int len;
    String a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15;
    CardView cardView;
    private CardView cardView1;
    private ArrayList al1,al2,al3,al4,al5,al6,al7,al8,bl1,bl2;
    private EditText textOut0;
    //    static ArrayList<JSONObject> items;
    JSONObject item2;
    JSONObject jo;
    private View addView;
    String [] spinnerArray,spinnerArray1;
    HashMap<Integer, String> spinnerMap,spinnerMap1;
    StringRequest stringRequest1,stringRequest2,stringRequest3,stringRequest4;
    String st_spinner1;
    int check=0;
    StringRequest stringRequest;
    Spinner spinner1,spinner2,spin_customername,spin_terms;
    TextInputEditText etUsername;
    TextInputEditText etPassword;
    TextInputEditText etbqty,etrate,etdiscount,etamount;
    String etamount2,st_customervalue,st_termsvalue;
    private int mYear, mMonth, mDay;

    public AddNewInvoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_invoice, container, false);
        context=this;

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        final String company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        n= new ArrayList<String>();
        e1= new ArrayList<String>();
        e2= new ArrayList<String>();
        e3= new ArrayList<String>();
        e4= new ArrayList<String>();
        e5= new ArrayList<String>();
        e6= new ArrayList<String>();

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

        bl1 = new ArrayList<String>();
        bl2 = new ArrayList<String>();


        a= new ArrayList<String>();


        fab_dialog=view.findViewById(R.id.showalertdialog);
//        textIn = findViewById(R.id.textin);
//        textIn1 = findViewById(R.id.textin1);

//        buttonAdd = findViewById(R.id.add);
        btn_getallids = view.findViewById(R.id.btn_getdata);
        container1 = view.findViewById(R.id.container1);
        spin_customername=view.findViewById(R.id.customernames);
        spin_terms=view.findViewById(R.id.termss);
        et_invoicedate=view.findViewById(R.id.invoicedatee);
        et_duedate=view.findViewById(R.id.duedatee);

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

                                et_invoicedate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        et_duedate.setOnClickListener(new View.OnClickListener() {
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

                                et_duedate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //Getting CUstomer DATA
        //Creating a string request
         stringRequest3 = new StringRequest(Request.Method.POST, Config.DISPLAY_CUSTOMERS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch customer data"+response.toString(), Toast.LENGTH_LONG).show();

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


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("pid"));
                                    al2.add((json.getString("name")));




                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();

                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray = new String[al1.size()];
                                spinnerMap = new HashMap<Integer, String>();

                                // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    spinnerMap.put(i,al1.get(i));
                                    spinnerArray[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
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


                                        Toast.makeText(getContext(), "Size Value"+st_customervalue, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest3);


        //fetching payterms and attaching to spinner
        stringRequest4 = new StringRequest(Request.Method.POST, Config.FETCH_PAY_TERMS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch terms data"+response.toString(), Toast.LENGTH_LONG).show();

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


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("Paytermval"));





                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
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


                                        Toast.makeText(getContext(), "Term Value"+st_termsvalue, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest4);


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


                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                requestQueue1.add(stringRequest2);



                etUsername = dialogView.findViewById(R.id.etUserName);
                etPassword = dialogView.findViewById(R.id.etPassword);
                etbqty = dialogView.findViewById(R.id.etbqty);
                etrate = dialogView.findViewById(R.id.etrate);
                etdiscount = dialogView.findViewById(R.id.etdiscount);
                etamount = dialogView.findViewById(R.id.etamount);


                Button btnLogin = dialogView.findViewById(R.id.btnLogin);

//                Toast.makeText(AddNewInvoice.this.getContext(), a1.toString(), Toast.LENGTH_SHORT).show();

//                etUsername.setText(a1.toString());
//                etPassword.setText(a2.toString());

                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
//                        Toast.makeText(MainActivity.this, "User name : "+etUsername.getText().toString()+"\nPassword : "+etPassword.getText().toString(), Toast.LENGTH_LONG).show();
                        LayoutInflater layoutInflater =
                                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                        addView = layoutInflater.inflate(R.layout.row, null);
                        cardView1 = (CardView) addView.findViewById(R.id.card1);
                        cardView1.setId(cardView1.generateViewId());

                        int cid = cardView1.getId();
                        final String cid1 = String.valueOf(cid);
                        cardView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Toast.makeText(AddNewInvoice.this.getContext(), cid1.toString(), Toast.LENGTH_SHORT).show();
                                String pid = (String) textOut0.getTag();
                                Toast.makeText(AddNewInvoice.this.getContext(), pid.toString(), Toast.LENGTH_SHORT).show();



                            }
                        });

                        textOut0 = (EditText) addView.findViewById(R.id.textout0);
//                        textOut.setId("");
                        textOut0.setText(spinner1.getSelectedItem().toString());
                        textOut0.setTag("a".concat(String.valueOf(cardView1.getId())));


                        textOut = (EditText) addView.findViewById(R.id.textout);
//                textOut.setId(textOut.generateViewId());
                        textOut.setText(etUsername.getText().toString());
                        textOut.setTag("b".concat(String.valueOf(cardView1.getId())));

                        textOut1 = (EditText) addView.findViewById(R.id.textout1);
//                textOut1.setId(textOut1.generateViewId());
                        textOut1.setText(etPassword.getText().toString());
                        textOut1.setTag("c".concat(String.valueOf(cardView1.getId())));


                        Toast.makeText(AddNewInvoice.this.getContext(), "id\n\n" + textOut.getId(), Toast.LENGTH_SHORT).show();

                        e1.add(etUsername.getText().toString());
                        e2.add(etPassword.getText().toString());
                        len = cardView1.getId();
                        String len1 = String.valueOf(len);

                        Toast.makeText(AddNewInvoice.this.getContext(), "len\n\n" + len1, Toast.LENGTH_SHORT).show();


                        try {
                            item2.put("aDataSort", etUsername.getText().toString());
                            item2.put("aTargets", etPassword.getText().toString());
                            items.add(item2);
                            //jo.put("aoColumnDefs", new JSONArray(items));

                            Toast.makeText(AddNewInvoice.this.getContext(), "the json object is\n\n" + item2.toString(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        container1.addView(addView, 0);

                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });



        stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_PRODUCT_URL,
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


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
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
                                spinnerMap = new HashMap<Integer,String>();

                                for(int p=0;p<al1.size();p++){

                                    spinnerMap.put(p, (String) al1.get(p));
                                    spinnerArray[p] = (String) al1.get(p);
                                }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewInvoice.this.getContext(),
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
                                        Toast.makeText(AddNewInvoice.this.getContext(), st_spinner1.toString(), Toast.LENGTH_SHORT).show();

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
                int type=1;
                params.put("Ttype", String.valueOf(type));
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };



        stringRequest1 = new StringRequest(Request.Method.POST, Config.FETCH_PRODUCT_FOR_UPDATE,
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

                                etUsername.setText(a3.toString());
                                etPassword.setText(a4.toString());
                                etrate.setText(a9.toString());

                                etPassword.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        Toast.makeText(getContext(), "161"+s.toString(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                        Toast.makeText(getContext(), "161"+s.toString(), Toast.LENGTH_SHORT).show();
                                        etbqty.setText(s.toString());

                                    }

                                });

                                etdiscount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        Toast.makeText(getContext(), "161"+s.toString(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                        Toast.makeText(getContext(), "et amount is "+s.toString(), Toast.LENGTH_SHORT).show();
                                        String etdiscount3 = s.toString();

                                        try {
                                            double etrate1 = Double.valueOf(etrate.getText().toString());
                                            double etdiscount1 = Double.valueOf(etdiscount3);

                                            double etamount1 = etrate1-(etrate1*etdiscount1*0.01);

                                            etamount2 = String.valueOf(etamount1);


                                        }catch(NumberFormatException ex) {

                                        }
                                        etamount.setText(etamount2.toString());


                                    }

                                });




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

        stringRequest2 = new StringRequest(Request.Method.POST, Config.FETCH_UNIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        bl2.clear();

                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Error in fetching data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{


                            try {

                                Toast.makeText(getContext(), "161"+response.toString(), Toast.LENGTH_SHORT).show();

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

                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddNewInvoice.this.getContext(),
                                        android.R.layout.simple_spinner_dropdown_item, bl2);

                                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(dataAdapter1);

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



//        LayoutTransition transition = new LayoutTransition();
//        container.setLayoutTransition(transition);


        btn_getallids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.clear();


//                int panelId = getResources().getIdentifier(String.valueOf(textOut0),"id",getPackageName());
//                String paneId1 = String.valueOf(panelId);

                //  Toast.makeText(AddNewInvoice.this.getContext(), paneId1.toString(), Toast.LENGTH_SHORT).show();

//                View view = addView.findViewWithTag("a1");
//                int id = view.getId();
//                String id1 = String.valueOf(id);
//                EditText sw = (EditText)addView.findViewById(id);
//                Toast.makeText(MainActivity.this, sw.getText().toString(), Toast.LENGTH_SHORT).show();
//
                for(int i=1;i<=len;i++) {

//                    try {
                    //retrieve id dynamically



//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                }
                for(int i=1;i<=len;i++) {


//                    CardView edit = (CardView)findViewById(i);
//
//                    int edit1 = edit.getId();
//                    String edit2 = String.valueOf(edit1);

                    //   a.add(edit.getText().toString());

                    Toast.makeText(AddNewInvoice.this.getContext(), "List Data112345\n\n" + e1.toString(), Toast.LENGTH_SHORT).show();


                    Toast.makeText(AddNewInvoice.this.getContext(), "List Data\n\n" + e2.toString(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(AddNewInvoice.this.getContext(), "List a is\n\n" + a.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
        }
    }
}