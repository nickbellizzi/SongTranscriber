package com.example.nickb.songtranscriber;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public class SongInfo implements Serializable {
    private String title;
    private String artist;
    private String[] lines;
    private String[] ipaTranscriptions;
    private long[] lineTimings;
    private Map<String, String> convertedWords;

    SongInfo(String title, String artist, String[] lyrics, Map<String, String> previous) {
        this.title = title;
        this.artist = artist;
        this.lines = lyrics;
        this.convertedWords = previous;
        ipaTranscriptions = new String[lines.length];
        //this.ipaTranscriptions = conversions;
    }

    public void convertLines() {
        /*ipaTranscriptions = new String[]{
                "aɪm ˈlʊkɪŋ ˈoʊvər ə fɔr-lif ˈkloʊvər",
                "aɪ ˈoʊvərˌlʊkt bɪˈfɔr",
                "wʌn lif ɪz ˈsʌnˌʃaɪn, ðə ˈsɛkənd ɪz reɪn",
                "θɜrd ɪz ðə ˈroʊzɪz ðæt groʊ ɪn ðə leɪn"
        };*/
        convertedWords = NewSongMenu.previous;
        for (String key : convertedWords.keySet()) {
            System.out.println(key + " is " + convertedWords.get(key));
        }
        System.out.println("in convert lines");
        for (int i = 0; i < lines.length; i++) {
            ipaTranscriptions[i] = convertLine(lines[i].toLowerCase());
        }
        System.out.println("IPA Transcriptions: " + Arrays.toString(ipaTranscriptions));
    }

    private String convertLine(String line) {
        System.out.println("in convert line");
        String[] words = line.trim().split(" ");
        String transcribedLine = "";
        for (String word : words) {
            transcribedLine += convertWord(word) + " ";
        }
        System.out.println("finished convertLine");
        return transcribedLine.trim();
    }

    private String convertWord(String word) {
        System.out.println("in convert word, looking for " + word);
        if (convertedWords.containsKey(word)) {
            return convertedWords.get(word);
        }
        return word;
        //preprocess words later
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
