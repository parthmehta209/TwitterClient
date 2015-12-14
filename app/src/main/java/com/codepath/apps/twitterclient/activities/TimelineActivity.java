package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TwitterApp;
import com.codepath.apps.twitterclient.adapters.TweetArrayAdapter;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.net.TwitterRestClient;
import com.codepath.apps.twitterclient.utils.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private static final String TAG = TimelineActivity.class.getSimpleName();
    private TwitterRestClient twitterClient;
    private ListView lvTweets;
    private TweetArrayAdapter tweetArrayAdapter;
    private ArrayList<Tweet> tweets;
    private JsonHttpResponseHandler responseHandler;
    static final int NEW_TWEET_REQ_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        twitterClient = TwitterApp.getRestClient();
        lvTweets = (ListView)findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        tweetArrayAdapter = new TweetArrayAdapter(this,tweets);
        lvTweets.setAdapter(tweetArrayAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                getMoreItems();

                return true;
            }
        });
        responseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweetArrayAdapter.addAll(Tweet.fromJsonArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, errorResponse.toString());
            }
        };
        populateTimeline();
    }


    private void populateTimeline() {
        twitterClient.getHomeTimeline(responseHandler);
    }

    private void getMoreItems() {
        long maxId = tweetArrayAdapter.getItem(tweetArrayAdapter.getCount()-1).getUid();
        twitterClient.getHomeTimelineNew(responseHandler, maxId - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent i = new Intent(this,ComposeActivity.class);
                startActivityForResult(i, NEW_TWEET_REQ_CODE);
                break;
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        JSONObject tweetJson;
        if (requestCode == NEW_TWEET_REQ_CODE) {

            if (resultCode == RESULT_OK) {
                try {
                     tweetJson = new JSONObject(data.getStringExtra("result"));
                } catch (JSONException e) {
                    Log.e(TAG,"" + e);
                    return;
                }

                Tweet tweet = Tweet.getTweet(tweetJson);
                tweetArrayAdapter.insert(tweet,0);
            }
        }
    }
}
