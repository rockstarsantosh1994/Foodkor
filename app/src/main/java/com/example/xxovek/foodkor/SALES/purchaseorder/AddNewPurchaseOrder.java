package com.example.xxovek.foodkor.SALES.purchaseorder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxovek.foodkor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewPurchaseOrder extends Fragment {


    public AddNewPurchaseOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_purchase_order, container, false);
    }

}
