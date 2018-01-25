package com.cardee.settings;

import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.usecase.ObtainBookings;
import com.cardee.renter_browse_cars.RenterBrowseCarListContract;

public class Settings {

    private BookingState filterBooking;
    private ObtainBookings.Sort sortBooking;
    private RenterBrowseCarListContract.Sort sortOffers;

    private final SettingsManager manager;

    Settings(SettingsManager manager) {
        this.manager = manager;
        filterBooking = manager.getBookingFilter();
        sortBooking = manager.getBookingSort();
        sortOffers = manager.getOffersSort();
    }

    public BookingState getFilterBooking() {
        return filterBooking;
    }

    public ObtainBookings.Sort getSortBooking() {
        return sortBooking;
    }

    public RenterBrowseCarListContract.Sort getSortOffers() {
        return sortOffers;
    }

    public void setFilterBooking(BookingState filterBooking) {
        this.filterBooking = filterBooking;
        manager.saveBookingFilter(filterBooking);
    }

    public void setSortBooking(ObtainBookings.Sort sortBooking) {
        this.sortBooking = sortBooking;
        manager.saveBookingSort(sortBooking);
    }

    public void setSortOffers(RenterBrowseCarListContract.Sort sortOffers) {
        this.sortOffers = sortOffers;
        manager.saveOffersSort(sortOffers);
    }
}
