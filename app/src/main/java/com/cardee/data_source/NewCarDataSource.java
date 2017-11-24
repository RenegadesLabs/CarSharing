package com.cardee.data_source;


import android.net.Uri;

import com.cardee.data_source.remote.api.cars.request.NewCarData;

public interface NewCarDataSource {

    void obtainSavedCarData(CacheCallback callback);

    void saveCarData(NewCarData carData, boolean forcePush, Callback callback);

    void saveCarImage(Uri imgUri, boolean forcePush, ImageCacheCallback callback);


    interface Callback {
        void onSuccess(Integer newCarId);

        void onError(Error error);
    }

    interface CacheCallback {
        void onSuccess(NewCarData carData);

        void onError(Error error);
    }

    interface ImageCacheCallback {
        void onSuccess();

        void onError(Error error);
    }
}
