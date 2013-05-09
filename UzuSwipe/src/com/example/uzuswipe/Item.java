package com.example.uzuswipe;

import android.location.Location;

public class Item {
	
	private String itemSubject;
	private String itemContent;
	private Location itemLocation;
	
	public Item(){
		
	}
	
	public Item(String subject, String content){
		itemSubject = subject;
		itemContent = content;
	}
	
	public Item(String subject, String content, Location location){
		itemSubject = subject;
		itemContent = content;
		itemLocation = location;
	}
	
	public String getItemSubject(){
		return itemSubject;
	}
	
	public String getItemContent(){
		return itemContent;
	}
	
	public Location getItemLocation(){
		return itemLocation;
	}
	
	public void setItemLocation(Location newLocation){
		itemLocation = newLocation;
	}
	
	@Override
	public String toString(){
		return itemSubject;
	}

}
