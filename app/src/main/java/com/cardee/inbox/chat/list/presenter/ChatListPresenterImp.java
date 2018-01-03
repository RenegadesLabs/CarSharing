package com.cardee.inbox.chat.list.presenter;

import android.content.Context;
import android.os.Bundle;

import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.inbox.usecase.chat.GetChats;

import java.util.List;

import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_ATTACHMENT;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.IS_NEW_CHAT;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_SERVER_ID;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_UNREAD_COUNT;

public class ChatListPresenterImp implements ChatListContract.Presenter {

    private ChatListContract.View mView;

    private final GetChats mGetChats;
    private final UseCaseExecutor mExecutor;
    private final String mAttachment;

    private boolean isFistChatEntering = true;

    public ChatListPresenterImp(Context context) {
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
        mView.showProgress(true);
        mExecutor.execute(mGetChats,
                new GetChats.RequestValues(mAttachment),
                new UseCase.Callback<GetChats.ResponseValues>() {
                    @Override
                    public void onSuccess(GetChats.ResponseValues response) {
                        showAllChats(response.getChats());
                    }

                    @Override
                    public void onError(Error error) {
                        showError(error);
                    }
                });
    }

    private void showError(Error error) {
        if (mView != null) {
            mView.showProgress(false);
            mView.showMessage(error.getMessage());
        }
    }

    private void showAllChats(List<Chat> chatList) {
        if (mView != null) {
            if (isFistChatEntering) {
                mView.showProgress(false);
                mView.showAllChats(chatList);
                isFistChatEntering = false;
            } else {
                mView.updateAllChats(chatList);
            }
        }
    }

    @Override
    public void onChatClick(Chat chat) {
        Bundle args = new Bundle();
        args.putInt(CHAT_SERVER_ID, chat.getChatId());
        args.putString(CHAT_ATTACHMENT, mAttachment);
        args.putInt(CHAT_UNREAD_COUNT, chat.getUnreadMessageCount());
        openChat(args);
    }

    private void openChat(Bundle args) {
        if (mView != null) {
            mView.showChat(args);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        mGetChats.dispose();
    }
}
