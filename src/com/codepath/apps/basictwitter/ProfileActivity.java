package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity {
	
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = (User) getIntent().getSerializableExtra("user");
		if (user != null) {
			populateProfileHeader();
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			UserTimelineFragment fragment = UserTimelineFragment.newInstance(user.getUid());
			ft.replace(R.id.flContainer2, fragment);
			ft.commit();
		}
		else {
			loadProfileInfo();
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			UserTimelineFragment fragment = new UserTimelineFragment();
			ft.replace(R.id.flContainer2, fragment);
			ft.commit();
		}
	}

	private void loadProfileInfo(){
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJSON(json);
				populateProfileHeader();
			}
		});
	}
	
	private void populateProfileHeader(){
		getActionBar().setTitle("@" + user.getScreenName());
		
		TextView tvName = (TextView) findViewById(R.id.tvUserName2);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage2);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowers() + " Followers");
		tvFollowing.setText(user.getFollowing() + " Following");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(user.getProfileImageURL(), ivProfileImage);
	}
}
