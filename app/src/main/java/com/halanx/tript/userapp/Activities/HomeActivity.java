package com.halanx.tript.userapp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.GsonBuilder;
import com.halanx.tript.userapp.Fragments.MainFragment;
import com.halanx.tript.userapp.Interfaces.DataInterface;
import com.halanx.tript.userapp.POJO.CartItem;
import com.halanx.tript.userapp.POJO.UserInfo;
import com.halanx.tript.userapp.R;
import com.halanx.tript.userapp.app.Config;
import com.halanx.tript.userapp.util.NotificationUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.halanx.tript.userapp.GlobalAccess.djangoBaseUrl;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    ImageView userImage;
    TextView nametv;
    Retrofit.Builder builder;
    Retrofit retrofit;
    DataInterface client;

    ImageView cart, locationButton;
    RelativeLayout cartitems;
    TextView itemcount;
    List<CartItem> activeItems;

    private static final String TAG = HomeActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    List<CartItem> items;
    AppBarLayout barLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        barLayout = (AppBarLayout) findViewById(R.id.app_bar);

        cart = (ImageView) findViewById(R.id.imageButton_cart);
        locationButton = (ImageView) findViewById(R.id.imageButton_location);
        cart.setOnClickListener(this);
        locationButton.setOnClickListener(this);


        builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).
                addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        client = retrofit.create(DataInterface.class);

        cartitems = (RelativeLayout) findViewById(R.id.cartitems);
        itemcount = (TextView) findViewById(R.id.itemcount);

        // set the fragment initially
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //FB LOGIN
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        nametv = (TextView) view.findViewById(R.id.nametv);
        userImage = (ImageView) view.findViewById(R.id.userimage);

        if (getSharedPreferences("Login", Context.MODE_PRIVATE).getString("MobileNumber", null) != null) {
            String userInfo = getSharedPreferences("Login", Context.MODE_PRIVATE).getString("UserInfo", null);
            UserInfo user = new GsonBuilder().create().fromJson(userInfo, UserInfo.class);
            nametv.setText("Hi " + user.getFirstName() + " !");
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("FB_DATA", Context.MODE_PRIVATE);
            String fName = sharedPreferences.getString("fbName", "halanx");
            Log.d("fname", fName);

            String fEmail = sharedPreferences.getString("fbEmail", "halanx");
            Log.d("femail", fEmail);
            String image = sharedPreferences.getString("fbPic", "halanx");
            Log.d("fimage", image);
            nametv.setText(fName);
            Picasso.with(this).load(image).into(userImage);

        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //  txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();


        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String mobileNumber = sharedPreferences.getString("MobileNumber", null);


        Call<List<CartItem>> call = client.getUserCartItems(mobileNumber);

        call.enqueue(new Callback<List<CartItem>>() {
                         @Override
                         public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {

                             items = response.body();
                             //Log.d("items", String.valueOf(items));

                             if (items != null && items.size() > 0) {
                                 cartitems.setVisibility(View.VISIBLE);
                                 itemcount.setText(String.valueOf(items.size()));

                             } else {
                                 cartitems.setVisibility(View.GONE);
                             }

                         }

                         @Override
                         public void onFailure(Call<List<CartItem>> call, Throwable t) {

                         }

                     }
        );

        sharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("first_login", false)) {

            ViewTarget target = new ViewTarget(R.id.imageButton_location, this);
            ShowcaseView sv = new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .setTarget(target)
                    .setContentTitle("DELIVERY LOCATION")
                    .setContentText("Change your Delivery Location and Search for Nearby By stores by just a singe Tap ")
                    .withHoloShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .build();


        }
        getSharedPreferences("status", Context.MODE_PRIVATE).edit().
                putBoolean("first_login", false).apply();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {

            //ADD ACCOUNT PAGE HERE
            startActivity(new Intent(HomeActivity.this, AccountActivity.class));

        } else if (id == R.id.nav_order) {

            startActivity(new Intent(HomeActivity.this, OrdersActivity.class));


        } else if (id == R.id.nav_payment) {

            startActivity(new Intent(HomeActivity.this, SavedCardsActivity.class));

        } else if (id == R.id.nav_favourites) {
            startActivity(new Intent(HomeActivity.this, FavouritesActivity.class));


        } else if (id == R.id.nav_pts) {
            startActivity(new Intent(HomeActivity.this, PointsActivity.class));


        } else if (id == R.id.nav_ref) {
            startActivity(new Intent(HomeActivity.this, ReferEarnActivity.class));

        } else if (id == R.id.nav_shopper) {

            startActivity(new Intent(HomeActivity.this, BecomeShopperActivity.class));

        } else if (id == R.id.nav_help) {

            startActivity(new Intent(HomeActivity.this, HelpActivity.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.imageButton_cart:

                startActivity(new Intent(HomeActivity.this, CartActivity.class));

                break;

//            case R.id.mapstart:
//                startActivity(new Intent(HomeActivity.this, MapsActivity.class));
//                break;

            case R.id.imageButton_location:
                startActivity(new Intent(HomeActivity.this, MapsActivity.class));
                break;


        }
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        String url = "http://ec2-34-208-181-152.us-west-2.compute.amazonaws.com/users/" +
                getSharedPreferences("Login", Context.MODE_PRIVATE).getString("MobileNumber", null)+"/";
        Log.i("Gcm",url);
        JSONObject obj = new JSONObject();
        try {
            obj.put("GcmId", regId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Volley.newRequestQueue(this).add(new JsonObjectRequest(Request.Method.PATCH, url, obj, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("GcmId", "Done");
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

        Log.e(TAG, "Firebase reg id: " + regId);

//        if (!TextUtils.isEmpty(regId))
//      //      txtRegId.setText("Firebase Reg Id: " + regId);
//        else//    txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String mobileNumber = sharedPreferences.getString("MobileNumber", null);


        Call<List<CartItem>> call = client.getUserCartItems(mobileNumber);

        call.enqueue(new Callback<List<CartItem>>() {
                         @Override
                         public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {

                             items = response.body();
                             // Log.d("items", String.valueOf(items));

                             if (items != null && items.size() > 0) {
                                 cartitems.setVisibility(View.VISIBLE);
                                 itemcount.setText(String.valueOf(items.size()));

                             } else {
                                 cartitems.setVisibility(View.GONE);
                             }

                         }

                         @Override
                         public void onFailure(Call<List<CartItem>> call, Throwable t) {

                         }

                     }
        );


        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


}

/*
public void showNotifiction(View v ){


    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setSmallIcon(R.drawable.logo);
    builder.setContentTitle("HALANX");
    builder.setContentText("DATA FOR NOTIFICATION");

    Intent intent = new Intent(this,CLASS_AFTER_NOTIFICATION);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    stackBuilder.addParentStack(CLASS_AFTER_NOTIFICATION);
    stackBuilder.addNextIntent(intent);
    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(pendingIntent);

    NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    NM.notify(0,builder.build());


}
*/




