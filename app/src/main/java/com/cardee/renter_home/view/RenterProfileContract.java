package com.cardee.renter_home.view;


import com.cardee.mvp.BaseView;

public interface RenterProfileContract {

    interface View extends BaseView {

        void openOwnerProfile();

        void openAccount();

        void openSettings();

        void openLiveChat();

        void openInviteFriends();

        void openCardee();

        void openSwitchToRenter();

        void setProfileImage(String profilePhotoLink);

        void setProfileName(String name);

    }

    enum Action {
        ACCOUNT_CLICKED, SETTINGS_CLICKED, CHAT_CLICKED, INVITE_CLICKED, CARDEE_CLICKED, SWITCH_CLICKED
    }
}
