package me.smudja;

public enum Period {
	MORNING, AFTERNOON, EVENING;
	
	public static Period getPeriod(String strPeriod) {
		switch(strPeriod.toUpperCase()) {
			case "MORNING":		return Period.MORNING;
			case "AFTERNOON":	return Period.AFTERNOON;
			case "EVENING":		return Period.EVENING;
			default:			return null;
		}
	}
}
