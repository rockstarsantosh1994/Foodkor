package com.example.xxovek.foodkor.SCHEMES;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UpdateSchemesFragment extends Fragment {

    EditText et_schemename,et_from,et_upto,et_onpurchase,et_freeqty;
    Spinner sp_productservice;
    Button btn_cancel,btn_save;
    HashMap<Integer,String> spinnerMap;
    private int mYear, mMonth, mDay;
    String id1,company_id;
    String cid;
    String a1,a2,a3,a4,a5,a6,a7;

    public UpdateSchemesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_update_schemes, container, false);
        et_schemename=view.findViewById(R.id.et_schemename);
        et_from=view.findViewById(R.id.et_fromdate);
        et_upto=view.findViewById(R.id.et_uptodate);
        et_onpurchase=view.findViewById(R.id.et_onpurchase);
        et_freeqty=view.findViewById(R.id.et_freeqty);
        btn_cancel=view.findViewById(R.id.btn_cancel);
        btn_save=view.findViewById(R.id.btn_save);
        sp_productservice=view.findViewById(R.id.sp_productname);

        cid = getArguments().getString("data");
        Toast.makeText(getActivity(),"UserId"+cid.toString(),Toast.LENGTH_LONG).show();

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");



        //Fetching all scheme data
        getParticularScheme();


        et_from.setOnClickListener(new View.OnClickListener() {
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

                                et_from.setText((year) + "/" + (monthOfYear + 1)  + "/" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        et_upto.setOnClickListener(new View.OnClickListener() {
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

                                et_upto.setText((year) + "/" + (monthOfYear + 1)  + "/" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewDataSchemesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String st_schemename= et_schemename.getText().toString().trim();
                final String st_from= et_from.getText().toString().trim();
                final String st_upto= et_upto.getText().toString().trim();
                final String st_onpurchase= et_upto.getText().toString().trim();
                final String st_freeqty= et_freeqty.getText().toString().trim();



                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_SCHEMES,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Add New Schemes Information.java "+response, Toast.LENGTH_SHORT).show();

                                Fragment fragment = new ViewDataSchemesFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_container, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                Toast.makeText(getContext(), a1.toString()+"\tScheme Updated Successfully ", Toast.LENGTH_SHORT).show();
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
                        params.put("scheme", st_schemename);
                        params.put("item", id1);
                        params.put("from", st_from);
                        params.put("upto", st_upto);
                        params.put("onpurchase", st_onpurchase);
                        params.put("freeqty", st_freeqty);
                        params.put("sId", cid);
                        return params;
                    }
                };





                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);


            }
        });


        return view;

    }



    //Fetching All Scheme's Details of particular scheme

    public void getParticularScheme(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_SCHEME_FOR_UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Error in Fetching Data of Schemes"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {



                                Toast.makeText(getContext(), "Schemes data\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


                                JSONObject json = new JSONObject(response);
                                a1 = (json.getString("schemeType"));
                                a2 = (json.getString("FromDate"));
                                a3 = (json.getString("UptoDate"));
                                a4 = (json.getString("ItemDetailId"));
                                a5 = (json.getString("OnPurchase"));
                                a6 = (json.getString("freeQty"));
                                a7 = (json.getString("itemName"));

                                Toast.makeText(getContext(), "a4\n"+a4, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), "a7\n"+a7, Toast.LENGTH_SHORT).show();

                                //Storing product details in spinner
                                getSpinProductNameService();

                                et_schemename.setText(a1.toString());
                                et_from.setText(a2.toString());
                                et_upto.setText(a3.toString());
                                et_onpurchase.setText(a5.toString());
                                et_freeqty.setText(a6.toString());

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
                params.put("SchemeId", cid);
                params.put("company_id",company_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    //Loading Product Name on Spinner
    public void getSpinProductNameService(){
        final StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Config.FETCH_PRODUCT_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Something went wrong"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "Product service/Name \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


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
                                    al1.add(json.getString("ItemId"));
                                    al2.add(json.getString("ItemName"));
                                    al3.add(json.getString("ItemName"));
                                    al4.add(json.getString("ItemName"));
                                    al5.add(json.getString("ItemName"));
                                    al6.add(json.getString("ItemName"));
                                    al7.add(json.getString("ItemName"));
                                    al8.add(json.getString("ItemName"));
                                }

                                   String[] spinnerArray = new String[al1.size()];
                                   spinnerMap = new HashMap<Integer, String>();


                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                    ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    sp_productservice.setAdapter(dataAdapter);

                                    for (int i = 0; i <al1.size(); i++)
                                    {
                                        spinnerMap.put(i,al1.get(i));
                                        spinnerArray[i] = al1.get(i);
                                    }

                                   // String name = sp_productservice.getSelectedItem().toString();
                                    //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                    //String Text = sp_productservice.getSelectedItem().toString();
                                    //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                    // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                    //
                                    int spinnerPosition1 = dataAdapter.getPosition(a7);
                                    sp_productservice.setSelection(spinnerPosition1);


                                    sp_productservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                            id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());


                                            Toast.makeText(getContext(), "Value"+id1, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

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
                //params.put("ItemId", "ItemId");
                //params.put("ItemName", "ItemName");
                params.put("company_id",company_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest1);
    }






}
