

package com.halanx.tript.userapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Check
public class ProductInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("ProductImage")
    @Expose
    private String productImage;
    @SerializedName("Features")
    @Expose
    private String features;
    @SerializedName("ProductSize")
    @Expose
    private Integer productSize;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;

    @SerializedName("RelatedStore")
    @Expose
    private RelatedStore relatedStore;

    public RelatedStore getRelatedStore() {
        return relatedStore;
    }

    public void setRelatedStore(RelatedStore relatedStore) {
        this.relatedStore = relatedStore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Integer getProductSize() {
        return productSize;
    }

    public void setProductSize(Integer productSize) {
        this.productSize = productSize;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}