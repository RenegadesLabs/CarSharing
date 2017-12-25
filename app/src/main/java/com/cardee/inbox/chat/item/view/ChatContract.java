package com.cardee.inbox.chat.item.view;

import android.os.Bundle;

import com.cardee.domain.inbox.usecase.entity.ChatInfo;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

public interface ChatContract {

    interface Presenter {

        void init(Bundle bundle);

        ChatInfo onChatDataRequest(int databaseId, int serverId);

        void onDestroy();
    }

    interface View extends BaseView {

    }

}
