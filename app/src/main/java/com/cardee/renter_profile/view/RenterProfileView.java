package com.cardee.renter_profile.view;

import com.cardee.mvp.BaseView;

public interface RenterProfileView extends BaseView {

    void setProfileName(String name);

    void setProfileRating(String rating);

    void setProfileAge(String age);

    void setDrivingExp(String exp);

    void setBookings(String count);

    void setProfileImage(String photoLink);

    void setNote(String note);

    void setReviewsCount(String text);

    void onChangeImageSuccess();
}
