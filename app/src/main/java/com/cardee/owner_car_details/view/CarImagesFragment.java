package com.cardee.owner_car_details.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.Image;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_details.CarImagesEditContract;
import com.cardee.owner_car_details.presenter.CarImagesPresenter;
import com.cardee.owner_car_details.view.adapter.CarImagesAdapter;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;
import com.cardee.owner_car_details.view.listener.ImageViewListener;

import java.util.List;

import static com.cardee.owner_car_details.CarImagesEditContract.CAR_ID;


public class CarImagesFragment extends Fragment
        implements CarImagesEditContract.View, ImageViewListener {

    private static final int IMAGE_REQUEST_CODE = 102;
    private static final int REQUEST_PERMISSION_CODE = 103;

    private DetailsChangedListener parentListener;
    private NewCarFormsContract.Action pendingAction;
    private RecyclerView imagesGrid;
    private View topView;
    private View cautionView;
    private View loadingView;
    private CarImagesAdapter adapter;
    private CarImagesPresenter presenter;
    private Toast currentToast;

    private int carId;

    public static CarImagesFragment newInstance(int id) {
        CarImagesFragment fragment = new CarImagesFragment();
        Bundle args = new Bundle();
        args.putInt(CAR_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    private SimpleBinder binder = new SimpleBinder() {
        @Override
        public void push(Bundle args) {
            NewCarFormsContract.Action action = (NewCarFormsContract.Action)
                    args.getSerializable(NewCarFormsContract.ACTION);
            if (action == null) {
                return;
            }
            pendingAction = action;
            switch (action) {
                case SAVE:
                    if (loadingView.getVisibility() == View.VISIBLE) {
                        return;
                    }
                case FINISH:
                    parentListener.onFinish(NewCarFormsContract.Mode.IMAGE, pendingAction);
                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentListener = (DetailsChangedListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentListener = (DetailsChangedListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_images, container, false);
        carId = getArguments().getInt(CAR_ID);
        imagesGrid = rootView.findViewById(R.id.images_grid);
        topView = rootView.findViewById(R.id.top_block);
        cautionView = rootView.findViewById(R.id.images_caution);
        loadingView = rootView.findViewById(R.id.progress_layout);
        presenter = new CarImagesPresenter(this, carId);
        adapter = new CarImagesAdapter(getActivity());
        adapter.setImageViewListener(this);
        imagesGrid.setAdapter(adapter);
        imagesGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        imagesGrid.setItemAnimator(new DefaultItemAnimator());

        parentListener.onBind(binder);
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.IMAGE);
        presenter.init();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress(boolean show) {
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
        topView.setAlpha(show ? .5f : 1f);
        imagesGrid.setAlpha(show ? .5f : 1f);
        cautionView.setAlpha(show ? .5f : 1f);
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public void onImagesObtained(List<Image> images) {
        adapter.setItems(images);
    }

    @Override
    public void onImageSetPrimary(Image image) {

    }

    @Override
    public void onImageRemoved(Image image) {

    }

    @Override
    public void onImageClick(Image image) {
        Intent intent = new Intent(getActivity(), CarImagesActivity.class);
        Bundle args = new Bundle();
        args.putInt(CarImagesEditContract.CAR_ID, carId);
        args.putInt(CarImagesEditContract.IMAGE_ID, image.getImageId());
        intent.putExtras(args);
        getActivity().startActivity(intent);
    }

    @Override
    public void onAddNewClick() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE);
            return;
        }
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMAGE_REQUEST_CODE);
    }

    private boolean hasPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && data != null) {
            Uri uri = data.getData();
            presenter.onAddNewImage(uri);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onAddNewClick();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
