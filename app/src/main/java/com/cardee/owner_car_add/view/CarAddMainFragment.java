package com.cardee.owner_car_add.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_car_add.view.items.CarAddItemFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CarAddMainFragment extends Fragment {

    private Unbinder mUnbinder;

    private CarAddView mView;

    @BindView(R.id.tv_addCarItem1)
    public TextView addCarItem1TV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_main, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        Bundle args = getArguments();
        if (args != null) {
            switch(args.getInt(CarAddItemFragment.FRAGMENT_NUMBER)) {
                case 0:
                    addCarItem1TV.setText(args.getString(CarAddItemFragment.FRAGMENT_VALUE));
                    break;
            }
        }

        return v;
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
}
