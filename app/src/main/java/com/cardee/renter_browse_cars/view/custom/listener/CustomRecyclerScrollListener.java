package com.cardee.renter_browse_cars.view.custom.listener;

import android.support.v7.widget.RecyclerView;

public abstract class CustomRecyclerScrollListener extends RecyclerView.OnScrollListener {

    private static final float MINIMUM = 25;

    private int scrollDist = 0;
    private boolean isVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (isVisible && scrollDist > MINIMUM) {
            hide();
            scrollDist = 0;
            isVisible = false;
        }
        else if (!isVisible && scrollDist < - MINIMUM) {
            show();
            scrollDist = 0;
            isVisible = true;
        }

        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }
    }

    public abstract void show();
    public abstract void hide();
}


