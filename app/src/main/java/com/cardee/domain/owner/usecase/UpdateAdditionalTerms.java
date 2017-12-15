package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.common.entity.RentalTermsAdditionalEntity;
import com.cardee.domain.UseCase;

public class UpdateAdditionalTerms implements UseCase<UpdateAdditionalTerms.RequestValues, UpdateAdditionalTerms.ResponseValues> {

    private CarEditRepository mRepository;

    public UpdateAdditionalTerms() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {

        mRepository.updateRentalAdditionalTerms(values.getId(), new RentalTermsAdditionalEntity(values.getAdditionalTerms()),
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
        private final String mAdditionalTerms;

        public RequestValues(int id, String additionalTerms) {
            mId = id;
            mAdditionalTerms = additionalTerms;
        }

        public String getAdditionalTerms() {
            return mAdditionalTerms;
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
