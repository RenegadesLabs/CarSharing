package com.cardee.inbox;

public interface InboxContract {

    interface Presenter {

        void init(InboxContract.View view);

        void onDestroy();
    }

    interface View {

        void setAlertTabState(boolean isUnread);

        void setChatTabState(boolean isUnread);
    }

}
