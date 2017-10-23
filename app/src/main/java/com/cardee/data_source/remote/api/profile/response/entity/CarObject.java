package com.cardee.data_source.remote.api.profile.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarObject {

    @Expose
    @SerializedName("car_availability_hourly_cnt")
    private Integer carAvailabilityHourlyCount;
    @Expose
    @SerializedName("car_availability_daily_cnt")
    private Integer carAvailabilityDailyCount;
    @Expose
    @SerializedName("car_availability_time_begin")
    private String carAvailabilityTimeBegin;
    @Expose
    @SerializedName("car_availability_time_end")
    private String carAvailabilityTimeEnd;
    @Expose
    @SerializedName("is_available_order_hours")
    private Boolean carAvailableOrderHours;
    @Expose
    @SerializedName("is_available_order_days")
    private Boolean carAvailableOrderDays;
    @Expose
    @SerializedName("car_availability_hourly")
    private String[] carAvailabilityHourlyDates;
    @Expose
    @SerializedName("car_availability_daily")
    private String[] carAvailabilityDailyDates;
    @Expose
    @SerializedName("car_details")
    private Details carDetails;

    public CarObject() {

    }

    public Integer getCarAvailabilityHourlyCount() {
        return carAvailabilityHourlyCount;
    }

    public void setCarAvailabilityHourlyCount(Integer carAvailabilityHourlyCount) {
        this.carAvailabilityHourlyCount = carAvailabilityHourlyCount;
    }

    public Integer getCarAvailabilityDailyCount() {
        return carAvailabilityDailyCount;
    }

    public void setCarAvailabilityDailyCount(Integer carAvailabilityDailyCount) {
        this.carAvailabilityDailyCount = carAvailabilityDailyCount;
    }

    public String getCarAvailabilityTimeBegin() {
        return carAvailabilityTimeBegin;
    }

    public void setCarAvailabilityTimeBegin(String carAvailabilityTimeBegin) {
        this.carAvailabilityTimeBegin = carAvailabilityTimeBegin;
    }

    public String getCarAvailabilityTimeEnd() {
        return carAvailabilityTimeEnd;
    }

    public void setCarAvailabilityTimeEnd(String carAvailabilityTimeEnd) {
        this.carAvailabilityTimeEnd = carAvailabilityTimeEnd;
    }

    public Boolean getCarAvailableOrderHours() {
        return carAvailableOrderHours;
    }

    public void setCarAvailableOrderHours(Boolean carAvailableOrderHours) {
        this.carAvailableOrderHours = carAvailableOrderHours;
    }

    public Boolean getCarAvailableOrderDays() {
        return carAvailableOrderDays;
    }

    public void setCarAvailableOrderDays(Boolean carAvailableOrderDays) {
        this.carAvailableOrderDays = carAvailableOrderDays;
    }

    public String[] getCarAvailabilityHourlyDates() {
        return carAvailabilityHourlyDates;
    }

    public void setCarAvailabilityHourlyDates(String[] carAvailabilityHourlyDates) {
        this.carAvailabilityHourlyDates = carAvailabilityHourlyDates;
    }

    public String[] getCarAvailabilityDailyDates() {
        return carAvailabilityDailyDates;
    }

    public void setCarAvailabilityDailyDates(String[] carAvailabilityDailyDates) {
        this.carAvailabilityDailyDates = carAvailabilityDailyDates;
    }

    public Details getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(Details carDetails) {
        this.carDetails = carDetails;
    }

    public static class Details {

        @Expose
        @SerializedName("car_id")
        private Integer carId;
        @Expose
        @SerializedName("car_transmission")
        private String carTransmission;
        @Expose
        @SerializedName("seating_capacity")
        private Integer seatingCapacity;
        @Expose
        @SerializedName("license_plate_number")
        private String licencePlateNumber;
        @Expose
        @SerializedName("year_manufacture")
        private String manufactureYear;
        @Expose
        @SerializedName("car_body_type")
        private String bodyType;
        @Expose
        @SerializedName("car_make")
        private String make;
        @Expose
        @SerializedName("car_title")
        private String title;
        @Expose
        @SerializedName("car_model")
        private String model;
        @Expose
        @SerializedName("vehicle_type")
        private String vehicleType;
        @Expose
        @SerializedName("vehicle_type_id")
        private Integer vehicleTypeId;
        @Expose
        @SerializedName("car_engine_capacity")
        private String engineCapacity;
        @Expose
        @SerializedName("images")
        private Image[] images;

        public Details() {

        }

        public Integer getCarId() {
            return carId;
        }

        public void setCarId(Integer carId) {
            this.carId = carId;
        }

        public String getCarTransmission() {
            return carTransmission;
        }

        public void setCarTransmission(String carTransmission) {
            this.carTransmission = carTransmission;
        }

        public Integer getSeatingCapacity() {
            return seatingCapacity;
        }

        public void setSeatingCapacity(Integer seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
        }

        public String getLicencePlateNumber() {
            return licencePlateNumber;
        }

        public void setLicencePlateNumber(String licencePlateNumber) {
            this.licencePlateNumber = licencePlateNumber;
        }

        public String getManufactureYear() {
            return manufactureYear;
        }

        public void setManufactureYear(String manufactureYear) {
            this.manufactureYear = manufactureYear;
        }

        public String getBodyType() {
            return bodyType;
        }

        public void setBodyType(String bodyType) {
            this.bodyType = bodyType;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public Integer getVehicleTypeId() {
            return vehicleTypeId;
        }

        public void setVehicleTypeId(Integer vehicleTypeId) {
            this.vehicleTypeId = vehicleTypeId;
        }

        public String getEngineCapacity() {
            return engineCapacity;
        }

        public void setEngineCapacity(String engineCapacity) {
            this.engineCapacity = engineCapacity;
        }

        public Image[] getImages() {
            return images;
        }

        public void setImages(Image[] images) {
            this.images = images;
        }
    }
}
