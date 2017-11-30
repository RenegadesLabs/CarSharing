package com.cardee.domain.owner.entity.mapper;

import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.CarReview;

import java.util.ArrayList;
import java.util.List;

public class ReviewAndCarToCarReviewMapper {

    public ReviewAndCarToCarReviewMapper() {
    }

    public CarReview transform(Review review, Car car) {
        return new CarReview(review.getRenter().getProfilePhoto(), review.getRenter().getName(), review.getReviewDate(), review.getReview(), car.getCarTitle(), review.getRate());
    }

    public List<CarReview> transform(List<Review> reviews, Car car) {
        if (reviews != null) {
            List<CarReview> carReviews = new ArrayList<>(reviews.size());
            for (Review review : reviews) {
                carReviews.add(transform(review, car));
            }
            return carReviews;
        }
        return null;
    }
}
