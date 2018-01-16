package com.cardee.inbox.chat.single.presenter;

import android.os.Bundle;
import android.widget.ImageView;

import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface ChatContract {

    interface Presenter {

        void init(Bundle bundle, android.view.View activityView);

        void onChatDataRequest();

        void onDestroy();

        void setOnClickListener(ImageView recipientPhoto, Integer recipientId);
    }

    interface View extends BaseView {
        void openOwnerAccount(Integer recipientId);

        void openRenterAccount(Integer recipientId);
    }
}
