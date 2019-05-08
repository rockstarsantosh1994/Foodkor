package com.example.xxovek.foodkor.SALES.invoice;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xxovek.foodkor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewInvoicePage extends Fragment {


    private String cid;

    public ViewInvoicePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cid = getArguments().getString("data");
        Toast.makeText(getActivity(),cid.toString(),Toast.LENGTH_LONG).show();

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String person_id=prf.getString("person_id", "");
        final String company_id=prf.getString("company_id","");
        final String company_flag=prf.getString("company_flag","");
        final String isAdmin=prf.getString("isAdmin","");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_invoice_page, container, false);

       




        return view;
    }

}
