package com.halanx.tript.userapp.Activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.halanx.tript.userapp.LocService;
import com.halanx.tript.userapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    SuggestionAdapter adapterTemp;
    int a = 0;
    RecyclerView suggestion_data;
    int length;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    SearchView searchView;
    TextView confirmBtn;
    FloatingActionButton setLocation;
    double lat, lon, latD, lonD, ulan = 0.0, ulon = 0.0;
    int i = 0, x = 0;
    LatLng locationAfterOrder, userLocation;
    List<String> suggestions = new ArrayList<>();
    CardView cvConfirmLoc;
    SharedPreferences.Editor editor;
    String addressConfirm;
    LatLng latLon;

    LatLng currLoc;
    String ApiKey = "AIzaSyBnCtz4CuPtcZ-87zXLsYvH1BrkTTJ9eyw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        cvConfirmLoc = (CardView) findViewById(R.id.cv_loc_confirm);
        setLocation = (FloatingActionButton) findViewById(R.id.set_location1);

        //Check internet connection
        if(!isNetworkAvailable()){
            new AlertDialog.Builder(this)
                    .setTitle("No internet connection")
                    .setMessage("You are not connected to the internet").setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .show();
        }



        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        confirmBtn = (TextView) findViewById(R.id.confirmBtn);
        checkLocationServices();
        mapFrag.getMapAsync(this);
        suggestion_data = (RecyclerView) findViewById(R.id.suggestions_data);

        getSharedPreferences("Store", Context.MODE_PRIVATE).edit().
                putBoolean("isMap", true).apply();


     /*  FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyAUvuRnFQtjzG29GBADSUybqTOpn-WXKQ")
                .setApplicationId("1:1022703278715:android:6b89977da89b7e26")
                .setDatabaseUrl("https://shopperapphalanx.firebaseio.com")
                .build();

        if(!already_initialise) {
            FirebaseApp shopperApp = FirebaseApp.initializeApp(MapsActivity.this, options, "ShopperAppReference");
            shopperDatabase = FirebaseDatabase.getInstance(shopperApp);
            shopperAppReferece = shopperDatabase.getReference();
            already_initialise = true;
        }*/

        SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        latD = sharedPreferences.getFloat("latitudeDelivery", 0);
        lonD = sharedPreferences.getFloat("longitudeDelivery", 0);
        latD = Double.parseDouble(new DecimalFormat("##.####").format(latD));
        lonD = Double.parseDouble(new DecimalFormat("##.####").format(lonD));


        searchView = (SearchView) findViewById(R.id.floating_search_view);

        EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchPlate.setHint("Search");

        View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d("focus", String.valueOf(b));
                if (b) {
                    if (suggestion_data.getVisibility() == View.GONE) {
                        suggestion_data.setVisibility(View.VISIBLE);
                    }

                } else {
                    suggestion_data.setVisibility(View.GONE);
                }
            }
        });

        // use this method for search process
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // use this method when query submitted

                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                // use this method for auto complete search process
                suggestions.clear();

                String url = "https://maps.googleapis.com//maps/api/place/autocomplete/json?input="
                        + newText + "&components=country:in&key="+ ApiKey;
                Volley.newRequestQueue(MapsActivity.this).add(new StringRequest(Request.Method.GET, url.replace(" ", "+").trim(),
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.i("Response",response);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    if (jsonObject.get("status").equals("OK")) {
                                        try {
                                            length = jsonObject.getJSONArray("predictions").length();
                                            for (int i = 0; i < length; ++i) {
                                                Log.d("TAG", "onResponse: " + jsonObject.getJSONArray("predictions")
                                                        .getJSONObject(i).getJSONObject("structured_formatting").get("main_text"));
                                                suggestions.add(jsonObject.getJSONArray("predictions")

                                                        .getJSONObject(i).getJSONObject("structured_formatting").
                                                                get("main_text").toString());


                                            }

                                            adapterTemp = new SuggestionAdapter(suggestions, getApplicationContext(), searchView);
                                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                            suggestion_data.setAdapter(adapterTemp);
                                            suggestion_data.setLayoutManager(layoutManager);
                                            Log.d("suggestions", String.valueOf(a) + String.valueOf(suggestions));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));


                return false;
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


//        Log.d("latD", "" + latD);
//
//        Log.d("lonD", "" + lonD);
//
//
//        x = sharedPreferences.getInt("x", 0);

        //GETTING SHOPPER LOCATION WHEN ORDER IS ACCEPTED

       /* shopperAppReferece.child("Shopper Location").child("Shopper ID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LatLongInfo info = dataSnapshot.getValue(LatLongInfo.class);
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(info.getLatitude()+0.01,
                        info.getLongitude()+0.01)).
                        title("Shopper Location").icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_CYAN)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrLocationMarker != null) {

                    mapFrag.getMapAsync(MapsActivity.this);
                    Intent intent = new Intent(MapsActivity.this, LocService.class);
                    startService(intent);


                    LocalBroadcastManager.getInstance(MapsActivity.this).registerReceiver(
                            mMessageReceiver, new IntentFilter("GPSLocationUpdates"));
                } else {

                    buildGoogleApiClient();
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mGoogleMap.setMyLocationEnabled(true);

                }
            }
        });
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            lat = intent.getDoubleExtra("latitude", 0.0);
            lon = intent.getDoubleExtra("longitude", 0.0);

            LatLng l = new LatLng(lat, lon);
            mCurrLocationMarker.setPosition(l);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(l).zoom(14).build();

            mCurrLocationMarker.setDraggable(true);
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        i = 1;

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();

            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);


        }

        //CODE TO BE USED LATER
//            GoogleDirection.withServerKey("AIzaSyDGpGmvzDetvS5IVrvceXvpgh83f6QSSis").
//                    from(new LatLng(poloc.latitude, poloc.longitude))
//                    .to(shopperLocation).avoid(AvoidType.FERRIES)
//                    .avoid(AvoidType.HIGHWAYS).execute(new DirectionCallback() {
//                @Override
//                public void onDirectionSuccess(Direction direction, String rawBody) {
//                    String status = direction.getStatus();
//                    Toast.makeText(MapsActivity.this, "Direction status : " + status, Toast.LENGTH_SHORT).show();
//                    if (status.equals(RequestResult.OK)) {
//
//                        Route route = direction.getRouteList().get(0);
//                        Leg leg = route.getLegList().get(0);
//                        ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
//                        PolylineOptions polylineOptions = DirectionConverter.createPolyline(MapsActivity.this,
//                                directionPositionList, 5, Color.RED);
//                        mGoogleMap.addPolyline(polylineOptions);
//
//                    } else if (status.equals(RequestResult.NOT_FOUND)) {
//                        Toast.makeText(MapsActivity.this, "Shopper location can't be determined at the moment",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onDirectionFailure(Throwable t) {
//                }
//            });


//        }

        cvConfirmLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrLocationMarker != null) {
                    locationAfterOrder = mCurrLocationMarker.getPosition();
                    SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);

                    editor = sharedPreferences.edit();
                    lat = locationAfterOrder.latitude;
                    lon = locationAfterOrder.longitude;

                    Log.d("TAG", "" + lon);


                    String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&key="+ApiKey;
                    Volley.newRequestQueue(MapsActivity.this).add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject revGeo = null;
                            Log.i("Response",response);
                            try {
                                revGeo = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (revGeo != null && revGeo.get("status").equals("OK")) {
                                    try {

                                        addressConfirm = revGeo.getJSONArray("results").getJSONObject(0).get("formatted_address").toString();
                                        Log.i("ADD", addressConfirm);

                                        mCurrLocationMarker.setTitle("Current Location");

                                        final AlertDialog.Builder dial = new AlertDialog.Builder(MapsActivity.this);
                                        final AlertDialog dialog;

                                        dial.setTitle("Please confirm your location ");
                                        dial.setMessage("Confirm location as " + addressConfirm);
                                        dial.setCancelable(false);
                                        dial.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int m) {
                                                i = 1;
                                                editor.putFloat("latitudeDelivery", (float) lat);
                                                editor.putFloat("longitudeDelivery", (float) lon);
                                                editor.putString("addressDelivery", addressConfirm);
                                                editor.apply();

                                                Log.d("activities", String.valueOf(getIntent().getBooleanExtra("fromCart", false)));
                                                if (getIntent().getBooleanExtra("fromCart", false)) {
                                                    getSharedPreferences("Store", Context.MODE_PRIVATE).edit().putBoolean("isMap", false).apply();
                                                    finish();
                                                } else {

                                                    startActivity(new Intent(MapsActivity.this, HomeActivity.class));
                                                    finish();
                                                }
                                            }
                                        });

                                        dial.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int m) {

                                                LocalBroadcastManager.getInstance(MapsActivity.this).registerReceiver(
                                                        mMessageReceiver, new IntentFilter("GPSLocationUpdates"));
                                                dialogInterface.dismiss();
                                            }
                                        });

                                        dialog = dial.create();
                                        dialog.show();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));

                }

            }
        });

        //Giving null point
        {
            mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    LatLng centerOfMap = mGoogleMap.getCameraPosition().target;
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.setPosition(centerOfMap);

                    }


                }
            });
        }
    }

    private void checkLocationServices() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location services not enabled");  // GPS not found
            builder.setMessage("Kindly enable the location services to proceed"); // Want to enable?
            builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.create().show();
            return;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (i == 1 || i == 2) {


            mLastLocation = location;

            if (i == 1 && mCurrLocationMarker == null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                getLatLongFromAddress(String.valueOf(location));

                markerOptions.title("Your Location");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(mCurrLocationMarker.getPosition()).zoom(14).build();

                currLoc = new LatLng(location.getLatitude(), location.getLongitude());

                mGoogleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
                i = 0;
            }


        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();


                        }
                        mGoogleMap.setMyLocationEnabled(true);

                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //Check internet connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.TempViewHolder> {

        List<String> suggestion;
        Context context;
        SearchView searchView;

        TextView data;

        SuggestionAdapter(List<String> s, Context applicationContext, SearchView searchView) {
            suggestion = s;
            context = applicationContext;
            this.searchView = searchView;

        }


        @Override
        public SuggestionAdapter.TempViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
            return new TempViewHolder(view, context, suggestion);
        }

        @Override
        public void onBindViewHolder(TempViewHolder holder, int position) {

            holder.number.setText(String.valueOf(position + 1));
            holder.data.setText(suggestion.get(position));

        }


        public class TempViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {


            List<String> suggestion;
            TextView data, number;
            Context c;

            TempViewHolder(View view, Context context, List<String> suggestion) {
                super(view);

                this.suggestion = suggestion;
                c = context;
                data = (TextView) view.findViewById(R.id.search_item);
                number = (TextView) view.findViewById(R.id.number);
                data.setOnClickListener(this);
                number.setOnClickListener(this);
            }


            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();

                getLatLongFromAddress(suggestion.get(pos));

            }
        }


        @Override
        public int getItemCount() {
            return suggestion.size();
        }


    }

    void getLatLongFromAddress(String strAddress) {

        String add = strAddress.replace(" ", "+");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + add + "&region=in" +
                "&key="+ApiKey;
        Volley.newRequestQueue(MapsActivity.this).add(new StringRequest(Request.Method.GET, url.trim()
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TAGG", response);
                JSONObject obj = null;
                try {

                    obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    if (obj.get("status").equals("OK")) {
                        try {
                            String location = obj.getJSONArray("results").getJSONObject(0).get("formatted_address").toString();

                            JSONObject loc = obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                            Double lat = Double.parseDouble(loc.get("lat").toString());
                            Double lon = Double.parseDouble(loc.get("lng").toString());
                            Log.i("TAGG", lat + " " + lon);


                            suggestion_data.setVisibility(View.GONE);

                            LatLng l = new LatLng(lat, lon);

                            mCurrLocationMarker.remove();
                            MarkerOptions markerOptions = new MarkerOptions().position(l).
                                    title("Your Location").
                                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(mCurrLocationMarker.getPosition()).zoom(14).build();

                            mGoogleMap.animateCamera(CameraUpdateFactory
                                    .newCameraPosition(cameraPosition));
                            i = 0;

                            searchView.setQuery(location, true);
                            searchView.clearFocus();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }


}


