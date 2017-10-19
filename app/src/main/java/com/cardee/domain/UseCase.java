package com.cardee.domain;

public interface UseCase<T> {

    void execute();

    interface RequestValues {

    }

    interface ResponseValues<C> {

        C getContent();

    }

    interface Callback<R> {
        void onSuccess(R response);

        void onError();
    }
}
