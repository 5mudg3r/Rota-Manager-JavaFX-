package me.smudja;

import java.io.Serializable;

/**
 * This class represents a shift on the rota.
 * It defines a series of objects with getters and setters.
 * 
 * @author smithl
 */
public class Shift implements Serializable {
	
	/**
	 * This is serial version of the class, used so that we can save the shift using an {@code ObjectOutputStream} in
	 * {@link me.smudja.ShiftManager#save(String) ShiftManager}.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The day in which the shift takes place
	 * 
	 * @see	me.smudja.Day
	 */
	private Day day;
	/**
	 * The period in which the shift takes place
	 * 
	 * @see me.smudja.Period
	 */
	private Period period;
	/**
	 * The name of the person taking the shift
	 */
	private String personName;
	/**
	 * The phone number for the person taking the shift
	 */
	private String personPhone;
	/**
	 * The name of the meal for the shift
	 */
	private String mealName;
	/**
	 * The ingredients for the meal on the shift
	 */
	private String[] mealIngreds;
	
	/**
	 * The constructor for creating a new shift
	 * 
	 * @param day			The day the shift takes place
	 * @param period		The period during which the shift takes place
	 * @param personName	The name of the person on the shift
	 * @param personPhone	The phone number of the person on the shift
	 * @param mealName		The name of the meal on the shift
	 * @param mealIngreds	The ingredients of the meal on the shift
	 */
	public Shift(Day day, Period period, String personName, String personPhone, String mealName, String[] mealIngreds) {
		this.day = day;
		this.period = period;
		this.personName = personName;
		this.personPhone = personPhone;
		this.mealName = mealName;
		this.mealIngreds = mealIngreds;
	}
	
	/**
	 * The is a getter for {@link me.smudja.Shift#personName personName}.
	 * 
	 * @return The person on the current shift
	 */
	public String getPerson() {
		return personName;
	}
	
	/**
	 * The is a getter for {@link me.smudja.Shift#mealName mealName}.
	 * 
	 * @return The meal on the current shift
	 */
	public String getMeal() {
		return mealName;
	}
	
	/**
	 * The is a getter for {@link me.smudja.Shift#day day}.
	 * 
	 * @return The day of the current shift
	 */
	public Day getDay() {
		return day;
	}
	
	/**
	 * The is a getter for {@link me.smudja.Shift#period period}.
	 * 
	 * @return The period of the current shift
	 */
	public Period getPeriod() {
		return period;
	}
	
	/**
	 * The is a getter for {@link me.smudja.Shift#personPhone personPhone}.
	 * 
	 * @return The phone number of the person on the current shift
	 */
	public String getPhone() {
		return personPhone;
	}
	
	/**
	 * The is a getter for {@link me.smudja.Shift#mealIngreds mealIngreds}.
	 * 
	 * @return The ingredients for the meal on the current shift
	 */
	public String[] getIngredients() {
		return mealIngreds;
	}
}
