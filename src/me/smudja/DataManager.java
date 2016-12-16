package me.smudja;

import java.util.ArrayList;

enum DataManager {
	
	INSTANCE;
	
	private ArrayList<String> people;
	private ArrayList<String> meals;
	
	private DataManager() {
		people = load("people.txt");
		meals = load("meals.txt");
	}
	private ArrayList<String> load(String fileName) {
		// TODO load from file
		return new ArrayList<String>();
	}

	public void save() {
		// TODO save to file
	}
	
	public void addPerson(String person) {
		if(people.contains(person)) return;
		people.add(person);
	}
	
	public void removePerson(String person) {
		if(people.contains(person)) people.remove(person);
	}
	
	public String getPerson(String person) {
		if(people.contains(person)) return person;
		return null;
	}
	
	public String[] getPeople() {
		String[] strArrPeople = new String[people.size()];
		return strArrPeople = people.toArray(strArrPeople);
	}
	
	public void addMeal(String meal) {
		if(meals.contains(meal)) return;
		meals.add(meal);
	}
	
	public void removeMeal(String meal) {
		if(meals.contains(meal)) {
			meals.remove(meal);
		}
	}
	
	public String getMeal(String meal) {
		if(meals.contains(meal)) return meal;
		return null;
	}
	
	public String[] getMeals() {
		String[] strArrMeals = new String[meals.size()];
		return strArrMeals = meals.toArray(strArrMeals);
	}

}
