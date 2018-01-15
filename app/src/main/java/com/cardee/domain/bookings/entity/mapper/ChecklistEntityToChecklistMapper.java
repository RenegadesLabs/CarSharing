package com.cardee.domain.bookings.entity.mapper;

import com.cardee.data_source.remote.api.booking.response.entity.ChecklistEntity;
import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.domain.bookings.entity.Checklist;
import com.cardee.domain.owner.entity.Image;

public class ChecklistEntityToChecklistMapper {

    public static Checklist transform(ChecklistEntity entity) {
        ImageEntity[] imageEntities = entity.getImages();
        Image[] images = new Image[imageEntities.length];
        Integer[] imageIds = new Integer[imageEntities.length];
        for (int i = 0; i < images.length; i++) {
            ImageEntity target = imageEntities[i];
            images[i] = new Image(target.getImageId(), target.getLink(), target.getThumbnail(), target.isPrimary());
        }
        for (int i = 0; i < imageIds.length; i++) {
            ImageEntity imgEntity = imageEntities[i];
            imageIds[i] = imgEntity.getImageId();
        }

        String remarks = entity.getRemarks();
        int bookingId = entity.getBookingId();
        int masterMileage = entity.getMileage();
        float tank = entity.getTank();
        String tankText = getTankFullness(tank);
        String driverName = entity.getDriver().getName();

        boolean byMileage = entity.getFuelPolicy().getFuelPolicyId() == 0;

        return new Checklist.Builder()
                .setBookingId(bookingId)
                .setRemarks(remarks)
                .setMasterMileage(masterMileage)
                .setDriver(driverName)
                .setByMileage(byMileage)
                .setTankText(tankText)
                .setTank(tank)
                .setImages(images)
                .setImageIds(imageIds)
                .build();
    }

    private static String getTankFullness(float tankPart) {
        String fullness = "Full";

        if (tankPart < 1.f && tankPart >= 0.875f) {
            fullness = "7/8";
        } else if (tankPart < 0.875f && tankPart >= 0.75f) {
            fullness = "6/8";
        } else if (tankPart < 0.75f && tankPart >= 0.625f) {
            fullness = "5/8";
        } else if(tankPart < 0.625f && tankPart >= 0.5f) {
            fullness = "1/2";
        } else if(tankPart < 0.5f && tankPart >= 0.375f) {
            fullness = "3/8";
        } else if(tankPart < 0.375f && tankPart >= 0.25f) {
            fullness = "2/8";
        } else if(tankPart < 0.25f && tankPart >= 0.125f) {
            fullness = "1/8";
        }
        return fullness;
    }
}
