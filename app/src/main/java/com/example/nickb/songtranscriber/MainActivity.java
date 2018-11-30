package com.example.nickb.songtranscriber;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<SongInfo> listOfSongs; // am i supposed to do this

    private final static int RESULT_ONE = 1;
    private final static int RESULT_TWO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfSongs = new ArrayList<>();
    }

    public void onGetNewSongInfo(View view) {
        Intent getNewSongInfo = new Intent(this, NewSongMenu.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SongInfo newSubmission = (SongInfo) data.getSerializableExtra("songSubmission");

        listOfSongs.add(newSubmission);
    }
}
