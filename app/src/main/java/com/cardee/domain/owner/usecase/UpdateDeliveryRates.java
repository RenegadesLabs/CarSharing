package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity;
import com.cardee.domain.UseCase;


public class UpdateDeliveryRates implements UseCase<UpdateDeliveryRates.RequestValues,
        UpdateDeliveryRates.ResponseValues> {

    private CarEditRepository mRepository;

    public UpdateDeliveryRates() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        DeliveryRatesEntity deliveryRates = new DeliveryRatesEntity();
        deliveryRates.setBaseRate(values.getBaseRate());
        deliveryRates.setDistanceRate(values.getDistanceRate());
        deliveryRates.setProvideFreeDelivery(values.isFreeProvided());
        deliveryRates.setRentalDuration(values.getRentalDuration());
        mRepository.updateDeliveryRates(values.getId(), deliveryRates, new CarEditDataSource.Callback() {
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
        private final float mBaseRate;
        private final float mDistanceRate;
        private final boolean freeProvided;
        private final float mRentalDuration;

        public RequestValues(int id, float baseRate, float distanceRate, boolean freeProvided, float rentalDuration) {
            mId = id;
            mBaseRate = baseRate;
            mDistanceRate = distanceRate;
            this.freeProvided = freeProvided;
            mRentalDuration = rentalDuration;
        }

        public int getId() {
            return mId;
        }

        public float getBaseRate() {
            return mBaseRate;
        }

        public float getDistanceRate() {
            return mDistanceRate;
        }

        public boolean isFreeProvided() {
            return freeProvided;
        }

        public float getRentalDuration() {
            return mRentalDuration;
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
