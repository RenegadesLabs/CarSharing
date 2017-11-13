package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.presenter.CarImagePresenter;
import com.cardee.owner_car_add.view.NewCarContract;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CarImageFragment extends Fragment implements NewCarContract.View {

    private DetailsChangedListener parentListener;
    private NewCarContract.Action pendingAction;

    private UploadImageListener mUploadListener;

    private Unbinder mUnbinder;

    @BindView(R.id.iv_addCarImage)
    public ImageView addCarImage;
    private CarImagePresenter presenter;
    private SimpleBinder binder = new SimpleBinder() {
        @Override
        public void push(Bundle args) {
            NewCarContract.Action action = (NewCarContract.Action)
                    args.getSerializable(NewCarContract.ACTION);
            if (action == null) {
                return;
            }
            pendingAction = action;
            switch (action) {
                case SAVE:
                case FINISH:
                    onFinish();
                    break;
                case UPDATE:
                    if (args.containsKey(NewCarContract.URI)) {
                        Uri uri = args.getParcelable(NewCarContract.URI);
                        setUserPhoto(uri);
                        presenter.saveCarImageToCache(uri);
                    }
                    break;
            }
        }
    };

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
        presenter = new CarImagePresenter(this);
        return v;
    }

    @OnClick(R.id.fl_addCarUploadImage)
    public void onUploadClicked() {
        mUploadListener.onImageUpload();
    }

    private void setUserPhoto(Uri uri) {
        Glide.with(getActivity())
                .load(uri)
                .placeholder(R.drawable.img_car_sample)
                .into(addCarImage);
    }

    private void setUserPhoto(File file) {
        Glide.with(getActivity())
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.img_car_sample)
                .error(R.drawable.img_car_sample)
                .into(addCarImage);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.init();
        parentListener.onBind(binder);
        parentListener.onModeDisplayed(NewCarContract.Mode.IMAGE);
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
        if (carData.getImage() != null) {
            String imagePath = carData.getImage();
            File imageFile = new File(imagePath);
            if (imageFile.exists() && imageFile.canRead()) {
                setUserPhoto(imageFile);
            }
        }
    }

    @Override
    public void onFinish() {
        parentListener.onFinish(NewCarContract.Mode.IMAGE, pendingAction);
    }
}
