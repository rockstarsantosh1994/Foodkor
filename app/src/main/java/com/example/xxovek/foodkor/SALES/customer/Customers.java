package com.example.xxovek.foodkor.SALES.customer;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.example.xxovek.foodkor.SALES.productandservice.ProductsAndServices;
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
public class Customers extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
    public int i;
    public MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;


    public Customers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        final String company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview2);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Add New Customer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Fragment fragment = new AddNewCustomer();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        //final String LOGIN_URL = "http://192.168.0.112/Foodcor/src/displayCustomers.php";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DISPLAY_CUSTOMERS_URL,
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
                                    al2.add("Name : ".concat(json.getString("name")));
                                    al3.add(json.getString("ptype"));
                                    al4.add("Email : ".concat(json.getString("email")));
                                    al5.add(json.getString("pid"));
                                    al6.add(json.getString("email"));
                                    al7.add(json.getString("email"));
                                    al8.add((json.getString("email")));



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
                                adapter = new MyRecyclerViewAdapter(getContext(), al1, al2, al3, al4,al5,al6,al7,al8,"1");
                                adapter.setClickListener(Customers.this);
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
                params.put("company_id", company_id);
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

        int id=view.getId();

        switch (id){
            case R.id.ib_delete:
                final String st_delid= adapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(android.R.drawable.ic_lock_power_off);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getContext(), "st_delid\n"+st_delid, Toast.LENGTH_SHORT).show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DELETE_CUSTOMER_URL1,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //If we are getting success from server
                                        if(TextUtils.isEmpty(response)){
                                            //Creating a shared preference
                                            Toast.makeText(getContext(), "Unable to delete product data"+response.toString(), Toast.LENGTH_LONG).show();

                                        }else{

                                            Toast.makeText(getContext(), "Customer Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            Fragment fragment = new Customers();
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.main_container, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            adapter.notifyDataSetChanged();
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

                                params.put("PersonId", st_delid);
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

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Delete Operation Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


                break;

            case R.id.t2:
                String user_id1 = adapter.getItem(position);
                int user_id2 = adapter.getItemCount();
                String user_id21 = String.valueOf(user_id2);

                Toast.makeText(getContext(), "getitem is  " + user_id1.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "count is  " + user_id21.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();


                // Intent intent = new Intent(getContext(), Clientsinfo.class);
                // startActivity(intent);
                Fragment fragment = new ViewCustomerPage();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("data", user_id1);//put string, int, etc in bundle with a key value
                fragment.setArguments(data);
                fragmentTransaction.replace(R.id.main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
        }



    }
}
