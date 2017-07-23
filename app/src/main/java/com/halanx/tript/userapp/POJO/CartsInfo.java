
package com.halanx.tript.userapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartsInfo {

    //check
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("UserPhone")
    @Expose
    private Double userPhone;
    @SerializedName("DeliveryCharges")
    @Expose
    private Double deliveryCharges;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("Username")
    @Expose
    private Integer username;

    public CartsInfo(Double userPhone) {
        this.userPhone = userPhone;
    }

    public CartsInfo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Double userPhone) {
        this.userPhone = userPhone;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

}