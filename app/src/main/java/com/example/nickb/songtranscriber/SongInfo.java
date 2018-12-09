package com.example.nickb.songtranscriber;

import android.content.Context;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SongInfo implements Serializable {
    private String title;
    private String artist;
    private String[] lines;
    private String[] ipaTranscriptions;
    private long[] lineTimings;
    private Map<String, String> convertedWords;

    /*private RequestQueue requestQueue;
    private final String apiURL = "https://wordsapiv1.p.rapidapi.com/words/";
    private JSONObject jsonObject;*/

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
        //String simple = word; // preprocessed word?
    }

    /*private void startAPICall(final String simple) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    apiURL + simple,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(simple);
                            System.out.println("got json response!");
                            jsonObject = response;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("api error! " + error.toString());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<>();
                    params.put("X-RapidAPI-Key", "e578e68f77msheb452bc0de6aa22p186f2cjsn504e4028958e");
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPrevious() {
        previous = new HashMap<>();
        previous.put("isn't", "ˈɪzənt"); //do later
    }*/

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
