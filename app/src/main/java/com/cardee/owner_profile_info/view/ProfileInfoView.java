package com.cardee.owner_profile_info.view;

import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.entity.CarReview;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface ProfileInfoView extends BaseView {

    void setProfileName(String name);

    void setProfileRating(String rating);

    void setAcceptance(String percent);

    void setResponseText(String time);

    void setBookings(String count);

    void setProfileImage(String photoLink);

    void setNote(String note);

    void setCarsCount(String text);

    void setReviewsCount(String text);

    void setCars(List<Car> items);

    void setCarReviews(List<CarReview> reviews);

    void openItem(Car car);

    void setMinutes(String minutes);
}
