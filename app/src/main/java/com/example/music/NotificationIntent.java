package com.example.music;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;
import static com.example.music.App.CHANNEL_1_ID;

public class NotificationIntent extends IntentService {
    private static final String PAUSE_ACTION = "com.example.music";

    public NotificationIntent(){
        super("NotificationIntent");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String NOTIFICATION_CHANNEL_ID = "com.example.music.PAUSE_ACTION";
        String channelName = "My Background Service";

        // create an intent to pause in the notification
        Intent intentPause = new Intent(this,ActionReceiver.class);
        intentPause.putExtra("action","pause");
        // pending intent using the pause intent above
        PendingIntent pause = PendingIntent.getBroadcast(this,1, intentPause, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Now Playing: " + intent.getStringExtra("Name"))
                .addAction(R.drawable.ic_launcher_foreground, "Play/Pause", pause)
                //the pause action is used when the play/pause button is clicked
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);

        for (int i = 0; i < 1000; i++) {
            SystemClock.sleep(10000000); // keep the notification until you turn notifications off on your android phone pretty much lol
        }

    }

}
