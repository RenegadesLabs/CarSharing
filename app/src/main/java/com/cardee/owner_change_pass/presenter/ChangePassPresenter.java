package com.cardee.owner_change_pass.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.ChangePass;
import com.cardee.owner_change_pass.view.ChangePassView;

public class ChangePassPresenter {

    private ChangePassView mView;
    private UseCaseExecutor mExecutor;
    private ChangePass mChangePassUseCase;
    private SharedPreferences mSharedPref;
    private String mPassLengthKey;


    public ChangePassPresenter(ChangePassView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mChangePassUseCase = new ChangePass();
        Context context = (Context) mView;
        mPassLengthKey = context.getString(R.string.pass_length_key);
        mSharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void changePassword(String oldPass, final String newPass) {
        if (mView != null) {
            mView.showProgress(true);

            final ChangePass.RequestValues values = new ChangePass.RequestValues(oldPass, newPass);
            mExecutor.execute(mChangePassUseCase, values, new UseCase.Callback<ChangePass.ResponseValues>() {
                @Override
                public void onSuccess(ChangePass.ResponseValues response) {
                    mView.showProgress(false);
                    mView.onSuccessfullyChanged();
                    saveSharedPrefs(newPass.length());
                }

                @Override
                public void onError(Error error) {
                    mView.showProgress(false);
                    mView.showMessage(error.getMessage());
                }
            });
        }
    }

    private void saveSharedPrefs(int newPassLength) {
        mSharedPref.edit().putInt(mPassLengthKey, newPassLength).apply();
    }

}
