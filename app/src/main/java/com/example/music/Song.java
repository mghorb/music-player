package com.example.music;

import java.util.ArrayList;

public class Song {
    public static ArrayList<Song> list = new ArrayList<Song>();

    // private variables, the ID, name, title etc
    private int id;
    private String songName;
    private String albumTitle;
    private String artist;
    private int art;

    // constructor with all variables as parameters
    public Song(int id, String songName, String albumTitle, String artist, int art){
        this.id = id;
        this.songName = songName;
        this.albumTitle = albumTitle;
        this.artist = artist;
        this.art = art;
        this.list.add(this);
    }

    // getters

    public int getId() {
        return id;
    }

    public String getSongName() {
        return songName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtist() {
        return artist;
    }

    public int getArt() {
        return art;
    }
}



