package com.cardee.owner_car_details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.owner.entity.Image;
import com.cardee.owner_car_details.CarImagesEditContract;
import com.cardee.owner_car_details.presenter.CarImagesPresenter;

import java.util.List;


public class CarImagesActivity extends AppCompatActivity
        implements CarImagesEditContract.View, View.OnClickListener {

    private CarImagesPresenter presenter;
    private int carId;
    private int imageId;
    private ViewPager imageSlider;
    private ImageSliderAdapter adapter;
    private TextView sliderIndicator;
    private View coverImageButton;
    private View deleteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_images);
        Bundle args = getIntent().getExtras();
        carId = args.getInt(CarImagesEditContract.CAR_ID, -1);
        imageId = args.getInt(CarImagesEditContract.IMAGE_ID, -1);
        presenter = new CarImagesPresenter(this, carId);

        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            deleteButton = toolbar.findViewById(R.id.toolbar_action);
            deleteButton.setOnClickListener(this);
        }

        imageSlider = (ViewPager) findViewById(R.id.image_slider);
        sliderIndicator = (TextView) findViewById(R.id.slider_indicator);
        deleteButton = findViewById(R.id.btn_delete);
        adapter = new ImageSliderAdapter();
        imageSlider.setAdapter(adapter);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }

    @Override
    public void onImagesObtained(List<Image> images) {

    }

    @Override
    public void onImageSetPrimary(Image image) {

    }

    @Override
    public void onImageRemoved(Image image) {

    }

    @Override
    public void onClick(View view) {

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

    private class ImageSliderAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

}
