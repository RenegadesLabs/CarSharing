package com.cardee.data_source.remote;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.RenterProfileDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.Profile;
import com.cardee.data_source.remote.api.profile.response.RenterProfileResponse;

import io.reactivex.functions.Consumer;

public class RemoteRenterProfileDataSource implements RenterProfileDataSource {

    private static final String TAG = RemoteRenterProfileDataSource.class.getSimpleName();
    private static RemoteRenterProfileDataSource instance;
    private final Profile mApi;

    public RemoteRenterProfileDataSource() {
        mApi = CardeeApp.retrofit.create(Profile.class);
    }

    public static RemoteRenterProfileDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteRenterProfileDataSource();
        }
        return instance;
    }

    @Override
    public void loadRenterProfile(final ProfileCallback callback) {
        mApi.loadRenterProfile().subscribe(new Consumer<RenterProfileResponse>() {
            @Override
            public void accept(RenterProfileResponse renterProfileResponse) throws Exception {
                if (renterProfileResponse.isSuccessful()) {
                    callback.onSuccess(renterProfileResponse.getRenterProfile());
                    return;
                }
                handleErrorResponse(callback, renterProfileResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, throwable.getMessage());
                callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
            }
        });
    }

    private void handleErrorResponse(BaseCallback callback, BaseResponse response) {
        if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            callback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            callback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            callback.onError(new Error(Error.Type.OTHER, "Undefined error"));
            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}
