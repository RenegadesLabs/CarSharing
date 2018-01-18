package com.cardee.renter_browse_cars.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


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

        private final ImageView mAvatar


        public RenterBrowseCarsListItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
