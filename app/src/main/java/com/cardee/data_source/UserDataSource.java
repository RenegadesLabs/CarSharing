package com.cardee.data_source;

import com.cardee.domain.owner.usecase.Register;
import com.cardee.domain.user.usecase.AuthFcmToken;

public interface UserDataSource {

    void login(String login, String password, Callback callback);

    void loginSocial(String provider, String token, Callback callback);

    void sendFcmToken(String fcmToken, Callback callback);

    void sendEmailToChangePassword(String email, Callback callback);

    void changePassword(String key, String pass, String passConfirm, Callback callback);

    void checkUniqueLogin(String login, String password, Callback callback);

    void register(Register.RequestValues registerValues, Callback callback);

    void setProfilePicture(Register.RequestValues registerValues, Callback callback);

    interface Callback {

        void onSuccess(boolean success);

        void onError(Error error);
    }
}
