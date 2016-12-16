package me.smudja;

public enum Day {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
	
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
