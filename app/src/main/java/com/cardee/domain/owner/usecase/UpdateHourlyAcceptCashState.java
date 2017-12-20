package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;


public class UpdateHourlyAcceptCashState implements UseCase<UpdateHourlyAcceptCashState.RequestValues,
        UpdateHourlyAcceptCashState.ResponseValues> {

    private CarEditRepository mRepository;

    public UpdateHourlyAcceptCashState() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.updateAcceptCashHourly(values.getId(), values.isAcceptCash(),
                new CarEditDataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess(new UpdateHourlyAcceptCashState.ResponseValues(true));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int mId;
        private final boolean isAcceptCash;

        public RequestValues(int id, boolean isAcceptCash) {
            mId = id;
            this.isAcceptCash = isAcceptCash;
        }

        public int getId() {
            return mId;
        }

        public boolean isAcceptCash() {
            return isAcceptCash;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean isSuccess;

        public ResponseValues(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
