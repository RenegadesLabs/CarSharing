package com.cardee.domain.owner.entity;


import android.support.annotation.NonNull;

public class Car {

    private final Integer mCarId;
    private final String mCarMake;
    private final String mCarTitle;
    private final String mCarModel;
    private final Integer mVehicleTypeId;
    private final String mVehicleType;
    private final String mManufactureYear;
    private final String mLicenceNumber;
    private final String mBodyType;
    private final String mEngineCapacity;
    private final String mTransmissionType;
    private final Integer mSeatingCapacity;
    private final String mPrimaryImageLink;
    private final String mPrimaryImageThumbnail;
    private final Integer mCarAvailabilityHourlyCount;
    private final Integer mCarAvailabilityDailyCount;
    private final String mCarAvailabilityTimeBegin;
    private final String mCarAvailabilityTimeEnd;
    private final Boolean mCarAvailableOrderHours;
    private final Boolean mCarAvailableOrderDays;
    private final String[] mCarAvailabilityHourlyDates;
    private final String[] mCarAvailabilityDailyDates;
    private final String mDescription;
    private final Image[] mImages;
    private final Double mLongitude;
    private final Double mLatitude;
    private final String mAddress;
    private final String mTown;
    private final Float mRating;

    public Car(@NonNull Integer carId,
               String carMake,
               String carTitle,
               String carModel,
               Integer vehicleTypeId,
               String vehicleType,
               String manufactureYear,
               String licenceNumber,
               String bodyType,
               String engineCapacity,
               String transmissionType,
               Integer seatingCapacity,
               String primaryImageLink,
               String primaryImageThumbnail,
               Integer carAvailabilityHourlyCount,
               Integer carAvailabilityDailyCount,
               String carAvailabilityTimeBegin,
               String carAvailabilityTimeEnd,
               Boolean carAvailableOrderHours,
               Boolean carAvailableOrderDays,
               String[] carAvailabilityHourlyDates,
               String[] carAvailabilityDailyDates,
               String description,
               Image[] images,
               Double longitude,
               Double latitude,
               String address,
               String town,
               Float rating) {
        mCarId = carId;
        mCarMake = carMake;
        mCarTitle = carTitle;
        mCarModel = carModel;
        mVehicleTypeId = vehicleTypeId;
        mVehicleType = vehicleType;
        mManufactureYear = manufactureYear;
        mLicenceNumber = licenceNumber;
        mBodyType = bodyType;
        mEngineCapacity = engineCapacity;
        mTransmissionType = transmissionType;
        mSeatingCapacity = seatingCapacity;
        mPrimaryImageLink = primaryImageLink;
        mPrimaryImageThumbnail = primaryImageThumbnail;
        mCarAvailabilityHourlyCount = carAvailabilityHourlyCount;
        mCarAvailabilityDailyCount = carAvailabilityDailyCount;
        mCarAvailabilityTimeBegin = carAvailabilityTimeBegin;
        mCarAvailabilityTimeEnd = carAvailabilityTimeEnd;
        mCarAvailableOrderHours = carAvailableOrderHours;
        mCarAvailableOrderDays = carAvailableOrderDays;
        mCarAvailabilityHourlyDates = carAvailabilityHourlyDates;
        mCarAvailabilityDailyDates = carAvailabilityDailyDates;
        mDescription = description;
        mImages = images;
        mLongitude = longitude;
        mLatitude = latitude;
        mAddress = address;
        mTown = town;
        mRating = rating;
    }

    public static class Builder {

        private Integer mBuilderCarId;
        private String mBuilderCarMake;
        private String mBuilderCarTitle;
        private String mBuilderCarModel;
        private Integer mBuilderVehicleTypeId;
        private String mBuilderVehicleType;
        private String mBuilderManufactureYear;
        private String mBuilderLicenceNumber;
        private String mBuilderBodyType;
        private String mBuilderEngineCapacity;
        private String mBuilderTransmissionType;
        private Integer mBuilderSeatingCapacity;
        private String mBuilderPrimaryImageLink;
        private String mBuilderPrimaryImageThumbnail;
        private Integer mBuilderCarAvailabilityHourlyCount;
        private Integer mBuilderCarAvailabilityDailyCount;
        private String mBuilderCarAvailabilityTimeBegin;
        private String mBuilderCarAvailabilityTimeEnd;
        private Boolean mBuilderCarAvailableOrderHours;
        private Boolean mBuilderCarAvailableOrderDays;
        private String[] mBuilderCarAvailabilityHourlyDates;
        private String[] mBuilderCarAvailabilityDailyDates;
        private String mBuilderDescription;
        private Image[] mBuilderImages;
        private Double mBuilderLongitude;
        private Double mBuilderLatitude;
        private String mBuilderAddress;
        private String mBuilderTown;
        private Float mRating;

