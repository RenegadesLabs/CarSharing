package com.cardee.data_source;

import android.support.annotation.NonNull;

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
        INVALID_REQUEST
    }

    public static class Message {

        public final static String WRONG_CREDENTIALS = "HTTP 422 UNPROCESSABLE ENTITY";

        public final static String LOGIN_NOT_VALID = "Not a valid login";

        public final static String LOGIN_DO_EXIST = "Login do exist, please choose another one.";

        public final static String PASSWORD_LENGTH = "Length must be between 3 and 36.";

        public final static String PASSWORD_DO_EXIST = "Password do exist.";
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
