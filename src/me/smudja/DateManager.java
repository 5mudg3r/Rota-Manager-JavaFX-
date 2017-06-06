package me.smudja;

import java.io.File;
import java.util.Scanner;

enum DateManager {
	
	INSTANCE;
	
	private Scanner x;
	private String[] arrayOfDates = new String[7];
	
	public void openFile(){		
		try{
			x = new Scanner(new File("config/weekdates.txt"));
		}
		catch(Exception e){
			System.out.println("Could not open file\n");
		}
	}
	
	public void readFile(){
		int i = 0;
		while (x.hasNext()){
			String a = x.next();
			String b = x.next();
			arrayOfDates[i] = a + " " + b;
			i += 1;
		}
	}
	
	public void closeFile(){
		x.close();
	}
	
	public String[] getDates() {
		return this.arrayOfDates;
	}
}