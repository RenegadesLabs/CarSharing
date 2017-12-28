package com.cardee.inbox.chat.single.view;

import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.inbox.chat.list.adapter.UtcDateFormatter;
import com.cardee.util.glide.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableEmitter;
import io.reactivex.subjects.PublishSubject;

public class ChatViewHolder implements ActivityViewHolder {

    private final RequestManager mRequestManager;
    private final UtcDateFormatter mDateFormatter;
    private final PublishSubject<String> mPublishSubject;
    private final VectorDrawableCompat userPhotoPlaceHolder;

    @BindView(R.id.chat_activity_toolbar_title)
    TextView mRecipientName;
    @BindView(R.id.toolbar_photo)
    ImageView mRecipientPhoto;
    @BindView(R.id.car_photo)
    ImageView mCarPhoto;
    @BindView(R.id.car_title)
    TextView mCarTitle;
    @BindView(R.id.car_licence_plate_number)
    TextView mCarLicenseNumber;
    @BindView(R.id.car_availability)
    TextView mCarAvailability;
    @BindView(R.id.chat_activity_message_field)
    AppCompatEditText mMessageField;
    @BindView(R.id.chat_activity_send)
    TextView mSend;

    public ChatViewHolder(View rootView) {
        ButterKnife.bind(this, rootView);
        userPhotoPlaceHolder = VectorDrawableCompat.create(rootView.getContext().getResources(), R.drawable.ic_photo_placeholder, null);
        mRequestManager = Glide.with(rootView.getContext());
        mDateFormatter = new ChatActivityDateFormatter(rootView.getContext());
        mPublishSubject = PublishSubject.create();
    }

    @Override
    public void setUserData(String recipientName, String photoUrl) {
        mRecipientName.setText(recipientName);
        mRequestManager
                .load(photoUrl)
                .centerCrop()
                .placeholder(userPhotoPlaceHolder)
                .transform(new CircleTransform(CardeeApp.context))
                .into(mRecipientPhoto);
    }

    @Override
    public void setCarData(String carPhotoUrl, String carTitle, String licenseNumber) {
        mRequestManager
                .load(carPhotoUrl)
                .placeholder(R.drawable.img_no_car)
                .centerCrop()
                .into(mCarPhoto);

        mCarTitle.setText(carTitle);
        mCarLicenseNumber.setText(licenseNumber);
    }

    @Override
    public void setCarBookingData(String mStartDate, String mEndDate) {
        String availability = mDateFormatter.formatDate(mStartDate) + mDateFormatter.getDivider() + mDateFormatter.formatDate(mEndDate);
        mCarAvailability.setText(availability);
    }
}
