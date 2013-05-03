package com.example.uzu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CollectionActivity extends Activity {
	
	static final Item[] items = {
		new Item("Item 1", "This is Item 1"),
		new Item("Item 2", "This is Item 2"),
		new Item("Item 3", "This is Item 3"),
		new Item("Item 4", "This is Item 4"),
		new Item("Item 5", "This is Item 5")
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		
		ListView listView = (ListView)findViewById(R.id.itemCollection);
		
		//Displaying Items using ArrayAdapter
		ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, items);
		listView.setAdapter(itemAdapter);
		
		//Specifying what happens when one item is clicked.
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				String item = "Subject: " + items[position].getItemSubject() + "\n" + "Content: " + items[position].getItemContent();
				Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collection, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()){
			case R.id.menu_scan:
				//Toast.makeText(this,"Scan selected", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_drop:
				//Toast.makeText(this,"Drop selected", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, DropActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_collection:
				//Toast.makeText(this,"Collection selected", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;	
		}
		return true;
	}

}
