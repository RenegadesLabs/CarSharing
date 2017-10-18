package android.cardee.com.cardee.mvp;

import android.support.annotation.StringRes;

public interface BaseView {

    void showProgress(boolean show);

    void showMessage(String message);

    void showMessage(@StringRes int messageId);
}
