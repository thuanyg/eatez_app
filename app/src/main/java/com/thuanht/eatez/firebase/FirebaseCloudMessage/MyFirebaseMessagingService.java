package com.thuanht.eatez.firebase.FirebaseCloudMessage;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.app.MyApplication;
import com.thuanht.eatez.database.database.AppDatabase;
import com.thuanht.eatez.jsonResponse.SignupResponse;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.retrofit.ApiService;
import com.thuanht.eatez.utils.DateUtils;
import com.thuanht.eatez.view.Activity.PostDetailActivity;
import com.thuanht.eatez.view.Activity.SettingActivity;
import com.thuanht.eatez.viewModel.UserViewModel;

import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private int postid = 0;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
//        RemoteMessage.Notification notification = message.getNotification();
//        if (notification == null) return;

//        String title = notification.getTitle();
//        String messageContent = notification.getBody();
//        postid = Integer.parseInt(message.getData().get("postid"));

        // Data message
        Map<String, String> stringMap = message.getData();
        String title = stringMap.get("title");
        String messageContent = stringMap.get("message");
        String imageLink = stringMap.get("imageLink");
        postid = Integer.parseInt(stringMap.get("postid"));
        SendNotification(title, messageContent, imageLink);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SaveNotification(title, messageContent, imageLink);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        updateUserTokenToServer(token);
    }

    private void updateUserTokenToServer(String token) {
        User user = null;
        try{
            user = LocalDataManager.getInstance().getUserLogin();
        } catch (Exception e){

        }
        if(user != null){
            int userid = user.getUserid();
            Call<SignupResponse> call = ApiService.ApiService.updateToken(userid, token);
            call.enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    if(response.isSuccessful())
                        Toast.makeText(MyFirebaseMessagingService.this, "Updated token", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable throwable) {
                    Toast.makeText(MyFirebaseMessagingService.this, "Error. Something wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SaveNotification(String title, String messageContent, String imageUrl) {
        com.thuanht.eatez.database.entity.Notification notification = new com.thuanht.eatez.database.entity.Notification();
        notification.setTitle(title);
        notification.setMessage(messageContent);
        notification.setImageLink(imageUrl);
        notification.setDate(DateUtils.getInstance().getCurrentDateTime());
        AppDatabase.getInstance(this).notificationDAO().insert(notification);
    }

    private void SendNotification(String title, String messageContent, String imageUrl) {
        int id = getNotificationID();
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("postid", postid);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Load Image
        Bitmap largeIconBitmap = null;
        try {
            FutureTarget futureTarget = Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .submit();
            largeIconBitmap = (Bitmap) futureTarget.get();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        // Get the bitmap from FutureTarget



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(messageContent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLargeIcon(largeIconBitmap)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (notificationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManager.notify(id, notification);
        }
    }

    private int getNotificationID() {
        return (int) System.currentTimeMillis();
    }
}
