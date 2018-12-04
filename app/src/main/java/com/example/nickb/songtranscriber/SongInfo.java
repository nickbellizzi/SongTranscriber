package com.example.nickb.songtranscriber;

import java.io.Serializable;
import java.util.List;

public class SongInfo implements Serializable {
    private String title;
    private String artist;
    private String lyrics;
    private String[] lines;
    private String[] ipaTranscriptions;
    private long[] lineTimings;

    SongInfo(String title, String artist, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        lines = lyrics.split("\\r?\\n");
    }

    public String[] getLines() {
        return lines;
    }

    public void setTimings(List<Long> timingsList) {
        lineTimings = new long[timingsList.size()];
        int i = 0;
        for (Long time : timingsList) {
            lineTimings[i] = time;
            i++;
        }
    }

    @Override
    public String toString() {
        return title + " — " + artist;
    }
}
