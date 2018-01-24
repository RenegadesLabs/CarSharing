package com.cardee.renter_browse_cars.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cardee.domain.renter.entity.OfferCar;

import java.util.List;

public class RenterBrowseCarsSearchListAdapter extends ArrayAdapter<OfferCar> {

    private List<OfferCar> mResults;
    private Context mCtx;

    public RenterBrowseCarsSearchListAdapter(@NonNull Context context, int resource, List<OfferCar> objects) {
        super(context, resource, objects);
        mCtx = context;
        mResults = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(mCtx).inflate(android.R.layout.activity_list_item, null);
        }
        TextView tv = v.findViewById(android.R.id.text1);
        tv.setText(mResults.get(position).getTitle());
        return v;
    }

    public void update(List<OfferCar> results) {
        if (!mResults.isEmpty()) {
            mResults.clear();
        }
        mResults.addAll(results);
        notifyDataSetChanged();
    }
}
