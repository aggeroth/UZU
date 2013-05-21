package uzu.client;

import com.example.uzuswipe.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;

/**
 * 
 * @author Minoru Nakano
 * MainActivity class
 * The class initializes all the fragments used in the application.
 */
public class MainActivity extends Activity {
	/**
	 * Creates Action Bar and Tabs with three Fragments.
	 */
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Set up the action bar
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//Set up three tabs
		Tab tab = actionBar.newTab();
		//Scan Tab
		String labelScan = getResources().getString(R.string.menu_scan);
		tab.setText(R.string.menu_scan);
		TabListener<ScanFragment> scanTab = new TabListener<ScanFragment>(this, labelScan, ScanFragment.class);
		tab.setTabListener(scanTab);
		actionBar.addTab(tab);
		
		//Drop Tab
		String labelDrop = getResources().getString(R.string.menu_drop);
		tab = actionBar.newTab();
		tab.setText(R.string.menu_drop);
		TabListener<DropFragment> dropTab = new TabListener<DropFragment>(this, labelDrop, DropFragment.class);
		tab.setTabListener(dropTab);
		actionBar.addTab(tab);
		
		//Collection Tab
		String labelCollection = getResources().getString(R.string.menu_drop);
		tab = actionBar.newTab();
		tab.setText(R.string.menu_collection);
		TabListener<CollectionFragment> collectionTab = new TabListener<CollectionFragment>(this, labelCollection, CollectionFragment.class);
		tab.setTabListener(collectionTab);
		actionBar.addTab(tab);
	}
	
	/**
	 * 
	 * @author Minoru Nakano
	 * TabListener class
	 * The class configures each tab with a speciic Fragment.
	 * @param <T> Fragment passed on to the TabListener
	 */
	private class TabListener<T extends Fragment> implements ActionBar.TabListener{
		private Fragment myFragment;
		private final Activity myActivity;
		private final String myTag;
		private final Class<T> myClass;
		
		/**
		 * Constructor for the class
		 * @param activity Activity associated with the listener (MainActivity in this case)
		 * @param tag Label for the tab.
		 * @param cls Fragment to be associated with the tab.
		 */
		public TabListener(Activity activity, String tag, Class<T> cls){
			myActivity = activity;
			myTag = tag;
			myClass = cls;
		}
		
		/**
		 * No method content specified. Method not used.
		 */
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){
			//nothing
		}
		
		/**
		 * Defines actions when a tab is selected.
		 * The method displays the Fragment associated with the selected tab.
		 */
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft){
			if(myFragment == null){
				myFragment = android.app.Fragment.instantiate(myActivity, myClass.getName());
				ft.add(android.R.id.content, myFragment, myTag);
			} else {
				ft.attach(myFragment);
			}
		}
		
		/**
		 * Defines actions when a tab is unselected.
		 * The method detach the associated Fragment from the FragmentTransaction.
		 */
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){
			if(myFragment != null){
				ft.detach(myFragment);
			}
		}			
	}
	
	/**
	 * The method to inflate the options menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
