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
    private Map<String, String> previous;

    /*private RequestQueue requestQueue;
    private final String apiURL = "https://wordsapiv1.p.rapidapi.com/words/";
    private JSONObject jsonObject;*/

    SongInfo(String title, String artist, String[] lyrics, String[] conversions) {
        this.title = title;
        this.artist = artist;
        this.lines = lyrics;
        this.ipaTranscriptions = conversions;
        System.out.println("IPA Transcriptions: " + Arrays.toString(ipaTranscriptions));
    }

    /*private void convertLines() {
        ipaTranscriptions = new String[]{
                "aɪm ˈlʊkɪŋ ˈoʊvər ə fɔr-lif ˈkloʊvər",
                "aɪ ˈoʊvərˌlʊkt bɪˈfɔr",
                "wʌn lif ɪz ˈsʌnˌʃaɪn, ðə ˈsɛkənd ɪz reɪn",
                "θɜrd ɪz ðə ˈroʊzɪz ðæt groʊ ɪn ðə leɪn"
        };
        System.out.println("in convert lines");
        for (int i = 0; i < lines.length; i++) {
            ipaTranscriptions[i] = convertLine(lines[i]);
        }
    }

    private String convertLine(String line) {
        System.out.println("in convert line");
        String[] words = line.trim().split(" ");
        String transcribedLine = "";
        for (String word : words) {
            transcribedLine += convertWord(word) + " ";
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
            startAPICall(simple);
            System.out.println("api call finished");
            if (jsonObject != null) {
                try {
                    System.out.println("successful api response?");
                    return jsonObject.getJSONObject("pronunciation").getString("all");
                } catch (JSONException e) {
                    System.out.println("got word, didn't parse correctly");
                    return "ERROR PARSING";
                } catch (NullPointerException e) {
                    System.out.println("json null!");
                    return "json null!";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "other error";
        } catch (Exception e) {
            System.out.println("exception" + e.toString());
            e.printStackTrace();
            return word;
        }
    }

    private void startAPICall(final String simple) {
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
