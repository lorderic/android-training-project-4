package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			long userID = bundle.getLong("userID", -1);
			populateTimeline(userID);
		}
		else 
			populateTimeline(-1);
	}
	
	public static UserTimelineFragment newInstance(long userID){
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle bundle = new Bundle();
		bundle.putLong("userID", userID);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void populateTimeline(long userID) {
		super.populateTimeline(userID);
		client.getUserTimeline(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
			}
		}, userID);
	}
}
