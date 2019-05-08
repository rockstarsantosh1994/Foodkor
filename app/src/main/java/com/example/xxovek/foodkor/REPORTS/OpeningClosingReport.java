package com.example.xxovek.foodkor.REPORTS;


import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OpeningClosingReport extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    private int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    EditText et_fromdate,et_todate;
    Button btn_submit;
    public MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;



    public OpeningClosingReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.opening_closing_fragment, container, false);
        et_fromdate=view.findViewById(R.id.et_fromdateopeningclosing);
        et_todate=view.findViewById(R.id.et_todate);
        btn_submit=view.findViewById(R.id.btn_submit);
        recyclerView=view.findViewById(R.id.recyclerview_openingclosing);

        et_todate.setOnClickListener(new View.OnClickListener() {
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

                                et_todate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        et_fromdate.setOnClickListener(new View.OnClickListener() {
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

                                et_fromdate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.OPENING_CLOSING_HISTORY_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                if(TextUtils.isEmpty(response)){
                                    //Creating a shared preference
                                    Toast.makeText(getContext(), "Unable to fetch opening closing data, please try again later"+response.toString(), Toast.LENGTH_LONG).show();

                                }else{

                                    try {

                                        Toast.makeText(getContext(), "Opening Closing Response\n\n"+response.toString(), Toast.LENGTH_SHORT).show();

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
                                            al1.add(json.getString("ProductName"));
                                            al2.add(json.getString("ProductName"));
                                            al3.add(json.getString("OpeningBal"));
                                            al4.add(json.getString("PurchaseBal"));
                                            al5.add(json.getString("Sale"));
                                            al6.add(json.getString("ClosingBal"));
                                            al7.add(json.getString("ClosingBal"));
                                            al8.add((json.getString("ClosingBal")));



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
                                        adapter = new MyRecyclerViewAdapter(getContext(), al1, al2, al3, al4,al5,al6,al7,al8,"5");
                                        adapter.setClickListener(OpeningClosingReport.this);
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
                        params.put("fromDate", String.valueOf(et_fromdate));
                        params.put("toDate", String.valueOf(et_todate));
//                params.put("password", password);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
