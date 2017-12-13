package com.cardee.owner_cardee.presenter;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.SendFeedback;
import com.cardee.owner_cardee.view.OwnerCardeeView;

public class OwnerCardeePresenter {
    private OwnerCardeeView mView;
    private SendFeedback mSendFeedback;
    private UseCaseExecutor mExecutor;

    public OwnerCardeePresenter(OwnerCardeeView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mSendFeedback = new SendFeedback();
    }

    public void onSubmitCliceked(String feedback) {
        mView.showProgress(true);
        mExecutor.execute(mSendFeedback, new SendFeedback.RequestValues(feedback), new UseCase.Callback<SendFeedback.ResponseValues>() {
            @Override
            public void onSuccess(SendFeedback.ResponseValues response) {
                mView.showProgress(false);
                mView.showMessage(R.string.send_feedback_success);
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }
}
