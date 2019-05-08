package com.example.xxovek.foodkor.TAXES;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UpdateTaxInformation extends Fragment {

    EditText et_taxname,et_taxpercent,et_taxdescription;
    Button btn_cancel,btn_saveandclose;
    Spinner sp_taxtype;
    String st_taxtype,cid;
    String a1,a2,a3,a4,company_id;

    public UpdateTaxInformation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_update_tax_information, container, false);

        et_taxname=view.findViewById(R.id.taxnamee);
        sp_taxtype=view.findViewById(R.id.taxtypee);
        et_taxpercent=view.findViewById(R.id.taxpercente);
        et_taxdescription=view.findViewById(R.id.taxdescpe);
        btn_cancel=view.findViewById(R.id.btn_cancel);
        btn_saveandclose=view.findViewById(R.id.btn_save);


        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        cid = getArguments().getString("data");
        Toast.makeText(getActivity(),"UserId"+cid.toString(),Toast.LENGTH_LONG).show();


        //getting tax particular tax data..
        getParticularTaxData();

        //getting tax type..
        //getTaxType();


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
                        params.put("TaxId", cid);
                        params.put("TaxName", st_taxname);
                        params.put("TaxDescription", st_taxdescription);
                        params.put("TaxType", sp_taxtype.getSelectedItem().toString());
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


    public void getParticularTaxData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_TAX_VALUE_FROM_TAXID1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Error in Fetching Data of tax"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {



                                Toast.makeText(getContext(), "Taxes data\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


                                JSONObject json = new JSONObject(response);
                                a1 = (json.getString("TaxName"));
                                a2 = (json.getString("TaxType"));
                                a3 = (json.getString("TaxPercent"));
                                a4 = (json.getString("TaxDescription"));

                                Toast.makeText(getContext(), "a2\n"+a2, Toast.LENGTH_SHORT).show();

                                //getting tax type
                                getTaxType();

                                et_taxname.setText(a1.toString());
                                et_taxpercent.setText(a3.toString());
                                et_taxdescription.setText(a4.toString());

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
                params.put("taxid", cid);
                params.put("company_id",company_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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

        int spinnerPosition1 = dataAdapter.getPosition(a2);
        sp_taxtype.setSelection(spinnerPosition1);

        Toast.makeText(getContext(), "Spinner Position\t"+spinnerPosition1, Toast.LENGTH_SHORT).show();


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
