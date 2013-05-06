package com.example.uzuswipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemActivity extends FragmentActivity {

	String subjectTitle;
	String subjectContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		
		TextView subject = (TextView)findViewById(R.id.textView2);
		TextView content = (TextView)findViewById(R.id.textView3);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    subjectTitle = extras.getString("subject");
		    subjectContent = extras.getString("content");
		}
		
		subject.setText(subjectTitle);
		content.setText(subjectContent);
	}
	

}
