package com.pasksoftvalley.cargobookingapp.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pasksoftvalley.cargobookingapp.Model.OrderDetailsModel;
import com.pasksoftvalley.cargobookingapp.R;
import com.pasksoftvalley.cargobookingapp.View.MainListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminDashBoard extends AppCompatActivity {

    ListView list;
    MainListAdapter adapter;
    public static ArrayList<OrderDetailsModel>  donorArraylist = new ArrayList<>();
    private static String URL_REG ="https://paksoftvalleyariisd.000webhostapp.com/ViewDonorDetails.php";
    OrderDetailsModel donor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewDonerDetails();
        list = (ListView)findViewById(R.id.MainList);
        adapter = new MainListAdapter(this,donorArraylist);
        list.setAdapter(adapter);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"View Donor Details"};
                builder.setTitle(donorArraylist.get(position).getFirstName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), OrderDetails.class)
                                        .putExtra("position",position));
                                break;
                            case 1:
                                /*startActivity(new Intent(getApplicationContext(),Edit_Activity.class)
                                        .putExtra("position",position));*/
                                break;
                            case 2:
                                /* deleteData(employeeArrayList.get(position).getId());*/
                                break;

                        }

                    }
                });
                builder.create().show();
            }
        });

        // retrieveData();
        //ViewDonerDetails();

    }

    /*private void deleteData(final String id) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://arsltechmysql.000webhostapp.com/delete.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("Data Deleted")){
                            Toast.makeText(HomeOfDoners.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(HomeOfDoners.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeOfDoners.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }*/
    public void ViewDonerDetails(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        // progressBar.setVisibility(View.VISIBLE);
        //String url = "http://www.playard.in/api/get_all_my_bookings_mobile";
        StringRequest request = new StringRequest(Request.Method.POST, URL_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();
                        donorArraylist.clear();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("bbdonerdetails");
                            if(sucess.equals("1")){
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jresponse = jsonArray.getJSONObject(i);
                                    final String id =                 jresponse.getString("id");
                                    final String DisplayFirstName =   jresponse.getString("FirstName");
                                    final String DisplayLastName =    jresponse.getString("LastName");
                                    final String DisplayGender =      jresponse.getString("Gender");
                                    final String PhoneNo =            jresponse.getString("PhoneNo");
                                    final String DisplayCNIC =        jresponse.getString("CNIC");
                                    final String DisplayBloodGroup =  jresponse.getString("BloodGroup");
                                    final String DisplayTestReports = jresponse.getString("TestReports");
                                    final String DisplayAnyDiesese =  jresponse.getString("AnyDiesese");
                                    final String DisplayAddress =     jresponse.getString("Address");
                                    final String DisplayLongitude =   jresponse.getString("Longitute");
                                    final String DisplayLatitude =    jresponse.getString("Latitude");
                                    donor = new OrderDetailsModel(id,DisplayFirstName, DisplayLastName, DisplayGender,PhoneNo, DisplayCNIC,DisplayBloodGroup,DisplayTestReports, DisplayAnyDiesese, DisplayAddress, DisplayLongitude, DisplayLatitude);
                                    donorArraylist.add(donor);
                                    adapter.notifyDataSetChanged();
                                }

                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Poor Network", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    /*public void btn_add_activity(View view) {
        startActivity(new Intent(getApplicationContext(),Add_Data_Activity.class));
    }*/
    public void BecomeaDonor(MenuItem item)
    {
        Intent i11 =new Intent(getApplicationContext(),AllNewOrders.class);
        startActivity(i11);
    }

    public void FindDonor(MenuItem item)
    {
        Intent i11 =new Intent(getApplicationContext(),AllNewOrders.class);
        startActivity(i11);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    public void Logout(MenuItem item)
    {
        Intent i11 =new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i11);
    }
}