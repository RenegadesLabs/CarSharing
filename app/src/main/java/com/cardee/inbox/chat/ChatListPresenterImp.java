package com.cardee.inbox.chat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.inbox.usecase.chat.GetChats;

import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_DB_ID;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_SERVER_ID;

public class ChatListPresenterImp implements ChatListContract.Presenter {

    private static final String TAG = ChatListPresenterImp.class.getSimpleName();
    private ChatListContract.View mView;

    private final GetChats mGetChats;
    private final UseCaseExecutor mExecutor;
    private final String mAttachment;

    ChatListPresenterImp(Context context) {
        mGetChats = new GetChats();
        mExecutor = UseCaseExecutor.getInstance();
        mAttachment = AccountManager.getInstance(context).getSessionInfo();
    }

    @Override
    public void onInit(ChatListContract.View view) {
        mView = view;
    }

    @Override
    public void onGetChats() {
        mExecutor.execute(mGetChats,
                new GetChats.RequestValues(mAttachment),
                new UseCase.Callback<GetChats.ResponseValues>() {
                    @Override
                    public void onSuccess(GetChats.ResponseValues response) {
                        showAllChats(response);
                    }

                    @Override
                    public void onError(Error error) {
                        showError(error);
                    }
                });
    }

    private void showError(Error error) {
        if (mView != null) {
            mView.showMessage(error.getMessage());
        }
    }

    private void showAllChats(GetChats.ResponseValues response) {
        if (mView != null) {
            mView.showAllChats(response.getChats());
        }
    }

    @Override
    public void onChatClick(Chat chat) {
        Bundle args = new Bundle();
        args.putInt(CHAT_DB_ID, chat.getChatLocalId());
        args.putInt(CHAT_SERVER_ID, chat.getChatServerId());
        mView.showChat(args);
        Log.e(TAG, "Chat selected: databaseId = " + chat.getChatLocalId() + " " + "serverId = " + chat.getChatServerId());
    }

    @Override
    public void onUnreadMessageReceived(boolean isUnread) {

    }

    @Override
    public void onDestroy() {
        mView = null;
        mGetChats.dispose();
    }
}
