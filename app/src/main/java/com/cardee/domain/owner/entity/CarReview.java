package com.cardee.domain.owner.entity;

public class CarReview {

    String renterImageLink;
    String renterProfileName;
    String reviewDate;
    String reviewText;
    String carTitle;
    Float carRate;

    public CarReview(String renterImageLink, String renterProfileName, String reviewDate, String reviewText, String carTitle, Float carRate) {
        this.renterImageLink = renterImageLink;
        this.renterProfileName = renterProfileName;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
        this.carTitle = carTitle;
        this.carRate = carRate;
    }

    public String getRenterImageLink() {
        return renterImageLink;
    }

    public void setRenterImageLink(String renterImageLink) {
        this.renterImageLink = renterImageLink;
    }

    public String getRenterProfileName() {
        return renterProfileName;
    }

    public void setRenterProfileName(String renterProfileName) {
        this.renterProfileName = renterProfileName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public Float getCarRate() {
        return carRate;
    }

    public void setCarRate(Float carRate) {
        this.carRate = carRate;
    }
}
