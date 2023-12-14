package io.github.rmmc.rmmctourism.util;

import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Utility class for creating MaterialAlertDialog instances.
 */
public class Messenger {

    /**
     * Shows a simple alert dialog with a positive button.
     *
     * @param context             The context in which the dialog should be displayed
     * @param title               The title of the alert dialog
     * @param message             The message displayed in the alert dialog
     * @param positiveButtonTitle The text to display on the positive button
     * @return A MaterialAlertDialogBuilder instance for further customization if needed
     */
    public static MaterialAlertDialogBuilder showAlertDialog(
            Context context,
            String title,
            String message,
            String positiveButtonTitle
    ) {
        return new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonTitle, (dialog, which) -> {
                });
    }

    /**
     * Shows an alert dialog with positive and negative buttons.
     *
     * @param context        The context in which the dialog should be displayed
     * @param title          The title of the alert dialog
     * @param message        The message displayed in the alert dialog
     * @param positive       The text to display on the positive button
     * @param negative       The text to display on the negative button
     * @param positiveAction The action to perform when the positive button is clicked
     * @param negativeAction The action to perform when the negative button is clicked
     * @return A MaterialAlertDialogBuilder instance for further customization if needed
     */
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
