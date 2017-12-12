package com.cardee.data_source;


public interface CommonsDataSource {

    void sendFeedback(String message, OwnerProfileDataSource.NoResponseCallback callback);
}
