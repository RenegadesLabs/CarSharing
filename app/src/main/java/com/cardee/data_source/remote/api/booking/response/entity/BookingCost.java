package com.cardee.data_source.remote.api.booking.response.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingCost {

    @Expose
    @SerializedName("fee")
    private Float fee;
    @Expose
    @SerializedName("non_peak_cost")
    private Float nonPeakCost;
    @Expose
    @SerializedName("peak_cost")
    private Float peakCost;
    @Expose
    @SerializedName("peak_cnt")
    private Integer peakCount;
    @Expose
    @SerializedName("non_peak_cnt")
    private Integer nonPeakCount;
    @Expose
    @SerializedName("delivery")
    private Float delivery;
    @Expose
    @SerializedName("discount")
    private Float discount;
    @Expose
    @SerializedName("amount_total")
    private Float total;

    public BookingCost() {

    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Float getNonPeakCost() {
        return nonPeakCost;
    }

    public void setNonPeakCost(Float nonPeakCost) {
        this.nonPeakCost = nonPeakCost;
    }

    public Float getPeakCost() {
        return peakCost;
    }

    public void setPeakCost(Float peakCost) {
        this.peakCost = peakCost;
    }

    public Integer getPeakCount() {
        return peakCount;
    }

    public void setPeakCount(Integer peakCount) {
        this.peakCount = peakCount;
    }

    public Integer getNonPeakCount() {
        return nonPeakCount;
    }

    public void setNonPeakCount(Integer nonPeakCount) {
        this.nonPeakCount = nonPeakCount;
    }

    public Float getDelivery() {
        return delivery;
    }

    public void setDelivery(Float delivery) {
        this.delivery = delivery;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
