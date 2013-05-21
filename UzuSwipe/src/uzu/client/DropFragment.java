package uzu.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.example.uzuswipe.R;

import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.Fragment;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
	
	private final static int IMAGE_COMPRESSION_RATIO = 640;
	private static final String SERVER_URL = "http://aggeroth.com:8080/RestEasyServices/ocean/drop";
	/** Maximum days for the item lifetime.*/
	private static final int DAY_END = 7;
	/** Maximum hours for the item lifetime.*/
	private static final int HOUR_END= 23;
	/** Maximum minutes for the item lifetime.*/
	private static final int MIN_END = 59;
	private static final int SEVEN_DAYS = 10080;
	private static final int BYTE_SIZE = 4096;
	
	private Activity activity;
	private Button buttonDrop;
	private Button buttonImageFunctionality;
	private ImageView uzuImage;
	private EditText subjectField;
	private EditText textField;
	
	//NumberPicker for the Uzu item lifetime.
	private NumberPicker days;
	private NumberPicker hours;
	private NumberPicker mins;
	
	private int total;
	
	
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
		
		subjectField = (EditText)view.findViewById(R.id.input_subject);
		textField = (EditText)view.findViewById(R.id.input_text);
		uzuImage = (ImageView)view.findViewById(R.id.image_uzu_drop);
		
		/**
		 * Allows for the creation of a button that can be used to insert an image into the Uzu item.
		 * Button can be used to remove image from the Uzu item.
		 * See onClick() for functionality logic.
		 */
		buttonImageFunctionality = (Button) view.findViewById(R.id.button_add_image);
		
		/**
		 * Setting the listener for the buttonImageFunctionality button.
		 * Allows for the button to both insert and remove an image from the Uzu item.
		 * The inserting of the image is used to trigger the native Android image gallery for image selection.
		 * The removing of the image sets the ImageView to null, and resets the button label.
		 */
		buttonImageFunctionality.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (buttonImageFunctionality.getText() == activity.getResources().getString(R.string.button_add_image)) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
				} else {
					clearUzuImageAndResetButton();
				}
			}
		});
		
		//Initialize Days, Hours and Minutes NumberPicker
		days = (NumberPicker) view.findViewById(R.id.numberPickerDay);
	    days.setMinValue(0);
	    days.setMaxValue(DAY_END);
	    days.setWrapSelectorWheel(false);
	    days.setValue(DAY_END);
	    
	    hours = (NumberPicker) view.findViewById(R.id.numberPickerHour);
	    hours.setMinValue(0);
	    hours.setMaxValue(HOUR_END);
	    hours.setWrapSelectorWheel(false);
	    hours.setValue(0);
	    
	    mins = (NumberPicker) view.findViewById(R.id.numberPickerMin);
	    mins.setMinValue(0);
	    mins.setMaxValue(MIN_END);
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
				if(total > SEVEN_DAYS){
	    			Toast.makeText(activity, "Your uzu cannot have a life time longer than 7 days.", Toast.LENGTH_SHORT).show();
	    		//if the calculated lifetime is 0,
				} else if (total == 0){
	    			Toast.makeText(activity, "You need to set a life time for your uzu.", Toast.LENGTH_SHORT).show();
	    		//if everything is ok, construct an Uzu item with input data.
				} else {
	    			String subject = subjectField.getText().toString();
					String text = textField.getText().toString();
					GPSTracker tracker = new GPSTracker(activity);
					int life = total;
					
					//Construct an Uzu item.
					Uzu item = new Uzu();
					//set Uzu subject.
					item.setSubject(subject);
					//set Uzu message.
					item.setMessage(text);
					//Get current location of the mobile device.
					Location loc = tracker.getLocation();
					//Set latitude, longitude, lifetime and image.
					item.setLatitude((float)loc.getLatitude());
					item.setLongitude((float)loc.getLongitude());
					item.setLife(life);
					if(uzuImage.getVisibility() == View.VISIBLE){
						// Get ViewImage's bitmap to convert to byte array.
						Bitmap uzuBitmap = ((BitmapDrawable)uzuImage.getDrawable()).getBitmap();
						
						//this might be a necessary step to properly convert image bitmap to byte array.
						ByteArrayOutputStream bao = new ByteArrayOutputStream();
						uzuBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
						item.setImage(Base64.encodeToString(bao.toByteArray(), Base64.DEFAULT));
					} else {
						item.setImage(null);
					}
					
					//Create JSONObject from the Uzu item.
					JSONObject newItem = UzuJSONParser.createJSON(item);
					
					//clearUzuImageAndResetButton();
					
					//Construct the UzuDropService with URL and the new Uzu item.
					UzuDropService uzuDrop = new UzuDropService(SERVER_URL, newItem);	
					
					//Send the item to the server.
					uzuDrop.execute();
	    		}				
			}
		});
		return view;
	}
	

	
	/**
	 * This method gets the URI for the image selected from the Android Gallery. Constructs a bitmap from URI.
	 * Sets the bitmap to the ImageView, and makes the ImageView visible. Then sets the insert image button to
	 * a remove image button (basically modifying the button text).
	 * 
	 * This method is automatically called after startActivityForResult() is completed.
	 * This is an inherited method from the fragment.
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == android.app.Activity.RESULT_OK) {
	        if (requestCode == 1) {
	            Uri curImageUri = data.getData();
	            String imagePath = getRealPathFromURI(curImageUri);
	            Bitmap image = BitmapFactory.decodeFile(imagePath);
	            uzuImage.setImageBitmap(compressImage(image));
	            uzuImage.setVisibility(View.VISIBLE);
	            
	            // Set button text to "remove image", basically switch from adding an image to deleting an image.
	            buttonImageFunctionality.setText(activity.getResources().getString(R.string.button_remove_image));
	        }
	    }
	}
	
	/**
	 * 
	 * @author Minoru Nakano
	 * UzuDropService class
	 * The class executes methods to send an Uzu item to the server on background thread.
	 */
	private class UzuDropService extends AsyncTask<Void, Void, String>{
		
		private String httpString;
		private JSONObject uzuPost;
		private int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
		
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
		private String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n > 0) {
				byte[] b = new byte[BYTE_SIZE];
				n =  in.read(b);
				if (n > 0) {
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
			
			try {
				//Sends the Uzu JSONObject to the server. 
				httpPost.setEntity(new ByteArrayEntity(uzuPost.toString().getBytes("UTF8")));
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				//Obtains the result of the execution.
				stringResult = getASCIIContentFromEntity(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return stringResult;
		}
		
		/**
		 * The method is executed after doInBakcground execution is completed.
		 * The method displays whether the Uzu item has been sent to the server successfully or not.
		 */
		protected void onPostExecute(String results) {
			if (results!=null) {
				clearUzuImageAndResetButton();
				subjectField.setText("");
				textField.setText("");
				days.setValue(DAY_END);
				hours.setValue(0);
				mins.setValue(0);
				Toast.makeText(activity, "Uzu item has been dropped!", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(activity, "Could not drop an uzu item. Please try again.", Toast.LENGTH_LONG).show();
			}
			buttonDrop.setClickable(true);
		}
	}
	
	/**
	 * Clear Uzu item image, and reset the button.
	 */
	private void clearUzuImageAndResetButton() {
		uzuImage.setImageBitmap(null);
		uzuImage.setVisibility(View.GONE);
		// Reset the label on the button; basically making sure that the button converts back to an insert image button.
        buttonImageFunctionality.setText(activity.getResources().getString(R.string.button_add_image));
	}
	
	/**
	 * Image compression at default width of IMAGE_COMPRESSION_RATIO
	 * @param uncompressed image
	 * @return compressed image
	 */
	private Bitmap compressImage(Bitmap image) {
		double width = image.getWidth();
        double height = image.getHeight();
        double ratio = IMAGE_COMPRESSION_RATIO / width;
        int newHeight = (int)(ratio * height);
        return Bitmap.createScaledBitmap(image, IMAGE_COMPRESSION_RATIO, newHeight, false);
	}
	
	/**
	 * Convert the image URI to the direct file system path of the image file
	 * @param contentUri image URI
	 * @return file system path of image
	 */
	private String getRealPathFromURI(Uri contentUri) {
	    String [] projection = {MediaStore.Images.Media.DATA};
	    
	    // managedQuery() is deprecated as of 3.0
	    // found this on http://stackoverflow.com/a/5915670/1243372
	    String selection = null;
	    String[] selectionArgs = null;
	    String sortOrder = null;
	    
	    CursorLoader cursorLoader = new CursorLoader(activity, contentUri, projection, selection, selectionArgs, sortOrder);
	    Cursor cursor = cursorLoader.loadInBackground();
	    // end of http://stackoverflow.com/a/5915670/1243372
	    
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}
}
