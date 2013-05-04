package com.example.uzuswipe;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CollectionFragment extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null){
			return null;
		}
		final Activity activity = getActivity();
		View view = (LinearLayout)inflater.inflate(R.layout.fragment_collection, container, false);
		
		ListView listView = (ListView)view.findViewById(R.id.itemCollection);
		
		//Displaying Items using ArrayAdapter
		ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(activity, android.R.layout.simple_list_item_1, MainActivity.items);
		listView.setAdapter(itemAdapter);
		
		//Specifying what happens when one item is clicked.
		listView.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			String item = "Subject: " + MainActivity.items[position].getItemSubject() + "\n" + "Content: " + MainActivity.items[position].getItemContent();
			Toast.makeText(activity, item, Toast.LENGTH_SHORT).show();
		}
		});
		
		return view;
	}
}
