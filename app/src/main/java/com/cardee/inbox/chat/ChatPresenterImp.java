package com.cardee.inbox.chat;

import android.content.Context;

import com.cardee.data_source.Error;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.inbox.usecase.GetChats;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import io.reactivex.functions.Consumer;

public class ChatPresenterImp implements ChatContract.Presenter, Consumer<InboxChat> {

    private ChatContract.View mView;

    private final GetChats mGetChats;
    private final UseCaseExecutor mExecutor;
    private final String mAttachment;

    ChatPresenterImp(Context context) {
        mGetChats = new GetChats();
        mExecutor = UseCaseExecutor.getInstance();
        mAttachment = AccountManager.getInstance(context).getSessionInfo();
    }

    @Override
    public void onInit(ChatContract.View view) {
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
            mView.showAllChats(response.getInboxChats());
        }
    }

    @Override
    public void accept(InboxChat inboxChat) throws Exception {
        mView.showChat();
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

}
