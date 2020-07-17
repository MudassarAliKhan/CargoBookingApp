package com.pasksoftvalley.cargobookingapp.Presenter;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pasksoftvalley.cargobookingapp.Model.LocationFinder;
import com.pasksoftvalley.cargobookingapp.R;

public class PlaceOrder extends AppCompatActivity {
    private EditText FirstName,LastName,CNIC,PhoneNo,AnyDiesese,TestReport,Address;
    private Button ButtonRegister;
    private ProgressBar Loading;
    String Spinner1Value,Spinner2Value,Spinner3Value;
    String Longitute,Latitude;
    public Spinner Spinner1,Spinner2,Spinner3;
    OrderDetails donorDetails;
    LocationFinder Lf;
    private static String URL_REG ="https://paksoftvalleyariisd.000webhostapp.com/DonorRegistration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Lf = new LocationFinder();
        Longitute = Double.toString(Lf.getLongitute());
        Latitude = Double.toString(Lf.getLatitute());
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        /*Caller*/
        VariableCaller();
        Spinner1Value();
        Spinner2Value();
        Spinner3Value();
        LocationFinder();
        /*Loading = (ProgressBar)findViewById(R.id.);*/
        ButtonRegister = (Button)findViewById(R.id.Register);
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }
    private void VariableCaller()
    {
        FirstName = (EditText)findViewById(R.id.BBDFirstName);
        LastName = (EditText)findViewById(R.id.BBDLastName);
        CNIC = (EditText)findViewById(R.id.BBDCNIC);
        PhoneNo = (EditText)findViewById(R.id.UserPhoneNo);
        Spinner1 = (Spinner) findViewById(R.id.BloodGroupList);
        Spinner2 = (Spinner)findViewById(R.id.BBDTestReportList);
        Spinner3 = (Spinner)findViewById(R.id.Genderlist);
        AnyDiesese = (EditText)findViewById(R.id.BBDAnyDiesese);
        Address = (EditText)findViewById(R.id.BBDAddress);
    }
    private void RegisterUser()
    {
        if(FirstName.getText().toString().equals(""))
        {
            Toast.makeText(this, "Please provide your FirstName", Toast.LENGTH_SHORT).show();
        }
        else if(LastName.getText().toString().equals(""))
        {
            Toast.makeText(this, "Please provide your LastName", Toast.LENGTH_SHORT).show();
        }
        else if(CNIC.getText().toString().equals(""))
        {
            Toast.makeText(this, "Please provide your CNIC", Toast.LENGTH_SHORT).show();
        }
        else if(Address.getText().toString().equals(""))
        {
            Toast.makeText(this, "Please provide your Address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading Data....");
            progressDialog.show();
            final String  UserFirstName =     FirstName.getText().toString();
            final String  UserLastName =      LastName.getText().toString();
            final String  UserCNIC =          CNIC.getText().toString();
            final String  UserGender =        Spinner2Value;
            final String  UserPhoneNo    =    PhoneNo.getText().toString();
            final String  UserBloodGroup =  Spinner1Value;
            final String  UserTestReport =  Spinner3Value;
            final String  UserLongitute =   Longitute;
            final String  UserLatitute =    Latitude;
            final String  UserAnyDiesese =   AnyDiesese.getText().toString();
            final String  UserAddress =     Address.getText().toString();
            StringRequest PostRequest = new StringRequest(Request.Method.POST, URL_REG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {

                            // Loading.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                            Toast.makeText(PlaceOrder.this, "Order Place Successfully", Toast.LENGTH_SHORT).show();
                            FirstName.setText("");
                            LastName.setText("");
                            CNIC.setText("");
                            AnyDiesese.setText("");
                            Address.setText("");
                            /*try
                            {
                                JSONObject jsonObject= new JSONObject(response);
                                String success = jsonObject.getString("success").toString();
                                if(success.equals("1"))
                                {
                                }
                            }catch (JSONException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(RegisterYourSelf.this, "Registration Failed"+e.toString(), Toast.LENGTH_SHORT).show();
                                Loading.setVisibility(View.INVISIBLE);
                                ButtonRegister.setVisibility(View.VISIBLE);
                            }*/
                            // Loading.setVisibility(View.GONE);
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PlaceOrder.this, "Order Placement Failed"+"Poor Network", Toast.LENGTH_SHORT).show();
                            //   Loading.setVisibility(View.INVISIBLE);
                            ButtonRegister.setVisibility(View.VISIBLE);
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> prams = new HashMap<String,String>();
                    prams.put("FirstName",UserFirstName);
                    prams.put("LastName",UserLastName);
                    prams.put("Gender",UserGender);
                    prams.put("PhoneNo",UserPhoneNo);
                    prams.put("CNIC",UserCNIC);
                    prams.put("BloodGroup",UserBloodGroup);
                    prams.put("TestReport",UserTestReport);
                    prams.put("AnyDiesese",UserAnyDiesese);
                    prams.put("Address",UserAddress);
                    prams.put("Longitute",UserLongitute);
                    prams.put("Latitude",UserLatitute);
                    return prams;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(PostRequest);
        }
    }

    /*LocationFinder*/
    private void LocationFinder()
    {

    }
    /*Blood Group*/
    private void Spinner1Value()
    {
        try {
            Spinner1 = (Spinner) findViewById(R.id.BloodGroupList);
            List<String> list3 = new ArrayList<String>();
            list3.add(0, "Choose Blood Group");
            list3.add("A");
            list3.add("O");
            list3.add("B");
            list3.add("AB");
            list3.add("AB+");
            list3.add("AB-");
            list3.add("A+");
            list3.add("A-");
            list3.add("B+");
            list3.add("B-");
            list3.add("O+");
            list3.add("O-");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list3);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner1.setAdapter(dataAdapter);
            Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    if(parent.getItemAtPosition(position).equals("Choose Unit"))
                    {
                        //do nothing;
                        System.out.println("Do Nothing");
                    }
                    else {
                        Spinner1Value = parent.getItemAtPosition(position).toString();
                        Toast.makeText(PlaceOrder.this, Spinner1Value, Toast.LENGTH_SHORT).show();
                    }
                };

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                    Toast.makeText(getApplicationContext(), "No Value is Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    /*TestReports Results*/
    private void Spinner2Value()
    {
        try {
            Spinner2 = (Spinner) findViewById(R.id.Genderlist);
            List<String> list3 = new ArrayList<String>();
            list3.add(0, "Choose Gender");
            list3.add("Male");
            list3.add("Female");
            list3.add("Trans-Gender");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list3);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner2.setAdapter(dataAdapter);
            Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    if(parent.getItemAtPosition(position).equals("Choose Unit"))
                    {
                        //do nothing;
                        System.out.println("Do Nothing");
                    }
                    else {
                        Spinner2Value = parent.getItemAtPosition(position).toString();
                        Toast.makeText(PlaceOrder.this, Spinner2Value, Toast.LENGTH_SHORT).show();
                    }
                };

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(PlaceOrder.this, "No Value is Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    /*Gender*/
    private void Spinner3Value() {
        try {
            Spinner3 = (Spinner) findViewById(R.id.BBDTestReportList);
            List<String> list3 = new ArrayList<String>();
            list3.add(0, "Test Report Result");
            list3.add("Positive");
            list3.add("Negative");
            list3.add("None");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list3);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner3.setAdapter(dataAdapter);
            Spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    if(parent.getItemAtPosition(position).equals("Choose Unit"))
                    {
                        //do nothing;
                        System.out.println("Do Nothing");
                    }
                    else {
                        Spinner3Value = parent.getItemAtPosition(position).toString();
                        Toast.makeText(PlaceOrder.this, Spinner3Value, Toast.LENGTH_SHORT).show();
                    }
                };

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(PlaceOrder.this, "No Value is Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
    //Performing action onItemSelected and onNothing selected



}