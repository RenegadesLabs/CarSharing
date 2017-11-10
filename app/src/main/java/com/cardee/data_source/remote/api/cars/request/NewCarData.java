package com.cardee.data_source.remote.api.cars.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCarData {

    @Expose
    @SerializedName("type_vehicle_id")
    private Integer vehicleType;

    @Expose
    @SerializedName("is_insurance_comprehensive")
    private Boolean insuranceComprehensive; //Optional
    @Expose
    @SerializedName("is_insurance_unnamed_driver")
    private Boolean insuranceUnnamedDriver; //Optional
    @Expose
    @SerializedName("insurance_min_age")
    private Integer insuranceMinAge; //Optional
    @Expose
    @SerializedName("insurance_min_year_dr_exp")
    private Integer insuranceMinYearsDrivingExperience; //Optional
    @Expose
    @SerializedName("date_insurance_expired")
    private String insuranceExpiredDate; //Optional

    @Expose
    @SerializedName("car_make")
    private String make;
    @Expose
    @SerializedName("car_model")
    private String model;
    @Expose
    @SerializedName("year_manufacture")
    private Integer manufactureYear;
    @Expose
    @SerializedName("car_title")
    private String title;
    @Expose
    @SerializedName("license_plate_number")
    private String licencePlateNumber;
    @Expose
    @SerializedName("seating_capacity")
    private String seatingCapacity;
    @Expose
    @SerializedName("car_engine_capacity")
    private String engineCapacity;
    @Expose
    @SerializedName("car_transmission_id")
    private Integer transmissionId;
    @Expose
    @SerializedName("car_body_type_id")
    private Integer bodyType;

    @Expose
    @SerializedName("car_image")
    private String image; //Base64

    @Expose
    @SerializedName("longitude")
    private Double latitude;
    @Expose
    @SerializedName("latitude")
    private Double longitude;
    @Expose
    @SerializedName("town")
    private String town;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("is_hide_exact_location")
    private Boolean hideExactLocation;

    @Expose
    @SerializedName("name")
    private String contactName;
    @Expose
    @SerializedName("phone")
    private String contactPhone;
    @Expose
    @SerializedName("email")
    private String contactEmail;

    public NewCarData() {

    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Boolean getInsuranceComprehensive() {
        return insuranceComprehensive;
    }

    public void setInsuranceComprehensive(Boolean insuranceComprehensive) {
        this.insuranceComprehensive = insuranceComprehensive;
    }

    public Boolean getInsuranceUnnamedDriver() {
        return insuranceUnnamedDriver;
    }

    public void setInsuranceUnnamedDriver(Boolean insuranceUnnamedDriver) {
        this.insuranceUnnamedDriver = insuranceUnnamedDriver;
    }

    public Integer getInsuranceMinAge() {
        return insuranceMinAge;
    }

    public void setInsuranceMinAge(Integer insuranceMinAge) {
        this.insuranceMinAge = insuranceMinAge;
    }

    public Integer getInsuranceMinYearsDrivingExperience() {
        return insuranceMinYearsDrivingExperience;
    }

    public void setInsuranceMinYearsDrivingExperience(Integer insuranceMinYearsDrivingExperience) {
        this.insuranceMinYearsDrivingExperience = insuranceMinYearsDrivingExperience;
    }

    public String getInsuranceExpiredDate() {
        return insuranceExpiredDate;
    }

    public void setInsuranceExpiredDate(String insuranceExpiredDate) {
        this.insuranceExpiredDate = insuranceExpiredDate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public void setLicencePlateNumber(String licencePlateNumber) {
        this.licencePlateNumber = licencePlateNumber;
    }

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(String seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Integer getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Integer getBodyType() {
        return bodyType;
    }

    public void setBodyType(Integer bodyType) {
        this.bodyType = bodyType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getHideExactLocation() {
        return hideExactLocation;
    }

    public void setHideExactLocation(Boolean hideExactLocation) {
        this.hideExactLocation = hideExactLocation;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
