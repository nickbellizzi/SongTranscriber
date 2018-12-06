package com.example.nickb.songtranscriber;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongInfo implements Serializable {
    private String title;
    private String artist;
    private String lyrics;
    private String[] lines;
    private String[] ipaTranscriptions;
    private long[] lineTimings;
    private Map<String, String> previous;

    SongInfo(String title, String artist, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        lines = lyrics.split("\\r?\\n");
        convertLines();
        loadPrevious();
    }

    private void convertLines() {
        ipaTranscriptions = new String[]{
                "aɪm ˈlʊkɪŋ ˈoʊvər ə fɔr-lif ˈkloʊvər",
                "aɪ ˈoʊvərˌlʊkt bɪˈfɔr",
                "wʌn lif ɪz ˈsʌnˌʃaɪn, ðə ˈsɛkənd ɪz reɪn",
                "θɜrd ɪz ðə ˈroʊzɪz ðæt groʊ ɪn ðə leɪn"
        };
    }

    private void loadPrevious() {
        previous = new HashMap<>();
        previous.put("isn't", "ˈɪzənt"); //do later
    }

    public String[] getLines() {
        return lines;
    }

    public void setTimings(long[] timingsArray) {
        lineTimings = timingsArray;
    }

    public String[] getIPA() {
        return ipaTranscriptions;
    }

    public long[] getTimes() {
        return lineTimings;
    }

    @Override
    public String toString() {
        return title + " — " + artist;
    }
}
