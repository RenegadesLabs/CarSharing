package com.cardee.inbox.chat.single.view;

import android.content.Context;

import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface ActivityViewHolder {

    void initAdapter(Context context);

    void setUserData(String recipientName, String photoUrl);

    void setCarData(String carPhotoUrl, String carTitle, String licenseNumber);

    void setCarBookingData(String mStartDate, String mEndDate);

    void setMessageList(List<ChatMessage> messageList);

    void showProgress(boolean isLoading);

    void updateMessagePreview(int messageId);

    void subscribeToInput(Consumer<String> consumer);
}