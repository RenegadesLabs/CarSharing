package com.cardee.owner_home.view.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardee.R;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarListItemViewHolder> {

    private final List<ViewCar> mCarViewItems;

    private LayoutInflater mInflater;

    public CarListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mCarViewItems = new ArrayList<>();
        mCarViewItems.add(new ViewCar("Volvo", R.drawable.auto_1));
        mCarViewItems.add(new ViewCar("Ford", R.drawable.auto_2));
        mCarViewItems.add(new ViewCar("Honda", R.drawable.auto_3));
        mCarViewItems.add(new ViewCar("BMW", R.drawable.auto_4));
        mCarViewItems.add(new ViewCar("Mercedes-Benz", R.drawable.auto_5));
    }


    @Override
    public CarListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_owner_car, parent, false);
        return new CarListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarListItemViewHolder holder, int position) {
        holder.bind(mCarViewItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mCarViewItems.size();
    }

    public static class CarListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleView;
        private ImageView mPrimaryImage;

        public CarListItemViewHolder(View itemView) {
            super(itemView);

            mTitleView = itemView.findViewById(R.id.car_title);
            mPrimaryImage = itemView.findViewById(R.id.car_primary_image);
        }

        private void bind(ViewCar model) {
            mTitleView.setText(model.title);
            mPrimaryImage.setImageResource(model.pic);
        }
    }

    private class ViewCar {
        private String title;
        @DrawableRes
        private int pic;

        public ViewCar() {

        }

        public ViewCar(String title, int pic) {
            this.title = title;
            this.pic = pic;
        }
    }
}
