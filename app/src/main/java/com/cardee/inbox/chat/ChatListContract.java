package com.cardee.inbox.chat;

import android.os.Bundle;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface ChatListContract {

    interface Presenter {

        void onInit(ChatListContract.View view);

        void onChatClick(Chat chat);

        void onUnreadMessageReceived(boolean isUnread);

        void onGetChats();

        void onDestroy();
    }

    interface View extends BaseView {

        void showAllChats(List<Chat> chatList);

        void showChat(Bundle bundle);
    }
}
