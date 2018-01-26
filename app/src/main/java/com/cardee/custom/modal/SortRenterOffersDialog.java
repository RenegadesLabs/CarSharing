package com.cardee.custom.modal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.renter_browse_cars.RenterBrowseCarListContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SortRenterOffersDialog extends BottomSheetDialogFragment {

    private static final String SORT = "_booking_sort";

    private RenterBrowseCarListContract.Sort currentSort;
    private SortSelectListener listener;

    @BindView(R.id.cars_sort_distance)
    TextView sortDistance;
    @BindView(R.id.cars_sort_price)
    TextView sortPrice;
    @BindView(R.id.cars_sort_ratings)
    TextView sortRatings;
    @BindView(R.id.cars_sort_popularity)
    TextView sortPopularity;

    @BindView(R.id.cars_sort_distance_selected)
    ImageView sortDistanceSelected;
    @BindView(R.id.cars_sort_price_selected)
    ImageView sortPriceSelected;
    @BindView(R.id.cars_sort_ratings_selected)
    ImageView sortRatingsSelected;
    @BindView(R.id.cars_sort_popularity_selected)
    ImageView sortPopularitySelected;

    private Unbinder unbinder;

    public static SortRenterOffersDialog getInstance(RenterBrowseCarListContract.Sort sort) {
        SortRenterOffersDialog fragment = new SortRenterOffersDialog();
        Bundle args = new Bundle();
        args.putSerializable(SORT, sort);
        fragment.setArguments(args);
        return fragment;
    }

    private BottomSheetBehavior.BottomSheetCallback mCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            currentSort = (RenterBrowseCarListContract.Sort) args.getSerializable(SORT);
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_offers_sort, null);
        unbinder = ButterKnife.bind(this, rootView);
        dialog.setContentView(rootView);
        changeSelection(currentSort);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) rootView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mCallback);
        }
        ViewParent parent = rootView.getParent();
        if (parent != null) {
            ((View) parent).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cars_sort_distance,
            R.id.cars_sort_price,
            R.id.cars_sort_ratings,
            R.id.cars_sort_popularity})
    public void onSortSelected(View sortView) {
        RenterBrowseCarListContract.Sort sort = null;
        switch (sortView.getId()) {
            case R.id.cars_sort_distance:
                sort = RenterBrowseCarListContract.Sort.DISTANCE;
                break;
            case R.id.cars_sort_price:
                sort = RenterBrowseCarListContract.Sort.PRICE;
                break;
            case R.id.cars_sort_ratings:
                sort = RenterBrowseCarListContract.Sort.RATINGS;
                break;
            case R.id.cars_sort_popularity:
                sort = RenterBrowseCarListContract.Sort.POPULARITY;
                break;
        }
        if (currentSort != null && !currentSort.equals(sort) ||
                (sort != null && !sort.equals(currentSort))) {
//            changeSelection(sort);
            if (listener != null) {
                listener.onSortSelected(sort);
            }
            dismiss();
        }
    }

    private void changeSelection(RenterBrowseCarListContract.Sort sort) {
        currentSort = sort;
        sortDistance.setTypeface(sort == null ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortPrice.setTypeface(RenterBrowseCarListContract.Sort.PRICE.equals(sort) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortRatings.setTypeface(RenterBrowseCarListContract.Sort.RATINGS.equals(sort) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortPopularity.setTypeface(RenterBrowseCarListContract.Sort.POPULARITY.equals(sort) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortDistanceSelected.setVisibility(RenterBrowseCarListContract.Sort.DISTANCE.equals(sort) ? View.VISIBLE : View.GONE);
        sortPriceSelected.setVisibility(RenterBrowseCarListContract.Sort.PRICE.equals(sort) ? View.VISIBLE : View.GONE);
        sortRatingsSelected.setVisibility(RenterBrowseCarListContract.Sort.RATINGS.equals(sort) ? View.VISIBLE : View.GONE);
        sortPopularitySelected.setVisibility(RenterBrowseCarListContract.Sort.POPULARITY.equals(sort) ? View.VISIBLE : View.GONE);
    }

    public void setSortSelectListener(SortSelectListener listener) {
        this.listener = listener;
    }

    public interface SortSelectListener {
        void onSortSelected(RenterBrowseCarListContract.Sort sort);
    }
}
