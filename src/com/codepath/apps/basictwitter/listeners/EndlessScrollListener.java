package com.codepath.apps.basictwitter.listeners;

import com.codepath.apps.basictwitter.models.Tweet;

import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	
	private ListView listView;
    private boolean isLoading;
	
	public EndlessScrollListener(ListView listView) {
        this.listView = listView;
        this.isLoading = false;
    }
	
	// FIXME: page number not using
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (listView.getLastVisiblePosition() + 1 >= totalItemCount && !isLoading
				&& listView.getLastVisiblePosition() > 0) {
			isLoading = true;
			Tweet tweet = (Tweet) listView.getAdapter().getItem(listView.getLastVisiblePosition());
			onLoadMore(tweet.getUid());
			//onLoadMore(pageNumber, totalItemCount);
		} 
		else if (listView.getLastVisiblePosition() + 1 < totalItemCount && isLoading){
			isLoading = false;
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Do nothing
	}
	
	public abstract void onLoadMore(long maxID);
	

}
