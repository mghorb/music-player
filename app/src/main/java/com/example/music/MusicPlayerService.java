package com.example.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;


public class MusicPlayerService extends Service {
    protected static MediaPlayer player;
    private int songID;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        // when the music player is started get the song id
        songID = intent.getIntExtra("Song ID", 0);
        //  create the media play and start
        player = MediaPlayer.create(this,songID);
        player.setLooping(true);
        player.start();

        //Start Sticky means that the user starts and stops this and no other way of stopping it.
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // stop the music when the service is destroyed, by starting a new song
        player.stop();

    }

    public void pause(){
        // public method to pause the song
        player.pause();
    }

}
