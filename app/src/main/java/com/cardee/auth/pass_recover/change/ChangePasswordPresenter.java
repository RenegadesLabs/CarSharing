package com.cardee.auth.pass_recover.change;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.user.usecase.ChangePassword;

public class ChangePasswordPresenter {

    private final ChangePassword mChangePassUseCase;
    private UseCaseExecutor mExecutor;
    private ChangePassView mView;

    public ChangePasswordPresenter(ChangePassView view) {
        mChangePassUseCase = new ChangePassword();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
    }

    public void changePassword(String key, String pass, String passConfirm) {
        if (mView != null)
            mView.showProgress(true);

        mExecutor.execute(mChangePassUseCase, new ChangePassword.RequestValues(key, pass, passConfirm), new UseCase.Callback<ChangePassword.ResponseValues>() {
            @Override
            public void onSuccess(ChangePassword.ResponseValues response) {
                mView.showProgress(false);
                if (response.getSuccess()) {
                    mView.showMessage(R.string.change_pass_success);
                    mView.onSuccessfullyChanged();
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }
}
