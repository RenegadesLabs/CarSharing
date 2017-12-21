package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.domain.UseCase;

public class UpdateHourlyFuelPolicy implements UseCase<UpdateHourlyFuelPolicy.RequestValues,
        UpdateHourlyFuelPolicy.ResponseValues> {

    private final CarEditRepository mRepository;

    public UpdateHourlyFuelPolicy() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        FuelPolicyEntity fuelPolicy = new FuelPolicyEntity();
        fuelPolicy.setFuelPolicyName("");
        fuelPolicy.setFuelPolicyId(values.getFuelPolicyId());
        fuelPolicy.setAmountPayMileage(values.getAmountPayMileage());
        mRepository.updateFuelPolicyHourly(values.getCarId(), fuelPolicy,
                new CarEditDataSource.Callback() {
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
        private final int mCarId;
        private final int mFuelPolicyId;
        private final float mAmountPayMileage;

        public RequestValues(int carId, int fuelPolicyId, float amountPayMileage) {
            mCarId = carId;
            mFuelPolicyId = fuelPolicyId;
            mAmountPayMileage = amountPayMileage;
        }

        public int getCarId() {
            return mCarId;
        }

        public int getFuelPolicyId() {
            return mFuelPolicyId;
        }

        public float getAmountPayMileage() {
            return mAmountPayMileage;
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
