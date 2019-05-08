package com.example.xxovek.foodkor.SALES.customer;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.xxovek.foodkor.R;
import com.example.xxovek.foodkor.TAXES.ViewTaxesInformationFragment;
import com.example.xxovek.foodkor.URLs.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddNewCustomer extends Fragment {

    Spinner sp_registrationname,sp_countrybill,sp_statebill,sp_citybill,sp_countryship,sp_stateship,sp_cityship;
    EditText  et_firstname,et_middlename,et_lastname,et_emailid,et_phonenumber,et_gstin,et_pan,et_billadd,et_shipadd,et_companyname,et_bzip,et_szip;
    String  st_firstname,st_middlename,st_lastname,st_emailid,st_phonenumber,st_gstin,st_pan,st_billadd,st_shipadd,st_companyname,st_bzip,st_szip;
    Button btn_submit;
    HashMap<Integer,String> spinnerMap,spinnerMap3,spinnerMap4,spinnerMap6;
    String registration_id,billing_country_id,shipping_country_id,billing_state_id,shipping_state_id,billing_city_id,shipping_city_id,st_state_id,registration_name;
    StringRequest stringRequest3,stringRequest4,stringRequest5,stringRequest6,stringRequest7,stringRequest8;


    public AddNewCustomer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_new_customer, container, false);

        et_firstname=view.findViewById(R.id.firstnamee);
        et_middlename=view.findViewById(R.id.middlenamee);
        et_lastname=view.findViewById(R.id.lastnamee);
        et_emailid=view.findViewById(R.id.emaile);
        et_phonenumber=view.findViewById(R.id.phonee);
        et_gstin=view.findViewById(R.id.gstine);
        et_pan=view.findViewById(R.id.pane);
        et_billadd=view.findViewById(R.id.baddresse);
        et_shipadd=view.findViewById(R.id.saddresse);
        et_companyname=view.findViewById(R.id.et_companyname);
        et_bzip=view.findViewById(R.id.et_bzip);
        et_szip=view.findViewById(R.id.et_szip);
        sp_registrationname=view.findViewById(R.id.regidtrationnames);
        sp_countrybill=view.findViewById(R.id.countrys1);
        sp_statebill=view.findViewById(R.id.states1);
        sp_citybill=view.findViewById(R.id.citys1);
        sp_countryship=view.findViewById(R.id.countrys2);
        sp_stateship=view.findViewById(R.id.states2);
        sp_cityship=view.findViewById(R.id.citys2);
        btn_submit=view.findViewById(R.id.btn_submit);

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        final String company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        //Getting Person Type or registration type...

        //Getting Country Data

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                st_firstname = et_firstname.getText().toString().trim();
                st_middlename = et_middlename.getText().toString().trim();
                st_lastname = et_lastname.getText().toString().trim();
                st_emailid = et_emailid.getText().toString().trim();
                st_phonenumber = et_phonenumber.getText().toString().trim();
                st_gstin = et_gstin.getText().toString().trim();
                st_pan = et_pan.getText().toString().trim();
                st_billadd = et_billadd.getText().toString().trim();
                st_shipadd = et_shipadd.getText().toString().trim();
                st_companyname=et_companyname.getText().toString().trim();
                st_bzip=et_bzip.getText().toString().trim();
                st_szip=et_szip.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_CUSTOMER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Customer Information\n\n"+response, Toast.LENGTH_SHORT).show();
                                //converting response to json object
                                // JSONObject obj = new JSONObject(response);
                                // JSONArray obj = new JSONArray(response);
                                //getting the user from the response
                                //JSONObject userJson = obj.getJSONObject("tax_info");
                                // int len = obj.length();

                                Fragment fragment = new Customers();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_container, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("ctype", registration_name);
                        params.put("fname", st_firstname);
                        params.put("mname", st_middlename);
                        params.put("lname", st_lastname);
                        params.put("email", st_emailid);
                        params.put("phone", st_phonenumber);
                        params.put("gstin", st_gstin);
                        params.put("Pan",st_pan );
                        params.put("companyName", st_companyname);
                        params.put("billaddr",st_billadd);
                        params.put("bzip", st_bzip);
                        params.put("bcountry",billing_country_id);
                        params.put("bstate",billing_state_id );
                        params.put("bcity", billing_city_id);
                        params.put("shipaddr", st_shipadd);
                        params.put("szip", st_szip);
                        params.put("scountry",shipping_country_id);
                        params.put("sstate",shipping_state_id );
                        params.put("scity",shipping_city_id );
                        params.put("company_id",company_id);
                        params.put("ctype1",registration_id);


                        Log.d("mytag", "getParams: \n"+registration_id+"\n"+st_firstname+"\n"+st_middlename
                                +"\n"+st_lastname+"\n"+st_emailid+"\n"+st_phonenumber+"\n"+st_gstin+"\n"+st_pan+
                                    "\n"+st_companyname+"\n"+st_billadd+"\n"+st_bzip+"\n"+billing_country_id+"\n"
                                        +billing_state_id+"\n"+billing_city_id+"\n"+st_shipadd+"\n"+st_szip+"\n"
                                            +shipping_country_id+"\n"+shipping_state_id+"\n"+shipping_city_id);


                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

            }
        });

        //Getting Country Data.....

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Config.COUNTRY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Country"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("sortname"));
                                    al3.add(json.getString("name"));
                                    al4.add(json.getString("phonecode"));
