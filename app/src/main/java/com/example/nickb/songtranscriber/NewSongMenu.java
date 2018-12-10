package com.example.nickb.songtranscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewSongMenu extends Activity {

    public static Map<String, String> previous;
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
        previous.put("bells", "bɛlz");
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
                previous.put(word, word);
                System.out.println("got word, didn't parse correctly");
            }
        }
    }
}
