package com.example.nickb.songtranscriber;

import java.io.Serializable;
import java.util.List;

public class SongInfo implements Serializable {
    private String title;
    private String artist;
    private String lyrics;
    private String[] lines;
    private String[] ipaTranscriptions;
    private double[] lineTimings;

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
        //convert to array
        //yeah that's basically it
    }

    @Override
    public String toString() {
        return title + " — " + artist;
    }
}
