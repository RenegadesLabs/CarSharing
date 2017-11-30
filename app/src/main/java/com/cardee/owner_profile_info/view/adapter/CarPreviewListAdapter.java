package com.cardee.owner_profile_info.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.cardee.domain.owner.entity.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class CarPreviewListAdapter extends RecyclerView.Adapter<CarPreviewListAdapter.CarListViewHolder> {

    private final List<Car> mCarItems;
    private final LayoutInflater mInflater;
    private final RequestManager mGlideRequestManager;
    private final PublishSubject<Car> onClickSubject;

    public CarPreviewListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGlideRequestManager = Glide.with(context);
        mCarItems = new ArrayList<>();
        onClickSubject = PublishSubject.create();
    }

    @Override
    public CarListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_owner_profile_info_car, parent, false);
        return new CarPreviewListAdapter.CarListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CarListViewHolder holder, int position) {
        final Car car = mCarItems.get(position);
        holder.setImage(car.getPrimaryImageLink(), mGlideRequestManager);
        holder.setCarTitle(car.getCarTitle());
        holder.setCarRate(car.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubject.onNext(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarItems.size();
    }

    public void insert(List<Car> cars) {
        for (Car car : cars) {
            if (!mCarItems.contains(car)) {
                mCarItems.add(car);
            }
        }
        notifyDataSetChanged();
    }

    public static class CarListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mCarImage;
        private final ImageView mCarRateStar;
        private final TextView mCarTitle;
        private final TextView mCarRate;
        private final ProgressBar mLoadingIndicator;

        public CarListViewHolder(View itemView) {
            super(itemView);

            mCarImage = itemView.findViewById(R.id.car_image);
            mCarTitle = itemView.findViewById(R.id.car_text);
            mCarRate = itemView.findViewById(R.id.rate_text);
            mCarRateStar = itemView.findViewById(R.id.star_rate);
            mLoadingIndicator = itemView.findViewById(R.id.car_image_loading_indicator);
        }

        public void setImage(String link, RequestManager imageRequestManager) {
            if (mLoadingIndicator.getVisibility() != View.VISIBLE) {
                mLoadingIndicator.setVisibility(View.VISIBLE);
                mCarRateStar.setVisibility(View.GONE);
            }
            imageRequestManager.load(link)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            mLoadingIndicator.setVisibility(View.GONE);
                            mCarRateStar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mLoadingIndicator.setVisibility(View.GONE);
                            mCarRateStar.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .error(R.drawable.img_no_car)
                    .into(mCarImage);
        }

        public void setCarTitle(String text) {
            mCarTitle.setText(text);
        }

        public void setCarRate(float rate) {
            mCarRate.setText((String.format(Locale.getDefault(), "%.1f", rate)));
        }
    }

    public void subscribe(Consumer<Car> consumer) {
        onClickSubject.subscribe(consumer);
        if (!mCarItems.isEmpty()) {
            notifyDataSetChanged();
        }
    }
}
