package me.smudja;

public enum Day {
	MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);
	
	private int loc;
	
	private Day(int loc) {
		this.loc = loc;
	}
	
	public int getLoc() {
		return loc;
	}
	
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
