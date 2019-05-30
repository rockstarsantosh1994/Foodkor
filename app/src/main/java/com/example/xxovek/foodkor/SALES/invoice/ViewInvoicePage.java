package com.example.xxovek.foodkor.SALES.invoice;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */

public class ViewInvoicePage extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView rv_viewitem;
    private String st_transactionid;
    public MyRecyclerViewAdapter adapter;
    public StringRequest stringRequest3,stringRequest4,stringRequest5;
    Spinner spin_customername,spin_terms;
    HashMap spinnerMap,spinnerMap1;
    String[] spinnerArray;
    String st_customervalue;
    String company_id,st_termsvalue,a1,a2;
    int nosofdays;
    EditText et_invoicedate,et_duedate,et_billaddress;

    public ViewInvoicePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        st_transactionid = getArguments().getString("data");
        Toast.makeText(getActivity(),st_transactionid.toString(),Toast.LENGTH_LONG).show();

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_invoice_page, container, false);
        rv_viewitem=view.findViewById(R.id.rv_viewitem);
        spin_customername=view.findViewById(R.id.spin_customernames);
        spin_terms=view.findViewById(R.id.spin_terms);
        et_invoicedate=view.findViewById(R.id.et_invoicedate);
        et_duedate=view.findViewById(R.id.et_duedate);
        et_billaddress=view.findViewById(R.id.et_billingaddress);


        //Attaching PayTerms Value to Spinner...
        getPayTermsSpin();

        //Attaching CustomerName to Spinner....
        getCustomerNameSpin();




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


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();--

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("itemDetailId"));
                                    al2.add(json.getString("qty"));
                                    //al3.add(json.getString("BillQty"));
                                    al4.add(json.getString("rate"));
                                    al5.add(json.getString("itemunitval"));
                                    al6.add(json.getString("TaxType"));
                                    al7.add(json.getString("TaxPercent"));
                                    al8.add(json.getString("itemdiscount"));
                                    al9.add(json.getString("description"));
                                    al10.add(json.getString("TransactionId"));
                                    al11.add(json.getString("discount"));
                                    al12.add(json.getString("FinancialYear"));
                                    al13.add(json.getString("TransactionNumber"));
                                    al14.add(json.getString("DateCreated"));
                                    al15.add(json.getString("DueDate"));
                                    al16.add(json.getString("PersonId"));
                                    al17.add(json.getString("itemid"));
                                    al18.add(json.getString("remarks"));
                                    al19.add(json.getString("contactId"));
                                    al20.add(json.getString("PaytermsId"));
                                    al21.add(json.getString("itemName"));
                                  //  al21.add(json.getString("PackingQty"));
                                    //al22.add(json.getString("SubPacking"));
                                   // al23.add(json.getString("TotalQty"));
                                   // al24.add(json.getString("Quantity"));

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
                                adapter = new MyRecyclerViewAdapter(getContext(), al1, al2, al21, al4,al5,al6,al7,al8,"5");
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
                        params.put("formid", String.valueOf(1));
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
                                        try{
                                            //Setting the date to the given date
                                            c.setTime(sdf.parse(et_invoicedate.getText().toString()));
                                            Toast.makeText(getContext(), "Et_invoice date is"+et_invoicedate.getText().toString(), Toast.LENGTH_SHORT).show();
                                        }catch(ParseException e){
                                            e.printStackTrace();
                                        }
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
    @Override
    public void onItemClick(View view, int position) {

    }
}
