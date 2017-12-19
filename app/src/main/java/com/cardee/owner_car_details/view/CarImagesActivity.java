package com.cardee.owner_car_details.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cardee.R;
import com.cardee.domain.owner.entity.Image;
import com.cardee.owner_car_details.CarImagesEditContract;
import com.cardee.owner_car_details.presenter.CarImagesPresenter;

import java.util.ArrayList;
import java.util.List;


public class CarImagesActivity extends AppCompatActivity
        implements CarImagesEditContract.View, View.OnClickListener, ViewPager.OnPageChangeListener {

    private int carId;
    private int imageId;
    private CarImagesPresenter presenter;
    private ViewPager imageSlider;
    private ImageSliderAdapter adapter;
    private TextView sliderIndicator;
    private TextView coverImageButton;
    private View deleteButton;
    private Toast currentToast;
    private View progressView;

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
        imageSlider.addOnPageChangeListener(this);
        sliderIndicator = (TextView) findViewById(R.id.slider_indicator);
        coverImageButton = (TextView) findViewById(R.id.btn_set_cover_image);
        coverImageButton.setOnClickListener(this);
        progressView = findViewById(R.id.progress_layout);
        adapter = new ImageSliderAdapter(this);
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
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        imageSlider.setAlpha(show ? .5f : 1f);
        sliderIndicator.setAlpha(show ? .5f : 1f);
        coverImageButton.setAlpha(show ? .5f : 1f);
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
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public void onImagesObtained(List<Image> images) {
        adapter.setImages(images);
        int pageIndex = adapter.findPageById(imageId);
        if (pageIndex > 0) {
            imageSlider.setCurrentItem(pageIndex);
        } else if (pageIndex == 0) {
            onPageSelected(pageIndex);
        }
    }

    @Override
    public void onImageSetPrimary(Image image) {
        adapter.setCoverImage(image);
        onPageSelected(imageSlider.getCurrentItem());
    }

    @Override
    public void onImageRemoved(Image image) {
        adapter.removeImage(image);
    }

    @Override
    public void onClick(View view) {
        if (progressView.getVisibility() == View.VISIBLE) {
            return;
        }
        int position = imageSlider.getCurrentItem();
        Image image = adapter.getImageByPosition(position);
        switch (view.getId()) {
            case R.id.toolbar_action:
                presenter.onImageRemove(image);
                break;
            case R.id.btn_set_cover_image:
                if (!image.isPrimary()) {
                    presenter.onImageSetPrimary(image);
                }
                break;
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Image image = adapter.getImageByPosition(position);
        if (image.isPrimary()) {
            coverImageButton.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
            coverImageButton.setBackgroundResource(R.drawable.bg_btn_stroke_active);
            coverImageButton.setText(R.string.cover_image);
        } else {
            coverImageButton.setTextColor(ContextCompat.getColor(this, R.color.bg_main));
            coverImageButton.setBackgroundResource(R.drawable.bg_btn_stroke);
            coverImageButton.setText(R.string.set_cover_image);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ImageSliderAdapter extends PagerAdapter {

        private final RequestManager imageRequestManager;
        private final List<Image> images;
        private final LayoutInflater inflater;

        ImageSliderAdapter(Context context) {
            imageRequestManager = Glide.with(context);
            images = new ArrayList<>();
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        Image getImageByPosition(int position) {
            try {
                return images.get(position);
            } catch (IndexOutOfBoundsException ex) {
                return null;
            }
        }

        public void setImages(List<Image> newImages) {
            images.clear();
            images.addAll(newImages);
            notifyDataSetChanged();
        }

        void removeImage(Image image) {
            images.remove(image);
            notifyDataSetChanged();
        }

        void setCoverImage(Image image) {
            for (int i = 0; i < images.size(); i++) {
                Image suspect = images.get(i);
                if (suspect.isPrimary()) {
                    Image modifiedSuspect =
                            new Image(suspect.getImageId(),
                                    suspect.getLink(),
                                    suspect.getThumbnail(),
                                    false);
                    images.set(i, modifiedSuspect);
                }
                if (suspect.equals(image)) {
                    Image modifiedSuspect =
                            new Image(suspect.getImageId(),
                                    suspect.getLink(),
                                    suspect.getThumbnail(),
                                    true);
                    images.set(i, modifiedSuspect);
                }
            }
        }

        int findPageById(int imageId) {
            for (int i = 0; i < images.size(); i++) {
                Image suspect = images.get(i);
                if (suspect.getImageId() == imageId) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = inflater.inflate(R.layout.view_image, container, false);
            ImageView imageView = itemView.findViewById(R.id.image_view);
            final View progressBar = itemView.findViewById(R.id.item_progress_layout);
            Image image = images.get(position);
            imageRequestManager
                    .load(image.getLink())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource,
                                                       String model, Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.img_no_car)
                    .into(imageView);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
