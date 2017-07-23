package com.halanx.tript.userapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.halanx.tript.userapp.Interfaces.DataInterface;
import com.halanx.tript.userapp.POJO.OrderInfo;
import com.halanx.tript.userapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.halanx.tript.userapp.GlobalAccess.djangoBaseUrl;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout ll1, ll2, ll3;

    String addressDetails, date, timings, starttime, endtime;

    Retrofit.Builder builder;
    Retrofit retrofit;
    DataInterface client;
    OrderInfo order;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).
                addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        client = retrofit.create(DataInterface.class);

        addressDetails = getIntent().getStringExtra("AddressDetails");
//        Log.i("TAG",addressDetails);
        Boolean isDelScheduled = getIntent().getBooleanExtra("deliveryScheduled", false);
        Log.d("date_change", String.valueOf(isDelScheduled));

        if (isDelScheduled) {
            date = String.valueOf(getIntent().getStringExtra("Date"));
            timings = String.valueOf(getIntent().getStringExtra("Timings"));

            Log.d("date_change",date);
            Log.d("date_change",timings);
            starttime = timings.substring(0, 5);
            endtime = timings.substring(6, 11);
        }

        ll1 = (LinearLayout) findViewById(R.id.ll_CreditDebitCard);
        ll2 = (LinearLayout) findViewById(R.id.ll_PayTM);
        ll3 = (LinearLayout) findViewById(R.id.ll_Cash);

        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_Cash:


            case R.id.ll_CreditDebitCard:

            case R.id.ll_PayTM:

                SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                long userMobile = Long.parseLong(sharedPreferences.getString("MobileNumber", null));

                SharedPreferences sharedPref = getSharedPreferences("location", Context.MODE_PRIVATE);
                float latitude = sharedPref.getFloat("latitudeDelivery",0);// LATITUDE
                float longitude = sharedPref.getFloat("longitudeDelivery",0);// LONGITUDE
                Log.d("latitudea",""+latitude);
                Log.d("longitude",""+longitude);

                if ((getIntent().getBooleanExtra("deliveryScheduled", false))) {

                    order = new OrderInfo(userMobile, addressDetails, date, starttime, endtime, null,latitude,longitude);
                    Log.d("done","done");
                } else if (!(getIntent().getBooleanExtra("deliveryScheduled", true))) {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    order = new OrderInfo(userMobile, addressDetails, date, null, null, "Delivery ASAP",latitude,longitude);
                }


                Log.i("ORDER", order.getDeliveryAddress() + order.getLatitude() + order.getLongitude());


//                pd = new ProgressDialog(PaymentActivity.this);
//                pd.setTitle("Please wait");
//                pd.setMessage("Posting your order");
//                pd.show();
                Call<OrderInfo> callOrder = client.postUserOrder(order);
                callOrder.enqueue(new Callback<OrderInfo>() {
                    @Override
                    public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {
                        Toast.makeText(PaymentActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
//                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(Call<OrderInfo> call, Throwable t) {
                        Log.d("TAG", "Order fail");
//                        pd.dismiss();
                    }
                });

                startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
                finish();

        }
    }
}
