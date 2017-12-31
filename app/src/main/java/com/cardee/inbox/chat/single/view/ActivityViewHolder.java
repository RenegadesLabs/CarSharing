package com.cardee.inbox.chat.single.view;

import io.reactivex.functions.Consumer;

public interface ActivityViewHolder {

    void setUserData(String recipientName, String photoUrl);

    void setCarData(String carPhotoUrl, String carTitle, String licenseNumber);

    void setCarBookingData(String mStartDate, String mEndDate);

    void subscribeToInput(Consumer<String> consumer);
}