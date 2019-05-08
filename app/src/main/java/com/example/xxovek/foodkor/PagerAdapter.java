package com.example.xxovek.foodkor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.xxovek.foodkor.SALES.allsales.AllSales;
import com.example.xxovek.foodkor.SALES.customer.Customers;
import com.example.xxovek.foodkor.SALES.invoice.Invoices;
import com.example.xxovek.foodkor.SALES.productandservice.ProductsAndServices;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllSales tab1 = new AllSales();
                return tab1;
            case 1:
                Invoices tab2 = new Invoices();
                return tab2;
            case 2:
                Customers tab3 = new Customers();
                return tab3;
            case 3:
                ProductsAndServices tab4 = new ProductsAndServices();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}