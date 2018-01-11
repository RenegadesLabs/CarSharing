package com.cardee.owner_bookings.car_checklist;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.entity.Checklist;
import com.cardee.domain.bookings.usecase.GetChecklist;
import com.cardee.owner_bookings.car_checklist.adapter.CarImagesAdapter;
import com.cardee.owner_bookings.car_checklist.strategy.ChecklistByMileageStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.ChecklistStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.PresentationStrategy;
import com.cardee.owner_bookings.car_checklist.view.ChecklistView;

import java.util.Arrays;


public class OwnerChecklistPresenter implements ChecklistContract.Presenter {

    private ChecklistContract.View mView;
    private ChecklistView mChecklistView;

    private int mBookingId;
    private final UseCaseExecutor mExecutor;
    private boolean isNotFetched = true;
    private PresentationStrategy mStrategy;

    public OwnerChecklistPresenter(int bookingId) {
        mBookingId = bookingId;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void setView(ChecklistContract.View view) {
        mView = view;
        if (view instanceof ChecklistView) {
            mChecklistView = (ChecklistView) view;
        }
    }

    @Override
    public void setStrategy(PresentationStrategy strategy) {
        mStrategy = strategy;
    }

    @Override
    public void getChecklist() {
        if (mView == null) {
            return;
        }

        mView.showProgress(isNotFetched);
        mExecutor.execute(new GetChecklist(), new GetChecklist.RequestValues(mBookingId),
                new UseCase.Callback<GetChecklist.ResponseValues>() {
                    @Override
                    public void onSuccess(GetChecklist.ResponseValues response) {
                        if (response.isSuccess()) {
                            isNotFetched = false;
                            mView.showProgress(isNotFetched);
                            chooseStrategy(response.getChecklist());
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgress(false);
                        mView.showMessage(R.string.error_occurred);
                    }
                });
    }

    @Override
    public void init() {
        if (isNotFetched) {
            getChecklist();
        }
    }

    @Override
    public void onDestroy() {
        isNotFetched = true;
    }

    @Override
    public void onHandover() {

    }

    @Override
    public void onAccurateCancel() {

    }

    @Override
    public void onAccurateConfirm() {

    }

    private void chooseStrategy(Checklist checklist) {
        if (checklist.isByMileage()) {
            mStrategy = new ChecklistByMileageStrategy(mChecklistView, this);
            mChecklistView.setMasterMileageValue(String.valueOf(checklist.getMasterMileage()) + " km");
        } else {
        mStrategy = new ChecklistStrategy(mChecklistView, this);
        mChecklistView.setMasterMileageValue(checklist.getTank());
        }
        CarImagesAdapter adapter = new CarImagesAdapter(mChecklistView.getContext());
        mChecklistView.setImagesAdapter(new CarImagesAdapter(mChecklistView.getContext()));
        adapter.setItems(Arrays.asList(checklist.getImages()));
    }
}
