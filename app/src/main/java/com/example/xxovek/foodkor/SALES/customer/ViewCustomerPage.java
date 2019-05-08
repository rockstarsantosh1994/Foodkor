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
import com.example.xxovek.foodkor.URLs.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCustomerPage extends Fragment implements View.OnClickListener{
    Spinner sp_registrationname,countrys1,countrys2,states1,states2,citys1,citys2;
    EditText firstnamee,middlenamee,lastnamee,emaile,phonee,gstine,pane,baddresse,saddresse,et_companyname,et_billzip,et_shipzip;
    Button updateb;
    String a1,a2,a3,a4,a5,a6,a7,a8,a9,  a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21;
    StringRequest stringRequest2,stringRequest3,stringRequest4,stringRequest8;
    public String abc,xyz,st_state_id,st_states2,registration_name,registration_id;
    String cid;
    String  st_firstname,st_middlename,st_lastname,st_emailid,st_phonenumber,st_gstin,st_pan,st_billadd,st_shipadd,st_companyname,st_bzip,st_szip;
    HashMap<Integer,String> spinnerMap4,spinnerMap6;
    String ctype,ctype1,company_id,pid;


    public ViewCustomerPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");


       cid = getArguments().getString("data");
       Toast.makeText(getActivity(),"UserId"+cid.toString(),Toast.LENGTH_LONG).show();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_customer_page, container, false);
        sp_registrationname = (Spinner) view.findViewById(R.id.regidtrationnames);
        countrys1 = (Spinner) view.findViewById(R.id.countrys1);
        countrys2 = (Spinner) view.findViewById(R.id.countrys2);
        states1 = (Spinner) view.findViewById(R.id.states1);
        states2 = (Spinner) view.findViewById(R.id.states2);
        citys1 = (Spinner) view.findViewById(R.id.citys1);
        citys2 = (Spinner) view.findViewById(R.id.citys2);



        firstnamee = (EditText) view.findViewById(R.id.firstnamee);
        middlenamee = (EditText) view.findViewById(R.id.middlenamee);
        lastnamee = (EditText) view.findViewById(R.id.lastnamee);
        emaile = (EditText) view.findViewById(R.id.emaile);
        phonee = (EditText) view.findViewById(R.id.phonee);
        gstine = (EditText) view.findViewById(R.id.gstine);
        pane = (EditText) view.findViewById(R.id.pane);
        baddresse = (EditText) view.findViewById(R.id.baddresse);
        saddresse = (EditText) view.findViewById(R.id.saddresse);
        et_companyname = (EditText) view.findViewById(R.id.et_companyname);
        et_billzip = (EditText) view.findViewById(R.id.et_billzip);
        et_shipzip = (EditText) view.findViewById(R.id.et_shipzip);

        updateb = (Button) view.findViewById(R.id.updateb);

        updateb.setOnClickListener(this);


      /*  final String LOGIN_URL = "http://192.168.0.112/Foodcor/src/fetchCustomer.php";
        final String COUNTRY_URL = "http://192.168.0.112/FoodkorAndroid/countries.php";
        final String STATES_URL = "http://192.168.0.112/FoodkorAndroid/states.php";
        final String CITY_URL = "http://192.168.0.112/FoodkorAndroid/city.php";*/



        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_CUSTOMER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Error in Fetching Data of customer"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "Customer data\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


                                JSONObject json = new JSONObject(response);
                                   a1 = (json.getString("fname"));
                                   a2 = (json.getString("mname"));
                                   a3 = (json.getString("lname"));
                                   a4 = (json.getString("email"));
                                   a5 = (json.getString("phone"));
                                   a6 = (json.getString("gstin"));
                                   a7 = (json.getString("Pan"));
                                   a8 = (json.getString("billaddress"));
                                   a9 = (json.getString("shipaddress"));
                                   a10 = (json.getString("bcountry"));
                                   a11 = (json.getString("bstate"));
                                   a12 = (json.getString("bcity"));
                                   a13 = (json.getString("scountry"));
                                   a14 = (json.getString("sstate"));
                                   a15 = (json.getString("scity"));
                                   a16=  (json.getString("ptype"));
                                   a17 = (json.getString("bzip"));
                                   a18 = (json.getString("szip"));
                                   a19 = (json.getString("CompanyName"));
                                   a20=  (json.getString("personTypeId"));
                                   a21=(json.getString("pid"));

                                Toast.makeText(getContext(), "a20\n"+a20, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), "Customer data1234567ase\n"+a17.toString(), Toast.LENGTH_SHORT).show();


                                firstnamee.setText(a1.toString());
                                middlenamee.setText(a2.toString());
                                lastnamee.setText(a3.toString());
                                emaile.setText(a4.toString());
                                phonee.setText(a5.toString());
                                gstine.setText(a6.toString());
                                pane.setText(a7.toString());
                                baddresse.setText(a8.toString());
                                saddresse.setText(a9.toString());
                                et_companyname.setText(a19.toString());
                                et_billzip.setText(a17.toString());
                                et_shipzip.setText(a18.toString());
                                ctype=a16.toString().trim();
                                ctype1=a20.toString().trim();
                                pid=a21.toString().trim();

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
                params.put("pid", cid);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Config.COUNTRY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

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

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewCustomerPage.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrys1.setAdapter(dataAdapter);
                                    int spinnerPosition = dataAdapter.getPosition(a10);
                                    countrys1.setSelection(spinnerPosition);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrys2.setAdapter(dataAdapter);
                                                                       int spinnerPosition1 = dataAdapter.getPosition(a13);
                                    countrys2.setSelection(spinnerPosition1);


                                    countrys1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            abc = countrys1.getItemAtPosition(i).toString();
                                            if(!abc.isEmpty()) {

                                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                                requestQueue1.add(stringRequest2);

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });





                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n";
                                }
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
                params.put("pid", cid);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        stringRequest2 = new StringRequest(Request.Method.POST, Config.STATES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    al4.add(json.getString("name"));
//

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewCustomerPage.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    String[] spinnerstateArray1 = new String[al1.size()];
                                    spinnerMap4 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap4.put(i, al1.get(i));
                                        spinnerstateArray1[i] = al1.get(i);
                                    }

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    states1.setAdapter(dataAdapter);
                                    int spinnerPosition = dataAdapter.getPosition(a11);
                                    states1.setSelection(spinnerPosition);

                                    states2.setAdapter(dataAdapter);
                                    int spinnerPosition1 = dataAdapter.getPosition(a14);
                                    states2.setSelection(spinnerPosition1);


                                    states1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            xyz = states1.getItemAtPosition(i).toString();
                                            Log.d("mytag", "onItemSelected: "+xyz);
                                            if(!xyz.isEmpty()) {
                                                st_state_id= spinnerMap4.get(states1.getSelectedItemPosition());

                                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                                requestQueue1.add(stringRequest3);

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });

                                    states2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            st_states2 = states2.getItemAtPosition(i).toString();
                                            Log.d("mytag", "onItemSelected: "+xyz);
                                            if(!st_states2.isEmpty()) {
                                                Log.d("mytag", "onItemSelected: On States 2 Selected"+st_states2);

                                                RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
                                                requestQueue1.add(stringRequest4);

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });





                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
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
                params.put("countryname", abc);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        stringRequest3 = new StringRequest(Request.Method.POST, Config.CITY_URL_FOR_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch city url"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("mytag", "onResponse: "+response.toString());
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
                                    al4.add(json.getString("name"));
//

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewCustomerPage.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    citys1.setAdapter(dataAdapter);
                                    int spinnerPosition = dataAdapter.getPosition(a12);
                                    citys1.setSelection(spinnerPosition);



                                    citys1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                             String xyz = citys1.getItemAtPosition(i).toString();
                                            if(!xyz.isEmpty()) {

                                                Toast.makeText(getActivity(), citys1.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

                                            }

                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });







                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
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
              //  params.put("countryname", xyz);

                    params.put("countryname",xyz);




