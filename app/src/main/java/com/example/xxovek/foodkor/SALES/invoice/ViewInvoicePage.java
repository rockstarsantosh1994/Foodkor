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


/**
 * A simple {@link Fragment} subclass.
 */

public class ViewInvoicePage extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView rv_viewitem;
    private String st_transactionid;
    public MyRecyclerViewAdapter adapter;

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
        final String company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_invoice_page, container, false);
        rv_viewitem=view.findViewById(R.id.rv_viewitem);


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
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();--

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("itemDetailId"));
                                    al2.add(json.getString("qty"));
                                   // al3.add(json.getString("BillQty"));
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
                                adapter = new MyRecyclerViewAdapter(getContext(), al1, al2, al19, al4,al5,al6,al7,al8,"5");
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

    public void getProductNameFromId(){


    }
    @Override
    public void onItemClick(View view, int position) {

    }
}
