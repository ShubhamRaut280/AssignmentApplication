package com.shubham.arachnomeshassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import android.provider.Settings;
import android.text.Highlights;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity  {

    private static final int PER_REQ_CODE = 100;

    double latitude, longitude;
    EditText name, contact;
    TextView permissionDenied, locationText;
    Button getLocationbutton;
    ProgressBar pb;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.nameField);
        contact = findViewById(R.id.contactField);
        locationText = findViewById(R.id.locationField);
        getLocationbutton = findViewById(R.id.getlocation);
        permissionDenied = findViewById(R.id.permissionDenied);
        pb = findViewById(R.id.progressBar);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        getLocationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    if (isGPSEnabled())
                    {
                        getcurrentLocation();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                        turnOnGPS();
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PER_REQ_CODE);
                }

            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PER_REQ_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getcurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PER_REQ_CODE);
            }
        }


    }

    @SuppressLint("MissingPermission")
    public void getcurrentLocation()
    {
        if(isGPSEnabled())
        {
            pb.setVisibility(View.VISIBLE);
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest,
                    new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            if(locationResult!=null && locationResult.getLocations().size() > 0)
                            {
                                latitude = locationResult.getLocations().get(locationResult.getLocations().size()-1).getLatitude();
                                longitude = locationResult.getLocations().get(locationResult.getLocations().size()-1).getLongitude();
                               locationText.setText("Latitude : "+latitude+" ,  Longitude: "+longitude +" ");
                               pb.setVisibility(View.GONE);
                            }
                        }
                    }, Looper.getMainLooper());
        }
        else
        {
            turnOnGPS();
        }
    }

    public void turnOnGPS() {
        Intent enableGps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(enableGps);
    }

    public boolean isGPSEnabled() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}