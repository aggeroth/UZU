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
		System.out.println("getalluzuz 1");
		
	    List<Uzu> uzuList = new ArrayList<Uzu>();
	    System.out.println("getalluzuz 2");
	    String selectQuery = "SELECT  * FROM Uzu";
	    System.out.println("getalluzuz 3");
	    SQLiteDatabase db = this.getWritableDatabase();
	    System.out.println("getalluzuz 4");
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    System.out.println("getalluzuz 5");
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	System.out.println("getalluzuz 6");
	        	Uzu uzu = new Uzu();
	        	System.out.println("getalluzuz 7");
	        	uzu.setUzuID(Integer.parseInt(cursor.getString(0)));
	        	System.out.println("getalluzuz 8");
	        	uzu.setLongitude(Double.parseDouble(cursor.getString(1)));
	        	System.out.println("getalluzuz 9");
	        	uzu.setLatitude(Double.parseDouble(cursor.getString(2)));
	        	System.out.println("getalluzuz 10");
	        	uzu.setSubject(cursor.getString(3));
	        	System.out.println("getalluzuz 11");
	        	uzu.setMessage(cursor.getString(4));
	        	System.out.println("getalluzuz 12");
	        	
	        	uzu.setImage(cursor.getString(5).getBytes());
	        	System.out.println("getalluzuz 13");
	            Calendar birthdate = Calendar.getInstance();
	            System.out.println("getalluzuz 14");
	            System.out.println(cursor.getString(6));
	            birthdate.setTimeInMillis(Long.parseLong(cursor.getString(6)));
	            System.out.println("getalluzuz 15");
	        	uzu.setBirth(birthdate);
	        	System.out.println("getalluzuz 16");
	        	uzu.setLife(Integer.parseInt(cursor.getString(7)));
	        	System.out.println("getalluzuz 17");
	        	
	            Calendar deathdate = Calendar.getInstance();
	            System.out.println("getalluzuz 18");
	            birthdate.setTimeInMillis(Long.parseLong(cursor.getString(8)));
	            System.out.println("getalluzuz 19");
	        	uzu.setBirth(deathdate);
	        	System.out.println("getalluzuz 20");
	        	uzu.setCategoryID(Integer.parseInt(cursor.getString(9)));
	        	System.out.println("getalluzuz 21");
	            // Adding contact to list
	            uzuList.add(uzu);
	            System.out.println("getalluzuz 22");
	        } while (cursor.moveToNext());
	    }
	 
	    System.out.println("getalluzuz 23");
	    Uzu[] uzuArray = new Uzu[uzuList.size()];
	    System.out.println("getalluzuz 24");
	    uzuList.toArray(uzuArray);
	    System.out.println("getalluzuz 25");
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
