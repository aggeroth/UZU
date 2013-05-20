package uzu.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class that creates and manages the SQLite database.
 * 
 * @author Nem
 *
 */
/**
 * @author Nem
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context) {
		super(context, "Uzu", null, 1);
	}

	/* 
	 * This function creates the Uzu table
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_UZU_TABLE = "CREATE TABLE Uzu (uzuID INTEGER PRIMARY KEY, longitude REAL, " +
				"latitude REAL, subjectHeading TEXT, messageBody TEXT, image TEXT, birth DATE, " +
				"life INTEGER, death DATE, categoryID INTEGER)";
		db.execSQL(CREATE_UZU_TABLE);
	}
	/*
	 * This function is used upgrade the Uzu table.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Uzu");
		onCreate(db);
	}

	/**
	 * This method is responsible for adding Uzu items to the SQLite database.
	 * 
	 * @param uzu
	 */
	public void addUZU(Uzu uzu) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put("uzuID", uzu.getUzuID());
	    values.put("longitude", uzu.getLongitude());
	    values.put("latitude", uzu.getLatitude());
	    values.put("subjectHeading", uzu.getSubject());
	    values.put("messageBody", uzu.getMessage());
	    values.put("image", uzu.getImage());
	    values.put("birth", uzu.getBirth().toString()); 
	    values.put("life", uzu.getLife());
	    values.put("death", uzu.getDeath().toString());
	    values.put("categoryID", uzu.getCategoryID());
	 
	    db.insert("Uzu", null, values);
	    db.close();
	}
	
	/**
	 * This method checks to see if a given uzuID already exists in the SQLite database.
	 * 
	 * @param uzuID
	 * @return true/false
	 */
	boolean doesUzuExist(int uzuID) {
	    String selectQuery = "SELECT  * FROM Uzu WHERE uzuID = " + uzuID;
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
        if (cursor != null) {
        	System.out.println(cursor);
        	System.out.println("meow true");
            return true;
        }
        else{
        	System.out.println("meow false");
        	return false;
        }
		
		
		
		
		
//		System.out.println("meow");
//		SQLiteDatabase db = this.getReadableDatabase();
//		System.out.println("meow2");
//        Cursor cursor = db.query("Uzu", new String[] { "uzuID",
//                "longitude", "latitude", "subjectHeading", "messageBody", "image", "birth" , "life" , "death", "categoryID" }, "uzuID" + "=?",
//                new String[] { String.valueOf(uzuID) }, null, null, null, null);
//        System.out.println("meow3");
//        if (cursor != null) {
//        	System.out.println("meow true");
//            return true;
//        }
//        else{
//        	System.out.println("meow false");
//        	return false;
//        }
    }
	
	/**
	 * This method is responsible for returning all the Uzu items in the SQLite databse.
	 * 
	 * @return Uzu[]
	 */
	public Uzu[] getAllUzus() {
	    List<Uzu> uzuList = new ArrayList<Uzu>();
	    String selectQuery = "SELECT  * FROM Uzu";
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	Uzu uzu = new Uzu();
	        	uzu.setUzuID(Integer.parseInt(cursor.getString(0)));
	        	uzu.setLongitude(Double.parseDouble(cursor.getString(1)));
	        	uzu.setLatitude(Double.parseDouble(cursor.getString(2)));
	        	uzu.setSubject(cursor.getString(3));
	        	uzu.setMessage(cursor.getString(4));
	        	uzu.setImage(cursor.getString(5));
	        	
	            Calendar birthdate = Calendar.getInstance();
	            birthdate.setTimeInMillis(cursor.getLong(6));
	        	uzu.setBirth(birthdate);
	        	
	            Calendar deathdate = Calendar.getInstance();
	            birthdate.setTimeInMillis(cursor.getLong(8));
	        	uzu.setBirth(deathdate);
	        	
	        	uzu.setCategoryID(Integer.parseInt(cursor.getString(9)));
	        	
	            uzuList.add(uzu);
	        } while (cursor.moveToNext());
	    }
	    Uzu[] uzuArray = new Uzu[uzuList.size()];
	    uzuList.toArray(uzuArray);

	    return uzuArray;
	}
	
	public int getDatabaseSize() {
	    List<Uzu> uzuList = new ArrayList<Uzu>();
	    String selectQuery = "SELECT  * FROM Uzu";
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	Uzu uzu = new Uzu();
	        	uzu.setUzuID(Integer.parseInt(cursor.getString(0)));
	        	uzu.setLongitude(Double.parseDouble(cursor.getString(1)));
	        	uzu.setLatitude(Double.parseDouble(cursor.getString(2)));
	        	uzu.setSubject(cursor.getString(3));
	        	uzu.setMessage(cursor.getString(4));
	        	uzu.setImage(cursor.getString(5));
	        	
	            Calendar birthdate = Calendar.getInstance();
	            birthdate.setTimeInMillis(cursor.getLong(6));
	        	uzu.setBirth(birthdate);
	        	
	            Calendar deathdate = Calendar.getInstance();
	            birthdate.setTimeInMillis(cursor.getLong(8));
	        	uzu.setBirth(deathdate);
	        	
	        	uzu.setCategoryID(Integer.parseInt(cursor.getString(9)));
	        	
	            uzuList.add(uzu);
	        } while (cursor.moveToNext());
	    }

	    return uzuList.size();
	}
	
	/**
	 * This method is responsible for deleting an Uzu item in the SQLite database by passing in it's Uzu Fragment
	 * 
	 * @param uzu
	 */
	public void deleteUzu(UzuFragment uzu) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("Uzu", "uzuID" + " = ?",
	            new String[] { String.valueOf(uzu.getUzuID()) });
	    db.close();
	}
	
}
