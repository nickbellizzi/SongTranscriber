package com.example.nickb.songtranscriber;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<SongInfo> listOfSongs; // idk

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
        startActivityForResult(getNewSongInfo, RESULT_ONE);
    }

    public void onPlaySongMenu(View view) {
        Intent openPlayMenu = new Intent(this, PlaySongMenu.class);
        startActivity(openPlayMenu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_ONE && resultCode == RESULT_OK) {
            SongInfo newSubmission = (SongInfo) data.getSerializableExtra("songSubmission");
            System.out.println("HELP ME " + newSubmission.toString() + " lines : " + newSubmission.getLines().length);
            listOfSongs.add(newSubmission);
            /*Intent getTimings = new Intent(this, NewSongMenu.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("songForTiming", newSubmission);
            getTimings.putExtras(bundle);
            startActivity(getTimings);*/
        }
    }
}
