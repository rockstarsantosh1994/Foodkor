package com.example.xxovek.foodkor.SALES.purchase;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Purchase extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    RecyclerView rv_purchase;
    public int i;
    public MyRecyclerViewAdapter adapter;

    public Purchase() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_purchase2, container, false);
        rv_purchase=view.findViewById(R.id.rv_fragmentpurchase);

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        final String company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DISPLAY_PURCHASE_FOR_CUSTOMER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.length()==0 || response.toString().equals("{}") || response.toString().equals("[]")){
                            //Creating a shared preference
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setIcon(R.drawable.ic_sad);
                            builder.setTitle("\n\nAlert Message");
                            builder.setMessage("\nSorry, No data available");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int id) {

                                }
                            });

                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();

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
                                List<String> al10 = new ArrayList<String>();


                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("InvoiceNumber"));
                                    al2.add(json.getString("TId"));
                                    al3.add("Name : ".concat(json.getString("name")));
                                    al4.add(json.getString("DateCreated"));
                                    al5.add("Due Date : ".concat(json.getString("DueDate")));
                                    al6.add(json.getString("Balance"));
                                    al7.add(json.getString("Total"));
                                    al8.add((json.getString("status")));
                                    al9.add((json.getString("companyId")));
                                    al10.add((json.getString("emailId")));



                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);

                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                                rv_purchase.setLayoutManager(mLayoutManager);

                                rv_purchase.setItemAnimator(new DefaultItemAnimator());
                                adapter = new MyRecyclerViewAdapter(getContext(), al1, al2, al3, al4,al5,al6,al7,al8,"3");
                                adapter.setClickListener(Purchase.this);
                                rv_purchase.setAdapter(adapter);
                                rv_purchase.addItemDecoration(new DividerItemDecoration(getContext(),
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
                params.put("company_id", company_id);
                params.put("Ttype", String.valueOf(1));
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
