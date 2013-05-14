package com.example.uzuswipe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CollectionFragment extends Fragment {
	
	ScanFragment scanFragment;
	
	List<Uzu> uzuFragments;
	
	static final UzuFragment[] items = {
		UzuFragment.newInstance("Item 1", "This is Item 1", (float)49.23232, (float)123.8987868),
		UzuFragment.newInstance("Item 2", "This is Item 2", (float)49.23232, (float)123.8987868),
		UzuFragment.newInstance("Item 3", "This is Item 3", (float)49.23232, (float)123.8987868),
		UzuFragment.newInstance("Item 4", "This is Item 4", (float)49.23232, (float)123.8987868),
		UzuFragment.newInstance("Item 5", "This is Item 5", (float)49.23232, (float)123.8987868)
	};
	
	UzuFragment item;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null){
			return null;
		}
		try{
			uzuFragments = scanFragment.getUzuList();
			for(int i = 0; i < uzuFragments.size(); i++) {
				System.out.println(uzuFragments.get(i).getSubject());
				
			}
		} catch (Exception e) {
			System.out.println("this is an error");
		}
		
		
		final Activity activity = getActivity();
		View view = (LinearLayout)inflater.inflate(R.layout.fragment_collection, container, false);
		
		ListView listView = (ListView)view.findViewById(R.id.itemCollection);
		
		
		//Displaying Items using ArrayAdapter
		ArrayAdapter<UzuFragment> itemAdapter = new ArrayAdapter<UzuFragment>(activity, 
				R.layout.list_view, items );
		//ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, MainActivity.items);
				
		listView.setAdapter(itemAdapter);
		
		//Specifying what happens when one item is clicked.
		listView.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			
			Intent intent = new Intent(activity, ItemActivity.class);
			
			intent.putExtra("position", position);
			//intent.putExtra("subject", subjectTitle);
			//intent.putExtra("content", subjectContent);
			startActivity(intent);
		}
		});
		
		return view;
	}


}
