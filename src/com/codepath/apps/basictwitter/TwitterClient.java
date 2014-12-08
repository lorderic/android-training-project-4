package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "91wTqoQ4uCRJkGYyWJrxpT2Wt";       // Change this
	public static final String REST_CONSUMER_SECRET = "yObZPMopBhHm2TwyjjnKr2fEJFuJlm7pATnphPo1wJTFfTcX9B"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// DEFINE METHODS for different API endpoints here
	public void getHomeTimeline(AsyncHttpResponseHandler handler, long maxID) {
		String apiURL = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("max_id", String.valueOf(maxID));
		params.put("count", "25");
		client.get(apiURL, params, handler);
	}
	
	public void getMentionsTimeline(AsyncHttpResponseHandler handler, long maxID){
		String apiURL = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("max_id", String.valueOf(maxID));
		params.put("count", "25");
		client.get(apiURL, params, handler);
	}
	
	public void composeTweet(AsyncHttpResponseHandler handler, String content){
		String apiURL = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", content);
		client.post(apiURL, params, handler);
	}
	
	public void getMyInfo(AsyncHttpResponseHandler handler){
		String apiURL = getApiUrl("account/verify_credentials.json");
		client.get(apiURL, null, handler);
	}
	
	public void getUserTimeline(AsyncHttpResponseHandler handler, long userID){
		String apiURL = getApiUrl("statuses/user_timeline.json");
		if (userID != -1) {
			RequestParams params = new RequestParams();
			params.put("user_id", String.valueOf(userID));
			client.get(apiURL, params, handler);
		}
		else {
			client.get(apiURL, null, handler);
		}
	}
	
	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}