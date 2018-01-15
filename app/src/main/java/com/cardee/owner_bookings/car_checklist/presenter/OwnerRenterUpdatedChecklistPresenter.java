package com.cardee.owner_bookings.car_checklist.presenter;

import android.net.Uri;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.entity.Checklist;
import com.cardee.domain.bookings.usecase.GetChecklist;
import com.cardee.owner_bookings.car_checklist.adapter.CarSquareImagesAdapter;
import com.cardee.owner_bookings.car_checklist.strategy.PresentationStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.RenterUpdatedChecklistStrategy;
import com.cardee.owner_bookings.car_checklist.view.ChecklistView;

import java.util.Arrays;

public class OwnerRenterUpdatedChecklistPresenter implements ChecklistContract.Presenter {

    private ChecklistContract.View mView;
    private ChecklistView mChecklistView;

    private PresentationStrategy mStrategy;

    private UseCaseExecutor mExecutor;

    private final int mBookingId;


    public OwnerRenterUpdatedChecklistPresenter(int bookingId) {
        mExecutor = UseCaseExecutor.getInstance();
        mBookingId = bookingId;
    }

    @Override
    public void setView(ChecklistContract.View view) {
        mView = view;
        if (view instanceof ChecklistView) {
            mChecklistView = (ChecklistView) view;
            mStrategy = new RenterUpdatedChecklistStrategy(mChecklistView, this);
        }
    }

    @Override
    public void setViewCallbacks(OwnerChecklistPresenter.View callbacks) {

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
        mView.showProgress(true);

        mExecutor.execute(new GetChecklist(), new GetChecklist.RequestValues(mBookingId),
                new UseCase.Callback<GetChecklist.ResponseValues>() {
                    @Override
                    public void onSuccess(GetChecklist.ResponseValues response) {
                        mView.showProgress(false);
                        if (response.isSuccess()) {
                            fillData(response.getChecklist());
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
    public void onAddNewImage(Uri uri) {

    }

    @Override
    public void init() {
        getChecklist();
    }

    @Override
    public void onDestroy() {

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

    private void fillData(Checklist checklist) {
        if (checklist.isByMileage()) {
            String s = checklist.getMasterMileage() + " " +
                    mChecklistView.getContext().getString(R.string.car_rental_rates_per_km) + ". " +
                    mChecklistView.getContext().getString(R.string.car_rental_fuel_policy_by_mileage) + ".";
            mChecklistView.setMileagePetrolDesc(s);
        } else {
            String s = checklist.getMasterMileage() + " " +
                    mChecklistView.getContext().getString(R.string.owner_handover_tank_filled) +
                    mChecklistView.getContext().getString(R.string.car_rental_fuel_policy_similar_lvl) + ".";
            mChecklistView.setMileagePetrolDesc(s);
        }
        mChecklistView.setFirstTitle(checklist.isByMileage());
        CarSquareImagesAdapter adapter = new CarSquareImagesAdapter(mChecklistView.getContext());
        adapter.setItems(Arrays.asList(checklist.getImages()));
        mChecklistView.setImagesAdapter(adapter);

        mChecklistView.setRemarksText(checklist.getRemarks());
    }
}
