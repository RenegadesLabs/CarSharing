package com.cardee.calendar.model;

public class Error {

    public enum Type {
        NOT_FOUND, UNAVAILABLE, BAD_REQUEST
    }

    private final String message;
    private final Type type;

    public Error(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public Type getType(){
        return type;
    }
}
