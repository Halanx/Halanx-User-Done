package com.halanx.tript.userapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samarthgupta on 27/06/17.
 */

public class CartItemPost  {
    @SerializedName("Item")
    @Expose
    private Integer item;

    @SerializedName("CartPhoneNo")
    @Expose
    private long cartPhoneNo;

    @SerializedName("Quantity")
    @Expose
    private Double quantity;

    @SerializedName("Notes")
    @Expose
    private String notes;

    public CartItemPost(long cartPhoneNo, Double quantity, Integer item, String notes) {
        this.item = item;
        this.cartPhoneNo = cartPhoneNo;
        this.quantity = quantity;
        this.notes = notes;
    }
}