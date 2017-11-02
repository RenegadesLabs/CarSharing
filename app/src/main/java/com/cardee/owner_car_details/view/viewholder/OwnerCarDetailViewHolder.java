package com.cardee.owner_car_details.view.viewholder;


import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.R;
import com.cardee.domain.owner.entity.DetailedCar;
import com.cardee.domain.owner.entity.Image;
import com.cardee.owner_car_details.OwnerCarDetailsContract;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class OwnerCarDetailViewHolder {

    private final View mRootView;

    private final ViewPager mImagePager;
    private final TextView mCarModel;
    private final TextView mCarYear;
    private final TextView mImagePageIndicator;
    private final TextView mCarModelTitle;
    private final TextView mCarShortSpecs;
    private final TextView mCarLocatoin;
    private final TextView mCarDescription;

    private final View mBtnImageEdit;
    private final View mBtnSpecsEdit;
    private final View mBtnLocationEdit;
    private final View mBtnDescriptionEdit;

    private final ImagePagerAdapter mImageAdapter;
    private RequestManager mGlideRequestManager;
    private Observable<OwnerCarDetailsContract.CarDetailEvent> mObservable;

    public OwnerCarDetailViewHolder(View rootView) {
        mRootView = rootView;

        mImagePager = rootView.findViewById(R.id.car_image_pager);
        mCarModel = rootView.findViewById(R.id.car_title);
        mCarYear = rootView.findViewById(R.id.car_year);
        mImagePageIndicator = rootView.findViewById(R.id.car_page_indicator);
        mCarModelTitle = rootView.findViewById(R.id.car_details_model);
        mCarShortSpecs = rootView.findViewById(R.id.car_details_value);
        mCarLocatoin = rootView.findViewById(R.id.car_location_value);
        mCarDescription = rootView.findViewById(R.id.car_description_value);

        mBtnImageEdit = rootView.findViewById(R.id.car_images_edit);
        mBtnSpecsEdit = rootView.findViewById(R.id.car_info_edit);
        mBtnLocationEdit = rootView.findViewById(R.id.car_location_edit);
        mBtnDescriptionEdit = rootView.findViewById(R.id.car_description_edit);

        mImageAdapter = new ImagePagerAdapter();
        createEventObservable();
        mGlideRequestManager = Glide.with(rootView.getContext());
    }


    private void createEventObservable() {
        mObservable = Observable.create(new ObservableOnSubscribe<OwnerCarDetailsContract.CarDetailEvent>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<OwnerCarDetailsContract.CarDetailEvent> emitter) throws Exception {
                mBtnImageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarDetailsContract.CarDetailEvent(OwnerCarDetailsContract.Action.EDIT_IMAGES));
                    }
                });
                mBtnSpecsEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarDetailsContract.CarDetailEvent(OwnerCarDetailsContract.Action.EDIT_SPECS));
                    }
                });
                mBtnLocationEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarDetailsContract.CarDetailEvent(OwnerCarDetailsContract.Action.EDIT_LOCATION));
                    }
                });
                mBtnDescriptionEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarDetailsContract.CarDetailEvent(OwnerCarDetailsContract.Action.EDIT_DESCRIPTION));
                    }
                });
            }
        });
    }

    public void bind(DetailedCar details) {

    }

    public void subscribe(Consumer<OwnerCarDetailsContract.CarDetailEvent> consumer) {
        mObservable.subscribe(consumer);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        @DrawableRes
        private int mDefaultImageId = R.drawable.img_car_placeholder;

        private Image[] mImages;
        boolean mEmpty;

        private SparseArray<ImageView> mViews;

        private ImagePagerAdapter() {
            mViews = new SparseArray<>();
        }

        private void setItems(Image[] images) {
            if (images == null || images.length == 0) {
                initEmptyPager();
                notifyDataSetChanged();
                return;
            }
            mImages = images;
            mEmpty = false;
            notifyDataSetChanged();
        }

        private void initEmptyPager() {
            mEmpty = true;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mViews.get(position);
            if (view == null) {
                view = new ImageView(container.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (!mEmpty) {
                    mGlideRequestManager
                            .load(mImages[position].getLink())
                            .error(mDefaultImageId)
                            .placeholder(mDefaultImageId)
                            .into(view);
                } else {
                    view.setImageResource(mDefaultImageId);
                }
                mViews.put(position, view);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mEmpty ? 1 : mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
