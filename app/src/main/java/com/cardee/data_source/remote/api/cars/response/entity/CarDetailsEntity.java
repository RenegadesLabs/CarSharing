package com.cardee.data_source.remote.api.cars.response.entity;

import com.cardee.data_source.remote.api.common.entity.BaseCarEntity;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarDetailsEntity extends BaseCarEntity {

    @Expose
    @SerializedName("longitude")
    private Double longitude;
    @Expose
    @SerializedName("latitude")
    private Double latitude;
    @Expose
    @SerializedName("town")
    private String town;
    @Expose
    @SerializedName("req_min_age")
    private Integer requiredMinAge;
    @Expose
    @SerializedName("req_max_age")
    private Integer requiredMaxAge;
    @Expose
    @SerializedName("req_dr_exp")
    private Integer requiredDrivingExp;
    @Expose
    @SerializedName("car_rules")
    private CarRuleEntity[] rules;
    @Expose
    @SerializedName("car_other_rules")
    private String carOtherRules;
    @Expose
    @SerializedName("is_req_security_deposit")
    private Boolean requiredSecurityDeposit;
    @Expose
    @SerializedName("security_deposit_description")
    private String securityDepositDescription;
    @Expose
    @SerializedName("compensation_excess")
    private String compensationAccess;
    @Expose
    @SerializedName("compensation_other_guidelines")
    private String compensationOtherGuidelines;
    @Expose
    @SerializedName("add_ons")
    private String addOns;
    @Expose
    @SerializedName("additional_terms")
    private String additionalTerms;
    @Expose
    @SerializedName("delivery_rates")
    private DeliveryRatesEntity deliveryRates;

    public CarDetailsEntity() {

    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Integer getRequiredMinAge() {
        return requiredMinAge;
    }

    public void setRequiredMinAge(Integer requiredMinAge) {
        this.requiredMinAge = requiredMinAge;
    }

    public Integer getRequiredMaxAge() {
        return requiredMaxAge;
    }

    public void setRequiredMaxAge(Integer requiredMaxAge) {
        this.requiredMaxAge = requiredMaxAge;
    }

    public Integer getRequiredDrivingExp() {
        return requiredDrivingExp;
    }

    public void setRequiredDrivingExp(Integer requiredDrivingExp) {
        this.requiredDrivingExp = requiredDrivingExp;
    }

    public String getCarOtherRules() {
        return carOtherRules;
    }

    public void setCarOtherRules(String carOtherRules) {
        this.carOtherRules = carOtherRules;
    }

    public Boolean getRequiredSecurityDeposit() {
        return requiredSecurityDeposit;
    }

    public void setRequiredSecurityDeposit(Boolean requiredSecurityDeposit) {
        this.requiredSecurityDeposit = requiredSecurityDeposit;
    }

    public String getSecurityDepositDescription() {
        return securityDepositDescription;
    }

    public void setSecurityDepositDescription(String securityDepositDescription) {
        this.securityDepositDescription = securityDepositDescription;
    }

    public String getCompensationAccess() {
        return compensationAccess;
    }

    public void setCompensationAccess(String compensationAccess) {
        this.compensationAccess = compensationAccess;
    }

    public String getCompensationOtherGuidelines() {
        return compensationOtherGuidelines;
    }

    public void setCompensationOtherGuidelines(String compensationOtherGuidelines) {
        this.compensationOtherGuidelines = compensationOtherGuidelines;
    }

    public String getAddOns() {
        return addOns;
    }

    public void setAddOns(String addOns) {
        this.addOns = addOns;
    }

    public CarRuleEntity[] getRules() {
        return rules;
    }

    public void setRules(CarRuleEntity[] rules) {
        this.rules = rules;
    }

    public String getAdditionalTerms() {
        return additionalTerms;
    }

    public void setAdditionalTerms(String additionalTerms) {
        this.additionalTerms = additionalTerms;
    }

    public DeliveryRatesEntity getDeliveryRates() {
        return deliveryRates;
    }

    public void setDeliveryRates(DeliveryRatesEntity deliveryRates) {
        this.deliveryRates = deliveryRates;
    }

    public static CarDetailsEntity from(BaseCarEntity carEntity) {
        CarDetailsEntity detailsEntity = new CarDetailsEntity();
        detailsEntity.setCarId(carEntity.getCarId());
        detailsEntity.setMake(carEntity.getMake());
        detailsEntity.setTitle(carEntity.getTitle());
        detailsEntity.setModel(carEntity.getModel());
        detailsEntity.setManufactureYear(carEntity.getManufactureYear());
        detailsEntity.setBodyType(carEntity.getBodyType());
        detailsEntity.setSeatingCapacity(carEntity.getSeatingCapacity());
        detailsEntity.setEngineCapacity(carEntity.getEngineCapacity());
        detailsEntity.setCarTransmission(carEntity.getCarTransmission());
        detailsEntity.setVehicleType(carEntity.getVehicleType());
        detailsEntity.setVehicleTypeId(carEntity.getVehicleTypeId());
        detailsEntity.setLicencePlateNumber(carEntity.getLicencePlateNumber());
        detailsEntity.setImages(carEntity.getImages());
        detailsEntity.setDescription(carEntity.getDescription());
        return detailsEntity;
    }
}
