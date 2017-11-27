package com.cardee.auth.pass_recover.send_email;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.user.usecase.SendEmail;
import com.cardee.mvp.BaseView;

public class SendEmailPresenter {

    private final SendEmail mSendEmailUseCase;
    private UseCaseExecutor mExecutor;
    private BaseView mView;

    public SendEmailPresenter(BaseView view) {
        mSendEmailUseCase = new SendEmail();
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
    }

    public void sendEmail(String email) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(mSendEmailUseCase, new SendEmail.RequestValues(email), new UseCase.Callback<SendEmail.ResponseValues>() {
            @Override
            public void onSuccess(SendEmail.ResponseValues response) {
                mView.showProgress(false);
                mView.showMessage("Reset password email sent!");
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
            }
        });
    }
}
