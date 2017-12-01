package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.common.entity.RentalTermsInsuranceEntity;
import com.cardee.domain.UseCase;

public class UpdateRentalInsuranceExcess implements UseCase<UpdateRentalInsuranceExcess.RequestValues, UpdateRentalInsuranceExcess.ResponseValues> {

    private final CarEditRepository mRepository;

    public UpdateRentalInsuranceExcess() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(UpdateRentalInsuranceExcess.RequestValues values, final Callback<UpdateRentalInsuranceExcess.ResponseValues> callback) {

        mRepository.updateRentalInsuranceExcess(values.getId(), new RentalTermsInsuranceEntity(values.getInsuranceExcess()),
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

        private final int mId;
        private final String mInsuranceExcess;

        public RequestValues(int id, String insuranceExcess) {
            mId = id;
            mInsuranceExcess = insuranceExcess;
        }

        public String getInsuranceExcess() {
            return mInsuranceExcess;
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
