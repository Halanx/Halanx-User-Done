package com.halanx.tript.userapp.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.halanx.tript.userapp.Adapters.CartsAdapter;
import com.halanx.tript.userapp.Interfaces.DataInterface;
import com.halanx.tript.userapp.POJO.CartItem;
import com.halanx.tript.userapp.POJO.CartsInfo;
import com.halanx.tript.userapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.halanx.tript.userapp.GlobalAccess.djangoBaseUrl;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    CartsAdapter adapterTemp;

    LinearLayout detailslayout, orderslayout, final_detail;

    Button btnCheckout, btnDelivery, btnconfirm;
    TextView tvSubtotal, tvTotal, tvDelivery, totalitems;

    Button btDelAsap, btDelSchedule, btAddDetails, btAddLocate;
    Boolean delivery_scheduled = false, delivery_address = false;


    ProgressBar progressBar;

    Retrofit.Builder builder;
    Retrofit retrofit;
    DataInterface client;

    List<CartItem> activeItems;
    AlertDialog.Builder AlertBuilder;
    boolean delivery = false;

    String date, timings;

    String addressDetails;
    EditText line1,line2,line3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).
                addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        client = retrofit.create(DataInterface.class);


        btDelAsap = (Button) findViewById(R.id.bt_delivery_asap);
        btDelSchedule = (Button) findViewById(R.id.bt_delivery_schedule);
        btAddDetails = (Button) findViewById(R.id.bt_address_details);
        btAddLocate = (Button) findViewById(R.id.bt_address_locate);

        btnDelivery = (Button) findViewById(R.id.details);

        btnCheckout = (Button) findViewById(R.id.checkout);
        orderslayout = (LinearLayout) findViewById(R.id.orders);
        detailslayout = (LinearLayout) findViewById(R.id.detail);
        final_detail = (LinearLayout) findViewById(R.id.confirm_detail);

        btDelAsap.setOnClickListener(this);
        btDelSchedule.setOnClickListener(this);
        btAddLocate.setOnClickListener(this);
        btAddDetails.setOnClickListener(this);
        btnCheckout.setOnClickListener(this);
        btnDelivery.setOnClickListener(this);


        tvSubtotal = (TextView) findViewById(R.id.tv_cart_subtotal);
        tvTotal = (TextView) findViewById(R.id.tv_cart_total);
        tvDelivery = (TextView) findViewById(R.id.tv_cart_deliverycharge);
        totalitems = (TextView) findViewById(R.id.total_items);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cart);

        btnconfirm = (Button) findViewById(R.id.confirm_details);
        btnconfirm.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);
        btnCheckout.setOnClickListener(this);
        // btnDelivery.setOnClickListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String mobileNumber = sharedPreferences.getString("MobileNumber", null);


        Call<List<CartItem>> call = client.getUserCartItems(mobileNumber);

        call.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {

                List<CartItem> items = response.body();

                if (!items.isEmpty()) {

                    activeItems = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {

                        if (!items.get(i).getRemovedFromCart()) {
                            activeItems.add(items.get(i));
                        }
                    }

                    totalitems.setText(String.valueOf(items.size()));

                    //Displaying carts
                    Log.d("TAG", "If");
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
                    adapterTemp = new CartsAdapter(activeItems, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setAdapter(adapterTemp);
                    recyclerView.setLayoutManager(layoutManager);


                    Call<CartsInfo> callCart = client.getCartDetails(mobileNumber);
                    callCart.enqueue(new Callback<CartsInfo>() {
                        @Override
                        public void onResponse(Call<CartsInfo> call, Response<CartsInfo> response) {
                            CartsInfo cart = response.body();

                            String total = cart.getTotal().toString();
                            String del = cart.getDeliveryCharges().toString();


                            tvTotal.setText(total);
                            tvDelivery.setText(del);



                        }

                        @Override
                        public void onFailure(Call<CartsInfo> call, Throwable t) {

                        }
                    });

                } else {
                    progressBar.setVisibility(View.INVISIBLE);


                    AlertBuilder = new AlertDialog.Builder(CartActivity.this);
                    AlertBuilder.setMessage("You have no items in your carts!");
                    AlertBuilder.setCancelable(false);

                    AlertBuilder.setPositiveButton(
                            "Go back",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });


                    AlertDialog alert = AlertBuilder.create();
                    alert.show();

                }


            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                date = data.getStringExtra("date");
                timings = data.getStringExtra("time_selected");

                delivery_scheduled = true;
                Log.d("timingsdata", String.valueOf(date));
                Log.d("datedata", String.valueOf(timings));

            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_delivery_asap:


                delivery = true;
                delivery_scheduled = false;
                btDelAsap.setBackground(getDrawable(R.color.red));
                btDelSchedule.setBackground(getDrawable(R.drawable.my_button_bg));


                break;

            case R.id.bt_delivery_schedule:

                delivery = true;
                btDelAsap.setBackground(getDrawable(R.drawable.my_button_bg));
                btDelSchedule.setBackground(getDrawable(R.color.red));
                Intent intent = new Intent(CartActivity.this, ScheduleActivity.class);

                startActivityForResult(intent, 1);

                break;

            case R.id.bt_address_details:
                delivery_address = true;
                final Dialog dialog = new Dialog(CartActivity.this);
                dialog.setContentView(R.layout.layout_custom_alert_dialogue);


                line1 = (EditText) dialog.findViewById(R.id.et1_dialogue);
                line2 = (EditText) dialog.findViewById(R.id.et2_dialogue);
                line3 = (EditText) dialog.findViewById(R.id.et3_dialogue);
                Button proceed = (Button) dialog.findViewById(R.id.btProceed_dialogue);
                Button cancel = (Button) dialog.findViewById(R.id.btCancel_dialogue);

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(line1.getText().equals("")||line2.getText().equals("")||line3.getText().equals("")){

                            Toast.makeText(getApplicationContext(), "Enter Your Address", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Address Details Saved", Toast.LENGTH_SHORT).show();
                            addressDetails = line1.getText().toString() + ", " + line2.getText().toString() + ", " + line3.getText().toString();
                            Log.d("TAG", addressDetails);
                            dialog.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                dialog.show();


                break;
            case R.id.bt_address_locate:

                Intent intentMap = new Intent(CartActivity.this, MapsActivity.class);
                intentMap.putExtra("fromCart",true);
                startActivity(intentMap);
                break;
            case R.id.checkout:


                Intent intentCheckout = new Intent(CartActivity.this, PaymentActivity.class);
                intentCheckout.putExtra("AddressDetails", addressDetails);
                if (delivery_scheduled) {
                    intentCheckout.putExtra("Date", date);
                    intentCheckout.putExtra("Timings", timings);
                }

                intentCheckout.putExtra("deliveryScheduled", delivery_scheduled);
                startActivity(intentCheckout);
                finish();
                break;
            case R.id.details: {
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

                if (detailslayout.getVisibility() == View.GONE) {

                    detailslayout.startAnimation(slideUp);
                    detailslayout.setVisibility(View.VISIBLE);
                    orderslayout.setVisibility(View.GONE);

                    btnDelivery.setVisibility(View.GONE);
                    btnconfirm.setVisibility(View.VISIBLE);

                }

                break;


            }
            case R.id.confirm_details:

                if (!delivery) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setMessage("Please select a delivery time");
                    builder.setCancelable(true);
                    AlertDialog dial = builder.create();
                    dial.show();
                    return;
                }

                if (!delivery_address) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setMessage("Please select a delivery address");
                    builder.setCancelable(true);
                    AlertDialog dial = builder.create();
                    dial.show();
                    return;
                }


                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

                if (final_detail.getVisibility() == View.GONE) {

                    final_detail.startAnimation(slideUp);
                    final_detail.setVisibility(View.VISIBLE);
                    detailslayout.setVisibility(View.GONE);
                    btnDelivery.setVisibility(View.GONE);
                    btnCheckout.setVisibility(View.VISIBLE);
                }
        }
    }
}






