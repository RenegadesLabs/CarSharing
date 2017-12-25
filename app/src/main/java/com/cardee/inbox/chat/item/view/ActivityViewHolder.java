package com.cardee.inbox.chat.item.view;

public interface ActivityViewHolder {

    void setUserData(String recipientName, String photoUrl);

    void setCarData(String carPhotoUrl, String carTitle, String licenseNumber);

    void setCarBookingData(String mStartDate, String mEndDate);

}