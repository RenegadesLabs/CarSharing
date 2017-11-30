package com.cardee.domain.owner.usecase;

import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.CarEditRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.common.entity.RentalTermsRequirementsEntity;
import com.cardee.domain.UseCase;

public class UpdateRentalRequirements implements UseCase<UpdateRentalRequirements.RequestValues, UpdateRentalRequirements.ResponseValues> {

    private CarEditRepository mRepository;

    public UpdateRentalRequirements() {
        mRepository = CarEditRepository.getInstance();
    }

    @Override
    public void execute(UpdateRentalRequirements.RequestValues values, final Callback<UpdateRentalRequirements.ResponseValues> callback) {

        mRepository.updateRentalRequirements(values.getId(), new RentalTermsRequirementsEntity(values.getMinAge(),
                        values.getMaxAge(),
                        values.getExperience()),
                new CarEditDataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess(new UpdateRentalRequirements.ResponseValues(true));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });

    }

    public static class RequestValues implements UseCase.RequestValues {

        private final int mId;
        private final int mMinAge;
        private final int mMaxAge;
        private final int mExperience;

        public RequestValues(int id, int minAge, int maxAge, int experience) {
            mId = id;
            mMinAge = minAge;
            mMaxAge = maxAge;
            mExperience = experience;
        }

        public int getMinAge() {
            return mMinAge;
        }

        public int getMaxAge() {
            return mMaxAge;
        }

        public int getExperience() {
            return mExperience;
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
