package com.cardee.account_details.presenter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.cardee.R;
import com.cardee.account_details.adapter.CardsAdapter;
import com.cardee.account_details.view.AccountDetailsView;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.payments.response.CardsResponseBody;
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile;
import com.cardee.data_source.util.DialogHelper;
import com.cardee.domain.RxUseCase;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.ChangeEmail;
import com.cardee.domain.owner.usecase.ChangeName;
import com.cardee.domain.owner.usecase.ChangePhone;
import com.cardee.domain.owner.usecase.GetOwnerInfo;
import com.cardee.domain.payments.usecase.GetCardsUseCase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class AccountDetailsPresenter {

    private final GetOwnerInfo mGetInfoUseCase;
    private final ChangeName mChangeName;
    private final ChangeEmail mChangeEmail;
    private final ChangePhone mChangePhone;
    private UseCaseExecutor mExecutor;
    private AccountDetailsView mView;
    private SharedPreferences mSharedPref;
    private final String mPassLengthKey;
    private final String mSocialLoggedInKey;
    private final String mDefaultPhoneText;
    private CardsAdapter mAdapter;
    private GetCardsUseCase getCards = null;
    private Disposable disposable = null;

    public AccountDetailsPresenter(AccountDetailsView view) {
        mGetInfoUseCase = new GetOwnerInfo();
        mChangeName = new ChangeName();
        mChangeEmail = new ChangeEmail();
        mChangePhone = new ChangePhone();
        getCards = new GetCardsUseCase();
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
        Context context = (Context) mView;
        mDefaultPhoneText = context.getString(R.string.account_details_default_phone_text);
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

                        String phone = profile.getPhone();
                        if (phone == null || phone.length() < 13) {
                            phone = mDefaultPhoneText;
                        }
                        mView.setPhone(phone);

                        if (profile.getSocialNetwork() != null) {
                            mView.hidePassword();
                        }
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

    public void showChangeNameDialog(Context context) {
        DialogHelper.getAlertDialog(context, R.layout.dialog_account_details_change_name,
                context.getResources().getString(R.string.account_details_change_name_title),
                context.getResources().getString(R.string.profile_info_note_change),
                new DialogHelper.OnClickCallback() {
                    @Override
                    public void onPositiveButtonClick(final String newName, final DialogInterface dialog) {
                        mView.showProgress(true);
                        mExecutor.execute(mChangeName, new ChangeName.RequestValues(newName), new UseCase.Callback<ChangeName.ResponseValues>() {
                            @Override
                            public void onSuccess(ChangeName.ResponseValues response) {
                                mView.showProgress(false);
                                dialog.dismiss();
                                mView.setName(newName);
                                mView.showMessage(R.string.owner_profile_info_name_change_success);
                            }

                            @Override
                            public void onError(Error error) {
                                mView.showProgress(false);
                                mView.showMessage(error.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }).show();

    }

    public void showChangeEmailDialog(Context context) {
        DialogHelper.getAlertDialog(context, R.layout.dialog_account_details_change_email,
                context.getResources().getString(R.string.account_details_change_email_title),
                context.getResources().getString(R.string.profile_info_note_change),
                new DialogHelper.OnClickCallback() {
                    @Override
                    public void onPositiveButtonClick(final String newEmail, final DialogInterface dialog) {
                        mView.showProgress(true);
                        mExecutor.execute(mChangeEmail, new ChangeEmail.RequestValues(newEmail), new UseCase.Callback<ChangeEmail.ResponseValues>() {
                            @Override
                            public void onSuccess(ChangeEmail.ResponseValues response) {
                                mView.showProgress(false);
                                dialog.dismiss();
                                mView.setEmail(newEmail);
                                mView.showMessage(R.string.account_details_email_change_success);
                            }

                            @Override
                            public void onError(Error error) {
                                mView.showProgress(false);
                                mView.showMessage(error.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void showChangePhoneDialog(Context context) {
        DialogHelper.getAlertDialog(context, R.layout.dialog_account_details_change_phone,
                context.getResources().getString(R.string.account_details_change_phone_title),
                context.getResources().getString(R.string.profile_info_note_change),
                new DialogHelper.OnClickCallback() {
                    @Override
                    public void onPositiveButtonClick(final String newPhone, final DialogInterface dialog) {
                        mView.showProgress(true);
                        mExecutor.execute(mChangePhone, new ChangePhone.RequestValues(newPhone), new UseCase.Callback<ChangePhone.ResponseValues>() {
                            @Override
                            public void onSuccess(ChangePhone.ResponseValues response) {
                                mView.showProgress(false);
                                dialog.dismiss();
                                mView.setPhone(newPhone);
                                mView.showMessage(R.string.account_details_phone_change_success);
                            }

                            @Override
                            public void onError(Error error) {
                                mView.showProgress(false);
                                mView.showMessage(error.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void getCards() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = getCards.execute(new GetCardsUseCase.RequestValues(), new RxUseCase.Callback<GetCardsUseCase.ResponseValues>() {
            @Override
            public void onError(@NotNull Error error) {
                if (mView != null) {
                    mView.showMessage(error.getMessage());
                }
            }

            @Override
            public void onSuccess(GetCardsUseCase.ResponseValues response) {
                List<CardsResponseBody> dataList = response.getCards();
                mAdapter.setData(dataList);
            }
        });
    }

    public void setAdapter(CardsAdapter adapter) {
        mAdapter = adapter;
    }

    public void onDestroy() {
        mView = null;
        mAdapter.onDestroy();
    }
}
