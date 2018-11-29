package com.example.nickb.songtranscriber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGetNewSongInfo(View view) {
        Intent getNewSongInfo = new Intent(this, NewSongMenu.class);
        startActivity(getNewSongInfo); //mb store a list of SongObjects here
    }
}
