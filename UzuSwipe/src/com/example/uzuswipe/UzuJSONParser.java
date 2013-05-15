package com.example.uzuswipe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 
 * @author Minoru Nakano
 * UzuJSONParser class
 * The class parses JSON string into a list of Uzu objects.
 */
public class UzuJSONParser {
	
	/** String variable for uzuID node*/
    private static final String TAG_UZUID = "uzuID";
    /** String variable for longitude node*/
    private static final String TAG_LONGITUDE = "longitude";
    /** String variable for latitude node*/
    private static final String TAG_LATITUDE = "latitude";
    /** String variable for subjectHeading node*/
    private static final String TAG_SUBJECT = "subjectHeading";
    /** String variable for messageBody node*/
    private static final String TAG_MESSAGE = "messageBody";
    /** String variable for birth node*/
    private static final String TAG_BIRTH = "birth";
    /** String variable for life node*/
    private static final String TAG_LIFE = "life";
    /** String variable for death node*/
    private static final String TAG_DEATH = "death";
    /** String variable for image node*/
    private static final String TAG_IMAGE = "image";
	
	//JSON string object to be returned from the server.
    String uzuJSONString;
    //JSONArray which is used to put JSON objects into arrays.
    JSONArray uzuArray;
    //JSONObject representing each JSONObject in the JSONArray.
	JSONObject jsonObject;
	//List of Uzu objects.
	List<Uzu> uzuList = new ArrayList<Uzu>();
	
	/**
	 * Constructor for the class.
	 * @param jsonString String object to be returned from the server.
	 */
	public UzuJSONParser(String jsonString){
		uzuJSONString = jsonString;
		 Log.d("UZU", uzuJSONString);
	}
	
	/**
	 * The method parses JSON String into list of Uzu objects.
	 * @return uzuList List of Uzu objects
	 */
	public List<Uzu> parse(){
		try { 
	        //parsing the string into JSONArray
			uzuArray = new JSONArray(uzuJSONString);
			
			// looping through All JSONObjects in the JSONArray.
	         for(int i = 0; i < uzuArray.length(); i++){
	        	 JSONObject u = uzuArray.getJSONObject(i);    
	        	 Log.d("UZU", "loop 1 " + i);
	        	 // Storing each JSON item in variable
	        	 String id = u.getString(TAG_UZUID);
	        	 String longitude = u.getString(TAG_LONGITUDE);
	             String latitude = u.getString(TAG_LATITUDE);
	             String subject = u.getString(TAG_SUBJECT);
	             String message = u.getString(TAG_MESSAGE);
	             String birth = u.getString(TAG_BIRTH);
	             String life = u.getString(TAG_LIFE);
	             String death = u.getString(TAG_DEATH);
	             String image = u.getString(TAG_IMAGE);
	             
	             Calendar birthdate = Calendar.getInstance();
	             birthdate.setTimeInMillis(Long.parseLong(birth));
	             
	             Calendar deathdate = Calendar.getInstance();
	             deathdate.setTimeInMillis(Long.parseLong(death));
	             
	             //Instantiating the new Uzu object.
	             Uzu uzu = new Uzu(Integer.parseInt(id), Double.parseDouble(longitude), Double.parseDouble(latitude), subject, message, image.getBytes(), birthdate, Integer.parseInt(life), deathdate);
	             //Adding the Uzu object into the list
	             uzuList.add(uzu);
	             Log.d("UZU", "loop 2 " + i);
	        }
	     } catch (JSONException e) {
	         e.printStackTrace();
	     }
		 return uzuList;
	}
}
