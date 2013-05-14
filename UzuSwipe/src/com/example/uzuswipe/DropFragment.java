package com.example.uzuswipe;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DropFragment extends Fragment {
	Activity activity;
	
	Button buttonDrop;
	TextView resultText;
	static final String url = "http://aggeroth.com:8080/RestEasyServices/ocean/drop";
	
	EditText subjectField;
	EditText textField;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null){
			return null;
		}
		activity = getActivity();
		View view = (LinearLayout)inflater.inflate(R.layout.fragment_drop, container, false);
		
		resultText = (TextView)view.findViewById(R.id.result_text);
		subjectField = (EditText)view.findViewById(R.id.input_subject);
		textField = (EditText)view.findViewById(R.id.input_text);
		
		//Set buttonDrop
		buttonDrop = (Button) view.findViewById(R.id.button_drop);
		buttonDrop.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View arg0) {				
				String subject = subjectField.getText().toString();
				String text = textField.getText().toString();
				GPSTracker tracker = new GPSTracker(activity);
				Log.d("UZU", "point 1");
				subjectField.setText("");
				textField.setText("");
				Log.d("UZU", "point 2");
				int life = 4;
				Uzu item = new Uzu();
				item.setSubject(subject);
				item.setMessage(text);
				Location loc = tracker.getLocation();
				item.setLatitude((float)loc.getLatitude());
				item.setLongitude((float)loc.getLongitude());
				item.setLife(life);
				item.setImage(null);
				Log.d("UZU", "point 3");
				JSONObject newItem = createJSON(item);
				Log.d("UZU", "point 4: " + newItem.toString());
				UzuDropService uzuDrop = new UzuDropService(url, newItem);
				Log.d("UZU", "point 5");
				subject = "";
				text = "";
				
				String itemString = "Subject: " + item.getSubject() + 
						"\n" + "Content: " + item.getMessage() + 
						"\n" + "Latitude: " + item.getLatitude() + 
						"\n" + "Longitude: " + item.getLongitude();
				
				Log.d("UZU", "point 6: " + itemString);
				uzuDrop.execute();
			}
		});
		return view;
	}
	
	public JSONObject createJSON(Uzu item) {
		  JSONObject object = new JSONObject();
		  try {
		    object.put("uzuID", (Integer)item.getUzuID());
		    object.put("longitude", (Double)item.getLongitude());
		    object.put("latitude", (Double)item.getLatitude());
		    object.put("subjectHeading", item.getSubject());
		    object.put("messageBody", item.getMessage());
		    object.put("birth", (Calendar)item.getBirth());
		    object.put("death", (Calendar)item.getDeath());
		    object.put("hasImages", (Blob)item.getImage());
		    object.put("life", (Integer)item.getLife());
		    object.put("categoryID", (Integer)item.getCategoryID());
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		  return object;
	}

	private class UzuDropService extends AsyncTask<Void, Void, String>{
		
		String httpString;
		JSONObject uzuPost;
		int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
		
		public UzuDropService(String url, JSONObject object){
			httpString = url;
			uzuPost = object;
		}
		
		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n>0) {
				byte[] b = new byte[4096];
				n =  in.read(b);
				if (n>0) {
					out.append(new String(b, 0, n));
				}
			}			
			return out.toString();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpPost httpPost = new HttpPost(httpString);
			httpPost.setHeader( "Content-Type", "application/json" );
			String stringResult = null;			
			Log.d("UZU", "point 7");
			try {
				httpPost.setEntity(new ByteArrayEntity(uzuPost.toString().getBytes("UTF8")));
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				stringResult = getASCIIContentFromEntity(entity);
				Log.d("UZU", "point 8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d("UZU", "point 9");
			return stringResult;
		}
		
		protected void onPostExecute(String results) {
			Log.d("UZU", "point 10: " + results);
			if (results!=null) {
				resultText.setText(results);
			}	
			buttonDrop.setClickable(true);
		}
	}
}
