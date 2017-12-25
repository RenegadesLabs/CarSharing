package com.cardee.inbox.chat.item.presenter;

import android.os.Bundle;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.inbox.usecase.chat.GetChatInfo;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;
import com.cardee.inbox.chat.item.view.ChatContract;

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View mView;
    private final GetChatInfo mGetInfoUseCase;
    private final UseCaseExecutor mExecutor;

    private int mChatDatabaseId;
    private int mChatServerId;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mGetInfoUseCase = new GetChatInfo();
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init(Bundle bundle) {
        mChatDatabaseId = bundle.getInt(Chat.CHAT_DB_ID);
        mChatServerId = bundle.getInt(Chat.CHAT_SERVER_ID);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public ChatInfo onChatDataRequest(int databaseId, int serverId) {
        return null;
    }
}
