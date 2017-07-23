
package com.halanx.tript.userapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CartItem {

    //Check constructor

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Item")
    @Expose
    private ProductInfo item;
    @SerializedName("CartPhoneNo")
    @Expose
    private long cartPhoneNo;
    @SerializedName("RemovedFromCart")
    @Expose
    private Boolean removedFromCart;
    @SerializedName("IsOrdered")
    @Expose
    private Boolean isOrdered;
    @SerializedName("Quantity")
    @Expose
    private Double quantity;
    @SerializedName("SubTotal")
    @Expose
    private Double subTotal;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("Cart")
    @Expose
    private Integer cart;
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;

    @SerializedName("CartUser")
    @Expose
    private UserInfo cartUser;

    @SerializedName("BatchId")
    @Expose
    private Integer batchId;

    public Boolean getOrdered() {
        return isOrdered;
    }

    public void setOrdered(Boolean ordered) {
        isOrdered = ordered;
    }

    public UserInfo getCartUser() {
        return cartUser;
    }

    public void setCartUser(UserInfo cartUser) {
        this.cartUser = cartUser;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public CartItem(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductInfo getItem() {
        return item;
    }

    public void setItem(ProductInfo item) {
        this.item = item;
    }

    public long getCartPhoneNo() {
        return cartPhoneNo;
    }

    public void setCartPhoneNo(long cartPhoneNo) {
        this.cartPhoneNo = cartPhoneNo;
    }

    public Boolean getRemovedFromCart() {
        return removedFromCart;
    }

    public void setRemovedFromCart(Boolean removedFromCart) {
        this.removedFromCart = removedFromCart;
    }

    public Boolean getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}




