package com.cardee.data_source.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.cardee.R;

public class DialogHelper {

    public static ProgressDialog getProgressDialog(Context context, String message, boolean cancelable) {
        ProgressDialog d = new ProgressDialog(context);
        d.setMessage(message);
        d.setCancelable(cancelable);
        return d;
    }

    public static AlertDialog getAlertDialog(final Context context, @LayoutRes int layout, String positiveButtonText, final OnClickCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.NoteChangeTextInputStyle));
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        final View inflater = layoutInflater.inflate(layout, null);
        final TextInputEditText editText = inflater.findViewById(R.id.note_edit);
        builder.setTitle(context.getResources().getString(R.string.owner_profile_info_note_change_title))
                .setView(inflater)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String newNote = editText.getText().toString();
                        if (newNote == null || newNote.isEmpty()) {
                            editText.setError("");
                        } else {
                            callback.onPositiveButtonClick(newNote, dialog);
                        }
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                callback.onNegativeButtonClick(dialog);
                            }
                        });

        return builder.create();
    }

    public interface OnClickCallback {

        void onPositiveButtonClick(String newNote, DialogInterface dialog);

        void onNegativeButtonClick(DialogInterface dialog);
    }
}
