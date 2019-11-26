package com.example.music;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static boolean load = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load songs if not loaded before
        if (load){
            loadSongs();
            load = false;
        }

        // create and connect recycler view
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(Song.list, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        // Search bar
        EditText editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // after the text is changing call filter, a function found below
                filter(s.toString());
            }
        });

    }
    private void filter(String text) {
        ArrayList<Song> filteredList = new ArrayList<>(); // create a filtered arraylist

        for (Song item : Song.list) {
            if (item.getSongName().toLowerCase().contains(text.toLowerCase())) {
                //for each song in the arraylist find the songs that contain the text found in the eddit text
                filteredList.add(item);
                //add it to the array list
            }
        }
        adapter.filterList(filteredList); // call the function filterList which is a function found in recycler adapter
    }

    private void loadSongs(){
        Song staySchemin = new Song(R.raw.rickross,"Stay Schemin'","Stay Schemin'","Rick Ross", R.drawable.stayschemin);
        Song song1 = new Song(R.raw.theless,"The Less I Know The Better", "Currents","Tame Impala", R.drawable.currents);
        Song song2 = new Song(R.raw.blue, "Blue (da be dee)", "Europop", "Eiffel 65", R.drawable.blue);
        Song song3 = new Song(R.raw.despacito, "Despacito", "Vida", "Luis Fonsi", R.drawable.despacito);
        Song song4 = new Song(R.raw.uptown, "Uptown Funk", "Uptown Special", "Mark Ronson", R.drawable.uptown);
        Song song5 = new Song(R.raw.shallow, "Shallow", "A Star was Born", "Lady Gaga, Bradley Cooper", R.drawable.shallow);
        Song song6 = new Song(R.raw.roar, "Roar", "Prism", "Katy Perry", R.drawable.roar);
        Song kayne1 = new Song(R.raw.closed_on_sunday,"Closed on Sunday","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne2 = new Song(R.raw.every_hour,"Every Hour","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne3 = new Song(R.raw.everything_we_need,"Everything We Need","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne4 = new Song(R.raw.follow_god,"Follow God","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne5 = new Song(R.raw.god_is,"God Is","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne6 = new Song(R.raw.hands_on,"Hands On","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne7 = new Song(R.raw.on_god,"On God","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne8 = new Song(R.raw.selah,"Selah","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne9 = new Song(R.raw.use_this_gospel,"Use This Gospel","Jesus is King","Kayne West",R.drawable.jesus_is_king);
        Song kayne10 = new Song(R.raw.water,"Water","Jesus is King","Kayne West",R.drawable.jesus_is_king);
    }

}
