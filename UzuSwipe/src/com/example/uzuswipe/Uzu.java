package com.example.uzuswipe;

import java.io.Serializable;
import java.util.Calendar;

public class Uzu implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private int uzuID;
	private double longitude;
	private double latitude;
	private String subject;
	private String message;
	private boolean hasImages;
	private Calendar birth;
	private int life; 
	private Calendar death;
	private int categoryID;
	
	public Uzu(){}
	
	public Uzu(int itemID, float longitude, float latitude, String subject, String message, boolean hasImages, Calendar birth, int life, Calendar death) {
		this.uzuID = itemID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.subject = subject;
		this.message = message;
		this.hasImages = hasImages;
		this.birth = birth;
		this.life = life;
		this.death = death;
	}
	
	public Uzu(float longitude, float latitude, String subject, String message, boolean hasImages, int life) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.subject = subject;
		this.message = message;
		this.hasImages = hasImages;
		this.life = life;
	}

	public int getUzuID() {
		return uzuID;
	}

	public void setUzuID(int itemID) {
		this.uzuID = itemID;
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

	public boolean isHasImages() {
		return hasImages;
	}

	public void setHasImages(boolean hasImages) {
		this.hasImages = hasImages;
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
	
	public int getCategoryID(){
		return categoryID;
	}
	
	public void setCategoryID(int id){
		categoryID = id;
	}

}