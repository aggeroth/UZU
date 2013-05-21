package uzu.client;

import java.util.Calendar;

import com.example.uzuswipe.R;

import android.support.v4.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Uzu Fragment class that takes a uzu object and creates a fragment.
 * 
 * @author Merisha Shim
 * 
 */
public class UzuFragment extends Fragment {

	/** Constant variable used for the subject. */
	static final String EXTRA_SUBJECT = "EXTRA_SUBJECT";

	/** Constant variable used for the message. */
	static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

	/** Constant variable used for the latitude. */
	static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";

	/** Constant variable used for the longitude. */
	static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";

	/** Constant variable used for the image. */
	static final String EXTRA_IMAGE = "EXTRA_IMAGE";

	/** Constant variable used for the life. */
	static final String EXTRA_LIFE = "LIFE";

	/** The id of the uzu object. */
	private int uzuID;

	/** The longitude of the uzu object. */
	private double longitude;

	/** The latitude of the uzu object. */
	private double latitude;

	/** The subject of the uzu object. */
	private String subject;
	/** The message of the uzu object. */
	private String message;
	/** The image of the uzu object. */
	private String image;
	/** The birth of the uzu object picked. */
	private Calendar birth;
	/** The life of the uzu object. */
	private int life;
	/** The death of the uzu object (when it expires). */
	private Calendar death;
	/** The category of the uzu object. */
	private int categoryID;

	/**
	 * Empty Constructor with no parameters.
	 * 
	 * @return f
	 */
	public static final UzuFragment newInstance() {
		UzuFragment f = new UzuFragment();
		return f;
	}

	/**
	 * Constructor that takes in the logitude, latitude, subject, message,
	 * image, life, and categoryID of the uzu object. This is used when dropping
	 * and picking up the uzu item.
	 * 
	 * @param longitude
	 *            The latitude of the uzu object.
	 * @param latitude
	 *            The longitude of the uzu object.
	 * @param subject
	 *            The subject of the uzu object.
	 * @param message
	 *            The message of the uzu object.
	 * @param image
	 *            The image of the uzu object.
	 * @param life
	 *            The life of the uzu object.
	 * @param categoryID
	 *            The categoryID of the uzu object.
	 * @return f
	 */
	public static final UzuFragment newInstance(double longitude,
			double latitude, String subject, String message, String image,
			int life, int categoryID) {
		UzuFragment f = new UzuFragment();
		Bundle bdl = new Bundle(6);
		bdl.putString(EXTRA_SUBJECT, subject);
		bdl.putString(EXTRA_MESSAGE, message);
		bdl.putDouble(EXTRA_LATITUDE, latitude);
		bdl.putDouble(EXTRA_LONGITUDE, longitude);
		bdl.putInt(EXTRA_LIFE, life);
		bdl.putString(EXTRA_IMAGE, image);
		f.setArguments(bdl);
		return f;
	}

	/**
	 * This method creates the view of the fragment. It uses the subject,
	 * content, and image to display on the fragment page.
	 * 
	 * @return v
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		subject = getArguments().getString(EXTRA_SUBJECT);
		message = getArguments().getString(EXTRA_MESSAGE);
		latitude = getArguments().getDouble(EXTRA_LATITUDE);
		longitude = getArguments().getDouble(EXTRA_LONGITUDE);
		image = getArguments().getString(EXTRA_IMAGE);
		View v = inflater.inflate(R.layout.activity_item, container, false);
		TextView subjectTextView = (TextView) v.findViewById(R.id.textView2);
		TextView contentTextView = (TextView) v.findViewById(R.id.textView3);
		ImageView uzuImage = (ImageView) v.findViewById(R.id.uzuItemImage);
		subjectTextView.setText(subject);
		contentTextView.setText(message);

		if (image != null) {

			byte[] data = Base64.decode(image, Base64.DEFAULT);
			uzuImage.setImageBitmap(BitmapFactory.decodeByteArray(data, 0,
					data.length));
		} else {
			uzuImage.setImageBitmap(null);
		}
		return v;
	}

	/**
	 * Getter method for the Uzu ID.
	 * 
	 * @return uzuID
	 */
	public int getUzuID() {
		return uzuID;
	}

	/**
	 * Setter method for the Uzu ID.
	 * 
	 * @param uzuID
	 *            The id for the uzu object.
	 */
	public void setUzuID(int uzuID) {
		this.uzuID = uzuID;
	}

	/**
	 * Getter method for the Longitude.
	 * 
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Setter method for the longitude.
	 * 
	 * @param longitude
	 *            The longitude of the uzu object.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Getter method for the latitude.
	 * 
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Setter method for the latitude.
	 * 
	 * @param latitude
	 *            The latitude of the uzu object.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Getter method for the subject.
	 * 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Setter method for the subject.
	 * 
	 * @param subject
	 *            The subject of the uzu object.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Getter method for the message.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for the message.
	 * 
	 * @param message
	 *            The message of the uzu object.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter method for the image.
	 * 
	 * @return image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Setter method for the image.
	 * @param image	The image of the uzu object.
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Getter method for the birth.
	 * @return birth
	 */
	public Calendar getBirth() {
		return birth;
	}

	/** 
	 * Setter method for the birth.
	 * @param birth	The birth of the uzu object.
	 */
	public void setBirth(Calendar birth) {
		this.birth = birth;
	}

	/**
	 * Getter method for the life.
	 * @return life.
	 */
	public int getLife() {
		return life;
	}

	/**
	 * Setter method for the life.
	 * @param life	The life of the uzu object.
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * Getter method for the death.
	 * @return death
	 */
	public Calendar getDeath() {
		return death;
	}

	/** 
	 * Setter method for the death.
	 * @param death
	 */
	public void setDeath(Calendar death) {
		this.death = death;
	}

	/**
	 * Getter method for the category ID.
	 * @return categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}

	/**
	 * Setter method for the category ID.
	 * @param categoryID	The category ID of the uzu object.
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * Getter method for the constant variable EXTRA_SUBJECT.
	 * @return EXTRA_SUBJECT
	 */
	public static String getExtraSubject() {
		return EXTRA_SUBJECT;
	}

	/**
	 * Getter method for the constant variable EXTRA_MESSAGE.
	 * @return EXTRA_MESSAGE
	 */
	public static String getExtraMessage() {
		return EXTRA_MESSAGE;
	}

	/**
	 * Getter method for the constant variable EXTRA_LATITUDE.
	 * @return EXTRA_LATITUDE
	 */
	public static String getExtraLatitude() {
		return EXTRA_LATITUDE;
	}
	/**
	 * Getter method for the constant variable EXTRA_LONGITUDE.
	 * @return EXTRA_LONGITUDE
	 */
	public static String getExtraLongitude() {
		return EXTRA_LONGITUDE;
	}
	/**
	 * Getter method for the constant variable EXTRA_IMAGE.
	 * @return EXTRA_IMAGE
	 */
	public static String getExtraHasImage() {
		return EXTRA_IMAGE;
	}
	/**
	 * Getter method for the constant variable EXTRA_LIFE.
	 * @return EXTRA_LIFE
	 */
	public static String getExtraLife() {
		return EXTRA_LIFE;
	}
	/**
	 * toString() method that takes the subject of the uzu object
	 * and is able to display on the list view.
	 */
	@Override
	public String toString() {
		return subject;
	}

}
