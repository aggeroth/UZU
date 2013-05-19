package uzu.client;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * @author Marwan Marwan, Nemanja Savic
 * Uzu class
 * Representation of an Uzu item.
 * Contains constructors as well as getter and setter methods for Uzu attributes.
 */
public class Uzu implements Serializable {
	
	/** Serial ID*/
	private static final long serialVersionUID = 1L;
	
	//Uzu attribute variables
	private int uzuID;
	private double longitude;
	private double latitude;
	private String subject;
	private String message;
	private String image;
	private Calendar birth;
	private int life; 
	private Calendar death;
	private int categoryID;
	
	/**
	 * Default constructor
	 */
	public Uzu(){}
	
	/**
	 * Constructor that takes all the attirbute values in.
	 * Used when parsing an Uzu object from JSON String.
	 * @param itemID uzuId
	 * @param longitude longitude value
	 * @param latitude latitude value
	 * @param subject subject value
	 * @param message message value
	 * @param image image value
	 * @param birth birth value
	 * @param life life value
	 * @param death death value
	 */
	public Uzu(int itemID, double longitude, double latitude, String subject, String message, String image, Calendar birth, int life, Calendar death) {
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
	
	/**
	 * Constructor that takes the following values.
	 * Used when creating a new Uzu object to be dropped. (Uploaded to the server.)
	 * @param longitude longitude value
	 * @param latitude latitude value
	 * @param subject subject value
	 * @param message message value
	 * @param image image value
	 * @param life life value
	 */
	public Uzu(double longitude, double latitude, String subject, String message, String image, int life) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.subject = subject;
		this.message = message;
		this.image = image;
		this.life = life;
	}
	
	/**
	 * Constructor that takes the following values.
	 * @param uzuID uzuID value
	 * @param longitude longitude value
	 * @param latitude latitude value
	 * @param subject subject value
	 * @param message message value
	 * @param image image value
	 * @param birth birth value
	 * @param death death value
	 */
	public Uzu(int uzuID, double longitude, double latitude, String subject, String message, String image, Calendar birth, Calendar death) {
		this.uzuID = uzuID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.subject = subject;
		this.message = message;
		this.image = image;
		this.birth = birth;
		this.death = death;
	}
	
	/**
	 * Gets uzuID attribute
	 * @return uzuID uzuID value
	 */
	public int getUzuID() {
		return uzuID;
	}
	
	/**
	 * Sets uzuID attribute
	 * @param uzuID uzuID new value
	 */
	public void setUzuID(int itemID) {
		this.uzuID = itemID;
	}
	
	/**
	 * Gets longitude attribute
	 * @return longitude longitude value
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Sets longitude attribute
	 * @param longitude longitude new value
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Gets latitude attribute
	 * @return latitude latitude value
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Sets latitude attribute
	 * @param latitude latitude new value
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Gets subject attribute
	 * @return subject subject value
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Sets subject attribute
	 * @param subject subject new value
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Gets message attribute
	 * @return message message value
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets message attribute
	 * @param message message new value
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets birth attribute
	 * @return birth birth value
	 */
	public Calendar getBirth() {
		return birth;
	}
	
	/**
	 * Sets birth attribute
	 * @param birth birth new value
	 */
	public void setBirth(Calendar birth) {
		this.birth = birth;
	}
	
	/**
	 * Gets life attribute
	 * @return life life value
	 */
	public int getLife() {
		return life;
	}
	
	/**
	 * Sets life attribute
	 * @param life life new value
	 */
	public void setLife(int life) {
		this.life = life;
	}
	
	/**
	 * Gets death attribute
	 * @return death death value
	 */
	public Calendar getDeath() {
		return death;
	}
	
	/**
	 * Sets death attribute
	 * @param death death new value
	 */
	public void setDeath(Calendar death) {
		this.death = death;
	}
	
	/**
	 * Gets categoryID attribute
	 * @return categoryID categoryID value
	 */
	public int getCategoryID(){
		return categoryID;
	}
	
	/**
	 * Sets categoryID attribute
	 * @param categoryID categoryID new value
	 */
	public void setCategoryID(int id){
		categoryID = id;
	}
	
	/**
	 * Gets image attribute
	 * @return image image value
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Sets image attribute
	 * @param image image new value
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
