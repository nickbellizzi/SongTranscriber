package com.example.nickb.songtranscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewSongMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_song_layout);
    }

    public void onSubmitSongInformation(View view) {

        EditText titleText = (EditText) findViewById(R.id.input_song_title);
        String songTitle = String.valueOf(titleText.getText());
        EditText artistText = (EditText) findViewById(R.id.input_artist);
        String artistName = String.valueOf(artistText.getText());
        EditText lyricsText = (EditText) findViewById(R.id.input_lyrics);
        String songLyrics = String.valueOf(lyricsText.getText());
        SongInfo newSong = new SongInfo(songTitle, artistName, songLyrics, this);
        Log.i(NewSongMenu.class.getName(), "sos " + newSong.toString());
        // handle errors
        Intent returnToMenu = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("songSubmission", newSong);
        returnToMenu.putExtras(bundle);
        setResult(RESULT_OK, returnToMenu);
        finish();
    }
}
