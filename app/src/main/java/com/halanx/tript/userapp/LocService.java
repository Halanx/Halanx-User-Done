package com.halanx.tript.userapp;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Dell on 6/30/2017.
 */

public class LocService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback{

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000;
    private static int FATEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("Inside","ON START COMMAND");


        if (checkPlayServices()) {
            Log.d("Inside","PLAY SERVICES");
            buildGoogleApiClient();
            createLocationRequest();
        }
        displayLocation();

        return START_NOT_STICKY;
    }

    private void displayLocation() {

        Log.d("Inside","DISPLAY");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleApiClient.connect();
        Log.d("Inside"," check DISPLAY");

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.d("Inside"," check last location DISPLAY");

        if (mLastLocation != null) {
            Log.d("Inside"," check null DISPLAY");
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            Intent intent = new Intent("GPSLocationUpdates");
            Bundle b = new Bundle();
            b.putParcelable("location", b);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            Log.d("latitude",""+latitude);
            Log.d("latitude",""+longitude);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        Log.d("Inside"," Build google api client");
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        Log.d("Inside","location request");
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) getApplicationContext(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
                Log.d("Inside","CHECKED PLAY");
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                Log.d("Inside","CHECKED not PLAY");
            }
            return false;
        }
        return true;
    }
    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Inside","start locatyion update");

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d("Inside","start locatyion update 2");

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Inside","onCOnnected");
        displayLocation();

        if (mRequestingLocationUpdates) {
            Log.d("Inside","sartttttt");
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Inside","Connection susprnd");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
    @Override
    public void onLocationChanged(Location location) {
        Log.d("Inside","Connectio fail");
        mLastLocation = location;

        displayLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
