package com.cardee.domain;

import com.cardee.data_source.Error;

public interface UseCase<V extends UseCase.RequestValues, R extends UseCase.ResponseValues> {

    void execute(V values, Callback<R> callback);

    interface RequestValues {

    }

    interface ResponseValues {

    }

    interface Callback<R> {
        void onSuccess(R response);

        void onError(Error error);
    }
}
