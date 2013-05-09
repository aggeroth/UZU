package com.example.uzuswipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScanFragment extends Fragment {
	
	TextView resultText;
	ImageButton imgBtn;
	List<Uzu> uzuList;
	String uzuString = "nothing";
	GPSTracker tracker;
	UzuScanService uzuScan;
	
	static final String url = "http://www.aggeroth.com:8080/RestEasyServices/ocean/trawl/";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null){
			return null;
		}
		Activity activity = getActivity();
		View view = (RelativeLayout)inflater.inflate(R.layout.fragment_scan, container, false);
		
		final ImageView iView = (ImageView)view.findViewById(R.id.imageButton1);
		final Animation an = AnimationUtils.loadAnimation(activity, R.anim.spin);
		tracker = new GPSTracker(activity);
		resultText = (TextView)view.findViewById(R.id.return_text);
		
		imgBtn = (ImageButton)view.findViewById(R.id.imageButton1);
		imgBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				imgBtn.setClickable(false);
				String completeURL = getURL(tracker, url);
				iView.startAnimation(an);
				Log.d("UZU", completeURL);
				uzuScan = new UzuScanService(completeURL);
				uzuScan.execute();
			}
		});
		
		return view;
	}
	
	public String getURL(GPSTracker tracker, String url){		
		String completeURL = url;
		// check if GPS enabled     
	    if(tracker.canGetLocation()){
	        Location loc = tracker.getLocation();
	        float longitude = (float)loc.getLongitude();
	        float latitude = (float)loc.getLatitude();
	        completeURL += latitude + "/" + longitude;	        
	    }else{
	        // can't get location, GPS or Network is not enabled, Ask user to enable GPS/network in settings
	        tracker.showSettingsAlert();
	    }
	    return completeURL;
	}
	
	private class UzuScanService extends AsyncTask<Void, Void, String>{	
		String httpString;
		
		public UzuScanService(String url){
			httpString = url;
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
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(httpString);
			String stringJSON = null;			
			try {
				HttpResponse response = httpClient.execute(httpGet, localContext);
				HttpEntity entity = response.getEntity();
				stringJSON = getASCIIContentFromEntity(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return stringJSON;
		}
		
		protected void onPostExecute(String results) {
			Log.d("UZU", "Result: " + results);
			if (results!=null) {
				try{
					UzuJSONParser parser = new UzuJSONParser(results);
					uzuList = parser.parse();
					uzuString = "";
					for(int i = 0; i < uzuList.size(); i++){
						Uzu temp = uzuList.get(i);
						uzuString += "Uzu: " + i + "\n" +
									"Subject: " + temp.getSubject() + "\n" +
									"Message: " + temp.getMessage() + "\n" +
									"Longitude: " + temp.getLongitude() + "\n" +
									"Latitude: " + temp.getLatitude() + "\n\n";
						Log.d("UZU", "Uzu: " + i + " " + uzuString);
					}					
				}catch(Exception e){
					Log.e("JSON Exception", e.toString());
				}			
			}
			resultText.setText(uzuString);
			imgBtn.setClickable(true);
		}
	}
}
