package com.thuanht.eatez.view.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.thuanht.eatez.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
//        int alpha = 64; // 50% alpha (0-255)
//        int red = 0;
//        int green = 0;
//        int blue = 0;
//        int rgbaColor = Color.argb(alpha, red, green, blue);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        setContentView(view);
    }

}