//

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_countrybill.setAdapter(dataAdapter);

                                   /* String[] spinnerArray = new String[al1.size()];
                                    spinnerMap1 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap1.put(i, al1.get(i));
                                        spinnerArray[i] = al1.get(i);
                                    }*/
                                          /* int spinnerPosition = dataAdapter.getPosition(a10);
                                    sp_countrybill.setSelection(spinnerPosition);*/

                                   /* dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrys2.setAdapter(dataAdapter);
                                    int spinnerPosition1 = dataAdapter.getPosition(a13);
                                    countrys2.setSelection(spinnerPosition1);*/

                                    sp_countrybill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            billing_country_id  = sp_countrybill.getItemAtPosition(i).toString();
                                            if (!billing_country_id .isEmpty()) {
                                                /*billing_country_id = spinnerMap1.get(sp_countrybill.getSelectedItemPosition());
*/

                                                Toast.makeText(getContext(), "Country id\n" + billing_country_id, Toast.LENGTH_SHORT).show();

                                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                                requestQueue1.add(stringRequest3);

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });
                                }


                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                /*params.put("pid", cid);*/
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest1);

        //Getting Shipping Country Data
         stringRequest6 = new StringRequest(Request.Method.POST, Config.COUNTRY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Country"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("sortname"));
                                    al3.add(json.getString("name"));
                                    al4.add(json.getString("phonecode"));
//


                                    //For Shipping address Spinner
                                    ArrayAdapter<String> shipAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    shipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_countryship.setAdapter(shipAdapter);

                                    /*String[] spinnerShipArray = new String[al1.size()];
                                    spinnerMap2 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap2.put(i, al1.get(i));
                                        spinnerShipArray[i] = al1.get(i);
                                    }*/

                                    sp_countryship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            shipping_country_id = sp_countryship.getItemAtPosition(i).toString();
                                            if (!shipping_country_id.isEmpty()) {
                                               /* shipping_country_id = spinnerMap2.get(sp_countryship.getSelectedItemPosition());*/


                                                Toast.makeText(getContext(), "Shipping Country id\n" + shipping_country_id, Toast.LENGTH_SHORT).show();

                                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                                requestQueue1.add(stringRequest5);

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });



                                }


                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                /*params.put("pid", cid);*/
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(stringRequest6);




        //Getting State Data

        stringRequest3 = new StringRequest(Request.Method.POST, Config.STATES_URL,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server
                if(TextUtils.isEmpty(response)){
                    //Creating a shared preference
                    Toast.makeText(getContext(), "State"+response.toString(), Toast.LENGTH_LONG).show();

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
                            al1.add(json.getString("id"));
                            al2.add(json.getString("name"));
                            al3.add(json.getString("name"));
                            //al4.add(json.getString("phonecode"));
//

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item, al2);

                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_statebill.setAdapter(dataAdapter);
                            String[] spinnerStateArray = new String[al1.size()];
                            spinnerMap3 = new HashMap<Integer, String>();

                            for (i = 0; i < al1.size(); i++) {
                                spinnerMap3.put(i, al1.get(i));
                                spinnerStateArray[i] = al1.get(i);
                            }
                                          /* int spinnerPosition = dataAdapter.getPosition(a10);
                                    sp_countrybill.setSelection(spinnerPosition);*/

                                   /* dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrys2.setAdapter(dataAdapter);
                                    int spinnerPosition1 = dataAdapter.getPosition(a13);
                                    countrys2.setSelection(spinnerPosition1);*/

                            sp_statebill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                    billing_state_id = sp_statebill.getItemAtPosition(i).toString();
                                    if (!billing_state_id.isEmpty()) {
                                        st_state_id= spinnerMap3.get(sp_statebill.getSelectedItemPosition());
                                        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
                                        requestQueue2.add(stringRequest4);

                                        Toast.makeText(getContext(), "State Id\n" + billing_state_id, Toast.LENGTH_SHORT).show();

                                    }

                                }


                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }


                            });
                        }


                        // a= a + "Age : "+json.getString("c_phone")+"\n";
                        //j= j + "Job : "+json.getString("Job")+"\n
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("countryname", String.valueOf(sp_countrybill));
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };


        //Getting Shipping State Data
        stringRequest5 = new StringRequest(Request.Method.POST, Config.STATES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "State"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("name"));
                                    al3.add(json.getString("name"));
                                    //al4.add(json.getString("phonecode"));