        private Builder(Car car) {
            mBuilderCarId = car.getCarId();
            mBuilderCarMake = car.getCarMake();
            mBuilderCarTitle = car.getCarTitle();
            mBuilderCarModel = car.getCarModel();
            mBuilderVehicleTypeId = car.getVehicleTypeId();
            mBuilderVehicleType = car.getVehicleType();
            mBuilderManufactureYear = car.getManufactureYear();
            mBuilderLicenceNumber = car.getLicenceNumber();
            mBuilderBodyType = car.getBodyType();
            mBuilderEngineCapacity = car.getEngineCapacity();
            mBuilderTransmissionType = car.getTransmissionType();
            mBuilderSeatingCapacity = car.getSeatingCapacity();
            mBuilderPrimaryImageLink = car.getPrimaryImageLink();
            mBuilderPrimaryImageThumbnail = car.getPrimaryImageThumbnail();
            mBuilderCarAvailabilityHourlyCount = car.getCarAvailabilityHourlyCount();
            mBuilderCarAvailabilityDailyCount = car.getCarAvailabilityDailyCount();
            mBuilderCarAvailabilityTimeBegin = car.getCarAvailabilityTimeBegin();
            mBuilderCarAvailabilityTimeEnd = car.getCarAvailabilityTimeEnd();
            mBuilderCarAvailableOrderHours = car.getCarAvailableOrderHours();
            mBuilderCarAvailableOrderDays = car.getCarAvailableOrderDays();
            mBuilderCarAvailabilityHourlyDates = car.getCarAvailabilityHourlyDates();
            mBuilderCarAvailabilityDailyDates = car.getCarAvailabilityDailyDates();
            mBuilderDescription = car.getDescription();
            mBuilderImages = car.getImages();
            mBuilderLatitude = car.getLatitude();
            mBuilderLongitude = car.getLongitude();
            mBuilderAddress = car.getAddress();
            mBuilderTown = car.getTown();
            mRating = car.getRating();
        }

        public Builder setCarMake(String carMake) {
            mBuilderCarModel = carMake;
            return this;
        }

        public Builder setCarTitle(String carTitle) {
            mBuilderCarTitle = carTitle;
            return this;
        }

        public Builder setCarModel(String carModel) {
            mBuilderCarModel = carModel;
            return this;
        }

        public Builder setVehicleTypeId(Integer vehicleTypeId) {
            mBuilderVehicleTypeId = vehicleTypeId;
            return this;
        }

        public Builder setVehicleType(String vehicleType) {
            mBuilderVehicleType = vehicleType;
            return this;
        }

        public Builder setManufactureYear(String year) {
            mBuilderManufactureYear = year;
            return this;
        }

        public Builder setLicenceNumber(String licenceNumber) {
            mBuilderLicenceNumber = licenceNumber;
            return this;
        }

        public Builder setBodyType(String bodyType) {
            mBuilderBodyType = bodyType;
            return this;
        }

        public Builder setEngineCapacity(String engineCapacity) {
            mBuilderEngineCapacity = engineCapacity;
            return this;
        }

        public Builder setTransmissionType(String transmissionType) {
            mBuilderTransmissionType = transmissionType;
            return this;
        }

        public Builder setDescription(String description) {
            mBuilderDescription = description;
            return this;
        }

        public Builder setSeatingCapacity(Integer seatingCapacity) {
            mBuilderSeatingCapacity = seatingCapacity;
            return this;
        }

        public Builder setAddress(String address) {
            mBuilderAddress = address;
            return this;
        }

