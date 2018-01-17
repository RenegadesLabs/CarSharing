package com.cardee.renter_browse_cars.presenter;


import com.cardee.data_source.Error;
import com.cardee.domain.UseCaseExecutor;

import io.reactivex.functions.Consumer;

public class RenterBrowseCarListPresenter implements Consumer<RenterBrowseCarListContract.CarEvent> {

    private final UseCaseExecutor mExecutor;
    private RenterBrowseCarListContract.View mView;

    private boolean firstStart = true;


    public RenterBrowseCarListPresenter(RenterBrowseCarListContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void accept(RenterBrowseCarListContract.CarEvent carEvent) throws Exception {

        switch (carEvent.getAction()) {
            case UPDATED:
                break;

            case OPEN:
                break;

            case ADD_TO_FAVORITE:
                break;
        }

    }

    public void loadItems() {
        if (firstStart && mView != null) {
            mView.showProgress(true);
        }

//        mExecutor.execute();

    }

    private void handleError(Error error) {

        if (error.isAuthError()) {
            mView.onUnauthorized();
        } else if (error.isConnectionError()) {
            mView.onConnectionLost();
        } else {
            mView.showMessage(error.getMessage());
        }
    }
}
