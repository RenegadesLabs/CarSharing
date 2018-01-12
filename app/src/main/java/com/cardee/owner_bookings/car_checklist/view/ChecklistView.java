package com.cardee.owner_bookings.car_checklist.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.owner_bookings.car_checklist.ChecklistContract;
import com.cardee.owner_bookings.car_checklist.adapter.CarSquareImagesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChecklistView extends ConstraintLayout implements ChecklistContract.View {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    public TextView toolbarTitle;
    @BindView(R.id.tv_checklistTitle1)
    public TextView title1;
    @BindView(R.id.v_checklistPetrolMileageView)
    public PetrolView petrolMileageView;
    @BindView(R.id.tv_checklistPetrolDesc)
    public TextView petrolDescTV;
//    @BindView(R.id.iv_handoverPetrolLvlMinus)
//    public AppCompatImageView petrolLvlMinus;
//    @BindView(R.id.iv_handoverPetrolLvlPlus)
//    public AppCompatImageView petrolLvlPlus;
//    @BindView(R.id.tv_handoverValue)
//    public TextView petrolValue;
    @BindView(R.id.rv_checklistPhotos)
    public RecyclerView imagesGrid;
    @BindView(R.id.remarks_container)
    public View remarksContainer;
    @BindView(R.id.tv_remarksText)
    public TextView remarksText;
    @BindView(R.id.et_handoverRemarks)
    public AppCompatEditText remarksET;
    @BindView(R.id.l_viewAccurateContainer)
    public ConstraintLayout viewAccurateContainer;
    @BindView(R.id.b_completeHandoverYES)
    public AppCompatButton completeYesB;
    @BindView(R.id.b_completeHandoverNO)
    public AppCompatButton completeNoB;
    @BindView(R.id.l_handoverContainer)
    public LinearLayout handoverContainer;
    @BindView(R.id.b_handoverCar)
    public TextView handoverB; //changed from AppCompatButton

    private Unbinder mUnbinder;

    private ChecklistContract.Presenter mPresenter;

    public ChecklistView(Context context) {
        super(context);
    }

    public ChecklistView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChecklistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mUnbinder = ButterKnife.bind(this, this);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void setPresenter(ChecklistContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        mUnbinder.unbind();
    }

    @Override
    public void showProgress(boolean show) {
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }

    public void setMasterMileageValue(String txt) {
        petrolMileageView.setValue(txt);
    }

    public void setImagesAdapter(CarSquareImagesAdapter adapter) {
        imagesGrid.setAdapter(adapter);
    }

    public String getRemarksText() {
        return remarksET.getText().toString();
    }

    public float getTankFullness() {
        return petrolMileageView.getTankFullness();
    }
    public int getMileage() {
        return petrolMileageView.getMileage();
    }
}
