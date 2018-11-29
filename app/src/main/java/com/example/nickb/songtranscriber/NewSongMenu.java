package com.example.nickb.songtranscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class NewSongMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_song_layout);
        Intent menuIntent = getIntent();
    }
}
