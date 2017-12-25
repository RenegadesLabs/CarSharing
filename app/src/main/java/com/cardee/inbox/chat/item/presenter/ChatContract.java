package com.cardee.inbox.chat.item.presenter;

import android.os.Bundle;

import com.cardee.mvp.BaseView;

public interface ChatContract {

    interface Presenter {

        void init(Bundle bundle, android.view.View activityView);

        void onChatDataRequest();

        void onDestroy();
    }

    interface View extends BaseView {

    }
}
