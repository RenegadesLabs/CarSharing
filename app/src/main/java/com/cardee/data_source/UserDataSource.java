package com.cardee.data_source;

public interface UserDataSource {

    void login(String login, String password, Callback callback);

    interface Callback {

        void onSuccess(boolean success);

        void onError(String message);
    }
}
