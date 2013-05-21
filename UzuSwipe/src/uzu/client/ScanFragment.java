package uzu.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.example.uzuswipe.R;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class ScanFragment extends Fragment {
	
	TextView resultText;
	ImageButton imgBtn;
	List<Uzu> uzuList;
	String uzuString = "nothing";
	GPSTracker tracker;
	UzuScanService uzuScan;
	List<Uzu> uzuList2;
	int scanSize;
	
	MainActivity mainActivity;
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
				uzuList2 = uzuList;
			}
		});
		System.out.println("the uzu list" + uzuList2);
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
			Activity activity = getActivity();
			DatabaseHandler db = new DatabaseHandler(getActivity());
			
			if (results!=null) {
				try{
					int currentSize = db.getDatabaseSize();
					UzuJSONParser parser = new UzuJSONParser(results);
					uzuList = parser.parse();
					uzuString = "";
					for(int i = 0; i < uzuList.size(); i++){
						Uzu temp = uzuList.get(i);

						Calendar pickedup = Calendar.getInstance();
						
						db.addUZU(new Uzu(temp.getUzuID(), temp.getLongitude(), temp.getLatitude(), temp.getSubject(), temp.getMessage(), temp.getImage(), pickedup, temp.getDeath()));
						
						
						/**uzuString += "Uzu: " + i + "\n" +
									"Subject: " + temp.getSubject() + "\n" +
									"Message: " + temp.getMessage() + "\n" +
									"Longitude: " + temp.getLongitude() + "\n" +
									"Latitude: " + temp.getLatitude() + "\n" +
									"Image: " + temp.getImage() + "\n\n";
						Log.d("UZU", "Uzu: " + i + " " + uzuString);
						*/
					}	
					int newSize = db.getDatabaseSize();
					scanSize = newSize - currentSize;
				}catch(Exception e){
					Log.e("JSON Exception", e.toString());
				}			
			}
			//resultText.setText(uzuString);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					activity);
			
			
			if (scanSize != 0) {
				

				// set title
				alertDialogBuilder.setTitle("Uzus Found!");

				// set dialog message
				alertDialogBuilder
						.setMessage(
								scanSize
										+ " new uzus found! Would you like to go to your Collection? (Just press no for now please)")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										
										CollectionFragment fragment = new CollectionFragment();
										ScanFragment scanFragment = new ScanFragment();
										FragmentManager fragmentManager = getFragmentManager();
										FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
										
										fragmentTransaction.replace(android.R.id.content, fragment);
										fragmentTransaction.detach(scanFragment);
										
										fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
										fragmentTransaction.addToBackStack(null);
										fragmentTransaction.commit();
																	
										
										//mainActivity.tab.setTabListener(mainActivity.scanTab);
										
										
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
			else {
				alertDialogBuilder.setTitle("No Uzuz found!");
				// set dialog message
				alertDialogBuilder
						.setMessage(
								"Sorry, no Uzus found...")
						.setCancelable(false)
						
						.setNegativeButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			
			}

			
			imgBtn.setClickable(true);
		}
	}

	public List<Uzu> getUzuList() {
		return uzuList2;
	}

	public void setUzuList(List<Uzu> uzuList) {
		this.uzuList = uzuList;
	}
	
}
