package com.example.uzuswipe;


import java.sql.Timestamp;

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
	private String uzuSubject;
	private String uzuMessage;
	private float uzuLatitude;
	private float uzuLongitude;
	private boolean hasImages;
	private Timestamp birth;
	private int life;
	private Timestamp death;
	private int categoryID;
	
	public static final UzuFragment newInstance(){
		UzuFragment f = new UzuFragment();
		return f;
	}
	
	public static final UzuFragment newInstance(float longitude, float latitude, String subject, String message, boolean hasImage, int life)
	{
		UzuFragment f = new UzuFragment();
		Bundle bdl = new Bundle(6);
		bdl.putString(EXTRA_SUBJECT, subject);
		bdl.putString(EXTRA_MESSAGE, message);
		bdl.putFloat(EXTRA_LATITUDE, latitude);
		bdl.putFloat(EXTRA_LONGITUDE, longitude);
		bdl.putBoolean(EXTRA_HAS_IMAGE, hasImage);
		bdl.putInt(EXTRA_LIFE, life);
		f.setArguments(bdl);
		return f;
	}
	
	public static final UzuFragment newInstance(String subject, String message, float latitude, float longitude)
	{
	    UzuFragment f = new UzuFragment();
	    Bundle bdl = new Bundle(4);
	    bdl.putString(EXTRA_SUBJECT, subject);
	    bdl.putString(EXTRA_MESSAGE, message);
	    bdl.putFloat(EXTRA_LATITUDE, latitude);
	    bdl.putFloat(EXTRA_LONGITUDE, longitude);
	    f.setArguments(bdl);
	    return f;
	}
	
	/*public static final UzuFragment newInstance(String subject, String content) 
	{
		UzuFragment f = new UzuFragment();
	    Bundle bdl = new Bundle(2);
	    bdl.putString(EXTRA_SUBJECT, subject);
	    bdl.putString(EXTRA_MESSAGE, content);
	    
	    uzuSubject = subject;
	    uzuMessage = content;
	    f.setArguments(bdl);
	    return f;
	}*/
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		uzuSubject = getArguments().getString(EXTRA_SUBJECT);
		uzuMessage = getArguments().getString(EXTRA_MESSAGE);
		uzuLatitude = getArguments().getFloat(EXTRA_LATITUDE);
		uzuLongitude = getArguments().getFloat(EXTRA_LONGITUDE);
		View v = inflater.inflate(R.layout.activity_item, container, false);
		TextView subjectTextView = (TextView)v.findViewById(R.id.textView2);
		TextView contentTextView = (TextView)v.findViewById(R.id.textView3);
		subjectTextView.setText(uzuSubject);
		contentTextView.setText(uzuMessage);
		
        return v;
    }

	
	public String getUzuSubject(){
		return uzuSubject;
	}
	
	public String getUzuMessage(){
		return uzuMessage;
	}
	
	public float getUzuLongitude() {
		return uzuLongitude;
	}

	public void setUzuLongitude(float uzuLongitude) {
		this.uzuLongitude = uzuLongitude;
	}

	public float getUzuLatitude() {
		return uzuLatitude;
	}

	public void setUzuLatitude(float uzuLatitude) {
		this.uzuLatitude = uzuLatitude;
	}

	public void setItemSubject(String uzuSubject) {
		this.uzuSubject = uzuSubject;
	}

	public int getUzuID() {
		return uzuID;
	}

	public void setUzuID(int uzuID) {
		this.uzuID = uzuID;
	}

	public boolean isHasImages() {
		return hasImages;
	}

	public void setHasImages(boolean hasImages) {
		this.hasImages = hasImages;
	}

	public Timestamp getBirth() {
		return birth;
	}

	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Timestamp getDeath() {
		return death;
	}

	public void setDeath(Timestamp death) {
		this.death = death;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public void setUzuMessage(String uzuMessage) {
		this.uzuMessage = uzuMessage;
	}
	
	public void setUzuSubject(String uzuSubject) {
		this.uzuSubject = uzuSubject;
	}

	@Override
	public String toString(){
		return uzuSubject;
	}


}
