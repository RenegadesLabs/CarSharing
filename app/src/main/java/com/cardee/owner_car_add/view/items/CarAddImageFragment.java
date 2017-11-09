package com.cardee.owner_car_add.view.items;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_add.view.CarAddView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CarAddImageFragment extends CarAddBaseFragment {

    private Unbinder mUnbinder;

    @BindView(R.id.iv_addCarImage)
    public ImageView addCarImage;

    private CarAddView mView;

    private CarAddActivity.CarInfoPassCallback mPassDataCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_image, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.fl_addCarUploadImage)
    public void onUploadClicked() {

    }

    public void setUserPhoto(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Glide.with(getActivity())
                .load(stream.toByteArray())
                .placeholder(R.drawable.placeholder_user_img)
                .into(addCarImage);
    }

    @Override
    void saveArguments(Bundle b, boolean onNext) {

    }

    @Override
    public void setPassDataCallback(CarAddActivity.CarInfoPassCallback callback) {
        mPassDataCallback = callback;
    }

    @Override
    public void setViewListener(CarAddView listener) {
        mView = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
