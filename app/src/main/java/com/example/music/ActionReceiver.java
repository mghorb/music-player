package com.example.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.music.ViewSong.setStartText;


public class ActionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        // if the pause action was selected from the notification
        String action=intent.getStringExtra("action");
        if(action.equals("pause")){
            //pause the song
            performPause();
        }

        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    public void performPause(){
        if(MusicPlayerService.player.isPlaying()) { // if the song is playing, button will pause
            MusicPlayerService.player.pause();
            setStartText(">");

        }else{
            MusicPlayerService.player.start(); // otherwise start the song.
            setStartText("||");
        }
    }


}