package com.example.triviaapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;


public class TriviaNotification extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean switchPreff = sharedPreferences.getBoolean("switch", true);

        Log.d("NOTIFICATION", "onReceive: ");

        if (!switchPreff){

            return;

        }else{

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ID", "Channel_1", NotificationManager.IMPORTANCE_DEFAULT);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }

            Intent openTriviaIntent = new Intent(context, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, openTriviaIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(context, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Are you feeling Smart today?")
                    .setContentText("Play another Trivia")
                    .setContentIntent(pendingIntent)
                    .build();

            if (notificationManager != null) {
                notificationManager.notify(1, notification);
            }
        }

    }
}
