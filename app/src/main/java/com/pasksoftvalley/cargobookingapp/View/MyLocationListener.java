package com.pasksoftvalley.cargobookingapp.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.pasksoftvalley.cargobookingapp.Model.LocationFinder;

import java.util.List;
import java.util.Locale;

public class MyLocationListener implements LocationListener {
    Context context;
    public static double longitude,latitude;
    public static String cityName="no";

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        MyLocationListener.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        MyLocationListener.latitude = latitude;
    }

    public static String getCityName() {
        return cityName;
    }

    public static void setCityName(String cityName) {
        MyLocationListener.cityName = cityName;
    }

    public static String getCountryCode() {
        return countryCode;
    }

    public static void setCountryCode(String countryCode) {
        MyLocationListener.countryCode = countryCode;
    }

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        MyLocationListener.location = location;
    }

    public static String countryCode="no";
    public static Location location;
    public static Context con;
    public MyLocationListener(Context context){
        this.context  =context ;
        con=context;
    }
    public void onLocationChanged(Location location) {
        String loc="Longitude="+String.valueOf(location.getLongitude())+", Latitude="+String.valueOf(location.getLatitude());
        longitude=location.getLongitude();
        latitude=location.getLatitude();
        LocationFinder ff = new LocationFinder(longitude,latitude);
        ff.longitute = longitude;
        ff.latitute = latitude;
        //   Toast.makeText(context,""+loc, Toast.LENGTH_LONG).show();
        //   this.location=location;
        /*----------to get City-Name from coordinates ------------- */
        Geocoder gcd = new Geocoder(context,
                Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location
                    .getLongitude(), 1);
            cityName=addresses.get(0).getLocality();
            countryCode= addresses.get(0).getCountryCode();
            //  Toast.makeText(context, cityName, Toast.LENGTH_LONG).show();
            // not working but improve it
            if(cityName.isEmpty())
            {
                cityName=countryCode;
            }

        } catch (Exception e) {
        }
    }
    public void onStatusChanged(String s, int i, Bundle b) {
        //  Toast.makeText(context, "Provider status changed",
        //         Toast.LENGTH_LONG).show();
    }

    public void onProviderDisabled(String s) {
        Toast.makeText(context,
                "Could not save location as GPS was turned off. Change your location later.",
                Toast.LENGTH_LONG).show();

    }

    public void onProviderEnabled(String s) {
        // SettingSaved.LoadData(context);
        Toast.makeText(context,
                "GPS turned on successfully. Now you can change your location.",
                Toast.LENGTH_LONG).show();
    }

    public static void alertBoxIntentOpener() {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage("Your Device's GPS is Disable. Please turn on to change your location. Your saved location will help you in finding users near you.")
                .setCancelable(false)
                .setTitle("Gps Status")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                con.startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean checkGpsStatus(){

        try {
            LocationManager locationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);

            boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (gpsStatus == true) {
                gpsStatus = true;
            } else {
                gpsStatus = false;
            }
            return gpsStatus;
        }

        catch (Exception e)
        {
            return false;
        }

    }

}