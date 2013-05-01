package com.example.uzu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_scan:
				Toast.makeText(this,"Scan selected", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_drop:
				Toast.makeText(this,"Drop selected", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_collection:
				Toast.makeText(this,"Collection selected", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;	
		}
		return true;
	}

}
