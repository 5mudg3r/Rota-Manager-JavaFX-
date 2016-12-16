package me.smudja;

public class Shift {
	
	private Day day;
	private Period period;
	private String personName;
	private String personPhone;
	private String mealName;
	private String[] mealIngreds;
	
	public Shift(Day day, Period period, String personName, String personPhone, String mealName, String[] mealIngreds) {
		this.day = day;
		this.period = period;
		this.personName = personName;
		this.personPhone = personPhone;
		this.mealName = mealName;
		this.mealIngreds = mealIngreds;
	}
	
	public Day getDay() {
		return day;
	}
	
	public Period getPeriod() {
		return period;
	}
	
	public void printToConsole() {
		System.out.println("Day: " + day.toString());
		System.out.println("Period: " + period.toString());
		System.out.println("Person: " + personName);
		System.out.println("Phone: " + personPhone);
		System.out.println("Meal: " + mealName);
	}
}
