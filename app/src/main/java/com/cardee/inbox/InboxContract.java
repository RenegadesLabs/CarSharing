package com.cardee.inbox;

import com.cardee.mvp.BaseView;

public interface InboxContract {

    interface Presenter {

        void init(InboxContract.View view, int position);

        void onDestroy();
    }

    interface View extends BaseView {

    }

}
