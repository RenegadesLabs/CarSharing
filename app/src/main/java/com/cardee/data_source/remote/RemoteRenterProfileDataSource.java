package com.cardee.data_source.remote;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.RenterProfileDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.profile.Profile;
import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest;
import com.cardee.data_source.remote.api.profile.response.RenterProfileResponse;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;

import io.reactivex.functions.Consumer;

public class RemoteRenterProfileDataSource implements RenterProfileDataSource {

    private static final String TAG = RemoteRenterProfileDataSource.class.getSimpleName();
    private static RemoteRenterProfileDataSource instance;
    private final Profile mApi;

    private RenterProfile cachedProfile;
    private RenterProfile cachedProfileById;

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

        if (cachedProfile != null) {
            callback.onSuccess(cachedProfile);
        }

        mApi.loadRenterProfile().subscribe(new Consumer<RenterProfileResponse>() {
            @Override
            public void accept(RenterProfileResponse renterProfileResponse) throws Exception {
                if (renterProfileResponse.isSuccessful()) {
                    callback.onSuccess(renterProfileResponse.getRenterProfile());
                    cachedProfile = renterProfileResponse.getRenterProfile();
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

    @Override
    public void loadRenterById(int id, ProfileCallback profileCallback) {

        if (cachedProfileById != null){
            profileCallback.onSuccess(cachedProfileById);
        }

        mApi.getRenterById(id).subscribe(new Consumer<RenterProfileResponse>() {
            @Override
            public void accept(RenterProfileResponse renterProfileResponse) throws Exception {
                if (renterProfileResponse.isSuccessful()) {
                    profileCallback.onSuccess(renterProfileResponse.getRenterProfile());
                    cachedProfileById = renterProfileResponse.getRenterProfile();
                    return;
                }
                handleErrorResponse(profileCallback, renterProfileResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, throwable.getMessage());
                profileCallback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
            }
        });
    }

    @Override
    public void changeRenterNote(ChangeNoteRequest changeNoteRequest, final NoResponseCallback callback) {
        mApi.updateRenterNote(changeNoteRequest).subscribe(new Consumer<NoDataResponse>() {
            @Override
            public void accept(NoDataResponse noDataResponse) throws Exception {
                if (noDataResponse.isSuccessful()) {
                    callback.onSuccess();
                    return;
                }
                handleErrorResponse(callback, noDataResponse);
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
