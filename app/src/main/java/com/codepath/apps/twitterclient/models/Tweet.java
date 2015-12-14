package com.codepath.apps.twitterclient.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tweet {
    private static final String TAG = Tweet.class.getSimpleName();
    final String TIME_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy";



    // Define table fields
	protected long uid;
	protected String body;
	protected String createdAt;
	protected User user;


    public long getUid() {
        return uid;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
        long tweetTime;
        long nowTime = Calendar.getInstance().getTimeInMillis();
        try {
            tweetTime = sf.parse(createdAt).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        long diff = nowTime - tweetTime;
        long days = diff/ (24 * 60 * 60 * 1000);
        if(days > 0) {
            return Long.toString(days) + "D";
        }

        long hours = diff/(60 * 60 * 1000);
        if(hours > 0) {
            return Long.toString(hours) + "H";
         }

        long minutes = diff/(60 * 1000);

        return Long.toString(minutes) + "M";
    }

    public User getUser() {
        return user;
    }

	public static Tweet getTweet(JSONObject jsonTweet) {
		Tweet tweet = new Tweet();
		tweet.body = jsonTweet.optString("text");
		tweet.uid = jsonTweet.optLong("id", -1);
		tweet.createdAt = jsonTweet.optString("created_at");
        tweet.getCreatedAt();
        JSONObject jsonUser = jsonTweet.optJSONObject("user");
        if(jsonUser == null) {
            return null;
        }
		tweet.user = User.getUser(jsonUser);
		if(tweet.uid == -1 || tweet.body == null || tweet.user == null)
            return null;
		return tweet;
	}

    public static List<Tweet> fromJsonArray(JSONArray jsonTweetArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for(int i=0; i< jsonTweetArray.length(); i++) {
            try {
                Tweet tweet = Tweet.getTweet(jsonTweetArray.getJSONObject(i));
                if(tweet != null) {
                    tweets.add(tweet);
                } else {
                    Log.d(TAG, "The tweet is null at id:" + i);
                }
            } catch (JSONException e) {
                Log.e(TAG,"" + e);
            }

        }
        return tweets;
    }

}
