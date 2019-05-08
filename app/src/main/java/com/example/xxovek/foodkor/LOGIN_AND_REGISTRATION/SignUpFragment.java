package com.example.xxovek.foodkor.LOGIN_AND_REGISTRATION;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.xxovek.foodkor.LOGIN_AND_REGISTRATION.MainActivity;
import com.example.xxovek.foodkor.R;


public class SignUpFragment extends Fragment {

    TextView tv_signin;
   AwesomeValidation awesomeValidation;
   EditText et_firstname,et_middlename,et_lastname,et_emailadd,et_password,et_companname,et_contactno;
   Button btn_register;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        tv_signin=view.findViewById(R.id.tv_signin);
        et_firstname=view.findViewById(R.id.et_firstname);
        et_middlename=view.findViewById(R.id.et_middlename);
        et_lastname=view.findViewById(R.id.et_lastname);
        et_emailadd=view.findViewById(R.id.et_emailaddress);
        et_password=view.findViewById(R.id.et_password);
        et_companname=view.findViewById(R.id.et_companyname);
        et_contactno=view.findViewById(R.id.et_contactnumber);
        btn_register=view.findViewById(R.id.btn_register);

/*
        awesomeValidation.addValidation(getActivity(),R.id.et_firstname,"[a-zA-Z]+\\\\.?",R.string.name_error);
        awesomeValidation.addValidation(getActivity(),R.id.et_middlename,"[a-zA-Z]+\\\\.?",R.string.name_error);
        awesomeValidation.addValidation(getActivity(),R.id.et_lastname,"[a-zA-Z]+\\\\.?",R.string.name_error);
        awesomeValidation.addValidation(getActivity(),R.id.et_emailaddress,Patterns.EMAIL_ADDRESS,R.string.email_error);
        awesomeValidation.addValidation(getActivity(),R.id.et_companyname,"[a-zA-Z]+\\\\.?",R.string.name_error);
        awesomeValidation.addValidation(getActivity(),R.id.et_contactnumber,"^[2-9]{2}[0-9]{8}$",R.string.contact_error);*/


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
               /* if(awesomeValidation.validate()){
                    Toast.makeText(getContext(), "All fields validation done properly", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        return view;
    }
}
