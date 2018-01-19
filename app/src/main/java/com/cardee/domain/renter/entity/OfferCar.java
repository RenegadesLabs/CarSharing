package com.cardee.domain.renter.entity;


import android.support.annotation.NonNull;

import com.cardee.domain.owner.entity.Image;
import com.google.android.gms.maps.model.LatLng;

public class OfferCar {

    private int mCarId;

    private boolean favorite;

    private int mSeatCapacity;

    private Image[] mImages;

    private Image mPrimaryCarImage;

    private String mLicensePlateNumber;

    private String mYearOfManufacture;

    private String mBodyType;

    private String mVehicleType;

    private String mTitle;

    private double mLongitude;

    private double mLatitude;

    private int mDistance;

    private int mOwnerId;

    private String mOwnerPicture;

    private String mOwnerName;

    private int mRatingCount;

    private float mRating;

    private boolean instantBooking;

    private boolean curbsideDelivery;

    private boolean acceptCash;

    private String mFuelPolicyName;

    private float mRateFirst;

    private float mRateSecond;

    private float mDiscountFirst;

    private float mDiscountSecond;

    private int mMinRentalDuration;

    private String mPickupTime;

    private String mReturnTime;

    public OfferCar(@NonNull int carId,
                    boolean favorite,
                    int seatCapacity,
                    Image[] images,
                    Image primaryCarImage,
                    String licensePlateNumber,
                    String yearOfManufacture,
                    String bodyType,
                    String vehicleType,
                    String title,
                    double longitude,
                    double latitude,
                    int distance,
                    int ownerId,
                    String ownerPicture,
                    String ownerName,
                    int ratingCount,
                    float rating,
                    boolean instantBooking,
                    boolean curbsideDelivery,
                    boolean acceptCash,
                    String fuelPolicyName,
                    float rateFirst,
                    float rateSecond,
                    float discountFirst,
                    float discountSecond,
                    int minRentalDuration,
                    String pickupTime,
                    String returnTime) {
        mCarId = carId;
        this.favorite = favorite;
        mSeatCapacity = seatCapacity;
        mImages = images;
        mPrimaryCarImage = primaryCarImage;
        mLicensePlateNumber = licensePlateNumber;
        mYearOfManufacture = yearOfManufacture;
        mBodyType = bodyType;
        mVehicleType = vehicleType;
        mTitle = title;
        mLongitude = longitude;
        mLatitude = latitude;
        mDistance = distance;
        mOwnerId = ownerId;
        mOwnerPicture = ownerPicture;
        mOwnerName = ownerName;
        mRatingCount = ratingCount;
        mRating = rating;
        this.instantBooking = instantBooking;
        this.curbsideDelivery = curbsideDelivery;
        this.acceptCash = acceptCash;
        mFuelPolicyName = fuelPolicyName;
        mRateFirst = rateFirst;
        mRateSecond = rateSecond;
        mDiscountFirst = discountFirst;
        mDiscountSecond = discountSecond;
        mMinRentalDuration = minRentalDuration;
        mPickupTime = pickupTime;
        mReturnTime = returnTime;
    }

    public static class Builder {

        private int mCarId;
        private boolean favorite;
        private int mSeatCapacity;
        private Image[] mImages;
        private Image mPrimaryCarImage;
        private String mLicensePlateNumber;
        private String mYearOfManufacture;
        private String mBodyType;
        private String mVehicleType;
        private String mTitle;
        private double mLongitude;
        private double mLatitude;
        private int mDistance;
        private int mOwnerId;
        private String mOwnerPicture;
        private String mOwnerName;
        private int mRatingCount;
        private float mRating;
        private boolean instantBooking;
        private boolean curbsideDelivery;
        private boolean acceptCash;
        private String mFuelPolicyName;
        private float mRateFirst;
        private float mRateSecond;
        private float mDiscountFirst;
        private float mDiscountSecond;
        private int mMinRentalDuration;
        private String mPickupTime;
        private String mReturnTime;

        public Builder setCarId(int carId) {
            mCarId = carId;
            return this;
        }

