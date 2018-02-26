package com.cardee.account_details.view;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.cardee.R;
import com.cardee.mvp.BaseView;

public interface AccountDetailsView extends BaseView {

    void setName(String name);

    void setEmail(String email);

    void setPhone(String phone);

    void setPass(String password);

    void setPassChangeState(boolean b);

    void hidePassword();

    void setVerified(boolean verified);

    void setDepositStatus(DepositStatus status);

    enum DepositStatus {
        PAID(R.color.positive_value_green, R.string.deposit_paid),
        NOT_PAID(R.color.red_error, R.string.deposit_not_paid);

        @ColorRes
        final int textColorId;
        @StringRes
        final int textId;

        private DepositStatus(int textColorId, int textId) {
            this.textColorId = textColorId;
            this.textId = textId;
        }
    }
}
