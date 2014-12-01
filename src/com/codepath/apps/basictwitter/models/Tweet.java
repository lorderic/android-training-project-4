package com.codepath.apps.basictwitter.models;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.text.style.SuperscriptSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.basictwitter.TimelineActivity;

public class Tweet {
	
	private String body;
	private long uid;
	private String createdAt;
	private User user;

	public static Tweet fromJSON(JSONObject json) {
		Tweet tweet = new Tweet();
		
		try {
			tweet.body = json.getString("text");
			tweet.uid = json.getLong("id");
			
			// parse timestamp and store it to createAt
			tweet.createdAt = json.getString("created_at");
			/*
			tweet.createdAt = DateUtils.getRelativeDateTimeString(TimelineActivity., parseTwitterDate(timeString).getTime(), 
					DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);
			*/
			tweet.user = User.fromJSON(json.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return tweet;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		
		for (int i = 0; i < json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJSON(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		
		return tweets;
	}
	
	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}
	
	public String toString() {
		return getBody() + " - " + getUser().getScreenName();
	}

}
