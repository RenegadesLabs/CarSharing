package com.cardee.domain.owner.entity;


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
    private final Integer mCarAvailabilityHourlyCount;
    private final Integer mCarAvailabilityDailyCount;
    private final String mCarAvailabilityTimeBegin;
    private final String mCarAvailabilityTimeEnd;
    private final Boolean mCarAvailableOrderHours;
    private final Boolean mCarAvailableOrderDays;
    private final String[] mCarAvailabilityHourlyDates;
    private final String[] mCarAvailabilityDailyDates;
    private final Image[] mImages;

    public Car(Integer carId,
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
               Integer carAvailabilityHourlyCount,
               Integer carAvailabilityDailyCount,
               String carAvailabilityTimeBegin,
               String carAvailabilityTimeEnd,
               Boolean carAvailableOrderHours,
               Boolean carAvailableOrderDays,
               String[] carAvailabilityHourlyDates,
               String[] carAvailabilityDailyDates,
               Image[] images) {
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
        mCarAvailabilityHourlyCount = carAvailabilityHourlyCount;
        mCarAvailabilityDailyCount = carAvailabilityDailyCount;
        mCarAvailabilityTimeBegin = carAvailabilityTimeBegin;
        mCarAvailabilityTimeEnd = carAvailabilityTimeEnd;
        mCarAvailableOrderHours = carAvailableOrderHours;
        mCarAvailableOrderDays = carAvailableOrderDays;
        mCarAvailabilityHourlyDates = carAvailabilityHourlyDates;
        mCarAvailabilityDailyDates = carAvailabilityDailyDates;
        mImages = images;
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
        private Integer mBuilderCarAvailabilityHourlyCount;
        private Integer mBuilderCarAvailabilityDailyCount;
        private String mBuilderCarAvailabilityTimeBegin;
        private String mBuilderCarAvailabilityTimeEnd;
        private Boolean mBuilderCarAvailableOrderHours;
        private Boolean mBuilderCarAvailableOrderDays;
        private String[] mBuilderCarAvailabilityHourlyDates;
        private String[] mBuilderCarAvailabilityDailyDates;
        private Image[] mBuilderImages;

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
            mBuilderCarAvailabilityHourlyCount = car.getCarAvailabilityHourlyCount();
            mBuilderCarAvailabilityDailyCount = car.getCarAvailabilityDailyCount();
            mBuilderCarAvailabilityTimeBegin = car.getCarAvailabilityTimeBegin();
            mBuilderCarAvailabilityTimeEnd = car.getCarAvailabilityTimeEnd();
            mBuilderCarAvailableOrderHours = car.getCarAvailableOrderHours();
            mBuilderCarAvailableOrderDays = car.getCarAvailableOrderDays();
            mBuilderCarAvailabilityHourlyDates = car.getCarAvailabilityHourlyDates();
            mBuilderCarAvailabilityDailyDates = car.getCarAvailabilityDailyDates();
            mBuilderImages = car.getImages();
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

        public Builder setSeatingCapacity(Integer seatingCapacity) {
            mBuilderSeatingCapacity = seatingCapacity;
            return this;
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
                    mBuilderCarAvailabilityHourlyCount,
                    mBuilderCarAvailabilityDailyCount,
                    mBuilderCarAvailabilityTimeBegin,
                    mBuilderCarAvailabilityTimeEnd,
                    mBuilderCarAvailableOrderHours,
                    mBuilderCarAvailableOrderDays,
                    mBuilderCarAvailabilityHourlyDates,
                    mBuilderCarAvailabilityDailyDates,
                    mBuilderImages);
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

    public Image[] getImages() {
        return mImages;
    }
}
