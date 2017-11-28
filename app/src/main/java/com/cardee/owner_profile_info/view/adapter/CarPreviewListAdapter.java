package com.cardee.owner_profile_info.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.R;
import com.cardee.domain.owner.entity.Car;

import java.util.ArrayList;
import java.util.List;

public class CarPreviewListAdapter extends RecyclerView.Adapter<CarPreviewListAdapter.CarListViewHolder> {

    private final List<Car> mCarItems;
    private final LayoutInflater mInflater;
    private final RequestManager mGlideRequestManager;

    public CarPreviewListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGlideRequestManager = Glide.with(context);
        mCarItems = new ArrayList<>();
    }

    @Override
    public CarListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_owner_profile_info_car, parent, false);
        return new CarPreviewListAdapter.CarListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarListViewHolder holder, int position) {
        final Car car = mCarItems.get(position);
        holder.setImage(car.getPrimaryImageLink(), mGlideRequestManager);
        holder.setCarTitle(car.getCarTitle());
//        holder.setCarRate();
    }

    @Override
    public int getItemCount() {
        return mCarItems.size();
    }

    public static class CarListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mCarImage;
        private final TextView mCarTitle;
        private final TextView mCarRate;

        public CarListViewHolder(View itemView) {
            super(itemView);

            mCarImage = itemView.findViewById(R.id.car_image);
            mCarTitle = itemView.findViewById(R.id.car_text);
            mCarRate = itemView.findViewById(R.id.rate_text);
        }

        public void setImage(String link, RequestManager imageRequestManager) {
            imageRequestManager.load(link)
                    .error(R.drawable.img_no_car)
                    .into(mCarImage);
        }

        public void setCarTitle(String text) {
            mCarTitle.setText(text);
        }

        public void setCarRate(String rate) {
            mCarRate.setText(rate);
        }
    }
}
