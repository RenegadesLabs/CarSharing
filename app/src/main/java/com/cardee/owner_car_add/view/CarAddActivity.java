package com.cardee.owner_car_add.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.CarAddContract;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_add.presenter.CarAddPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CarAddActivity extends AppCompatActivity
        implements View.OnClickListener, CarAddContract.View {

    private Unbinder unbinder;

    private View mActionSave;

    @BindView(R.id.tv_addCarItem1)
    public TextView typeValueView;

    @BindView(R.id.tv_addCarItem2)
    public TextView infoValueView;

    @BindView(R.id.tv_addCarItem3)
    public TextView imageValueView;

    @BindView(R.id.tv_addCarItem4)
    public TextView locationValueView;

    @BindView(R.id.tv_addCarItem5)
    public TextView contactsValueView;

    @BindView(R.id.tv_addCarItem6)
    public TextView paymentValueView;

    @BindView(R.id.tv_addCarItem7)
    public TextView mobileValueView;

    @BindView(R.id.tv_addCarItem8)
    public TextView emailValueView;

    @BindView(R.id.iv_addCarItem1)
    public AppCompatImageView typeCompletedIconView;

    @BindView(R.id.iv_addCarItem2)
    public AppCompatImageView infoCompletedIconView;

    @BindView(R.id.iv_addCarItem3)
    public AppCompatImageView imageCompletedIconView;

    @BindView(R.id.iv_addCarItem4)
    public AppCompatImageView locationCompletedIconView;

    @BindView(R.id.iv_addCarItem5)
    public AppCompatImageView contactsCompletedIconView;

    @BindView(R.id.iv_addCarItem6)
    public AppCompatImageView paymentCompletedIconView;

    @BindView(R.id.iv_addCarItem7)
    public AppCompatImageView mobileCompletedIconView;

    @BindView(R.id.iv_addCarItem8)
    public AppCompatImageView emailCompletedIconView;

    private CarAddPresenter presenter;
    private Toast currentToast;
    private ProgressBar progressBar;
    private boolean firstShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_car_add);
        unbinder = ButterKnife.bind(this);
        presenter = new CarAddPresenter(this);
        progressBar = (ProgressBar) findViewById(R.id.add_car_progress);
        initToolbar();
        firstShow = true;
    }

    private void initToolbar() {
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            mActionSave = toolbar.findViewById(R.id.toolbar_action);
            mActionSave.setOnClickListener(this);
            mActionSave.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.init();
    }

    @Override
    public void setCarData(CarData carData) {
        //No need to implement in current view
    }

    @Override
    public void onFinish() {
        setResult(NewCarFormsContract.CAR_CREATED);
        finish();
    }

    @Override
    public void onNoSavedCar() {
        if (!firstShow) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_car, null);
        builder.setView(rootView);
        Dialog dialog = builder.create();
        AppCompatButton buttonClose = rootView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(view ->
                dialog.dismiss());
        TextView textView = rootView.findViewById(R.id.text);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.car_add_dialog_text));
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 121, 143,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        dialog.show();

        firstShow = false;
    }

    @Override
    public void setTypeCompleted(boolean completed) {
        setCompletedIconIfNeed(typeCompletedIconView, completed);
        typeValueView.setText(completed ? "Edit" : "Add");
    }

    @Override
    public void setInfoCompleted(boolean completed) {
        setCompletedIconIfNeed(infoCompletedIconView, completed);
        infoValueView.setText(completed ? "Edit" : "Add");
    }

    @Override
    public void setImageCompleted(boolean completed) {
        setCompletedIconIfNeed(imageCompletedIconView, completed);
        imageValueView.setText(completed ? "Edit" : "Add");
    }

    @Override
    public void setLocationCompleted(boolean completed) {
        setCompletedIconIfNeed(locationCompletedIconView, completed);
        locationValueView.setText(completed ? "Edit" : "Add");
    }

    @Override
    public void setContactsCompleted(boolean completed) {
        setCompletedIconIfNeed(contactsCompletedIconView, completed);
        contactsValueView.setText(completed ? "Edit" : "Add");
    }

    @Override
    public void setMobileCompleted(boolean completed) {
        setCompletedIconIfNeed(mobileCompletedIconView, completed);
        mobileValueView.setText(completed ? "Edit" : "Add");
    }

    @Override
    public void setEmailCompleted(boolean completed) {
        setCompletedIconIfNeed(emailCompletedIconView, completed);
        emailValueView.setText(completed ? "Edit" : "Add");
    }

    @Override
    public void setPaymentCompleted(boolean completed) {
        setCompletedIconIfNeed(paymentCompletedIconView, completed);
        paymentValueView.setText(completed ? "Edit" : "Add");
    }

    private void setCompletedIconIfNeed(ImageView view, boolean completed) {
        if (completed) {
            view.setImageResource(R.drawable.ic_check_circle);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.add_type)
    public void onTypeClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.TYPE);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_info)
    public void onInfoClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.INFO);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_image)
    public void onImageClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.IMAGE);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_location)
    public void onLoactionClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.LOCATION);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_contact)
    public void onContactClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.CONTACT);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_mobile)
    public void onMobileClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.MOBILE);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_email)
    public void onEmailClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.EMAIL);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.add_payment)
    public void onPaymentClicked() {
        Bundle args = new Bundle();
        args.putSerializable(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.PAYMENT);
        openNewCarEditActivity(args);
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitClicked() {
        presenter.createCar();
    }

    private void openNewCarEditActivity(Bundle args) {
        Intent intent = new Intent(this, NewCarFormsActivity.class);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void showMessage(int messageId) {
        showMessage(getString(messageId));
    }
}
