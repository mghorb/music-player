package com.example.music;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Song> list;
    private static Intent currentIntent;
    private static Intent notif;
    private static boolean serviceRunning = false;

    //Constructor to set up the list of items
    public RecyclerAdapter(List<Song> list, Context context) {
        this.list = list;
    }

    //inflate the list with song_layouts (an xml layout we created), with the parent's list, which is given as a parameter in constructor
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_layout, parent, false);
        return new ViewHolder(view);
    }

    //Set each item to display information
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = list.get(position);
        holder.textViewSongName.setText(song.getSongName());
        holder.textViewArtist.setText(song.getArtist());
        holder.textViewAlbum.setText(song.getAlbumTitle());
        holder.image.setImageResource(song.getArt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSongName, textViewArtist, textViewAlbum;
        ImageView image;
        private NotificationManagerCompat mNotificationManagerCompat;

        public ViewHolder(final View itemView) {
            super(itemView);

            mNotificationManagerCompat = NotificationManagerCompat.from(itemView.getContext());

            textViewSongName = itemView.findViewById(R.id.songName);
            textViewArtist = itemView.findViewById(R.id.artist);
            textViewAlbum = itemView.findViewById(R.id.albumName);
            image = itemView.findViewById(R.id.image);

            //when the song is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //get which song is you clicked on
                    Song song = Song.list.get(getAdapterPosition());

                    startSong(v, song, getAdapterPosition()); // start song function found below

                }
            });
        }
    }

    public void filterList(ArrayList<Song> filteredList) {
        list = filteredList; // set the list to the filtered list
        notifyDataSetChanged();  // notify data set changed
    }

    public static void startSong(View v, Song song, int index) {

        if (serviceRunning) { //if a song is currently playing stop it
            v.getContext().stopService(currentIntent);
            v.getContext().stopService(notif);
        }

        //start the service to play the song
        currentIntent = new Intent(v.getContext(), MusicPlayerService.class);
        currentIntent.putExtra("Song ID", song.getId());
        v.getContext().startService(currentIntent); // start the music player service to play the song


        // send a notification
        notif = new Intent(v.getContext(), NotificationIntent.class);
        notif.putExtra("Name", song.getSongName());
        notif.putExtra("Artist", song.getArtist());
        v.getContext().startService(notif); // start the notification service

        serviceRunning = true;

        //start the activity to open up the song and the song view
        Intent intent = new Intent(v.getContext(), ViewSong.class);
        intent.putExtra("Song Name", song.getSongName());
        intent.putExtra("Artist Name", song.getArtist());
        intent.putExtra("Art", song.getArt());
        intent.putExtra("Index", index);
        v.getContext().startActivity(intent);
    }

}
