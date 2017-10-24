package com.cardee.owner_home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarListItemViewHolder> {

    private final List<String> mCarViewItems;

    private LayoutInflater mInflater;

    public CarListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mCarViewItems = new ArrayList<>();
        mCarViewItems.add("Volvo");
        mCarViewItems.add("Ford");
        mCarViewItems.add("Honda");
        mCarViewItems.add("BMW");
        mCarViewItems.add("Mercedes-Benz");
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

        public CarListItemViewHolder(View itemView) {
            super(itemView);

            mTitleView = itemView.findViewById(R.id.car_title);
        }

        private void bind(String model) {
            mTitleView.setText(model);
        }
    }
}
