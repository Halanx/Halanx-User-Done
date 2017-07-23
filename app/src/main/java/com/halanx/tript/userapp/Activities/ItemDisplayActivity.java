package com.halanx.tript.userapp.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.halanx.tript.userapp.Interfaces.DataInterface;
import com.halanx.tript.userapp.POJO.CartItemPost;
import com.halanx.tript.userapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.halanx.tript.userapp.GlobalAccess.djangoBaseUrl;


public class ItemDisplayActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etQuantity;
    TextView plus, minus;
    Boolean already =false;
    Button cart;
    int i;
    String val;
    ImageView iv_fav, iv_productImage;
    TextView tv_productName, tv_productPrice;
    Boolean isFav;
    Integer productID;

    String productName, productFeatures, productImage;
    Double productPrice;
    List<CartItemPost> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //GET PRODUCT DATA VIA INTENT
        productName = getIntent().getStringExtra("Name");
        productPrice = getIntent().getDoubleExtra("Price", 0.0);
        productFeatures = getIntent().getStringExtra("Features");
        productImage = getIntent().getStringExtra("Image");
        productID = getIntent().getIntExtra("ID", -10);

        setTitle(productName);
        setContentView(R.layout.activity_item_display);

        etQuantity = (EditText) findViewById(R.id.quantity);
        etQuantity.setText("1");
        plus = (TextView) findViewById(R.id.increment);
        minus = (TextView) findViewById(R.id.decrement);
        cart = (Button) findViewById(R.id.bt_add_to_cart);
        iv_fav = (ImageView) findViewById(R.id.imgFav);


        iv_productImage = (ImageView) findViewById(R.id.product_image);
        tv_productName = (TextView) findViewById(R.id.item_name);
        tv_productPrice = (TextView) findViewById(R.id.item_price);


        if (!(productImage.isEmpty())) {
            Picasso.with(getApplicationContext()).load(productImage).into(iv_productImage);
        }
        else {
            Picasso.with(getApplicationContext()).load(R.drawable.fav_48).into(iv_productImage);
        }

        tv_productName.setText(productName);
        tv_productPrice.setText(Double.toString(productPrice));
        isFav = false;


        val = etQuantity.getText().toString();
        try {
            i = Integer.parseInt(val);
        } catch (NumberFormatException a) {
            a.printStackTrace();
        }

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        cart.setOnClickListener(this);
        iv_fav.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.increment:

                if(i<10){
                i++;
                val = Integer.toString(i);
                etQuantity.setText(val); }
                break;

            case R.id.decrement:
                if (i != 0) {
                    i--;
                    val = Integer.toString(i);
                    etQuantity.setText(val);
                }

                break;
            case R.id.bt_add_to_cart:

                if(!already){
                    already = true;

                    addCartItem();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Already added to cart",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.imgFav:

                if (!isFav) {
                    isFav = true;
                    Picasso.with(getApplicationContext()).load(R.drawable.fav_filled_48).into(iv_fav);
                    Toast.makeText(ItemDisplayActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                } else {
                    isFav = false;
                    Picasso.with(getApplicationContext()).load(R.drawable.fav_48).into(iv_fav);
                    Toast.makeText(ItemDisplayActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }


    void addCartItem() {

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String mobileNumber = sharedPreferences.getString("MobileNumber", null);

        CartItemPost item = new CartItemPost(Long.parseLong(mobileNumber), Double.parseDouble(val), productID,null);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).
                addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        DataInterface client = retrofit.create(DataInterface.class);

        Call<CartItemPost> call = client.putCartItemOnServer(item);
        call.enqueue(new Callback<CartItemPost>() {
            @Override
            public void onResponse(Call<CartItemPost> call, Response<CartItemPost> response) {


                Toast.makeText(ItemDisplayActivity.this, "Added item " + productName +"to your cart!", Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(Call<CartItemPost> call, Throwable t) {

            }
        });


    }




}
