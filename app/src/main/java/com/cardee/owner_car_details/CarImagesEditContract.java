package com.cardee.owner_car_details;

import android.net.Uri;

import com.cardee.domain.owner.entity.Image;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface CarImagesEditContract {

    interface View extends BaseView {

        void onImagesObtained(List<Image> images);

        void onImageSetPrimary(Image image);

        void onImageRemoved(Image image);
    }

    interface Presenter extends BasePresenter {

        void onImageSetPrimary(Image image);

        void onImageRemove(Image image);

        void onAddNewImage(Uri uri);
    }

}
