package me.smudja;

public enum Period {
	MORNING(1), AFTERNOON(2), EVENING(3);
	
	private int loc;
	
	private Period(int loc) {
		this.loc = loc;
	}
	
	public int getLoc() {
		return loc;
	}
	
	public static Period getPeriod(String strPeriod) {
		switch(strPeriod.toUpperCase()) {
			case "MORNING":		return Period.MORNING;
			case "AFTERNOON":	return Period.AFTERNOON;
			case "EVENING":		return Period.EVENING;
			default:			return null;
		}
	}
}
