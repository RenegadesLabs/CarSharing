package com.cardee.owner_bookings.car_checklist.presenter;

import android.net.Uri;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.entity.Checklist;
import com.cardee.domain.bookings.usecase.AddImageToChecklist;
import com.cardee.domain.bookings.usecase.GetChecklist;
import com.cardee.domain.bookings.usecase.SaveChecklist;
import com.cardee.domain.owner.entity.Image;
import com.cardee.owner_bookings.car_checklist.adapter.CarSquareImagesAdapter;
import com.cardee.owner_bookings.car_checklist.strategy.OwnerChecklistByMileageStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.OwnerChecklistStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.PresentationStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.RenterChecklistByMileageStrategy;
import com.cardee.owner_bookings.car_checklist.strategy.RenterEditChecklistStrategy;
import com.cardee.owner_bookings.car_checklist.view.ChecklistView;
import com.cardee.owner_car_details.view.listener.ImageViewListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChecklistPresenter implements OwnerChecklistContract.Presenter, ImageViewListener {

    private OwnerChecklistContract.View mView;
    private ChecklistView mChecklistView;

    private int mBookingId;
    private boolean isRenter;
    private final UseCaseExecutor mExecutor;
    private boolean isNotFetched = true;
    private PresentationStrategy mStrategy;

    private Checklist mChecklistObj;
    private List<Integer> mImageIdsList;
    private List<Image> mImages;

    private CarSquareImagesAdapter mAdapter;

    private View mCallbacks;

    public ChecklistPresenter(int bookingId, boolean renter) {
        mBookingId = bookingId;
        isRenter = renter;
        mExecutor = UseCaseExecutor.getInstance();
        mImageIdsList = new ArrayList<>();
        mImages = new ArrayList<>();
    }

    @Override
    public void setView(OwnerChecklistContract.View view) {
        mView = view;
        if (view instanceof ChecklistView) {
            mChecklistView = (ChecklistView) view;
            mAdapter = new CarSquareImagesAdapter(mChecklistView.getContext(), true);
            mAdapter.setImageViewListener(this);
        }
    }

    @Override
    public void setStrategy(PresentationStrategy strategy) {
        mStrategy = strategy;
    }

    @Override
    public void getChecklist() {
        if (mView == null) {
            return;
        }

        mView.showProgressPetrolMileage(isNotFetched);
        mExecutor.execute(new GetChecklist(), new GetChecklist.RequestValues(mBookingId),
                new UseCase.Callback<GetChecklist.ResponseValues>() {
                    @Override
                    public void onSuccess(GetChecklist.ResponseValues response) {
                        if (response.isSuccess()) {
                            isNotFetched = false;
                            mView.showProgressPetrolMileage(isNotFetched);
                            mChecklistObj = response.getChecklist();
                            mImageIdsList.addAll(Arrays.asList(mChecklistObj.getImageIds()));
                            chooseStrategy();
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showProgressPetrolMileage(false);
                        mView.showMessage(R.string.error_occurred);
                    }
                });
    }

    @Override
    public void onAddNewImage(Uri uri) {
        if (uri == null) {
            return;
        }

        mExecutor.execute(new AddImageToChecklist(), new AddImageToChecklist.RequestValues(mBookingId, uri),
                new UseCase.Callback<AddImageToChecklist.ResponseValues>() {
                    @Override
                    public void onSuccess(AddImageToChecklist.ResponseValues response) {
                        if (mImageIdsList.contains(response.getImageId())) {
                            mView.showMessage(R.string.only_one_image);
                            return;
                        }
                        mImages.add(new Image(response.getImageId(), uri.toString(), "", false));
                        mAdapter.setItems(mImages);
                        mImageIdsList.add(response.getImageId());
                    }

                    @Override
                    public void onError(Error error) {
                        mView.showMessage(R.string.error_occurred);
                    }
                });
    }

    @Override
    public void init() {
        if (isNotFetched) {
            getChecklist();
        }
    }

    @Override
    public void onDestroy() {
        isNotFetched = true;
    }

    @Override
    public void onHandover() {
        saveChecklist();
    }

    @Override
    public void onAccurateCancel() {

    }

    @Override
    public void onAccurateConfirm() {

    }

    private void chooseStrategy() {
        if (mChecklistObj.isByMileage()) {
            if (isRenter) {
                mStrategy = new RenterChecklistByMileageStrategy(mChecklistView, this);
            } else {
                mStrategy = new OwnerChecklistByMileageStrategy(mChecklistView, this);
            }
            mChecklistView.setMasterMileageValue(String.valueOf(mChecklistObj.getMasterMileage()));
        } else {
            if (isRenter) {
                mStrategy = new RenterEditChecklistStrategy(mChecklistView, this);
            } else {
                mStrategy = new OwnerChecklistStrategy(mChecklistView, this);
            }
            mChecklistView.setPetrolValue(mChecklistObj.getTankText());
        }
        mImages.addAll(Arrays.asList(mChecklistObj.getImages()));
        mAdapter.setItems(mImages);
        mChecklistView.setImagesAdapter(mAdapter);
    }

    private void saveChecklist() {

        mView.onHandingOverProcessing(true);

        mExecutor.execute(new SaveChecklist(),
                new SaveChecklist.RequestValues(mBookingId, mChecklistView.getRemarksText(),
                        mChecklistView.getTankFullness(), mChecklistView.getMileage(), listToArray(mImageIdsList)),
                new UseCase.Callback<SaveChecklist.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveChecklist.ResponseValues response) {
                        if (response.isSuccess()) {
                            mView.onHandingOverProcessing(false);
                            mView.showMessage(R.string.saved_successfully);
                            mCallbacks.onHandover(mBookingId);
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        mView.onHandingOverProcessing(false);
                        mView.showMessage(R.string.error_occurred);
                    }
                });
    }

    @Override
    public void onImageClick(Image image) {

    }

    @Override
    public void onAddNewClick() {
        if (mCallbacks == null) {
            return;
        }
        mCallbacks.onPickPhoto();
    }


    @Override
    public void setViewCallbacks(View view) {
        mCallbacks = view;
    }

    @Override
    public void setViewRenterUpdatedCallbacks(OwnerRenterUpdatedChecklistPresenter.View callbacks) {

    }

    public interface View {
        void onPickPhoto();

        void onHandover(int bookingId);
    }

    private Integer[] listToArray(List<Integer> list) {
        Integer[] ids = new Integer[list.size()];
        list.toArray(ids);
        return ids;
    }

}
