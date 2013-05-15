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
		String CREATE_UZU_TABLE = "CREATE TABLE Uzu (uzuID INTEGER PRIMARY KEY, longitude REAL, latitude REAL, subjectHeading TEXT, messageBody TEXT, image BLOB, birth DATE, life INTEGER, death DATE, categoryID INTEGER)";
		db.execSQL(CREATE_UZU_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Uzu");
		onCreate(db);
	}

	public void addUZU(Uzu uzu) {
		System.out.println("nem 0");
	    SQLiteDatabase db = this.getWritableDatabase();
	    System.out.println("nem 1");
	    ContentValues values = new ContentValues();
	    System.out.println("nem 2");
	    values.put("uzuID", uzu.getUzuID());
	    System.out.println("nem 3");
	    values.put("longitude", uzu.getLongitude());
	    System.out.println("nem 4");
	    values.put("latitude", uzu.getLatitude());
	    System.out.println("nem 5");
	    values.put("subjectHeading", uzu.getSubject());
	    System.out.println("nem 6");
	    values.put("messageBody", uzu.getMessage());
	    System.out.println("nem 7");
	    values.put("image", uzu.getImage().toString());
	    System.out.println("nem 8");
	    System.out.println(uzu.getBirth().toString()+ "8888888888");
	    System.out.println(uzu.getBirth());
	    values.put("birth", uzu.getBirth().toString()); 
	    System.out.println("nem 9");
	    values.put("life", uzu.getLife());
	    System.out.println("nem 10");
	    values.put("death", uzu.getDeath().toString());
	    System.out.println("nem 11");
	    values.put("categoryID", uzu.getCategoryID());
	    System.out.println("nem 12");
	 
	    db.insert("Uzu", null, values);
	    System.out.println("nem 13");
	    db.close();
	    System.out.println("nem 14");
	}
	
	boolean doesUzuExist(int uzuID) {
		SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Uzu", new String[] { "uzuID",
                "longitude", "latitude", "subjectHeading", "messageBody", "image", "birth" , "life" , "death", "categoryID" }, "uzuID" + "=?",
                new String[] { String.valueOf(uzuID) }, null, null, null, null);
        if (cursor != null) {
            return true;
        }
        else{
        	return false;
        }
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
	        	
	        	uzu.setImage(cursor.getString(5).getBytes());
	        	
	            Calendar birthdate = Calendar.getInstance();
	            birthdate.setTimeInMillis(Long.parseLong(cursor.getString(6)));
	        	uzu.setBirth(birthdate);
	        	
	        	uzu.setLife(Integer.parseInt(cursor.getString(7)));
	        	
	            Calendar deathdate = Calendar.getInstance();
	            birthdate.setTimeInMillis(Long.parseLong(cursor.getString(8)));
	        	uzu.setBirth(deathdate);
	        	
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
