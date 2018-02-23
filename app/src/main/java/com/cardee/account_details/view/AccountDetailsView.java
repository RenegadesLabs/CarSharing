package com.cardee.account_details.view;

import com.cardee.mvp.BaseView;

public interface AccountDetailsView extends BaseView {

    void setName(String name);

    void setEmail(String email);

    void setPhone(String phone);

    void setPass(String password);

    void setPassChangeState(boolean b);

    void hidePassword();

    void setVerified(boolean verified);
}
