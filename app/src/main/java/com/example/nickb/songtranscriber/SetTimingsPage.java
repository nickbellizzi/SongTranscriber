package com.example.nickb.songtranscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SetTimingsPage extends Activity {

    private SongInfo songToSet;
    private String[] lines;
    private long start, end;
    private int lineNumber;
    private List<Long> timeDifferences = new ArrayList<>();
    private TextView instructionsTV, lineTV;
    private Button startBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timings_layout);
        Intent passedObject = getIntent();
        songToSet = (SongInfo) passedObject.getSerializableExtra("songForTiming");
        lines = songToSet.getLines();
        lineNumber = 0;
        instructionsTV = (TextView) findViewById(R.id.instructions_text);
        lineTV = (TextView) findViewById(R.id.line_text_display);
        startBtn = (Button) findViewById(R.id.start_timings_button);
        nextBtn = (Button) findViewById(R.id.next_button);
    }

    public void onStartTimings(View view) {
        instructionsTV.setVisibility(View.INVISIBLE);
        startBtn.setVisibility(View.INVISIBLE);
        lineTV.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.VISIBLE);
        lineTV.setText(lines[lineNumber]);
        lineNumber++;
        start = System.currentTimeMillis();
    }

    public void onNextLine(View view) {
        end = System.currentTimeMillis() - start;
        timeDifferences.add(end);
        if (lineNumber < lines.length) {
            lineTV.setText(lines[lineNumber]);
            lineNumber++;
            start = System.currentTimeMillis();
        } else {
            lineNumber = 0;
            songToSet.setTimings(timeDifferences);
            String displayTimings = "";
            for (long time : timeDifferences) {
                displayTimings += ((double)time / 1000) + ", ";
            }
            System.out.println(displayTimings);
            finish();
        }
    }
}
