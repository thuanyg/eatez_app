package com.thuanht.eatez.firebase.FirebaseCloudMessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thuanht.eatez.R;
import com.thuanht.eatez.app.MyApplication;
import com.thuanht.eatez.view.Activity.PostDetailActivity;
import com.thuanht.eatez.view.Activity.SettingActivity;

import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification = message.getNotification();
        if (notification == null) return;

        String title = notification.getTitle();
        String messageContent = notification.getBody();

        SendNotification(title, messageContent);
    }

    private void SendNotification(String title, String messageContent) {
        int id = getNotificationID();
        Intent intent = new Intent(this, SettingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, MyApplication.CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(messageContent)
                    .setSmallIcon(R.mipmap.ic_launcher_monochrome)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if(notificationManager != null){
                notificationManager.notify(id, notification);
            }
        }
    }

    private int getNotificationID(){
        return (int) System.currentTimeMillis();
    }
}
