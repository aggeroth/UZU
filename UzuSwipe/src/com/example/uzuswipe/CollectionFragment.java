package com.example.uzuswipe;

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
	
	static final Item[] items = {
		new Item("Item 1", "This is Item 1"),
		new Item("Item 2", "This is Item 2"),
		new Item("Item 3", "This is Item 3"),
		new Item("Item 4", "This is Item 4"),
		new Item("Item 5", "This is Item 5")
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null){
			return null;
		}
		final Activity activity = getActivity();
		View view = (LinearLayout)inflater.inflate(R.layout.fragment_collection, container, false);
		
		ListView listView = (ListView)view.findViewById(R.id.itemCollection);
		
		//Displaying Items using ArrayAdapter
		ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(activity, android.R.layout.simple_list_item_1, MainActivity.items);
		//ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, MainActivity.items);
		listView.setAdapter(itemAdapter);
		
		//Specifying what happens when one item is clicked.
		listView.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			/*String item = "Subject: " + MainActivity.items[position].getItemSubject() + "\n" + "Content: " + MainActivity.items[position].getItemContent();
			Toast.makeText(activity, item, Toast.LENGTH_SHORT).show(); */
			
			TextView subject = (TextView)view.findViewById(R.id.textView2);
			TextView content = (TextView)view.findViewById(R.id.textView3);
			
			
			
			String subjectTitle = items[position].getItemSubject();	
			String subjectContent = items[position].getItemContent();
			
			Intent intent = new Intent(getActivity(), ItemActivity.class);
			intent.putExtra("subject", subjectTitle);
			intent.putExtra("content", subjectContent);
			startActivity(intent);
		}
		});
		
		return view;
	}
}
