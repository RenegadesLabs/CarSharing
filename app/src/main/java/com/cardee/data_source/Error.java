package com.cardee.data_source;

import android.support.annotation.NonNull;

public class Error {

    private final Type mType;
    private final String mMessage;

    public enum Type {
        AUTHORIZATION, WRONG_CREDENTIALS, SERVER, OTHER, LOST_CONNECTION
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
