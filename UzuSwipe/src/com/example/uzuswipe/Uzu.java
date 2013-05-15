package com.example.uzuswipe;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Calendar;

public class Uzu implements Serializable {
	
private static final long serialVersionUID = 1L;
	
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
	
	public Uzu(){}
	
	public Uzu(int itemID, double longitude, double latitude, String subject, String message, byte[] image, Calendar birth, int life, Calendar death) {
		this.uzuID = itemID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.subject = subject;
		this.message = message;
		this.image = image;
		this.birth = birth;
		this.life = life;
		this.death = death;
	}
	
	public Uzu(double longitude, double latitude, String subject, String message, byte[] image, int life) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.subject = subject;
		this.message = message;
		this.image = image;
		this.life = life;
	}
	
	public Uzu(int uzuID, double longitude, double latitude, String subject, String message, byte[] image, Calendar birth, Calendar death) {
		this.uzuID = uzuID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.subject = subject;
		this.message = message;
		this.image = image;
		this.birth = birth;
		this.death = death;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	

}
