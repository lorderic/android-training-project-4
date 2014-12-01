package com.codepath.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		
		View view;
		
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			view = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) view.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		TextView tvTimeStamp = (TextView) view.findViewById(R.id.tvTimestamp);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageURL(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		
		String timeString = (String) DateUtils.getRelativeDateTimeString(parent.getContext(), parseTwitterDate(tweet.getCreatedAt()).getTime(), 
				DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);
		
		tvTimeStamp.setText(timeString);
		
		return view;
	}
	
	private Date parseTwitterDate(String dateString) {
		final String TWITTER_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		Date date;
		try{
			SimpleDateFormat sf = new SimpleDateFormat(TWITTER_FORMAT);
			date = sf.parse(dateString);
		} catch (ParseException e){
			e.printStackTrace();
			date = null;
		}
		return date;
	}

}
