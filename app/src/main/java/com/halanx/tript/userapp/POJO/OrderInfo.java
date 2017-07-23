
package com.halanx.tript.userapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OrderInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_items")
    @Expose
    private List<CartItem> orderItems = null;
    @SerializedName("CustomerPhoneNo")
    @Expose
    private long customerPhoneNo;
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("DeliveryCharges")
    @Expose
    private Double deliveryCharges;
    @SerializedName("PlacingTime")
    @Expose
    private String placingTime;
    @SerializedName("DeliveryAddress")
    @Expose
    private String deliveryAddress;
    @SerializedName("Earnings")
    @Expose
    private Double earnings;
    @SerializedName("Latitude")
    @Expose
    private float latitude;
    @SerializedName("Longitude")
    @Expose
    private float longitude;
    @SerializedName("UserRating")
    @Expose
    private Double userRating;
    @SerializedName("ShopperRating")
    @Expose
    private Double shopperRating;
    @SerializedName("IsDelivered")
    @Expose
    private Boolean isDelivered;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("PriorityScore")
    @Expose
    private Integer priorityScore;

    public OrderInfo(long customerPhoneNo, String deliveryAddress, String deliveryDate, String startTime, String endTime, String notes, float lat, float lon) {
        this.customerPhoneNo = customerPhoneNo;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        latitude = lat;
        longitude = lon;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    public long getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(long customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getPlacingTime() {
        return placingTime;
    }

    public void setPlacingTime(String placingTime) {
        this.placingTime = placingTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Double getEarnings() {
        return earnings;
    }

    public void setEarnings(Double earnings) {
        this.earnings = earnings;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public Double getShopperRating() {
        return shopperRating;
    }

    public void setShopperRating(Double shopperRating) {
        this.shopperRating = shopperRating;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(Integer priorityScore) {
        this.priorityScore = priorityScore;
    }

}



