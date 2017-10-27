package com.cardee.owner_home.presenter;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.owner.usecase.GetCars;
import com.cardee.domain.owner.usecase.SwitchDaily;
import com.cardee.domain.owner.usecase.SwitchHourly;
import com.cardee.owner_home.OwnerCarListContract;

import java.util.List;

import io.reactivex.functions.Consumer;

public class OwnerCarsPresenter implements Consumer<OwnerCarListContract.CarEvent> {


    private OwnerCarListContract.View mView;

    private final GetCars mGetCars;
    private final SwitchDaily mSwitchDaily;
    private final SwitchHourly mSwitchHourly;

    private final UseCaseExecutor mExecutor;

    public OwnerCarsPresenter(OwnerCarListContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();

        mGetCars = new GetCars();
        mSwitchDaily = new SwitchDaily();
        mSwitchHourly = new SwitchHourly();
    }

    public void loadItems(boolean forceRefresh) {
        GetCars.RequestValues requestValues = new GetCars.RequestValues(forceRefresh);
        mExecutor.execute(mGetCars, requestValues, new UseCase.Callback<GetCars.ResponseValues>() {
            @Override
            public void onSuccess(GetCars.ResponseValues response) {
                List<Car> cars = response.getCars();
                if (mView != null) {
                    mView.setItems(cars);
                }
            }

            @Override
            public void onError(Error error) {

            }
        });
    }

    public void destroy() {
        mView = null;
    }

    @Override
    public void accept(OwnerCarListContract.CarEvent carEvent) throws Exception {

    }
}
