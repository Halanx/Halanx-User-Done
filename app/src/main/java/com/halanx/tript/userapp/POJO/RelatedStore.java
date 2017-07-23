package com.halanx.tript.userapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samarthgupta on 23/07/17.
 */

public class RelatedStore {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("StoreLogo")
    @Expose
    private String storeLogo;
}
