package com.cardee.domain.owner.usecase;


import com.cardee.data_source.CommonsDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.RemoteCommonsDataSource;
import com.cardee.domain.UseCase;

public class SendFeedback implements UseCase<SendFeedback.RequestValues, SendFeedback.ResponseValues> {
    private final CommonsDataSource mRepository;

    public SendFeedback() {
        mRepository = RemoteCommonsDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.sendFeedback(values.getMessage(), new OwnerProfileDataSource.NoResponseCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(new ResponseValues());
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final String message;

        public RequestValues(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
    }
}
