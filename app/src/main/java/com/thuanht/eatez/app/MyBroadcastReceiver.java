package com.thuanht.eatez.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.thuanht.eatez.utils.NetworkUtils;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            if(NetworkUtils.isNetworkAvailable(context)){
//                Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Network disconnected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