//                params.put("password", password);

                //returning parameter
                return params;
            }
        };


        //Adding the string request to the queue
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest1);
        requestQueue1.add(stringRequest3);


        //Getting Shipping City Data using states2

        stringRequest4 = new StringRequest(Request.Method.POST, Config.CITY_URL_FOR_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch city url"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try{

                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("mytag", "onResponse: "+response.toString());
                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();
                                List<String> al3 = new ArrayList<String>();
                                List<String> al4 = new ArrayList<String>();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("name"));
                                    al3.add(json.getString("name"));
                                    al4.add(json.getString("name"));
//

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewCustomerPage.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    citys2.setAdapter(dataAdapter);
                                    int spinnerPosition1 = dataAdapter.getPosition(a15);
                                    citys2.setSelection(spinnerPosition1);

                                    citys2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            String xyz = citys2.getItemAtPosition(i).toString();
                                            if(!xyz.isEmpty()) {

                                                Toast.makeText(getActivity(), citys2.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }


                                    });





                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
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
                //  params.put("countryname", xyz);

                params.put("countryname",st_states2);


//                params.put("password", password);

                //returning parameter
                return params;
            }
        };


        //Adding the string request to the queue
        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(stringRequest4);


        //getting registration of customer


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

                                int spinnerPosition1 = shipAdapter.getPosition(a16);
                                sp_registrationname.setSelection(spinnerPosition1);

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
        RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
        requestQueue3.add(stringRequest8);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.updateb:

                st_firstname = firstnamee.getText().toString().trim();
                st_middlename = middlenamee.getText().toString().trim();
                st_lastname = lastnamee.getText().toString().trim();
                st_emailid = emaile.getText().toString().trim();
                st_phonenumber = phonee.getText().toString().trim();
                st_gstin = gstine.getText().toString().trim();
                st_pan = pane.getText().toString().trim();
                st_billadd = baddresse.getText().toString().trim();
                st_shipadd = saddresse.getText().toString().trim();
                st_companyname=et_companyname.getText().toString().trim();
                st_bzip=et_billzip.getText().toString().trim();
                st_szip=et_shipzip.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_CUSTOMER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Customer Information Updated Successfully\n\n"+response, Toast.LENGTH_SHORT).show();
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
                        params.put("pid",pid);
                        params.put("ctype", ctype);
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
                        params.put("bcountry",countrys1.getSelectedItem().toString());
                        params.put("bstate",states1.getSelectedItem().toString());
                        params.put("bcity", citys1.getSelectedItem().toString());
                        params.put("shipaddr", st_shipadd);
                        params.put("szip", st_szip);
                        params.put("scountry",countrys2.getSelectedItem().toString());
                        params.put("sstate", states2.getSelectedItem().toString());
                        params.put("scity",citys2.getSelectedItem().toString());
                        params.put("company_id",company_id);
                        params.put("ctype1",ctype1);


                        Log.d("mytag", "getParams: \n"+registration_id+"\n"+st_firstname+"\n"+st_middlename
                                +"\n"+st_lastname+"\n"+st_emailid+"\n"+st_phonenumber+"\n"+st_gstin+"\n"+st_pan+
                                "\n"+st_companyname+"\n"+st_billadd+"\n"+st_bzip+"\n"+countrys1.getSelectedItem().toString()+"\n"
                                +states1.getSelectedItem().toString()+"\n"+citys1.getSelectedItem().toString()+"\n"+st_shipadd+"\n"+st_szip+"\n"
                                +countrys2.getSelectedItem().toString()+"\n"+states2.getSelectedItem().toString()+"\n"+citys2.getSelectedItem().toString());


                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);


                break;
        }

    }
}
