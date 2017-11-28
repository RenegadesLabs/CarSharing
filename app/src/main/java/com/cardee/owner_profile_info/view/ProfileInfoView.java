package com.cardee.owner_profile_info.view;

import com.cardee.mvp.BaseView;

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
}
