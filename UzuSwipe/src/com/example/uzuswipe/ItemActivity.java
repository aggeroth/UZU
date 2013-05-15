package com.example.uzuswipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class ItemActivity extends FragmentActivity {
	MyPageAdapter pageAdapter;
	
	String subjectTitle;
	String subjectContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_views);
		
		//Grabs the position of the page from the previous page
		int prePosition = getIntent().getIntExtra("position", 0); //The Zero is just a random default number
		
		//Creates an array of fragments from CollectionFragment
		UzuFragment[] fragments = CollectionFragment.uzuFragments.toArray(new UzuFragment[0]);
        
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
        pager.setCurrentItem(prePosition);
        
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
