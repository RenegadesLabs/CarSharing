package com.cardee.data_source.remote.api.booking.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingEntity {

    @Expose
    @SerializedName("amnt_total")
    private Integer totalAmount;
    @Expose
    @SerializedName("time_begin")
    private String timeBegin;
    @Expose
    @SerializedName("time_end")
    private String timeEnd;
    @Expose
    @SerializedName("booking_id")
    private Integer bookingId;
    @Expose
    @SerializedName("car_version")
    private BookingCar car;
    @Expose
    @SerializedName("date_created")
    private String dateCreated;
    @Expose
    @SerializedName("state_booking")
    private String bookingStateName;
    @Expose
    @SerializedName("state_type")
    private String bookingStateType;

    public BookingEntity(){

    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public BookingCar getCar() {
        return car;
    }

    public void setCar(BookingCar car) {
        this.car = car;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getBookingStateName() {
        return bookingStateName;
    }

    public void setBookingStateName(String bookingStateName) {
        this.bookingStateName = bookingStateName;
    }

    public String getBookingStateType() {
        return bookingStateType;
    }

    public void setBookingStateType(String bookingStateType) {
        this.bookingStateType = bookingStateType;
    }
}
