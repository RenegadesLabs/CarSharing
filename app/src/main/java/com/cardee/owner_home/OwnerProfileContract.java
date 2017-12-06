package com.cardee.owner_home;


import com.cardee.mvp.BaseView;

public interface OwnerProfileContract {

    interface View extends BaseView {

        void openOwnerProfile();

        void openAccount();

        void openSettings();

        void openCreditBalance();

        void openLiveChat();

        void openInviteFriends();

        void openCardee();

        void openSwitchToRenter();

        void setProfileImage(String profilePhotoLink);

        void setProfileName(String name);

        void setCreditBalance(String creditBalance);
    }

    enum Action {
        ACCOUNT_CLICKED, SETTINGS_CLICKED, CREDIT_CLICKED, CHAT_CLICKED, INVITE_CLICKED, CARDEE_CLICKED, SWITCH_CLICKED
    }
}
