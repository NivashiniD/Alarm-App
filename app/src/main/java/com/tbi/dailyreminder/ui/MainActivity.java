package com.tbi.dailyreminder.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.tbi.dailyreminder.broadcast.ReminderBroadcast;
import com.tbi.dailyreminder.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();
        onClick();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void onClick() {

        binding.tvTime.setOnClickListener(v -> {
            timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Select Alarm Time")
                    .build();
            timePicker.show(getSupportFragmentManager(), "Notify");

            timePicker.addOnPositiveButtonClickListener(view -> {
                if (timePicker.getHour() > 12) {
                    binding.tvTime.setText(
                            String.format("%02d", (timePicker.getHour() - 12)) + ":" + String.format("%02d", timePicker.getMinute()) + "PM"
                    );
                } else {
                    binding.tvTime.setText(timePicker.getHour() + ":" + timePicker.getMinute() + "AM");
                }
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            });

        });

        binding.btnSetUp.setOnClickListener(v -> {

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmSound == null){
                // Alarm sound not found, fallback to notification sound
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);

            intent.putExtra("alarmSound", alarmSound.toString());

            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();

        });
    }

    //BroadCast  notification created
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Alarm";
            String desc = "Channel for Alarm Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Notify", name, imp);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}