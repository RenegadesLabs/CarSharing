package com.cardee.calendar.domain;

import com.cardee.calendar.model.Error;

public interface UseCase<I extends UseCase.RequestValues, O extends UseCase.ResponseValues> {


    void execute(I request, Callback<O> callback);

    interface RequestValues {

    }

    interface ResponseValues {

    }

    interface Callback<R extends ResponseValues> {

        void onSuccess(R response);

        void onError(Error error);

    }

}
