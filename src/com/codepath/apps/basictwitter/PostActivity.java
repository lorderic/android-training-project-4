package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class PostActivity extends Activity {
	
	private EditText etCompose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
	}
	
	public void composeTweet(View v){
		etCompose = (EditText) findViewById(R.id.etCompose);
		String content = etCompose.getText().toString();
		
		Intent intent = new Intent();
		intent.putExtra("content", content);
		//intent.putExtra("filters", filters);
		setResult(RESULT_OK, intent);
		
		finish();
	}
	
	public void cancel(View v){
		setResult(RESULT_CANCELED);
		
		finish();
	}
}
