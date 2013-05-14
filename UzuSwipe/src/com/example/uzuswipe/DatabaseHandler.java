package com.example.uzuswipe;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context) {
		super(context, "Uzu", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_UZU_TABLE = "CREATE TABLE Uzu (uzuID INTEGER PRIMARY KEY, longitude REAL, latitude REAL, subjectHeading TEXT, messageBody TEXT, image BLOB, brith DATE, life INTEGER, death DATE, categoryID INTEGER";
		db.execSQL(CREATE_UZU_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Uzu");
		onCreate(db);
	}

	public void addUZU(Uzu uzu) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("uzuID", uzu.getUzuID());
	    values.put("longitude", uzu.getLongitude());
	    values.put("latitude", uzu.getLatitude());
	    values.put("subjectHeading", uzu.getSubject());
	    values.put("messageBody", uzu.getMessage());
	    values.put("image", uzu.getImage().toString());
	    values.put("birth", uzu.getBirth().toString());
	    values.put("life", uzu.getLife());
	    values.put("death", uzu.getDeath().toString());
	    values.put("categoryID", uzu.getCategoryID());
	 
	    db.insert("Uzu", null, values);
	    db.close();
	}
	
	public Uzu[] getAllUzus() {
		
	    List<Uzu> uzuList = new ArrayList<Uzu>();
	    String selectQuery = "SELECT  * FROM Uzu";
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	Uzu uzu = new Uzu();
	        	uzu.setUzuID(Integer.parseInt(cursor.getString(0)));
	        	uzu.setLongitude(Double.parseDouble(cursor.getString(1)));
	        	uzu.setLatitude(Double.parseDouble(cursor.getString(2)));
	        	uzu.setSubject(cursor.getString(3));
	        	uzu.setMessage(cursor.getString(4));
	        	
	            Blob blob = null;
	            try {
					blob.setBytes(1, cursor.getBlob(5));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("Convert to blob failed");
				}
	        	uzu.setImage(blob);
	        	
	        	Calendar birth = Calendar.getInstance();
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            try {
					birth.setTime(sdf.parse(cursor.getString(6)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Convert to birth date failed");
				}
	        	uzu.setBirth(birth);
	        	
	        	uzu.setLife(Integer.parseInt(cursor.getString(7)));
	        	
	        	Calendar death = Calendar.getInstance();
	            try {
					death.setTime(sdf.parse(cursor.getString(8)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Convert to death date failed");
				}
	        	uzu.setDeath(death);
	        	
	        	uzu.setCategoryID(Integer.parseInt(cursor.getString(9)));
	        	
	            // Adding contact to list
	            uzuList.add(uzu);
	        } while (cursor.moveToNext());
	    }
	 
	    
	    Uzu[] uzuArray = new Uzu[uzuList.size()];
	    uzuList.toArray(uzuArray);
	    // return contact list
	    return uzuArray;
	}
	
	public void deleteUzu(Uzu uzu) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("Uzu", "uzuID" + " = ?",
	            new String[] { String.valueOf(uzu.getUzuID()) });
	    db.close();
	}
	
}
