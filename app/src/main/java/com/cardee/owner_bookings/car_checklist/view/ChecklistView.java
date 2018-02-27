package com.cardee.owner_bookings.car_checklist.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.owner_bookings.car_checklist.adapter.CarSquareImagesAdapter;
import com.cardee.owner_bookings.car_checklist.presenter.OwnerChecklistContract;
import com.cardee.owner_bookings.car_checklist.presenter.RenterChecklistContract;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChecklistView extends ConstraintLayout implements OwnerChecklistContract.View, RenterChecklistContract.View {

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
    @BindView(R.id.pb_petrolMileage)
    public ProgressBar petrolMileageProgress;
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

    private ProgressDialog mProgressHandingOver, mProgress;

    private Unbinder mUnbinder;

    private OwnerChecklistContract.Presenter mPresenter;

    private RenterChecklistContract.Presenter renterPresenter;

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
        mProgressHandingOver = DialogHelper.getProgressDialog(getContext(),
                getContext().getResources().getString(R.string.owner_handover_progress), false);
        mProgress = DialogHelper.getProgressDialog(getContext(),
                getContext().getString(R.string.car_rental_info_rates), false);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void setPresenter(OwnerChecklistContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setView(this);
    }

    @Override
    public void setPresenter(RenterChecklistContract.Presenter presenter) {
        renterPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        renterPresenter = null;
        mUnbinder.unbind();
    }

    @Override
    public void onHandingOverProcessing(boolean showProgress) {
        if (showProgress) {
            mProgressHandingOver.show();
            return;
        }
        mProgressHandingOver.dismiss();
    }

    @Override
    public void showProgressPetrolMileage(boolean show) {
        if (show) {
            petrolMileageProgress.setVisibility(VISIBLE);
            return;
        }
        petrolMileageProgress.setVisibility(GONE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgress.show();
            return;
        }
        mProgress.dismiss();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
    }

    public void setFirstTitle(boolean isByMileage) {
        if (isByMileage) {
            title1.setText(R.string.owner_handover_checklist_master_title);
            return;
        }
        title1.setText(R.string.owner_handover_checklist_petrol_title);
    }

    public void setMileagePetrolDesc(String desc) {
        petrolDescTV.setText(desc);
    }

    public void setRemarksText(String text) {
        remarksText.setText(text);
    }

    public void setMasterMileageValue(String txt) {
        petrolMileageView.setMileageValue(txt);
    }

    public void setPetrolValue(String txt) {
        petrolMileageView.setPetrolValue(txt);
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
