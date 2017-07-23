package com.halanx.tript.userapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatchInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Earning")
    @Expose
    private Double earning;
    @SerializedName("ShopperId")
    @Expose
    private Integer shopperId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getEarning() {
        return earning;
    }

    public void setEarning(Double earning) {
        this.earning = earning;
    }

    public Integer getShopperId() {
        return shopperId;
    }

    public void setShopperId(Integer shopperId) {
        this.shopperId = shopperId;
    }

}

