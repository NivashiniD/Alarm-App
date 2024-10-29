package com.tbi.dailyreminder.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.tbi.dailyreminder.R;

public class ReminderBroadcast extends BroadcastReceiver {


    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
       String contentTitle = "Reminder";
       String contentText = "It's time to wake up";

        String alarmSoundUriString = intent.getStringExtra("alarmSound");
        Uri alarmSoundUri = Uri.parse(alarmSoundUriString);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notify")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Reminder")
                .setContentText("It's time to wake up")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(alarmSoundUri);


        System.out.println("Title"+contentTitle);

        Log.d("NotificationTitle", "Title: " + contentTitle);
        Log.d("NotificationContent", "Content: " + contentText);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200,builder.build());

        Log.d("NotificationSent", "Notification sent successfully");
    }
}
