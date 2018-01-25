package com.cardee.domain.renter.entity;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.cardee.domain.owner.entity.Image;
import com.google.android.gms.maps.model.LatLng;

public class OfferCar implements Parcelable {

    private int mCarId;

    private boolean favorite;

    private int mSeatCapacity;

    private Image[] mImages;

    private String mPrimaryCarImage;

    private String mLicensePlateNumber;

    private String mYearOfManufacture;

    private String mBodyType;

    private String mVehicleType;

    private String mTitle;

    private Double mLongitude;

    private Double mLatitude;

    private int mDistance;

    private String mAddress;

    private String mTown;

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
                    String primaryCarImage,
                    String licensePlateNumber,
                    String yearOfManufacture,
                    String bodyType,
                    String vehicleType,
                    String title,
                    Double longitude,
                    Double latitude,
                    int distance,
                    String address,
                    String town,
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
        mAddress = address;
        mTown = town;
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

    protected OfferCar(Parcel in) {
        mCarId = in.readInt();
        favorite = in.readByte() != 0;
        mSeatCapacity = in.readInt();
        mPrimaryCarImage = in.readString();
        mLicensePlateNumber = in.readString();
        mYearOfManufacture = in.readString();
        mBodyType = in.readString();
        mVehicleType = in.readString();
        mTitle = in.readString();
        mLongitude = in.readDouble();
        mLatitude = in.readDouble();
        mDistance = in.readInt();
        mAddress = in.readString();
        mTown = in.readString();
        mOwnerId = in.readInt();
        mOwnerPicture = in.readString();
        mOwnerName = in.readString();
        mRatingCount = in.readInt();
        mRating = in.readFloat();
        instantBooking = in.readByte() != 0;
        curbsideDelivery = in.readByte() != 0;
        acceptCash = in.readByte() != 0;
        mFuelPolicyName = in.readString();
        mRateFirst = in.readFloat();
        mRateSecond = in.readFloat();
        mDiscountFirst = in.readFloat();
        mDiscountSecond = in.readFloat();
        mMinRentalDuration = in.readInt();
        mPickupTime = in.readString();
        mReturnTime = in.readString();
    }

    public static final Creator<OfferCar> CREATOR = new Creator<OfferCar>() {
        @Override
        public OfferCar createFromParcel(Parcel in) {
            return new OfferCar(in);
        }

        @Override
        public OfferCar[] newArray(int size) {
            return new OfferCar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mCarId);
        parcel.writeByte((byte) (favorite ? 1 : 0));
        parcel.writeInt(mSeatCapacity);
        parcel.writeString(mPrimaryCarImage);
        parcel.writeString(mLicensePlateNumber);
        parcel.writeString(mYearOfManufacture);
        parcel.writeString(mBodyType);
        parcel.writeString(mVehicleType);
        parcel.writeString(mTitle);
        parcel.writeDouble(mLongitude);
        parcel.writeDouble(mLatitude);
        parcel.writeInt(mDistance);
        parcel.writeString(mAddress);
        parcel.writeString(mTown);
        parcel.writeInt(mOwnerId);
        parcel.writeString(mOwnerPicture);
        parcel.writeString(mOwnerName);
        parcel.writeInt(mRatingCount);
        parcel.writeFloat(mRating);
        parcel.writeByte((byte) (instantBooking ? 1 : 0));
        parcel.writeByte((byte) (curbsideDelivery ? 1 : 0));
        parcel.writeByte((byte) (acceptCash ? 1 : 0));
        parcel.writeString(mFuelPolicyName);
        parcel.writeFloat(mRateFirst);
        parcel.writeFloat(mRateSecond);
        parcel.writeFloat(mDiscountFirst);
        parcel.writeFloat(mDiscountSecond);
        parcel.writeInt(mMinRentalDuration);
        parcel.writeString(mPickupTime);
        parcel.writeString(mReturnTime);
    }

    public static class Builder {

        private int mCarId;
        private boolean favorite;
        private int mSeatCapacity;
        private Image[] mImages;
        private String mPrimaryCarImage;
        private String mLicensePlateNumber;
        private String mYearOfManufacture;
        private String mBodyType;
        private String mVehicleType;
        private String mTitle;
        private Double mLongitude;
        private Double mLatitude;
        private int mDistance;
        private String mAddress;
        private String mTown;
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

        public Builder setPrimaryCarImage(String primaryCarImage) {
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

        public Builder setLongitude(Double longitude) {
            mLongitude = longitude;
            return this;
        }

        public Builder setLatitude(Double latitude) {
            mLatitude = latitude;
            return this;
        }

        public Builder setDistance(int distance) {
            mDistance = distance;
            return this;
        }

        public Builder setAddress(String address) {
            mAddress = address;
            return this;
        }

        public Builder setTown(String town) {
            mTown = town;
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
                    mDistance, mAddress, mTown,
                    mOwnerId, mOwnerPicture,
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

    public String getPrimaryCarThumbnail() {
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

    public Double getLongitude() {
        return mLongitude;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public int getDistance() {
        return mDistance;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getTown() {
        return mTown;
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
