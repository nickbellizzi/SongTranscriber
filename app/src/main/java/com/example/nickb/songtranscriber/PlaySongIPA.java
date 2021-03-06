package com.example.nickb.songtranscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class PlaySongIPA extends Activity {

    private SongInfo songInfo;
    private String[] lyricsIPA;
    private int lineNumber;
    private long[] timings;
    private TextView lineTV;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_ipa_song_layout);
        Intent passedObject = getIntent();
        songInfo = (SongInfo) passedObject.getSerializableExtra("songToPlay");
        lyricsIPA = songInfo.getIPA();
        timings = songInfo.getTimes();
        lineNumber = 0;
        lineTV = (TextView) findViewById(R.id.ipa_line_text_display);
        startBtn = (Button) findViewById(R.id.start_playback_button);
    }

    public void onStartSong(View view) {
        startBtn.setVisibility(View.INVISIBLE);
        lineTV.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                lineTV.setText(lyricsIPA[lineNumber]);
                if (lineNumber < lyricsIPA.length) {
                    handler.postDelayed(this, timings[lineNumber]);
                } else {
                    lineNumber = 0;
                    finish();
                }
                lineNumber++;
            }
        });
    }
}
