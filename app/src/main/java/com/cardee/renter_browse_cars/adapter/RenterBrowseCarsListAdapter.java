package com.cardee.renter_browse_cars.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cardee.R;
import com.cardee.custom.CustomRatingBar;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.renter_browse_cars.RenterBrowseCarListContract;
import com.cardee.util.glide.CircleTransform;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


public class RenterBrowseCarsListAdapter
        extends RecyclerView.Adapter<RenterBrowseCarsListAdapter.RenterBrowseCarsListItemViewHolder> {

    private final List<OfferCar> mOfferCars;

    private final LayoutInflater mInflater;
    private final RequestManager mGlideRequestManager;
    private final Context mContext;

    private SparseArray<RenterBrowseCarsListAdapter.RenterBrowseCarsListItemViewHolder> mHolders;

    private final PublishSubject<RenterBrowseCarListContract.CarEvent> mEventObservable;

    public RenterBrowseCarsListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mOfferCars = new ArrayList<>();
        mHolders = new SparseArray<>();
        mGlideRequestManager = Glide.with(context);
        mEventObservable = PublishSubject.create();
        mContext = context;
    }

    @Override
    public RenterBrowseCarsListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_renter_car, parent, false);
        return new RenterBrowseCarsListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RenterBrowseCarsListItemViewHolder holder, int position) {
        final OfferCar car = mOfferCars.get(position);
        holder.bind(car, mGlideRequestManager, mEventObservable, mContext);
        mHolders.put(car.getCarId(), holder);
    }

    @Override
    public int getItemCount() {
        return mOfferCars.size();
    }

    public static class RenterBrowseCarsListItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mAvatar;
        private final TextView mTitle;
        private final TextView mLocation;
        private final TextView mType;
        private final CustomRatingBar mRating;
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
                          PublishSubject<RenterBrowseCarListContract.CarEvent> observable, Context context) {

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
                        .transform(new CircleTransform(context))
                        .into(mAvatar);

                if (mCarImageProgress.getVisibility() == View.GONE) {
                    mCarImageProgress.setVisibility(View.VISIBLE);
                }

                imageRequestManager
                        .load(model.getPrimaryCarThumbnail())
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

            SpannableString title = new SpannableString(model.getTitle());
            title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTitle.setText(title);

            SpannableString year = new SpannableString("   " + model.getYearOfManufacture());
            year.setSpan(new RelativeSizeSpan(0.9f), 0, year.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTitle.append(year);

            String location;
            int distance = model.getDistance();
            if (distance > 999) {
                location = new DecimalFormat("#.#").format(distance / 1000f) + "km \u2022 " + model.getAddress();
            } else {
                location = distance <= 0 ? model.getAddress() : distance + "m \u2022 " + model.getAddress();
            }
            mLocation.setText(location);
            String type = model.getBodyType() + " " + String.valueOf(model.getSeatCapacity());
            mType.setText(type);
            mRating.setScore(model.getRating());
            String ratingCount = "(" + model.getRatingCount() + ")";
            mRatingCount.setText(ratingCount);
            String price = "$" + new DecimalFormat("#.##").format(model.getCost());
            mHeart.setImageResource(model.isFavorite() ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite);
            mHeart.setOnClickListener(view -> {
                observable.onNext(new RenterBrowseCarListContract.CarEvent(model,
                        RenterBrowseCarListContract.Action.FAVORITE));
                mHeart.setImageResource(model.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_filled);
            });
            mPrice.setText(price);
            mInstant.setVisibility(model.isInstantBooking() ? View.VISIBLE : View.GONE);
            mCurbside.setVisibility(model.isCurbsideDelivery() ? View.VISIBLE : View.GONE);
            mPrimaryCarImage.setOnClickListener(view ->
                    observable.onNext(new RenterBrowseCarListContract.CarEvent(model, RenterBrowseCarListContract.Action.OPEN)));
        }
    }


    public void insert(List<OfferCar> cars) {
        mOfferCars.clear();
        mOfferCars.addAll(cars);
        notifyDataSetChanged();
    }

    public void update(OfferCar car) {
        int index = mOfferCars.indexOf(car);
        if (index == -1) {
            return;
        }
        mOfferCars.set(index, car);
        RenterBrowseCarsListItemViewHolder holder = mHolders.get(car.getCarId());
        if (holder != null) {
            holder.bind(car, null, mEventObservable, mContext);
            return;
        }
        notifyItemChanged(index);
    }

    public void remove(OfferCar car) {
        int index = mOfferCars.indexOf(car);
        if (index > -1) {
            mOfferCars.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void subscribe(Consumer<RenterBrowseCarListContract.CarEvent> consumer) {
        mEventObservable.subscribe(consumer);
        if (!mOfferCars.isEmpty()) {
            notifyDataSetChanged();
        }
    }

    public void recycle() {
        mOfferCars.clear();
        mHolders.clear();
    }
}
