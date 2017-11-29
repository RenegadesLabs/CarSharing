package com.cardee.owner_profile_info.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.domain.owner.entity.Car;
import com.cardee.owner_car_details.OwnerCarDetailsContract;
import com.cardee.owner_car_details.view.OwnerCarDetailsActivity;
import com.cardee.owner_profile_info.presenter.OwnerProfileInfoPresenter;
import com.cardee.owner_profile_info.view.adapter.CarPreviewListAdapter;
import com.cardee.owner_profile_info.view.adapter.ReviewListAdapter;
import com.cardee.util.glide.CircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnerProfileInfoActivity extends AppCompatActivity implements ProfileInfoView {

    public final static String TAG = OwnerProfileInfoActivity.class.getCanonicalName();
    private OwnerProfileInfoPresenter mPresenter;
    private ProgressDialog mProgress;
    private CarPreviewListAdapter mCarsAdapter;
    private ReviewListAdapter mReviewAdapter;

    @BindView(R.id.profile_name)
    TextView mProfileNameText;

    @BindView(R.id.profile_rating)
    TextView mProfileRatingText;

    @BindView(R.id.acceptance_percentage)
    TextView mProfileAcceptance;

    @BindView(R.id.response_time)
    TextView mProfileResponse;

    @BindView(R.id.bookings_count)
    TextView mProfileBookings;

    @BindView(R.id.profile_image)
    ImageView mProfilePhoto;

    @BindView(R.id.note_edit)
    TextView mNoteText;

    @BindView(R.id.cars_count)
    TextView mCarsCount;

    @BindView(R.id.reviews_count)
    TextView mReviewsCount;

    @BindView(R.id.cars_list)
    RecyclerView mCarsListView;

    @BindView(R.id.reviews_list)
    RecyclerView mReviewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile_info);
        ButterKnife.bind(this);

        initPresenter();
        mProgress = DialogHelper.getProgressDialog(this, getString(R.string.loading), false);
        showProgress(true);
        mPresenter.getOwnerInfo();
        mCarsAdapter = new CarPreviewListAdapter(this);
        mReviewAdapter = new ReviewListAdapter(this);
        initCarList(mCarsListView);
        initReviewList(mReviewsListView);
        mCarsAdapter.subscribe(mPresenter);
    }

    private void initReviewList(RecyclerView mReviewsListView) {
        mReviewsListView.setHasFixedSize(true);
        mReviewsListView.setAdapter(mReviewAdapter);
        mReviewsListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initCarList(RecyclerView mCarsListView) {
        mCarsListView.setHasFixedSize(true);
        mCarsListView.setAdapter(mCarsAdapter);
        mCarsListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initPresenter() {
        mPresenter = new OwnerProfileInfoPresenter(this);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProfileName(String name) {
        mProfileNameText.setText(name);
    }

    @Override
    public void setProfileRating(String rating) {
        mProfileRatingText.setText(rating);
    }

    @Override
    public void setAcceptance(String percent) {
        mProfileAcceptance.setText(percent);
    }

    @Override
    public void setResponseText(String time) {
        mProfileResponse.setText(time);
    }

    @Override
    public void setBookings(String count) {
        mProfileBookings.setText(count);
    }

    @Override
    public void setProfileImage(String photoLink) {
        Glide.with(this)
                .load(photoLink)
                .transform(new CircleTransform(this))
                .into(mProfilePhoto);
    }

    @Override
    public void setNote(String note) {
        mNoteText.setText(note);
    }

    @Override
    public void setCarsCount(String text) {
        mCarsCount.setText(text);
    }

    @Override
    public void setReviewsCount(String text) {
        mReviewsCount.setText(text);
    }

    @Override
    public void setCars(List<Car> items) {
        mCarsAdapter.insert(items);
    }

    @Override
    public void setReviews(List<Review> reviews) {
        mReviewAdapter.insert(reviews);
    }

    @Override
    public void openItem(Car car) {
        Intent intent = new Intent(this, OwnerCarDetailsActivity.class);
        Bundle args = new Bundle();
        args.putInt(OwnerCarDetailsContract.CAR_ID, car.getCarId() == null ? -1 : car.getCarId());
        args.putString(OwnerCarDetailsContract.CAR_NUMBER, car.getLicenceNumber());
        intent.putExtras(args);
        startActivity(intent);
    }

}
