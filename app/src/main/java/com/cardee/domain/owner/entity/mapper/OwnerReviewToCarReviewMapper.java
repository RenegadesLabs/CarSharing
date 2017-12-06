package com.cardee.domain.owner.entity.mapper;

import com.cardee.data_source.remote.api.profile.response.entity.OwnerReview;
import com.cardee.domain.owner.entity.CarReview;

import java.util.ArrayList;
import java.util.List;

public class OwnerReviewToCarReviewMapper {

    public OwnerReviewToCarReviewMapper() {
    }

    public CarReview transform(OwnerReview review) {
        return new CarReview(review.getAuthor().getProfilePhoto(), review.getAuthor().getName(), review.getReviewDate(), review.getReview(), review.getCar().getCarTitle(), review.getRating());
    }

    public List<CarReview> transform(OwnerReview[] reviews) {
        if (reviews != null) {
            List<CarReview> carReviews = new ArrayList<>(reviews.length);
            for (OwnerReview review : reviews) {
                carReviews.add(transform(review));
            }
            return carReviews;
        }
        return null;
    }
}
