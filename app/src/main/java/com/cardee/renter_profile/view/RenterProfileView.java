package com.cardee.renter_profile.view;

import com.cardee.data_source.remote.api.profile.response.entity.RenterReview;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface RenterProfileView extends BaseView {

    void setProfileName(String name);

    void setProfileRating(String rating);

    void setProfileAge(String age);

    void setDrivingExp(String exp);

    void setDrivingExpYears(String years);

    void setBookings(String count);

    void setProfileImage(String photoLink);

    void setNote(String note);

    void setReviewsCount(String text);

    void setReviews(List<RenterReview> reviews);

    void onChangeImageSuccess();
}
