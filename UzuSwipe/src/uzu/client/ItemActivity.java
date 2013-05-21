package uzu.client;

import com.example.uzuswipe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * This class grabs the CollectionFragment class list and individually puts them 
 * in a fragment to swipe.
 * 
 * @author Merisha Shim
 *
 */

public class ItemActivity extends FragmentActivity {
	
	/** This is a MyPageAdapter that is used for swiping. */
	MyPageAdapter pageAdapter;

	/** This is a string that contains the subjectTitle. */
	String subjectTitle;
	
	/** This is a string that contains the subjectContent. */
	String subjectContent;
	
	/** This is a button for the buttonDelete. */
	Button buttonDelete;

	int poss;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Activity activity = (Activity) this;
		final DatabaseHandler db = new DatabaseHandler(activity);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_views);
		// Grabs the position of the page from the previous page
		poss = getIntent().getIntExtra("position", 0);
		
		// Creates an array of fragments from CollectionFragment
		final UzuFragment[] fragments = CollectionFragment.getUzuFragements();

		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(pageAdapter);
		pager.setCurrentItem(poss);
				
		// Set buttonDrop
		buttonDelete = (Button) findViewById(R.id.button1);
		buttonDelete.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View arg0) {
			
				//deletes the fragment
				db.deleteUzu(fragments[pager.getCurrentItem()]);


				pager.setCurrentItem(pager.getCurrentItem()+1);
				/*Intent intent = new Intent(activity, MainActivity.class);

				startActivity(intent); */		
				//goes back to the previous page 'CollectionFragment'
				//finish();
				
			}
			
		});
			
		
	}

	/**
	 * This is an inner class that allows the same layout to view different fragments.
	 * @author Merisha Shim
	 *
	 */
	private class MyPageAdapter extends FragmentPagerAdapter {
		/** An array of UzuFragments. */
		private UzuFragment[] fragments;

		/**
		 * Constructor that takes a FragmentManager, UzuFragment array.
		 * 
		 * @param fm
		 * @param fragments
		 */
		public MyPageAdapter(FragmentManager fm, UzuFragment[] fragments) {
			super(fm);
			this.fragments = fragments;
		}

		/**
		 * This function gets the item from the position of the fragment.
		 */
		@Override
		public Fragment getItem(int position) {
			return this.fragments[position];
		}
		/**
		 * This function returns the number of fragments.
		 */
		@Override
		public int getCount() {
			return this.fragments.length;
		}
	}

}
