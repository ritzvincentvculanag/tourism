package io.github.rmmc.rmmctourism.util;

import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Messenger {
    public static MaterialAlertDialogBuilder showAlertDialog(
            Context context,
            String title,
            String message
    ) {
        return new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message);
    }

    public static MaterialAlertDialogBuilder showAlertDialog(
            Context context,
            String title,
            String message,
            String positive,
            String negative,
            DialogInterface.OnClickListener positiveAction,
            DialogInterface.OnClickListener negativeAction
    ) {
        return new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, positiveAction)
                .setNegativeButton(negative, negativeAction);
    }
}
