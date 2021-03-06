package com.codepath.apps.twitterclient.net;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterRestClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "Mw3YnUtfLAmjv3ytlyChuAZjc";
	public static final String REST_CONSUMER_SECRET = "GMbqvoGndce7L9aCR1JmDF0jN78doIt1HzH8Zn5k7V6KKPda4N";
	public static final String REST_CALLBACK_URL = "oauth://pcmtweetclient"; // Change this (here and in manifest)

	private static final String HOME_TIMELINE_URL = "statuses/home_timeline.json";
	private static final String POST_URL = "statuses/update.json";

	public TwitterRestClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
	    String apiUrl = getApiUrl(HOME_TIMELINE_URL);
        RequestParams params = new RequestParams();
        params.put("count",25);
        params.put("since_id",1);
        client.get(apiUrl, params, handler);
	}

	public void getHomeTimelineNew(AsyncHttpResponseHandler handler,long maxId) {
		String apiUrl = getApiUrl(HOME_TIMELINE_URL);
		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("max_id",maxId);
		params.put("since_id",1);
		client.get(apiUrl,params,handler);
	}

	public void postTweet(AsyncHttpResponseHandler handler, String tweetBody) {

        String apiUrl = getApiUrl(POST_URL);
        RequestParams params = new RequestParams();
        params.put("status",tweetBody);
        client.post(apiUrl,params,handler);
	}
}