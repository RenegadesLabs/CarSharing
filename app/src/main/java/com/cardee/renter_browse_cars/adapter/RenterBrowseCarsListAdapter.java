package com.cardee.renter_browse_cars.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cardee.R;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.renter_browse_cars.presenter.RenterBrowseCarListContract;

import io.reactivex.subjects.PublishSubject;


public class RenterBrowseCarsListAdapter
        extends RecyclerView.Adapter<RenterBrowseCarsListAdapter.RenterBrowseCarsListItemViewHolder> {

    @Override
    public RenterBrowseCarsListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RenterBrowseCarsListItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RenterBrowseCarsListItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mAvatar;
        private final TextView mTitle;
        private final TextView mYear;
        private final TextView mLocation;
        private final TextView mType;
        private final AppCompatRatingBar mRating;
        private final TextView mRatingCount;
        private final AppCompatImageView mHeart;
        private final TextView mPrice;
        private final TextView mPeriod;
        private final AppCompatImageView mInstant;
        private final AppCompatImageView mCurbside;
        private final ImageView mPrimaryCarImage;
        private final ProgressBar mCarImageProgress;


        public RenterBrowseCarsListItemViewHolder(View itemView) {
            super(itemView);

            mAvatar = itemView.findViewById(R.id.iv_renterCarItemAvatar);
            mTitle = itemView.findViewById(R.id.tv_renterCarItemTitle);
            mYear = itemView.findViewById(R.id.tv_renterCarItemYear);
            mLocation = itemView.findViewById(R.id.tv_renterCarItemLocationText);
            mType = itemView.findViewById(R.id.tv_renterCarItemType);
            mRating = itemView.findViewById(R.id.rb_renterCatItemRating);
            mRatingCount = itemView.findViewById(R.id.tv_renterCarItemRatingCount);
            mHeart = itemView.findViewById(R.id.iv_renterCarItemHeart);
            mPrice = itemView.findViewById(R.id.tv_renterCarItemPrice);
            mPeriod = itemView.findViewById(R.id.tv_renterCarItemTime);
            mInstant = itemView.findViewById(R.id.iv_renterCarItemInstant);
            mCurbside = itemView.findViewById(R.id.iv_renterCarItemCurbside);
            mPrimaryCarImage = itemView.findViewById(R.id.car_primary_image);
            mCarImageProgress = itemView.findViewById(R.id.car_primary_image_loading_indicator);
        }

        private void bind(final OfferCar model,
                          RequestManager imageRequestManager,
                          PublishSubject<RenterBrowseCarListContract.CarEvent> observable) {

            if (imageRequestManager != null) {

                imageRequestManager
                        .load(model.getOwnerPicture())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(mAvatar);

                if (mCarImageProgress.getVisibility() == View.GONE) {
                    mCarImageProgress.setVisibility(View.VISIBLE);
                }

                imageRequestManager
                        .load(model.getPrimaryCarImage().getThumbnail())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                mCarImageProgress.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                mCarImageProgress.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .error(R.drawable.img_no_car)
                        .into(mPrimaryCarImage);
            }

            mTitle.setText(model.getTitle());
            mYear.setText(model.getYearOfManufacture());
            String location = model.getDistance() + " m " + "\u26AB";
            mLocation.setText(location);
            mType.setText(model.getBodyType());
            mRating.setRating(model.getRating());
            String ratingCount = "(" + model.getRatingCount() + ")";
            mRatingCount.setText(ratingCount);

        }
    }
}
