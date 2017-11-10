package com.cardee.domain.owner.entity;

public class NewCar {

    private final Integer vehicleType;
    private final Boolean insuranceComprehensive; //Optional
    private final Boolean insuranceUnnamedDriver; //Optional
    private final Integer insuranceMinAge; //Optional
    private final Integer insuranceMinYearsDrivingExperience; //Optional
    private final String insuranceExpiredDate; //Optional
    private final String make;
    private final String model;
    private final Integer manufactureYear;
    private final String title;
    private final String licencePlateNumber;
    private final String seatingCapacity;
    private final String engineCapacity;
    private final Integer transmissionId;
    private final Integer bodyType;
    private final String image;
    private final Double latitude;
    private final Double longitude;
    private final String town;
    private final String address;
    private final Boolean hideExactLocation;
    private final String contactName;
    private final String contactPhone;
    private final String contactEmail;

    private NewCar(Integer vehicleType,
                   Boolean insuranceComprehensive,
                   Boolean insuranceUnnamedDriver,
                   Integer insuranceMinAge,
                   Integer insuranceMinYearsDrivingExperience,
                   String insuranceExpiredDate,
                   String make,
                   String model,
                   Integer manufactureYear,
                   String title,
                   String licencePlateNumber,
                   String seatingCapacity,
                   String engineCapacity,
                   Integer transmissionId,
                   Integer bodyType,
                   String image,
                   Double latitude,
                   Double longitude,
                   String town,
                   String address,
                   Boolean hideExactLocation,
                   String contactName,
                   String contactPhone,
                   String contactEmail) {
        this.vehicleType = vehicleType;
        this.insuranceComprehensive = insuranceComprehensive;
        this.insuranceUnnamedDriver = insuranceUnnamedDriver;
        this.insuranceMinAge = insuranceMinAge;
        this.insuranceMinYearsDrivingExperience = insuranceMinYearsDrivingExperience;
        this.insuranceExpiredDate = insuranceExpiredDate;
        this.make = make;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.title = title;
        this.licencePlateNumber = licencePlateNumber;
        this.seatingCapacity = seatingCapacity;
        this.engineCapacity = engineCapacity;
        this.transmissionId = transmissionId;
        this.bodyType = bodyType;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.town = town;
        this.address = address;
        this.hideExactLocation = hideExactLocation;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    public static class Builder {
        private Integer vehicleType;
        private Boolean insuranceComprehensive; //Optional
        private Boolean insuranceUnnamedDriver; //Optional
        private Integer insuranceMinAge; //Optional
        private Integer insuranceMinYearsDrivingExperience; //Optional
        private String insuranceExpiredDate; //Optional
        private String make;
        private String model;
        private Integer manufactureYear;
        private String title;
        private String licencePlateNumber;
        private String seatingCapacity;
        private String engineCapacity;
        private Integer transmissionId;
        private Integer bodyType;
        private String image;
        private Double latitude;
        private Double longitude;
        private String town;
        private String address;
        private Boolean hideExactLocation;
        private String contactName;
        private String contactPhone;
        private String contactEmail;

        public Builder() {

        }

        private Builder(NewCar carData) {

        }

        public Builder setVehicleType(Integer vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder setInsuranceComprehensive(Boolean insuranceComprehensive) {
            this.insuranceComprehensive = insuranceComprehensive;
            return this;
        }

        public Builder setInsuranceUnnamedDriver(Boolean insuranceUnnamedDriver) {
            this.insuranceUnnamedDriver = insuranceUnnamedDriver;
            return this;
        }

        public Builder setInsuranceMinAge(Integer insuranceMinAge) {
            this.insuranceMinAge = insuranceMinAge;
            return this;
        }

        public Builder setInsuranceMinYearsDrivingExperience(Integer insuranceMinYearsDrivingExperience) {
            this.insuranceMinYearsDrivingExperience = insuranceMinYearsDrivingExperience;
            return this;
        }

        public Builder setInsuranceExpiredDate(String insuranceExpiredDate) {
            this.insuranceExpiredDate = insuranceExpiredDate;
            return this;
        }

        public Builder setMake(String make) {
            this.make = make;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setManufactureYear(Integer manufactureYear) {
            this.manufactureYear = manufactureYear;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setLicencePlateNumber(String licencePlateNumber) {
            this.licencePlateNumber = licencePlateNumber;
            return this;
        }

        public Builder setSeatingCapacity(String seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            return this;
        }

        public Builder setEngineCapacity(String engineCapacity) {
            this.engineCapacity = engineCapacity;
            return this;
        }

        public Builder setTransmissionId(Integer transmissionId) {
            this.transmissionId = transmissionId;
            return this;
        }

        public Builder setBodyType(Integer bodyType) {
            this.bodyType = bodyType;
            return this;
        }

        public Builder setImage(String image) {
            this.image = image;
            return this;
        }

        public Builder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setTown(String town) {
            this.town = town;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setHideExactLocation(Boolean hideExactLocation) {
            this.hideExactLocation = hideExactLocation;
            return this;
        }

        public Builder setContactName(String contactName) {
            this.contactName = contactName;
            return this;
        }

        public Builder setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
            return this;
        }

        public Builder setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public NewCar build() {
            return new NewCar(this.vehicleType,
                    this.insuranceComprehensive,
                    this.insuranceUnnamedDriver,
                    this.insuranceMinAge,
                    this.insuranceMinYearsDrivingExperience,
                    this.insuranceExpiredDate,
                    this.make,
                    this.model,
                    this.manufactureYear,
                    this.title,
                    this.licencePlateNumber,
                    this.seatingCapacity,
                    this.engineCapacity,
                    this.transmissionId,
                    this.bodyType,
                    this.image,
                    this.latitude,
                    this.longitude,
                    this.town,
                    this.address,
                    this.hideExactLocation,
                    this.contactName,
                    this.contactPhone,
                    this.contactEmail);
        }
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public Boolean getInsuranceComprehensive() {
        return insuranceComprehensive;
    }

    public Boolean getInsuranceUnnamedDriver() {
        return insuranceUnnamedDriver;
    }

    public Integer getInsuranceMinAge() {
        return insuranceMinAge;
    }

    public Integer getInsuranceMinYearsDrivingExperience() {
        return insuranceMinYearsDrivingExperience;
    }

    public String getInsuranceExpiredDate() {
        return insuranceExpiredDate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public String getTitle() {
        return title;
    }

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public Integer getTransmissionId() {
        return transmissionId;
    }

    public Integer getBodyType() {
        return bodyType;
    }

    public String getImage() {
        return image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTown() {
        return town;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getHideExactLocation() {
        return hideExactLocation;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Builder newBuilder() {
        return new Builder()
                .setVehicleType(this.vehicleType)
                .setMake(this.make)
                .setModel(this.model)
                .setManufactureYear(this.manufactureYear)
                .setTitle(this.getTitle())
                .setLicencePlateNumber(this.licencePlateNumber)
                .setSeatingCapacity(this.seatingCapacity)
                .setEngineCapacity(this.engineCapacity)
                .setTransmissionId(this.transmissionId)
                .setBodyType(this.bodyType)
                .setImage(this.image)
                .setInsuranceComprehensive(this.insuranceComprehensive)
                .setInsuranceUnnamedDriver(this.insuranceUnnamedDriver)
                .setInsuranceMinAge(this.insuranceMinAge)
                .setInsuranceMinYearsDrivingExperience(this.insuranceMinYearsDrivingExperience)
                .setInsuranceExpiredDate(this.insuranceExpiredDate)
                .setLongitude(this.longitude)
                .setLatitude(this.latitude)
                .setAddress(this.address)
                .setTown(this.town)
                .setHideExactLocation(this.hideExactLocation)
                .setContactName(this.contactName)
                .setContactPhone(this.contactPhone)
                .setContactEmail(this.contactEmail);
    }
}
