package com.cardee.domain.bookings.entity;

import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.domain.owner.entity.Image;

public class Checklist {

    private String remarks;
    private int bookingId;
    private String tank;
    private int masterMileage;
    private boolean byMileage;
    private String driverName;
    private Image[] images;
    private int[] imageIds;

    public Checklist(String remarks,
                     int bookingId,
                     String tank,
                     int masterMileage,
                     boolean byMileage,
                     String driver,
                     Image[] images,
                     int[] imageIds) {
        this.remarks = remarks;
        this.bookingId = bookingId;
        this.tank = tank;
        this.masterMileage = masterMileage;
        this.byMileage = byMileage;
        this.driverName = driver;
        this.images = images;
        this.imageIds = imageIds;
    }

    public static class Builder {
        private String remarks;
        private int bookingId;
        private String tank;
        private int masterMileage;
        private boolean byMileage;
        private String driver;
        private Image[] images;
        private int[] imageIds;

        public Builder() {}

        public Builder setRemarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

        public Builder setBookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setTank(String tank) {
            this.tank = tank;
            return this;
        }

        public Builder setMasterMileage(int masterMileage) {
            this.masterMileage = masterMileage;
            return this;
        }

        public Builder setByMileage(boolean byMileage) {
            this.byMileage = byMileage;
            return this;
        }

        public Builder setDriver(String driver) {
            this.driver = driver;
            return this;
        }

        public Builder setImages(Image[] images) {
            this.images = images;
            return this;
        }

        public Builder setImageIds(int[] imageIds) {
            this.imageIds = imageIds;
            return this;
        }

        public Checklist build() {
            return new Checklist(remarks, bookingId, tank, masterMileage, byMileage,
                     driver, images, imageIds);
        }
    }

    public String getRemarks() {
        return remarks;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getTank() {
        return tank;
    }

    public int getMasterMileage() {
        return masterMileage;
    }

    public boolean isByMileage() {
        return byMileage;
    }

    public String getDriverName() {
        return driverName;
    }

    public Image[] getImages() {
        return images;
    }

    public int[] getImageIds() {
        return imageIds;
    }
}
