package com.cardee.domain.bookings.entity.mapper;


import com.cardee.CardeeApp;
import com.cardee.data_source.remote.api.booking.response.BookingEntity;
import com.cardee.data_source.remote.api.common.entity.ImageEntity;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.owner.entity.Image;
import com.cardee.util.DateStringDelegate;

public class BookingEntityToBookingMapper {

    private DateStringDelegate delegate;

    public BookingEntityToBookingMapper() {
        delegate = new DateStringDelegate(CardeeApp.context);
    }

    public Booking transform(BookingEntity entity) {
        ImageEntity[] imageEntities = entity.getCar().getImages();
        Image[] images = new Image[imageEntities.length];
        Image primary = null;
        for (int i = 0; i < images.length; i++) {
            ImageEntity target = imageEntities[i];
            images[i] = new Image(target.getImageId(), target.getLink(), target.getThumbnail(), target.isPrimary());
            if (primary == null && images[i].isPrimary()) {
                primary = images[i];
            }
        }
        if(primary == null){
            primary = new Image(0, "", "", true);
        }
        String beginTime = delegate.formatShortBookingDate(entity.getTimeBegin());
        String endTime = delegate.formatShortBookingDate(entity.getTimeEnd());
        String createTime = delegate.formatShortBookingDate(entity.getDateCreated());
        return new Booking.Builder()
                .setTotalAmount(entity.getTotalAmount())
                .setTimeBegin(beginTime)
                .setTimeEnd(endTime)
                .setBookingStateType(BookingState.valueOf(entity.getBookingStateType()))
                .setBookingStateName(entity.getBookingStateName())
                .setBookingId(entity.getBookingId())
                .setDateCreated(createTime)
                .setCarId(entity.getCar().getCarId())
                .setCarTitle(entity.getCar().getCarTitle())
                .setManufactureYear(entity.getCar().getManufactureYear())
                .setPlateNumber(entity.getCar().getPlateNumber())
                .setImages(images)
                .setPrimaryImage(primary)
                .build();
    }
}
