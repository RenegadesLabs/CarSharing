package com.cardee.domain.bookings.entity;

import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.owner.entity.Image;

import java.util.Arrays;

public class Booking {

    private final Float totalAmount;
    private final String timeBegin;
    private final String timeEnd;
    private final Integer bookingId;
    private final String bookingNum;
    private final String note;
    private final Integer tankPart;
    private final Integer tankPartRentingOut;
    private final Integer fuelPolicyId;
    private final String fuelPolicyName;
    private final String dateCreated;
    private final String bookingStateName;
    private final BookingState bookingStateType;
    private final Integer carId;
    private final String manufactureYear;
    private final String plateNumber;
    private final String carTitle;
    private final Image[] images;
    private final Image primaryImage;
    private final Integer renterId;
    private final String renterName;
    private final String renterPhoto;
    private final Rate[] renterRates;
    private final Integer ownerId;
    private final String ownerName;
    private final String ownerPhoto;
    private final Rate[] ownerRates;
    private final String reviewFromRenter;
    private final String reviewFromOwner;
    private final String deliveryAddress;


    private Booking(Float totalAmount,
                    String timeBegin,
                    String timeEnd,
                    Integer bookingId,
                    String bookingNum,
                    String note,
                    Integer tankPart,
                    Integer tankPartRentingOut,
                    Integer fuelPolicyId,
                    String fuelPolicyName,
                    String dateCreated,
                    String bookingStateName,
                    BookingState bookingStateType,
                    Integer carId,
                    String manufactureYear,
                    String plateNumber,
                    String carTitle,
                    Image[] images,
                    Image primaryImage,
                    Integer renterId,
                    String renterName,
                    String renterPhoto,
                    Rate[] renterRates,
                    Integer ownerId,
                    String ownerName,
                    String ownerPhoto,
                    Rate[] ownerRates,
                    String reviewFromRenter,
                    String reviewFromOwner,
                    String deliveryAddress) {
        this.totalAmount = totalAmount;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.bookingId = bookingId;
        this.bookingNum = bookingNum;
        this.note = note;
        this.tankPart = tankPart;
        this.tankPartRentingOut = tankPartRentingOut;
        this.fuelPolicyId = fuelPolicyId;
        this.fuelPolicyName = fuelPolicyName;
        this.dateCreated = dateCreated;
        this.bookingStateName = bookingStateName;
        this.bookingStateType = bookingStateType;
        this.carId = carId;
        this.manufactureYear = manufactureYear;
        this.plateNumber = plateNumber;
        this.carTitle = carTitle;
        this.images = images;
        this.primaryImage = primaryImage;
        this.renterId = renterId;
        this.renterName = renterName;
        this.renterPhoto = renterPhoto;
        this.renterRates = renterRates;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerPhoto = ownerPhoto;
        this.ownerRates = ownerRates;
        this.reviewFromRenter = reviewFromRenter;
        this.reviewFromOwner = reviewFromOwner;
        this.deliveryAddress = deliveryAddress;
    }

    public static class Builder {
        private Float totalAmount;
        private String timeBegin;
        private String timeEnd;
        private Integer bookingId;
        private String bookingNum;
        private String note;
        private Integer tankPart;
        private Integer tankPartRentingOut;
        private Integer fuelPolicyId;
        private String fuelPolicyName;
        private String dateCreated;
        private String bookingStateName;
        private BookingState bookingStateType;
        private Integer carId;
        private String manufactureYear;
        private String plateNumber;
        private String carTitle;
        private Image[] images;
        private Image primaryImage;
        private Integer renterId;
        private String renterName;
        private String renterPhoto;
        private Rate[] renterRates;
        private Integer ownerId;
        private String ownerName;
        private String ownerPhoto;
        private Rate[] ownerRates;
        private String reviewFromRenter;
        private String reviewFromOwner;
        private String deliveryAddress;

        public Builder() {

        }

        public Builder setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setTimeBegin(String timeBegin) {
            this.timeBegin = timeBegin;
            return this;
        }

        public Builder setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
            return this;
        }

        public Builder setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder setBookingStateName(String bookingStateName) {
            this.bookingStateName = bookingStateName;
            return this;
        }

        public Builder setBookingStateType(BookingState bookingStateType) {
            this.bookingStateType = bookingStateType;
            return this;
        }

        public Builder setCarId(Integer carId) {
            this.carId = carId;
            return this;
        }

        public Builder setManufactureYear(String manufactureYear) {
            this.manufactureYear = manufactureYear;
            return this;
        }

