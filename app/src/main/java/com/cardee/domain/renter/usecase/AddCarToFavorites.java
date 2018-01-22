package com.cardee.domain.renter.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.data_source.RenterCarsRepository;
import com.cardee.domain.UseCase;


public class AddCarToFavorites implements UseCase<AddCarToFavorites.RequestValues, AddCarToFavorites.ResponseValues> {

    private final RenterCarsRepository mRepository;

    public AddCarToFavorites() {
        mRepository = RenterCarsRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.addCarToFavorites(values.getId(), new RenterCarsDataSource.Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(new ResponseValues(true));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int mId;

        public RequestValues(int id) {
            mId = id;
        }

        public int getId() {
            return mId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean success;

        public ResponseValues(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
