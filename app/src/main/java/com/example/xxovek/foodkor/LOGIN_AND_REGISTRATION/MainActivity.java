package com.example.xxovek.foodkor.LOGIN_AND_REGISTRATION;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
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
import com.example.xxovek.foodkor.HomeActivity;
import com.example.xxovek.foodkor.R;
import com.example.xxovek.foodkor.URLs.Config;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prf;
    SharedPreferences.Editor editor;
    Button loginb_main;
    EditText usernamee_main, passworde_main;
    AlertDialog alertDialog;
    Context context = getApplication();
    TextView textView2, textView5, textView6,tv_signup;
    MenuItem item;
    int year = 0, monthi = 0, day = 0, databaseyear = 0, databasemonth = 0, databaseday = 0;
    //String databasedate;
    ArrayList<String> xyz = new ArrayList<String>();
    String xyz1, day1, month1, year1, currentmonth1, currentmonth2, frag, date, date1;
    String[] seprated, seprated1;
    FragmentTransaction fragmentTransaction;
    ViewPager mViewPager;
    public String a1,a2,a3,a4,a5,a6;
    String email,password;
   // private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_signup=findViewById(R.id.tv_signup);
        prf = getApplicationContext().getSharedPreferences("Options", Context.MODE_PRIVATE);
        editor = prf.edit();

        if(prf.getBoolean("logged",false)){
            goToMainActivity();
        }

        loginb_main = (Button) findViewById(R.id.loginb_main);
        usernamee_main = (EditText) findViewById(R.id.usernamee_main);
        passworde_main = (EditText) findViewById(R.id.passworde_main);



        loginb_main.setOnClickListener(this);

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new SignUpFragment());
                fragmentTransaction.commit()*/;
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container2,new SignUpFragment());
                fragmentTransaction.commit();



            }
        });
    }

    public void goToMainActivity(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.loginb_main:


                String emailAddress = usernamee_main.getText().toString().trim();
                if (passworde_main.getText().toString().length() < 6) {
                    passworde_main.setError("password minimum contain 6 character");
                            passworde_main.requestFocus();
                }
                if (passworde_main.getText().toString().equals("")) {
                    passworde_main.setError("please enter password");
                            passworde_main.requestFocus();
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    usernamee_main.setError("please enter valid email address");
                            usernamee_main.requestFocus();
                }
                if (usernamee_main.getText().toString().equals("")) {
                    usernamee_main.setError("please enter email address");
                            usernamee_main.requestFocus();
                }
                if (!emailAddress.equals("") &&
                        passworde_main.getText().toString().length() >= 6 &&
                        !passworde_main.getText().toString().trim().equals("")
                        && android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    // do  your action

                    // final String email = usernamee_main.getText().toString().trim();
                    //final String password = passworde_main.getText().toString().trim();
                    email = usernamee_main.getText().toString();
                    password = passworde_main.getText().toString();
                    // final String LOGIN_URL = "http://192.168.0.112/Foodcor/src/userLogin.php";

                    //Creating a string request
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.USER_LOGIN_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject json = new JSONObject(response);
                                        Toast.makeText(MainActivity.this, "162" + json, Toast.LENGTH_SHORT).show();

                                        a1 = (json.getString("msg"));
                                        a2 = (json.getString("person_id"));
                                        a3 = (json.getString("company_id"));
                                        a4 = (json.getString("company_flag"));
                                        a5 = (json.getString("isAdmin"));

                                        //Toast.makeText(MainActivity.this, "Data from Json\n"+a1+"\n"+a2+"\n"+a3+"\n"+a4+"\n"+a5 , Toast.LENGTH_LONG).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //If we are getting success from server
                                    if (a1.equals("0")) {
                                        //Creating a shared preference
                                        Toast.makeText(MainActivity.this, "Invalid username or password" + response.toString(), Toast.LENGTH_LONG).show();

                                    } else {

                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Options", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("logged", true).apply();
                                        editor.putString("person_id", a2);// Storing string
                                        editor.putString("company_id", a3);// Storing string
                                        editor.putString("company_flag", a4);// Storing string
                                        editor.putString("isAdmin", a5);// Storing string


                                        editor.commit();

                                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                                        Toast.makeText(MainActivity.this, a2.toString() + a3.toString() + a4.toString() + a5.toString(), Toast.LENGTH_LONG).show();

                                        //Starting profile activity
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding parameters to request
                            params.put("uname", email);
                            params.put("pwd", password);

                            //returning parameter
                            return params;
                        }
                    };

                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);

                    break;

                }


        }

    }
}
