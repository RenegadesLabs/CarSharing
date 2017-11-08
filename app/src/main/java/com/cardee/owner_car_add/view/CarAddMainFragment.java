package com.cardee.owner_car_add.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_car_add.view.items.CarAddItemFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CarAddMainFragment extends Fragment implements CarAddActivity.CarInfoPassCallback {

    private Unbinder mUnbinder;

    private CarAddView mView;

    private String mValueItem1, mValueItem2,
            mValueItem3, mValueItem4, mValueItem5,
            mValueItem6;

    private boolean isFilled1 = false;

    private boolean isFilled2, isFilled3,
            isFilled4, isFilled5, isFilled6;

    @BindView(R.id.tv_addCarItem1)
    public TextView addCarItem1TV;

    @BindView(R.id.tv_addCarItem2)
    public TextView addCarItem2TV;

    @BindView(R.id.tv_addCarItem3)
    public TextView addCarItem3TV;

    @BindView(R.id.tv_addCarItem4)
    public TextView addCarItem4TV;

    @BindView(R.id.tv_addCarItem5)
    public TextView addCarItem5TV;

    @BindView(R.id.tv_addCarItem6)
    public TextView addCarItem6TV;

    @BindView(R.id.iv_addCarItem1)
    public AppCompatImageView addCarItem1IV;

    @BindView(R.id.iv_addCarItem2)
    public AppCompatImageView addCarItem2IV;

    @BindView(R.id.iv_addCarItem3)
    public AppCompatImageView addCarItem3IV;

    @BindView(R.id.iv_addCarItem4)
    public AppCompatImageView addCarItem4IV;

    @BindView(R.id.iv_addCarItem5)
    public AppCompatImageView addCarItem5IV;

    @BindView(R.id.iv_addCarItem6)
    public AppCompatImageView addCarItem6IV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_main, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        initViewState();
        return v;
    }

    private void initViewState() {
        if (mValueItem1 != null && !mValueItem1.equals("")
                && isFilled1) {
            addCarItem1TV.setText(mValueItem1);
            addCarItem1IV.setImageResource(R.drawable.ic_check_circle);
        }

        if (mValueItem2 != null && !mValueItem2.equals("")
                && isFilled2) {
            addCarItem2TV.setText(mValueItem2);
            addCarItem2IV.setImageResource(R.drawable.ic_check_circle);
        }

    }

    public CarAddActivity.CarInfoPassCallback getListener() {
        return this;
    }

    public void setViewListener(CarAddView viewListener) {
        mView = viewListener;
    }

    @OnClick(R.id.add_item1)
    public void onItem1Clicked() {
        if (mView != null)
            mView.onItem1();
    }

    @OnClick(R.id.add_item2)
    public void onItem2Clicked() {
        if (mView != null)
            mView.onItem2();
    }

    @OnClick(R.id.add_item3)
    public void onItem3Clicked() {
        if (mView != null)
            mView.onItem3();

    }

    @OnClick(R.id.add_item4)
    public void onItem4Clicked() {
        if (mView != null)
            mView.onItem4();
    }

    @OnClick(R.id.add_item5)
    public void onItem5Clicked() {
        if (mView != null)
            mView.onItem5();
    }

    @OnClick(R.id.add_item6)
    public void onItem6Clicked() {
        if (mView != null)
            mView.onItem6();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onPassData(Bundle b) {
        switch (b.getInt(CarAddItemFragment.FRAGMENT_NUMBER)) {
            case 0:
                mValueItem1 = b.getString(CarAddItemFragment.FRAGMENT_VALUE);
//                addCarItem1TV.setText(mValueItem1);
//                addCarItem1IV.setImageResource(R.drawable.ic_check_circle);
                isFilled1 = true;
                break;
            case 1:
                mValueItem2 = "Edit";
//                addCarItem2TV.setText(mValueItem2);
//                addCarItem2IV.setImageResource(R.drawable.ic_check_circle);
                isFilled2 = true;
                break;

        }
    }
}
