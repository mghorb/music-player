package com.example.music;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewSong extends AppCompatActivity implements View.OnClickListener {

    private static Button start;
    private TextView artist, song;
    private Button forward, backward;
    private ImageView art;

    private Handler handler;
    private Runnable runnable;
    private static SeekBar seekBar;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song);

        // create all the buttons
        start = findViewById(R.id.startButton);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);
        song = findViewById(R.id.songName);
        artist = findViewById(R.id.artistName);
        art = findViewById(R.id.art);


        // set the on click listeners
        start.setOnClickListener(this);
        forward.setOnClickListener(this);
        backward.setOnClickListener(this);

        seekBar = findViewById(R.id.seekBar);
        handler = new Handler();

        // get the intent and the song name
        Intent intent = getIntent();
        song.setText(intent.getStringExtra("Song Name"));
        artist.setText(intent.getStringExtra("Artist Name"));
        art.setImageResource(intent.getIntExtra("Art", 0));
        index = intent.getIntExtra("Index", 0);

        ViewSong.seekBar.setMax(MusicPlayerService.player.getDuration());
        // play recursively (used later)
        playCycle();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    //update the progress bar
                    MusicPlayerService.player.seekTo(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //set up a back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    //the play cycle sets up the progress bar updates only
    public void playCycle(){
        seekBar.setProgress(MusicPlayerService.player.getCurrentPosition());
        if(MusicPlayerService.player.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    // recursively calls again to cause asynchronous updates
                    playCycle();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }

    // this function is to scrub forward and backward
    private void changeSeekbar(){
        seekBar.setProgress(MusicPlayerService.player.getCurrentPosition());
        if (MusicPlayerService.player.isPlaying()){
            runnable = new Runnable(){
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.startButton:
               if(MusicPlayerService.player.isPlaying()) { // if the song is playing, button will pause
                   MusicPlayerService.player.pause();
                   start.setText(">");
               }else{
                   MusicPlayerService.player.start(); // otherwise start the song.
                   start.setText("||");
                   changeSeekbar();
               }
                break;
            case R.id.forward:
                if (index == Song.list.size() - 1){ // if the forward button is pressed and it is the last song
                    index = -1;} // set the index to the first song
                RecyclerAdapter.startSong(view, Song.list.get(index+1), index+1); // play the next song
                break;
            case R.id.backward:
                if (index == 0){ // if the back button is pressed and it is the first song
                    index = Song.list.size();} // set the index to the last song
                RecyclerAdapter.startSong(view, Song.list.get(index-1), index-1); // play the song before this one
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        // finish the activity when the back button is pressed
        finish();
        return true;
    }

    public static void setStartText(String string){
        // public method to set the start button's text, to set it to stop or play "||" ">"
        start.setText(string);
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // on the android back button being pressed go to android home
        startActivity(startMain);
    }


}
