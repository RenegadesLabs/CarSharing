package com.cardee.owner_account_details.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.cardee.R;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetOwnerInfo;
import com.cardee.owner_account_details.view.AccountDetailsView;

public class AccountDetailsPresenter {

    private final GetOwnerInfo mGetInfoUseCase;
    private UseCaseExecutor mExecutor;
    private AccountDetailsView mView;
    private SharedPreferences mSharedPref;
    private String mPassLengthKey;
    private String mSocialLoggedInKey;

    public AccountDetailsPresenter(AccountDetailsView view) {
        mGetInfoUseCase = new GetOwnerInfo();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
        Context context = (Context) mView;
        mPassLengthKey = context.getString(R.string.pass_length_key);
        mSocialLoggedInKey = context.getString(R.string.social_logged_key);
        mSharedPref = (context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE));
    }

    public void getOwnerInfo() {
        mView.showProgress(true);

        mExecutor.execute(mGetInfoUseCase, null, new UseCase.Callback<GetOwnerInfo.ResponseValues>() {
            @Override
            public void onSuccess(GetOwnerInfo.ResponseValues response) {
                if (mView != null) {
                    OwnerProfile profile = response.getOwnerProfile();
                    if (profile != null) {
                        mView.setName(profile.getName());
                        mView.setEmail(profile.getEmail());
                        mView.setPhone(profile.getPhone());
                    }
                }

                mView.showProgress(false);
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }

    public void readPassFromSharedPref() {
        boolean socialLoggedIn = mSharedPref.getBoolean(mSocialLoggedInKey, false);
        int passLength = mSharedPref.getInt(mPassLengthKey, 0);
        if (socialLoggedIn) {
            passLength = 0;
            mView.setPassChangeState(false);
        }
        String repeated = new String(new char[passLength]);
        mView.setPass(repeated);
    }

}
