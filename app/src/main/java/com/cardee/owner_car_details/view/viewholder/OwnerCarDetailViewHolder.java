package com.cardee.owner_car_details.view.viewholder;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.owner.entity.Car;

public class OwnerCarDetailViewHolder {

    private View mRootView;

    private ViewPager mImagePager;
    private TextView mCarModel;
    private TextView mCarYear;
    private TextView mImagePageIndicator;
    private TextView mCarModelTitle;
    private TextView mCarShortSpecs;
    private TextView mCarLocatoin;
    private TextView mCarDescription;

    private View mBtnImageEdit;
    private View mBtnSpecsEdit;
    private View mBtnLocationEdit;
    private View mBtnDescriptionEdit;

    private ImagePagerAdapter mImageAdapter;

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
    }

    public void init(Car car) {

    }

    private class ImagePagerAdapter extends PagerAdapter {

        private final LayoutInflater mInflater;

        private ImagePagerAdapter(LayoutInflater inflater) {
            mInflater = inflater;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
