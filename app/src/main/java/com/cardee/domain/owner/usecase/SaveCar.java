package com.cardee.domain.owner.usecase;


import com.cardee.data_source.Error;
import com.cardee.data_source.NewCarDataSource;
import com.cardee.data_source.NewCarRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.NewCar;
import com.cardee.domain.owner.entity.mapper.NewCarDataMapper;

public class SaveCar implements UseCase<SaveCar.RequestValues, SaveCar.ResponseValues> {

    private final NewCarRepository repository;
    private final NewCarDataMapper mapper;

    public SaveCar() {
        repository = NewCarRepository.getInstance();
        mapper = new NewCarDataMapper();
    }

    @Override
    public void execute(SaveCar.RequestValues values, final Callback<SaveCar.ResponseValues> callback) {
        boolean readyToSubmit = values.isAllDone();
        NewCar carData = values.getCarData();
        repository.saveCarData(mapper.transform(carData), readyToSubmit,
                new NewCarDataSource.Callback() {
                    @Override
                    public void onSuccess(Integer newCarId) {
                        callback.onSuccess(new ResponseValues(newCarId));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final boolean allDone;
        private final NewCar carData;

        public RequestValues(NewCar carData, boolean allDone) {
            this.carData = carData;
            this.allDone = allDone;
        }

        public boolean isAllDone() {
            return allDone;
        }

        public NewCar getCarData() {
            return carData;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final Integer newCarId;

        public ResponseValues(Integer newCarId) {
            this.newCarId = newCarId;
        }

        public Integer getNewCarId() {
            return newCarId;
        }
    }
}
