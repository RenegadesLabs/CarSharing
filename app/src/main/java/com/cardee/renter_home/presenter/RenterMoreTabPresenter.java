package com.cardee.renter_home.presenter;

import android.view.View;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.renter.usecase.GetRenterProfile;
import com.cardee.renter_home.view.RenterProfileContract;

import io.reactivex.functions.Consumer;

public class RenterMoreTabPresenter implements Consumer<RenterProfileContract.Action> {

    private RenterProfileContract.View mView;
    private final UseCaseExecutor mExecutor;
    private final GetRenterProfile mGetProfileUseCase;

    public RenterMoreTabPresenter(RenterProfileContract.View view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mGetProfileUseCase = new GetRenterProfile();
    }

    public void loadProfile() {
        mView.showProgress(true);
        mExecutor.execute(mGetProfileUseCase, null, new UseCase.Callback<GetRenterProfile.ResponseValues>() {
            @Override
            public void onSuccess(GetRenterProfile.ResponseValues response) {
                mView.showProgress(false);
                RenterProfile profile = response.getRenterProfile();
                if (profile != null) {
                    mView.setProfileImage(profile.getProfilePhoto());
                    mView.setProfileName(profile.getName());
                }
            }

            @Override
            public void onError(Error error) {
                mView.showProgress(false);
                mView.showMessage(error.getMessage());
            }
        });
    }

    @Override
    public void accept(RenterProfileContract.Action action) throws Exception {
        switch (action) {
            case ACCOUNT_CLICKED:
                mView.openAccount();
                break;
            case SETTINGS_CLICKED:
                mView.openSettings();
                break;
            case CHAT_CLICKED:
                mView.openLiveChat();
                break;
            case INVITE_CLICKED:
                mView.openInviteFriends();
                break;
            case CARDEE_CLICKED:
                mView.openCardee();
                break;
            case SWITCH_CLICKED:
                mView.openSwitchToRenter();
                break;
        }
    }

    public void setOnClickListenerToHeader(View header) {
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.openOwnerProfile();
            }
        });
    }

    public void setAccState(AccountManager.ACC_STATE state) {
        AccountManager.getInstance(CardeeApp.context).setCurrentState(state);
    }
}
