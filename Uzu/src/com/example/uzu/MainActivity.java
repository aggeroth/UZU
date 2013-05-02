package com.example.uzu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ImageView iView = (ImageView)findViewById(R.id.imageButton1);
		
		final Animation an = AnimationUtils.loadAnimation(this, R.anim.spin);
		
		ImageButton imgBtn = (ImageButton) findViewById(R.id.imageButton1);

		
		imgBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				iView.startAnimation(an);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()){
			case R.id.menu_scan:
				//Toast.makeText(this,"Scan selected", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_drop:
				//Toast.makeText(this,"Drop selected", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, DropActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_collection:
				//Toast.makeText(this,"Collection selected", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, CollectionActivity.class);
				startActivity(intent);
				break;
			default:
				break;	
		}
		return true;
	}

}