        public Builder setFavorite(boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        public Builder setSeatCapacity(int seatCapacity) {
            mSeatCapacity = seatCapacity;
            return this;
        }

        public Builder setImages(Image[] images) {
            mImages = images;
            return this;
        }

        public Builder setPrimaryCarImage(Image primaryCarImage) {
            mPrimaryCarImage = primaryCarImage;
            return this;
        }

        public Builder setLicensePlateNumber(String licensePlateNumber) {
            mLicensePlateNumber = licensePlateNumber;
            return this;
        }

        public Builder setYearOfManufacture(String yearOfManufacture) {
            mYearOfManufacture = yearOfManufacture;
            return this;
        }

        public Builder setBodyType(String bodyType) {
            mBodyType = bodyType;
            return this;
        }

        public Builder setVehicleType(String vehicleType) {
            mVehicleType = vehicleType;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setLongitude(double longitude) {
            mLongitude = longitude;
            return this;
        }

        public Builder setLatitude(double latitude) {
            mLatitude = latitude;
            return this;
        }

        public Builder setDistance(int distance) {
            mDistance = distance;
            return this;
        }

        public Builder setOwnerId(int ownerId) {
            mOwnerId = ownerId;
            return this;
        }

        public Builder setOwnerPicture(String ownerPicture) {
            mOwnerPicture = ownerPicture;
            return this;
        }

        public Builder setOwnerName(String ownerName) {
            mOwnerName = ownerName;
            return this;
        }

        public Builder setRatingCount(int ratingCount) {
            mRatingCount = ratingCount;
            return this;
        }

        public Builder setRating(float rating) {
            mRating = rating;
            return this;
        }

        public Builder setInstantBooking(boolean instantBooking) {
            this.instantBooking = instantBooking;
            return this;
        }

        public Builder setCurbsideDelivery(boolean curbsideDelivery) {
            this.curbsideDelivery = curbsideDelivery;
            return this;
        }

        public Builder setAcceptCash(boolean acceptCash) {
            this.acceptCash = acceptCash;
            return this;
        }

        public Builder setFuelPolicyName(String fuelPolicyName) {
            mFuelPolicyName = fuelPolicyName;
            return this;
        }

        public Builder setRateFirst(float rateFirst) {
            mRateFirst = rateFirst;
            return this;
        }

        public Builder setRateSecond(float rateSecond) {
            mRateSecond = rateSecond;
            return this;
        }

        public Builder setDiscountFirst(float discountFirst) {
            mDiscountFirst = discountFirst;
            return this;
        }

        public Builder setDiscountSecond(float discountSecond) {
            mDiscountSecond = discountSecond;
            return this;
        }

        public Builder setMinRentalDuration(int minRentalDuration) {
            mMinRentalDuration = minRentalDuration;
            return this;
        }

        public Builder setPickupTime(String pickupTime) {
            mPickupTime = pickupTime;
            return this;
        }

        public Builder setReturnTime(String returnTime) {
            mReturnTime = returnTime;
            return this;
        }

        public OfferCar build() {
            return new OfferCar(mCarId, favorite,
                    mSeatCapacity, mImages, mPrimaryCarImage, mLicensePlateNumber,
                    mYearOfManufacture, mBodyType, mVehicleType,
                    mTitle, mLongitude, mLatitude,
                    mDistance, mOwnerId, mOwnerPicture,
                    mOwnerName, mRatingCount, mRating,
                    instantBooking, curbsideDelivery, acceptCash,
                    mFuelPolicyName, mRateFirst, mRateSecond,
                    mDiscountFirst, mDiscountSecond, mMinRentalDuration,
                    mPickupTime, mReturnTime);
        }
    }


    public int getCarId() {
        return mCarId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getSeatCapacity() {
        return mSeatCapacity;
    }

    public Image[] getImages() {
        return mImages;
    }

    public Image getPrimaryCarImage() {
        return mPrimaryCarImage;
    }

    public String getLicensePlateNumber() {
        return mLicensePlateNumber;
    }

    public String getYearOfManufacture() {
        return mYearOfManufacture;
    }

    public String getBodyType() {
        return mBodyType;
    }

    public String getVehicleType() {
        return mVehicleType;
    }

    public String getTitle() {
        return mTitle;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public int getDistance() {
        return mDistance;
    }

    public int getOwnerId() {
        return mOwnerId;
    }

    public String getOwnerPicture() {
        return mOwnerPicture;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public float getRating() {
        return mRating;
    }

    public boolean isInstantBooking() {
        return instantBooking;
    }

    public boolean isCurbsideDelivery() {
        return curbsideDelivery;
    }

    public boolean isAcceptCash() {
        return acceptCash;
    }

    public String getFuelPolicyName() {
        return mFuelPolicyName;
    }

    public float getRateFirst() {
        return mRateFirst;
    }

    public float getRateSecond() {
        return mRateSecond;
    }

    public float getDiscountFirst() {
        return mDiscountFirst;
    }

    public float getDiscountSecond() {
        return mDiscountSecond;
    }

    public int getMinRentalDuration() {
        return mMinRentalDuration;
    }

    public String getPickupTime() {
        return mPickupTime;
    }

    public String getReturnTime() {
        return mReturnTime;
    }
}
