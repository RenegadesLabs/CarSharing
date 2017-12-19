package com.cardee.inbox.chat;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.inbox.usecase.GetChats;

public class ChatPresenterImp implements ChatContract.Presenter {

    private ChatContract.View mView;
    private final GetChats mGetChats;
    private final UseCaseExecutor mExecutor;

    ChatPresenterImp() {
        mGetChats = new GetChats();
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void onInit(ChatContract.View view) {
        mView = view;
    }

    @Override
    public void onGetChats() {
        mExecutor.execute(mGetChats,
                new GetChats.RequestValues(),
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
    public void onDestroy() {
        mView = null;
    }
}
