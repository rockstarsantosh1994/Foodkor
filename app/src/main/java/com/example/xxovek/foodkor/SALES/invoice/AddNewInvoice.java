package com.example.xxovek.foodkor.SALES.invoice;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.DynamicLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xxovek.foodkor.CardViewAdapter;
import com.example.xxovek.foodkor.DynamicViews;
import com.example.xxovek.foodkor.MainActivity;
import com.example.xxovek.foodkor.R;
import com.example.xxovek.foodkor.RecyclerAdapter2;

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
public class AddNewInvoice extends Fragment implements View.OnClickListener{
    public int i;
    public RecyclerAdapter2 adapter;
    RecyclerView recyclerView;
    Button button12;
    public CardView cardView3;
    DynamicViews dnv;
    Context context;
    RelativeLayout myRoot,relativeLayout;
    View view;
    TextView textview12;
    ViewGroup parent;
    GridLayout mlayout;
    private CardViewAdapter mAdapter;
    MainActivity main;


    public AddNewInvoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_invoice, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        button12 = (Button) view.findViewById(R.id.button12);
        cardView3 = (CardView) view.findViewById(R.id.card_view2);
        textview12 = (TextView) view.findViewById(R.id.textview12);
        mlayout = (GridLayout) view.findViewById(R.id.mylayout);

        button12.setOnClickListener(this);

        mAdapter = new CardViewAdapter();
        //fill the recyclerView with the cards
        recyclerView = (RecyclerView) view.findViewById(R.id.card_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);


        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {

            case R.id.button12:


                break;

        }

    }
}
