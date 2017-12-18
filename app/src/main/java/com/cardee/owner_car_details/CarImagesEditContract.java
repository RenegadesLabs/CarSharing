package com.cardee.owner_car_details;

import android.net.Uri;

import com.cardee.domain.owner.entity.Image;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface CarImagesEditContract {

    String CAR_ID = "car_id";
    String IMAGE_ID = "image_id";

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
