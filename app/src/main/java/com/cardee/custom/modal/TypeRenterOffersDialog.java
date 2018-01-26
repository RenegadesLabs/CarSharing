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

public class TypeRenterOffersDialog extends BottomSheetDialogFragment {

    private static final String TYPE = "_booking_sort";

    private RenterBrowseCarListContract.VehicleType currentType;
    private TypeSelectListener listener;

    @BindView(R.id.cars_type_personal_t)
    TextView typePersonal;
    @BindView(R.id.cars_type_private_t)
    TextView typePrivate;
    @BindView(R.id.cars_type_commercial_t)
    TextView typeCommercial;

    @BindView(R.id.cars_type_personal_selected)
    ImageView typePersonalSelected;
    @BindView(R.id.cars_type_private_selected)
    ImageView typePrivateSelected;
    @BindView(R.id.cars_type_commercial_selected)
    ImageView typeCommercialSelected;

    private Unbinder unbinder;

    public static TypeRenterOffersDialog getInstance(RenterBrowseCarListContract.VehicleType type) {
        TypeRenterOffersDialog fragment = new TypeRenterOffersDialog();
        Bundle args = new Bundle();
        args.putSerializable(TYPE, type);
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
            currentType = (RenterBrowseCarListContract.VehicleType) args.getSerializable(TYPE);
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_offers_type, null);
        unbinder = ButterKnife.bind(this, rootView);
        dialog.setContentView(rootView);
        changeSelection(currentType);
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

    @OnClick({R.id.cars_type_personal,
            R.id.cars_type_private,
            R.id.cars_type_commercial})
    public void onSortSelected(View sortView) {
        RenterBrowseCarListContract.VehicleType type = null;
        switch (sortView.getId()) {
            case R.id.cars_type_personal:
//                type = RenterBrowseCarListContract.VehicleType.PERSONAL;
                break;
            case R.id.cars_type_private:
                type = RenterBrowseCarListContract.VehicleType.PRIVATE;
                break;
            case R.id.cars_type_commercial:
                type = RenterBrowseCarListContract.VehicleType.COMMERCIAL;
                break;
        }
        if (currentType != null && !currentType.equals(type) ||
                (type != null && !type.equals(currentType))) {
//            changeSelection(sort);
            if (listener != null) {
                listener.onTypeSelected(type);
            }
            dismiss();
        }
    }

    private void changeSelection(RenterBrowseCarListContract.VehicleType type) {
        currentType = type;
        typePersonal.setTypeface(type == null ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        typePrivate.setTypeface(RenterBrowseCarListContract.VehicleType.PRIVATE.equals(type) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        typeCommercial.setTypeface(RenterBrowseCarListContract.VehicleType.COMMERCIAL.equals(type) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        typePersonalSelected.setVisibility(type == null ? View.VISIBLE : View.GONE);
        typePrivateSelected.setVisibility(RenterBrowseCarListContract.VehicleType.PRIVATE.equals(type) ? View.VISIBLE : View.GONE);
        typeCommercialSelected.setVisibility(RenterBrowseCarListContract.VehicleType.COMMERCIAL.equals(type) ? View.VISIBLE : View.GONE);
    }

    public void setTypeSelectListener(TypeSelectListener listener) {
        this.listener = listener;
    }

    public interface TypeSelectListener {
        void onTypeSelected(RenterBrowseCarListContract.VehicleType sort);
    }
}
