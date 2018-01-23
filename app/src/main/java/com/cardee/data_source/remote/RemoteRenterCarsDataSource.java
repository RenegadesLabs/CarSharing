package com.cardee.data_source.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.offers.Offers;
import com.cardee.data_source.remote.api.offers.response.OffersResponse;
import com.cardee.domain.renter.entity.FilterRequest;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class RemoteRenterCarsDataSource implements RenterCarsDataSource {


    private static RemoteRenterCarsDataSource INSTANCE;

    private Offers mApi;

    public RemoteRenterCarsDataSource() {
        mApi = CardeeApp.retrofit.create(Offers.class);
    }

    public static RemoteRenterCarsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRenterCarsDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCars(Callback callback) {
        try {
            Response<OffersResponse> response = mApi.browseCars().execute();
            if (response.isSuccessful()) {
                callback.onSuccess(response.body().getOfferResponseBody());
                return;
            }
            handleErrorResponse(callback, response.body());
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public Disposable obtainCarsByFilter(FilterRequest filterRequest, Callback callback) {
        return mApi.obtainCarsByFilter(filterRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<OffersResponse>() {
                    @Override
                    public void onSuccess(OffersResponse response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(response.getOfferResponseBody());
                            return;
                        }
                        handleErrorResponse(callback, response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new Error(Error.Type.LOST_CONNECTION, Error.Message.CONNECTION_LOST));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void handleErrorResponse(Callback callback, BaseResponse response) {
        if (response == null) {
            callback.onError(new Error(Error.Type.OTHER, "null response"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            callback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            callback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            callback.onError(new Error(Error.Type.OTHER, "Undefined error"));
//            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}
