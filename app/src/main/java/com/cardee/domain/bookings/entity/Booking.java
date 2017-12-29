package com.cardee.domain.bookings.entity;

import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.owner.entity.Image;

import java.util.Arrays;

public class Booking {

    private final Integer totalAmount;
    private final String timeBegin;
    private final String timeEnd;
    private final Integer bookingId;
    private final String dateCreated;
    private final String bookingStateName;
    private final BookingState bookingStateType;
    private final Integer carId;
    private final String manufactureYear;
    private final String plateNumber;
    private final String carTitle;
    private final Image[] images;
    private final Image primaryImage;
    private final String renterName;
    private final String renterPhoto;
    private final String ownerPhoto;
    private final Integer ownerId;

    private Booking(Integer totalAmount,
                    String timeBegin,
                    String timeEnd,
                    Integer bookingId,
                    String dateCreated,
                    String bookingStateName,
                    BookingState bookingStateType,
                    Integer carId,
                    String manufactureYear,
                    String plateNumber,
                    String carTitle,
                    Image[] images,
                    Image primaryImage,
                    String renterName,
                    String renterPhoto,
                    String ownerPhoto,
                    Integer ownerId) {
        this.totalAmount = totalAmount;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.bookingId = bookingId;
        this.dateCreated = dateCreated;
        this.bookingStateName = bookingStateName;
        this.bookingStateType = bookingStateType;
        this.carId = carId;
        this.manufactureYear = manufactureYear;
        this.plateNumber = plateNumber;
        this.carTitle = carTitle;
        this.images = images;
        this.primaryImage = primaryImage;
        this.renterName = renterName;
        this.renterPhoto = renterPhoto;
        this.ownerPhoto = ownerPhoto;
        this.ownerId = ownerId;
    }

    public static class Builder {
        private Integer totalAmount;
        private String timeBegin;
        private String timeEnd;
        private Integer bookingId;
        private String dateCreated;
        private String bookingStateName;
        private BookingState bookingStateType;
        private Integer carId;
        private String manufactureYear;
        private String plateNumber;
        private String carTitle;
        private Image[] images;
        private Image primaryImage;
        private String renterName;
        private String renterPhoto;
        private String ownerPhoto;
        private Integer ownerId;

        public Builder() {

        }

        public Builder setTotalAmount(Integer totalAmount) {
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

        public Builder setOwnerPhoto(String ownerPhoto) {
            this.ownerPhoto = ownerPhoto;
            return this;
        }

        public Builder setOwnerId(Integer ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Booking build() {
            return new Booking(totalAmount, timeBegin, timeEnd, bookingId, dateCreated,
                    bookingStateName, bookingStateType, carId, manufactureYear, plateNumber,
                    carTitle, images, primaryImage, renterName, renterPhoto, ownerPhoto, ownerId);
        }
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.setTotalAmount(totalAmount);
        builder.setTimeBegin(timeBegin);
        builder.setTimeEnd(timeEnd);
        builder.setBookingId(bookingId);
        builder.setDateCreated(dateCreated);
        builder.setBookingStateName(bookingStateName);
        builder.setBookingStateType(bookingStateType);
        builder.setCarId(carId);
        builder.setManufactureYear(manufactureYear);
        builder.setPlateNumber(plateNumber);
        builder.setCarTitle(carTitle);
        builder.setImages(Arrays.copyOf(images, images.length));
        builder.setRenterName(renterName);
        builder.setRenterPhoto(renterPhoto);
        builder.setOwnerPhoto(ownerPhoto);
        builder.setOwnerId(ownerId);
        return builder;
    }

    public Integer getTotalAmount() {
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

    public String getOwnerPhoto() {
        return ownerPhoto;
    }

    public Integer getOwnerId() {
        return ownerId;
    }
}
