package com.cardee.owner_home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cardee.R;
import com.cardee.domain.owner.entity.Car;
import com.cardee.owner_home.OwnerCarListContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarListItemViewHolder> {

    private final List<Car> mCarViewItems;

    private final LayoutInflater mInflater;
    private final RequestManager mGlideRequestManager;

    private SparseArray<CarListItemViewHolder> mHolders;

    private Consumer<OwnerCarListContract.CarEvent> mConsumer;

    public CarListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCarViewItems = new ArrayList<>();
        mHolders = new SparseArray<>();
        mGlideRequestManager = Glide.with(context);
    }

    @Override
    public CarListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_owner_car, parent, false);
        return new CarListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CarListItemViewHolder holder, int position) {
        final Car car = mCarViewItems.get(position);
        holder.bind(car, mGlideRequestManager);
        mHolders.put(car.getCarId(), holder);
        if (mConsumer == null) {
            return;
        }
        Observable.create(new ObservableOnSubscribe<OwnerCarListContract.CarEvent>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<OwnerCarListContract.CarEvent> emitter) throws Exception {
                holder.mDailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        emitter.onNext(new OwnerCarListContract.CarEvent(car, OwnerCarListContract.Action.DAILY_SWITCHED));
                    }
                });
                holder.mHourlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        emitter.onNext(new OwnerCarListContract.CarEvent(car, OwnerCarListContract.Action.HOURLY_SWITCHED));
                    }
                });
                holder.mPrimaryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarListContract.CarEvent(car, OwnerCarListContract.Action.OPEN));
                    }
                });
                holder.mLocationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarListContract.CarEvent(car, OwnerCarListContract.Action.LOCATION_CLICKED));
                    }
                });
                holder.mDailyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarListContract.CarEvent(car, OwnerCarListContract.Action.DAILY_CLICKED));
                    }
                });
                holder.mHourlyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(new OwnerCarListContract.CarEvent(car, OwnerCarListContract.Action.HOURLY_CLICKED));
                    }
                });
            }
        }).subscribe(mConsumer);
    }

    @Override
    public int getItemCount() {
        return mCarViewItems.size();
    }

    public static class CarListItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleView;
        private final ImageView mPrimaryImage;
        private final TextView mYearView;
        private final TextView mLicenceNumberView;
        private final Switch mHourlySwitch;
        private final Switch mDailySwitch;
        private final TextView mHourlyView;
        private final TextView mDailyView;
        private final TextView mLocationView;
        private final ProgressBar mLoadingIndicator;

        public CarListItemViewHolder(View itemView) {
            super(itemView);

            mTitleView = itemView.findViewById(R.id.car_title);
            mPrimaryImage = itemView.findViewById(R.id.car_primary_image);
            mYearView = itemView.findViewById(R.id.car_year);
            mLicenceNumberView = itemView.findViewById(R.id.car_licence_plate_number);
            mHourlySwitch = itemView.findViewById(R.id.car_hourly_toggle);
            mDailySwitch = itemView.findViewById(R.id.car_daily_toggle);
            mHourlyView = itemView.findViewById(R.id.car_hourly_selector);
            mDailyView = itemView.findViewById(R.id.car_daily_selector);
            mLocationView = itemView.findViewById(R.id.car_location_selector);
            mLoadingIndicator = itemView.findViewById(R.id.car_primary_image_loading_indicator);
        }

        private void bind(Car model, RequestManager imageRequestManager) {
            mTitleView.setText(model.getCarTitle());
            mYearView.setText(model.getManufactureYear());
            mLicenceNumberView.setText(model.getLicenceNumber());

            mHourlySwitch.setChecked(model.isAvailableHourly());
            mDailySwitch.setChecked(model.isAvailableDaily());

            mHourlyView.setEnabled(model.isAvailableHourly());
            mDailyView.setEnabled(model.isAvailableDaily());

            if (mLoadingIndicator.getVisibility() != View.VISIBLE) {
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }
            imageRequestManager
                    .load(model.getPrimaryImageLink())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            mLoadingIndicator.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mLoadingIndicator.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.img_car_placeholder)
                    .into(mPrimaryImage);
        }
    }

    public void insert(List<Car> cars) {
        for (Car car : cars) {
            if (!mCarViewItems.contains(car)) {
                mCarViewItems.add(car);
            }
        }
        notifyDataSetChanged();
    }

    public void update(Car car) {
        int index = mCarViewItems.indexOf(car);
        if (index == -1) {
            return;
        }
        mCarViewItems.set(index, car);
        CarListItemViewHolder holder = mHolders.get(car.getCarId());
        if (holder != null) {
            update(car, holder);
            return;
        }
        notifyItemChanged(index);
    }

    private void update(Car car, CarListItemViewHolder holder) {

    }

    public void remove(Car car) {
        int index = mCarViewItems.indexOf(car);
        if (index > -1) {
            mCarViewItems.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void subscribe(Consumer<OwnerCarListContract.CarEvent> consumer) {
        mConsumer = consumer;
        if (!mCarViewItems.isEmpty()) {
            notifyDataSetChanged();
        }
    }

    public void recycle() {
        mCarViewItems.clear();
        mHolders.clear();
    }
}
