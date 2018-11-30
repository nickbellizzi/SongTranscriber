package com.example.nickb.songtranscriber;

import java.io.Serializable;

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
    }

    @Override
    public String toString() {
        return title + " — " + artist;
    }
}
