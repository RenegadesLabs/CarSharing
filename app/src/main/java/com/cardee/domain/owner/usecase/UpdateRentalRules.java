package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.domain.UseCase;

public class UpdateRentalRules implements UseCase<UpdateRentalRules.RequestValues, UpdateRentalRules.ResponseValues> {

    private final CarEditRepository mRepository;

    public UpdateRentalRules() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.updateRentalRules(values.getId(), new CarRuleEntity(values.getOtherRules()),
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
//        private final CarRuleEntity.Rule[] mRules;
        private final String mOtherRules;

        public RequestValues(int id, String otherRules) {
            mId = id;
            mOtherRules = otherRules;
        }

        public int getId() {
            return mId;
        }

        public String getOtherRules() {
            return mOtherRules;
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