//


                                    //For Shipping statte Spinner
                                    ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_stateship.setAdapter(stateAdapter);

                                    String[] spinnerstateArray1 = new String[al1.size()];
                                    spinnerMap4 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap4.put(i, al1.get(i));
                                        spinnerstateArray1[i] = al1.get(i);
                                    }

                                    sp_stateship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            shipping_state_id = sp_stateship.getItemAtPosition(i).toString();
                                            if (!shipping_state_id.isEmpty()) {
                                                st_state_id= spinnerMap4.get(sp_stateship.getSelectedItemPosition());


                                                Toast.makeText(getContext(), "Shipping State id\n" + shipping_state_id, Toast.LENGTH_SHORT).show();
                                                RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
                                                requestQueue3.add(stringRequest7);
                                                /*RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                                requestQueue1.add(stringRequest2);*/

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });



                                }


                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("countryname", String.valueOf(sp_countrybill));
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };



        //Getting City Data
                                stringRequest4 = new StringRequest(Request.Method.POST, Config.CITY_URL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                //If we are getting success from server
                                                if(TextUtils.isEmpty(response)){
                                                    //Creating a shared preference
                                                    Toast.makeText(getContext(), "City"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("name"));
                                    al3.add(json.getString("name"));
                                    //al4.add(json.getString("phonecode"));
//

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_citybill.setAdapter(dataAdapter);

                                    /*String[] spinnerCityArray = new String[al1.size()];
                                    spinnerMap5 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap5.put(i, al1.get(i));
                                        spinnerCityArray[i] = al1.get(i);
                                    }*/
                                          /* int spinnerPosition = dataAdapter.getPosition(a10);
                                    sp_countrybill.setSelection(spinnerPosition);*/

                                   /* dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrys2.setAdapter(dataAdapter);
                                    int spinnerPosition1 = dataAdapter.getPosition(a13);
                                    countrys2.setSelection(spinnerPosition1);*/

                                    sp_citybill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            billing_city_id = sp_citybill.getItemAtPosition(i).toString();
                                            if (!billing_city_id.isEmpty()) {
                                                /*billing_city_id = spinnerMap5.get(sp_citybill.getSelectedItemPosition());*/


                                                Toast.makeText(getContext(), "City Bill Id\n" + billing_city_id, Toast.LENGTH_SHORT).show();

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }

                                    });
                                }

                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("state_id", st_state_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }



        };


        //Getting Shipping City Data
        stringRequest7 = new StringRequest(Request.Method.POST, Config.CITY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "City"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("name"));
                                    al3.add(json.getString("name"));
                                    //al4.add(json.getString("phonecode"));
//



                                    //For Shipping City Spinner
                                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);

                                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_cityship.setAdapter(cityAdapter);
/*
                                    String[] spinnercityArray2 = new String[al1.size()];
                                    spinnerMap6 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap6.put(i, al1.get(i));
                                        spinnercityArray2[i] = al1.get(i);
                                    }*/

                                    sp_cityship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            shipping_city_id = sp_cityship.getItemAtPosition(i).toString();
                                            if (!shipping_city_id.isEmpty()) {
                                                //shipping_city_id = spinnerMap6.get(sp_cityship.getSelectedItemPosition());


                                                Toast.makeText(getContext(), "Shipping City id\n" + shipping_city_id, Toast.LENGTH_SHORT).show();



                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });



                                }


                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
            protected Map<String,   String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("state_id", st_state_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }

        };


        stringRequest8 = new StringRequest(Request.Method.POST, Config.PERSON_TYPE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Person type"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    al1.add(json.getString("personTypeId"));
                                    al2.add(json.getString("PersonType"));
                                    al3.add(json.getString("created_at"));
                                    al4.add(json.getString("updated_at"));
                                }
                                    Integer a1 = al1.size();
                                    String a2 = String.valueOf(a1);
                                    String[] spinnerregistrationArray = new String[al1.size()];
                                    spinnerMap6 = new HashMap<Integer, String>();

                                    for (int i = 0; i < al1.size(); i++) {
                                        spinnerMap6.put(i, al1.get(i));
                                        spinnerregistrationArray[i] = al1.get(i);
                                    }



                                    ArrayAdapter<String> shipAdapter= new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);

                                    shipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_registrationname.setAdapter(shipAdapter);



                                    sp_registrationname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            registration_name = sp_registrationname.getItemAtPosition(i).toString();


                                                registration_id=spinnerMap6.get(sp_registrationname.getSelectedItemPosition());

                                                Toast.makeText(getContext(), "registration id\n" + registration_id, Toast.LENGTH_SHORT).show();




                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });






                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                /*params.put("pid", cid);*/
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest8);



        return view;
    }

}