        public Builder setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
            return this;
        }

        public Builder setCarTitle(String carTitle) {
            this.carTitle = carTitle;
            return this;
        }

        public Builder setImages(Image[] images) {
            this.images = images;
            return this;
        }

        public Builder setPrimaryImage(Image primaryImage) {
            this.primaryImage = primaryImage;
            return this;
        }

        public Builder setRenterName(String renterName) {
            this.renterName = renterName;
            return this;
        }

        public Builder setRenterPhoto(String renterPhoto) {
            this.renterPhoto = renterPhoto;
            return this;
        }

        public Builder setBookingNum(String bookingNum) {
            this.bookingNum = bookingNum;
            return this;
        }

        public Builder setNote(String note) {
            this.note = note;
            return this;
        }

        public Builder setTankPart(Integer tankPart) {
            this.tankPart = tankPart;
            return this;
        }

        public Builder setTankPartRentingOut(Integer tankPartRentingOut) {
            this.tankPartRentingOut = tankPartRentingOut;
            return this;
        }

        public Builder setFuelPolicyId(Integer fuelPolicyId) {
            this.fuelPolicyId = fuelPolicyId;
            return this;
        }

        public Builder setFuelPolicyName(String fuelPolicyName) {
            this.fuelPolicyName = fuelPolicyName;
            return this;
        }

        public Builder setRenterId(Integer renterId) {
            this.renterId = renterId;
            return this;
        }

        public Builder setRenterRates(Rate[] renterRates) {
            this.renterRates = renterRates;
            return this;
        }

        public Builder setOwnerId(Integer ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder setOwnerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public Builder setOwnerPhoto(String ownerPhoto) {
            this.ownerPhoto = ownerPhoto;
            return this;
        }

        public Builder setOwnerRates(Rate[] ownerRates) {
            this.ownerRates = ownerRates;
            return this;
        }

        public Builder setReviewFromRenter(String reviewFromRenter) {
            this.reviewFromRenter = reviewFromRenter;
            return this;
        }

        public Builder setReviewFromOwner(String reviewFromOwner) {
            this.reviewFromOwner = reviewFromOwner;
            return this;
        }

        public Builder setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Booking build() {
            return new Booking(totalAmount, timeBegin, timeEnd, bookingId, bookingNum, note,
                    tankPart, tankPartRentingOut, fuelPolicyId, fuelPolicyName, dateCreated,
                    bookingStateName, bookingStateType, carId, manufactureYear, plateNumber,
                    carTitle, images, primaryImage, renterId, renterName, renterPhoto, renterRates,
                    ownerId, ownerName, ownerPhoto, ownerRates, reviewFromRenter, reviewFromOwner,
                    deliveryAddress);
        }
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.setTotalAmount(totalAmount);
        builder.setTimeBegin(timeBegin);
        builder.setTimeEnd(timeEnd);
        builder.setBookingId(bookingId);
        builder.setBookingNum(bookingNum);
        builder.setNote(note);
        builder.setTankPart(tankPart);
        builder.setTankPartRentingOut(tankPartRentingOut);
        builder.setFuelPolicyId(fuelPolicyId);
        builder.setFuelPolicyName(fuelPolicyName);
        builder.setDateCreated(dateCreated);
        builder.setBookingStateName(bookingStateName);
        builder.setBookingStateType(bookingStateType);
        builder.setCarId(carId);
        builder.setManufactureYear(manufactureYear);
        builder.setPlateNumber(plateNumber);
        builder.setCarTitle(carTitle);
        builder.setImages(Arrays.copyOf(images, images.length));
        builder.setRenterId(renterId);
        builder.setRenterName(renterName);
        builder.setRenterPhoto(renterPhoto);
        builder.setRenterRates(renterRates);
        builder.setOwnerId(ownerId);
        builder.setOwnerName(ownerName);
        builder.setOwnerPhoto(ownerPhoto);
        builder.setOwnerRates(ownerRates);
        builder.setReviewFromRenter(reviewFromRenter);
        builder.setReviewFromOwner(reviewFromOwner);
        builder.setDeliveryAddress(deliveryAddress);
        return builder;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getBookingStateName() {
        return bookingStateName;
    }

    public BookingState getBookingStateType() {
        return bookingStateType;
    }

    public Integer getCarId() {
        return carId;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public Image[] getImages() {
        return images;
    }

    public Image getPrimaryImage() {
        return primaryImage;
    }

    public String getRenterName() {
        return renterName;
    }

    public String getRenterPhoto() {
        return renterPhoto;
    }

    public String getBookingNum() {
        return bookingNum;
    }

    public String getNote() {
        return note;
    }

    public Integer getTankPart() {
        return tankPart;
    }

    public Integer getTankPartRentingOut() {
        return tankPartRentingOut;
    }

    public Integer getFuelPolicyId() {
        return fuelPolicyId;
    }

    public String getFuelPolicyName() {
        return fuelPolicyName;
    }

    public Integer getRenterId() {
        return renterId;
    }

    public Rate[] getRenterRates() {
        return renterRates;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPhoto() {
        return ownerPhoto;
    }

    public Rate[] getOwnerRates() {
        return ownerRates;
    }

    public String getReviewFromRenter() {
        return reviewFromRenter;
    }

    public String getReviewFromOwner() {
        return reviewFromOwner;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
