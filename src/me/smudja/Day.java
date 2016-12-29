package me.smudja;

/**
 * This enum represents a day of the week.
 * This enum is a parameter in {@link me.smudja.Shift Shift}.
 * 
 * @author smithl
 * @see	   me.smudja.Shift
 */
public enum Day {
	MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);
	
	/**
	 * Stores the x location of the day in a grid layout, where the leftmost cell is 1.
	 */
	private int loc;
	
	/**
	 * This constructor is never called
	 *  
	 * @param loc	The location of the day of the week in a grid layout
	 */
	private Day(int loc) {
		this.loc = loc;
	}
	
	/**
	 * This method returns the x location of the day of the week in a grid layout, where the leftmost cell is 1.
	 * 
	 * @return	the x location of the day of the week.
	 */
	public int getLoc() {
		return loc;
	}
	
	
	/**
	 * This static method returns the {@code Day} object associated with the {@code String} supplied as a parameter
	 * if it exists. 
	 * 
	 * @param strDay	The name of the day to find the {@code Day} for, in any case
	 * @return			The day as a {@code Day} object or {@code null}
	 */
	public static Day getDay(String strDay) {
		switch(strDay.toUpperCase()) {
			case "MONDAY":		return Day.MONDAY;
			case "TUESDAY":		return Day.TUESDAY;
			case "WEDNESDAY":	return Day.WEDNESDAY;
			case "THURSDAY":	return Day.THURSDAY;
			case "FRIDAY":		return Day.FRIDAY;
			case "SATURDAY":	return Day.SATURDAY;
			case "SUNDAY":		return Day.SUNDAY;
			default:			return null;
		}
	}
}
