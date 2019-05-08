package com.example.xxovek.foodkor.SALES.productandservice;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import static android.R.layout.simple_spinner_dropdown_item;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewProductsServices extends Fragment {

    Spinner spin_units,spin_size,spin_packingqty,spin_itemcategory,spin_taxvalue,spin_suppliername;
    public String company_id;
    String st_unitnames,st_sizevalue,st_packingqty,st_itemcategory,st_taxvalue,st_suppliername,st_productname,st_sku,st_hsncode,st_initqtyinhnd,st_lowstockalert,st_salespricerates,st_packqty,st_subpackqty,st_desc;
    String[] spinnerArray,spinnerArray2,spinnerArray3,spinnerArray4,spinnerArray5;
    EditText et_productname,et_sku,et_hsncode,et_initqtyinhnd,et_lowstockalert,et_salespricerates,et_packqty,et_subpackqty,et_desc;
    Button btn_submitproduct;

    HashMap spinnerMap,spinnerMap2,spinnerMap3,spinnerMap4,spinnerMap5;

    public AddNewProductsServices() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_products_services, container, false);

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");
        spin_units=view.findViewById(R.id.units);
        spin_size=view.findViewById(R.id.sizevalues);
        spin_packingqty=view.findViewById(R.id.packingtypes);
        spin_itemcategory=view.findViewById(R.id.categorys);
        spin_taxvalue=view.findViewById(R.id.taxs);
        spin_suppliername=view.findViewById(R.id.prefferedsuppliers);

        et_productname=view.findViewById(R.id.productnamee);
        et_sku=view.findViewById(R.id.skue);
        et_hsncode=view.findViewById(R.id.hsncodee);
        et_initqtyinhnd=view.findViewById(R.id.initialquantityinhande);
        et_lowstockalert=view.findViewById(R.id.lowstockalerte);
        et_salespricerates=view.findViewById(R.id.salespricerate);
        et_packqty=view.findViewById(R.id.packingquantity);
        et_subpackqty=view.findViewById(R.id.subpackingquantitye);
        et_desc=view.findViewById(R.id.descriptione);
        btn_submitproduct=view.findViewById(R.id.btn_submitproduct);

        //fetching and storing units name in spinner...
        getSpinUnitsNames();

        //fetching and storing size value in spinner...
        getSpinSizeValues();

        //fetching and storing packingtypeqty in spinner...
        getSpinPackingQty();

        //fetching and storing itemcategory in spinner...
        getSpinItemCategory();

        //fetching and storing itemtaxvalue in spiiner...
        getSpinTaxValues();

        //fetching and storing suppliername in spinner
        getSpinPrefferedSupplier();


        btn_submitproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_productname=et_productname.getText().toString();
                st_sku=et_sku.getText().toString();
                st_hsncode=et_hsncode.getText().toString();
                st_initqtyinhnd=et_initqtyinhnd.getText().toString();
                st_lowstockalert=et_lowstockalert.getText().toString();
                st_productname=et_productname.getText().toString();
                st_salespricerates=et_salespricerates.getText().toString();
                st_packqty=et_packqty.getText().toString();
                st_subpackqty=et_subpackqty.getText().toString();
                st_desc=et_desc.getText().toString();


                final StringRequest stringRequest6 = new StringRequest(Request.Method.POST, Config.ADD_ITEMS_DETAILS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Item Information\n\n"+response, Toast.LENGTH_SHORT).show();
                                //converting response to json object
                                // JSONObject obj = new JSONObject(response);
                                // JSONArray obj = new JSONArray(response);
                                //getting the user from the response
                                //JSONObject userJson = obj.getJSONObject("tax_info");
                                // int len = obj.length();

                                Fragment fragment = new ProductsAndServices();
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
                        params.put("ItemName", st_productname);
                        params.put("ItemSKU", st_sku);
                        params.put("ItemHSN", st_hsncode);
                        params.put("ItemUnit", st_unitnames);
                        params.put("ItemSize", st_sizevalue);
                        params.put("PackingTypeId", st_packingqty);
                        params.put("ItemQuantity", st_initqtyinhnd);
                        params.put("ItemReorderLabel",st_lowstockalert );
                        params.put("ItemCategory", st_itemcategory);
                        params.put("ItemTax",st_taxvalue);
                        params.put("ItemPrice", st_salespricerates);
                        params.put("ItemSizeQty",st_packqty);
                        params.put("ItemSizeSubQty",st_subpackqty );
                        params.put("SupplierId", st_suppliername);
                        params.put("ItemDescription", st_desc);
                        params.put("company_id",company_id);

                        Log.d("mytag", "getParams: \n"+st_productname+"\n"+st_sku+"\n"
                        +st_hsncode+"\n"+st_unitnames+"\n"+st_sizevalue+"\n"+st_packingqty+
                        "\n"+st_initqtyinhnd+"\n"+st_lowstockalert+"\n"+st_itemcategory+"\n"
                        +st_taxvalue+"\n"+st_salespricerates+"\n"+st_packqty+"\n"+st_subpackqty+
                        "\n"+st_suppliername+"\n"+st_desc+"\n"+company_id );


                        return params;
                    }
                };

                RequestQueue requestQueue6 = Volley.newRequestQueue(getContext());
                requestQueue6.add(stringRequest6);

            }
        });





        return view;
    }

    public void saveAllData(){


    }
    public void getSpinUnitsNames(){
        spin_units.setAdapter(new ArrayAdapter<String>(getContext(), simple_spinner_dropdown_item,GetData.getUnitNames));

        spin_units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                st_unitnames = spin_units.getItemAtPosition(i).toString();
                if (!st_unitnames.isEmpty()) {
                                               /* shipping_country_id = spinnerMap2.get(sp_countryship.getSelectedItemPosition());*/


                    Toast.makeText(getContext(), "Unit name is \n" + st_unitnames, Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSpinSizeValues(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GET_SIZE_VALUES_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch size data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "getSizeValue Response \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


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
                                    al1.add(json.getString("SizeId"));
                                    al2.add(json.getString("SizeValue"));
                                    al3.add(json.getString("SizeValue"));
                                    al4.add(json.getString("SizeValue"));
                                    al5.add(json.getString("SizeValue"));
                                    al6.add(json.getString("SizeValue"));
                                    al7.add(json.getString("SizeValue"));
                                    al8.add(json.getString("SizeValue"));

                                }
                                    Integer a1 = al1.size();
                                    String a2 = String.valueOf(a1);
                                    spinnerArray = new String[al1.size()];
                                    spinnerMap = new HashMap<Integer, String>();

                                   // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                    for (int i = 0; i <al1.size(); i++)
                                    {
                                        spinnerMap.put(i,al1.get(i));
                                        spinnerArray[i] = al1.get(i);
                                    }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                    ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                    spin_size.setAdapter(dataAdapter);

                                    //String name = spin_size.getSelectedItem().toString();
                                    //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                    //String Text = sp_productservice.getSelectedItem().toString();
                                    //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                    // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                    //


                                    spin_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                            st_sizevalue = (String) spinnerMap.get(spin_size.getSelectedItemPosition());


                                            Toast.makeText(getContext(), "Size Value"+st_sizevalue, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);
    }

    public void getSpinPackingQty(){
        final StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Config.GET_PACKINGQTY_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch packing qty data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "getPackingQty Response \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


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
                                    al1.add(json.getString("PackingId"));
                                    al2.add(json.getString("PackingType"));

                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray2 = new String[al1.size()];
                                spinnerMap2 = new HashMap<Integer, String>();

                               // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    spinnerMap2.put(i,al1.get(i));
                                    spinnerArray2[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_packingqty.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                //


                                spin_packingqty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_packingqty= (String) spinnerMap2.get(spin_packingqty.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "Packing qty Value"+st_packingqty, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(stringRequest2);

    }

    public void getSpinItemCategory(){
        final StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Config.GET_ITEMCATEGORY_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch Item Category data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "getItemCategory Response \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


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
                                    al1.add(json.getString("CategoryId"));
                                    al2.add(json.getString("CategoryName"));

                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray3 = new String[al1.size()];
                                spinnerMap3 = new HashMap<Integer, String>();

                              //  Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    spinnerMap3.put(i,al1.get(i));
                                    spinnerArray3[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_itemcategory.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                //


                                spin_itemcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_itemcategory= (String) spinnerMap3.get(spin_itemcategory.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "Item Category Value\t"+st_itemcategory, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
        requestQueue3.add(stringRequest3);

    }

    public void getSpinTaxValues(){
        final StringRequest stringRequest4 = new StringRequest(Request.Method.POST, Config.GET_TAXVALUES_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch Tax value data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "getTaxValues Response \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


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
                                    al1.add(json.getString("TaxId"));
                                    al2.add(json.getString("tax"));

                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray4 = new String[al1.size()];
                                spinnerMap4 = new HashMap<Integer, String>();

                             //   Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    spinnerMap4.put(i,al1.get(i));
                                    spinnerArray4[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_taxvalue.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                //


                                spin_taxvalue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_taxvalue= (String) spinnerMap4.get(spin_taxvalue.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "Tax Value\t"+st_taxvalue, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue4 = Volley.newRequestQueue(getContext());
        requestQueue4.add(stringRequest4);
    }

    public void getSpinPrefferedSupplier(){
        final StringRequest stringRequest5 = new StringRequest(Request.Method.POST, Config.GET_SUPPLIERNAME_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch supplier name data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "getprefferedspinner Response \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


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
                                    al1.add(json.getString("PersonId"));
                                    al2.add(json.getString("FirstName"));


                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray5 = new String[al1.size()];
                                spinnerMap5 = new HashMap<Integer, String>();

                                Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    spinnerMap5.put(i,al1.get(i));
                                    spinnerArray5[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_suppliername.setAdapter(dataAdapter);

                                //String name = spin_size.getSelectedItem().toString();
                                //id1 = spinnerMap.get(sp_productservice.getSelectedItemPosition());

                                //String Text = sp_productservice.getSelectedItem().toString();
                                //String value = GetClassCode.getCode(Text);//here u have to pass the value that is selected on the spinner

                                // Toast.makeText(getContext(), "Value"+value, Toast.LENGTH_SHORT).show()
                                //


                                spin_suppliername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_suppliername = (String) spinnerMap5.get(spin_suppliername.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "supplier name"+st_suppliername, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue5 = Volley.newRequestQueue(getContext());
        requestQueue5.add(stringRequest5);
    }
}
