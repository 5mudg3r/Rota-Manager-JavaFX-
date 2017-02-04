package me.smudja;

/**
 * This enum represents a period of a day.
 * This enum is a parameter in {@link me.smudja.Shift Shift}.
 * 
 * @author smithl
 * @see	   me.smudja.Shift
 */
public enum Period {
	MORNING(1), AFTERNOON(2), EVENING(3);
	
	/**
	 * Stores the y location of the period in a grid layout, where the uppermost cell is 1.
	 */
	private int loc;
	
	/**
	 * This constructor is never called
	 *  
	 * @param loc	The location of the period in a grid layout
	 */
	private Period(int loc) {
		this.loc = loc;
	}
	
	/**
	 * This method returns the y location of the period in a grid layout, where the uppermost cell is 1.
	 * 
	 * @return	the y location of the period.
	 */
	public int getLoc() {
		return loc;
	}
	
	/**
	 * This static method returns the {@code Period} object associated with the {@code String} supplied as a parameter
	 * if it exists. 
	 * 
	 * @param strPeriod	The name of the period to find the {@code Period} for, in any case
	 * @return			The period as a {@code Period} object or {@code null}
	 */
	public static Period getPeriod(String strPeriod) {
		switch(strPeriod.toUpperCase()) {
			case "MORNING":		return Period.MORNING;
			case "AFTERNOON":	return Period.AFTERNOON;
			case "EVENING":		return Period.EVENING;
			default:			return null;
		}
	}

	public static String getPeriodString(int period) {
		for(Period p : Period.values()) {
			if(p.getLoc() == period) {
				return p.toString();
			}
		}
		return null;
	}
}
