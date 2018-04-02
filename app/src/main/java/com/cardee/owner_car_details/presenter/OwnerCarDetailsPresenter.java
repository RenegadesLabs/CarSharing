package com.cardee.owner_car_details.presenter;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetCar;
import com.cardee.domain.owner.usecase.SaveCarTitle;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_details.OwnerCarDetailsContract;

import io.reactivex.functions.Consumer;

public class OwnerCarDetailsPresenter
        implements OwnerCarDetailsContract.Presenter, Consumer<OwnerCarDetailsContract.CarDetailEvent> {

    private static final int PROGRESS_CANCEL_TIMEOUT = 300;

    private final UseCaseExecutor mExecutor;
    private final GetCar mGetCar;
    private final SaveCarTitle saveCar;

    private boolean mNeedProgress;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private OwnerCarDetailsContract.View mView;
    private int mCarId;

    public OwnerCarDetailsPresenter(OwnerCarDetailsContract.View view, int carId) {
        mView = view;
        mCarId = carId;

        mExecutor = UseCaseExecutor.getInstance();
        mGetCar = new GetCar();
        saveCar = new SaveCarTitle();
    }

    public void get() {
        showCancelableProgress();
        GetCar.RequestValues values = new GetCar.RequestValues(mCarId);
        mExecutor.execute(mGetCar, values, new UseCase.Callback<GetCar.ResponseValues>() {
            @Override
            public void onSuccess(GetCar.ResponseValues response) {
                if (mView != null) {
                    cancelProgress();
                    mView.setCar(response.getCar());
                }
            }

            @Override
            public void onError(Error error) {
                if (mView != null) {
                    cancelProgress();
                    handleError(error);
                }
            }
        });
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

    public void destroy() {
        mView = null;
    }

    @Override
    public void accept(OwnerCarDetailsContract.CarDetailEvent event) throws Exception {
        Bundle args = new Bundle();
        args.putInt(NewCarFormsContract.CAR_ID, mCarId);
        switch (event.getAction()) {
            case EDIT_IMAGES:
                args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.IMAGES);
                mView.moveToImages(args);
                break;
            case EDIT_SPECS:
                mView.moveToSpecs(null);
                break;
            case EDIT_LOCATION:
                args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.LOCATION);
                mView.moveToLocation(args);
                break;
            case EDIT_DESCRIPTION:
                args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.DESCRIPTION);
                mView.moveToDescription(args);
                break;
        }
    }

    private void showCancelableProgress() {
        mNeedProgress = true;
        mHandler.postDelayed(() -> {
            if (mNeedProgress && mView != null) {
                mView.showProgress(mNeedProgress);
                mNeedProgress = false;
            }
        }, PROGRESS_CANCEL_TIMEOUT);
    }

    private void cancelProgress() {
        mNeedProgress = false;
        mView.showProgress(false);
    }

    public void initTitleEditText(TextInputEditText title) {
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_button_20dp, 0);
                }
            }
        });

        title.setOnTouchListener((view, motionEvent) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawRight = title.getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawRight != null) {
                    if (motionEvent.getRawX() >= (title.getRight() - drawRight.getBounds().width()) - title.getPaddingEnd()) {
                        title.setText("");
                        return true;
                    }
                }
            }
            return false;
        });
    }

    public void saveTitle(String titleText) {
        saveCar.execute(new SaveCarTitle.RequestValues(mCarId, titleText), new UseCase.Callback<SaveCarTitle.ResponseValues>() {
            @Override
            public void onSuccess(SaveCarTitle.ResponseValues response) {
                if (mView != null) {
                    mView.showMessage(R.string.saved_successfully);
                    mView.onTitleSaved();
                }
            }

            @Override
            public void onError(Error error) {
                if (mView != null) {
                    mView.showMessage(error.getMessage());
                }
            }
        });
    }
}
