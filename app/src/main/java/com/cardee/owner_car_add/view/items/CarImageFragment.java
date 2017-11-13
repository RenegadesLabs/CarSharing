package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.presenter.CarImagePresenter;
import com.cardee.owner_car_add.view.NewCarFormsContract;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CarImageFragment extends Fragment implements NewCarFormsContract.View {

    private DetailsChangedListener parentListener;
    private NewCarFormsContract.Action pendingAction;

    private UploadImageListener mUploadListener;

    private Unbinder mUnbinder;

    private CarImagePresenter mPresenter;

    @BindView(R.id.iv_addCarImage)
    public ImageView addCarImage;

    public static Fragment newInstance() {
        Fragment fragment = new CarImageFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailsChangedListener) {
            parentListener = (DetailsChangedListener) context;
        }
        if (context instanceof UploadImageListener) {
            mUploadListener = (UploadImageListener) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailsChangedListener) {
            parentListener = (DetailsChangedListener) activity;
        }
        if (activity instanceof UploadImageListener) {
            mUploadListener = (UploadImageListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_image, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        mPresenter = new CarImagePresenter(this, getActivity());
        Glide.with(getActivity())
                .load(mPresenter.getImageFileInByteArray())
                .placeholder(R.drawable.img_car_sample)
                .into(addCarImage);
        return v;
    }

    @OnClick(R.id.fl_addCarUploadImage)
    public void onUploadClicked() {
        mUploadListener.onImageUpload();
    }

    public void setUserPhoto(Uri uri) {
        Glide.with(getActivity())
                .load(mPresenter.saveCarImageToCache(uri).getImageFileInByteArray())
                .placeholder(R.drawable.img_car_sample)
                .into(addCarImage);
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.IMAGE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }

    @Override
    public void setCarData(CarData carData) {
    }

    @Override
    public void onFinish() {
        parentListener.onFinish(NewCarFormsContract.Mode.IMAGE, pendingAction);
    }
}
