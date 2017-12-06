package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.common.entity.RentalTermsSecurityDepositEntity;
import com.cardee.domain.UseCase;

public class UpdateRentalSecurityDeposit implements UseCase<UpdateRentalSecurityDeposit.RequestValues, UpdateRentalSecurityDeposit.ResponseValues> {

    private final CarEditRepository mRepository;

    public UpdateRentalSecurityDeposit() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.updateRentalSecurityDeposit(values.getId(), new RentalTermsSecurityDepositEntity(values.isRequired(), values.getDescription()),
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
        private final boolean mRequired;
        private final String mDescription;

        public RequestValues(int id, boolean required, String description) {
            mId = id;
            mRequired = required;
            mDescription = description;
        }

        public int getId() {
            return mId;
        }

        public boolean isRequired() {
            return mRequired;
        }

        public String getDescription() {
            return mDescription;
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
