package me.smudja;

import java.util.ArrayList;

enum ShiftManager {
	
	INSTANCE;
	
	private ArrayList<Shift> shifts;
	
	private ShiftManager() {
		shifts = new ArrayList<Shift>();
	}

	public ArrayList<Shift> load(String fromFile) {
		// TODO load shifts from file
		return new ArrayList<Shift>();
	}
	
	public void save(String toFile) {
		// TODO save shifts to file
	}
	
	public void purge() {
		shifts.clear();
	}
	
	public void add(Shift shift) {
		if(!shifts.contains(shift)) shifts.add(shift);
	}
	
	public void remove(Shift shift) {
		if(shifts.contains(shift)) shifts.remove(shift);
	}
	
	public Shift[] getShifts() {
		Shift[] shiftsArr = new Shift[shifts.size()];
		shiftsArr = shifts.toArray(shiftsArr);
		return shiftsArr;
	}
	
	public Shift clashes(Shift shift) {
		for(Shift s : shifts) {
			if(shift.getDay().equals(s.getDay()) && shift.getPeriod().equals(s.getPeriod())) {
				return s;
			}
		}
		return null;
	}

	public void printToConsole() {
		for(Shift s : shifts) {
			s.printToConsole();
			System.out.println();
		}
	}
	
}
