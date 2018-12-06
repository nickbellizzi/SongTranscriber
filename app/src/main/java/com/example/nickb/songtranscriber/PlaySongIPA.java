package com.example.nickb.songtranscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        System.out.println("got here");
        final Handler handler = new Handler();
        while (lineNumber < lyricsIPA.length) {
            lineTV.setText(lyricsIPA[lineNumber]);
            System.out.println("entered loop");
            final long currTime = timings[lineNumber];
            System.out.println("in loop");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.out.println("in loop");
                    System.out.println(currTime);
                    lineNumber++;
                }
            }, currTime);
        }
        lineNumber = 0;
        finish();
    }
}
