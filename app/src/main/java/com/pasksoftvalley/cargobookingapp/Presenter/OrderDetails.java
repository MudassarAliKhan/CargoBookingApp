package com.pasksoftvalley.cargobookingapp.Presenter;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pasksoftvalley.cargobookingapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderDetails extends AppCompatActivity {

    TextView text0, text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11;
    int position;
    ImageView imageButton;
    String longitude;
    String Latitute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        imageButton = (ImageView) findViewById(R.id.ImageId);
        variablecaller();
        showdetails();
        longitude = text9.getText().toString();
        Latitute = text10.getText().toString();
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloodDonorGoogleLocation();
            }
        });
    }

    private void variablecaller()
    {
        text0 = (TextView)findViewById(R.id.DonorId);
        text1 = (TextView)findViewById(R.id.DisplayFirstName);
        text2 = (TextView)findViewById(R.id.DisplayLastName);
        text3 = (TextView)findViewById(R.id.DisplayCNIC);
        text4 = (TextView)findViewById(R.id.DisplayBloodGroup);
        text5 = (TextView)findViewById(R.id.DisplayGender);
        text6 = (TextView)findViewById(R.id.DisplayAnyDiesese);
        text7 = (TextView)findViewById(R.id.DisplayTestReports);
        text8 = (TextView)findViewById(R.id.DisplayAddress);
        text9 = (TextView)findViewById(R.id.DisplayLongitude);
        text10 = (TextView)findViewById(R.id.DisplayLatitude);
        text11 = (TextView)findViewById(R.id.PhoneNo);

    }
    private void showdetails()
    {
        Intent ii = getIntent();
        position = ii.getExtras().getInt("position");
        if(position!=0)
        {
            text0.setText(AdminDashBoard.donorArraylist.get(position).getid());
            text1.setText(AdminDashBoard.donorArraylist.get(position).getFirstName());
            text2.setText(AdminDashBoard.donorArraylist.get(position).getLastName());
            text3.setText(AdminDashBoard.donorArraylist.get(position).getCNIC());
            text4.setText(AdminDashBoard.donorArraylist.get(position).getBloodGroup());
            text5.setText(AdminDashBoard.donorArraylist.get(position).getGender());
            text6.setText(AdminDashBoard.donorArraylist.get(position).getAnyDiesese());
            text7.setText(AdminDashBoard.donorArraylist.get(position).getTestReport());
            text8.setText(AdminDashBoard.donorArraylist.get(position).getAddress());
            text9.setText(AdminDashBoard.donorArraylist.get(position).getLongitute());
            text10.setText(AdminDashBoard.donorArraylist.get(position).getLatitude());
            text11.setText(AdminDashBoard.donorArraylist.get(position).getPhoneNo());
        }
    }
    public void BloodDonorGoogleLocation()
    {
        Intent intent = new Intent(OrderDetails.this,MapsActivity.class);
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitute",Latitute);
        startActivity(intent);
    }

}