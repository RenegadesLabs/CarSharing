package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.common.entity.RentalRatesEntity;
import com.cardee.domain.UseCase;

public class UpdateHourlyRentalRates implements UseCase<UpdateHourlyRentalRates.RequestValues,
        UpdateHourlyRentalRates.ResponseValues> {

    private CarEditRepository mRepository;

    public UpdateHourlyRentalRates() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.updateRentalRatesHourly(values.getCarId(),
                new RentalRatesEntity(values.getRateFirst(), values.getRateSecond(),
                        values.getDiscountFirst(), values.getDiscountSecond(), values.getMinRental()),
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
        private final Integer carId;
        private final Double mRateFirst;
        private final Double mRateSecond;
        private final Integer mDiscountFirst;
        private final Integer mDiscountSecond;
        private final Integer mMinRental;

        public RequestValues(Integer carId, Double rateFirst, Double rateSecond,
                             Integer discountFirst, Integer discountSecond, Integer minRental) {
            this.carId = carId;
            mRateFirst = rateFirst;
            mRateSecond = rateSecond;
            mDiscountFirst = discountFirst;
            mDiscountSecond = discountSecond;
            mMinRental = minRental;
        }

        public Integer getCarId() {
            return carId;
        }

        public Double getRateFirst() {
            return mRateFirst;
        }

        public Double getRateSecond() {
            return mRateSecond;
        }

        public Integer getDiscountFirst() {
            return mDiscountFirst;
        }

        public Integer getDiscountSecond() {
            return mDiscountSecond;
        }

        public Integer getMinRental() {
            return mMinRental;
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
