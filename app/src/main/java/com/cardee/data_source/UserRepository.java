package com.cardee.data_source;


import com.cardee.data_source.remote.api.auth.Authentication;

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE;
    private Authentication api;

    private UserRepository() {

    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    @Override
    public void login(String login, String password, UserDataSource.Callback callback) {

    }
}
