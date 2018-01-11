package com.cardee.domain.bookings.entity;

public class Rate {

    private final Integer rating;
    private final String rateName;
    private final Integer rateId;

    public Rate(Integer rating, String rateName, Integer rateId) {
        this.rating = rating;
        this.rateId = rateId;
        this.rateName = rateName;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getRateId() {
        return rateId;
    }

    public String getRateName() {
        return rateName;
    }
}
