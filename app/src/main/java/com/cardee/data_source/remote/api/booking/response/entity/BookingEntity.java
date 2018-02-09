package com.cardee.data_source.remote.api.booking.response.entity;


import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.data_source.remote.api.reviews.response.entity.Renter;
import com.cardee.domain.bookings.entity.Booking;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingEntity {

    @Expose
    @SerializedName("amnt_total")
    private Float totalAmount;
    @Expose
    @SerializedName("time_begin")
    private String timeBegin;
    @Expose
    @SerializedName("time_end")
    private String timeEnd;
    @SerializedName("booking_num")
    @Expose
    private String bookingNum;
    @SerializedName("note")
    @Expose
    private String note;
    @Expose
    @SerializedName("booking_id")
    private Integer bookingId;
    @SerializedName("tank_part")
    @Expose
    private Float tankPart;
    @SerializedName("tank_part_renting_out")
    @Expose
    private Float tankPartRentingOut;
    @SerializedName("master_mileage")
    @Expose
    private Object masterMileage;
    @SerializedName("fuel_policy")
    @Expose
    private FuelPolicyEntity mFuelPolicyEntity;
    @Expose
    @SerializedName("car_version")
    private BookingCar car;
    @SerializedName("owner")
    @Expose
    private OwnerProfile owner;
    @SerializedName("owner_rate")
    @Expose
    private List<OwnerRateEntity> ownerRate = null;
    @SerializedName("renter")
    @Expose
    private Renter renter;
    @SerializedName("renter_rate")
    @Expose
    private List<RenterRateEntity> renterRate = null;
    @SerializedName("review_from_renter")
    @Expose
    private String reviewFromRenter;
    @SerializedName("review_from_owner")
    @Expose
    private String reviewFromOwner;
    @Expose
    @SerializedName("date_created")
    private String dateCreated;
    @Expose
    @SerializedName("state_booking")
    private String bookingStateName;
    @Expose
    @SerializedName("state_type")
    private String bookingStateType;
    @SerializedName("address_delivery")
    @Expose
    private String addressDelivery;
    @Expose
    @SerializedName("cost_breakdown")
    private BookingCost cost;
    @Expose
    @SerializedName("is_booking_by_day")
    private Boolean bookingByDay;


    public BookingEntity(){

    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
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

    public String getBookingNum() {
        return bookingNum;
    }

    public void setBookingNum(String bookingNum) {
        this.bookingNum = bookingNum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getTankPart() {
        return tankPart;
    }

    public void setTankPart(Float tankPart) {
        this.tankPart = tankPart;
    }

    public Float getTankPartRentingOut() {
        return tankPartRentingOut;
    }

    public void setTankPartRentingOut(Float tankPartRentingOut) {
        this.tankPartRentingOut = tankPartRentingOut;
    }

    public Object getMasterMileage() {
        return masterMileage;
    }

    public void setMasterMileage(Object masterMileage) {
        this.masterMileage = masterMileage;
    }

    public FuelPolicyEntity getFuelPolicyEntity() {
        return mFuelPolicyEntity;
    }

    public void setFuelPolicyEntity(FuelPolicyEntity fuelPolicyEntity) {
        mFuelPolicyEntity = fuelPolicyEntity;
    }

    public OwnerProfile getOwner() {
        return owner;
    }

    public void setOwner(OwnerProfile owner) {
        this.owner = owner;
    }

    public List<OwnerRateEntity> getOwnerRate() {
        return ownerRate;
    }

    public void setOwnerRate(List<OwnerRateEntity> ownerRate) {
        this.ownerRate = ownerRate;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public List<RenterRateEntity> getRenterRate() {
        return renterRate;
    }

    public void setRenterRate(List<RenterRateEntity> renterRate) {
        this.renterRate = renterRate;
    }

    public String getReviewFromRenter() {
        return reviewFromRenter;
    }

    public void setReviewFromRenter(String reviewFromRenter) {
        this.reviewFromRenter = reviewFromRenter;
    }

    public String getReviewFromOwner() {
        return reviewFromOwner;
    }

    public void setReviewFromOwner(String reviewFromOwner) {
        this.reviewFromOwner = reviewFromOwner;
    }

    public String getAddressDelivery() {
        return addressDelivery;
    }

    public void setAddressDelivery(String addressDelivery) {
        this.addressDelivery = addressDelivery;
    }

    public BookingCost getCost() {
        return cost;
    }

    public void setCost(BookingCost cost) {
        this.cost = cost;
    }

    public Boolean getBookingByDay() {
        return bookingByDay;
    }

    public void setBookingByDay(Boolean bookingByDay) {
        this.bookingByDay = bookingByDay;
    }
}
