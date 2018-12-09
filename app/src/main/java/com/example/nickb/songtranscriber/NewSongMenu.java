package com.example.nickb.songtranscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

public class NewSongMenu extends Activity {

    public static Map<String, String> previous;
    private boolean callError;
    private RequestQueue requestQueue;
    private final String apiURL = "https://wordsapiv1.p.rapidapi.com/words/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_song_layout);
        requestQueue = Volley.newRequestQueue(this);
        loadPrevious();
    }

    private void loadPrevious() {
        previous = new HashMap<>();
        previous.put("isn't", "ˈɪzənt");
        previous.put("come", "kʌm");
        previous.put("jingling", "ˈʤɪŋgəlɪŋ");
        //add more common words later
    }

    public void onSubmitSongInformation(View view) {

        EditText titleText = (EditText) findViewById(R.id.input_song_title);
        String songTitle = String.valueOf(titleText.getText());
        EditText artistText = (EditText) findViewById(R.id.input_artist);
        String artistName = String.valueOf(artistText.getText());
        EditText lyricsText = (EditText) findViewById(R.id.input_lyrics);
        String songLyrics = String.valueOf(lyricsText.getText());
        String[] lines = songLyrics.split("\\r?\\n");
        //String[] conversions = convertLines(lines);
        loadConversions(lines);
        System.out.println("converted lines??");
        SongInfo newSong = new SongInfo(songTitle, artistName, lines, previous);
        System.out.println("sos " + newSong.toString());
        // handle errors
        Intent returnToMenu = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("songSubmission", newSong);
        returnToMenu.putExtras(bundle);
        setResult(RESULT_OK, returnToMenu);
        finish();
    }

    private void loadConversions(String[] lines) {
        for (String line : lines) {
            convertLine(line.toLowerCase());
        }
    }

    private void convertLine(String line) {
        String[] words = line.trim().split(" ");
        for (String word : words) {
            if (!previous.containsKey(word)) {
                startAPICall(word);
            }
        }
    }

    /*private String[] convertLines(String[] lines) {
        System.out.println("in convert lines");
        String[] ipaTranscriptions = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            ipaTranscriptions[i] = convertLine(lines[i].toLowerCase());
        }
        return ipaTranscriptions;
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

    private synchronized String convertWord(String word) {
        if (previous.containsKey(word)) {
            return previous.get(word);
        }
        System.out.println("in convert word");
        callError = false;
        //preprocess word??
        startAPICall(word);
        try {
            /*while (!previous.containsKey(word)) {
                wait();
            }//
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Interruption");
        }
        //while (!previous.containsKey(word) && !callError) {}
        /*for (int i = 0; i < 900; i++) {
            if (previous.containsKey(word)) {
                return previous.get(word);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception");
            }
        }//
        if (previous.containsKey(word)) {
            return previous.get(word);
        }
        System.out.println("BACK TO CONVERT WORDS");
        return "ERROR";
    }*/

    private void startAPICall(final String word) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    apiURL + word,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(word);
                            processResponse(response, word);
                            System.out.println("got json response!");
                            System.out.println(word + " : " + previous.get(word));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("api error! " + error.toString());
                            callError = true;
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

    private void processResponse(JSONObject response, String word) {
        try {
            System.out.println("successful api response?");
            String ipaWord = response.getJSONObject("pronunciation").getString("all");
            previous.put(word, ipaWord);
        } catch (JSONException e1) {
            try {
                System.out.println("(only one entry for pronunciation)");
                String ipaWord = response.getString("pronunciation");
                previous.put(word, ipaWord);
            } catch (JSONException e2) {
                previous.put(word, "ERROR PARSING");
                System.out.println("got word, didn't parse correctly");
            }
        }
    }
}
