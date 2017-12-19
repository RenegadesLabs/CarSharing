package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_add.presenter.CarDescriptionPresenter;
import com.cardee.owner_car_add.view.CarDescriptionView;
import com.cardee.owner_car_details.CarDetailsEditContract;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;


public class CarDescriptionFragment extends Fragment
        implements CarDescriptionView {

    private DetailsChangedListener parentListener;
    private CarDescriptionPresenter presenter;
    private EditText descriptionView;
    private View cautionView;
    private View progressLayout;
    private Toast currentToast;

    public static CarDescriptionFragment newInstance(int carId) {
        CarDescriptionFragment fragment = new CarDescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(CarDetailsEditContract.CAR_ID, carId);
        fragment.setArguments(args);
        return fragment;
    }

    private SimpleBinder binder = new SimpleBinder() {
        @Override
        public void push(Bundle args) {
            NewCarFormsContract.Action action = (NewCarFormsContract.Action)
                    args.getSerializable(NewCarFormsContract.ACTION);
            if (action == null) {
                return;
            }
            switch (action) {
                case SAVE:
                    if (progressLayout.getVisibility() != View.VISIBLE) {
                        presenter.onSave(descriptionView.getText().toString());
                        break;
                    }
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentListener = (DetailsChangedListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentListener = (DetailsChangedListener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        presenter = new CarDescriptionPresenter(this, args.getInt(CarDetailsEditContract.CAR_ID));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_description, container, false);
        descriptionView = rootView.findViewById(R.id.car_description);
        cautionView = rootView.findViewById(R.id.car_description_caution);
        progressLayout = rootView.findViewById(R.id.progress_layout);
        presenter.init();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onBind(binder);
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.DESCRIPTION);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress(boolean show) {
        progressLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        descriptionView.setAlpha(show ? .5f : 1);
        cautionView.setAlpha(show ? .5f : 1);
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public void onDescriptionObtained(String description) {
        descriptionView.setText(description);
    }

    @Override
    public void onDescriptionSaved() {
        showProgress(false);
        parentListener.onFinish(
                NewCarFormsContract.Mode.DESCRIPTION,
                NewCarFormsContract.Action.SAVE);
    }
}
