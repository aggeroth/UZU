package com.example.uzuswipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.widget.Button;


public class ItemActivity extends FragmentActivity {
	MyPageAdapter pageAdapter;

	String subjectTitle;
	String subjectContent;
	Button buttonDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Activity activity = (Activity) this;
		final DatabaseHandler db = new DatabaseHandler(activity);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_views);
		// Grabs the position of the page from the previous page
		final int prePosition = getIntent().getIntExtra("position", 0);
		
		// Creates an array of fragments from CollectionFragment
		final UzuFragment[] fragments = CollectionFragment.getUzuFragements();
		
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(pageAdapter);
		
				
		// Set buttonDrop
		buttonDelete = (Button) findViewById(R.id.button1);
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				db.deleteUzu(fragments[prePosition]);
			
			}
			
		});
		
		
	}

	private class MyPageAdapter extends FragmentPagerAdapter {
		private UzuFragment[] fragments;

		public MyPageAdapter(FragmentManager fm, UzuFragment[] fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments[position];
		}

		@Override
		public int getCount() {
			return this.fragments.length;
		}
	}

}
