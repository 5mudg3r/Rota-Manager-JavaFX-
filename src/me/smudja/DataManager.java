package me.smudja;

import java.util.HashMap;

enum DataManager {
	
	INSTANCE;
	
	private HashMap<String, String> people;
	private HashMap<String, String[]> meals;
	
	private DataManager() {
		people = loadPeople("people.txt");
		meals = loadMeals("meals.txt");
	}
	private HashMap<String, String> loadPeople(String fileName) {
		// TODO load from file
		return new HashMap<String, String>();
	}
	private HashMap<String, String[]> loadMeals(String fileName) {
		// TODO load from file
		return new HashMap<String, String[]>();
	}

	public void save() {
		// TODO save to file
	}
	
	public void addPerson(String person, String phone) {
		if(people.containsKey(person)) return;
		people.put(person, phone);
	}
	
	public void removePerson(String person) {
		if(people.containsKey(person)) people.remove(person);
	}
	
	public String getPhone(String person) {
		if(people.containsKey(person)) return people.get(person);
		return null;
	}
	
	public String[] getPeople() {
		String[] strArrPeople = new String[people.size()];
		strArrPeople = people.keySet().toArray(strArrPeople);
		return strArrPeople;
	}
	
	public void addMeal(String meal, String[] ingreds) {
		if(meals.containsKey(meal)) return;
		meals.put(meal, ingreds);
	}
	
	public void removeMeal(String meal) {
		if(meals.containsKey(meal)) {
			meals.remove(meal);
		}
	}
	
	public String[] getIngredients(String meal) {
		if(meals.containsKey(meal)) return meals.get(meal);
		return null;
	}
	
	public String[] getMeals() {
		String[] strArrMeals = new String[meals.size()];
		return strArrMeals = meals.keySet().toArray(strArrMeals);
	}

}
