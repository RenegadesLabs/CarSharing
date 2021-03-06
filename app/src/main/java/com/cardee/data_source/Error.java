package com.cardee.data_source;

import android.support.annotation.NonNull;

import com.cardee.CardeeApp;
import com.cardee.R;

public class Error {

    private final Type mType;
    private final String mMessage;

    public enum Type {
        AUTHORIZATION,
        WRONG_CREDENTIALS,
        SERVER,
        OTHER,
        LOST_CONNECTION,
        INTERNAL,
        INVALID_REQUEST,
        WRONG_AUTHENTICATION
    }

    public static class Message {

        public final static String WRONG_CREDENTIALS = "HTTP 422 UNPROCESSABLE ENTITY";

        public final static String LOGIN_NOT_VALID = "Not a valid login";

        public final static String LOGIN_DO_EXIST = "Login do exist, please choose another one.";
        public final static String LOGIN_DO_EXIST_NEW = CardeeApp.context.getString(R.string.acc_exists);

        public final static String PASSWORD_LENGTH = "Length must be between 3 and 36.";

        public final static String PASSWORD_DO_EXIST = "Password do exist.";

        public final static String WRONG_AUTHENTICATION = "Wrong password.";

        public final static String CONNECTION_LOST = "Connection lost";

        public final static String INVALID_CARD = CardeeApp.context.getString(R.string.credit_card_invalid_card_warning);
    }

    public Error(@NonNull Type type, @NonNull String message) {
        mType = type;
        mMessage = message;
    }

    public boolean isAuthError() {
        return mType.equals(Type.AUTHORIZATION);
    }


    public boolean isConnectionError() {
        return mType.equals(Type.LOST_CONNECTION);
    }

    public Type getErrorType() {
        return mType;
    }

    public String getMessage() {
        return mMessage;
    }
}
