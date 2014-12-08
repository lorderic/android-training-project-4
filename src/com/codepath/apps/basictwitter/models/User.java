package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	private String name;
	private long uid;
	private String screenName;
	private String profileImageURL;
	private int followers;
	private int following;
	private String tagline;
	
	public static User fromJSON(JSONObject json) {
		User user = new User();
		try {
			user.name = json.getString("name");
			user.uid = json.getLong("id");
			user.screenName = json.getString("screen_name");
			user.profileImageURL = json.getString("profile_image_url");
			user.followers = json.getInt("followers_count");
			user.following = json.getInt("friends_count");
			user.tagline = json.getString("description");
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}
	
	public int getFollowers() {
		return followers;
	}

	public int getFollowing() {
		return following;
	}

	public String getTagline() {
		return tagline;
	}

}
