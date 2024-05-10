package com.thuanht.eatez.view.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.view.Activity.LoginActivity;

// Tried to cover up all properties
// But because of in v 2.0.0 I've added a lot of functions so it is tough to to show all
// properties here. So, it will be better if you explore them by yourself.

public class DialogUtil {
    private static String heading;
    private static String description;
    private static String PositiveButtonText;
    private static String NegativeButtonText;

    public DialogUtil() {
    }

    public static void showProgressDialog(Context context) {
        PopupDialog.getInstance(context)
                .progressDialogBuilder()
                .createProgressDialog()
                .setTint(R.color.pure_light_blue)
//                .setCancelable(false)
//                .setTimeout(3000)
                .build()
                .show();
    }

    public static void showLottieDialog(Context context) {
        PopupDialog.getInstance(context)
                .progressDialogBuilder()
                .createLottieDialog()
                .setRawRes(com.saadahmedev.popupdialog.R.raw.success)
                .setCancelable(true)
//                .setTimeout(3000)
                .setLottieAnimationSpeed(3F)
                .setLottieRepeatCount(Integer.MAX_VALUE)
                .build()
                .show();
    }

    public static void showStandardDialog(Context context, String heading, String description,
                                          String PositiveButtonText, String NegativeButtonText, DialogClickListener listener) {
        PopupDialog.getInstance(context)
                .standardDialogBuilder()
                .createStandardDialog()
                .setHeading(heading)
                .setDescription(description)
                .setIcon(R.drawable.ic_remove)
                .setIconColor(R.color.black)
//                .setFontFamily(R.font.cubano)
                .setCancelable(false)

                .setPositiveButtonBackgroundColor(R.color.red_hat_red)
                .setPositiveButtonCornerRadius(20F)
                .setNegativeButtonBackgroundColor(R.color.gray)
                .setNegativeButtonCornerRadius(20F)

                .setPositiveButtonText(PositiveButtonText)
                .setNegativeButtonText(NegativeButtonText)
                .setPositiveButtonTextColor(R.color.white)
                .setNegativeButtonTextColor(R.color.black)

                .setPositiveButtonRippleColor(R.color.white)
                .setNegativeButtonRippleColor(R.color.white)

//                .setBackground(R.drawable.bg_blue_10)
//                .setBackgroundColor(R.color.teal_200)
//                .setBackgroundCornerRadius(50F)
//                .setBackgroundCornerRadius(50F, 10F, 10F, 50F)
//                .setFontFamily(R.font.roboto)
//                .setHeadingFont(R.font.roboto)
//                .setDescriptionFont(R.font.roboto)
//                .setButtonFont(R.font.roboto)
//                .setHeadingFontSize(25F)
//                .setDescriptionFontSize(25F)
//                .setButtonFontSize(25F)

                .build(new StandardDialogActionListener() {
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        if (listener != null)
                            listener.onPositiveButtonClicked(dialog);
                    }

                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void showIOSDialog(Context context) {
        PopupDialog.getInstance(context)
                .standardDialogBuilder()
                .createIOSDialog()
                .setHeading(heading)
                .setDescription(description)
//                .setHeadingFont(R.font.cubano)
                .setPositiveButtonText(PositiveButtonText)
                .setNegativeButtonText(NegativeButtonText)
                .setPositiveButtonTextColor(R.color.purple_500)
                .setNegativeButtonTextColor(R.color.teal_200)

//                .setBackground(R.drawable.bg_blue_10)
//                .setBackgroundColor(R.color.teal_200)
//                .setBackgroundCornerRadius(50F)
//                .setBackgroundCornerRadius(50F, 10F, 10F, 50F)

                .build(new StandardDialogActionListener() {
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void showAlertDialog(Context context) {
        PopupDialog.getInstance(context)
                .standardDialogBuilder()
                .createAlertDialog()
                .setHeading(heading)
                .setDescription(description)
//                .setHeadingFont(R.font.cubano)
                .setPositiveButtonText(PositiveButtonText)
                .setNegativeButtonText(NegativeButtonText)
                .setPositiveButtonTextColor(R.color.purple_500)
                .setNegativeButtonTextColor(R.color.teal_200)

//                .setBackground(R.drawable.bg_blue_10)
//                .setBackgroundColor(R.color.teal_200)
//                .setBackgroundCornerRadius(50F)
//                .setBackgroundCornerRadius(50F, 10F, 10F, 50F)

                .build(new StandardDialogActionListener() {
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void showStatusDialog(Context context) {
        PopupDialog.getInstance(context)
                .statusDialogBuilder()
                .createStatusDialog()
                .setLottieIcon(com.saadahmedev.popupdialog.R.raw.success)
                .setHeading(heading)
                .setDescription(description)
//                .setHeadingFont(R.font.cubano)
                .setActionButtonText("Play")
                .setActionButtonTextColor(R.color.black)
                .setActionButtonBackgroundColor(R.color.purple_200)
                .setDismissButtonRippleColor(R.color.white)

//                .setBackground(R.drawable.bg_blue_10)
//                .setBackgroundColor(R.color.teal_200)
//                .setBackgroundCornerRadius(50F)
//                .setBackgroundCornerRadius(50F, 10F, 10F, 50F)

                .build(Dialog::dismiss)
                .show();
    }

    public static void showSuccessDialog(Context context, String heading, String description, String button) {
        PopupDialog.getInstance(context)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading(heading)
                .setDescription(description)
                .setActionButtonText(button)
                .setActionButtonTextColor(R.color.black)
                .setActionButtonBackgroundColor(R.color.button_color)
                .setDismissButtonRippleColor(R.color.white)
                .build(Dialog::dismiss)
                .show();
    }

    public static void showErrorDalog(Context context, String heading, String description, String button) {
        PopupDialog.getInstance(context)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Uh-Oh")
                .setDescription("Unexpected error occurred." +
                        " Try again later.")
                .build(Dialog::dismiss)
                .show();
    }

    public interface DialogClickListener {
        void onPositiveButtonClicked(Dialog dialog);

        void onNegativeButtonClicked();
    }

    public static boolean checkLoginAndRequired(Activity activity) {
        if (LocalDataManager.getInstance().getUserLogin() == null) {
            showStandardDialog(activity, "Login required", "Please login your account to do this function.",
                    "OK", "Cancel", new DialogUtil.DialogClickListener() {
                        @Override
                        public void onPositiveButtonClicked(Dialog dialog) {
                            activity.finish();
                            activity.startActivity(new Intent(activity, LoginActivity.class));
                        }

                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });
            return false;
        } else return true;
    }
}