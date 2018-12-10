package com.example.nickb.songtranscriber;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** List for multiple songs; currently only one used. */
    private List<SongInfo> listOfSongs;

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
        //Create play menu of multiple songs using list later
        /*Intent openPlayMenu = new Intent(this, PlaySongMenu.class);
        pass song object songToPlay to PlaySongIPA
        startActivity(openPlayMenu);*/
        Intent playSong = new Intent(this, PlaySongIPA.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("songToPlay", listOfSongs.get(0));
        playSong.putExtras(bundle);
        startActivity(playSong);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_ONE && resultCode == RESULT_OK) {
            SongInfo newSubmission = (SongInfo) data.getSerializableExtra("songSubmission");
            System.out.println("New Song : " + newSubmission.toString() + "; lines : " + newSubmission.getLines().length);
            listOfSongs.add(newSubmission);
            Intent getTimings = new Intent(this, SetTimingsPage.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("songForTiming", newSubmission);
            getTimings.putExtras(bundle);
            startActivityForResult(getTimings, RESULT_TWO);
        }

        if (requestCode == RESULT_TWO && resultCode == RESULT_OK) {
            System.out.println("back from set timings");
            long[] lineTimes = data.getLongArrayExtra("timingsArray");
            SongInfo songWithNewInfo = listOfSongs.get(listOfSongs.size() - 1);
            songWithNewInfo.setTimings(lineTimes);
            songWithNewInfo.convertLines();
        }
    }
}
