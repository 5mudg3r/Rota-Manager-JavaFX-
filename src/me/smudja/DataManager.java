package me.smudja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * This enum manages all the people and meals currently available to the application.
 * Only 1 instance of this enum ever exists and is accessed through {@code INSTANCE}
 * 
 * @author smithl
 * @see    me.smudja.Shift
 */
enum DataManager {
	
	INSTANCE;
	
	/**
	 * A hash map containing all people along with their phone numbers as Strings.
	 * This hash map uses the person's name as a key.
	 */
	private HashMap<String, String> people;
	/**
	 * A hash map containing all meals along with their ingredients as a String array.
	 * This hash map uses the meal's name as a key.
	 */
	private HashMap<String, String[]> meals;
	
	/**
	 * Constructor for the enum.
	 * This is only called when the enum is first called in {@link me.smudja.RotaManager#init() RotaManager}.
	 * 
	 * It constructs a {@code config/} directory if it does not exist and tries to load people and meals into the two
	 * hash maps if possible.
	 */
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

	
	/**
	 * This method saves the both hash maps via an {@code ObjectOutputStream}. It saves each hash map to its own file from
	 * which the constructor tries to load the hash maps. It handles any exceptions by printing to console.
	 */
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
	
	/**
	 * This method adds a new person to the {@code people} hash map if a person of the same name does not already exist.
	 * It then saves both hash maps ({@code people} and {@code meals}).
	 * 
	 * @param person	The name of the person to add
	 * @param phone		The phone number of the person being added
	 */
	public void addPerson(String person, String phone) {
		if(people.containsKey(person)) return;
		people.put(person, phone);
		save();
	}
	
	/**
	 * This method tries to remove the person specified in the parameter, if they are present.
	 * It then saves both hash maps.
	 * 
	 * @param person	The name of the person to remove
	 */
	public void removePerson(String person) {
		if(people.containsKey(person)) people.remove(person);
		save();
	}
	
	/**
	 * This method returns the phone number for the person specified in the parameter if they are in {@code people}.
	 * 
	 * @param person	Person whose phone number you want
	 * @return			Phone number of person
	 */
	public String getPhone(String person) {
		if(people.containsKey(person)) return people.get(person);
		return null;
	}
	
	/**
	 * This method returns a String array of all the people's names in {@code people}.
	 * 
	 * @return String array of all people's names
	 */
	public String[] getPeople() {
		String[] strArrPeople = new String[people.size()];
		strArrPeople = people.keySet().toArray(strArrPeople);
		return strArrPeople;
	}
	
	/**
	 * This method adds a new meal to the {@code meals} hash map if a meal of the same name does not already exist.
	 * It then saves both hash maps ({@code people} and {@code meals}).
	 * 
	 * @param meal		The name of the meal to add
	 * @param ingreds	The ingredients of the meal being added
	 */
	public void addMeal(String meal, String[] ingreds) {
		if(meals.containsKey(meal)) return;
		meals.put(meal, ingreds);
		save();
	}
	
	/**
	 * This method tries to remove the meal specified in the parameter, if it is present.
	 * It then saves both hash maps.
	 * 
	 * @param meal	The name of the meal to remove
	 */
	public void removeMeal(String meal) {
		if(meals.containsKey(meal)) {
			meals.remove(meal);
			save();
		}
	}
	
	/**
	 * This method returns the ingredients for the meal specified in the parameter if it is in {@code meals}.
	 * 
	 * @param meal	Meal whose ingredients you want
	 * @return		Array of ingredients of meal
	 */
	public String[] getIngredients(String meal) {
		if(meals.containsKey(meal)) return meals.get(meal);
		return null;
	}
	
//	public String[] getAllIngredients() {
//		ArrayList<String> aryIngreds = new ArrayList<String>();
//		for(String[] mealIngreds: meals.values()) {
//			for(String ingred: mealIngreds) {
//				aryIngreds.add(ingred);
//			}
//		}
//		String[] ingreds = new String[aryIngreds.size()];
//		return ingreds = aryIngreds.toArray(ingreds);
//	}
	
	/**
	 * This method returns a String array of all the meal's names in {@code meals}.
	 * 
	 * @return String array of all meal's names
	 */
	public String[] getMeals() {
		String[] strArrMeals = new String[meals.size()];
		return strArrMeals = meals.keySet().toArray(strArrMeals);
	}

}
