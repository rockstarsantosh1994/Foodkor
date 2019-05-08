package com.example.xxovek.foodkor;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.xxovek.foodkor.ACCOUNTREPORT.FetchAccountingFragment;
import com.example.xxovek.foodkor.DASHBOARD.Dashboard;
import com.example.xxovek.foodkor.REPORTS.OpeningClosingReport;
import com.example.xxovek.foodkor.SALES.customer.Customers;
import com.example.xxovek.foodkor.SALES.invoice.Invoices;
import com.example.xxovek.foodkor.SALES.purchaseorder.FragmentTabPurchaseOrder;
import com.example.xxovek.foodkor.SALES.productandservice.ProductsAndServices;
import com.example.xxovek.foodkor.SALES.purchase.FragmentTabPurchase;
import com.example.xxovek.foodkor.SCHEMES.ViewDataSchemesFragment;
import com.example.xxovek.foodkor.TAXES.ViewTaxesInformationFragment;


public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar,toolbar1;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    public static final String mypreference = "prf";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);





        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        String person_id=prf.getString("person_id", "");
        String company_id=prf.getString("company_id","");
        String company_flag=prf.getString("company_flag","");
        String isAdmin=prf.getString("isAdmin","");

        ImageButton signout = (ImageButton) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {

                                           SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
                                           SharedPreferences.Editor editor = prf.edit();
                                           editor.clear();
                                           editor.commit();
                                           finish();

                                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                           startActivity(intent);

                                           Toast.makeText(getApplicationContext(),
                                                   "LOGGED OUT",
                                                   Toast.LENGTH_LONG).show();
                                       }
                                   });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new Dashboard());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("DASHBOARD");
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int newfrag = item.getItemId();
                switch(newfrag)
                {

                    case R.id.dashboard_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Dashboard());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("DASHBOARD");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;

                    /*case R.id.all_sales_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new AllSales());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("ALL SALES");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;*/

                    case R.id.invoice_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Invoices());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("INVOICES");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;

                    case R.id.purchase_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new FragmentTabPurchase());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("PURCHASE");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);

                        break;

                    case R.id.purchase_order_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new FragmentTabPurchaseOrder());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("PURCHASE ORDER");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;

                    case R.id.customer_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Customers());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("CUSTOMERS");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;

                    case R.id.account_chart_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new FetchAccountingFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("ACCOUNT REPORT");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;

                    case R.id.product_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new ProductsAndServices());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("PRODUCT AND SERVICES");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;

                    case R.id.taxes_information_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new ViewTaxesInformationFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("TAXES");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;

                    case R.id.schemes_information_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new ViewDataSchemesFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("SCHEMES INFORMATION");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;


                    case R.id.stock_report_id :
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new OpeningClosingReport());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("OPENING CLOSING REPORT");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;



                }


                return false;
            }
        });


    }


    @Override
    protected void onPostCreate(Bundle savedInstance){
        super.onPostCreate(savedInstance);
        actionBarDrawerToggle.syncState();
        }
}