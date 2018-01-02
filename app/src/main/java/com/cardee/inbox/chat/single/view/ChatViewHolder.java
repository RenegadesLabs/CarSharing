package com.cardee.inbox.chat.single.view;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.inbox.chat.list.adapter.UtcDateFormatter;
import com.cardee.inbox.chat.single.adapter.SingleChatAdapter;
import com.cardee.util.glide.CircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ChatViewHolder implements ActivityViewHolder {

    private final RequestManager mRequestManager;
    private final UtcDateFormatter mDateFormatter;
    private final Observable<String> mMessageStream;
    private final VectorDrawableCompat userPhotoPlaceHolder;
    private SingleChatAdapter mAdapter;

    @BindView(R.id.chat_list)
    RecyclerView mRecyclerView;
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
        mMessageStream = Observable.create(emitter -> mSend.setOnClickListener(view -> emitter.onNext(mMessageField.getText().toString())));
    }

    @Override
    public void initAdapter(Context context) {
        mAdapter = new SingleChatAdapter();
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.addOnLayoutChangeListener((view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//            if (bottom < oldBottom) {
//                mRecyclerView.post(() -> mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1));
//            }
//        });
        mRecyclerView.setLayoutManager(getLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);
        return layoutManager;
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

    @Override
    public void setMessageList(List<ChatMessage> messageList) {
        mAdapter.setMessageList(messageList);
        scrollRecycler(mAdapter.getLastItemPosition());
    }

    @Override
    public void updateAllMessages(List<ChatMessage> messageList) {
        mAdapter.updateMessageList(messageList);
        scrollRecycler(mAdapter.getLastItemPosition());
    }

    @Override
    public void updateMessagePreview(int realMessageId) {
        mAdapter.updateNewMessagePreview(realMessageId);
    }

    private void scrollRecycler(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void subscribeToInput(Consumer<String> consumer) {
        mMessageStream
                .filter(message -> !message.isEmpty())
                .doOnNext(message -> {
                    mAdapter.addNewMessagePreview(message);
                    scrollRecycler(mAdapter.getLastItemPosition());
                })
                .doAfterNext(message -> mMessageField.setText(null))
                .subscribe(consumer);
    }
}
