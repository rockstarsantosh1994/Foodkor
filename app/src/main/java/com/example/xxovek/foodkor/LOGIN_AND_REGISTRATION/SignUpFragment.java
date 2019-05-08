package com.example.xxovek.foodkor.LOGIN_AND_REGISTRATION;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.xxovek.foodkor.LOGIN_AND_REGISTRATION.MainActivity;
import com.example.xxovek.foodkor.R;
import com.example.xxovek.foodkor.SALES.customer.Customers;
import com.example.xxovek.foodkor.URLs.Config;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {

    TextView tv_signin;

   EditText et_firstname,et_middlename,et_lastname,et_emailadd,et_password,et_companname,et_contactno;
   String st_firstname,st_middlename,st_lastname,st_emailadd,st_password,st_companname,st_contactno;
   Button btn_register;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        tv_signin=view.findViewById(R.id.tv_signin);
        et_firstname=view.findViewById(R.id.et_firstname);
        et_middlename=view.findViewById(R.id.et_middlename);
        et_lastname=view.findViewById(R.id.et_lastname);
        et_emailadd=view.findViewById(R.id.et_emailaddress);
        et_password=view.findViewById(R.id.et_password);
        et_companname=view.findViewById(R.id.et_companyname);
        et_contactno=view.findViewById(R.id.et_contactnumber);
        btn_register=view.findViewById(R.id.btn_register);



        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_firstname=et_firstname.getText().toString();
                st_middlename=et_middlename.getText().toString();
                st_lastname=et_lastname.getText().toString();
                st_emailadd=et_emailadd.getText().toString();
                st_password=et_password.getText().toString();
                st_companname=et_companname.getText().toString();
                st_contactno=et_contactno.getText().toString();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.USER_REGISTRATION_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(getContext(), "\n\n"+response, Toast.LENGTH_SHORT).show();

                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                                builder.setIcon(R.drawable.ic_like);
                                builder.setTitle("\n\nMessage");
                                builder.setMessage("\nRegistration Successful Mr."+st_firstname+".");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {
                                        Intent intent=new Intent(getContext(),MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                android.app.AlertDialog dialog = builder.create();
                                dialog.show();
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
                        params.put("firstName", st_firstname);
                        params.put("lastName", st_lastname);
                        params.put("mname", st_middlename);
                        params.put("email", st_emailadd);
                        params.put("pwd", st_password);
                        params.put("companyName", st_companname);
                        params.put("contactNumber", st_contactno);

                        Log.d("mytag", "getParams: "+st_firstname+"\n"+st_lastname+"\n"+st_middlename+"\n"+st_emailadd+"\n"
                                +st_password+"\n"+st_companname+"\n"+st_contactno);

                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);




            }
        });
        return view;
    }
}
