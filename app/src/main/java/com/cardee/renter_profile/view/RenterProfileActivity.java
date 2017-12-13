package com.cardee.renter_profile.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.data_source.remote.api.profile.response.entity.RenterReview;
import com.cardee.renter_profile.presenter.RenterProfilePresenter;
import com.cardee.renter_profile.view.adapter.RenterReviewListAdapter;
import com.cardee.util.display.ActivityHelper;
import com.cardee.util.glide.CircleTransform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenterProfileActivity extends AppCompatActivity implements RenterProfileView {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private RenterProfilePresenter mPresenter;
    private RenterReviewListAdapter mAdapter;
    private Toast mCurrentToast;
    private byte[] mPictureByteArray;


    @BindView(R.id.renter_profile_container)
    View mContainer;

    @BindView(R.id.renter_profile_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.profile_name)
    TextView mProfileNameText;

    @BindView(R.id.profile_rating)
    TextView mProfileRatingText;

    @BindView(R.id.profile_image)
    ImageView mProfilePhoto;

    @BindView(R.id.profile_image_edit)
    TextView mProfilePhotoEdit;

    @BindView(R.id.tv_age)
    TextView mProfileAge;

    @BindView(R.id.tv_driving_exp)
    TextView mDrivingExp;

    @BindView(R.id.driving_exp_years)
    TextView mDrivingExpYears;

    @BindView(R.id.bookings_count)
    TextView mBookings;

    @BindView(R.id.note_text)
    TextView mNote;

    @BindView(R.id.note_edit)
    TextView mNoteEdit;

    @BindView(R.id.reviews_count)
    TextView mReviewsCount;

    @BindView(R.id.reviews_list)
    RecyclerView mReviewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_profile);
        ButterKnife.bind(this);

        initToolBar();
        mPresenter = new RenterProfilePresenter(this);
        mAdapter = new RenterReviewListAdapter(this);
        initReviewList();

        mPresenter.getRenterProfile();
    }

    private void initReviewList() {
        mReviewsListView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewsListView.setLayoutManager(layoutManager);
        mReviewsListView.setNestedScrollingEnabled(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mReviewsListView.getContext(),
                LinearLayoutManager.VERTICAL);
        mReviewsListView.addItemDecoration(dividerItemDecoration);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @OnClick(R.id.profile_image_edit)
    public void onEditPhotoClicked() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        } else {
            ActivityHelper.pickImageIntent(RenterProfileActivity.this, ActivityHelper.PICK_IMAGE);
        }
    }

    @OnClick(R.id.note_edit)
    public void onNoteEditClicked() {
        mPresenter.changeNote(this);
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
    public void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    public void showMessage(int messageId) {
        showMessage(getString(messageId));
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
    public void setProfileAge(String age) {
        mProfileAge.setText(age);
    }

    @Override
    public void setDrivingExp(String exp) {
        mDrivingExp.setText(exp);
    }

    @Override
    public void setDrivingExpYears(String years) {
        mDrivingExpYears.setText(years);
    }

    @Override
    public void setBookings(String count) {
        mBookings.setText(count);
    }

    @Override
    public void setProfileImage(String photoLink) {
        Glide.with(this)
                .load(photoLink)
                .placeholder(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .error(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                .centerCrop()
                .transform(new CircleTransform(this))
                .into(mProfilePhoto);
    }

    public void setProfileImage(byte[] pictureByteArray) {
        Glide.with(this)
                .load(pictureByteArray)
                .centerCrop()
                .transform(new CircleTransform(this))
                .into(mProfilePhoto);
    }

    @Override
    public void setNote(String note) {
        mNote.setText(note);
    }

    @Override
    public void setReviewsCount(String text) {
        mReviewsCount.setText(text);
    }

    @Override
    public void setReviews(List<RenterReview> reviews) {
        mAdapter.insert(reviews);
    }

    @Override
    public void onChangeImageSuccess() {
        if (mPictureByteArray != null) {
            setProfileImage(mPictureByteArray);
            setResult(RESULT_OK);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityHelper.pickImageIntent(RenterProfileActivity.this, ActivityHelper.PICK_IMAGE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityHelper.PICK_IMAGE) {
            if (resultCode == RESULT_OK && data.getData() != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] pictureByteArray = stream.toByteArray();
                    mPictureByteArray = pictureByteArray;
                    mPresenter.setProfilePicture(convertByteArrayToFile(pictureByteArray));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File convertByteArrayToFile(byte[] byteArray) {
        if (byteArray != null) {
            FileOutputStream fos;
            File f = null;
            try {
                f = new File(this.getCacheDir(), "picture");
                fos = new FileOutputStream(f);
                fos.write(byteArray);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return f;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
