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

/**
 * This class is responsible for scanning items from around the area and parsing
 * them through as uzu objects.
 * 
 * @author Merisha Shim, Nemanja Savic, Minoru Nakano, Marwan Marwan
 * 
 */
public class ScanFragment extends Fragment {

	/** The textview that show the result of the uzu object. */
	TextView resultText;
	/** The image button of the scan. */
	ImageButton imgBtn;
	/** The list of uzu objects. */
	List<Uzu> uzuList;
	/** The uzu string. */
	String uzuString = "nothing";
	/** Using the GPSTracker class. */
	GPSTracker tracker;
	/** Using the UzuScanService class. */
	UzuScanService uzuScan;
	/** The size of the uzu objects found. */
	int scanSize;
	
	/** Address of the server. */
	static final String url = "http://www.aggeroth.com:8080/RestEasyServices/ocean/trawl/";

	/** Creates a view that displays the scanning page. */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		Activity activity = getActivity();
		View view = (RelativeLayout) inflater.inflate(R.layout.fragment_scan,
				container, false);

		final ImageView iView = (ImageView) view
				.findViewById(R.id.imageButton1);
		final Animation an = AnimationUtils
				.loadAnimation(activity, R.anim.spin);
		tracker = new GPSTracker(activity);
		resultText = (TextView) view.findViewById(R.id.return_text);

		imgBtn = (ImageButton) view.findViewById(R.id.imageButton1);
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

	/**
	 * Method that gets the URL of the server and locates the longitude and latitude of the specific location.
	 * @param tracker	The GPSTracker class.
	 * @param url		The url of the server.
	 * @return the server URL
	 */
	public String getURL(GPSTracker tracker, String url) {
		String completeURL = url;
		// check if GPS enabled
		if (tracker.canGetLocation()) {
			Location loc = tracker.getLocation();
			float longitude = (float) loc.getLongitude();
			float latitude = (float) loc.getLatitude();
			completeURL += latitude + "/" + longitude;
		} else {

			tracker.showSettingsAlert();
		}
		return completeURL;
	}

	/**
	 * This class is responsible for sending requests to the server to get the
	 * uzu objects.
	 * 
	 * @author Minoru Nakano
	 *
	 */
	private class UzuScanService extends AsyncTask<Void, Void, String> {
		String httpString;

		/**
		 * Constructor that takes the server URL.
		 * @param url	The server URL.
		 */
		public UzuScanService(String url) {
			httpString = url;
		}

		/**
		 * This method returns a entity from the server.
		 * 
		 * @param entity	The entity of the object.
		 * @return the ASCII string of the entity
		 * @throws IllegalStateException
		 * @throws IOException
		 */
		protected String getASCIIContentFromEntity(HttpEntity entity)
				throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n > 0) {
				byte[] b = new byte[4096];
				n = in.read(b);
				if (n > 0) {
					out.append(new String(b, 0, n));
				}
			}
			return out.toString();
		}

		/**
		 * 
		 */
		@Override
		protected String doInBackground(Void... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(httpString);
			String stringJSON = null;
			try {
				HttpResponse response = httpClient.execute(httpGet,
						localContext);
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

			if (results != null) {
				try {
					int currentSize = db.getDatabaseSize();
					UzuJSONParser parser = new UzuJSONParser(results);
					uzuList = parser.parse();
					uzuString = "";
					for (int i = 0; i < uzuList.size(); i++) {
						Uzu temp = uzuList.get(i);

						Calendar pickedup = Calendar.getInstance();

						db.addUZU(new Uzu(temp.getUzuID(), temp.getLongitude(),
								temp.getLatitude(), temp.getSubject(), temp
										.getMessage(), temp.getImage(),
								pickedup, temp.getDeath()));

						
					}
					int newSize = db.getDatabaseSize();
					scanSize = newSize - currentSize;
				} catch (Exception e) {
					Log.e("JSON Exception", e.toString());
				}
			}
			// resultText.setText(uzuString);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					activity);

			if (scanSize != 0) {

				// set title
				alertDialogBuilder.setTitle("Uzus Found!");

				// set dialog message
				alertDialogBuilder
						.setMessage(
								scanSize
										+ " new uzus found and added to your collection.")
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
			} else {
				alertDialogBuilder.setTitle("No Uzuz found!");
				// set dialog message
				alertDialogBuilder
						.setMessage("Sorry, no Uzus found...")
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

	
}
