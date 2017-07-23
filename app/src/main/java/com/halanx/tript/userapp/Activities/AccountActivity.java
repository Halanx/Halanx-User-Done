package com.halanx.tript.userapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.halanx.tript.userapp.POJO.UserInfo;
import com.halanx.tript.userapp.R;

public class AccountActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    UserInfo user;
    TextView tvFirstName, tvLastName, tvEmail, tvMobile, signout;
    EditText tvAddress;
    String mobileNumber;


    ImageView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvFirstName = (TextView) findViewById(R.id.tv_firstName_useraccount);
        tvLastName = (TextView) findViewById(R.id.tv_lastName_user_account);
        tvEmail = (TextView) findViewById(R.id.tv_email_user_account);
        tvAddress = (EditText) findViewById(R.id.tv_address_user_account);
        tvMobile = (TextView) findViewById(R.id.tv_mobile_user_account);
        edit = (ImageView) findViewById(R.id.edit);

        signout = (TextView) findViewById(R.id.signout);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAddress.setCursorVisible(true);
            }
        });


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
