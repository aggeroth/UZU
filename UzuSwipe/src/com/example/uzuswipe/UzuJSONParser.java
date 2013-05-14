package com.example.uzuswipe;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

//import android.util.Log;

public class UzuJSONParser {
	
	// JSON Node names
    private static final String TAG_UZUID = "uzuID";
    private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_SUBJECT = "subjectHeading";
    private static final String TAG_MESSAGE = "messageBody";
    private static final String TAG_BIRTH = "birth";
    private static final String TAG_LIFE = "life";
    private static final String TAG_DEATH = "death";
    private static final String TAG_IMAGE = "image";
	
	String uzuJSONString;
    JSONArray uzuArray;
	JSONObject jsonObject;
	List<Uzu> uzuList = new ArrayList<Uzu>();
	
	public UzuJSONParser(String jsonString){
		uzuJSONString = jsonString;
		 Log.d("UZU", uzuJSONString);
	}
	
	public List<Uzu> parse(){
		try { 
	        //parsing the string into JSONArray
			uzuArray = new JSONArray(uzuJSONString);
			
			// looping through All Contacts
	         for(int i = 0; i < uzuArray.length(); i++){
	        	 JSONObject u = uzuArray.getJSONObject(i);    
	        	 Log.d("UZU", "loop 1 " + i);
	        	 // Storing each json item in variable
	        	 String id = u.getString(TAG_UZUID);
	        	 String longitude = u.getString(TAG_LONGITUDE);
	             String latitude = u.getString(TAG_LATITUDE);
	             String subject = u.getString(TAG_SUBJECT);
	             String message = u.getString(TAG_MESSAGE);
	             String birth = u.getString(TAG_BIRTH);
	             String life = u.getString(TAG_LIFE);
	             String death = u.getString(TAG_DEATH);
	             String image = u.getString(TAG_IMAGE);
	             
	             Uzu uzu = new Uzu(Float.parseFloat(longitude), Float.parseFloat(latitude), subject, message, image.getBytes(), Integer.parseInt(life));
	             uzuList.add(uzu);
	             Log.d("UZU", "loop 2 " + i);
	        }
	     } catch (JSONException e) {
	         e.printStackTrace();
	     }
		 return uzuList;
	}
}
