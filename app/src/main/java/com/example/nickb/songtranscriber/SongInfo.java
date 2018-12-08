package com.example.nickb.songtranscriber;

import android.content.Context;
import android.view.textclassifier.TextLinks;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SongInfo implements Serializable {
    private String title;
    private String artist;
    private String lyrics;
    private String[] lines;
    private String[] ipaTranscriptions;
    private long[] lineTimings;
    private Map<String, String> previous;

    private RequestQueue requestQueue;
    private final String apiURL = "https://wordsapiv1.p.rapidapi.com/words/";

    SongInfo(String title, String artist, String lyrics, Context ctx) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        lines = lyrics.split("\\r?\\n");
        requestQueue = Volley.newRequestQueue(ctx);
        loadPrevious();
        convertLines();
        System.out.println(Arrays.toString(ipaTranscriptions));
    }

    private void convertLines() {
        /*ipaTranscriptions = new String[]{
                "aɪm ˈlʊkɪŋ ˈoʊvər ə fɔr-lif ˈkloʊvər",
                "aɪ ˈoʊvərˌlʊkt bɪˈfɔr",
                "wʌn lif ɪz ˈsʌnˌʃaɪn, ðə ˈsɛkənd ɪz reɪn",
                "θɜrd ɪz ðə ˈroʊzɪz ðæt groʊ ɪn ðə leɪn"
        };*/
        System.out.println("in convert lines");
        for (int i = 0; i < lines.length; i++) {
            ipaTranscriptions[i] = convertLine(lines[i]);
        }
    }

    private String convertLine(String line) {
        System.out.println("in convert line");
        String[] words = line.trim().split(" ");
        String transcribedLine = "";
        for (int i = 0; i < words.length; i++) {
            transcribedLine += convertWord(words[i]) + " ";
        }
        return transcribedLine.trim();
    }

    private String convertWord(String word) {
        System.out.println("in convert word");
        word = word.toLowerCase();
        if (previous.containsKey(word)) {
            return previous.get(word);
        }
        String simple = word; // preprocessed word?
        try {
            String apiResult = startAPICall(simple);
            System.out.println("api call");
            if (!apiResult.equals("ERROR")) {
                return apiResult;
            }
            return word;
        } catch (Exception e) {
            System.out.println("exception");
            return word;
        }
    }

    private String startAPICall(String simple) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    apiURL + simple + "/pronunciation",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String wordResult = response.getJSONObject("pronunciation").getString("all");
                            } catch (JSONException e) {
                                System.out.println("got word, didn't parse correctly");
                            }
                            requestQueue.stop();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("api error! " + error.toString());
                            requestQueue.stop();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("X-RapidAPI-Key", "apiKey");
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
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
