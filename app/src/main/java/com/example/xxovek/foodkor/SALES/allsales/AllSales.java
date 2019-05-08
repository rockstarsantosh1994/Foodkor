package com.example.xxovek.foodkor.SALES.allsales;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllSales extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
    Spinner spinnertype;
    public MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;


    public AllSales() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_sales, container, false);

        spinnertype = (Spinner) view.findViewById(R.id.type);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview1);


        //final String LOGIN_URL = "http://192.168.0.112/FoodkorAndroid/AllSalesType.php";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ALL_SALES_TYPE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{



                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();


                            String result1 = response.replace("\"", "");
                            result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                            String str[] = result1.split(",");

                            List<String> al = new ArrayList<String>();

                            al = Arrays.asList(str);

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AllSales.this.getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item, al);

                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnertype.setAdapter(dataAdapter);
                            spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                    final String abc = spinnertype.getItemAtPosition(i).toString();
                                    if(!abc.isEmpty()) {
                                        Toast.makeText(getContext(), "222"+abc.toString(), Toast.LENGTH_SHORT).show();

                                        //final String LOGIN_URL = "http://192.168.0.112/FoodkorAndroid/displayAllSalesTblData.php";

                                        //Creating a string request
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DISPLAY_ALL_SALES_TBL_DATA_URL,
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
                                                                    al1.add(json.getString("name"));
                                                                    al2.add("Rs ".concat(json.getString("Total")));
                                                                    al3.add(abc.toString().concat(" #").concat(json.getString("InvoiceNumber")));
                                                                    al4.add("Due : ".concat(json.getString("DueDate")));
                                                                    al5.add(json.getString("status"));
                                                                    al6.add(json.getString("status"));
                                                                    al7.add(json.getString("status"));
                                                                    al8.add((json.getString("status")));



                                                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                                                }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();


                                                                String result1 = response.replace("\"", "");
                                                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                                                String str[] = result1.split(",");


                                                                //al = Arrays.asList(n);

                                                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                                                                recyclerView.setLayoutManager(mLayoutManager);

                                                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                                                adapter = new MyRecyclerViewAdapter(getContext(), al1, al2, al3, al4,al5,al6,al7,al8,"0");
                                                                adapter.setClickListener(AllSales.this);
                                                                recyclerView.setAdapter(adapter);
                                                                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                                                        DividerItemDecoration.VERTICAL));
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
                                                params.put("type", abc);
//                params.put("password", password);

                                                //returning parameter
                                                return params;
                                            }
                                        };

                                        //Adding the string request to the queue
                                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                        requestQueue.add(stringRequest);


                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



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
                params.put("username", "email");
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

    @Override
    public void onItemClick(View view, int position) {

    }
}
