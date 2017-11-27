package com.cardee.data_source.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.cardee.CardeeApp;

public class DialogHelper {

    public static ProgressDialog getProgressDialog(Context context, String message, boolean cancelable) {
        ProgressDialog d = new ProgressDialog(context);
        d.setMessage(message);
        d.setCancelable(cancelable);
        return d;
    }
}
