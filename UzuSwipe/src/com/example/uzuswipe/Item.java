package com.example.uzuswipe;

public class Item {
	
	private String itemSubject;
	private String itemContent;
	
	public Item(){
		
	}
	
	public Item(String subject, String content){
		itemSubject = subject;
		itemContent = content;
	}
	
	public String getItemSubject(){
		return itemSubject;
	}
	
	public String getItemContent(){
		return itemContent;
	}
	
	@Override
	public String toString(){
		return itemSubject;
	}

}
