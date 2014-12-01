package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.basictwitter.models.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TimelineActivity extends Activity {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private SwipeRefreshLayout swipeContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		populateTimeline(Long.MAX_VALUE);
		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		setEndlessScrollListener();
		
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            	refreshTimeline();
            } 
        });
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.miCompose) {
            Intent intent = new Intent(this, PostActivity.class);
        	startActivityForResult(intent, 200);
            
            return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if (requestCode == 200){
    		if (resultCode == RESULT_OK){
    			String content = data.getStringExtra("content");
    			
    			if (content != null) {
	    			client.composeTweet(new JsonHttpResponseHandler() {
	    				@Override
	    				public void onSuccess(JSONArray json) {
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
    		}
    		
    		else if (resultCode == RESULT_CANCELED) {
    			// do nothing
    		}
    		
    		refreshTimeline();
    	}
    }
	
    private void refreshTimeline(){
		tweets.clear();
		aTweets.notifyDataSetChanged();
		lvTweets.smoothScrollToPosition(0);
		populateTimeline(Long.MAX_VALUE);
    }
	
	public void populateTimeline(long maxID) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
			}
		}, maxID-1);
	}
	
	private void setEndlessScrollListener(){
		lvTweets.setOnScrollListener(new EndlessScrollListener(lvTweets) {
			
			@Override
			public void onLoadMore(long maxID) {
				populateTimeline(maxID);
			}
		});
    	
    }
}
