package me.smudja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

enum DataManager {
	
	INSTANCE;
	
	private HashMap<String, String> people;
	private HashMap<String, String[]> meals;
	
	@SuppressWarnings("unchecked")
	private DataManager() {
		people = new HashMap<String, String>();
		meals = new HashMap<String, String[]>();
		new File("config").mkdir();
		try (ObjectInputStream inStreamMeals = new ObjectInputStream(new FileInputStream("config/meals"))) {
			this.meals = (HashMap<String, String[]>) inStreamMeals.readObject();
		}
		catch(FileNotFoundException fnfExc) {
			System.out.println("File Does Not Yet Exist");
		}
		catch (Exception exc) {
			System.out.println("Unable to load from file");
		}
		try (ObjectInputStream inStreamPeople = new ObjectInputStream(new FileInputStream("config/people"))) {
			this.people = (HashMap<String, String>) inStreamPeople.readObject();
		}
		catch(FileNotFoundException fnfExc) {
			System.out.println("File Does Not Yet Exist");
		}
		catch (Exception exc) {
			System.out.println("Unable to load from file");
		}
	}

	public void save() {
		new File("config").mkdir();
		try (ObjectOutputStream outStreamPeople = new ObjectOutputStream(new FileOutputStream("config/people"));
				ObjectOutputStream outStreamMeals = new ObjectOutputStream(new FileOutputStream("config/meals"))) {
			outStreamPeople.writeObject(people);
			outStreamMeals.writeObject(meals);
		}
		catch (IOException exc) {
			System.out.println("Unable to save to file");
			exc.printStackTrace();
		}
	}
	
	public void addPerson(String person, String phone) {
		if(people.containsKey(person)) return;
		people.put(person, phone);
		save();
	}
	
	public void removePerson(String person) {
		if(people.containsKey(person)) people.remove(person);
		save();
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
		save();
	}
	
	public void removeMeal(String meal) {
		if(meals.containsKey(meal)) {
			meals.remove(meal);
			save();
		}
	}
	
	public String[] getIngredients(String meal) {
		if(meals.containsKey(meal)) return meals.get(meal);
		return null;
	}
	
	public String[] getAllIngredients() {
		ArrayList<String> aryIngreds = new ArrayList<String>();
		for(String[] mealIngreds: meals.values()) {
			for(String ingred: mealIngreds) {
				aryIngreds.add(ingred);
			}
		}
		String[] ingreds = new String[aryIngreds.size()];
		return ingreds = aryIngreds.toArray(ingreds);
	}
	
	public String[] getMeals() {
		String[] strArrMeals = new String[meals.size()];
		return strArrMeals = meals.keySet().toArray(strArrMeals);
	}

}
