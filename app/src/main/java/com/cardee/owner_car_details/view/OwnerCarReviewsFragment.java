package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.owner_car_details.presenter.CarReviewsPresenter;
import com.cardee.owner_car_details.view.adapter.CarReviewsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cardee.owner_car_details.view.CarReviewsContract.Presenter;


public class OwnerCarReviewsFragment extends Fragment implements CarReviewsContract.View {

    private static final String CAR_ID = "car_id";
    private static final int MAX_RATE = 5;
    private Toast mCurrentToast;
    private Presenter mPresenter;
    private CarReviewsAdapter mAdapter;

    private ImageView star;
    private ImageView greyStar;

    @BindView(R.id.container)
    View mContainer;

    @BindView(R.id.car_reviews_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.condition)
    LinearLayout mCondition;

    @BindView(R.id.comfort)
    LinearLayout mComfort;

    @BindView(R.id.owner)
    LinearLayout mOwner;

    @BindView(R.id.experience)
    LinearLayout mExperience;

    @BindView(R.id.ratings_count)
    TextView mRatingsCount;

    @BindView(R.id.reviews_count)
    TextView mReviewsCount;

    @BindView(R.id.reviews_list)
    RecyclerView mReviewsList;

    public static Fragment newInstance(Integer carId) {
        OwnerCarReviewsFragment fragment = new OwnerCarReviewsFragment();
        Bundle args = new Bundle();
        args.putInt(CAR_ID, carId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int carId = -1;
        Bundle arguments = getArguments();
        if (arguments != null) {
            carId = arguments.getInt(CAR_ID, -1);
        }

        mPresenter = new CarReviewsPresenter(this, carId, getActivity());
        mAdapter = new CarReviewsAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_car_reviews, container, false);
        ButterKnife.bind(this, rootView);

        mReviewsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mReviewsList.setHasFixedSize(true);
        mReviewsList.setNestedScrollingEnabled(false);
        mReviewsList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.get();
    }


    @Override
    public void setCarReviews(List<Review> reviews) {
        mAdapter.insert(reviews);
    }

    @Override
    public void setConditions(int conditions) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < conditions; i++) {
            star = new ImageView(getActivity());
            star.setImageResource(R.drawable.ic_star_rate);
            star.setLayoutParams(params);
            mCondition.addView(star);
        }
        for (int i = 0; i < (MAX_RATE - conditions); i++) {
            greyStar = new ImageView(getActivity());
            greyStar.setImageResource(R.drawable.ic_star_rate_inactive);
            greyStar.setLayoutParams(params);
            mCondition.addView(greyStar);
        }
    }

    @Override
    public void setComfort(int comfort) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < comfort; i++) {
            star = new ImageView(getActivity());
            star.setImageResource(R.drawable.ic_star_rate);
            star.setLayoutParams(params);
            mComfort.addView(star);
        }
        for (int i = 0; i < (MAX_RATE - comfort); i++) {
            greyStar = new ImageView(getActivity());
            greyStar.setImageResource(R.drawable.ic_star_rate_inactive);
            greyStar.setLayoutParams(params);
            mComfort.addView(greyStar);
        }
    }

    @Override
    public void setOwnerRate(int owner) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < owner; i++) {
            star = new ImageView(getActivity());
            star.setImageResource(R.drawable.ic_star_rate);
            star.setLayoutParams(params);
            mOwner.addView(star);
        }
        for (int i = 0; i < (MAX_RATE - owner); i++) {
            greyStar = new ImageView(getActivity());
            greyStar.setImageResource(R.drawable.ic_star_rate_inactive);
            greyStar.setLayoutParams(params);
            mOwner.addView(greyStar);
        }
    }

    @Override
    public void setExperience(int overall) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < overall; i++) {
            star = new ImageView(getActivity());
            star.setImageResource(R.drawable.ic_star_rate);
            star.setLayoutParams(params);
            mExperience.addView(star);
        }
        for (int i = 0; i < (MAX_RATE - overall); i++) {
            greyStar = new ImageView(getActivity());
            greyStar.setImageResource(R.drawable.ic_star_rate_inactive);
            greyStar.setLayoutParams(params);
            mExperience.addView(greyStar);
        }
    }

    @Override
    public void setRatingsCount(String s) {
        mRatingsCount.setText(s);
    }

    @Override
    public void setReviewsCount(String s) {
        mReviewsCount.setText(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mAdapter.destroy();
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            return;
        }
        mContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }


    @Override
    public void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

}
