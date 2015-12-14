package com.codepath.apps.twitterclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TwitterApp;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bPostTweet;
    Button bCancelTweet;
    EditText etTweetText;
    JsonHttpResponseHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etTweetText = (EditText)findViewById(R.id.etTweetText);
        bPostTweet = (Button)findViewById(R.id.bPostTweet);
        bCancelTweet = (Button)findViewById(R.id.bCancelTweet);
        bCancelTweet.setOnClickListener(this);

        handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(ComposeActivity.this,"Tweet posted",Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",response.toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ComposeActivity.this,"Could not post tweet",Toast.LENGTH_LONG).show();
            }
        };
        bPostTweet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bCancelTweet:
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
                break;
            case R.id.bPostTweet:
                String tweetBody = etTweetText.getText().toString();
                TwitterApp.getRestClient().postTweet(handler, tweetBody);
                break;

        }

    }
}
