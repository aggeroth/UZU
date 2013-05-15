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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Minoru Nakano, Marwan Marwan, Martin Javier
 * DropFragment class
 * The class contains methods to;
 *  - create Fragment view for dropping an Uzu item.
 *  - send an Uzu item to the server
 */
public class DropFragment extends Fragment {
	Activity activity;
	
	Button buttonDrop;
	TextView resultText;
	static final String url = "http://aggeroth.com:8080/RestEasyServices/ocean/drop";
	
	EditText subjectField;
	EditText textField;
	
	//NumberPicker for the Uzu item lifetime.
	NumberPicker days, hours, mins;
	int total;
	/** Maximum days for the item lifetime.*/
	public static final int DAY_END = 7;
	/** Maximum hours for the item lifetime.*/
	public static final int HOUR_END= 23;
	/** Maximum minutes for the item lifetime.*/
	public static final int MIN_END = 59;
	
	/**
	 * Creates the Fragment view.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null){
			return null;
		}
		//Associate activity to MainActivity.
		activity = getActivity();
		//Assosicate view to this Fragment.
		View view = (LinearLayout)inflater.inflate(R.layout.fragment_drop, container, false);
		
		resultText = (TextView)view.findViewById(R.id.result_text);
		subjectField = (EditText)view.findViewById(R.id.input_subject);
		textField = (EditText)view.findViewById(R.id.input_text);
		
		//Initialize Days, Hours and Minutes NumberPicker
		days = (NumberPicker) view.findViewById(R.id.numberPickerDay);
	    days.setMinValue(0);
	    days.setMaxValue(7);
	    days.setWrapSelectorWheel(false);
	    days.setValue(DAY_END);
	    
	    hours = (NumberPicker) view.findViewById(R.id.numberPickerHour);
	    hours.setMinValue(0);
	    hours.setMaxValue(23);
	    hours.setWrapSelectorWheel(false);
	    hours.setValue(0);
	    
	    mins = (NumberPicker) view.findViewById(R.id.numberPickerMin);
	    mins.setMinValue(0);
	    mins.setMaxValue(59);
	    mins.setWrapSelectorWheel(false);
	    mins.setValue(0);
		
		//Set buttonDrop
		buttonDrop = (Button) view.findViewById(R.id.button_drop);
		buttonDrop.setOnClickListener(new View.OnClickListener() {
			
			/**
			 * Overriding onClick method.
			 */
			@Override
			public void onClick(View arg0) {				
				//Calculate the submitted total lifetime in minutes.
				total = (days.getValue() * 1440) + (hours.getValue() * 60) + mins.getValue();
				//if the calculated lifetime is more than 7 days,
				if(total > 10080){
	    			Toast.makeText(activity, "Your uzu cannot have a life time longer than 7 days.", Toast.LENGTH_SHORT).show();
	    		//if the calculated lifetime is 0,
				} else if (total == 0){
	    			Toast.makeText(activity, "You need to set a life time for your uzu.", Toast.LENGTH_SHORT).show();
	    		//if everything is ok, construct an Uzu item with input data.
				} else {
	    			String subject = subjectField.getText().toString();
					String text = textField.getText().toString();
					GPSTracker tracker = new GPSTracker(activity);
					Log.d("UZU", "point 1");
					subjectField.setText("");
					textField.setText("");
					Log.d("UZU", "point 2");
					int life = total;
					
					//Construct an Uzu item.
					Uzu item = new Uzu();
					System.out.println("this actulaly prints the item");
					//set Uzu subject.
					item.setSubject(subject);
					System.out.println(subject);
					//set Uzu message.
					item.setMessage(text);
					System.out.println(text);
					//Get current location of the mobile device.
					Location loc = tracker.getLocation();
					System.out.println(loc);
					//Set latitude, longitude, lifetime and image.
					item.setLatitude((float)loc.getLatitude());
					item.setLongitude((float)loc.getLongitude());
					item.setLife(life);
					//image could be null.
					item.setImage(null);
		
					Log.d("UZU", "point 3");
					//Create JSONObject from the Uzu item.
					JSONObject newItem = createJSON(item);
					Log.d("UZU", "point 4: " + newItem.toString());
					//Construct the UzuDropService with URL and the new Uzu item.
					UzuDropService uzuDrop = new UzuDropService(url, newItem);
					Log.d("UZU", "point 5");
					subject = "";
					text = "";
					
					String itemString = "Subject: " + item.getSubject() + 
							"\n" + "Content: " + item.getMessage() + 
							"\n" + "Latitude: " + item.getLatitude() + 
							"\n" + "Longitude: " + item.getLongitude() +
							"\n" + "Lifetime: " + item.getLife();
					
					Log.d("UZU", "point 6: " + itemString);
					//Send the item to the server.
					uzuDrop.execute();
	    		}				
			}
		});
		return view;
	}
	
	/**
	 * The method converts an Uzu item into JSONOject.
	 * @param item new Uzu item to be dropped.
	 * @return object JSONObject to be sent to the server.
	 */
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
		    object.put("image", (byte[])item.getImage());
		    object.put("life", (Integer)item.getLife());
		    object.put("categoryID", (Integer)item.getCategoryID());
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		  return object;
	}
	
	/**
	 * 
	 * @author Minoru Nakano
	 * UzuDropService class
	 * The class executes methods to send an Uzu item to the server on background thread.
	 */
	private class UzuDropService extends AsyncTask<Void, Void, String>{
		
		String httpString;
		JSONObject uzuPost;
		int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
		
		/**
		 * Constructor for the class.
		 * @param url URL String used to send an item to the server.
		 * @param object JSONObject of an Uzu item to be sent to the server.
		 */
		public UzuDropService(String url, JSONObject object){
			httpString = url;
			uzuPost = object;
		}
		
		/**
		 * 
		 * The method renders entity returned from the server into String.
		 * @param entity HttpEntity returned from the server.
		 * @return out String object rendered from entity.
		 * @throws IllegalStateException Exception thrown by the method.
		 * @throws IOException Exception thrown by the method.
		 */
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
		
		/**
		 * The method is executed on the background thread.
		 * @return stringResult indicates whether the Uzu has been successfully sent to the server or not.
		 */
		@Override
		protected String doInBackground(Void... params) {
			
			//Initializes the HTTP request.
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpPost httpPost = new HttpPost(httpString);
			httpPost.setHeader( "Content-Type", "application/json" );
			String stringResult = null;			
			Log.d("UZU", "point 7");
			try {
				//Sends the Uzu JSONObject to the server. 
				httpPost.setEntity(new ByteArrayEntity(uzuPost.toString().getBytes("UTF8")));
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				//Obtains the reault of the execution.
				stringResult = getASCIIContentFromEntity(entity);
				Log.d("UZU", "point 8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d("UZU", "point 9");
			return stringResult;
		}
		
		/**
		 * The method is executed after doInBakcground execution is completed.
		 * The method displays whether the Uzu item has been sent to the server successfully or not.
		 */
		protected void onPostExecute(String results) {
			Log.d("UZU", "point 10: " + results);
			if (results!=null) {
				Toast.makeText(activity, results, Toast.LENGTH_SHORT).show();
			}	
			buttonDrop.setClickable(true);
		}
	}
}
