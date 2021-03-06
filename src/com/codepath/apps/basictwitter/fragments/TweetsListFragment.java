package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TweetsListFragment extends Fragment {
	
	protected TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private SwipeRefreshLayout swipeContainer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Non-view initialization
		client = TwitterApplication.getRestClient();
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// Assign our view references
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ProfileActivity.class);
				User user = tweets.get(position).getUser();
				intent.putExtra("user", user);
				startActivity(intent);
			}
			
		});
		
		lvTweets.setAdapter(aTweets);
		setEndlessScrollListener();
		/*
		String content = getArguments().getString("content");
		if (content != null) {
			client.composeTweet(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray json) {
					refreshTimeline();
					//tweets.addAll(0, Tweet.fromJSONArray(json));
					//aTweets.notifyDataSetChanged();
					//lvTweets.smoothScrollToPosition(0);
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("DEBUG", e.toString());
				}
			}, content);
		}
		*/
		swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            	refreshTimeline();
            } 
        });

		return v;
	}
	
	public void addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}
	
	protected void refreshTimeline(){
		tweets.clear();
		aTweets.notifyDataSetChanged();
		lvTweets.smoothScrollToPosition(0);
		populateTimeline(Long.MAX_VALUE);
    }
	
	protected void setEndlessScrollListener(){
		lvTweets.setOnScrollListener(new EndlessScrollListener(lvTweets) {
			
			@Override
			public void onLoadMore(long maxID) {
				populateTimeline(maxID);
			}
		});
    }
	
	protected void populateTimeline(long maxID) {
		// Need to be override
	}

}
