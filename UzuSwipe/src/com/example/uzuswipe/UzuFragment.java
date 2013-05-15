package com.example.uzuswipe;



import java.util.Calendar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UzuFragment extends Fragment {
	
	static final String EXTRA_SUBJECT = "EXTRA_SUBJECT";
	static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
	static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
	static final String EXTRA_HAS_IMAGE = "EXTRA_HAS_IMAGE";
	static final String EXTRA_LIFE = "LIFE";
	
	private int uzuID;
	private double longitude;
	private double latitude;
	private String subject;
	private String message;
	private byte[] image;
	private Calendar birth;
	private int life; 
	private Calendar death;
	private int categoryID;
	
	
	public static final UzuFragment newInstance(){
		UzuFragment f = new UzuFragment();
		return f;
	}
	
	public static final UzuFragment newInstance(double longitude, double latitude, String subject, String message, byte[] image, int life, int death, int categoryID)
	{
		UzuFragment f = new UzuFragment();
		Bundle bdl = new Bundle(6);
		bdl.putString(EXTRA_SUBJECT, subject);
		bdl.putString(EXTRA_MESSAGE, message);
		bdl.putDouble(EXTRA_LATITUDE, latitude);
		bdl.putDouble(EXTRA_LONGITUDE, longitude);
		bdl.putInt(EXTRA_LIFE, life);
		f.setArguments(bdl);
		return f;
	}
	
	public static final UzuFragment newInstance(String subject, String message, double latitude, double longitude)
	{
	    UzuFragment f = new UzuFragment();
	    Bundle bdl = new Bundle(4);
	    bdl.putString(EXTRA_SUBJECT, subject);
	    bdl.putString(EXTRA_MESSAGE, message);
	    bdl.putDouble(EXTRA_LATITUDE, latitude);
	    bdl.putDouble(EXTRA_LONGITUDE, longitude);
	    f.setArguments(bdl);
	    return f;
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		subject = getArguments().getString(EXTRA_SUBJECT);
		message = getArguments().getString(EXTRA_MESSAGE);
		latitude = getArguments().getDouble(EXTRA_LATITUDE);
		longitude = getArguments().getDouble(EXTRA_LONGITUDE);
		View v = inflater.inflate(R.layout.activity_item, container, false);
		TextView subjectTextView = (TextView)v.findViewById(R.id.textView2);
		TextView contentTextView = (TextView)v.findViewById(R.id.textView3);
		subjectTextView.setText(subject);
		contentTextView.setText(message);
		
        return v;
    }

	public int getUzuID() {
		return uzuID;
	}

	public void setUzuID(int uzuID) {
		this.uzuID = uzuID;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Calendar getBirth() {
		return birth;
	}

	public void setBirth(Calendar birth) {
		this.birth = birth;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Calendar getDeath() {
		return death;
	}

	public void setDeath(Calendar death) {
		this.death = death;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public static String getExtraSubject() {
		return EXTRA_SUBJECT;
	}

	public static String getExtraMessage() {
		return EXTRA_MESSAGE;
	}

	public static String getExtraLatitude() {
		return EXTRA_LATITUDE;
	}

	public static String getExtraLongitude() {
		return EXTRA_LONGITUDE;
	}

	public static String getExtraHasImage() {
		return EXTRA_HAS_IMAGE;
	}

	public static String getExtraLife() {
		return EXTRA_LIFE;
	}

		
}
