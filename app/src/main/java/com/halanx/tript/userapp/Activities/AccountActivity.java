package com.halanx.tript.userapp.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.halanx.tript.userapp.POJO.UserInfo;
import com.halanx.tript.userapp.R;

public class AccountActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    UserInfo user;
    TextView tvFirstName, tvLastName, tvEmail, tvMobile, signout;
    EditText tvAddress;
    String mobileNumber;
    EditText line1,line2,line3;

    String addressDetails;

    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvFirstName = (TextView) findViewById(R.id.tv_firstName_useraccount);
        tvLastName = (TextView) findViewById(R.id.tv_lastName_user_account);
        tvEmail = (TextView) findViewById(R.id.tv_email_user_account);
        tvAddress = (EditText) findViewById(R.id.tv_address_user_account);
        tvMobile = (TextView) findViewById(R.id.tv_mobile_user_account);
        edit = (Button) findViewById(R.id.edit);

        signout = (TextView) findViewById(R.id.signout);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AccountActivity.this);
                dialog.setContentView(R.layout.layout_custom_alert_dialogue);


                line1 = (EditText) dialog.findViewById(R.id.et1_dialogue);
                line2 = (EditText) dialog.findViewById(R.id.et2_dialogue);
                line3 = (EditText) dialog.findViewById(R.id.et3_dialogue);
                Button proceed = (Button) dialog.findViewById(R.id.btProceed_dialogue);
                Button cancel = (Button) dialog.findViewById(R.id.btCancel_dialogue);

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (line1.getText().equals(" ") || line2.getText().equals(" ") || line3.getText().equals(" ")) {

                            Toast.makeText(getApplicationContext(), "Enter Your Address", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Address Details Saved", Toast.LENGTH_SHORT).show();
                            addressDetails = line1.getText().toString() + ", " + line2.getText().toString() + ", " + line3.getText().toString();
                            Log.d("TAG", addressDetails);
                            dialog.dismiss();
                        }
                    }
                });
                tvAddress.setText(addressDetails);

            }});


        String userInfo = getSharedPreferences("Login", Context.MODE_PRIVATE).getString("UserInfo", null);
        UserInfo user = new GsonBuilder().create().fromJson(userInfo, UserInfo.class);



        tvFirstName.setText(user.getFirstName());
        tvLastName.setText(user.getLastName());
        tvEmail.setText(user.getEmailId());
        tvMobile.setText(Long.toString(user.getPhoneNo()));
        tvAddress.setText(user.getAddress());


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences("Login", Context.MODE_PRIVATE).edit().
                        putBoolean("Loginned", false).remove("MobileNumber")
                        .remove("UserInfo").apply();

                startActivity(new Intent(AccountActivity.this, SigninActivity.class));
                finish();
            }
        });


//
//        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
//        mobileNumber = sharedPreferences.getString("MobileNumber", null);
//Log.i("TAG","User "+ userInfo + mobileNumber);
//        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ec2-34-208-181-152.us-west-2.compute.amazonaws.com/").
//                addConverterFactory(GsonConverterFactory.create());
//        Retrofit retrofit = builder.build();
//        DataInterface client = retrofit.create(DataInterface.class);
//        Call<UserInfo> call = client.getUserData(mobileNumber);
//        call.enqueue(new Callback<UserInfo>() {
//            @Override
//            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
//                user = response.body();
//
//                if (user.getEmailId() != null) {
//                    tvFirstName.setText(user.getFirstName());
//                    tvLastName.setText(user.getLastName());
//                    tvEmail.setText(user.getEmailId());
//                    tvMobile.setText(mobileNumber);
//                    tvAddress.setText(user.getAddress());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserInfo> call, Throwable t) {
//
//            }
//        });

    }
}
