package com.example.hikaru.cardcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    Button hide;
    Button show;
    Button get_card_id;
    TextView cardCount;
    EditText etGitHubUser; // This will be a reference to our GitHub username input.
    Button btnGetRepos;  // This is a reference to the "Get Repos" button.
    TextView tvRepoList;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.    RequestQueue requestQueue;
    String baseUrl = "https://github.com/crobertsbmw/deckofcards.git";
    String url_shuffle = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
    String currentDeckId;
    ImageView cardImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        this.etGitHubUser = (EditText) findViewById(R.id.et_github_user);  // Link our github user text box.
//        this.btnGetRepos = (Button) findViewById(R.id.btn_get_repos);  // Link our clicky button.
//        this.tvRepoList = (TextView) findViewById(R.id.tv_repo_list);  // Link our repository list text output box.
        this.tvRepoList.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        get_card_id = findViewById(R.id.get_card_id);
        hide = (Button) findViewById(R.id.hide);
        show = (Button) findViewById(R.id.show);
        cardCount = (TextView) findViewById(R.id.Count);
        get_card_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleDeck();
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardCount.setVisibility(View.INVISIBLE);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardCount.setVisibility(View.VISIBLE);
            }
        });
    }
    private void clearRepoList() {
        // This will clear the repo list (set it as a blank string).
        this.tvRepoList.setText("");
    }

    private void addToRepoList(String repoName, String lastUpdated) {
        // This will add a new repo to our list.
        // It combines the repoName and lastUpdated strings together.
        // And then adds them followed by a new line (\n\n make two new lines).
        String strRow = repoName + " / " + lastUpdated;
        String currentText = tvRepoList.getText().toString();
        this.tvRepoList.setText(currentText + "\n\n" + strRow);
    }
    private void setRepoListText(String str) {
        // This is used for setting the text of our repo list box to a specific string.
        // We will use this to write a "No repos found" message if the user doens't have any.
        this.tvRepoList.setText(str);
    }
    private void shuffleDeck() {
   // To fully understand this, I'd recommend reading the office docs: https://developer.android.com/training/volley/index.html
        JsonObjectRequest arrReq = new JsonObjectRequest(
                Request.Method.GET,
                url_shuffle,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentDeckId = response.getString("deck_id")
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }
    private void drawACard(final int totalCount) {
        String url_drawCard = "https://deckofcardsapi.com/api/deck/" + currentDeckId + "/draw/?count=2";
        private String getValue;
        JsonObjectRequest arrReq = new JsonObjectRequest(
                Request.Method.GET,
                url_drawCard,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            cardImage.setImageAlpha(response.getString("image"));
                            getValue = response.getString("value");
                            if (getValue == "KING" || getValue == "QUEEN" || getValue == "JACK" || getValue == "10" || getValue == "ACE") {
                                totalCount--;
                            }
                            if (getValue == "2" || getValue == "3" || getValue == "4" || getValue == "5" || getValue == "6") {
                                totalCount++;
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }
    public void getReposClicked(View v) {
        // Clear the repo list (so we have a fresh screen to add to)
        clearRepoList();
        // Call our getRepoList() function that is defined above and pass in the
        // text which has been entered into the etGitHubUser text input field.
//        getRepoList(etGitHubUser.getText().toString());
    }
}


