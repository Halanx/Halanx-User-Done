package com.halanx.tript.userapp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.halanx.tript.userapp.Interfaces.DataInterface;
import com.halanx.tript.userapp.POJO.Resp;
import com.halanx.tript.userapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.halanx.tript.userapp.Activities.MapsActivity.MY_PERMISSIONS_REQUEST_LOCATION;
import static com.halanx.tript.userapp.GlobalAccess.phpBaseUrl;

/**
 * Created by samarthgupta on 12/02/17.
 */


public class SigninActivity extends AppCompatActivity {

    private EditText inputMobile, inputPassword;
    private ProgressBar progressBar;
    private TextView btnRegister;
    private Button btnLogin;
    SharedPreferences sharedPreferences;

    LoginButton fblogin;
    CallbackManager callbackManager;
    String name, email;
    String[] namea;
    String mobile;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        }

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
                                ActivityCompat.requestPermissions(SigninActivity.this,
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


        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
            Boolean loginStatus = sharedPreferences.getBoolean("Loginned", false);

        if (loginStatus) {
            if(sharedPreferences.getBoolean("first_login",false)) {

                startActivity(new Intent(SigninActivity.this, HomeActivity.class));

                finish();
            }
            else
                {
                startActivity(new Intent(SigninActivity.this, MapsActivity.class));
                finish();
            }
        }




        //FACEBOOK SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_signin);


        
        inputMobile = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnRegister = (TextView) findViewById(R.id.signUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = inputMobile.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "Enter mobile address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);

                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(phpBaseUrl)
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                DataInterface client = retrofit.create(DataInterface.class);
                Call<Resp> call = client.login(mobile, password);

                call.enqueue(new Callback<Resp>() {
                    @Override
                    public void onResponse(Call<Resp> call, Response<Resp> response) {

                        Log.e("TAG", "LOGIN SUCCESS" + !response.body().getError());

                        //IF ERROR IS FALSE, LOGIN SUCCESS
                        if (!response.body().getError()) {
                            progressBar.setVisibility(View.INVISIBLE);
//                            Toast.makeText(SigninActivity.this, "Login " + !response.body().getError(), Toast.LENGTH_SHORT).show();

                            Volley.newRequestQueue(SigninActivity.this).add(new StringRequest(Request.Method.GET, "http://ec2-34-208-181-152.us-west-2.compute.amazonaws.com/users/" + mobile, new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.i("TAG", response);
                                    getSharedPreferences("Login", Context.MODE_PRIVATE).edit().
                                            putString("UserInfo", response).putString("MobileNumber", mobile).
                                            putBoolean("first_login", true).
                                            putBoolean("Loginned", true).apply();

                                    getSharedPreferences("status", Context.MODE_PRIVATE).edit().
                                            putBoolean("first_login", true).apply();




                                    Log.i("TAG", response);
                                    Log.i("TAG", "Info" + getSharedPreferences("Login", Context.MODE_PRIVATE).getString("UserInfo", null));
                                    startActivity(new Intent(SigninActivity.this, MapsActivity.class));
                                    finish();


                                }
                            }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }));


                        } else {
                            Toast.makeText(SigninActivity.this, "Please enter correct credentials " + !response.body().getError(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<Resp> call, Throwable t) {

                    }
                });

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, RegisterActivity.class));

            }
        });


        // FACEBOOK LOGIN
//        fblogin = (LoginButton) findViewById(R.id.login_button);
//        fblogin.setReadPermissions(Arrays.asList(
//                "public_profile", "email", "user_birthday", "user_friends"));
//        callbackManager = CallbackManager.Factory.create();
//
//        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(final LoginResult loginResult) {
//                Log.d("enter", "4");
//
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//
//
//                                Log.v("LoginActivity", String.valueOf(loginResult.getAccessToken()));
//                                Log.d("fb_data", String.valueOf(object));
//
//                                try {// Application code
//
//                                    name = object.getString("name");
//                                    Log.d("FB NAME", name);
//
//                                    email = object.getString("email");
//                                    Log.d("FB NAME", email);
////
//                                    namea = name.trim().split("\\s+");
//                                    getSharedPreferences("fbdata", Context.MODE_PRIVATE).edit().
//                                            putBoolean("fbloginned", true)
//                                            .putString("first_name", namea[0])
//                                            .putString("last_name", namea[2])
//                                            .putString("email", email).apply();
//
//
////                                   // 01/31/1980 format
//                                } catch (JSONException e) {
//                                    Log.d("catch", e.toString());
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        });
//                Bundle parameters = new Bundle();
//
//                parameters.putString("fields", "id,name,email,gender,birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//
//
////                Toast.makeText(SigninActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                Log.d("Intent", "1");
//                Intent intent = new Intent(SigninActivity.this, RegisterActivity.class);
//                startActivity(intent);
//
//
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Log.v("LoginActivity", "cancel");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Log.v("LoginActivity", exception.getCause().toString());
//            }
//        });


//                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.d("enter", "1");
//
//                                Log.d("fbdata", String.valueOf(response));
//                                //   signInCheckInt=2;
//
//                                String profileImageUrl = ImageRequest.getProfilePictureUri(object.optString("id"), 500, 500).toString();
//                                SharedPreferences sharedPreferences = getSharedPreferences("FB_DATA", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putString("fbPic", profileImageUrl);
//                                Log.d("FB PIC", profileImageUrl);
//                                //       editor.putInt("GInt",signInCheckInt);
//
//                                try {
//
//                                    String fbname = object.getString("name");
//                                    Log.d("FB NAME", fbname);
//                                    String fbemail = loginResult.getAccessToken().getUserId();
//                                    editor.putString("fbEmail", fbemail);
//                                    Log.d("FB EMAIL", fbemail);
//                                    editor.putString("fbName", fbname);
//                                    editor.commit();
//
//                                } catch (JSONException e) {
//                                    Log.d("catch", e.toString());
//
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                Log.d("bundle", "2");
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,mobile,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//
//                Toast.makeText(SigninActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                Log.d("Intent", "1");
//                Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(SigninActivity.this, "Login Failed! Try Again.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//                Toast.makeText(SigninActivity.this, "Login Error! Check your internet or login again." + error.toString(),
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}