package com.cardee.inbox.chat;

import com.cardee.domain.inbox.usecase.entity.InboxChat;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface ChatContract {

    interface Presenter {

        void onInit(ChatContract.View view);

        void onGetChats();

        void onDestroy();
    }

    interface View extends BaseView {

        void showAllChats(List<InboxChat> chatList);

        void showChat();
    }
}
