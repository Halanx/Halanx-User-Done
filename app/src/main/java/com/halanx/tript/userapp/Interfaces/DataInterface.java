package com.halanx.tript.userapp.Interfaces;


import com.halanx.tript.userapp.POJO.CartItem;
import com.halanx.tript.userapp.POJO.CartItemPost;
import com.halanx.tript.userapp.POJO.CartsInfo;
import com.halanx.tript.userapp.POJO.OrderInfo;
import com.halanx.tript.userapp.POJO.ProductInfo;
import com.halanx.tript.userapp.POJO.Resp;
import com.halanx.tript.userapp.POJO.StoreInfo;
import com.halanx.tript.userapp.POJO.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by samarthgupta on 05/04/17.
 */

public interface DataInterface {

    //User Registration
    @FormUrlEncoded
    @POST("/NewRegUser/register.php/")
    Call<Resp> register(@Field("firstname") String firstName, @Field("lastname") String lastName,
                        @Field("email") String email, @Field("password") String password,
                        @Field("mobilenumber") String mobileNumber);

    //User Login
    @FormUrlEncoded
    @POST("/NewRegUser/login.php/")
    Call<Resp> login(@Field("mobilenumber") String mobileNumber, @Field("password") String password);

    //Register User On Django
    @POST("/users/")
    Call<UserInfo> putUserDataOnServer(@Body UserInfo userData);

    //Post user cart
    @POST("/carts/")
    Call<CartsInfo> putUserCartOnServer(@Body CartsInfo cart);

    //http://ec2-34-208-181-152.us-west-2.compute.amazonaws.com/carts/2222222222/active

    //Get User Cart Items where isRemoved is false
    @GET("/carts/{UserMobile}/active/")
    Call<List<CartItem>> getUserCartItems(@Path("UserMobile") String mobileNumber);

    //Showing products on home fragment
    @GET("/products/")
    Call<List<ProductInfo>> getAllProductsFromServer();

    //Get products according to the store ID
    @GET("/stores/{storeID}/products")
    Call<List<ProductInfo>> getProductsFromStore(@Path("storeID") String storeID);

    //Post cart item when add to cart clicked - while posting item is sent as an integer
    @POST("/carts/items/")
    Call<CartItemPost> putCartItemOnServer(@Body CartItemPost cartItem);

    //Get cart details for order total etc
    @GET("/carts/{mobileNumber}")
    Call<CartsInfo> getCartDetails(@Path("mobileNumber") String mobileNumber);

    //Posting an order
    @POST("/orders/")
    Call<OrderInfo> postUserOrder(@Body OrderInfo order);

    //Getting user orders
    @GET("/orders/user/{mobileNumber}/")
    Call<List<OrderInfo>> getUserOrders(@Path("mobileNumber") String mobileNumber);

    //Getting store list
    @GET("/stores/")
    Call<List<StoreInfo>> getStoreInfo();









//
//    @DELETE("/carts/items/{cartItemId}")
//    Call<CartsItems> deleteCartItemFromServer(@Path("cartItemId") String cartItemId);
//
//    @PUT("/carts/items/{cartItemId}")
//    Call<CartsItems> updateCartItemFromServer(@Path("cartItemId") String cartItemId,CartsItems cartsItem);
//




}
