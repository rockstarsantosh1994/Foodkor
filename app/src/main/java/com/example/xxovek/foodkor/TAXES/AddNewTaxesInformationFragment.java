package com.example.xxovek.foodkor.TAXES;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class AddNewTaxesInformationFragment extends Fragment {

    EditText et_taxname,et_taxpercent,et_taxdescription;
    Button btn_cancel,btn_saveandclose;
    Spinner sp_taxtype;
    String st_taxtype;

    public AddNewTaxesInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_taxes_information, container, false);

         et_taxname=view.findViewById(R.id.taxnamee);
         sp_taxtype=view.findViewById(R.id.taxtypee);
         et_taxpercent=view.findViewById(R.id.taxpercente);
         et_taxdescription=view.findViewById(R.id.taxdescpe);
         btn_cancel=view.findViewById(R.id.btn_cancel);
         btn_saveandclose=view.findViewById(R.id.btn_save);


         getTaxType();

         btn_cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Fragment fragment = new ViewTaxesInformationFragment();
                 FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                 fragmentTransaction.replace(R.id.main_container, fragment);
                 fragmentTransaction.addToBackStack(null);
                 fragmentTransaction.commit();
             }
         });


        btn_saveandclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               // final String ADD_TAXES_URL = "http://192.168.0.109/Foodcor/src/addTaxesFormValues.php";

                final String st_taxname= et_taxname.getText().toString().trim();
                final String st_taxpercent = et_taxpercent.getText().toString().trim();
                final String st_taxdescription= et_taxdescription.getText().toString().trim();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_TAXES_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Add New Taxes Information.java "+response, Toast.LENGTH_SHORT).show();
                                    //converting response to json object
                                   // JSONObject obj = new JSONObject(response);
                                   // JSONArray obj = new JSONArray(response);
                                   //getting the user from the response
                                        //JSONObject userJson = obj.getJSONObject("tax_info");
                                       // int len = obj.length();

                                       Fragment fragment = new ViewTaxesInformationFragment();
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
                        params.put("TaxName", st_taxname);
                        params.put("TaxDescription", st_taxdescription);
                        params.put("TaxType", st_taxtype);
                        params.put("TaxPercent", st_taxpercent);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

            }
        });


        return view;
    }

    public void getTaxType(){
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("GST");
        categories.add("VAT");
        categories.add("Service Tax");
        categories.add("Swatch Bharat Cess");
        categories.add("Krishi Kalyan Cess");
        categories.add("CST");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_taxtype.setAdapter(dataAdapter);

        sp_taxtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                st_taxtype  = sp_taxtype.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), ""+st_taxtype, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });
    }
}