        public Builder setTown(String town) {
            mBuilderTown = town;
            return this;
        }

        public Builder setLongitude(Double longitude) {
            mBuilderLongitude = longitude;
            return this;
        }

        public Builder setLatitude(Double latitude) {
            mBuilderLatitude = latitude;
            return this;
        }

        public void setRating(Float rating) {
            mRating = rating;
        }

        //TODO implement Availability setting

        public Car build() {
            return new Car(
                    mBuilderCarId,
                    mBuilderCarMake,
                    mBuilderCarTitle,
                    mBuilderCarModel,
                    mBuilderVehicleTypeId,
                    mBuilderVehicleType,
                    mBuilderManufactureYear,
                    mBuilderLicenceNumber,
                    mBuilderBodyType,
                    mBuilderEngineCapacity,
                    mBuilderTransmissionType,
                    mBuilderSeatingCapacity,
                    mBuilderPrimaryImageLink,
                    mBuilderPrimaryImageThumbnail,
                    mBuilderCarAvailabilityHourlyCount,
                    mBuilderCarAvailabilityDailyCount,
                    mBuilderCarAvailabilityTimeBegin,
                    mBuilderCarAvailabilityTimeEnd,
                    mBuilderCarAvailableOrderHours,
                    mBuilderCarAvailableOrderDays,
                    mBuilderCarAvailabilityHourlyDates,
                    mBuilderCarAvailabilityDailyDates,
                    mBuilderDescription,
                    mBuilderImages,
                    mBuilderLongitude,
                    mBuilderLatitude,
                    mBuilderAddress,
                    mBuilderTown,
                    mRating);
        }
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public Integer getCarId() {
        return mCarId;
    }

    public String getCarMake() {
        return mCarMake;
    }

    public String getCarTitle() {
        return mCarTitle;
    }

    public String getCarModel() {
        return mCarModel;
    }

    public Integer getVehicleTypeId() {
        return mVehicleTypeId;
    }

    public String getVehicleType() {
        return mVehicleType;
    }

    public String getManufactureYear() {
        return mManufactureYear;
    }

    public String getLicenceNumber() {
        return mLicenceNumber;
    }

    public String getBodyType() {
        return mBodyType;
    }

    public String getEngineCapacity() {
        return mEngineCapacity;
    }

    public String getTransmissionType() {
        return mTransmissionType;
    }

    public Integer getSeatingCapacity() {
        return mSeatingCapacity;
    }

    public String getPrimaryImageLink() {
        return mPrimaryImageLink;
    }

    public String getPrimaryImageThumbnail() {
        return mPrimaryImageThumbnail;
    }

    public Integer getCarAvailabilityHourlyCount() {
        return mCarAvailabilityHourlyCount;
    }

    public Integer getCarAvailabilityDailyCount() {
        return mCarAvailabilityDailyCount;
    }

    public String getCarAvailabilityTimeBegin() {
        return mCarAvailabilityTimeBegin;
    }

    public String getCarAvailabilityTimeEnd() {
        return mCarAvailabilityTimeEnd;
    }

    public Boolean getCarAvailableOrderHours() {
        return mCarAvailableOrderHours;
    }

    public Boolean getCarAvailableOrderDays() {
        return mCarAvailableOrderDays;
    }

    public String[] getCarAvailabilityHourlyDates() {
        return mCarAvailabilityHourlyDates;
    }

    public String[] getCarAvailabilityDailyDates() {
        return mCarAvailabilityDailyDates;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getTown() {
        return mTown;
    }

    public Image[] getImages() {
        return mImages;
    }

    public String getDescription() {
        return mDescription;
    }

    public Float getRating() {
        return mRating;
    }

    public boolean isAvailableHourly() {
        return mCarAvailableOrderHours != null && mCarAvailableOrderHours;
    }

    public boolean isAvailableDaily() {
        return mCarAvailableOrderDays != null && mCarAvailableOrderDays;
    }

    @Override
    public int hashCode() {
        return mCarId == null ? 0 : mCarId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().getName().equals(getClass().getName())) {
            return false;
        }
        Car suspect = (Car) obj;
        return suspect.mCarId != null && mCarId != null && mCarId.equals(suspect.mCarId);
    }
}
