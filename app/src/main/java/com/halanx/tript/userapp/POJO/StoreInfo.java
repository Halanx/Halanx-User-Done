package com.halanx.tript.userapp.POJO;

/**
 * Created by samarthgupta on 14/07/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("StoreAddress")
    @Expose
    private String storeAddress;
    @SerializedName("StoreLogo")
    @Expose
    private String storeLogo;
    @SerializedName("Dealer_FirstName")
    @Expose
    private String dealerFirstName;
    @SerializedName("Dealer_LastName")
    @Expose
    private String dealerLastName;
    @SerializedName("Dealer_ContactNo")
    @Expose
    private long dealerContactNo;
    @SerializedName("Dealer_EmailId")
    @Expose
    private String dealerEmailId;
    @SerializedName("Dealer_password")
    @Expose
    private String dealerPassword;
    @SerializedName("Dealer_Designation")
    @Expose
    private String dealerDesignation;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("CompanyLegalName")
    @Expose
    private String companyLegalName;
    @SerializedName("PANNumber")
    @Expose
    private String pANNumber;
    @SerializedName("BankAccountNumber")
    @Expose
    private String bankAccountNumber;
    @SerializedName("BankAccountType")
    @Expose
    private String bankAccountType;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("BankBranch")
    @Expose
    private String bankBranch;
    @SerializedName("BankBranchAddress")
    @Expose
    private String bankBranchAddress;
    @SerializedName("IFSCCode")
    @Expose
    private String iFSCCode;
    @SerializedName("MondayOpeningTime")
    @Expose
    private String mondayOpeningTime;
    @SerializedName("TuesdayOpeningTime")
    @Expose
    private String tuesdayOpeningTime;
    @SerializedName("WednesdayOpeningTime")
    @Expose
    private String wednesdayOpeningTime;
    @SerializedName("ThursdayOpeningTime")
    @Expose
    private String thursdayOpeningTime;
    @SerializedName("FridayOpeningTime")
    @Expose
    private String fridayOpeningTime;
    @SerializedName("SaturdayOpeningTime")
    @Expose
    private String saturdayOpeningTime;
    @SerializedName("SundayOpeningTime")
    @Expose
    private String sundayOpeningTime;
    @SerializedName("MondayClosingTime")
    @Expose
    private String mondayClosingTime;
    @SerializedName("TuesdayClosingTime")
    @Expose
    private String tuesdayClosingTime;
    @SerializedName("WednesdayClosingTime")
    @Expose
    private String wednesdayClosingTime;
    @SerializedName("ThursdayClosingTime")
    @Expose
    private String thursdayClosingTime;
    @SerializedName("FridayClosingTime")
    @Expose
    private String fridayClosingTime;
    @SerializedName("SaturdayClosingTime")
    @Expose
    private String saturdayClosingTime;
    @SerializedName("SundayClosingTime")
    @Expose
    private String sundayClosingTime;
    @SerializedName("Active")
    @Expose
    private Boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getDealerFirstName() {
        return dealerFirstName;
    }

    public void setDealerFirstName(String dealerFirstName) {
        this.dealerFirstName = dealerFirstName;
    }

    public String getDealerLastName() {
        return dealerLastName;
    }

    public void setDealerLastName(String dealerLastName) {
        this.dealerLastName = dealerLastName;
    }

    public long getDealerContactNo() {
        return dealerContactNo;
    }

    public void setDealerContactNo(long dealerContactNo) {
        this.dealerContactNo = dealerContactNo;
    }

    public String getDealerEmailId() {
        return dealerEmailId;
    }

    public void setDealerEmailId(String dealerEmailId) {
        this.dealerEmailId = dealerEmailId;
    }

    public String getDealerPassword() {
        return dealerPassword;
    }

    public void setDealerPassword(String dealerPassword) {
        this.dealerPassword = dealerPassword;
    }

    public String getDealerDesignation() {
        return dealerDesignation;
    }

    public void setDealerDesignation(String dealerDesignation) {
        this.dealerDesignation = dealerDesignation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCompanyLegalName() {
        return companyLegalName;
    }

    public void setCompanyLegalName(String companyLegalName) {
        this.companyLegalName = companyLegalName;
    }

    public String getpANNumber() {
        return pANNumber;
    }

    public void setpANNumber(String pANNumber) {
        this.pANNumber = pANNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankBranchAddress() {
        return bankBranchAddress;
    }

    public void setBankBranchAddress(String bankBranchAddress) {
        this.bankBranchAddress = bankBranchAddress;
    }

    public String getiFSCCode() {
        return iFSCCode;
    }

    public void setiFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public String getMondayOpeningTime() {
        return mondayOpeningTime;
    }

    public void setMondayOpeningTime(String mondayOpeningTime) {
        this.mondayOpeningTime = mondayOpeningTime;
    }

    public String getTuesdayOpeningTime() {
        return tuesdayOpeningTime;
    }

    public void setTuesdayOpeningTime(String tuesdayOpeningTime) {
        this.tuesdayOpeningTime = tuesdayOpeningTime;
    }

    public String getWednesdayOpeningTime() {
        return wednesdayOpeningTime;
    }

    public void setWednesdayOpeningTime(String wednesdayOpeningTime) {
        this.wednesdayOpeningTime = wednesdayOpeningTime;
    }

    public String getThursdayOpeningTime() {
        return thursdayOpeningTime;
    }

    public void setThursdayOpeningTime(String thursdayOpeningTime) {
        this.thursdayOpeningTime = thursdayOpeningTime;
    }

    public String getFridayOpeningTime() {
        return fridayOpeningTime;
    }

    public void setFridayOpeningTime(String fridayOpeningTime) {
        this.fridayOpeningTime = fridayOpeningTime;
    }

    public String getSaturdayOpeningTime() {
        return saturdayOpeningTime;
    }

    public void setSaturdayOpeningTime(String saturdayOpeningTime) {
        this.saturdayOpeningTime = saturdayOpeningTime;
    }

    public String getSundayOpeningTime() {
        return sundayOpeningTime;
    }

    public void setSundayOpeningTime(String sundayOpeningTime) {
        this.sundayOpeningTime = sundayOpeningTime;
    }

    public String getMondayClosingTime() {
        return mondayClosingTime;
    }

    public void setMondayClosingTime(String mondayClosingTime) {
        this.mondayClosingTime = mondayClosingTime;
    }

    public String getTuesdayClosingTime() {
        return tuesdayClosingTime;
    }

    public void setTuesdayClosingTime(String tuesdayClosingTime) {
        this.tuesdayClosingTime = tuesdayClosingTime;
    }

    public String getWednesdayClosingTime() {
        return wednesdayClosingTime;
    }

    public void setWednesdayClosingTime(String wednesdayClosingTime) {
        this.wednesdayClosingTime = wednesdayClosingTime;
    }

    public String getThursdayClosingTime() {
        return thursdayClosingTime;
    }

    public void setThursdayClosingTime(String thursdayClosingTime) {
        this.thursdayClosingTime = thursdayClosingTime;
    }

    public String getFridayClosingTime() {
        return fridayClosingTime;
    }

    public void setFridayClosingTime(String fridayClosingTime) {
        this.fridayClosingTime = fridayClosingTime;
    }

    public String getSaturdayClosingTime() {
        return saturdayClosingTime;
    }

    public void setSaturdayClosingTime(String saturdayClosingTime) {
        this.saturdayClosingTime = saturdayClosingTime;
    }

    public String getSundayClosingTime() {
        return sundayClosingTime;
    }

    public void setSundayClosingTime(String sundayClosingTime) {
        this.sundayClosingTime = sundayClosingTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}