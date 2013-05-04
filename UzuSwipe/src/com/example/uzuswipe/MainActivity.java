package com.example.uzuswipe;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends Activity {
	
	static final Item[] items = {
		new Item("Item 1", "This is Item 1"),
		new Item("Item 2", "This is Item 2"),
		new Item("Item 3", "This is Item 3"),
		new Item("Item 4", "This is Item 4"),
		new Item("Item 5", "This is Item 5")
	};
	
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
		String labelScan = getResources().getString(R.string.menu_scan);
		tab.setText(R.string.menu_scan);
		TabListener<ScanFragment> scanTab = new TabListener<ScanFragment>(this, labelScan, ScanFragment.class);
		tab.setTabListener(scanTab);
		actionBar.addTab(tab);
		
		String labelDrop = getResources().getString(R.string.menu_drop);
		tab = actionBar.newTab();
		tab.setText(R.string.menu_drop);
		TabListener<DropFragment> dropTab = new TabListener<DropFragment>(this, labelDrop, DropFragment.class);
		tab.setTabListener(dropTab);
		actionBar.addTab(tab);
		
		String labelCollection = getResources().getString(R.string.menu_drop);
		tab = actionBar.newTab();
		tab.setText(R.string.menu_collection);
		TabListener<CollectionFragment> collectionTab = new TabListener<CollectionFragment>(this, labelCollection, CollectionFragment.class);
		tab.setTabListener(collectionTab);
		actionBar.addTab(tab);
	}
	
	private class TabListener<T extends Fragment> implements ActionBar.TabListener{
		private Fragment myFragment;
		private final Activity myActivity;
		private final String myTag;
		private final Class<T> myClass;
		
		public TabListener(Activity activity, String tag, Class<T> cls){
			myActivity = activity;
			myTag = tag;
			myClass = cls;
		}
		
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){
			//nothing
		}
		
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft){
			if(myFragment == null){
				myFragment = android.app.Fragment.instantiate(myActivity, myClass.getName());
				ft.add(android.R.id.content, myFragment, myTag);
			} else {
				ft.attach(myFragment);
			}
		}
		
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){
			if(myFragment != null){
				ft.detach(myFragment);
			}
		}			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
